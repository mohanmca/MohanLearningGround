* Event - Immutable object, input to the system
* Event (Lean) - Only required details (Moust click, Address Change, NewOrder)
* Event (Rich or Fat) - Event with past, present and additional context, so that processing system no need to query other system to know the details. 
* GetExchangeRateUSD_JPY
  * Is above event?
  * Result would change depends on time.
* CommandSource :: State -> Command -> Event
* EventSource :: State -> Event -> Event

* [Command Source](http://thinkbeforecoding.com/post/2013/07/28/Event-Sourcing-vs-Command-Sourcing)
* [Event Source](https://www.martinfowler.com/eaaDev/EventSourcing.html)
* [Event Source](https://goodenoughsoftware.net/tag/event-sourcing/)
* [CQRS](https://goodenoughsoftware.net/tag/cqrs/)
* [Event Source](https://ookami86.github.io/event-sourcing-in-practice/#further-reading-1.md)
* [Domain Event | Command](https://www.martinfowler.com/eaaDev/DomainEvent.html)