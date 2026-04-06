# Flink Application Guidelines for Backend Developers

> A checklist and anti-pattern guide for developers coming from non-distributed backgrounds.
> Derived from Flink 2.2 documentation, `StreamTask.java` internals, and operator state source code.

---

## 25 MUST-DO Checklist (Things You Will Forget)

### Identity & Savepoints

- [ ] **1. Set `.uid("descriptive-name")` on EVERY operator.**
  Without it, Flink auto-generates operator IDs from topology hash — any code refactor (reordering, adding operators) breaks savepoint restore. This is the #1 mistake.
  ```java
  stream
      .map(new MyMapper()).uid("my-mapper")
      .filter(new MyFilter()).uid("my-filter");
  ```

- [ ] **2. Set `.name("readable-name")` on every operator.**
  This is what shows in the Flink Web UI. Without it you get `Flat Map -> Sink: Unnamed` which is undebuggable in production.

- [ ] **3. Set `.setParallelism()` explicitly on operators with known bottlenecks** (e.g., a DB sink).
  Don't rely solely on `env.setParallelism()` — a single global parallelism is rarely correct for all stages.

### State Management

- [ ] **4. Register ALL state in `open()` or `initializeState()`, NEVER in `processElement()`.**
  State descriptors must be created once during initialization. Creating them per-record is a silent performance killer — it won't crash, it just gets slow.
  ```java
  // CORRECT
  public void open(Configuration parameters) {
      ValueStateDescriptor<Long> desc = new ValueStateDescriptor<>("count", Long.class);
      countState = getRuntimeContext().getState(desc);
  }
  ```

- [ ] **5. Use `CheckpointedFunction` (not deprecated `ListCheckpointed`) for operator state.**
  Implement `initializeState()` to register state, check `context.isRestored()` to distinguish fresh start from recovery.
  ```java
  public class MyFunction implements CheckpointedFunction {
      private ListState<Long> checkpointedState;

      @Override
      public void initializeState(FunctionInitializationContext context) {
          checkpointedState = context.getOperatorStateStore()
              .getListState(new ListStateDescriptor<>("state", Long.class));
          if (context.isRestored()) {
              // recover from checkpoint
          }
      }

      @Override
      public void snapshotState(FunctionSnapshotContext context) {
          checkpointedState.clear();
          checkpointedState.add(currentValue);
      }
  }
  ```

- [ ] **6. Configure State TTL (`StateTtlConfig`) for any keyed state that can grow unbounded.**
  E.g., a `MapState` tracking user sessions — without TTL, state grows forever and eventually OOMs or makes checkpoints huge.
  ```java
  StateTtlConfig ttlConfig = StateTtlConfig
      .newBuilder(Duration.ofHours(24))
      .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
      .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
      .build();

  ValueStateDescriptor<String> descriptor = new ValueStateDescriptor<>("session", String.class);
  descriptor.enableTimeToLive(ttlConfig);
  ```

- [ ] **7. Clear state you no longer need.**
  Call `state.clear()` explicitly when a key's lifecycle ends (e.g., session closes). TTL is a safety net, not a substitute for explicit cleanup.

- [ ] **8. Choose the right state primitive.**
  - `ValueState` — single value per key
  - `MapState` — key-value lookups per key (NOT a `ValueState<HashMap<>>` — that serializes the entire map on every access)
  - `ListState` — append-heavy workloads
  - `ReducingState` / `AggregatingState` — pre-aggregated counters

### Checkpointing & Fault Tolerance

- [ ] **9. Enable checkpointing with a sane interval.**
  Without this line, there is ZERO fault tolerance — a crash loses everything.
  ```java
  env.enableCheckpointing(60_000); // 1 minute
  ```

- [ ] **10. Set `CheckpointingMode.EXACTLY_ONCE`** (it's the default, but be explicit).
  Understand that this means exactly-once *state*, not exactly-once *side effects* — your sink must support transactions or idempotent writes for end-to-end guarantees.

- [ ] **11. Configure externalized checkpoints.**
  Without this, cancelling a job deletes all checkpoints and you cannot resume.
  ```java
  env.getCheckpointConfig().setExternalizedCheckpointRetention(
      ExternalizedCheckpointRetention.RETAIN_ON_CANCELLATION);
  ```

- [ ] **12. Set `minPauseBetweenCheckpoints`** (e.g., 30s).
  Prevents checkpoint storms where one slow checkpoint triggers the next immediately, creating a death spiral under backpressure.
  ```java
  env.getCheckpointConfig().setMinPauseBetweenCheckpoints(30_000);
  ```

- [ ] **13. Set `checkpointTimeout`** (e.g., 10 minutes).
  A checkpoint that hangs forever blocks the next one. The default of 10 min is usually fine but think about your state size.

### Serialization

- [ ] **14. Use Flink's type system, not raw Java serialization.**
  Prefer POJOs (public class, no-arg constructor, public fields or getters/setters) or use explicit type hints. Falling back to Kryo serialization is 10-100x slower and breaks schema evolution.
  ```java
  // GOOD: Flink POJO
  public class SensorReading {
      public String sensorId;
      public long timestamp;
      public double temperature;

      public SensorReading() {} // no-arg constructor required
  }

  // BAD: Flink falls back to Kryo
  public class SensorReading implements Serializable {
      private final String sensorId; // final fields = not a POJO
  }
  ```

- [ ] **15. Register custom serializers for third-party classes** that Flink can't auto-detect.
  Unregistered types get Kryo with full class names in every record.
  ```java
  env.getConfig().registerTypeWithKryoSerializer(MyClass.class, MySerializer.class);
  ```

### Time & Watermarks

- [ ] **16. Use event time, not processing time, for any windowed computation.**
  Processing time gives non-deterministic results — replaying from Kafka produces different output.

- [ ] **17. Assign watermarks at the source, not downstream.**
  Assigning watermarks after a `map()` or `filter()` means the framework can't track per-partition watermarks, causing idle partitions to stall everything.
  ```java
  KafkaSource<Event> source = KafkaSource.<Event>builder()
      .setBootstrapServers("localhost:9092")
      .setTopics("events")
      .build();

  DataStream<Event> stream = env.fromSource(
      source,
      WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ofSeconds(5))
          .withTimestampAssigner((event, ts) -> event.getTimestamp()),
      "kafka-source"
  ).uid("kafka-source");
  ```

- [ ] **18. Handle idle sources with `.withIdleness(Duration.ofMinutes(1))`.**
  If one Kafka partition has no data, its watermark stays at `Long.MIN_VALUE` and holds back ALL downstream windows forever.
  ```java
  WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ofSeconds(5))
      .withTimestampAssigner((event, ts) -> event.getTimestamp())
      .withIdleness(Duration.ofMinutes(1));
  ```

### Connectors & I/O

- [ ] **19. Use Flink's async I/O (`AsyncDataStream.unorderedWait()`) for external lookups.**
  A synchronous HTTP call in `processElement()` blocks the entire operator chain — one slow response stalls all records.
  ```java
  AsyncDataStream.unorderedWait(
      inputStream,
      new AsyncDatabaseLookup(),
      5000, TimeUnit.MILLISECONDS, // timeout
      100  // max concurrent requests
  ).uid("async-db-lookup");
  ```

- [ ] **20. Use `SinkFunction` or the new `Sink` API with at-least-once/exactly-once guarantees.**
  Don't write to databases in `processElement()` with raw JDBC — you lose batching, retry logic, and checkpoint integration.

### Resource Configuration

- [ ] **21. Set `taskmanager.memory.managed.fraction` appropriately for RocksDB.**
  RocksDB state backend uses off-heap managed memory for its block cache and write buffers. The default (0.4) may not be enough for state-heavy jobs.

- [ ] **22. Set max parallelism at job creation and NEVER change it.**
  This determines the number of key groups. Changing it later invalidates all existing savepoints for keyed state.
  ```java
  env.setMaxParallelism(256); // set once, never change
  ```

### Testing & Deployment

- [ ] **23. Write tests using `MiniClusterWithClientResource` or the test harness.**
  `KeyedOneInputStreamOperatorTestHarness` lets you unit-test stateful operators with watermarks, timers, and checkpoints — without spinning up a cluster.

- [ ] **24. Test savepoint compatibility before deploying.**
  Take a savepoint from old version, restore with new code. If you forgot UIDs or changed state schema without migration, you find out here — not in production at 3am.

- [ ] **25. Set `restart-strategy` explicitly.**
  The default depends on whether checkpointing is enabled. Without it, a single `NullPointerException` kills the whole job permanently.
  ```java
  env.setRestartStrategy(
      RestartStrategies.fixedDelayRestart(3, Time.seconds(10)));
  ```

---

## 25 DON'Ts (Anti-Patterns That Seem Reasonable)

### State Abuse

> **1. DON'T use `ValueState<HashMap<K,V>>`.**
> Use `MapState<K,V>` instead. ValueState serializes/deserializes the *entire* HashMap on every access. MapState gives you per-key-entry access with RocksDB, meaning O(1) lookups instead of O(n) deserialization.

> **2. DON'T store large blobs (images, files) in Flink state.**
> State is checkpointed — a 10MB blob per key means your checkpoint is gigabytes. Use an external store (S3, Redis) and keep only references in state.

> **3. DON'T access keyed state outside a keyed context.**
> Calling `getRuntimeContext().getState()` in a non-keyed operator throws a runtime exception. Keyed state is only available after a `keyBy()`.

> **4. DON'T assume state is shared between parallel instances.**
> Each parallel subtask has its own state partition. There is no "global state" — if you need cross-partition coordination, use `BroadcastState` pattern or an external system.

### Threading & Concurrency

> **5. DON'T spawn your own threads in operators.**
> Flink's mailbox model (see `StreamTask.processInput()`) runs all operator methods in a single thread. Background threads that touch state or emit records cause race conditions, corrupt state, and produce non-deterministic bugs. Use `AsyncDataStream` for async work.

> **6. DON'T use `static` mutable fields in operators.**
> Parallel instances in the same TaskManager JVM share statics. You get invisible cross-operator contamination that works on parallelism=1 and explodes on parallelism=8.

> **7. DON'T do blocking I/O in `processElement()`.**
> A 200ms database call at 10K events/sec means 2000 seconds of blocking per second. Use async I/O or batch writes in a `SinkFunction` with checkpoint-triggered flushes.

### Checkpointing Mistakes

> **8. DON'T set checkpoint interval too low (e.g., 1 second) without understanding the cost.**
> Each checkpoint snapshots ALL state. With large RocksDB state, sub-second checkpoints can't complete before the next one starts — permanent backpressure.

> **9. DON'T ignore `CheckpointException` in logs.**
> Frequent checkpoint failures mean your job has ZERO fault tolerance while they're failing. Alert on `number_of_failed_checkpoints` in metrics.

> **10. DON'T rely on checkpoints for "application saves" (use savepoints).**
> Checkpoints are owned by Flink and may be cleaned up. For planned stops/upgrades, trigger a savepoint explicitly: `flink savepoint <jobId>`.

> **11. DON'T change the state schema and restore from a savepoint without a migration strategy.**
> Adding/removing fields from a POJO in state, or changing types, breaks deserialization. Use Flink's state schema evolution (Avro or POJO evolution rules) or plan a state migration.

### Windowing & Time

> **12. DON'T use processing-time windows for business logic.**
> On failure recovery, Flink replays records at max speed — processing time windows will produce completely different (wrong) results compared to the original run.

> **13. DON'T set watermark delay to zero unless your data is perfectly ordered.**
> Zero tolerance means ANY out-of-order event is late and gets dropped. Start with `forBoundedOutOfOrderness(Duration.ofSeconds(N))` based on your actual data skew.

> **14. DON'T forget that watermarks flow per-partition.**
> A single stalled Kafka partition (no data) holds back the entire watermark. This silently freezes all windows. Use `.withIdleness()`.

> **15. DON'T use `allowedLateness()` as a fix for bad watermarks.**
> It causes windows to fire multiple times (incremental results), which most downstream systems can't handle. Fix your watermark strategy instead.

### Topology & Deployment

> **16. DON'T change operator UIDs between deployments if you want savepoint compatibility.**
> UIDs are the mapping between savepoint state and your new code. Change the UID = lose that operator's state.

> **17. DON'T use `rebalance()` when you need `keyBy()`.**
> Rebalance is round-robin for load distribution. It breaks any downstream keyed operation because the same key goes to different subtasks. Use `keyBy()` to co-locate state with the right records.

> **18. DON'T chain everything into one massive pipeline.**
> Split logically independent processing into separate jobs. A single 50-operator pipeline means one bad operator's backpressure stalls everything, and you can't independently scale or restart parts.

> **19. DON'T change `maxParallelism` after the first deployment.**
> It determines key group assignment. Changing it makes existing savepoints non-restorable for keyed state. Set it once, set it generously (e.g., 128 or 256), and leave it.

### Performance

> **20. DON'T use `ValueState` or `MapState` for counting — use `ReducingState` or `AggregatingState`.**
> They pre-aggregate on the write path, avoiding a read-modify-write cycle. With RocksDB this means one `put()` instead of `get()` + `put()`.

> **21. DON'T `collect()` or `print()` in production.**
> These create single-threaded sinks that bottleneck the entire pipeline. They're for debugging only.

> **22. DON'T use Java `Serializable` for state types.**
> Java serialization is slow, produces huge payloads, and breaks on any class change. Use POJOs following Flink's rules, Avro, or register Kryo serializers.

> **23. DON'T ignore backpressure.**
> If the Flink Web UI shows backpressure (red), your job is running at the speed of the slowest operator. Throwing more parallelism at the source doesn't help — find and fix the bottleneck.

### Operational

> **24. DON'T run with `HashMapStateBackend` in production for large state.**
> It keeps ALL state on the JVM heap. At a few GB of state, GC pauses cause checkpoint timeouts and cascading failures. Use `EmbeddedRocksDBStateBackend` for anything beyond trivial state.

> **25. DON'T deploy without metrics and alerting.**
> At minimum monitor: `lastCheckpointDuration`, `numberOfFailedCheckpoints`, `records-lag-max` (Kafka), `numRecordsOutPerSecond`, and backpressure metrics. A Flink job that silently stops processing looks healthy until someone checks the output topic.

---

## Key Takeaway

Flink is not a "deploy and forget" framework like a REST API behind a load balancer. Every operator is a long-running stateful process. The `.uid()`, checkpointing config, state backend choice, and watermark strategy are not optional tuning — they are **correctness requirements**. Skip any of them and your job works perfectly until the first failure, rescale, or upgrade — then you lose data.

---

## Quick Reference: StreamTask Lifecycle (What Happens Under the Hood)

Understanding the runtime helps debug production issues:

```
restore()
  ├── Create OperatorChain (all operators chained in one thread)
  ├── init() (task-specific setup)
  ├── restoreStateAndGates()
  │   ├── Read channel output state
  │   ├── Initialize operator state (calls your initializeState())
  │   └── Read channel input state (async)
  └── isRunning = true

invoke()
  ├── scheduleBufferDebloater()
  ├── runMailboxLoop()          ← main processing loop
  │   └── processInput()        ← calls your processElement() repeatedly
  │       ├── MORE_AVAILABLE    → continue
  │       ├── NOTHING_AVAILABLE → wait
  │       ├── END_OF_DATA       → emit MAX_WATERMARK, finish operators
  │       └── END_OF_INPUT      → suspend mailbox
  └── afterInvoke()
      ├── Drain mailbox
      ├── Quiesce timers
      ├── Flush outputs
      └── Close operators

Checkpoint (triggered by barrier or coordinator)
  ├── triggerCheckpointAsync() → enqueued to mailbox
  ├── performCheckpoint()
  │   └── subtaskCheckpointCoordinator.checkpointState()
  │       └── snapshots ALL operator state (calls your snapshotState())
  └── notifyCheckpointComplete() → async notification

cleanUp(throwable)
  ├── cancelTask() if error
  └── Close all resources
```

## Quick Reference: State Types

| State Type | Scope | Access Pattern | When to Use |
|-----------|-------|---------------|-------------|
| `ValueState<T>` | Per key | Single value read/write | Simple per-key tracking |
| `ListState<T>` | Per key | Append + iterate | Event buffering, collecting |
| `MapState<K,V>` | Per key | Per-entry read/write | Lookups within a key's data |
| `ReducingState<T>` | Per key | Add (pre-aggregated) | Counts, sums, min/max |
| `AggregatingState<IN,OUT>` | Per key | Add (pre-aggregated) | Complex aggregations |
| Operator `ListState` | Per operator | Full list per subtask | Non-keyed state, offsets |
| `BroadcastState<K,V>` | Global read | Key-value broadcast | Rules, config, patterns |
