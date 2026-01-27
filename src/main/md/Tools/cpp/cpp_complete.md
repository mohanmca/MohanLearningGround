# C++ Complete Knowledge Base

## Table of Contents
1. [Learning Resources & Courses](#learning-resources--courses)
2. [C Language Fundamentals (NPTEL)](#c-language-fundamentals-nptel)
3. [C++ Fundamentals](#c-fundamentals)
4. [Templates & Generic Programming](#templates--generic-programming)
5. [Concurrency](#concurrency)
6. [Build System & Ecosystem](#build-system--ecosystem)
7. [Code Coverage](#code-coverage)
8. [Sample Code & Examples](#sample-code--examples)
9. [CppCon & Video Resources](#cppcon--video-resources)
10. [References](#references)

---

## Learning Resources & Courses

### Where to run Cpp
1. [https://godbolt.org/](https://godbolt.org/)
2. [Hacking CPP](https://hackingcpp.com/cpp/cheat_sheets.html)
3. [CPP-Cling](https://root.cern/cling/)

### Missing lectures
1. [https://missing.csail.mit.edu/](https://missing.csail.mit.edu/2019/)
1. [Software Carpentry](https://software-carpentry.org/lessons/)

### CPP Lectures
1. [C++ University Lectures and notes](https://www.ipb.uni-bonn.de/teaching/cpp-2020/lectures/index.html)
2. [C++ Bonn Lectures and combined notes](https://github.com/mohanmca/MohanLearningGround/blob/master/src/main/resources/uni-bonn-de-teaching-modern-cpp-2020-lecture.pdf)
3. [Systems Programming in C++](https://db.in.tum.de/teaching/ss23/c++praktikum/slides/lecture.pdf?lang=en)
4. [Systems Programming in C++- Combined notes](https://github.com/mohanmca/MohanLearningGround/blob/master/src/main/resources/SystemsProgramminginC%2B%2BMunich.pdf)
5. [Systems Programming in C++ with video recordings](https://db.in.tum.de/teaching/ss21/c++praktikum/index.shtml?lang=en)

### CPP Test
1. [Linked In Assesment for C++](https://linkedin-faq.github.io/skill-assessments-quizzes/quiz/c%2B%2B)
1. [google.com/edu/c++ quiz](https://developers.google.com/edu/c++/quiz)
2. [CPP Standard Quiz](https://cppquiz.org/)
3. [C++ Chapter wise test](https://test.sanfoundry.com/cplusplus-programming-tests/)
4. [C++ Test](https://picake314.github.io/cppishard/#/)
5. [CPP Quizes](https://www.reddit.com/r/cpp/comments/fcqruv/c_quizzes/)
6. [Edabit](https://edabit.com/)
7. [C++ Chapterwise quiz](https://www.cprogramming.com/quiz/)
8. [Hard to crack C++ Quiz](https://pvs-studio.com/en/blog/quest/kushnirenko_quiz/)

### CPP Courses
1. [Modern-CPP-Programming](https://github.com/federico-busato/Modern-CPP-Programming?tab=readme-ov-file)
1. [Modern CPP overview](https://oleksandrkvl.github.io/2021/04/02/cpp-20-overview.html)
1. [ISO Guidelines](https://isocpp.github.io/CppCoreGuidelines/CppCoreGuidelines#Rc-zero)
1. [C++ FAQ](https://yosefk.com/c++fqa/)
1. [Fastest Modern CPP book](https://changkun.de/modern-cpp/pdf/modern-cpp-tutorial-en-us.pdf)

### CPP - Design Patterns
1. [Singleton](https://refactoring.guru/design-patterns/singleton/cpp/example)
2. [Strategy](https://refactoring.guru/design-patterns/strategy/cpp/example)
3. [Iterator](https://refactoring.guru/design-patterns/iterator/cpp/example)
4. [Template method](https://refactoring.guru/design-patterns/template-method/cpp/example)
5. [Prototype](https://refactoring.guru/design-patterns/prototype/cpp/example)

### Mastering CPP
1. [Entire game engine coded live](https://handmadehero.org/)
2. [Everything about CPP hacking](https://hackingcpp.com/cpp/blogs.html)

### [Low latency C++ Exchange Thread](https://news.ycombinator.com/item?id=40908273)
1. [Review comments about queue and code](https://news.ycombinator.com/item?id=40908273)

### [CPLUSPLUS Coursera Crypto Course](https://www.coursera.org/learn/cplusplus-crypto-i)

#### Standard IO
1. It is generally a good idea to use `endl` only when flushing the stream would be a feature and '\n' when it would not flush.

#### Course-1 Reading - C++ std::cin; operators
1. https://www.cplusplus.com/doc/tutorial/basic_io/
1. https://en.cppreference.com/w/cpp/io/cin
1. https://www.cplusplus.com/doc/tutorial/operators/

#### Course-1 Reading - C++ While loops and exercise
1. https://beginnersbook.com/2017/08/cpp-while-loop/
1. https://www.learncpp.com/cpp-tutorial/intro-to-loops-and-while-statements/
1. https://zetcode.com/cpp/while/

#### Course-1 Reading - C++ Functions and exercise
1. https://www.tutorialspoint.com/cplusplus/cpp_functions.htm
1. https://www.cplusplus.com/doc/tutorial/functions/

#### Course-1 Reading - C++ Data read in from a text stream, binary and text modes:
1. https://en.cppreference.com/w/cpp/io/c/FILE

#### Course-1 Reading -  Bash information can be found in this handy guide on the Linux Documentation Project:
1. https://www.tldp.org/LDP/Bash-Beginners-Guide/Bash-Beginners-Guide.pdf
1. https://tldp.org/LDP/Bash-Beginners-Guide/html/index.html

#### Exchange Concepts
1. Order book
2. TradingPair
3. Asks : Ask for money, offers to sell asset
4. Bidding : Offers money, ready to buy asset
5. Wallet
6. Matching Engine

### [Programming In Modern C++ | NPTEL](https://onlinecourses.nptel.ac.in/noc24_cs44/preview)
1. PROF. PARTHA PRATIM DAS
2. [Course Videos and slides](http://www.nitttrc.edu.in/nptel/courses/video/106105234/106105234.html)
3. [C++](https://www.youtube.com/watch?v=mMswJnSDepc)

### [Learn CPP by Example](https://cppbyexample.com/)
1. [CPP by example](https://cppbyexample.com/)

---

## C Language Fundamentals (NPTEL)

### Basic C Program

```c
#include <stdio.h>

int main() {
  printf("Hello world\n");
  return 0;
}
```

### Basics of C
1. By default it returns 0, unless we return something explicit
2. char/int/foat/double/_Bool
3. void : is a special type, it indicates 'no type', we can use in many places except parameter to function
  1. when function has nothing to return, use void
  2. Function to memory location that has nothing to point, then we can use (void*), later we can cast to any other pointer-type such as int*
  3. malloc function uses void*, and malloc is important function
4. Derived types are
  1. Array, Structure (struct & union), Pointer, Function, String (C-Strings are not a type)
5. Type modifier
  1. short, long, signed and unsigned
6. Literals refer to fixed value of built-in-type
  1. 0173 //octal value; "Hello" //(char *) String literal
7. C Programs are either expression or statement
  1. lieral, variable, 1, 2 ... connected by operator is an expression, a function call is an
8. Statement has no-value but specific action
  1. if, if-else, goto, while, do-while, break, return, switch, for, continue are all statement
  2. pair of { and } or block is a compound statement


### C Operators
1. sizeof, &, * ?: . ,
2. unary, binary and ternary operators (?:)
3. Operator Precedence
  1. Left-to-Right
  2. Highest-to-Lowest
3. Operator has two kinds of associativity
4. Left/Right associativity =  (a = b = c ==> (a=(b=c)) // assignment operator is right associatity
5. We can use () parenthesis to alter/control associativity


### Examples of C program snippets
```c
int a[10]; double d, dist_in_ly; unsigned int count;
char* name[]={"Partha", "Pratim", "Das"} // Array size is 3
int i=10; int *p = &i; *p= 30;
int primes[5] = {2,3} // last 3 elements set to 0
int mat[3][4] // two dimensional, Arrays are row-major
```

### Containers and Pointers
1. Array - indexed container
2. Structure - Container for one or more members
  1. All the members are accessed by member name
  2. Union - special type of strucuture to support variant types (only one out of all the members)
3. C - two types of addressing
  1. indexed vs referential
  2. Referential - address of variable - can be stored and manipulated

### Structure (two ways to declare)

```c
struct Complex {
  double re;
  double im;
} c;

strct Complex x = {2.0, 3.0};
strct Complex x = {2.0}; //initialize only first member
```
1. strcut - is called - tag
2. c is variable of type Compex structure

### Strcture using type-def alias
```c
typedef struct _Books {
  char title[50];
  char author[50];
  itn book_id;
} Books;
Books book;
book.book_id = 23;
strcpy(book.title, "C Programming");
```
1. Books is an alias for struct _Books type

### Union - Variant type
```
typedef union _Packet {
  int iData;
  double dDate;
  char cData;
} Packet;
printf("%d\n", p.iData);
Packet p = {10} // initialize only with a value of the type of first member
p.iData = 2;
printf(p.dData);
```
1. When we store smallar data-type, higher data-type bits will be filled with zero
2. You can put some data-type and can access other data
3. Should be very careful

### Pointer
1. Value is a memory address
2. type of pointer is determined by the "type of its pointee"
3. int *ip //pointer to an integer
4. ip = &i;


### Pointer-Array Duality
```c
int a[] = {1,2,3,4,5}
int *p;
p = a;
a[0] = *p;
a[1] = *++p;
a[2] = *(1+p);
int *p =(int *)malloc(sizeof(int))
unsigned char *q = p //Little endian: LSB 1st
*q++;
*q++;
free(p);
```

```c
strct Complex *p = &c;
(*p).re = 2.5;
p->im = 3.6 // Access by redirection p-> == "(*p)."
```

### Function (C)
1. If a function has no parameter, you can invoke with any number of parameters in 'C'
2. if a function has f(void) -- void as parameter, you can't call with parameter, but declaring with void as parameter in function in old style
3. Function declaration without body is supported, parameter names are optional in declaration
  1. ```c
     int funct(int x, int y);
     int funct(int, int);
    ```

### Call and Return by Value
1. Actual parameters vs Formal Parameters
2. Value of an actual parameters are copied to the formal parameters
3. z = funct(a,b) // a, b are actual parameters
4. Call-by-reference
  1. is not supported in C in general
  2. arrays are passed by reference
  3. formal and actual parameters are pointing same in-case of Array


### Function pointers: Delegation of function calls
1. Functions can be pointed by a pointer
``` typedef void(*DrawFunc) (strcut GeoObject);
void drawCir(struct GeoObject go) {
  printf("Rect: (%lf, %lf, %lf, %lf)\n", go.r.x, go.r.y, go.r.w, go.r.h);}
}
void drawRect(struct GeoObject go) {
  printf("Rect: (%lf, %lf, %lf, %lf)\n", go.r.x, go.r.y, go.r.w, go.r.h);}
}
void drawTrg(struct GeoObject go) {
  printf("Rect: (%lf, %lf, %lf, %lf)\n", go.r.x, go.r.y, go.r.w, go.r.h);}
}
DrawFunc DrawArr[] = { drawCir, drawRec, drawTrg };
DrawArr[1](go);
```

---

## C++ Fundamentals

### CPP - Todo
1. CPP - Iterator Invalidation Rules


### What is stack-frame? How is stack used during function invocation?
1. Use this as prompt: In C++, when local variables are stored on the stack within a function, how are they accessed during the function's execution? Do the variables require repeated push and pop operations, or is there a different mechanism used to access them?

### What is hello world program in CPP?
```c++
#include <iostream>

using namespace std;
int main(void) {
  cout << "It's working\n" << endl;
}
```

### Real Errors
1. [Error: pure virtual method called - terminate called without an active exception - Aborted](https://stackoverflow.com/questions/63261761/qt-pure-virtual-method-called-terminate-called-without-an-active-exception)

### Stack, Heap and Segments
1. Stack
   1. Every function call has function-call-frame
   2. Function call frame has all local the variables of a function and arguments of the function
   3. If there is pointer variable, pointer variable may be in local stack, but its target address (memory) may be in heap
2. Heap
   2. Dynamic allocation
   3. calloc / malloc
3. Data segment
   1. global and static variable
4. Code segment  or Text segment
   1. Machine code segment
   2. Also known as text segment

### "=Delete" usage? How to avoid  double deletion or resource leaks?

1. Syntax is used to explicitly disallow certain functions that would otherwise be automatically generated by the compiler.
2. Copy constructor and copy assignment operator are declared as deleted (few times)?
3. This means that the compiler will not allow the ClassName objects to be copied. This is typically done for objects that manage resources (like memory or file handles), where the default shallow copy behavior would lead to problems (like double deletion or resource leaks).
4. By deleting these functions, the author of the class is ensuring that every ClassName object has exclusive ownership over its buffer (m_buffer). This is a common technique in C++ to prevent resource management bugs.

### Why few classes might be using calloc instead of new byte[]?
1. Initialization: calloc initializes the allocated memory to zero, whereas new byte[] does not. This can be useful if you want to ensure all bytes in your buffer are initially zero.
2. Compatibility: calloc is a function from the C standard library. If ClassNames is designed to be compatible with C, using calloc would make sense
3. Error Handling: calloc returns a null pointer if it fails to allocate memory, which can be checked against. In contrast, new byte[] throws an exception if memory allocation fails. Depending on how error handling is done in the rest of the code, one might be preferable over the other.

### What is lvalue and rvalue
* lvalue a lot of time, it is on left side of equal sign (or) value passed to function
  * 10 = i; compiler error, expression must be a modifiable lvalue (here i is lvalue, if int i = 10;
  * int a = i; both a and i are lvalue; considering above line is int i =10;
  * You can't take lvalue reference from rvalue
  * SetValue(int value) //function takes lvalue
  * SetValue(int& value) //function takes lvalue reference
* rvalue a lot of time, it is on right side of equal sign (or) value returned by function
  * rvalue could be literal, int i = 10; 10
  * rvalue could be result of a function (value returned by function)
  * You can't take lvalue reference from rvalue
  * SetValue(int&& value) //function takes rvalue reference
```cpp
int i=10;
```

```cpp
int& GetLValue() {
  static int value = 10;
  return value;
}

int main() {
 GetLValue() = 5; //this would work, due to Lvalue Reference
}
```

### What is the difference between reference and pointer
1. Reference is similar to pointer, but we don't need prefix * (such as *p)
2. Reference can't be made to refer to a different object after its initialization
3. References are useful to specify function arguments
4. "Functions taking const references are very common." (good practice)
5. We can't have uninitialized reference

### Assignment
1. = assignment operation is simple copy operation in C++ (not in java)
2. "After x=y, we have x==y for every built-in type and well-designed user-defined type (Chapter 2) that offers = (assignment) and == (equality comparison)."

### Initialization
1. For almost all the type, effect of reading from or writing to an uninitialized variable in undefined
2. int& r; //Error: Uninitialized reference
3. "The basic semantics of argument passing and function value return are that of initialization"

### What is nullptr?
1. It is singleton
2. When pointer needs to dereference from the object that is pointing to

### Coverting C++ into a program
1. g++ -S main.cpp -o main.s (Produces human readable assembly code)
2. g++ -c main.cpp -o main.o (Produces object file)
3. g++ main.cpp -o main (Produces executable using compiles, assembles and links)
  1. Linking the library is done by the linker, which is called by the compiler.
  1. During linking the linker will resolve the symbols in the object files and libraries.
6. g++ --std=c++11 main.cpp -o main (Use C++11 standard)

### Data types
1. 3E8 == 300,000,000 = 3 x 10^8
2. Plancks constant - 6.62607E-34
3. Divide by zero might cause - compilation error, runtime error or some message at runtime.
   4. Division by 0 invokes undefined behaviour
4. Operators of larger (higher) priority perform their operations before the operators with lower priority.
5. Binding, C++ mostly left binding.

### Why sometime we declare variable with uint32_t instead of int?
1. Guaranteed size of 32 bits
2. Range clarification
3. Portability
4. Explicit intent (avoid signedness and overflow)
5. Adherence to moder practice (MISRA & CERT-C)

### What is simplest C++ program that processes command line String

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

### Tidbits about C++
1. in C++, all strings are considered as arrays
2. Char is just subset of number, that would be treated as alphabets. ASCII mapping.
3. In ASCII, --- space is 32, A = 65, A+___ (space) = a, 97
4. \0 = nul char, represent no-character
5. '\47' = Octal chracter, 39 decimal == '\'', '\x27'
6. you can mix and match int with char and perform arithmatic operation
7.   'a' - ' ', a - 32, 'A' + 32, 'A' + ' '
8. 'uint64_t' {aka 'long unsigned int'}

### Stream has two responsible
1. Converting any types into human readable
2. Transfer them to output device
3. You can use *manipulator* along with stream to transform values
4. hex is one manipulator, changes the output streams properties called *basefield*
5. hex, dec, oct are the other manipulators
6. Manipulators
7. endl is a manipulator

### Manipulator Examples
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

### Why to use curly-brace-delimiter-initializer-list
1. It saves from coversions that lose information
2. int i2 {7.8} -- would compilation failure, whereas int i2=7.8; would work


### datatype modifers to reduce memory or increase width of the number
1. modifiers such as long/short/unsigned would either decrease the memory usage of primitivie-data-type such as int, or increase to suit larger number
2. long int == long, short int = short, unsigned int = unsigned
3. char == [-128 to 127], unsigned char == 0 255
4. long can be declared in 3 days
	5. literal value goes beyond the acceptable range of type int; 98l or 987L (suffix L)
 6. short modifer never works with float, but `long float` would work, 'long float == double'
 7. double can store larger number and with better accuracy
 8. float would store 8 digits after decimal point for accuracy, double would store 15-17 accuracy
 9. numerical anomaly - is unavoidable

### boolean
1. bool developer_is_hubgry =  true
2. boolean is like int, but it uses only 8-bit (1 - byte)
3. zero == false, non-zero == true

### empty for-loop
1. when data missing, c++ assumes 1 is there, hence below is infinite;
```c++
for( ; ; ) {
  /* the body goes here */
}
```

### Logical opertion on it
```
C++ also uses - two's complement numbers
i: 00000000000000000000000000001111
j: 00000000000000000000000000010110
int log = i && j;
log:00000000000000000000000000000001
int bit = i & j;
bit:00000000000000000000000000000110
```

### Unsigned CPP (double the range)
```
unsigned int myUnsignedVariable = 0;
myUnsignedVariable--;  // Decrementing from 0
It will wrap around, and the result will be the maximum value representable by an unsigned int. For a 32-bit unsigned int, the maximum value is 4294967295.
```

### Switch case
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

### What is CRTP?

```cpp
template<class Z> class Y {};
class X : public Y<X> {};
```

### What is shared smart pointer and how to create?
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


### Source of bugs
1. implicit narrowing conversion
2. Union
3. Array Decay

### Many more
1. int vector[5] = {0,1,2,3,4}; //array with initializaion
2. If you provide fewer values than the size of an array, like this, nothing bad will happen.
  3. he compiler determines that those elements you didn't specify any value to should be set to 0.
  4. int vector[5] = {0,1,2};
  5. int vector[] = {0,1,2,3,4,5,6}; //this is legal
  6. int chessboard[8][8];
4. fill first 100 slots with -1 for an array - ```std::fill_n(array, 100, -1);```

### usual_arithmetic_conversions
[Usual arithmetic coversion](https://en.cppreference.com/w/cpp/language/usual_arithmetic_conversions)

### Pure function and const, consteval and constexpr
1. const at runtime, wheras constexpr is during compiletime, const-expr places value in read-only memory
2. Function can also be declared with constexpr (it has to evaluate during compile time, otherwise it would error)
  1. ```"constexpr double square(double x) { return x*x; }"```
  1. ```"consteval double square(double x) { return x*x; }"``` //function that needs to eval'ed only during compilation time
3.  *Pure Function*
  3.1. Function declared with constexpr and consteval are C++ version of "pure function", they cannot have side effects
  3.2. Pure function can only operate within the arguments given, they can't modify non-local variable
4. size of the array must be constant expression

### Auto for-loop

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

### If statement introduced varaible, and check/test
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

### What are disadvantages of header.h include file mechanics?

There are four disadvantages

1. Compilation time: If you #include header.h in 101 translation units, the text of header.h will be processed by the compiler 101 times.
2. Order dependencies: If we #include header1.h before header2.h the declarations and macros (§19.3.2.1) in header1.h might affect the meaning of the code in header2.h. If instead you #include header2.h before header1.h, it is header2.h that might affect the code in header1.h.
3. Inconsistencies: Defining an entity, such as a type or a function, in one file and then defining it slightly differently in another file, can lead to crashes or subtle errors. This can happen if we – accidentally or deliberately – declare an entity separately in two source files, rather than putting it in a header, or through order dependencies between different header files.
4. Transitivity: All code that is needed to express a declaration in a header file must be present in that header file. This leads to massive code bloat as header files #include other headers and this results in the user of a header file – accidentially or deliberately – becoming dependent on such implementation details."

### What are the advantages of Modules
1. A module is compiled once only (rather than in each translation unit in which it is used).
2. Two modules can be imported in either order without changing their meaning.
3. If you import or #include something into a module, users of your module do not implicitly gain access to (and are not bothered by) that: import is not transitive.

### Role of using directive

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

### How many ways we can pass argument?

```cpp
void test(vector<int> v, vector<int>& rv)          // v is passed by value; rv is passed by reference
{
        v[1] = 99;          // modify v (a local variable)
        rv[2] = 66;         // modify whatever rv refers to
}

int main()
{
        vector fib = {1, 2, 3, 5, 8, 13, 21};
        test(fib,fib);
        cout << fib[1] <<'' << fib[2] << '\n';           // prints 2 66
}
```

### When to pass by value or by reference?
1. When we care about performance, we usually pass small values by-value and larger ones by-reference.
2. Here "small" means "something that's really cheap to copy." Exactly what "small" means depends on machine architecture
3. but "the size of two or three pointers or less" is a good rule of thumb. "

### Memory error
1. try to not create pointer for stack variable, prefer to use reference.
2. Don't create pointer of varaible in stack and use that in shared pointer (don't do it)
3. std::unique_ptr(can_take_existing_ptr)

### Is CPP supports default function argument?
* yes!
``` "void print(int value, int base =10);   // print value in base "base"```

### When not to return pointer or reference to a variable?

* a local variable disappears when the function returns, so we should not return a pointer or reference to it:
* Compiler would generally catch below error
```cpp
	int& bad(){
		int x;
		// ...
		return x;  // bad: return a reference to the local variable x
	}
```

### Auto function return type deduction pros and cons
1. This can be convenient
2. Especially for generic functions (§7.3.1) and lambdas (§7.3.3),
3. but should be used carefully because a deduced type does not offer a stable interface:
4. A change to the implementation of the function (or lambda) can change its type.
5. auto mean "the return type will be mentioned later or be deduced."
6. auto mul(int i, double d) -> double { return i*d; }         // the return type is "double"

### Structured Binding
1. "A function can return only a single value, but that value can be a class object with many members. This allows us to elegantly return many values."


### Function
1. Function can't be invoked uless it was already declared
1. ```cpp
    [[attributes]] ReturnType FuncName(ArgumentList...) {
      // Some awesome code here.
      return return_value;
    }
```
1. When function returns void, it is called sub-routine or procedure
1. ARTD - Automated return type deduction
```cpp
auto GetDictionary() {
  return std:map<char, int>{{'a',2}, {'b',7}};
}
```

### Function to return multiple values using tuple > 17
```cpp
#<tuple>
auto GetDictionary() {
  return make_tuple("string", 14);
}
int main() {
  auto [name, value] = GetDictionary();
  cout << name << value << endl;
  return 0;
}
```

### Why most of addresses are shown in 12digit hexa decimal?
1. In most of the systems, only the lower 48 bits (48 bits will have 12 hexadecimal digit) (or even fewer) are used for addressing, while the higher bits are either reserved or fixed.
1. With 48 bits, it could address nearly 262,144 GB of space (217 TB)
1. Rarely even if they use 64bit, leadingzeros are not shown
2. 0x56330247ce70 - How come it shows 12 digit hexa decimal? shouldn't be just 8 digit hexadecimal on64 bit computer?

### In C++, is object names are constant?
* An object name is like a constant, which cannot be reassigned with a new object?

### Copy ellision

### Move constructor

### LValue and RValue
1. Things that are declared as rvalue reference can be lvalues or rvalues. The distinguishing criterion is: if it has a name, then it is an lvalue. Otherwise, it is an rvalue.
2. ```int const r& = 10;```
3. C++ cannot bind a non-const lvalue reference to a temporary object (or rvalue).
   1. ```int& r=10;```  //will fails to compile
5. The expression 10 is a temporary int, and only a const int& can bind to a temporary object.
   1. ```const int& r = 10;```


### Define function that can be called only on LValue or RValue?

```
#include <iostream>

class Example {
public:
    void onlyLvalue() & {
        std::cout << "This function can only be called on an lvalue." << std::endl;
    }

    void onlyRvalue() && {
        std::cout << "This function can only be called on an rvalue." << std::endl;
    }
};

int main() {
    Example e;

    e.onlyLvalue(); // OK, e is an lvalue
    // Example().onlyLvalue(); // Error, temporary object is an rvalue

    // e.onlyRvalue(); // Error, e is an lvalue
    Example().onlyRvalue(); // OK, temporary object is an rvalue

    return 0;
}
```

### Function Arguments
1. use function name as CamelCase
1. use argument name as snake_case
1. Always try to pass arguments as const-pass-by-reference (const std::string& huge_string);


### Namespace
1. Avoid using namespace <name>, local definition might shadow other namespace declaration
1. namespace splits the project into logical unit
1. Never use using namespace name in *.hpp files
1. Prefer using explicit using even in *.cpp files
1. Use using correctly,... "using my_namepspace::myFunc"

### Namless namespace [Stroustrup chapter-014]
1. If you find yourself relying on some constants in a file and these constants should not be see in any other fiile
1. Put them into nameless namespace on the top of this file.

```cpp
namespace {
    const int  kLocal = 13;
    const float kLocalFloat = 13.0f
}
```


### How to force compiler not to generate implicit constructors?

```cpp
class MyClass {
public:
    MyClass() = delete;                      // Delete default constructor
    MyClass(const MyClass&) = delete;        // Delete copy constructor
    MyClass(MyClass&&) = delete;             // Delete move constructor
    MyClass& operator=(const MyClass&) = delete; // Delete copy assignment operator
    MyClass& operator=(MyClass&&) = delete;  // Delete move assignment operator

    // Add your own constructors or methods here
    MyClass(int x) {
        // constructor code
    }
};
```

### static variables
1. static is happens during compile time
1. Dynami is only during run-time
1. Avoid static as much as possible
1. f::counter  has been statically allcoated
```cpp
void Counter() {
    static int counter = 0;
    counter++;
}
f(); // counter = 1
f(); // counter = 2
```


### Function calls
1. It is expensive
1. make it inline if it is small
1. inline is a hint to the compiler
1. [Manipulate function definition with inline](https://godbolt.org/z/EGd6aG)

---

## Templates & Generic Programming

### [Generic programming or Template Programming or Meta-Programming](https://www.ipb.uni-bonn.de/html/teaching/modern-cpp/slides/lecture_9.pdf)
1.
    ```cpp
      Cup<T>
      Cup<Cofee>
    ```
2. Iterator is also part of Generic programming
3. Writing programming that manipulate other or same programs as if they were data - Anders Hejlsberg

### C++ Definitions of template
1. Family of classes
2. Family of functions

### Example programming challenge without templates
1. max(double, double)
2. max(int, int)
3. max(float,float)
4. abs, labs, llabc=s, fabs, fabsl

### Template keywords
1. Classes templates are not classes. They are templates for making classes
2. Don't pay for what you don't use: If nobody calls MyClass<int>, it won't be instantiated by the compiler at all.
3. Template Parameters - Think the template parameters the same way as any function arguemnents, but at compile-time.
4. Every template is parameterized by one or more template parameters: template < parameter-list > declaration
5. Type deduction for function templates might mislead us to believe some of function calls are not template functions? (It is!)

### Function Templates
```cpp
template <typename T>
T abs(T x) {
  return (x>=0) ? x : -x;
}
```
1. Function templates are not functions
2. If nobody calls abs<int>, it won't be instantiated by the compiler at all.

### Function Template sample code
```cpp
template <typename T>
void Foo() {}
int main() {
  Foo<int>(); //comment and check in compiler explorer
  Foo<double>(); //comment and check in compiler explorer
}
```

### Templates Multiple types
```cpp
template <typename T, typename S>
T awesome_function(const T& var_t, const S& var_s) {
  T result = var_t;
  return result;
}
```

### Templates can have number or default arg
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

### Usage of template keyword in invoking method

```cpp
#include <iostream>
#include <string>

template <typename T>
class MyClass {
public:
    template <typename U>
    void myMethod(U arg) {
        std::cout << "Value: " << arg << std::endl;
    }
};

int main() {
    MyClass<int> obj;
    obj.template myMethod<int>(10);        // Accessing template method with int
    obj.template myMethod<double>(3.14);      // Accessing template method with double
    obj.template myMethod<std::string>("Mohan");   // Accessing template method with std::string
    obj.template myMethod<const char*>("Hello");     // Accessing template method with const char*
    return 0;
}
```

### why are we separating template parameters here:
```cpp
template<typename Host> template<typename T, typename F>
template<typename Host> template<typename T, typename F> void Sink<Host>::doSomething(T const& msg, F next) {
//block
}
```
1. It supposed to mean, class Sink takes a template parameter Host, while Sink::do_something take 2 template parameters

### Template usage in using
```
template <typename Handler> using Service = std::shared_ptr<Service<Handler>>;
```
1. C++ is not whitespace or layout sensitive


### What is template function?
1. A template function in C++ is a special kind of function that allows you to write generic and reusable code.
2. Instead of writing separate functions for different data types, you can use a template to define a single function that works with any type.
```cpp
#include <iostream>
template<typename T, std::size_t N>
constexpr std::size_t size(const T(&arr)[N]) noexcept
{
	return N;
}
int main()
{
	constexpr int a[]{ 5,4,3,2,1 };
	std::cout << size(a) << '\n';
	return 0;
}
```

### TODO
1. [Tag dispatching](https://www.fluentcpp.com/2018/04/27/tag-dispatching/)
1. [Tag vs ENum](https://www.fluentcpp.com/2018/05/01/when-to-use-enums-and-when-to-use-tag-dispatching-in-cpp/)
2. [Ranges](https://mariusbancila.ro/blog/2019/01/20/cpp-code-samples-before-and-after-ranges/)
3. [Julian Templeman on Templates](https://learning.oreilly.com/course/diving-deeper-into/9781491988701/)
4. [Julian Templeman on Templates](https://learning.oreilly.com/course/further-exploration-of/9781491988732/)
5. [31 nooby C++ habits you need to ditch ](https://www.youtube.com/watch?v=i_wDa2AS_8w)

---

## Concurrency

### C++ Concurrency in action
1. Multithreadin is added in C++1
2. We can eat and watch tv, walk and talk at the same time while scratching head
3. Task switching is fundamental in olden days
4. All threads in a process share same address space
5. Concurrency vs Parallelism
   6. Focus and Intent
   7. Parallelism - to increase performance of bulk data processing
   8. Concurrency - responsibility separation, responsiveness


### Using concurrency for sepearation of concerns
1. One thread for UI and one thread for DVD decoding and play back
2. Separate task could run behind and produce output later
3. The number of thereads is indepdendent of the number of CPU cores avaialble (when separation of concern is what matters)
4. The division is based on conceptual design rather than an attempt to increase throughput


### Using concurrency for for performance
1. Task and data - due to multiple-cores
2. Free lunch is over, software has to be designed to run multiple process in parallel
3. Embarassingly parallel alogrithms, Naturally parallel and conveniently concurrent

### Thread Overhead
1. OS has to allocate the associated kernel resources
2. stack space
3. new thread to scheduler
4. If the task running is quite light (dwarf) don't use threads...
5. Too many threads ---> Too much memory consumption
6. Each stack has 1MB stack on typical machine
7. Prefer to use threadpools to reduce thread
8. More thread more context switch
9. If clarifty of design improved due to multithreaded, then it might be okay to use despite acceptable performance degrade.

### C++ Concurrency before C++11
1. Rely on compiler extension
2. Rely on C library and wrapped by Boost or ACE
3. Rely on platform API provided by MFC or Windows (internally C API), wrapped using RAII
4. Till C++11, threads were not accepted (and defined) by C++ standard

### C++ 11 specification
1. C++ Memory model
2. C++ STL was extended to include classes for managing threads
3. Protecting shared data
4. Synchronizing operation between threads
5. Low-level atomic operations
6. Based on C++ Boost thread library (primary model)

### C++ 14/17 -  specification
1. C++14 - New mutex type for protecting data
2. A full suite of parallel algorithm
3. Synchronizing operation between threads for functions and classes

### C++ Efficiency in the C++ Thread library
1. Abstraction cost quite low
2. you dont pay for things that you don't use
3. Reduce the contention always
4. Thread library may offer native_handle() member function that allows the underlying implementation to be directly manipulated using platform-specific API. It is not portable


### C++ Concurrency Example

```
#include <iostream>
#include <thread>

int hello() {
   std::cout<<"Hello Concurrent world\n";
}
int main() {
   std::thread t(hello);
   t.join();
}
```

---

## Build System & Ecosystem

### Compilation steps
1. [Modern CPP](https://www.ipb.uni-bonn.de/teaching/cpp-2020/index.html)
1. [Compilation steps](https://www.ipb.uni-bonn.de/html/teaching/modern-cpp/slides/lecture_1.pdf)
1. Ensure build-essentials installed prior to compliing in C/C++

### How to compile using clang?
```bash
clang++ main.cpp
./a.out
```

### What can be replaced with includer header.h
1. It is just copying content of header.h into translation-unit or compilation unit
2. we can just copy paste the content into compilation unit and compiler

### How to debug preprocessor
```cpp
clang++ -E main.cpp > main.i
clang++ -E main.cpp ## to view the content
```

### Does pre-processor removes the comment?
1. Yes, -E flag confirms it

### How to create assembly code from code?
```bash
clang++  -S main.cpp
## we can find main.s file
```

### How to create object file from assembly.s?
```bash
clang++  -c main.s
## we can find main.o file
```

### Libraries types
1. .a - static
2. .so  - dynamic

### Compling using higher standard

```bash
clang++ -std=c++17 program.cpp -o main.o
```

### What is the role of linker
1. fn :: Linker == ([objects], [libraries]) --> executable
2. Takes one are more objects + libraries and produces executable

### How to find names inside object file
1. nm is a utility that displays the symbol table of a given object, executable, or binary file in Linux
```bash
nm -gC tools.o
```

### How to create a static library
```bash
gcc -c tool.cpp -o tool.o
ar rcs tools.a tools.o
```

### Why we use AR command instead of tar to create library?

1. "ar" Designed specifically for creating static libraries.
1. Creates an archive of object files that can be linked into executable programs.
1. Generates symbol tables that are necessary for the linker to resolve symbols.
2. Supports updating and maintaining the archive without re-archiving all files.

### Command to find library named tools in current directory to create exeuctable
```bash
clang++  -std=c++17 -L . -ltools -o main
```

### Build system
1. shell < make
2. CMake -> meta build system, it is not build system
3. CMake + (Make or Ninja) ~~> BuildSystem


### CMakeLists.txt

```CMakeLists.txt
cmake_minimum_required(VERSION 3.1) # Mandatory.
project( first_project ) # Mandatory.
set(CMAKE_CXX_STANDARD 17) # Use c++17.
set (COMPILE_CXX_FLAGS "-Wall")
set(CMAKE_EXPORT_COMPILE_COMMANDS on)
# tell cmake where to look for *.hpp, *.h files
include_directories(include /)

# create library "libtools"
add_library(tools src/tools.cpp) # creates libtools.a from tools.cpp

# add executable main
add_executable(main src/tools_main .cpp) # main.o

# tell the linker to bind these objects together
target_link_libraries(main tools) # ./main
```

1. build/compile_commands.json is exported, that has all the commands

### List of libraries being used
1. [https://github.com/hyperrealm/libconfig](https://hyperrealm.github.io/libconfig/libconfig_manual.pdf)
    1. C/C++ library for processing configuration files
2. [A modern formatting library](https://github.com/fmtlib/fmt)
3. [Fast C++ logging library](https://github.com/gabime/spdlog)
4. [JSON for modern C++](https://github.com/nlohmann/json)
5. [Modern open source C++ FIX framework](https://github.com/fix8/fix8)
6. [GRPC C++](https://github.com/grpc/grpc/tree/master/src/cpp)
7. [SBE C++](https://github.com/real-logic/simple-binary-encoding/wiki/Cpp-User-Guide)
8. [K8S-EKS]
9. [Envoy, Istio, Haproxy, Loadbalancer - learn well]

### Why hyperrealm libconfig being used?
1. Libconfig is a library for reading, manipulating, and writing structured configuration files.
2. More readable and compact than XML and more flexible than the obsolete but prevalent Windows "INI" file format.

### [hyperrealm libconfig features](https://hyperrealm.github.io/libconfig/libconfig_manual.html#Introduction)
1.  name = value; or name: value ;
2.  groups - { settings ... } // but each seettings must have a unique name within the group
3.  Arrays - [ value, value ... ]
4.  Lists - ( value, value ... )
1.  A list may have zero or more elements, each of which can be a scalar value, an array, a group, or another list.
1.  The last element in a list may be followed by a comma, which will be ignored.
5. 64-bit Integer Values - For example, '0L' indicates a 64-bit integer value 0.
6. Boolean Values : 'true' or 'false'
7. Include Directives - @include "quote.cfg"
      ```
         # file: test.cfg
         info: {
           name = "Winston Churchill";
           @include "quote.cfg"
           country = "UK";
         };
      ```

### CPP API
1. #include <libconfig.h++>
1. using namespace libconfig;

---

## Code Coverage

### Cmake command

1. cmake -G Ninja -DCMAKE_COMPILER=g++ -DCMAKE_C_COMPILER=/usr/bin/gcc -DCMAKE_TOOL_CHAIN_FILE=../KDABViewer/coverage.cmake ../KDABViewer
2. When you run cmake -G Ninja.. it essentially means that you are using a build system namely Ninja.
3. Furthermore, the Ninja in cmake -G Ninja.. will generate Ninja build files.
4. The source directory "/../KDABViewer" should contain CMakeLists.txt.

### Gcov
1.[Introduction GCov](https://github.com/vikasnagpaliitd/linux-prog-tools/blob/master/gcov_notes.pdf)
2. Compiler instruments file for coverage report
3. gcovr --root ../service -j 10 --html-details -o html/ .
4. Alternative to gcov is lcob

### .gcda .gcno files
1. compiler generates .gcno file
2. Once we execute the file ./a.out, .gcda file would be created
3. For further execution, .gcda file would be updated
4. To get the output, gcov main.c can be issues, to see the result. During result creation, it creates source.c.gcov file
5. They are binary files not human redable.

---

## Sample Code & Examples

### How to get the address of the underlying object of the smart pointer?

```bash
int main() {
    std::shared_ptr<MyClass> ptr = std::make_shared<MyClass>(10);
    MyClass* raw_ptr = ptr.get();
    return 0;
}
```

### Handle Signal

```cpp
#include <iostream>
#include <csignal>
#include <atomic>
#include <thread>
#include <mutex>

using namespace std;

// Atomic flag to indicate whether the signal was received
atomic<bool> signalReceived(false);

// Mutex to protect shared resources
mutex mtx;

// Signal handler function
void signalHandler(int signum) {
    // Set the atomic flag to true
    signalReceived.store(true, memory_order_relaxed);
}

// Worker thread function
void workerThread() {
    while (true) {
        // Check if the signal was received
        if (signalReceived.load(memory_order_relaxed)) {
            // Lock the mutex before accessing shared resources
            unique_lock<mutex> lock(mtx);

            // Perform any necessary operations here
            cout << "Signal received! Performing cleanup...\n";

            // Reset the signal flag
            signalReceived.store(false, memory_order_relaxed);

            // Release the lock
            lock.unlock();
        }

        // Simulate some work being done
        this_thread::sleep_for(chrono::seconds(1));
    }
}

int main() {
    // Register signal SIGINT and signal handler
    signal(SIGINT, signalHandler);

    cout << "Signal handling example. Press Ctrl+C to trigger SIGINT.\n";

    // Start the worker thread
    thread worker(workerThread);

    // Wait for the worker thread to finish (which it won't)
    worker.join();

    return 0;
}
```

### Find SQRT of a float

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

### When to use Anonymous namespace

```cpp

#include <iostream>

namespace {
    // This variable is accessible only within this translation unit.
    int internalValue = 42;

    // This function is accessible only within this translation unit.
    void internalFunction() {
        std::cout << "Internal function called\n";
    }
}

// Public function
void publicFunction() {
    // Access the internal variable
    std::cout << "Internal value: " << internalValue << std::endl;

    // Call the internal function
    internalFunction();
}

int main() {
    // Call the public function
    publicFunction();

    return 0;
}
```

### How to initialise struct

```cpp
struct MyStruct {
    int a;
    double b;
    char c;
};

// Initializing a struct using aggregate initialization
MyStruct s = {10, 3.14, 'X'};

// Initializing a struct using direct initialization
MyStruct s(10, 3.14, 'X');
```

### How to initialize member varaible in class using Direct Initialization Technique

```cpp
class MyClass {
public:
    MyClass(int a, int b) : member1(a), member2(b) {}
private:
    int member1;
    int member2;
};
```

### How to do pretty print for a class?

```cpp
#include <iostream>

class Point {
public:
    int x, y;
    Point(int x = 0, int y = 0) : x(x), y(y) {}
};

// Overload the operator<< for Point
std::ostream& operator<<(std::ostream& os, const Point& point) {
    os << "Point(" << point.x << ", " << point.y << ")";
    return os;
}

int main() {
    Point p1(3, 4);
    std::cout << p1 << std::endl; // Output: Point(3, 4)
    return 0;
}
```

### Structured binding example
1. The auto [n,v] declares two local variables n and v with their types deduced from read_entry()'s return type. This mechanism for giving local names to members of a class object is called structured binding.
```cpp
struct Entry {
        string name;
        int value;
};

Entry read_entry(istream& is)            // naive read function (for a better version, see §11.5)
{
        string s;
        int i;
        is >> s >> i;
        return {s,i};
}

auto e = read_entry(cin);

cout << "{ " << e.name << " , " << e.value << " }\n";
"auto [n,v] = read_entry(is);
cout << "{ " << n << " , " << v << " }\n";"


map<string,int> m;
// ... fill m ...
for (const auto [key,value] : m)
        cout << "{" << key << "," << value << "}\n";

void incr(map<string,int>& m)        // increment the value of each element of m
{
    for (auto& [key,value] : m)
            ++value;
}
```

### What is the output of this program

```cpp
/**
* Number is: 205
* Extension is: .txt
**/
#include <iomanip>
#include <iostream>
#include <sstream>

using namespace std;

int main() {
    stringstream filename {"00205.txt"};
    int num = 0, n=0;
    string name, ext;
    filename >> num >> ext;
    cout << "Number is: " << num << endl;
    cout << "Extension is: " << ext << endl;
    return 0;
}
```

### What is the output of the following code?

```c
/***
Copy this!
Copy this!
this!
**/
#include <cstring>
#include <iostream>

int main() {
    const char source[] = "Copy this!";
    char dest [5];
    std::cout << source << '\n';
    std::strcpy(dest, source);
    std::cout << dest << '\n';
    // source is const, no problem right?
    std::cout << source << '\n'; return 0;
}
```

### What is the failure?
//Program terminated with signal: SIGSEGV
```cpp
#
#include <cstring>
#include <iostream>

int& MultiplyBy10(int num) {
    int ret = 0;
    ret = 10 * num;
    return ret;

}

int main() {
    std::cout << MultiplyBy10(11) << std::endl;
    return 0;
}
```
Never return reference to local variable

### Pointer to rescue stack space
```
#include <iostream>
#include <cstdlib>
// Type your code here, or load an example.
int square(int num) {
    return num * num;
}

using std::cout;

int main(int argc, char** argv) {
    int size=2;
    int* ptr = nullptr;
    {
        int arr[2] = {2, 3};
        ptr = arr;
    }
    for(int i=0;i<2;i++) cout << ptr[i] << std::endl;
}
```

### Behaviour of shared pointer

```cpp
#include <iostream>
#include <memory> // For std::shared_ptr

class MyClass {
public:
    MyClass() {
        ++instance_count;
        std::cout << "MyClass constructor called! Instance count: " << instance_count << std::endl;
    }

    ~MyClass() {
        --instance_count;
        std::cout << "MyClass destructor called! Instance count: " << instance_count << std::endl;
    }

    void display() const {
        std::cout << "MyClass instance method called! Current instance count: " << instance_count << std::endl;
    }

    static int getInstanceCount() {
        return instance_count;
    }

private:
    static int instance_count;
};

// Define and initialize the static member variable
int MyClass::instance_count = 0;

void useSharedPtr(std::shared_ptr<MyClass> ptr) {
    std::cout << "In function, use count: " << ptr.use_count() << std::endl;
    ptr->display();
}

int main() {
    std::cout << "Initial instance count: " << MyClass::getInstanceCount() << std::endl;

    // Create a shared_ptr instance
    std::shared_ptr<MyClass> ptr1 = std::make_shared<MyClass>();
    std::cout << "After creation, use count: " << ptr1.use_count() << std::endl;
    std::cout << "Instance count: " << MyClass::getInstanceCount() << std::endl;

    {
        // Create another shared_ptr instance that shares ownership with ptr1
        std::shared_ptr<MyClass> ptr2 = ptr1;
        std::cout << "In scope, use count: " << ptr1.use_count() << std::endl;
        std::cout << "Instance count: " << MyClass::getInstanceCount() << std::endl;

        useSharedPtr(ptr2);
        std::cout << "After function call, use count: " << ptr1.use_count() << std::endl;
    }

    std::cout << "Out of inner scope, use count: " << ptr1.use_count() << std::endl;
    std::cout << "Instance count: " << MyClass::getInstanceCount() << std::endl;

    // ptr1 goes out of scope here, and the managed object will be deleted
    return 0;
}
```

### How to parse URL path using STD algorithm in C++

```cpp
#include <iostream>
#include <string>
#include <algorithm>
#include <iterator>

void parse_url(const std::string& url) {
    // Find the protocol using std::search
    auto protocol_end = std::search(url.begin(), url.end(), "://", "://" + 3);
    std::string protocol(url.begin(), protocol_end);

    // Find the host using std::find
    auto host_start = protocol_end;
    if (host_start != url.end()) {
        std::advance(host_start, 3); // Skip "://"
    }
    auto path_start = std::find(host_start, url.end(), '/');
    std::string host(host_start, path_start);

    // Find the path using std::find
    auto query_start = std::find(path_start, url.end(), '?');
    std::string path(path_start, query_start);

    // Find the query
    std::string query;
    if (query_start != url.end()) {
        ++query_start; // Skip '?'
        query = std::string(query_start, url.end());
    }

    // Output the results
    std::cout << "Protocol: " << protocol << std::endl;
    std::cout << "Host: " << host << std::endl;
    std::cout << "Path: " << path << std::endl;
    std::cout << "Query: " << query << std::endl;
}

int main() {
    std::string url = "https://www.example.com/path/to/resource?query=example";
    parse_url(url);
    return 0;
}
```

### Standard way of catching exceptions

```cpp
#define EXPECT_NO_THROW_WITH_DETAIL(stmt) \
    try { \
        stmt; \
    } catch (const std::invalid_argument& e) { \
        std::cerr << "Caught invalid_argument: " << e.what() << std::endl; \
        FAIL() << "invalid_argument thrown: " << e.what(); \
    } catch (const std::out_of_range& e) { \
        std::cerr << "Caught out_of_range: " << e.what() << std::endl; \
        FAIL() << "out_of_range thrown: " << e.what(); \
    } catch (const std::runtime_error& e) { \
        std::cerr << "Caught runtime_error: " << e.what() << std::endl; \
        FAIL() << "runtime_error thrown: " << e.what(); \
    } catch (const std::logic_error& e) { \
        std::cerr << "Caught logic_error: " << e.what() << std::endl; \
        FAIL() << "logic_error thrown: " << e.what(); \
    } catch (const std::exception& e) { \
        std::cerr << "Caught exception: " << e.what() << std::endl; \
        FAIL() << "Exception thrown: " << e.what(); \
    } catch (...) { \
        std::cerr << "Caught unknown exception" << std::endl; \
        FAIL() << "Unknown exception thrown"; \
    }
```

### how to run benchmark?
1. [Sample cpp benchmark](https://quick-bench.com/q/yovU63tEGtde-VjFD1xB2e_VhPY)

---

## CppCon & Video Resources

### Introduction to templates
1. [Templates - Part-I-2021 CPPCon Back2Basics](https://github.com/BobSteagall/CppCon2021/blob/main/Templates/back_to_basics_templates_part_1__bob_steagall__cppcon_2021.pdf)
2. [Templates - Part-II-2021 CPPCon Back2Basics](https://github.com/BobSteagall/CppCon2021/blob/main/Templates/back_to_basics_templates_part_2__bob_steagall__cppcon_2021.pdf)
3. [Templates-I-2022 CppCon2022 Nicolai Josuttis](https://github.com/CppCon/CppCon2022/blob/main/Presentations/CTemplates_cppcon_220918.pdf)
4. [2022 CppCon2022 Nicolai Josuttis](https://github.com/CppCon/CppCon2022/blob/main/Presentations/CTemplates_cppcon_220918.pdf)
5. [2022 CppCon2022 Templates Concepts-Arthur O'Dwyer](https://github.com/CppCon/CppCon2022/blob/main/Presentations/From-Templates-to-Concepts.pdf)
6. [CppCon 2019: Arthur O'Dwyer "Back to Basics: Smart Pointers"](https://www.youtube.com/watch?v=xGDLkt-jBJ4)
7. [Template Metaprogramming: Type Traits (part 1 of 2) - Jody Hagins - CppCon 2020](https://github.com/CppCon/CppCon2020/blob/main/Presentations/template_metaprogramming_type_traits/template_metaprogramming_type_traits__jody_hagins__cppcon_2020.pdf)
8. [Template Metaprogramming: Type Traits (part 2 of 2) - Jody Hagins - CppCon 2020](https://github.com/CppCon/CppCon2020/blob/main/Presentations/template_metaprogramming_type_traits/template_metaprogramming_type_traits__jody_hagins__cppcon_2020.pdf)
9. [CppCon 2016: Arthur O'Dwyer "Template Normal Programming (part 1 of 2)"](https://www.youtube.com/watch?v=vwrXHznaYLA)
10. [CppCon 2016: Arthur O'Dwyer "Template Normal Programming (part 2 of 2)"](https://www.youtube.com/watch?v=VIz6xBvwYd8)
11. [CppCon 2017: Arthur O'Dwyer "A Soupçon of SFINAE"](https://www.youtube.com/watch?v=ybaE9qlhHvw)

### Recommended by colleagues
1. [Modifying gRPC Services Over Time I - Eric Anderson, Google](https://www.youtube.com/watch?v=F2WYEFLTKEw)
1. [CppCon 2015: Greg Law " Give me 15 minutes & I'll change your view of GDB" ](https://www.youtube.com/watch?v=PorfLSr3DDI)
1. [CPP 2021 Back to Basics](https://www.youtube.com/watch?v=Bt3zcJZIalk&list=PLHTh1InhhwT4TJaHBVWzvBOYhp27UO7mI)
1. [CPP 2020 Back to Basics](https://www.youtube.com/watch?v=ZAji7PkXaKY&list=PLHTh1InhhwT5o3GwbFYy3sR7HDNRA353e)
1. [CPP 2019 Back to Basics](https://www.youtube.com/watch?v=32tDTD9UJCE&list=PLHTh1InhhwT4CTnVjJqnAKeMfGzOWjsRa)
1. [CppCon 2014: Herb Sutter "Back to the Basics! Essentials of Modern C++ Style"](https://www.youtube.com/watch?v=xnqTKD8uD64&t=28m27s)
1. [CppCon 2019: Alisdair Meredith, Pablo Halpern "Getting Allocators out of Our Way"](https://www.youtube.com/watch?v=RLezJuqNcEQ)
1. [CppCon 2017: Pablo Halpern "Allocators: The Good Parts"](https://www.youtube.com/watch?v=v3dz-AKOVL8)
1. [Practical Advice for Maintaining and Migrating Working Code - Brian Ruth - CppCon 2021](https://www.youtube.com/watch?v=CktRuMALe2A)

### [John Lakos videos on large scale development in C++]
* [CppCon 2018: John Lakos "C++ Modules and Large-Scale Development"](https://www.youtube.com/watch?v=K_fTl_hIEGY)
* [CppCon 2016: John Lakos "Advanced Levelization Techniques (part 1 of 3)"](https://www.youtube.com/watch?v=QjFpKJ8Xx78)
* [CppCon 2016: John Lakos "Advanced Levelization Techniques (part 2 of 3)"](https://www.youtube.com/watch?v=fzFOLsFASjU)
* [CppCon 2016: John Lakos "Advanced Levelization Techniques (part 3 of 3)"](https://www.youtube.com/watch?v=NrARQ7rHV-c)

### Unit testing
1. [Coleman Ruiz: Overcoming Physical & Emotional Challenges](https://www.youtube.com/watch?v=acgz0C-z-gc)
1. [Lightning Talk: Dependency Injection for Modern C++ - Tyler Weaver - CppCon 2022](https://www.youtube.com/watch?v=Yr0w62Gjrlw)
1. [Don't chase test coverage](https://www.youtube.com/watch?v=BVErL_Ez9LI)
1. [Unit Testing Is Not Good Enough](https://www.youtube.com/watch?v=h-4i5N89TUI)


### Concurrency and parallelism
1. [An Introduction to Multithreading in C++20 - Anthony Williams - CppCon 2022](https://www.youtube.com/watch?v=A7sVFJLJM-A)

---

## References

1. [cpp.sh](https://cpp.sh)
2. [https://ideone](https://ideone.com/HmZVro](https://ideone.com/HmZVro)
3. [Operator Precedence](https://en.cppreference.com/w/cpp/language/operator_precedence)

---

## Generate MdAnki

```bash
mdanki cpp_complete.md cpp_complete.apkg --deck "Mohan::DeepWork::cpp::complete"
```

### Individual Deck Generation (Original Files)
```bash
mdanki cpp.md cpp.apkg --deck "Mohan::DeepWork::cpp::cpp"
mdanki cpp_ecosystem.md cpp_ecosystem_to_be_more_productive_at_work.apkg --deck "Mohan::DeepWork::cpp::MoreProductive"
mdanki cpp_sample_codes.md cpp_sample_codes.apkg --deck "Mohan::DeepWork::cpp::sample_code_cpp"
```
