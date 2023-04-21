## [1321. Restaurant Growth](https://leetcode.com/problems/restaurant-growth/)
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


## [1369. Get the Second Most Recent Activity](https://leetcode.com/problems/get-the-second-most-recent-activity)
```sql
with cte as (
    select username,
        count(activity) over(partition by username) as cnt,
        row_number() over(partition by username order by endDate desc) as row_num,
        activity,
        startDate,
        endDate
    from
        UserActivity
)
select username, activity, startDate, endDate from cte where row_num=2 or cnt=1
```

## How to create anki
```
mdanki Daily_Track_Sql.md Daily_Track_Sql.apkg --deck "Mohan::Pack::Daily_Track_Sql"
```
