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
