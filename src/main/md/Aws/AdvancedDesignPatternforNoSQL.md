## RDBMS
  * Invented in 70's
  * Invented when storage was expensive. 250K for 4 MB HDD
  * Should know how join works on RDBMS, it is basic
  * Features
    * Optimized for storage, but CPU has to do lot of work for denormalized form. Join table and denormalize
    * Normalized/relational
    * Ad hoc queries
    * Scale vertically
    * Good for OLAP
  * ACID transactions are required since we store the related data in different table, We need to update all the relevant table
  * Design table and query can be anything
  * 1 application service = N table

## NOSQL
  * Technology adoption and hype curve
  * If you learn how to model data correctly for noSql, you will get best output
  * Features
    * Optimized for compute
    * DeNormalized/hierarchical
    * Instantiated Views
    * Scale horizontally
    * Built for OLTP at scale
  * Every real world application has some soft of relation data model in nature
  * ACID is not required since we can store/update all relvant data in same place/table
  * Avoid relational design patterns, use one table
  * Need to know the access pattern upfront
  * Query should be known upfront, and table should be designed based on queries
  * More we tune to the access pattern, more tightly coupled to application
  * Not much flexible, but very efficient to use at scale
  * 1 application service = 1 table
  * When the data gets loaded, get even and pre-compute for future queries upfront
  * 

## MongoDB aggregation framework is nice, but won't scale


## Amazon DynamoDB
* Fully managed NoSql
* Document or Key-Value
  * Wide column key value store
  * Every document should have one key - that is the partition key
  * optional sort key - execute complex range queries - can order the items
  * customer id - parition key, order date - sort key
* Support document attribute type
* 4 million transaction for second
* Fast and consistent
* Fine grain access
  * Tables
  * Documents in Tables
  * Attributes in Documents
* Eventual consistent read vs Strong consisten read
* Global secondary index vs Local secondary index  
  * Local secondary index  (Resort the data wihin the paritions - should be same as global partion key as table)
  * Global secondary index - Could be new attribute for other aggregation. Example: Warehouse
* Partition Key - Large number of distinct values, Items are uniformly requested and randomly distribtued (Bad: Gender, Status. Good: CustomerId, DeviceId)


# Scenario design
* 
Lack of CompositeKey
```SQL
Select * from Game where player='Bob' order by Date desc Filter on Status='PENDING'
```
CompositeKey works better for large data 
```SQL
Select * from Game where player='Bob' Filter on Status begins with 'PENDING'
```

Transaction
```SQL
COPY Item.V0 -> Item1.v3 if Item.v3 = null
Update Item1.v3 SET Attr1 +=1
Update Item1.v3 SET Attr2 =..
Update Item1.v3 SET Attr3 =..
COPY Item.V3 -> Item1.v0 SET CurVer = 3
```

# Reference
* (AWS re:Invent 2018: Amazon DynamoDB Deep Dive: Advanced Design Patterns for DynamoDB (DAT401))[https://www.youtube.com/watch?time_continue=33&v=HaEPXoXVf2k]
* (Rick Houlihan - Principal Technologist)[https://www.quora.com/profile/Rick-Houlihan]
* [amazon-dynamodb-deep-dive-advanced-design-patterns-for-dynamodb](https://www.slideshare.net/AmazonWebServices/amazon-dynamodb-deep-dive-advanced-design-patterns-for-dynamodb-dat401-aws-reinvent-2018pdf)
* [Best Practices NOSql](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/best-practices.html)