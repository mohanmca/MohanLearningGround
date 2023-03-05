## What is the difference between RowId and RowNum

In Oracle, RowID - physical location id for row. rowid for a row never changes. Rownum changes for every sql resultset.

| ROWID					      |ROWNUM	|
|:------------------------|------:|
| AAAAECAABAAAAgiAAA		    |1 |


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

## Join types are
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

## [Correlated_subquery](https://en.wikipedia.org/wiki/Correlated_subquery)