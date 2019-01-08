* Fixed point
  * c is a fixed point of the function f(x) if f(c) = c.
  * if c is a fixed point, wherever you pass f(c), you could just pass c. (Stopy applying function)
  * Below someCurve has fixed point of 2
  * f(x) => x^2 -x*x + 4
  * ```const someCurve = (n) =>  n ^2 - (3 * n) + 4```
* Fixed point - YCombinator
  * Ycombinator: ![alt text][ycombinator]


```js
const y = function(le) {
    return function(f) {
        return f(f);
    }(function(f) {
        return le(
            function(x) { return (f(f))(x); }
        );
    });
};
const makeFact = function(givenFact) {
    return function(n) {
        if( n < 2 ) return 1;
        else return n * givenFact(n-1);
    }
};
const fact = y(makeFact);
console.log('Factorial ' + fact(5)); // Outputs 120
```
[ycombinator]: ../img/ycombinator.svg "Ycombinator"  