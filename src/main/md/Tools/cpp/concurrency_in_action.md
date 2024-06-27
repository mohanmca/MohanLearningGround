## C++ Concurrency in action
1. Multithreadin is added in C++1
2. We can eat and watch tv, walk and talk at the same time while scratching head
3. Task switching is fundamental in olden days
4. All threads in a process share same address space
5. Concurrency vs Parallelism
   6. Focus and Intent
   7. Parallelism - to increase performance of bulk data processing
   8. Concurrency - responsibility separation, responsiveness


## Using concurrency for sepearation of concerns
1. One thread for UI and one thread for DVD decoding and play back
2. Separate task could run behind and produce output later
3. The number of thereads is indepdendent of the number of CPU cores avaialble (when separation of concern is what matters)
4. The division is based on conceptual design rather than an attempt to increase throughput


## Using concurrency for for performance
1. Task and data - due to multiple-cores
2. Free lunch is over, software has to be designed to run multiple process in parallel
3. Embarassingly parallel alogrithms, Naturally parallel and conveniently concurrent

## Thread Overhead
1. OS has to allocate the associated kernel resources
2. stack space
3. new thread to scheduler
4. If the task running is quite light (dwarf) don't use threads...
5. Too many threads ---> Too much memory consumption
6. Each stack has 1MB stack on typical machine
7. Prefer to use threadpools to reduce thread
8. More thread more context switch
9. If clarifty of design improved due to multithreaded, then it might be okay to use despite acceptable performance degrade.

## C++ Concurrency before C++11
1. Rely on compiler extension
2. Rely on C library and wrapped by Boost or ACE
3. Rely on platform API provided by MFC or Windows (internally C API), wrapped using RAII
4. Till C++11, threads were not accepted (and defined) by C++ standard

## C++ 11 specification
1. C++ Memory model
2. C++ STL was extended to include classes for managing threads
3. Protecting shared data
4. Synchronizing operation between threads
5. Low-level atomic operations
6. Based on C++ Boost thread library (primary model)   

## C++ 14/17 -  specification
1. C++14 - New mutex type for protecting data
2. A full suite of parallel algorithm
3. Synchronizing operation between threads for functions and classes

## C++ Efficiency in the C++ Thread library
1. Abstraction cost quite low
2. you dont pay for things that you don't use
3. Reduce the contention always
4. Thread library may offer native_handle() member function that allows the underlying implementation to be directly manipulated using platform-specific API. It is not portable


## C++ Concurrency

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
   
