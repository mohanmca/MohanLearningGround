## Trivia
* In Flink's state backend, what happens to MapState entries when a checkpoint is triggered — are they snapshotted synchronously or asynchronously?
* Trivia answer from last time (in case you were pondering): with RocksDB state backend, MapState snapshots are taken asynchronously — Flink takes a snapshot of the RocksDB native memory/SST files without blocking the processing pipeline. The embedded heap backend does
  a synchronous deep copy.
