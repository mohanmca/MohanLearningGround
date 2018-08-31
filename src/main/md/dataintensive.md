* Read-after-write consistency - Users should always see data that they submitted themselves.
* Monotonic reads - After users have seen the data at one point in time, they shouldn’t later see the data from some earlier point in time.
* Consistent prefix reads - Users should see the data in a state that makes causal sense: for example, seeing a question and its reply in the correct order.
* Read Repair
* r + w > n (replica)
* Hinted Handoff
* Failure reasons
  * different order at different nodes
  * due to variable network delays
  * partial failures
* two operations concurrent if they are both unaware of each other, regardless of the physical time at which they occurred  
* “Handling Write Conflicts”.
  * Last write wins
  * Happens before
* Use of a version number per replica as well as per key - is kind of vector clock with subtle difference
* The collection of version numbers from all the replicas is called a version vector
* Partition
  * Key range partitioning
  * Hash partitioning
  * Hybrid approaches are also possible, for example with a compound key: using one part of the key to identify the partition and another part for the sort order
  * Document-partitioned indexes (local indexes), where the secondary indexes are stored in the same partition as the primary key and value.
  * Term-partitioned indexes (global indexes), where the secondary indexes are partitioned separately, using the indexed values
  
* Transaction
* Lost Updates
  * ATOMIC WRITE OPERATIONS - ORM may spoil it
  * UPDATE counters SET value = value + 1 WHERE key = 'foo';
  * EXPLICIT LOCKING
  * AUTOMATICALLY DETECTING LOST UPDATES
  * COMPARE-AND-SET
  * last write wins (LWW) conflict resolution method is prone to lost updates - Don't use them
* Write Skew and Phantoms
  * d   