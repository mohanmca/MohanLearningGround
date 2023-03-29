## How to aggregate all the rows under group

1. Mysql - group_concat(distinct product order by product asc, ',') as products
2. PGSQL - String_aggregate()


## [1484. Group Sold Products By The Date](https://leetcode.com/problems/group-sold-products-by-the-date/description/)

```sql
select 
    sell_date, count(distinct product) as num_sold,
    group_concat(distinct product order by product asc, ',') as products
from    Activities
        group by sell_date
order by sell_date, product
```

## [1511. Customer Order Frequency](https://leetcode.com/problems/customer-order-frequency/description/)

```sql
SELECT customer_id, name
    FROM 
Customers JOIN Orders USING(customer_id) JOIN Product USING(product_id)
GROUP BY customer_id
HAVING SUM(IF(LEFT(order_date, 7) = '2020-06', quantity, 0) * price) >= 100
AND SUM(IF(LEFT(order_date, 7) = '2020-07', quantity, 0) * price) >= 100
```

```sql
select customer_id, name from (
select 
    c.name,
    c.customer_id,
    month(o.order_date),
    row_number() over (partition by c.customer_id order by o.order_date) as rown
from 
    Product p inner join Orders o on o.product_id = p.product_id
    inner join Customers c on o.customer_id = c.customer_id
where 
    order_date between '2020-06-01' and '2020-07-31'
group by c.customer_id, month(o.order_date)
having sum(o.quantity * p.price) >= 100

) as ranked_customer
where rown=2
```

## How to make ANKI
```
mdanki Level3.md Advanced_Level3_Sql.apkg --deck "Mohan::Pack::Advanced_Level3_Sql"
```
