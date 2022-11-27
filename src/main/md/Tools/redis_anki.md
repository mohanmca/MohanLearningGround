## How to run redis on windows with client

1. docker run -name redis -d redis
2. docker exec -it redis sh
3. redis-cli

## Basics of Redis Commands

## Strings
1. set string-key value
2. set email mohan@maoh.com
3. mset key value key value
4. mget key1 key2
5. getrange name 0 4 (substring of value)
6. strlen technlogy (string length of value would be answer)
7. keys *
8. flushall

## Counter
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

## Lists

1. lpush country India
2. lpush count USA
3. lrange country 0 -1 (all the data)
4. lrange country 0 1
5. rpush country Australia (push at the bottom)
6. llen country
7. lpop country
8. rpop country
9. lrange country
10. lpush country France
11. clear
12. lrange country 0 -1
13. lset country 0 German
14. lset country before Germany "NewZealand"
15. lindex country 2
16. lpushx movies Avengers (push only if list exists)
17. rpushx movies Avengers
18. lrange movies 0 -1
19. sort country ALPHA
20. sorty country desc ALPHA

## Sets
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

## SortedSets
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

## Hyperloglog

1. Probablistic DataStructure to count unique values
   1. Unique IP Adderesses
   2. Email address
2. pfadd hll a
   1. value of a = 1
3. pfadd hll b c d e f g
   1. all its values are 1
4. pfcount hll
   1. 7
5. pfcount hll hll2
   1. pfmerge mergedhll hll hll2
   2. pfcount mergedhll

## Hashes in Redis
1. hset myhash name value key value
2. hset myhash email email@gomain.com
3. hkeys kyhash
4. hexist myhash name
   1. 1 - if value available
   2. 0 - if not available
5. hlen myhash
6. hlen myhash
7. hmset myhash country India Phone 9999
   1. hmget myhash name email
8. hincrby myhash age 2
9. hdecrbyfloat myhash salary 1.5
10. hexists myhash age
11. hstrlen myhash name
12. hsetnx myhash name value
    1. Add name if and only if not exist already

## Redis Transaction
1. multi -- begins transactions
2. set name a
3. get name
4. set a 1
5. set b 2
6. exec --commit all the transactions
7. multi
8. get a
9. discard --discard all the transactions
10. flushall
11. watch a
    1. if transaction needs to be aborted by other process/threads
    2. watch would monitor for changes to any variable that is being monitored
    
## PubSub
1. ... session1
   1. subscribe news_channel
2. ... session2
   1. subscribe news_channel
3. ... session3
   1. publish news_channel "Breaking News"
   2. publish broad_cast_channel "Broadcasting"
   3. publish broad_cast_channel "New game"
4. psubscribe news* h?llo
   1. pattern based subscriber
5. pubsub channels
   1. list of the channels
6. pubsub numsub news
   1. Number of subscriber to the channels (without pattern based subscribers)
7. pubsub numpat
   1. number of pattern based subscribers


## Redis scripts
1. eval "redis.call('set', KEYS[1], ARGV[1])" 1 name Shabbit
2. get name
3. eval "redis.call('mset', KEYS[1], ARGV[1], KEYS[2], ARGV[2])" 2 name last_name Shabbir Dawoodi
4. zadd country 1 Italy 2 India 3 USA
5. zrange country 0 -1
6. eval "local order = redis.call('zrange', KEYS[1], 0, -1); return redis.call('hmget', KEYS[2], unpack(order));"
7. script load "local order = redis.call('zrange', KEYS[1], 0, -1); return redis.call('hmget', KEYS[2], unpack(order));"
   1. "0303493920923403dfsdf3903ndc09309230"
8. evalsha 0303493920923403dfsdf3903ndc09309230 2 country country_cap
9. script exists 0303493920923403dfsdf3903ndc09309230
   1. If script exists with hash
10. script flush
11. script exists 0303493920923403dfsdf3903ndc09309230
    1. (integer) 0

## Redis Connection & Security
1. ping
2. echo message
3. select index
   1. list of database
4. select 0
5. select 2
6. All the database is stored in "rdb" or "aof" file (only one file)
7. Single node has 0 index, all index stored in one file
8. select 0
9. set name Shabbir
10. get name --value would be available
11. select 1
12. get name -- value is missing (since it is different db)
13. client list
    1. ---list all the client available
14. client kill id 17
15. config set requirepass
    1. Every client requires password
16. get name
    1. NOAUTH authentication required
17. auth shabbir123
    1. get name
    2. answer displayed due to authentication

## Redis Geospatial (Spherical longitude and latidue) - based on Geohash value
1. GEOADD maps longitude latitude member
2. GEOADD maps 72.585 23.033863 Ahmedabad
3. GEOADD maps 72.8775 93.033863 Mumbai 77.8775 13.033863 Bangalore 
4. Geospatial Data stored in sorted sets
5. zrange maps 0 -1
6. GEOHashValues
7. GEOHASH maps Ahmedabad
8. Geohash.org
9. GEOPOS maps Ahmedabad -- redis command
   1. "ts5e5od9xs" -- Geohash value is encoded form of long + latitude
   2. Redis converts into 52 bit integer and stores, There might be 0.5% of error
10. GEOADD maps 73.33 23.3434 Pune
11. GEODIST maps Mumbai Pune
    1. Value in Meter
12. GEODIST maps Mumbai Pune km
    1. Value in Kilo-Meter
13. GEODIST maps Mumbai Ahmedabad km
14. Find all the restuarent near a restaurant
15. GEORADIUS maps 72.234 51.343 500 m
    1. --find other info within 500 m
16. GEORADIUS maps 72.234 51.343 500 m withcord withdist withhash
17. GEORAIDUSBYMEMBER maps Ahmedabad 500 km
18. GEORAIDUSBYMEMBER maps Ahmedabad 1500 km desc (farthest to closest)

## Redis Benchmark
1. redis-cli -h hostname -p 23455
2. redis-benchmark (U can specificy port and host)
   1. Default with 50 clients with all the commands, 3-bytes clients data
   2. redis-benchmark -n 1000
      1. run benchmark with 1000 commands
   3. redis-benchmark -n 1000 -d 1000000
   4. 1000 clients, with 100Gig of data

## Reference
1. [Redis Course - In-Memory Database Tutorial](https://www.youtube.com/watch?v=XCsS_NVAa1g)