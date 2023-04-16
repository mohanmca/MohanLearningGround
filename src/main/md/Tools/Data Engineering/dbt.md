## Why DBT?
1. Imagine every transformation pipeline was built using GUI (where code is not accessible)
   1. Informatica
   2. SSIS
2. We can't manage version control dbt pipeline
3. It is 'T' in the ELT

## Basics of DB
1. Models
2. Sources
3. Documentation
4. Test
5. Deployment

## ETL and ELT
* ELT - Super computer attached to DWH (analogy)
* ELT (extract load transform) is a more recent process of creating new database objects by first extracting and loading raw data into a data warehouse and then transforming that data directly in the warehouse.
* The new ELT process is made possible by the introduction of cloud-based data warehouse technologies.
* ETL (extract transform load) is the process of creating new database objects by extracting data from multiple data sources, transforming it on a local or third party machine, and loading the transformed data into a data warehouse.

## [What are 3 different roles in Data-Engineering](https://www.getdbt.com/what-is-analytics-engineering/)
1. Data Engineer
2. Analytics Engineers
3. Data Analysts

## Analytics Engineering
* Analytics engineers focus on the transformation of raw data into transformed data that is ready for analysis. This new role on the data team changes the responsibilities of data engineers and data analysts.
* Data engineers can focus on larger data architecture and the EL in ELT.
* Data analysts can focus on insight and dashboard work using the transformed data.
   * Note: At a small company, a data team of one may own all three of these roles and responsibilities. As your team grows, the lines between these roles will remain blurry.


## What are all famous data platform
1. Snowflakes
2. BigQuery
3. RedShift
4. Databricks

## Where does DBT fits in?
1. It is ELT tool and closely work with data-platform
   1. Models (the data-platform)
   2. Transforms (the data-platform)
   3. Test (the data-platform)
   4. Document (the data-platform)

## DBT connection for Snowflake

1. [Steps](https://www.youtube.com/watch?v=kbCkwhySV_I)
2. Actual steps
   1. Login to sandbox account
      1. [Loading Data into snowflake](https://docs.getdbt.com/docs/quickstarts/dbt-cloud/snowflake#loading-data)
   2. Ensure right role, or create new role (should be "AccountAdmin")
   3. Data - Where will the raw data be loaded & where will the transformed data will be loaded
      1. Fivetran and Stitch is famous tools to load raw data into Snowflake
      2. Ensure there is dedicated db for DBT (within snowflake)
   4. Create role for dbt to access dataplatform (transformer)
      1. create role transformer;
   ```
      grant IMPORTED PRIVILEGES on database snowflake_sample_data to role transformer;
      grant usage on schema snowflake_sample_data.tpch_sf10 to role transformer;
      grant select on all tables in schema snowflake_sample_data.tpch_sf10 to role transformer;
   ```
   ```
      grant usage on database analytics to role transformer;
      grant reference_usage on database analytics to role transformer;
      grant modify on database analytics to role transformer;
      grant monitor on database analytics to role transformer;
      grant create schema on database analytics to role transformer;
   ```
   ```
      grant operate on warehouse transforming to role transformer;
      grant usage on warehouse transforming to role transformer;
   ```
    ```      
      create user db_transform_user email='email@gmail.com' password='passowrd' default_role = transformer;     grant role transformer to user db_transform_user; 
    ```
   5. [Video: Configure Snowflake for dbt Cloud](https://youtu.be/kbCkwhySV_I)
   6. [Video: Loading snowflake data](https://docs.getdbt.com/docs/quickstarts/dbt-cloud/snowflake#loading-data)
   7. which one of the following should always be unique for each dbt developer when working in development?
      1. Target Schema
   8. Snowflake details
      1. dbtsnowflake - account
      2. dbtsnowflake_db - DB
      3. dbtsnowflake_wh - wh
      4. Develpment Credential
         1. dbtcloud_usr/dbtcloud_pwd/dbt_mnarayanaswamy
         2. dbt will build your models into a schema with this name. 
         3. This schema name should be unique to your personal development environment and should not be shared by other members of your team.
      5. PC_DBT_DB/PC_DBT_WH/PC_DBT_USER
      6. dbt_account (account name in dbt)
      7. Username: MOHANMCA/Dedicated Login URL: https://jhyyodn-yc35388.snowflakecomputing.com



## Models
1. How to modularize
2. Usage of refFunction
3. Modelling Paradigm
4. Naming convention
5. SubFolder


## Modelling
1. Building tables and views
2. Just select statement in dbt project
3. Each of these represents one modular piece of logic
   1. Each Model: raw-data => final business logic
   2. How the model has to be built
      1. Either at the top of the model
      2. Or at the separate yaml file
      3. Model has to be built
      4. DBT build would convert model into DML or DDL
4. dbt run
   1. would create view/table (default table) directly from dbt cloud
   2. We don't need to login to snowflake

## Config block

```{{
      config (materialized='table')
   }}
```

## Modularity
1. How different parts of cars are produced and finally assembled, we can create different staging and can use it in final assembling model
2. stg_customer
3. stg_orders
4. Each model is reusable for other downstream models

## Commands
1. ```dbt run```
2. ```dbt run --select model_name```
3. ```dbt build docs```
4. ```dbt run --full-refresh```
5. ```dbt test```
6. ```dbt test --select stg_customers```
7. ```dbt source freshnes```
8. ```dbt test --select source:jaffle_shop```
9. ```dbt --select +dim_customers```
   1. +everything in upstream of
10. ```dbt docs generate```


## Config block

```{{
      config (materialized='table')
   }}
```

## Traditional Modelling
1. Star Schema
2. Kimball
3. Data Vault
* Traditional modelling were designed to reduce storage cost
  * Optimized for reducing data redundancy
* Denormalized viewpoint modelling
  * Storage is cheap
  * Agile analytics
  * Ad-hoc analytics

## Naming conventions
1. Sources - are models
   1. The raw data that has already been loaded
2. Staging - models
   1. 1-1 with underlying source tables
   2. Rename column, quick conversion of one or two columns
3. Intermediate
   1. Models between staging and final models
   2. Never refer to source tabls, but refer the staging models
4. Fact
   1. Events, clicks, votes, orders
   2. Things that already occurred
5. Dimension
   1. People, place, or thing
   2. Users, companies
   3. Products, customers
   4. (These are slowly changing models)
6. Which of the following is a benefit of using subdirectories in your models directory?
   1. Subdirectories allow you to configure materializations at the folder level for a collection of models


## Sources (raw-tables)
1. Make updates in one place
   1. ELT pipeline can refer some name, that is configured in source
   2. Even if source name is changed, we don't need to change everywhere
2. Visualize raw tables in your lineage
3. When loading process changes, we can update dbt project in one place
4. Sources can be configured in .yml file
   1. {{ source('stripe', 'payments') }} -- could refer the yaml for the actual source
      1. .yml may point to raw.stripe.payments


## Testing

1. Testing is used in software engineering to make sure that the code does what we expect it to.
2. In Analytics Engineering, testing allows us to make sure that the SQL transformations we write produce a model that meets our assertions.
3. In dbt, tests are written as select statements. These select statements are run against your materialized models to ensure they meet your assertions.
4. Assertion about the data
   1. Example, how often in excel creating new columns  - is_valid is used to find culprit record
5. Test runs against the **model**
6. Test can run during **development**
7. Everyday test can be executed and keep check with evolving data model
8. Kinds of tests
   1. Singular
   2. Generic tests
      1. Unique
      2. not_null
      3. accepted_values
      4. relationships
9. Tests are compiled and SQL is executed
   1. ```sql
      select 
           count(*) as failures, 
           count(*) != 0 as should_warn, 
           count(*) as should_error from (
         select customer_id from 
                analytics.dbt_jholtzman.stg_customers where customer_id is null
         )
   ```
10. Singular test
    1. Custom sql scripts as test-case (for a model)
    2. Any sql can be fired as part of test-case
    3. We want to ensure specific model and specific column has to be asserted


## Documentation
1. What is this data mean?
2. How it is converted/calculated?
3. Documents are stale causes many issues, dbt clubs code and documentation
4. Lineage DAG is created

## Deployment
1. There is dedicated production branch 
2. Dedicated production schema (dbt_production)
3. Run any dbt command on a schedule

## Faq
1. How to find if table or view is the output of compilation?
   1. dbt build log should contain it

```java
Sending event: {'category': 'dbt', 'action': 'resource_counts', 'label': '388c2aca-5818-4c29-a1ab-f02ba913f252', 'context': [<snowplow_tracker.self_describing_json.SelfDescribingJson object at 0x7f85ca4fa100>]}
Found 3 models, 5 tests, 0 snapshots, 0 analyses, 306 macros, 0 operations, 0 seed files, 0 sources, 0 exposures, 0 metrics
Acquiring new snowflake connection 'rpc.jaffle_shop.request'
Began compiling node rpc.jaffle_shop.request
Timing info for rpc.jaffle_shop.request (compile): 2023-04-15 23:20:33.847775 => 2023-04-15 23:20:33.850192
Began executing node rpc.jaffle_shop.request
Using snowflake connection "rpc.jaffle_shop.request"
On rpc.jaffle_shop.request: with customers as (
Opening a new connection, currently in state init
Snowflake adapter: Snowflake query id: 01aba738-3200-b55d-0000-0003f5a66069
Snowflake adapter: Snowflake error: 002003 (42S02): SQL compilation error:
Timing info for rpc.jaffle_shop.request (execute): 2023-04-15 23:20:33.850630 => 2023-04-15 23:20:35.338378
On rpc.jaffle_shop.request: Close
Got an exception: Database Error
  002003 (42S02): SQL compilation error:
  Object 'ANALYTICS.DBT_MNARAYANASWAMY.STG_CUSTOMERS' does not exist or not authorized.
Got exception RPCException(10003, Database Error, {'type': 'DbtDatabaseError', 'message': "Database Error in rpc request (from remote system.sql)\n  002003 (42S02): SQL compilation error:\n  Object 'ANALYTICS.DBT_MNARAYANASWAMY.STG_CUSTOMERS' does not exist or not authorized.", 'raw_code': "with customers as (\n select * from {{ ref('stg_customers') }}\n),\norders as (\n    select * from {{ ref('stg_orders') }}\n),\ncustomer_orders as (\n    select\n        customer_id,\n        min(order_date) as first_order_date,\n        max(order_date) as most_recent_order_date,\n        count(order_id) as number_of_orders\n    from orders group by 1\n),\nfinal as (\n    select\n        customers.customer_id,\n        customers.first_name,\n        customers.last_name,\n        customer_orders.first_order_date,\n        customer_orders.most_recent_order_date,\n        coalesce(customer_orders.number_of_orders, 0) as number_of_orders\n    from customers left join customer_orders using (customer_id)\n)\nselect * from final\nlimit 500\n/* limit added automatically by dbt cloud */", 'compiled_code': 'with customers as (\n select * from analytics.dbt_mnarayanaswamy.stg_customers\n),\norders as (\n    select * from analytics.dbt_mnarayanaswamy.stg_orders\n),\ncustomer_orders as (\n    select\n        customer_id,\n        min(order_date) as first_order_date,\n        max(order_date) as most_recent_order_date,\n        count(order_id) as number_of_orders\n    from orders group by 1\n),\nfinal as (\n    select\n        customers.customer_id,\n        customers.first_name,\n        customers.last_name,\n        customer_orders.first_order_date,\n        customer_orders.most_recent_order_date,\n        coalesce(customer_orders.number_of_orders, 0) as number_of_orders\n    from customers left join customer_orders using (customer_id)\n)\nselect * from final\nlimit 500\n/* limit added automatically by dbt cloud */', 'tags': None}, None)
```

## What is DBT - Reference?
1. [The unreasonable effectiveness of dbt](https://dbt.picturatechnica.com/)
2. [How to get started with dbt](https://www.blef.fr/get-started-dbt/)
3. [DBT - AI Engineering](https://docs.google.com/presentation/d/1MKjgNU_2hpq0XalSJAE8FmDATfxfJtu6jZiC8ZrekPc/edit?ref=blef.fr#slide=id.g13de222be64_0_0)
4. [DBT Fundamentals](https://import.cdn.thinkific.com/342803/courses/1539942/epg1Qi2USSiXGS6JZJkG_dbt%20Fundamentals.png)

