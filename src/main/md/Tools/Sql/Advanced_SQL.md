## [What is Window function?](Effective-SQL:Item 37: Know How to Use Window Functions)
1. “Window” refers to a set of rows that surround a considered row, either preceding or following that row.
2. PARTITION BY predicate specifies how the window should be divided.
   1. If partition by is omitted, your database system applies the function over the entire result set
3. Partition BY results are sensitive to the order in which the rows are returned.
   1. Query may have different predicates for each OVER clause
4. If there is no partition-clause or oder-by given... aggregate function work like group-by
   1. select account_id, amount, SUM(amount) OVER() AS total_sum from account;

## When to use Window functions
1. Window functions are “aware” of the surrounding rows, which makes it easier to create running or moving aggregations than with the traditional aggregation functions and statement-level grouping.

## What are few function that could be used for window function?
1. Many of the aggregate functions SUM(), COUNT(), AVG(), and others, can be used as window functions.
2. ROW_NUMBER() and RANK()

## Window Functions Range vs Rows

1. Whenever you need to change the window frame’s bounding to a non-default setting, you must specify an ORDER BY predicate even when it is optional.
2. Image If you need to define an arbitrary size for a window frame, you must use ROWS, which allows you to input how many rows preceding or following are to be included in the window frame.
3. Image RANGE can accept only UNBOUNDED PRECEDING, CURRENT ROW, or UNBOUNDED FOLLOWING as valid options.
4. Image You can choose between RANGE for logical grouping of rows and ROWS for physical offset of the rows. If the ORDER BY predicate does not return duplicate values, the results are equivalen

## Find TotalByCustomer - running sum for each customer, TotalOverall - and entire order Expected: "CustomerID, OrderNumber, OrderTotal, TotalByCustomer and TotalOverall"

```sql
SELECT  o.OrderNumber, o.CustomerID, o.OrderTotal, 
        SUM(o.OrderTotal) OVER (PARTITION BY o.CustomerID ORDER BY o.OrderNumber, o.CustomerID) AS TotalByCustomer, 
        SUM(o.OrderTotal) OVER (ORDER BY o.OrderNumber) AS TotalOverall
FROM Orders AS o
ORDER BY o.OrderNumber, o.CustomerID;
```

## Find total-unspent using different predicate for each over clause, Transaction: Amount, AccountId, TransactionId

```sql
SELECT  t.AccountID, t.Amount, 
        SUM(t.Amount) OVER (PARTITION BY t.AccountID ORDER BY t.TransactionID DESC) - t.Amount AS TotalUnspent, 
        SUM(t.Amount) OVER (ORDER BY t.TransactionID) AS TotalOverall
FROM Transactions AS t
ORDER BY t.TransactionID
```
![Running Total](../../img/running_total.png)

## Find OrderSequence, CustomerOrderSequence, OrderRanking, CustomerOrderRanking

```sql
SET search_path = SalesOrdersSample;

SELECT 
  ROW_NUMBER() OVER ( ORDER BY o.OrderDate) AS OrderSequence,
  ROW_NUMBER() OVER ( PARTITION BY o.CustomerID   ORDER BY o.OrderDate ) AS CustomerOrderSequence,
  o.OrderNumber, o.CustomerID, o.OrderDate, o.OrderTotal,
  RANK() OVER (    ORDER BY o.OrderTotal DESC  ) AS OrderRanking,
  RANK() OVER (    PARTITION BY o.CustomerID    ORDER BY o.OrderTotal DESC    ) AS CustomerOrderRanking
FROM Orders AS o
ORDER BY o.OrderDate;
```
## [Demonstration of moving average window functions](5.34 -Effective SQL)
```sql
SELECT  s.CustomerID, s.PurchaseYear, s.PurchaseMonth,
  LAG(s.PurchaseTotal, 1) OVER (PARTITION BY s.CustomerID, s.PurchaseMonth    ORDER BY s.PurchaseYear ) AS PreviousMonthTotal,
  s.PurchaseTotal AS CurrentMonthTotal,
  LEAD(s.PurchaseTotal, 1) OVER (PARTITION BY s.CustomerID, s.PurchaseMonth  ORDER BY s.PurchaseYear ) AS NextMonthTotal,
  AVG(s.PurchaseTotal) OVER ( PARTITION BY s.CustomerID, s.PurchaseMonth    ORDER BY s.PurchaseYear    ROWS BETWEEN 1 PRECEDING AND 1 FOLLOWING  ) AS MonthOfYearAverage
FROM PurchaseStatistics AS s
ORDER BY s.CustomerID, s.PurchaseYear, s.PurchaseMonth;
```

## [Demonstration of a query with both RANGE and ROWS](5.34 -Effective SQL)
```sql
SELECT
  s.CustomerID, s.PurchaseYear, s.PurchaseMonth,
  SUM(s.PurchaseCount) OVER (    PARTITION BY s.PurchaseYear
    ORDER BY s.CustomerID
    RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
  ) AS CountByRange,

  SUM(s.PurchaseCount) OVER (
    PARTITION BY s.PurchaseYear
    ORDER BY s.CustomerID
    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
  ) AS CountByRows

FROM PurchaseStatistics AS s
ORDER BY s.CustomerID, s.PurchaseYear, s.PurchaseMonth;
```

## How to create anki from this boot mock question file
1. [Effective-SQL/PostgreSQL/Chapter 05/Listing 5.031.sql](https://github.com/TexanInParis/Effective-SQL/blob/8047973838c2780da1795742793016faff36315c/PostgreSQL/Chapter%2005/Listing%205.031.sql#L10)
2. [Crack SQL Interview Question: Subquery vs. CTE](https://towardsdatascience.com/sql-for-data-analysis-subquery-vs-cte-699ef629d9eb)
```
mdanki Advanced_Sql.md Sql.apkg --deck "Mohan::Pack::Advanced_Sql"
```
