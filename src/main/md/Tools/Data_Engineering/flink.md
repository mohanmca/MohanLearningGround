## Trivia
* In Flink's state backend, what happens to MapState entries when a checkpoint is triggered — are they snapshotted synchronously or asynchronously?
  * Trivia answer from last time (in case you were pondering): with RocksDB state backend, MapState snapshots are taken asynchronously — Flink takes a snapshot of the RocksDB native memory/SST files without blocking the processing pipeline. The embedded heap backend does
  a synchronous deep copy.
* What's the difference between ValueState and ListState when it comes to RocksDB serialization overhead? (Hint: think about what happens on every .value() call vs .get() call.)
  * Trivia answer from before: ValueState.value() on RocksDB deserializes the entire byte array on every call. ListState.get() returns a lazy iterator that deserializes elements one at a time. So for a single counter, ValueState is fine, but for large collections
  ListState avoids the full-deserialization penalty.
