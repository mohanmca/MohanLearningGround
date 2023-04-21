## [1321. Restaurant Growth](https://leetcode.com/problems/restaurant-growth/)
tags: , 
%

```sql
select A.visited_on, sum(B.daily_sum) as amount, round(avg(B.daily_sum), 2) as average_amount
from 
    (select visited_on, sum(amount) as daily_sum  from Customer group by visited_on) as A,
    (select visited_on, sum(amount) as daily_sum  from Customer group by visited_on) as B
where 
    DATEDIFF(A.visited_on, B.visited_on) between 0 AND 6
group by A.visited_on
having count(A.visited_on) = 7
```
[#running_sum](./running_sum.md) [#cross_join]()

## How to create anki
```
mdanki Daily_Track_Sql.md Daily_Track_Sql.apkg --deck "Mohan::Pack::Daily_Track_Sql"
```
