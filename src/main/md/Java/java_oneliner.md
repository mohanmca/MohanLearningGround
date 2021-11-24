### find the jar of the className
```sh
java -verbose:class -classpath $(echo *.jar | sed ‘s/ /:/g’)  com.anything.yourclass | grep “yourclass”
```

## Find maximum in a array

```java
int maxValue = Arrays.stream(nums).reduce(nums[0], (x,y) -> x>y ? x : y );
```

## How to iterate List in reverser order

```java
        Iterator lit = obj.descendingIterator();
        System.out.println("Backward Iterations");
        while(lit.hasNext()){
            System.out.println(lit.next());
        }
```

### Quick analysis for xception handling in java code
```java
find . -name \*java | grep -v “test.*est” | xargs grep -A 4 “catch.*xception” > exceptionHandling.txt
grep -A 4 catch.*xception `find . -type f -name \*java | grep -v test` > xception.log
```

### Initialize two dimensional array
```java
new int[][]{{1, 2}, {3}, {3}, {}}
```

### Java format arguments while printing (or String manipulation)
```java
String.format("%1$10s-%2$-10s-%3$10s-%4$10d","1000",2500,"123",123);
//        "      1000-2500      -       123-       123"
String.format("%1$-10s-%2$10s-%3$-10s-%4$10d","1000",2500,"123",123);
//        "1000      -      2500-123       -       123"
```

### How to convert an List<Integer> containing Integers to primitive int array?
```java
listOfIntegers.stream().mapToInt(Integer::intValue).toArray()
```
### How to convert an List<Integer> containing Integers to primitive int array?
```java
String[] stringArray = stringStream.toArray(String[]::new);
```

### How to reverse Java int[] Array?
```java
IntStream.rangeClosed(1, a.length).map(i -> a[a.length-i]).toArray();
Collections.reverse(Arrays.asList(yourArray));
```

### How to find a digit inside array using binary search after index
```java
java.util.Arrays.binarySearch(sortedArr, index + 1, sortedArr.length, key);
//If return value >=0, we found the key
```

### How to sort an IntStream and collect as List<Integer> (sorted output)
```java
verticalOrder.get(key).stream().sorted().collect(Collectors.toList())
```

### How to sum the  Stack<Integer> using lambda?
```java
stack.stream().reduce(0, Integer::sum);
```