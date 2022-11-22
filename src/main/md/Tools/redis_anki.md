## How to run redis on windows with client

1. docker run -name redis -d redis
2. docker exec -it redis sh
3. redis-cli

## Basics of Redis Commands
1. Strings
   1. set string-key value
   2. set email mohan@maoh.com
   3. mset key value key value
   4. mget key1 key2
   5. getrange name 0 4 (substring of value)
   6. strlen technlogy (string length of value would be answer)
   1. keys *
   2. flushall
2. Counter
   1. set count 1
   2. incr count
   3. incrby count 10
   4. decr count
   5. decrby count 5
   6. incrbyfloat pi 0.001
   7. set a 1
   8. get a
   9. expire a 10 (expire this key in 10 seconds)
   10. ttl a
   11. ttl a
   12. get a (nil)
3. Lists
   3. lpush country India
   4. lpush count USA
   5. lrange country 0 -1 (all the data)
   6. lrange country 0 1
   7. rpush country Australia (push at the bottom)
   8. llen country
   9. lpop country
   10. rpop country
   11. lrange country
   12. lpush country France
   13. clear
   14. lrange country 0 -1
   15. lset country 0 German
   16. lset country before Germany "NewZealand"
   17. lindex country 2
   18. lpushx movies Avengers (push only if list exists)
   19. rpushx movies Avengers
   20. lrange movies 0 -1
   21. sort country ALPHA
   22. sorty country desc ALPHA
4. Sets
   1. sadd technology Java
   2. sadd technology Redis Noodejs AWS
   3. smembers technology
   4. sadd technology add
   5. scard technology
   6. sismember technology java (1)
   7. sismember technology Spring (0)
   8. sadd frontend Javascript HTML nodejs React
   9. sdiff technology frontend
   10. sdiffstore newset technology frontend
   11. sinter technology frontend
   12. sinter technology frontend newset
   13. sinterstore destinationset technology frontend
   14. smembers newinter
   15. smembers technology
   16. sunion technology frontend
5. SortedSets
   1. zadd users 1 Shabbir
   2. zadd users 2 Alex 3 Nimah 4 Steve 5 Nich
   3. zrange users 0 -1
   4. zrange users 0 -1 withscores (display with scores)
   5. zcard users (number of variabble)
   6. zcount users -inf +inf (to fetch the count. find all uers with score range)
   7. zcount users 0 4
   8. zrem users Alex (remove from set)
   9. zrange users 0 -1 withscores
   10. zrange users 0 -1
   11. zrevrange users 0 -1 withscores (Reverse the range... display in reverse order)
   12. zscore users Shabbir (find the score of the user)
   13. zrevrangebyscore users 5 0 (list all from 5 to 0)
   14. zincrby users 2 Steve (increase the score of the user)
   15. zremrangebyscore users 5 6
   16. zadd users 5 Qutub 6 Babu
   17. zrange users 0 -1 withscores
   18. zremrangebyrank users 0 2
   19. 
   
   

## Reference
1. [Redis Course - In-Memory Database Tutorial](https://www.youtube.com/watch?v=XCsS_NVAa1g)