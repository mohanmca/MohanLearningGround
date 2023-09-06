## Circuit Breaker
1. Timeouts are hard to handle
2. Circuit Breaker can help in a way

## Use case
1. User{1,2,3..} => Middle_Tier_Service => External_Service
2. What happens if middle_tier calls are timedout?
3. Would we propagate this timeouts to Users?

## Circuit_Breaker States (Open, Close and Half-Open)
1. Closed (Success) ... Everything is fine
  2. Timeouts or failures increment a failure counter
  3. Success resets the counter
  4. Max failures => Open State
5. Many timeout would trigger CircuitBreaker to move its state to Open
  6. Once it is open state, all the calls would fails (fail fast state)
  7. Cool-down period timer is starts
  8. After a reset timeout => Half-Open
  9. After the cool-down period completed, half-open state is triggered
10. Half-open state
  11. First call allowed through
  13. if first call succeeds => closed
  14. if fails => Open
  15. All other calls fail fast
  
## Transition listeners
1. onOpen
2. onClose
3. onHalfOpen

## Circuit Breaker components
1. Schduler - timer
2. maxFailures before `openning` circuit breaker, toleration limit
3. callTimeout - how long to wait for api to be considered successful
4. resetTimeout

## Akka Stacktrace to familiarize

```scala
 akka.http.scaladsl.model.IllegalRequestException: Request timed out after 10 seconds while waiting for entity data: Consider increasing the timeout for toStrict
[info]  at akka.http.scaladsl.model.IllegalRequestException$.apply(ErrorInfo.scala:99)
[info]  at akka.http.scaladsl.server.directives.BasicDirectives$$anonfun$$nestedInanonfun$toStrictEntity$3$1.applyOrElse(BasicDirectives.scala:404)
[info]  at akka.http.scaladsl.server.directives.BasicDirectives$$anonfun$$nestedInanonfun$toStrictEntity$3$1.applyOrElse(BasicDirectives.scala:400)
[info]  at scala.util.Failure.recover(Try.scala:233)
[info]  at scala.concurrent.impl.Promise$Transformation.run(Promise.scala:487)
[info]  at akka.dispatch.BatchingExecutor$AbstractBatch.processBatch(BatchingExecutor.scala:63)
[info]  at akka.dispatch.BatchingExecutor$BlockableBatch.$anonfun$run$1(BatchingExecutor.scala:100)
[info]  at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
[info]  at scala.concurrent.BlockContext$.withBlockContext(BlockContext.scala:94)
[info]  at akka.dispatch.BatchingExecutor$BlockableBatch.run(BatchingExecutor.scala:100)
[info]  at akka.dispatch.TaskInvocation.run(AbstractDispatcher.scala:49)
[info]  at akka.dispatch.ForkJoinExecutorConfigurator$AkkaForkJoinTask.exec(ForkJoinExecutorConfigurator.scala:48)
[info]  at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
[info]  at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
[info]  at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1656)
[info]  at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)
[info]  at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:183)
```

## Reference
1. [Akka Circuit Breaker](https://www.youtube.com/watch?v=FLw95uX3mkU)
2. [Akka Markdown](https://github.com/akka/akka/blob/main/akka-docs/src/main/paradox/common/circuitbreaker.md)
3. [Akka documents](https://doc.akka.io/docs/akka/current/common/circuitbreaker.html)
