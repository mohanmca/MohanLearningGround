* cd C:\Users\mohan\git\MohanLearningGround\src\main\idris
* idris -c average.idr

--
# Notes
* Nat —An unbounded unsigned integer type. This is very often used for sizes and indexing of data structures, because they can never be negative. a Nat can only be subtracted from a larger Nat.
* Idris supports lazy evaluation using explicit types
* In Idris, types to help us write programs, rather than to use programs to help us infer types! Hence  omitting type declarations is not possible.
* Partial application is common in Idris programs, currying supported like Haskell
* Any name that appears in a type declaration, begins with a lowercase letter, and is otherwise undefined is assumed to be a variable. Restrain care before call them as type variables
* With dependent types, variables in types don’t necessarily stand for only as type variables
* Infix operators in Idris aren’t a primitive part of the syntax, but are defined by functions. Putting operators in brackets, as with (+), (==), and (<=) in the REPL example, means that they’ll be treated as ordinary function syntax. 
* Infix operators can also be partially applied using operator sections: (< 3) gives a function that returns whether its input is less than 3.


---

Observation

* Try below in REPL
```Idris
map length $  words "Mohan test"
:let x = 100
:let ws = words "Mohan test"
cast integerval + doubleval
substr 6 5 "Hello world"
100 /= 99 --True : Bool
:t the
:t (+)
:t (==)
```
