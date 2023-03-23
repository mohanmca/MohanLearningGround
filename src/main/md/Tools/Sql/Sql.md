## Other Sql References.
1. [Effective SQL](Effective_SQL.md)
2. [Advanced SQL](Advanced_SQL.md)
3. [SQL](Sql.md)

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

## SQL - Group by vs where and Having
1. GROUP BY clause is executed after the WHERE clause is executed
2. Having is filter for group-by clause
   1. we can also use function such as HAVING MIN(Sales.sale_date)

## For inner join, what are all the expression allowed?

1. Any logic that you can use in a WHERE clause can be used in your JOIN clauses
2. More complicated joins can read very much like a WHERE clause.
3. Examples
   1. JOIN t2 ON t2.column = t1.column
   2. JOIN t2 ON t2.column LIKE t1.column**

## Intersect vs Except
1. INTERSECT operator will ensure that only rows that are identical in both result sets are returned
2. The EXCEPT operator will ensure that only rows in the first result set that aren't in the second are returned.

## When to use SubQuery

1. Use a subquery anywhere you can use a table name
2. A scalar subquery - Use a subquery that returns a single column wherever you can use a list of values—for example, in an IN clause.
3. A subquery that returns one column and zero or only one value can be used anywhere you can use a column name or a single literal.

## Where to use SubQuery
1. Subquery in several places in another SELECT, UPDATE, INSERT, or DELETE statement.
2. Subquery can nest at any level, innermost is executed

## How many times inner-query in co-related subquery executed

1. if outer query processes a million records, inner co-related subquery also executed million times

## Where can we use SubQuery

1. Select
2. From
3. Where
4. Having

## Example of Scalar Subquery, find all employee whose salary is greater than avg-salary

```sql
select * from employee
where salary > (select avg(salary) from Employee)
```

```sql
select e.* from employee e 
   inner join (select avg(salary) salary from Employee) avg_salary
   on e.salary > avg_salary.salary
```

## Example of Multi-Row Subquery, find all employee whose salary is highest in their department

```sql
select * from employee e where (salary, department_id) in (select max(salary), department_id from salary group by department_id
```

## Finding products not ordered in December 2015 using a single-column table subquery

```sql
SELECT Products.ProductName FROM Products
WHERE Products.ProductNumber NOT IN 
  (SELECT Order_Details.ProductNumber 
   FROM Orders 
      INNER JOIN Order_Details
        ON Orders.OrderNumber = Order_Details.OrderNumber
   WHERE Orders.OrderDate 
    BETWEEN '2015-12-01' AND '2015-12-31');
```

## Scalar SubQuery

```sql
SELECT Products.ProductNumber, Products.ProductName, (
    SELECT MAX(Orders.OrderDate)
    FROM Orders
      INNER JOIN Order_Details
      ON Orders.OrderNumber = Order_Details.OrderNumber    WHERE Order_Details.ProductNumber = Products.ProductNumber
    ) AS LastOrder
FROM Products;
```

## SubQuery

1. ```SELECT * FROM sales_associates WHERE salary > (SELECT AVG(revenue_generated) FROM sales_associates);```
2. ```DELETE FROM Student2 WHERE ROLL_NO IN ( SELECT ROLL_NO FROM Student1 WHERE LOCATION = ’chennai’);```
3. ```UPDATE Student2 SET NAME=’geeks’ WHERE LOCATION IN ( SELECT LOCATION FROM Student1 WHERE NAME IN (‘Raju’,’Ravi’));```

## Update Statement switch M to F (and vice versa)

```sql
update Salary set sex=(case when sex = 'f' then 'm' else 'f' end)
```

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

## Order/Ranking - Find the 3rd highest wage from Wages table
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

## Delete duplicate records using sub-query

```SQL
delete from table_name where rowid not in (select max(rowid) from table group by duplicate_values_field_name);
DELETE FROM Employee  WHERE EmpID NOT IN (SELECT MAX(EmpID) FROM MyTable GROUP BY EmpName) 
```

## Delete duplicate records using co-related subquery

```sql
delete duplicate_values_field_name dv from table_name ta where rowid <(select min(rowid)  from table_name tb where ta.dv=tb.dv);
```

## Delete duplicate person based on duplicate email using self-join 
```SQL
delete p from Person p, Person r where p.email=r.email and p.id > r.id;
```

## [SQL Ordered data with Offset and pagination](https://sqlbolt.com/lesson/filtering_sorting_query_results)

```sql
SELECT column, another_column, … FROM mytable
WHERE condition(s) ORDER BY column ASC/DESC
LIMIT num_limit OFFSET num_offset;
```

## Latitude north to south

```sql
SELECT City FROM north_american_cities where Country like 'United States' Order by latitude desc;
```

## Find the total domestic and international sales that can be attributed to each director (join + group by)

```sql
SELECT director, sum(b.domestic_sales) + sum(b.international_sales) FROM movies m, boxoffice b where m.id = b.movie_id group by m.director;
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

## Show all Users that do not have addresses (user and address table)

 ```SQL
/*#show all Users that do not have addresses */
select * from User u  left outer join Address a on u.UserID = a.UserID  where a.UserID is null 
```

## Show all customers that do not have Orders
```
    select  c.name as Customers from Customers c left outer join Orders o on c.id=o.customerId where  o.id is null
```

## Find male employees whose City is London
```SQL
  /*Having vs Where*/
  /* HAVING specifies a search condition for a group or an aggregate function used in SELECT statement. */
  select City, SUM(Salary) as TotalSalary
  from tblEmployee
  Where Gender = 'Male'
  group by City
  Having City = 'London'
```

## SQL - The winner in each group is the player who scored the maximum total points within the group. In the case of a tie, the lowest player_id wins. (Matches and PlayersTable)

```sql
select group_id as GROUP_ID, min(player_id) as PLAYER_ID
from Players,
    (select player, sum(score) as score from
        (select first_player as player, first_score as score from Matches
        union all
        select second_player, second_score from Matches) s
    group by player) PlayerScores
where Players.player_id = PlayerScores.player and (group_id, score) in
	(select group_id, max(score)
	from Players,
		(select player, sum(score) as score from
			(select first_player as player, first_score as score from Matches
			union all
			select second_player, second_score from Matches) s
		group by player) PlayerScores
	where Players.player_id = PlayerScores.player
	group by group_id)
group by group_id
```

## SQL - The winner in each group is the player who scored the maximum total points within the group. In the case of a tie, the lowest player_id wins. (With CTE and Partition)
```SQL
-- Line up all players and scores
-- (each match will result in 2 rows, one for first_player, the other for second_player)
WITH all_points AS
(
    SELECT first_player as player_id, first_score as score
    FROM Matches
    UNION ALL
    SELECT second_player as player_id, second_score as score
    FROM Matches
)
-- Sum up all the points scored by each player
, player_score AS
(
    SELECT
        player_id,
        SUM(score) as final_score
    FROM all_points
    GROUP BY player_id
),
-- Use RANK to rank players in each group
ranking AS
(
    SELECT group_id, ps.player_id, RANK() OVER(PARTITION BY group_id ORDER BY final_score DESC, ps.player_id ASC) as ranked
    FROM player_score as ps
    INNER JOIN Players as p
        ON ps.player_id = p.player_id
)
-- Select 1st ranked players from each group
SELECT group_id, player_id
FROM ranking
WHERE ranked = 1```

## Example for CTE, Common table expression
```SQL
  /* With clause query */
  WITH employee AS (SELECT * FROM Employees)
  SELECT * FROM employee WHERE ID < 20
  UNION ALL
  SELECT * FROM employee WHERE Sex = 'M'
```

## Example for CTE, Step by Step construction query
```SQL
/* multiple with clause query */
WITH SET1 AS (SELECT SYSDATE FROM DUAL), -- SET1 initialised
     SET2 AS (SELECT * FROM SET1)        -- SET1 accessed
SELECT * FROM SET2;                      -- SET2 projected
```

## (Unique product that sold only in certain quarter)[https://leetcode.com/problems/sales-analysis-iii/submissions/915377197/]

```sql
select uniq_sales.product_id, p.product_name from
(select distinct product_id from sales s where s.sale_date between '2019-01-01' and  '2019-03-31'
and product_id not in (select distinct product_id from sales s where s.sale_date < '2019-01-01' or s.sale_date >  '2019-03-31')) as uniq_sales, Product p
where p.product_id = uniq_sales.product_id

SELECT Product.product_id, Product.product_name FROM Product 
JOIN Sales ON Product.product_id = Sales.product_id 
GROUP BY Sales.product_id 
HAVING MIN(Sales.sale_date) >= "2019-01-01" AND MAX(Sales.sale_date) <= "2019-03-31";
```

## [Find all the seller who has maximum total_price](https://leetcode.com/problems/sales-analysis-i/description/)
```sql
SELECT seller_id FROM Sales GROUP BY seller_id
HAVING SUM(PRICE) >= all ( SELECT SUM(PRICE) FROM Sales GROUP BY seller_id)
```

## [that reports the buyers who have bought S8 but not iPhone - Using HavingFilter](https://leetcode.com/problems/sales-analysis-ii/solutions)

```sql
select s.buyer_id from Sales s inner join Product p on p.product_id = s.product_id
group by s.buyer_id
having 
SUM( case when p.product_name ='S8' then 1 else 0 end) > 0 AND
SUM( case when p.product_name ='iPhone' then 1 else 0 end) = 0 
```

## If player activity is stored in Activity table, find the first device that he used to login

```sql
select player_id, device_id from activity 
where (player_id, event_date) in (select player_id, min(event_date) from activity group by player_id ) 

select distinct B.player_id, B.device_id from Activity B where b.event_date in (select min(event_date) from Activity A where A.player_id = B.player_id)
```

## [Find all the seller who has maximum total_price](https://leetcode.com/problems/sales-analysis-i/description/)
```sql
with total_price as (select seller_id, sum(price) as sumprice from Sales group by seller_id order by sumprice desc)
    select seller_id from total_price where total_price.sumprice = (select sumprice from total_price limit 1)
```
```sql
with total_price as (select seller_id, sum(price) as sumprice from Sales group by seller_id order by sumprice desc)
    select seller_id from total_price where total_price.sumprice = (select max(sumprice) from total_price)
```

## [Find all the S8 buyer](https://leetcode.com/problems/sales-analysis-ii/)
```
with iphone_buyer as (select s.buyer_id from Sales s inner join Product p on s.product_id = p.product_id and p.product_name='iPhone'),
    s8_buyer as (select s.buyer_id from Sales s inner join Product p on s.product_id = p.product_id and p.product_name='S8')
    select distinct s.buyer_id from s8_buyer s left join iphone_buyer i on s.buyer_id = i.buyer_id where i.buyer_id is null

with iphone_buyer as (select s.buyer_id from Sales s inner join Product p on s.product_id = p.product_id and p.product_name='iPhone'),
    s8_buyer as (select s.buyer_id from Sales s inner join Product p on s.product_id = p.product_id and p.product_name='S8')
    select distinct s8.buyer_id from s8_buyer s8 where s8.buyer_id not in (select buyer_id from iphone_buyer)    
```

## If player activity is stored in Activity table, find the first device that he used to login - using window function

```sql
select distinct player_id, first_value(device_id) over (partition by player_id order by event_date) device_id
from activity
```

## Select project_id that has maximum number of employees

```sql
with a as (select project_id, count(employee_id) as ct from Project as p group by project_id) 
   select project_id from a where a.ct = (select max(ct) from a)

with cnt as (select p.project_id, count(*) as emp_count from Project p group by p.project_id order by emp_count desc),
    maxi as (select emp_count from cnt limit 1)
    select project_id from cnt where emp_count in (select emp_count from maxi)
```

## Select project_id that has maximum number of employees - use Having Count filter

```sql
# Write your MySQL query statement below
SELECT project_id
FROM project
GROUP BY project_id
HAVING COUNT(employee_id) =
(
    SELECT count(employee_id)
    FROM project
    GROUP BY project_id
    ORDER BY count(employee_id) desc
    LIMIT 1
)
```

## [1113. Reported Posts](https://leetcode.com/problems/reported-posts/submissions/915539685/)

```sql
select extra as report_reason, count(distinct post_id) as report_count from Actions 
where extra is not null AND action='report' AND action_date = '2019-07-04' 
group by extra
```

## [Find Capital Gain](https://leetcode.com/problems/capital-gainloss/solutions/?orderBy=most_votes)

```sql
SELECT stock_name, SUM(
    CASE
        WHEN operation = 'Buy' THEN -price
        ELSE price
    END
) AS capital_gain_loss
FROM Stocks
GROUP BY stock_name

with pp as (select stock_name, sum(price) as buy from Stocks where operation='Buy' group by stock_name),
     sp as (select stock_name, sum(price) as sell from Stocks where operation='Sell' group by stock_name)
     select pp.stock_name, (sp.sell - pp.buy) as capital_gain_loss from pp, sp where pp.stock_name = sp.stock_name
```

## Find the customer-number who placed maximum number of orders

```
select customer_number from (select customer_number, count(customer_number) as cnt from Orders group by customer_number order by cnt desc limit 1) top
```
```
with count_order as (select customer_number, count(customer_number) as cnt from Orders group by customer_number )
select customer_number from count_order where cnt in (
    select MAX(cnt) from count_order 
)
```

## SQL query to report all the consecutive available seats in the cinema.
```sql
select distinct c1.seat_id from Cinema c1, Cinema c2 where c1.free=1 and Abs(c1.seat_id - c2.seat_id) = 1 and c2.free=1 order by c1.seat_id;
--self-join
select distinct(a.seat_id) from Cinema a join Cinema b 
where abs(a.seat_id-b.seat_id) =1  and a.free=1 and b.free=1
order by a.seat_id
```

## SQL query to report all the consecutive available seats in the cinema using IN SubQuery

```sql
SELECT seat_id FROM cinema
WHERE free = 1 AND (
    seat_id - 1 IN (SELECT seat_id FROM cinema WHERE free = 1)
    OR
    seat_id + 1 IN (SELECT seat_id FROM cinema WHERE free = 1)
);
```

## Non-Aggregate window functions

1. CUME_DIST()
1. DENSE_RANK()
1. FIRST_VALUE()
1. LAG()
1. LAST_VALUE()
1. LEAD()
1. NTH_VALUE()
1. NTILE()
1. PERCENT_RANK()
1. RANK()
1. ROW_NUMBER()

## How to find Leaf/Inner/Root from Tree Table

```sql
select id,
case when p_id is null then 'Root'
     when id in (select p_id from tree) then 'Inner'
     else 'Leaf' end as Type
from tree
```

## How to find - the names of all the salespersons who did not have any orders related to the company with the name "RED". (order, company, salesperson) with join and right-join
```sql
select salesperson.name
from orders o join company c on (o.com_id = c.com_id and c.name = 'RED')
right join salesperson on salesperson.sales_id = o.sales_id
where o.sales_id is null
```

## How to find - the names of all the salespersons who did not have any orders related to the company with the name "RED". (order, company, salesperson) - WITH CTE

```sql
with red_order as (select o.sales_id from Company c, Orders o where c.com_id = o.com_id and c.name = 'RED')
select name from SalesPerson where sales_id not in (select sales_id from red_order)

with red_order as (select o.sales_id from Company c, Orders o where c.com_id = o.com_id and c.name = 'RED')
select name from SalesPerson s left join red_order r on r.sales_id = s.sales_id where r.sales_id is null
```

## Check if 3 given side can form triangle

```sql
SELECT *, IF(x+y>z and x+z>y and y+z>x, 'Yes', 'No') as triangle FROM triangle
```

## Select all emp.name, bonus.bonus where bonus is less than 1000

```sql
select e.name, b.bonus from employee e left join bonus b on  e.empId=b.empId where COALESCE(b.bonus,0) < 1000 ;
```

## Date_Add 

```sql
select e.name, b.bonus from employee e left join bonus b on  e.empId=b.empId where COALESCE(b.bonus,0) < 1000 ;
```

## How to create anki from this boot mock question file
1. [Sql bolt](https://sqlbolt.com/lesson/filtering_sorting_query_results)
2. [Crack SQL Interview Question: Subquery vs. CTE](https://towardsdatascience.com/sql-for-data-analysis-subquery-vs-cte-699ef629d9eb)
```
mdanki Sql.md Sql.apkg --deck "Mohan::Pack::Sql"
```
