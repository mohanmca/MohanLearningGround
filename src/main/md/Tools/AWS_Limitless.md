## [How to scale database](https://www.youtube.com/watch?v=a9FfjuVJ9d8)
1. Sharding
2. Tradionally application talks to one databse or one partition
3. Problems due to sharding
   1. How to query data across multiple table if we shard
      1. We have to write application to join the data
   1. Consistency across multiple table lost due to  transaction inability across multiple transaction

## AWS - Limitless
1. Scaling + Manged + Shared
2. Uses distributed architecture, but gives interface of single database for application
3. We can scale workflows for multiple million transactions per second due to multiple server

## How does it work?
1. Shared + Reference
2. Customer-id as a column for sharding
3. If we chosse customer-id as sharding key, same customer-id data in same shard
4. We can choose multiple table that has same shard-key (customer and order table)
5. We can keep some simple reference data across all the shards

## Shard Group
1. There is Distributed Transaction Routes
2. Data Access Shards
3. There is primary writer for each shard
4. There is writer and reader endpoint

## Distributed transaction routers
1. Serve all application traffic
2. Scale vertically and horizontally based on load
3. Knows schema and key range placement
4. Assign time for transaction snapshot and drive distributed commits
5. Perform initial planning of query and aggregate results from multi-shard queries


## Data access shards
1. Own portion of sharded table key space and have full copies of reference tables
2. Scale vertically and split based on load
3. Perform local planning and execution of query fragments
4. Execute local transaction logic
5. Backed by Aurora distributed storage

```
aws rds create-db-cluster \
  --db-cluster-identifier my-limitless-cluster \
  --engine aurora-postgresql \
  --engine-version 16.6-limitless \
  --storage-type aurora-iopt1 \
  --cluster-scalability-type limitless \
  --master-username myuser \
  --master-user-password mypassword \
  --enable-performance-insights \
  --performance-insights-retention-period 31 \
  --monitoring-interval 5 \
  --monitoring-role-arn arn:aws:iam::123456789012:role/EMrole \
  --enable-cloudwatch-logs-exports postgresql
```

```
aws rds create-db-shard-group \
  --db-shard-group-identifier my-db-shard-group \
  --db-cluster-identifier my-limitless-cluster \
  --max-acu 1000 \
  --min-acu 16 \
  --compute-redundancy 1 \
  --publicly-accessible
```

## AWS Commands
```
SET rds_aurora.limitless_create_table_mode='sharded';
SET rds_aurora.limitless_create_table_shard_key='{"cust_id"}';
SET rds_aurora.limitless_create_table_mode='reference';
SET rds_aurora.limitless_create_table_mode='standard';
SET rds_aurora.limitless_create_table_collocate_with='customer';
```
