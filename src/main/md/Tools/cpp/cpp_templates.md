## [Generic programming or Template Programming or Meta-Programming](https://www.ipb.uni-bonn.de/html/teaching/modern-cpp/slides/lecture_9.pdf)

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

## Template keywords
1. Classes templates are not classes. They are templates for making classes
2. Don’t pay for what you don’t use: If nobody calls MyClass<int>, it won’t be instantiated by the compiler at all.
3. Template Parameters - Think the template parameters the same way as any function arguemnents, but at compile-time.
4. Every template is parameterized by one or more template parameters: template < parameter-list > declaration
5. Type deduction for function templates might mislead us to believe some of function calls are not template functions? (It is!)
6. 

## Function Templates
```cpp
template <typename T>
T abs(T x) {
  return (x>=0) ? x : -x;
}
```
1. Function templates are not functions
2. If nobody calls abs<int>, it won't be instantiated by the compiler at all.   

## Function Template sample code
```cpp
template <typename T>
void Foo() {}
int main() {
  Foo<int>(); //comment and check in compiler explorer
  Foo<double>(); //comment and check in compiler explorer
}
```

## Multiple types
```cpp
template <typename T, typename S>
T awesome_function(const T& var_t, const S& var_s) {
  T result = var_t;
  return result;
}
```

## Templates can have number or default arg
```cpp
template <typename T, size_t N = 10> T
AccumulateVector(const T& val) {
  std::vector<T> vec(val, N);
  return std::accumulate(vec.begin(), vec.end(), 0);
}
using namespace std;
int main() {
  cout << AccumulateVector(1) << endl;
  cout << AccumulateVector <float >(2) << endl;
  cout << AccumulateVector<float, 5>(2.0) << endl;
  return 0;
}
```
