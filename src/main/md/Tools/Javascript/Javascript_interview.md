## Most often asked interview questions

1. [Javascript Questions](https://github.com/ganqqwerty/123-Essential-JavaScript-Interview-Questions)
2. [300-JS](https://github.com/bgoonz/DEV-RESOURCES/tree/master/STAGING/INTERVIEW-PREP-COMPLETE-master/INTERVIEW-PREP-COMPLETE-master/interview-prep/300-react-q-and-as)
3. [FrontEnd-Summary-JS](https://github.com/bgoonz/DEV-RESOURCES/blob/master/STAGING/INTERVIEW-PREP-COMPLETE-master/INTERVIEW-PREP-COMPLETE-master/SUMMARY.md)

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