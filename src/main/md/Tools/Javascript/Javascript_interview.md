## Most often asked interview questions

1. [Javascript Questions](https://github.com/ganqqwerty/123-Essential-JavaScript-Interview-Questions)
2. [300-JS](https://github.com/bgoonz/DEV-RESOURCES/tree/master/STAGING/INTERVIEW-PREP-COMPLETE-master/INTERVIEW-PREP-COMPLETE-master/interview-prep/300-react-q-and-as)
3. [FrontEnd-Summary-JS](https://github.com/bgoonz/DEV-RESOURCES/blob/master/STAGING/INTERVIEW-PREP-COMPLETE-master/INTERVIEW-PREP-COMPLETE-master/SUMMARY.md)

## ES2018 - Reference

* [The Modern JavaScript Tutorial](https://javascript.info/intro)
* [A re-introduction to JavaScript (JS tutorial)](https://developer.mozilla.org/en-US/docs/Web/JavaScript/A_re-introduction_to_JavaScript)
* [Object rest and spread properties](https://developers.google.com/web/updates/2017/06/object-rest-spread)
* [Promise.prototype.finally](https://developers.google.com/web/updates/2017/10/promise-finally)
* [Async iterators and generators](https://jakearchibald.com/2017/async-iterators-and-generators/)
* [Here are examples of everything new in ECMAScript 2016, 2017, and 2018](https://medium.freecodecamp.org/here-are-examples-of-everything-new-in-ecmascript-2016-2017-and-2018-d52fa3b5a70e)
    * [Hacker news comments](https://news.ycombinator.com/item?id=16743765)


## How to reverse String

``[...'Mohan'].reverse().join('')'``

## Others - html
1. [] + {} --- zero, empty string, object - based on the order and combination, it would change
2. document.body.contentEditable = true   --- To make webpage editatble
3. 

## IIFE

```JavaScript
var counter = (function () {
    var i = 0;

    return {
        get: function () {
            return i;
        },
        set: function (val) {
            i = val;
        },
        increment: function () {
            return ++i;
        }
    };
})();
```

## How to define Person (can be invoked without using new)

```java
function makePerson(first, last) {
  return {
    first: first,
    last: last,
    fullName: function() {
      return this.first + ' ' + this.last;
    },
    fullNameReversed: function() {
      return this.last + ', ' + this.first;
    }
  };
}

var s = makePerson('Simon', 'Willison');
s.fullName(); // "Simon Willison"
s.fullNameReversed(); // "Willison, Simon"
```

## How to define Constructor Person (can be invoked using new)

```java
function Person(first, last) {
  this.first = first;
  this.last = last;
  this.fullName = function() {
    return this.first + ' ' + this.last;
  };
  this.fullNameReversed = function() {
    return this.last + ', ' + this.first;
  };
}
var s = new Person('Simon', 'Willison');
```

## What is the disadvantage in above Person

1. Code is duplicated to all the object
2. More memory
3. Using Prototype, we can reduce memory

## Define Person with Prototype method

```java
function Person(first, last) {
  this.first = first;
  this.last = last;
}
Person.prototype.fullName = function() {
  return this.first + ' ' + this.last;
};
Person.prototype.fullNameReversed = function() {
  return this.last + ', ' + this.first;
};
```

## Give an example to use call method

```java
function lastNameCaps() {
  return this.last.toUpperCase();
}
var s = new Person('Simon', 'Willison');
lastNameCaps.call(s);
// Is the same as:
s.lastNameCaps = lastNameCaps;
s.lastNameCaps(); // WILLISON
```

## How to list all the parameters of global scope

1. you can't pass const arguments in javascript as parameter
```javascript
const visited = new Set()
const queue = [];

function listValues() {
    const [O, i, ...rest] = arguments;
    console.log(arguments[1])
    if (visited.has(O)) return;
    visited.add(O);
    for (let p in O) {
        if (typeof(O[p]) === 'object' && O[p] !== O) {
            listValues(O[p], i + 1);
        } else if (O[p] === O) {
            console.log("cyclic reference   " + typeof(O[p]) + " **~~~> " + p + " : " + " --> " + O);
        } else if (O[p] === 'function') {
            console.log(i + "   " + typeof(O[p]) + " **~~~> " + p + " : " + " --> " + O[p]);
        } else {
            console.log(i + "   " + typeof(O[p]) + " --> " + p + " : " + " --> " + O[p]);
        }
    }
}
```

## ReferenceError vs Undefined

```java
function example() {
  let declaredButNotAssigned;
  console.log(declaredButNotAssigned); // => undefined
  declaredButNotAssigned = true;
}

// using const and let
function example() {
  console.log(declaredButNotAssigned); // => throws a ReferenceError
  console.log(typeof declaredButNotAssigned); // => throws a ReferenceError
  const declaredButNotAssigned = true;
}
```