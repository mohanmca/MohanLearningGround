## How to swap columns instead of joining two tables?

```sql
with unique_friends as (
    select user1_id as user_id from Friendship where user2_id = 1
    union
    select user2_id as user_id from Friendship where user1_id = 1
)
```
```sql
with unique_friends as (
    select  case when user1_id=1 then user2_id  when user2_id=1 then user1_id end as user_id  from Friendship
)
```


## How to calculate running total for last x transaction

1. Cross Join
2. Filter diff of transaction count difference to 4 or less
3. [Calculate Running Totals Using SQL Server CROSS JOINs](https://www.mssqltips.com/sqlservertip/1686/calculate-running-totals-using-sql-server-cross-joins/)