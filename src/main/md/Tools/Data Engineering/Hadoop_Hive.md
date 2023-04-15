## Where does Hive stores the data

1. metadata in rdbms
   1. Metastore

## Hadoop1 vs Hadoop2

1. NameNode - (Common for 1 & 2)
2. SecondaryNameNode - (2)
3. DataNode - (Common for 1 & 2)
4. Resource Manager - (in Yarn)
   1. Job Tracker - (in V1)
5. Node Manager - (in Yarn)
   1. Task Tracker - in (V1)

## When does HDFS data is not available
1. All the 3 replicated nodes are missing
2. Client failed while writing the initial file
   1. Once name places entry, client will upload the data to one of the data node
   2. Data node would use replication pipeline to replicate the data.

## Types of node
1. Master node
2. Secondary-node (Checkpoint node)

## Hadoop-1 vs rest
1. in hadoop-1 we bring process into data-node
   1. Now it is not the case anymore
2. Spark-submit
   1. It is compiler, it implements A

## Hdfs High Availability

1. There are multiple name-node, one of them is active node, others are passive node
2. Secondary node is not what it means
3. All the data node send heart beat to all the name-nodes
4. When Active name node dies, zk would elect next active-name-node
5. All the node would always write data to "Journal Node"
   1. Journal node can have multiple replicated copy
   2. High availability is maintained by multiple Journal node copy

## HDFS - Client Read
1. Name node would give all the replication details to client
   1. If one of the copy is not available, client will automatically try to get data from replication
   2. Since NameNode already sent replication block details to client

## Hive commands

1. show databases
2. show tables
3. use database catalog;
4. use temp;
5. .hql file can contain all the script, and could be executed
6. create table emp(sno int, user_name string)
7. load data inpath '/usr_data.txt' into table emp;
   1. when we have 'load data' without 'local', it is loading from hdfs
8. insert into emp(123, 'name);
   1. Hive itself stores the data

## Where does Hive stores the data
1. /user/hive/warehouse/test_db/
2. /user/hive/warehouse/test_db/table/
3. /user/hive/warehouse/test_db/user_data
   1. (Above paths are HDFS path)

## Does Map_Reduce run for every query?
1. No MAP Reduce for load and vanilla select statement
2. When Map-Reduce run for Hive Query
   1. insert into (via spark as well) would trigger the map-reduce
   2. select join 
   3. select aggregation would trigger map-reduce job
   4. Any analytical query would map-reduce

## If we copy the file directly into hive store without using load data, would hive recognize the data
1. Yes, it would recognize the data

## How to load local file to hdfs
1. hadoop fs -put /home/test/usr_data/txt /usr_data.txt
   1. src:local_name and target:hdfs_name
2. hadoop fs ls /

## Hive supports UDF
1. we can create UDF for hive

## How to create bucket for table

```sql
CREATE TABLE zipcodes(
    RecordNumber int,
    Country string,
    City string,
    Zipcode int
)
    PARTITIONED BY(state string)
    CLUSTERED BY (Zipcode) INTO 32 BUCKETS
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ',';
```


## What is the difference between internal and external table

1. When internal table is dropped, both metadata and data is stored
   1. ACID is only supported in internal table 
2. When external table is dropped, only metadata is dropped
   1. External table might be used by PIG, spark
   2. We can recreate the table and query the same data again