## How to list all the scheama?
```select * from information_schema.schemata;```

## How to show each record in columnar multi-row format?
```
\x on
Expanded display is on.
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

## Generate mdanki
mdanki postgresql.md postgresql.apkg --deck "Mohan::DeepWork::postgresql"
