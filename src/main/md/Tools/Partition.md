## Partition solves many scale issue, but..

### -- This query will scan ALL partitions 
1. SELECT * FROM table_partitioned  WHERE account_id NOT IN (123, 345, ...);
2. why would scan if partitioned by accountId?
