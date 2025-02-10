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


## AWS Commands
```
SET rds_aurora.limitless_create_table_mode='sharded';
SET rds_aurora.limitless_create_table_shard_key='{"cust_id"}';
SET rds_aurora.limitless_create_table_mode='reference';
SET rds_aurora.limitless_create_table_mode='standard';
SET rds_aurora.limitless_create_table_collocate_with='customer';
```
