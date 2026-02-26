## Trivia
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

## Terraform
* To create AWS Application via terraform we have to use resource "aws_kinesisanalyticsv2_application" "this"
