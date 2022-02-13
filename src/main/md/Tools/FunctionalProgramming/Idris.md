* IDRIS - Dependently Typed Functional Programming Language
  * Software bugs would become pretty costly in future, In Typed programs many errors are found during compile time. With dependent types compilers will do more work than simple typed programs
  * Supports tactic based theorem proving
  * Types may be predicated on values
* Total Function
  * A total function is guaranteed to produce a result
  * Total function will return a value in a finite time for every possible well-typed input
  * Total function guaranteed not to throw any exceptions
* Type driven development 
  * Types are first class language construct, function can accept, manipulate and return types
  * Types can be manipulated, used, passed as arguments to functions, and returned from functions just like any other value, such as numbers, strings, or list
  * It allows relationships to be expressed between values; for example, that two lists have the same length.
  * It allows assumptions to be made explicit and checkable by the compiler. 
    * For example, if you assume that a list is non-empty, Idris can ensure this assumption always holds before the program is run.
    * If desired, it allows program behavior to be formally stated and proven correct.



# Chapter-1 Summary #
* Types are a means of classifying values. Programming languages use types to decide how to lay out data in memory, and to ensure that data is interpreted consistently.
* A type can be viewed as a specification, so that a language implementation (specifically, its type checker) can check whether a program conforms to that specification.
* Type-driven development is an iterative process of type, define, refine, creating a type to model a system, then defining functions, and finally refining the types as necessary.
* In type-driven development, a type is viewed more like a plan, helping an interactive environment guide the programmer to a working program.
* Dependent types allow you to give more-precise types to programs, and hence more informative plans to the machine.
* In a functional programming language, program execution consists of evaluating functions.
* In a purely functional programming language, additionally, functions have no side effects.
* Instead of writing programs that perform side effects, you can write programs that describe side effects, with the side effects stated explicitly in a program’s type.
* A total function is guaranteed to produce a result for any well-typed input in finite time.
* Idris is a programming language that’s specifically designed to support type-driven development. It’s a purely functional programming language with first-class dependent types.
* Idris allows programs to contain holes that stand for incomplete programs.
* In Idris, types are first-class, meaning that they can be stored in variables, passed to functions, or returned from functions like any other value.


# Chapter-2 Summary #
* The Prelude defines a number of basic types and functions and is imported automatically by all Idris programs.
* Idris provides basic numeric types, Int, Integer, Nat, and Double, as well as a Boolean type, Bool, a character type, Char, and a string type, String.
* Values can be converted between compatible types using the cast function, and can be given explicit types with the the function.
* Tuples are fixed-size collections where each element can be a different type.
* Lists are variable size collections where each element has the same type.
* Function types have one or more input types and one output type.
* Function types can be generic, meaning that they can contain variables. These variables can be constrained to allow a smaller set of types.
* Higher-order functions are functions in which one of the arguments is itself a function.
* Functions consist of a required type declaration and a definition. Function definitions are equations defining rewrite rules to be used during evaluation.
* Whitespace is significant in Idris programs. Each definition in a block must begin in exactly the same column.
* Function documentation can be accessed at the REPL with the :doc command.
* Idris programs can be divided into separate source files called modules.
* The entry point to an Idris program is the main function, which must have type IO (), and be defined in the module Main. Simple interactive programs can be written by applying the repl function from main.