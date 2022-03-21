## KX Workshop
1. ```tables[]``` - List all the tables in the database
   1. ``` `jan09`smalltrips`trips`weather ```
2. smalltrips - Equivalent of ```select few-records from smalltrips```
   1. ```select from smalltrips``` -- \* can be omitted in Q-SQL
   2. select vendor, pickup_time, fare from smalltrips
3. ```count trips```
   1. Display number of records in trips table
4. ```meta trips``` cols trips
   1. select schema of table(trips)
   2. ```cols trips```
5. Aggregation example
   1. ```select date, month, vendor, passengers, fare, tip from trips where date = min date, tip > 20 ```
      1. The first day of data is found by applying aggregation min to the date column, and selecting dates equal to it.
6. show jan09
   1. We can store result in temporary variable and show them in one step
7. Comments
   1. / - Single or double // - both would work as comment

## Which query runs faster
1. select date, month, vendor, passengers, fare, tip from trips where date = min date, tip > 20
2. select date, month, vendor, passengers, fare, tip from trips where tip > 20, date = min date
3. In timeseries database always constraint of time should be first to reduce the scope of the data
4. Filtering on the partition column value first minimizes the number of directories to read.
   1. \ts select date, month, vendor, passengers, fare, tip from trips where date = min date, tip > 20
      \ts select date, month, vendor, passengers, fare, tip from trips where tip > 20, date = min date
   2. We can measure performance by \ts

##  Calculated columns in the result by assignment in the form column_name : value

1. select duration, total: fare + tip, fare, tip from trips
2. select count i from trips where date = min date, passengers = 2
   1. A virtual column i maps to a ***record index*** in the table. 
   2. A simple aggregation can be obtained by taking the count of this virtual column.

## On the earliest/latest date, how many trips had fewer than two passengers?
1. select count i from trips where date = min date, passengers < 2
2. select count i from trips where date = first date, passengers < 2
3. select count i from trips where date = last date, passengers < 2
4. select count i from trips where date = max date, passengers < 2

## Date format
1. ```date within 2009.01.10 2009.01.31``` (YYYY.MM.DD)

## In q, assignment is denoted by : (intermediate tables/results can be store)
1. jan09:select from trips where date within 2009.01.10 2009.01.31

## Aggregation
1. jan09:select from trips where date within 2009.01.10 2009.01.31
2. select sum fare, sum tip from jan09
3. select max tip, min tip from jan09
4. select max tip, avg tip by payment_type from jan09 (multiple aggregation using same group by)

## Q lang introspection (in Q terminal)
1. key`.q
   1. ``neg`not`null`string`reciprocal`floor`ceiling`signum`mod`xbar`xlog`and`or`ea..
2. .Q.res
   1. `abs`acos`asin`atan`avg`bin`binr`cor`cos`cov`delete`dev`div`do`enlist`exec`ex..
3. .Q
4. .q

## Nested Subquery in SQL - in RDBMS
1. Nested queries are commonly required in SQL where filter criteria require aggregations in the context of some other column.
```SQL
SELECT job_id,
       Avg(salary)
FROM   employees
GROUP  BY job_id
HAVING Avg(salary) < (SELECT Max(Avg(min_salary))
                      FROM   jobs
                      WHERE  job_id IN (SELECT job_id
                                        FROM   job_history
                                        WHERE  department_id BETWEEN 50 AND 100)
                      GROUP  BY job_id);  
```

## [Nested Subquery in SQL - in RDBMS (and filter-by aggregation fby)](https://code.kx.com/q/ref/fby/)
```sql
## Get the average duration per vendor and save resulting table in a variable
resBy: select avgDuration:avg duration by vendor from jan09
select max fare from jan09 lj resBy where duration < avgDuration

## Without temporary storage, but using fby
select max fare from jan09 where duration < (avg;duration) fby vendor
```

```
show resBy: select avgDuration:avg duration by vendor from jan09
noFBy: select from (jan09 lj resBy) where duration < avgDuration
withFBy: select max fare from jan09 where duration < (avg;duration) fby vendor
noFBy=withFBy // we are using match operator to check, it won't match since additional column in two steps approach
0b --failed output -- no match output for above
(delete avgDuration from noFBy)=withFBy
1b --output matches
```


## Type casting to typestamp
`timespan$resultcolumn

```sql
select duration
   vendor, avg_duration:`timestamp$(avg;duration) fby vendor,
   cond:duration < (avg;duration) fby vendor
 from jan09 
```

## Which payment type produces the highest average tip when only trips with a fare larger than the average for each vendor is considered?
```sql
req:select avg tip by payment_type from jan09 where fare > (avg;fare) fby vendor;
select payment_type from req where tip=max tip
```

## Which vendor has the largest number of trips when only considering trips shorter than the average duration for each vendor?

```sql
durBy:select cnt:count i by vendor from jan09 where duration < (avg;duration) fby vendor;
show durBy;
select vendor from durBy where cnt = max cnt
res7b:select count i by vendor from jan09 where duration < (avg;duration) fby vendor;
show res7b;
select vendor from res7b where x = max x
```

## Other kql

1. jan09: update passengers: 5 from jan09 where passengers > 5 //Update maximum number of passengers to be 5 and save againt to jan09
2. jan09:update wAvgfare:passengers wavg fare from jan09 //Add new column named wAvgfare
3. jan09:delete from jan09 where duration=00:00:00.000 //delete few rows
4. select pickup_time, pickup_time.second, pickup_time.minute, pickup_time.hh from jan09 //from timestamp we can query sub-component


## Expression evaluation

1. evaluation is from right-to-left. 
2. ```select total:sum fare + tip by pickup_time.minute from jan09```
   1. The argument of sum is everything to its right, that is fare plus `tip. 
3. There are no priorities to remember.
4. ```select total:(sum fare) + tip by pickup_time.minute from jan09```

## Temporal expressions

1. ```select count i by 60 xbar pickup_time.minute from jan09 where date = 2009.01.10```
2. ```select max tip by 15 xbar pickup_time.minute from jan09```
   1. Show the largest tip for each 15-minute timespan during the month of January.
3. ```select max tip by 15 xbar pickup_time.minute,vendor from jan09```
   1. Show the largest tip for each 15-minute timespan, for each-vendor during the month of January.

## Reference
1. [Q reference card](https://code.kx.com/q/ref/#keywords)
2. [Kx Workshop Session 1 qSQL](https://learn.kx.com/j/notebooks/Kx%20Workshop%20Session%201%20qSQL.ipynb)
3. [Taxi data](https://www1.nyc.gov/site/tlc/about/tlc-trip-record-data.page)