## Generic programming or Template Programming or Meta-Programming

1. 
```cpp
  Cup<T>
  Cup<Cofee>
```
2. Iterator is also part of Generic programming
3. Writing programming that manipulate other or same programs as if they were data - Anders Hejlsberg

## C++ Definitions of template
1. Family of classes
2. Family of functions

## Example programming challenge
1. max(double, double)
2. max(int, int)
3. max(float,float)
4. abs, labs, llabc=s, fabs, fabsl

## Function Templates
```cpp
template <typename T>
T abs(T x) {
  return (x>=0) ? x : -x;
}
```
1. Function templates are not functions
2. If nobody calls abs<int>, it won't be instantiated by the compiler at all.   
