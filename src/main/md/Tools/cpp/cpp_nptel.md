## [Programming In Modern C++ | NPTEL](https://onlinecourses.nptel.ac.in/noc24_cs44/preview)
1. PROF. PARTHA PRATIM DAS
2. [Course Videos and slides](http://www.nitttrc.edu.in/nptel/courses/video/106105234/106105234.html)
3. [C++](https://www.youtube.com/watch?v=mMswJnSDepc)

## Basic C Program

```c
#include <stdio.h>

int main() {
  printf("Hello world\n");
  return 0;
}
```

## Basics of C
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
    

## C Operators
1. sizeof, &, * ?: . ,
2. unary, binary and ternary operators (?:)
3. Operator Precedence
  1. Left-to-Right
  2. Highest-to-Lowest
3. Operator has two kinds of associativity
4. Left/Right associativity =  (a = b = c ==> (a=(b=c)) // assignment operator is right associatity
5. We can use () parenthesis to alter/control associativity 

  
## Examples of C program snippets
```c
int a[10]; double d, dist_in_ly; unsigned int count;
char* name[]={"Partha", "Pratim", "Das"} // Array size is 3
int i=10; int *p = &i; *p= 30;
int primes[5] = {2,3} // last 3 elements set to 0
int mat[3][4] // two dimensional, Arrays are row-major
```

## Containers and Pointers
1. Array - indexed container
2. Structure - Container for one or more members
  1. All the members are accessed by member name
  2. Union - special type of strucuture to support variant types (only one out of all the members)
3. C - two types of addressing
  1. indexed vs referential
  2. Referential - address of variable - can be stored and manipulated

## Structure (two ways to declare)

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

## Strcture using type-def   
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

