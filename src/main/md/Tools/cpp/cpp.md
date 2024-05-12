## Where to run Cpp
1. [https://godbolt.org/](https://godbolt.org/)

## What is hello world program in CPP?

```c++
#include <iostream>

using namespace std;
int main(void) {
  cout << "It's working\n" << endl;
}
```

## What is the difference between reference and pointer
1. Reference is similar to pointer, but we don't need prefix * (such as *p)
2. Reference can't be made to refer to a different object after its initialization
3. References are useful to specify function arguments
4. “Functions taking const references are very common.” (good practice)
5. We can't have uninitialized reference

## Assignment
1. = assignment operation is simple copy operation in C++ (not in java)
2. “After x=y, we have x==y for every built-in type and well-designed user-defined type (Chapter 2) that offers = (assignment) and == (equality comparison).”

## Initialization
1. For almost all the type, effect of reading from or writing to an uninitialized variable in undefined
2. int& r; //Error: Uninitialized reference
3. “The basic semantics of argument passing and function value return are that of initialization”

## What is nullptr?
1. It is singleton
2. When pointer needs to dereference from the object that is pointing to

## Coverting C++ into a program
1. g++ -S main.cpp -o main.s (Produces human readable assembly code)
2. g++ -c main.cpp -o main.o (Produces object file)
3. g++ main.cpp -o main (Produces executable using compiles, assembles and links)
  1. Linking the library is done by the linker, which is called by the compiler.
  1. During linking the linker will resolve the symbols in the object files and libraries.
6. g++ --std=c++11 main.cpp -o main (Use C++11 standard)

## Data types
1. 3E8 == 300,000,000 = 3 x 10^8
2. Plancks constant - 6.62607E-34
3. Divide by zero might cause - compilation error, runtime error or some message at runtime.
   4. Division by 0 invokes undefined behaviour
4. Operators of larger (higher) priority perform their operations before the operators with lower priority.
5. Binding, C++ mostly left binding. 

## What is simplest C++ program that processes command line String

```c++
#include <iostream>
#include <vector>
#include <string>

int main(int argc, char** argv) {
    std::vector<std::string> arguments;

    for (int i = 0; i < argc; ++i) {
        arguments.push_back(argv[i]);
    }

    for (const std::string& arg : arguments) {
        std::cout << arg << std::endl;
    }

    return 0;
}
```

## Tidbits about C++

1. in C++, all strings are considered as arrays
2. Char is just subset of number, that would be treated as alphabets. ASCII mapping.
3. In ASCII, --- space is 32, A = 65, A+___ (space) = a, 97
4. \0 = nul char, represent no-character
5. '\47' = Octal chracter, 39 decimal == '\'', '\x27'
6. you can mix and match int with char and perform arithmatic operation
7.   'a' - ' ', a - 32, 'A' + 32, 'A' + ' '
8. 'uint64_t' {aka 'long unsigned int'}

## Stream has two responsible
1. Converting any types into human readable
2. Transfer them to output device
3. You can use *manipulator* along with stream to transform values
4. hex is one manipulator, changes the output streams properties called *basefield*
5. hex, dec, oct are the other manipulators
6. Manipulators
7. endl is a manipulator

## Manipulator Examples
```C++
int main() {
    int byte = 255;
    cout << hex << byte << endl;
    cout << byte << endl << dec << byte<<endl;
}
```

```c++
#include <iostream>
#include <iomanip>
using namespace std;
 
int main()
{
    float PI = 3.14;
    int num = 100;
    cout << "Entering a new line." << endl;
    cout << setw(10) << "Output" << endl;
    cout << setprecision(10) << PI << endl;
    cout << setbase(16) << num << endl; //sets base to 16
}
```

* The sign << is called the insertion operator.
* The sign >> is called the extraction operator.
* cout keyword is used to print.
* cin keyword is used to take input at run time.

## Why to use curly-brace-delimiter-initializer-list
1. It saves from coversions that lose information
2. int i2 {7.8} -- would compilation failure, whereas int i2=7.8; would work

## Find SQRT of a float

```C++
#include <iostream>
#include <cmath>

using namespace std;

int main(void) {
        float value, squareroot;

        cout << "Give me a number and I will find its square root:" << endl;
        cin >> value;
        
	        squareroot = sqrtf(value);
	        cout << "You have given: " << value << endl;
	        cout << "The square root is: " << squareroot << endl;
        
        return 0;
}
```

## datatype modifers to reduce memory or increase width of the number
1. modifiers such as long/short/unsigned would either decrease the memory usage of primitivie-data-type such as int, or increase to suit larger number
2. long int == long, short int = short, unsigned int = unsigned
3. char == [-128 to 127], unsigned char == 0 255
4. long can be declared in 3 days
	5. literal value goes beyond the acceptable range of type int; 98l or 987L (suffix L)
 6. short modifer never works with float, but `long float` would work, 'long float == double'
 7. double can store larger number and with better accuracy
 8. float would store 8 digits after decimal point for accuracy, double would store 15-17 accuracy
 9. numerical anomaly - is unavoidable

## boolean
1. bool developer_is_hubgry =  true
2. boolean is like int, but it uses only 8-bit (1 - byte)
3. zero == false, non-zero == true

## empty for-loop
1. when data missing, c++ assumes 1 is there, hence below is infinite;
```c++
for( ; ; ) {
  /* the body goes here */ 
}
```

## Logical opertion on it
```
C++ also uses - two's complement numbers
i: 00000000000000000000000000001111
j: 00000000000000000000000000010110
int log = i && j;
log:00000000000000000000000000000001
int bit = i & j;
bit:00000000000000000000000000000110
```

## Unsigned CPP (double the range)
```
unsigned int myUnsignedVariable = 0;
myUnsignedVariable--;  // Decrementing from 0
It will wrap around, and the result will be the maximum value representable by an unsigned int. For a 32-bit unsigned int, the maximum value is 4294967295.
```

## Switch case
1. Missing break is big mistake
2. Value after the case must not be an expression containing variables or any other entities whose values aren't known at compilation time;
```
switch(i) {
    case 1: cout << "Only one?" << endl; break;
    case 2: cout << "I want more" << endl; break;
    case 3: 
    case 4: cout << "OK and work for 3 Or 4" << endl; break;
    default: cout << "Don't care"  << endl;
}
```

## What is CRTP?

```cpp
template<class Z> class Y {};
class X : public Y<X> {};
```

## What is shared smart pointer and how to create?
```
In this example, std::make_shared<MyClass>(10) creates a new instance of MyClass with 10 as the argument to its constructor, 
and returns a std::shared_ptr that owns the newly created object. 
The std::shared_ptr will automatically delete the object when there are no more std::shared_ptr objects pointing to it.
```
```cpp
#include <memory>

class MyClass {
public:
    MyClass(int val) : value(val) {}
    int value;
};

int main() {
    std::shared_ptr<MyClass> ptr = std::make_shared<MyClass>(10);
    return 0;
}
```

## How to get the address of the underlying object of the smart pointer?

```bash
int main() {
    std::shared_ptr<MyClass> ptr = std::make_shared<MyClass>(10);
    MyClass* raw_ptr = ptr.get();
    return 0;
}
```

## Source of bugs
1. implicit narrowing conversion
2. Union
3. Array Decay

## Many more
1. int vector[5] = {0,1,2,3,4}; //array with initializaion
2. If you provide fewer values than the size of an array, like this, nothing bad will happen.
  3. he compiler determines that those elements you didn‘t specify any value to should be set to 0.
  4. int vector[5] = {0,1,2};
  5. int vector[] = {0,1,2,3,4,5,6}; //this is legal
  6. int chessboard[8][8];
4. fill first 100 slots with -1 for an array - ```std::fill_n(array, 100, -1);```

## usual_arithmetic_conversions
[Usual arithmetic coversion](https://en.cppreference.com/w/cpp/language/usual_arithmetic_conversions)

## Pure function and const, consteval and constexpr
1. const at runtime, wheras constexpr is during compiletime, const-expr places value in read-only memory
2. Function can also be declared with constexpr (it has to evaluate during compile time, otherwise it would error)
  1. ```“constexpr double square(double x) { return x*x; }”```
  1. ```“consteval double square(double x) { return x*x; }”``` //function that needs to eval'ed only during compilation time
3.  *Pure Function*
  3.1. Function declared with constexpr and consteval are C++ version of "pure function", they cannot have side effects
  3.2. Pure function can only operate within the arguments given, they can't modify non-local variable
4. size of the array must be constant expression

## Auto for-loop

```cpp
int main() {
for(const auto x: {1,2,3,4,5})
  cout << x << "\n";
}

int main() {
for(const auto& x: {1,2,3,4,5})
  cout << x << "\n";
}
```

## If statement introduced varaible, and check/test
1. Variable is available in both the block (if and else)
2. We can use shortcut when things are not zero
```cpp
if (auto n = v.size(); n!=0) {
	// ... we get here if n!=0 ...
}
if (auto n = v.size()) {
	// ... we get here if n!=0 ...
}
```

## What are disadvantages of header.h include file mechanics?

There are four disadvantages

1. Compilation time: If you #include header.h in 101 translation units, the text of header.h will be processed by the compiler 101 times.
2. Order dependencies: If we #include header1.h before header2.h the declarations and macros (§19.3.2.1) in header1.h might affect the meaning of the code in header2.h. If instead you #include header2.h before header1.h, it is header2.h that might affect the code in header1.h.
3. Inconsistencies: Defining an entity, such as a type or a function, in one file and then defining it slightly differently in another file, can lead to crashes or subtle errors. This can happen if we – accidentally or deliberately – declare an entity separately in two source files, rather than putting it in a header, or through order dependencies between different header files.
4. Transitivity: All code that is needed to express a declaration in a header file must be present in that header file. This leads to massive code bloat as header files #include other headers and this results in the user of a header file – accidentially or deliberately – becoming dependent on such implementation details.”

## What are the advantages of Modules
1. A module is compiled once only (rather than in each translation unit in which it is used).
2. Two modules can be imported in either order without changing their meaning.
3. If you import or #include something into a module, users of your module do not implicitly gain access to (and are not bothered by) that: import is not transitive.

## Role of using directive


1. A using-declaration makes a name from a namespace usable as if it were declared in the scope in which it appears. After using std::swap, it is exactly as if swap had been declared in current scope.
2. A using-directive makes unqualified names from the named namespace accessible from the scope in which we placed the directive. 
3. So after the using-directive for std, we can simply write cout rather than std::cout.
4. 
```cpp
export module vector_printer;
import std;
using namespace std;

export
template<typename T>
void print(vector<T>& v) // this is the (only) function seen by users
{
        cout << "{\n";
        for (const T& val : v)
                cout << " " << val << '\n';
        cout << '}';
}
```


## Reference
1. [cpp.sh](https://cpp.sh)
2. [https://ideone](https://ideone.com/HmZVro](https://ideone.com/HmZVro)
3. [Operator Precedence](https://en.cppreference.com/w/cpp/language/operator_precedence)

## Generate MdAnki
```bash
mdanki cpp.md cpp.apkg --deck "Mohan::DeepWork::cpp"
```
