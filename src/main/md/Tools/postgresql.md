## How to list all the scheama?
```select * from information_schema.schemata;```
```SELECT datname FROM pg_database WHERE datistemplate = false;```

## How to show each record in columnar multi-row format?
```
\x on
Expanded display is on.
```

## From CLI
```
time psql "service=exchange_activity" -X -v ON_ERROR_STOP=1 -c "SELECT count(*) FROM order_details;"
```


## What are PSQL?

```psql
\dt list all tables
\i [filename]: Executes SQL commands from a file. - '\i script.sql'
\df [function_name]: Lists all functions, or details of a specific function. Usage: \df my_function
\du: Lists all roles (users) and their attributes.
\dv: Lists all views in the current database.
\di: Lists all indexes in the current database.
\! [shell_command]
```

## How to list all the scheama?
```select * from information_schema.columns where column_name like '%lev%'```

## How to list all the tables?
```select * from information_schema.columns where table_name like '%acc%'```

## How to convert enum into text in PSQL?
```
 select currency::text like 'USD' from currency;
```

## How to export sql data into csv?
```
 \copy (SELECT id  FROM order_table  WHERE key >=99 and  currency::text like 'USD'  and  num_key<= 479414144 and price > 2) to '/tmp/orders.csv' with csv header
```


## What are all performance monitor knobs in Postgres
1. System Level
   1. CPU, Network, Memory, Read/Write Throughput, Read/Write  IOPS, Read/Write Disk Latency
   2. Burst Bucket IO Balance, Used Disk Storage, Outstanding DISK IO Requests
2. Objects
   1. Database Sizes, Table Count by DB, Table Sizes
3. Activity
   1. Query throughput by query, Average duration by query
   2. Commits by db, Rollbacks by db, Connections by db
   3. Rows Modified by Operation Type, Max Transaction by db, Open Transaction by db
4. Locks (by table, type and mode)
5. Replication
   1. WAL Write by Client
   2. WAL Flush Lag by Client
   3. WAL Replay Lag by Client
6. Background Writer
   1. Scheduled checkpoints Performed
   2. Requested Checkpoints Performed
   3. Max Checkpoint Sync Time
   4. Buffers Written in Checkpoint
   5. Buffers Allocated
   6. Buffers Written by Background Writer
7. Vaccum
   1. Lives Rows per Table
   2. Dead Rows per Table
   3. Last Autovacuum Age by Table
   4. Last Autoanalyze Age by Table
   5. Heap Blocks Scanned by Table
8. Blocks
   1. Total Shared Block Hits by Query
   2. Total Shared Block Read by Query
   3. Total Shared Block Dirtied by Query
   4. Total Shared Block Hits per Query Execution
   5. Total Shared Block Read per Query Execution
   6. Total Shared Block Dirtied per Query Execution
9. Indexes
   1. Index Blocks Hit per Table
   2. Index Blocks Read per Table
   3. Index Scans Read per Table & Index
   4. Index Rows Read per Table & Index
   5. Index Rows Fetched per Table & Index


## Generate mdanki
mdanki postgresql.md postgresql.apkg --deck "Mohan::DeepWork::postgresql"
