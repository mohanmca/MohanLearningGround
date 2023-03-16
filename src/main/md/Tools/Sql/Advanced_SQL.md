## [What is Window function?](Effective-SQL:Item 37: Know How to Use Window Functions)
1. “Window” refers to a set of rows that surround a considered row, either preceding or following that row.
2. PARTITION BY predicate specifies how the window should be divided.
   1. If partition by is omitted, your database system applies the function over the entire result set
3. Partition BY results are sensitive to the order in which the rows are returned.
   1. Query may have different predicates for each OVER clause

## What are few function that could be used for window function?
1. Many of the aggregate functions SUM(), COUNT(), AVG(), and others, can be used as window functions.
2. ROW_NUMBER() and RANK()

## Find TotalByCustomer - running sum for each customer, TotalOverall - and entire order Expected: "CustomerID, OrderNumber, OrderTotal, TotalByCustomer and TotalOverall"

```sql
SELECT  o.OrderNumber, o.CustomerID, o.OrderTotal, 
        SUM(o.OrderTotal) OVER (PARTITION BY o.CustomerID ORDER BY o.OrderNumber, o.CustomerID) AS TotalByCustomer, 
        SUM(o.OrderTotal) OVER (ORDER BY o.OrderNumber) AS TotalOverall
FROM Orders AS o
ORDER BY o.OrderNumber, o.CustomerID;
```

## Find total-unspent using different predicate for each over clause

```sql
SELECT  t.AccountID, t.Amount, 
        SUM(t.Amount) OVER (PARTITION BY t.AccountID ORDER BY t.TransactionID DESC) - t.Amount AS TotalUnspent, 
        SUM(t.Amount) OVER (ORDER BY t.TransactionID) AS TotalOverall
FROM Transactions AS t
ORDER BY t.TransactionID
```
![Running Total](../../img/running_total.png)

## How to create anki from this boot mock question file
1. [Sql bolt](https://sqlbolt.com/lesson/filtering_sorting_query_results)
2. [Crack SQL Interview Question: Subquery vs. CTE](https://towardsdatascience.com/sql-for-data-analysis-subquery-vs-cte-699ef629d9eb)
```
mdanki Advanced_Sql.md Sql.apkg --deck "Mohan::Pack::Advanced_Sql"
```
