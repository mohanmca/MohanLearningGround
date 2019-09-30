## New model
* IBM DB1 - IMS (Hierarchical dbms) - Released in 1968 
* IBM DB2 - 1970 - "A Relational Model of Data for Large Shared Data Banks - Dr. Edgar F. Codd"
* Pros : It works for most of the cases
  * SQL - Support
  * ACID - Transaction (A Transformation of State - Jim Gray)
    * Atomic (State A to State B - no in-between)
    * Consistency
    * Isolated - Force transactions to be serially executed. (If it doesn't require consistency and atomic, it is  possible to have isolated and parallel txns)
    * Durable - Never lost
* Cons : Won't work for massively web scale db

## How RDBMS is tuned

* Introduce Index
* Master(write), Slave (many times only used for read)
  * Introduces replication and transaction issues
  * Introduces consistency issues
* Add more CPU, RAM - Vertical scaling
* Partitioning/Sharding
* Disable journaling  

## Two-phase commit vs Compensation

* Compensation
  * Writing off the transaction if it fails, deciding to discard erroneous transactions and reconciling later. 
  * Retry failed operations later on notification. 
* In a reservation system or a stock sales ticker, these are not likely to meet your requirements. 
* For other kinds of applications, such as billing or ticketing applications, this can be acceptable.
* Starbucks Does Not Use Two-Phase Commit
  * https://www.enterpriseintegrationpatterns.com/ramblings/18_starbucks.html

## Sharding (Share nothing)

* Rather keeping all customer in one table, divide up that single customer table so that each database has only some of the records, with their order preserved? Then, when clients execute queries, they put load only on the machine that has the record they’re looking for, with no load on the other machines.
* How to shard?
  * Name-wise sharding issues like customer names that starts with "Q,J" will have less, whereas customer name starts with J, M and S may be busy
  * Shard by DOB, SSN, HASH
* Three basic strategies for determining shard structure  
  * Feature-based shard or functional segmentation
  * Key-based sharding - one-way hash on a key data element and distribute data across machines according to the hash.
  * Lookup Table

# [NoSQL](http://nosql-database.org/)

* Key-Value stores - Oracle Coherence, Redis, and MemcacheD, Amazon’s Dynamo DB, Riak, and Voldemort.
* Column stores - Cassandra, Hypertable, and Apache Hadoop’s HBase.
* Document stores -  MongoDB and CouchDB.
* Graph databases - Blazegraph, FlockDB, Neo4J, and Polyglot
* Object databases -  db4o and InterSystems Caché
* XML databases - Tamino from Software AG and eXist.


## Quotes
* If you can’t split it, you can’t scale it. "Randy Shoup, Distinguished Architect, eBay"
* [“The Case for Shared Nothing” - Michael Stonebreaker](http://db.cs.berkeley.edu/papers/hpts85-nothing.pdf)