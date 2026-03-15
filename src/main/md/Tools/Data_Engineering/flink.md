## Trivia
* [Apache Flink Playground](https://github.com/mohanmca/FlinkPlayground/tree/master/src/main/md/performance.md)
* What is savepoint restore failures cause?
  * Forgetting .uid() is the #1 cause of savepoint restore failures. Once you have state saved without UIDs, you cannot retroactively add them without losing state.
* In Flink's state backend, what happens to MapState entries when a checkpoint is triggered — are they snapshotted synchronously or asynchronously?
  * Trivia answer from last time (in case you were pondering): with RocksDB state backend, MapState snapshots are taken asynchronously — Flink takes a snapshot of the RocksDB native memory/SST files without blocking the processing pipeline. The embedded heap backend does
  a synchronous deep copy.
* What's the difference between ValueState and ListState when it comes to RocksDB serialization overhead? (Hint: think about what happens on every .value() call vs .get() call.)
  * Trivia answer from before: ValueState.value() on RocksDB deserializes the entire byte array on every call. ListState.get() returns a lazy iterator that deserializes elements one at a time. So for a single counter, ValueState is fine, but for large collections
  ListState avoids the full-deserialization penalty.
* Flink trivia while you review: In Flink's KafkaSink, when checkpointing is enabled with EXACTLY_ONCE, the sink uses Kafka's two-phase commit protocol. But here's the catch — what happens to in-flight Kafka transactions if a TaskManager crashes between the pre-commit
  and the commit phase of a checkpoint? How does Flink recover those "dangling" transactions on restart?
  * Answer to the trivia: When a TaskManager crashes between pre-commit and commit of a Kafka transaction, Flink handles this on restart by recovering the transactional.id from the checkpoint metadata. The new TaskManager instance re-opens the transaction with the same
  transactional.id and either commits or aborts it based on the checkpoint state. Kafka's transaction timeout (transaction.timeout.ms) acts as a safety net — if Flink doesn't recover in time, the broker auto-aborts the dangling transaction, and consumers with
  read_committed isolation never see those uncommitted records.
* In Flink's exactly-once Kafka sink, what's the role of the transactional.id prefix — and what happens if two job instances accidentally share the same prefix?
* In Flink's ProcessFunction, what is the difference between processElement() output via Collector<O> vs side output via OutputTag<T>? When would you choose one over the other?
* In Apache Flink's checkpointing mechanism, what is the difference between "exactly-once" and "at-least-once" checkpoint semantics in terms of barrier alignment?
  * Now — about that Flink trivia: barrier alignment in exactly-once mode means each operator waits for barriers from all input channels before checkpointing, which can cause backpressure. At-least-once skips alignment — operators process
records from faster channels while waiting, so records between barriers may be replayed on recovery. What's your take?
*  In Flink's event time processing, what happens when a watermark arrives at an operator with multiple input channels? (a) The operator advances its watermark to the max of all input watermarks, (b) The operator advances its watermark to the
  min of all input watermarks, (c) Each input channel is processed independently, (d) The operator drops events from the slower channel.
*   Trivia: In Flink's KeyedStream, if you apply a reduce() followed by a window(), vs a window() followed by a reduce() — which one is a rolling aggregation and which is a windowed aggregation? What's the state management difference?

## Flink Savepoint vs checkpoints

* Savepoints for planned, manual operations where you need to preserve the application's state while making changes. While checkpoints are for automatic failure recovery, savepoints are for user-driven management.
* Create savepoint ```./bin/flink stop -p [targetDirectory] <jobID>```
* Resume savepoint ```./bin/flink run -d savepointPath```
* Deletes a savepoint and cleans up its associated metadata and files from storage. - ```./bin/flink savepoint -d <savepointPath>```

## What are failure strategy
* fixed-delay = retry on a steady cadence. Best for short, transient issues like a brief network glitch or a momentary downstream timeout, where a few predictable retries are enough.
* failure-rate = retry only as long as failures stay within a defined budget over time. You set something like “allow at most 3 restarts in 10 minutes”; if the job exceeds that, Flink stops restarting it. This is useful when occasional failures are acceptable, but repeated flapping should fail fast instead of hiding a deeper issue.
  * Example: if Kafka or a downstream service blips once or twice during a deployment, the job recovers; if it keeps crashing every couple of minutes, Flink stops after the budget is exhausted and surfaces the instability.
*  exponential-delay = retry with progressively longer waits after each failure. Best when dependencies may need time to recover, and you want to avoid hammering the system with constant restarts during outages or overload.
* no-restart = fail immediately and surface the problem. Best when the failure is likely deterministic, such as bad code, config, schema mismatch, or invalid input.

## Terraform
* To create AWS Application via terraform we have to use resource "aws_kinesisanalyticsv2_application" "this"


## Kafka
* The source consumer can read from the topic but is not authorized to use the consumer group "group-id" on the MSK cluster. This is because the env property sets KAFKA_SOURCE_GROUP_ID="group-id", and the MSK IAM. policy for the flink role likely doesn't include kafka-cluster:AlterGroup / kafka-cluster:DescribeGroup permissions for that group name.
* The "Processing event" logs may appear from the initial burst before the group authorization kicked in — the consumer fetched records but will fail when trying to commit offsets.
