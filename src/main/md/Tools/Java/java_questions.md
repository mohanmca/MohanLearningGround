## What was the HashMap change in JDK8

1. TREEIFY_THRESHOLD - In Java 8, HashMap replaces the linked list with another useful data structure i.e. binary tree on breaching a certain threshold
2. Which is known as TREEIFY_THRESHOLD. Once this threshold is reached the linked list of Entries is converted to the TreeNodes 
3. which reduces the time complexity from O(n) to O(log(n)).
