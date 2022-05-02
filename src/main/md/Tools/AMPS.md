## What is AMPS?
1. Advanced Message Processing System
   1. Messaging system with Database
   2. Database with Messaging system
2. Realtime messaging inside a SQL DB!
3. Content filtering to prevent over-subscription
4. Integration of DB, messaging and Analytics is un-necessary


## How they sell about AMPS?

1. AMPS = DB + Messaging + Analytics
2. DB = SOW
3. Analytics - smaller subset of stream-base
4. High performance - Content filtering reduces over-subscription
   1. Over-subscription = waste
   2. Tradition system - Forces produces and consumer to talk to dedicated TOPIC to avoid waste
   3. SQL-92 + X-Path
      1. XML
         1. /FIXML/Order@Sym = 'APPLE' AND /FIXML/Order@Prc >= 130.2
      2. FIX
         1. /symbol = "IBM" and /status in ('D', 'C')
5. Messages are delivered in order
   1. 500-million messages are handled
   2. No index (internally), uses pseduo index

## Order or message delivered
1. Order in which it is stored in DB is delivered
2. Order in which txn committed

## Kernel bypass technology
1. For faster delivery of message
2. 


## Reference

1. [Low-Latency Meets Large Scale](https://www.crankuptheamps.com/downloads/documentation/LLS_2013-11-12.pdf)
2. [AMPS User-guide - 4.0.0](http://devnull.crankuptheamps.com/documentation/4.0.0.2/guides/user-guide/UserGuide.pdf)
3. [60East CEO Jeffrey Birnbaum: Real Time DBs, Low Latency meets Large Scale when you crank up the AMPS](https://www.youtube.com/watch?v=ju5NRXZG61w)
4. [What could you build with a data time machine? Jeffrey Birnbaum, CEO, 60East Technologies AMPS](https://www.youtube.com/watch?v=3hcO3KVgzko)
5. [Scalable Real Time Streaming with High Performance NoSQL SQL Queues, 60East CEO Jeffrey Birnbaum](https://www.youtube.com/watch?v=_iEf4FWbr2Y)
6. [AMPS for ViewServers](https://www.crankuptheamps.com/downloads/documentation/views_final.pdf)
7. [AMPS filetype:pdf inurl:https://www.crankuptheamps.com/downloads/documentation/](Google URL for AMPS presentation)