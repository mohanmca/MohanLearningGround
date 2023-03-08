## What is the difference between RowId and RowNum

In Oracle, RowID - physical location id for row. rowid for a row never changes. Rownum changes for every sql resultset.

| ROWID					      |ROWNUM	|
|:------------------------|------:|
| AAAAECAABAAAAgiAAA		    |1 |


## How to take care for update (or delete) statement
1. Many would miss condition statement and update the entire table and wreck the data
2. Always test the update statement with SQL statement with condition and check before using update statement

## What are all the constraints are there

1. Unique
2. Non-Null
3. AutoIncrement
4. Check-Constraint (check complex expression)
5. Foreign-key (every value inserted should be in master table)

## Union vs Union All
1. What is the criteria for Union query
   1. Number of columns has to match
   2. Data-type of columns has to match
   3. Order of the columns has to match
2. The UNION and UNION ALL operator allows you to append the results of one query to another 
   1. Assuming that they have the same column count, order and data type.
3. If you use the UNION without the ALL, duplicate rows between the tables will be removed from the result.

## Intersect vs Except
1. INTERSECT operator will ensure that only rows that are identical in both result sets are returned
2. The EXCEPT operator will ensure that only rows in the first result set that aren't in the second are returned.

## SubQuery

1. ```SELECT * FROM sales_associates WHERE salary > (SELECT AVG(revenue_generated) FROM sales_associates);```
2. ```DELETE FROM Student2 WHERE ROLL_NO IN ( SELECT ROLL_NO FROM Student1 WHERE LOCATION = ’chennai’);```
3. ```UPDATE Student2 SET NAME=’geeks’ WHERE LOCATION IN ( SELECT LOCATION FROM Student1 WHERE NAME IN (‘Raju’,’Ravi’));```
4. Subquery can nest at any level, inner most is executed


## Co-related subquery

1. Inner query in Co-related query is executed for every result of external query result
2. ``` SELECT * FROM employees WHERE salary > (SELECT AVG(revenue_generated) FROM employees AS dept_employees WHERE dept_employees.department = employees.department);```


## Alter table
1. ```Alter table Movies add column Aspect_Ratio float default 0.0```
2. ```ALTER TABLE mytable DROP column_to_be_deleted;```
3. ```ALTER TABLE mytable RENAME TO new_table_name;```
4. ```alter table movies add column Language Text default 'English'```

## Select into table

```SQL
insert into ENTL_ENTITY Select 700, 1,'A123456',DESCR,TYPE,STATUS,DATE_CREATED,DATE_MODIFIED,MODIFIED_BY,CREATED_BY,UNIVERSE_ID From  ENTL_ENTITY  Where ID = 667
  ```

## How to group by and sort by the "group-by" column itself
```SQL
    select count(account_number) as cnt, CHH_ID from chh_acct_map where approved_on is not null group by chh_id order by cnt desc
```

## Select distinct column without using distinct
```SQL
select  LENGTH from FILEMETA group by LENGTH
```

```SQL
SELECT Employee.Name, Department.DeptName  FROM Employee, Department WHERE Employee.Dept_ID = Department.Dept_ID;
```

## Order/Ranking
```SQL
SELECT MIN(Wages) FROM 
( 
    SELECT TOP 3 Wages FROM table ORDER BY Wages DESC; 
) As tmp; 
```

## Second best mark IInd best mark
```SQL
select max(a.mark) from student a where a.mark not in (select max(b.mark) from student b)
```

## Delete duplicate records

```SQL
delete from table_name where rowid not in (select max(rowid) from table group by duplicate_values_field_name);
delete duplicate_values_field_name dv from table_name ta where rowid <(select min(rowid)  from table_name tb where ta.dv=tb.dv); 
DELETE FROM Employee  WHERE EmpID NOT IN (SELECT MAX(EmpID) FROM MyTable GROUP BY EmpName)
```

## Delete duplicate email (person)
```SQL
delete p from Person p, Person r where p.email=r.email and p.id > r.id;
```
## [SQL Ordered data with Offset and pagination](https://sqlbolt.com/lesson/filtering_sorting_query_results)

```sql
SELECT column, another_column, …
FROM mytable
WHERE condition(s)
ORDER BY column ASC/DESC
LIMIT num_limit OFFSET num_offset;
```

## Latitude north to south

```sql
SELECT City FROM north_american_cities where Country like 'United States' Order by latitude desc;
```

## Find the total domestic and international sales that can be attributed to each director (join + group by)

```sql
SELECT director, sum(b.domestic_sales) + sum(b.international_sales) FROM movies m, boxoffice b
where m.id = b.movie_id group by m.director;
```

## How to insert multiple records in one-Sql

```sql
INSERT INTO mytable
VALUES (value_or_expr, another_value_or_expr, …),
       (value_or_expr_2, another_value_or_expr_2, …),
       …;
```

## How to update the data

```sql
UPDATE mytable
SET column = value_or_expr, 
    other_column = another_value_or_expr, 
    …
WHERE condition;
```

## Join types are
* Inner Join -- when data is symmetrical (won't work for assymetrical data)
* self join
* outer join (LEFT, RIGHT), 
* cross-join ( SELECT * FROM table1, table2 - product n*m rows returned)
* SQL should be better practiced in notebook for joins, group by, group by with joins and self Join
* SQL should be supported with knowledge about Index - Clustered and non Clustered"

## Summarizes the result of the join operations:

* The result of T1 **INNER JOIN** T2 consists of their paired rows where the join-condition is true.
* The result of T1 **LEFT OUTER JOIN** T2 consists of their paired rows where the join-condition is true and, for each unpaired row of T1, the   concatenation of that row with the null row of T2. All columns derived from T2 allow null values.** 
* The result of T1 **RIGHT OUTER JOIN** T2 consists of their paired rows where the join-condition is true and, for each unpaired row of T2, the concatenation of that row with the null row of T1. All columns derived from T1 allow null values.** 
* The result of T1 **FULL OUTER JOIN** T2 consists of their paired rows and, for each unpaired row of T2, the concatenation of that row with the null row of T1 and, for each unpaired row of T1, the concatenation of that row with the null row of T2. All columns derived from T1 and T2 allow null values.**


## Show all Users that do not have addresses (user and address table)

 ```SQL
/*#show all Users that do not have addresses */
select * from User u  left outer join Address a on u.UserID = a.UserID  where a.UserID is null 
  ```

## Show all customers that do not have Orders
```
    select  c.name as Customers from Customers c left outer join Orders o on c.id=o.customerId where  o.id is null
```

## SQL - Group by vs where
1. GROUP BY clause is executed after the WHERE clause is executed

## Show all Users that do not have addresses (user and address table)
```SQL
  /*Having vs Where*/
  /* HAVING specifies a search condition for a group or an aggregate function used in SELECT statement. */
  select City, SUM(Salary) as TotalSalary
  from tblEmployee
  Where Gender = 'Male'
  group by City
  Having City = 'London'
  
  select City, CNT=Count(1)
  From Address
  Where State = 'MA'
  Group By City
  Having Count(1)>5
  
  SELECT edc_country, COUNT(*)
  FROM Ed_Centers
  GROUP BY edc_country
  HAVING COUNT(*) > 1
  ORDER BY edc_country;
```

## Example for CTE, Common table expression
```SQL
  /* With clause query */
  WITH employee AS (SELECT * FROM Employees)
  SELECT * FROM employee WHERE ID < 20
  UNION ALL
  SELECT * FROM employee WHERE Sex = 'M'
```

## Example for CTE, SET1 SET2 example
```SQL
/* multiple with clause query */
WITH SET1 AS (SELECT SYSDATE FROM DUAL), -- SET1 initialised
     SET2 AS (SELECT * FROM SET1)        -- SET1 accessed
SELECT * FROM SET2;                      -- SET2 projected
```

## If player activity is stored in Activity table, find the first device that he used to login

```sql
select player_id, device_id 
from activity 
where (player_id, event_date) in (select player_id, min(event_date) from activity group by player_id ) 

select distinct player_id
, first_value(device_id) over (partition by player_id order by event_date) device_id
from activity
```



## How to create anki from this boot mock question file
1. [Sql bolt](https://sqlbolt.com/lesson/filtering_sorting_query_results)
```
mdanki Sql.md Sql.apkg --deck "Mohan::pack::sql"
```
