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

## Reference
1. [Akka Circuit Breaker](https://www.youtube.com/watch?v=FLw95uX3mkU)
2. [Akka Markdown](https://github.com/akka/akka/blob/main/akka-docs/src/main/paradox/common/circuitbreaker.md)
3. [Akka documents](https://doc.akka.io/docs/akka/current/common/circuitbreaker.html)
