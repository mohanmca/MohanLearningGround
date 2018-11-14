* Event 
  * Facts, Already happened
  * Immutable object, input to the system
  * New events can invalidate old events
  * Cannot be deleted, retracted
  * Even name should be in past tense - *NameChanged, OrderPlaced, UserDeleted, SettlementInitiated, TradeSettled, GoodsDelivered*
* Event 
  * (Lean) - Only required details (Mouse click, Address Change, NewOrder)
  * (Rich or Fat) - Event with past, present and additional context, so that processing system no need to query other system to know the details.   
* Reactor
  * Receive and React (or not) to events (facts)
* Event Sourcing architecture should rely on eventual consistency
  * There is no current accurate instance of state
* GetExchangeRateUSD_JPY
  * Is above event?
  * Result would change depends on time.
* CommandSource :: State -> Command -> Event
  * Object form of method/Action request
  * Represents side effects
  * CreateOrder, UpdateInventory, ShiptProduct
  * Command name should not ask, it should tell. "ShipIt, TransferIt, DeliverIt, CloseOrder, DelteUser"
* EventSource :: State -> Event -> Event
* [Command Source](http://thinkbeforecoding.com/post/2013/07/28/Event-Sourcing-vs-Command-Sourcing)
* [Event Source](https://www.martinfowler.com/eaaDev/EventSourcing.html)
* [Event Source](https://goodenoughsoftware.net/tag/event-sourcing/)
* [CQRS](https://goodenoughsoftware.net/tag/cqrs/)
* [Event Source](https://ookami86.github.io/event-sourcing-in-practice/#further-reading-1.md)
* [Domain Event | Command](https://www.martinfowler.com/eaaDev/DomainEvent.html)
* [Microsoft EventSource document](https://docs.microsoft.com/en-us/azure/architecture/patterns/event-sourcing)
* [How Events Are Reshaping Modern Systems](https://www.infoq.com/presentations/systems-event-driven)
