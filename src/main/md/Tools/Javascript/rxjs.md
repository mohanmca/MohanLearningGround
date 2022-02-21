### 4 problems with callback
* Call back hell
* Underyling concurrency is complex
* callback called more than once
* changes the error sequence (not like try-catch)

### Event Handler
* Events are not handled as first-class value in handler, as handlers ignores return value of handler
* Can't compose two event handlers

*Promise doesn't support series of events, only handles one events (and result)*

### Reactive Streaming
* We can think of our streaming sequence as an array in which elements are separated by time instead of by memory (or Comma). 
* Dealing with sequences gives us enormous power; we can merge, transform, or pass around Observables easily. 
* Turn events we can’t get a handle on into a tangible data structure  (sequence of observable), that’s as easy to use as an array.


###RX
* Create, Transform, Compose and React to streams of data
* http://rxmarbles.com/#findIndex
*  RxJS provides plenty of other operators (like create) that make it easy to create Observables for common sources.
* https://www.learnrxjs.io/operators/creation/create.html

###Usage
npm install rx

```Javascript
var Rx = require(​'rx'​);
    
Rx.Observable.just(​'Hello World!'​).subscribe(​function​(value) {
      console.log(value);
});
```



```Javascript
var​ clicks = 0;
​   document.addEventListener(​'click'​, ​function​ registerClicks(e) {
​     ​if​ (clicks < 10) {
​       ​if​ (e.clientX > window.innerWidth / 2) {
​         console.log(e.clientX, e.clientY);
​         clicks += 1;
​       }
​     } ​else​ {
​       document.removeEventListener(​'click'​, registerClicks);
​     }
​   });

//vs

    Rx.Observable.fromEvent(document, 'click')
      .filter(function(c) { return c.clientX > window.innerWidth / 2; })
      .take(10)
      .subscribe(function(c) { console.log(c.clientX, c.clientY) })

```

### Equation between Observer, Iterator and Observable
Iterator pattern

Iterator.hasNext()
Iterator.next()
https://media.pragprog.com/titles/smreactjs/code/ch1/iterator.js

  
Rx pattern = Observer pattern + Iterator pattern

* The Observable sequence, or simply Observable is central to the Rx pattern. 
* An Observable emits its values in order—like an iterator
* But instead of its consumers requesting the next value, the Observable “pushes” values to consumers as they become available. 
* It has a similar role to the Producer’s in the Observer pattern: emitting values and pushing them to its listeners.

*In Rx, Observable is one Data Type to Rule Them All  (Example: Merge events with Array, convert Array into Obserable)*


### Difference with traditional Observer and Observable

* An Observable doesn’t start streaming items until it has at least one Observer subscribed to it.
* Like iterators, an Observable can signal when the sequence is completed.

**Observables don’t do anything until at least one Observer subscribes to them. **


#### How to create Obserable
```Javascript
​   ​var​ observer = Rx.Observer.create(
​     ​function​ onNext(x) { console.log(​'Next: '​ + x); },
​     ​function​ onError(err) { console.log(​'Error: '​ + err); },
​     ​function​ onCompleted() { console.log(​'Completed'​); }
​   );
```

```Javascript
​   ​function​ get(url) {
​     ​return​ Rx.Observable.create(​function​(observer) {
​       ​// Make a traditional Ajax request​
​       ​var​ req = ​new​ XMLHttpRequest();
​       req.open(​'GET'​, url);
​   
​       req.onload = ​function​() {
​         ​if​ (req.status == 200) {
​           ​// If the status is 200, meaning there have been no problems,​
​           ​// Yield the result to listeners and complete the sequence​
​           observer.onNext(req.response);
​           observer.onCompleted();
​         }
​         ​else​ {
​           ​// Otherwise, signal to listeners that there has been an error​
​           observer.onError(​new​ Error(req.statusText));
​         }
​       };
​   
​       req.onerror = ​function​() {
​         observer.onError(​new​ Error(​"Unknown Error"​));
​       };
​   
​       req.send();
​     });
​   }
​   
​   ​// Create an Ajax Observable​,Nothing happens without subscriber, No request sent to remote server
​   ​var​ test = get(​'/api/contents.json'​);

​​   ​//With Subscriber, real usage..
    Rx.DOM.get(​'/api/contents.json'​).subscribe(
​     ​function​ onNext(data) { console.log(data.response); },
​     ​function​ onError(err) { console.error(err); }
​   );
```

```Javascript
​   ​var​ Rx = require(​'rx'​); ​// Load RxJS​
​   ​var​ fs = require(​'fs'​); ​// Load Node.js Filesystem module​
​   
​   ​// Create an Observable from the readdir method​
​   ​var​ readdir = Rx.Observable.fromNodeCallback(fs.readdir);
​   
​   ​// Send a delayed message​
​   ​var​ source = readdir(​'/Users/sergi'​);
​   
​   ​var​ subscription = source.subscribe(
​     ​function​(res) { console.log(​'List of directories: '​ + res); },
​     ​function​(err) { console.log(​'Error: '​ + err); },
​     ​function​() { console.log(​'Done!'​); });
```

```Javascript
var counter = Rx.Observable.interval(1000);
var disposable  = counter.subscribe( (i) => console.log(i) )
disposable.dispose()
````

### From Promise to RX
```Javascript
var p = Promise.resolve(3)
Rx.Observable.fromPromise(p).subscribe( i => console.log(i))
```

## Three choices of error Handling
* onError, Survives only one error, onComplete will no be invoked
* catch operator,  Multiple error can be handled, but error will be part of actual data (instead of onError)
* retry operator, N number of errors are tolerated, without input, it would become infinity

```Javascript
//create promise that immediately rejects
const myBadPromise = () => new Promise((resolve, reject) => reject('Rejected!'));
//emit single value after 1 second
const source = Rx.Observable.timer(1000);
//catch rejected promise, returning observable containing error message
const example = source.flatMap(() => Rx.Observable
                                       .fromPromise(myBadPromise())
                                       .catch(error => Rx.Observable.of(`Bad Promise: ${error}`))
                                    );
//output: 'Bad Promise: Rejected'
const subscribe = example.subscribe(val => console.log(val));
```