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
