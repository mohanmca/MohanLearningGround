## What was the HashMap change in JDK8

1. TREEIFY_THRESHOLD - In Java 8, HashMap replaces the linked list with another useful data structure i.e. binary tree on breaching a certain threshold
2. Which is known as TREEIFY_THRESHOLD. Once this threshold is reached the linked list of Entries is converted to the TreeNodes 
3. which reduces the time complexity from O(n) to O(log(n)).


## How Hashmap works in Java

* [HashMap.java](https://github.com/AdoptOpenJDK/openjdk-jdk/blob/master/src/java.base/share/classes/java/util/HashMap.java) 

## How locking works inside concurrent hashmap

* Lock-strip segment (before 8)
* First element in each bucket is locked

## Write concurrent bounded-buffer with size 10?

## What is PECS?

## Print odd even with lock (without synchronized)