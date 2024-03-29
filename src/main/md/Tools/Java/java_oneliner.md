## find the jar of the className
```sh
java -verbose:class -classpath $(echo *.jar | sed ‘s/ /:/g’)  com.anything.yourclass | grep “yourclass”
```
[//]: # "Order of document - Array-Initialization, IntStream, List, String, Map"

## What classes are implementing Stack interface?

* There is no stack interface in Java
* push + pop + peek - are the methods generally used by classes that supports stack functionality (Stack and ArrayDeque)

## What are the methods that can return special value or timeout in BlockingQueue
1. offer(e) and offer(e, time, unit) (boolean)
1. poll() and poll(time, unit) (returns E or null when timeout)

## What are the methods that Blocks in BlockingQueue
1. put(E e)
1. take()

## What was the HashMap change in JDK8

1. TREEIFY_THRESHOLD - In Java 8, HashMap replaces the linked list with another useful data structure i.e. binary tree on breaching a certain threshold
2. Which is known as TREEIFY_THRESHOLD. Once this threshold is reached the linked list of Entries is converted to the TreeNodes
3. which reduces the time complexity from O(n) to O(log(n)).

## How Hashmap works in Java
* [HashMap.java](https://github.com/AdoptOpenJDK/openjdk-jdk/blob/master/src/java.base/share/classes/java/util/HashMap.java)

## How LinkedBlockingQueue works?
* [LinkedBlockingQueue](https://github.com/AdoptOpenJDK/openjdk-jdk/blob/master/src/java.base/share/classes/java/util/concurrent/LinkedBlockingQueue.java)

## What is Queue API? What are exception throwing API? What are boolean return API?

* [Queue](https://github.com/AdoptOpenJDK/openjdk-jdk/blob/master/src/java.base/share/classes/java/util/Queue.java)
* Exception throwing API
  * add(e)
  * remove()
  * element()
* Special value returning API
  * offer(e)
  * poll()
  * peek()

## How locking works inside concurrent hashmap
* Lock-strip segment (before 8)
* First element in each bucket is locked

## [Java Deque API](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Deque.html)

```java
1. Deque behaves differently based on its interface
2. Stack (Interface)
   1. push - addFirst
   2. pop - removeFirst
   3. peek - getFirst
3. Queue (Interface) - Equivalent Deque Method
   1. add(e) - addLast(e)
   2. offer - offerLast
   3. remove() - removeFirst()
   4. poll - pollFirst()
   5. element - getFirst()
   6. peek - peekFirst
```

## Java Comparators

1. Comparator.comparing(Function.identity())
2. Comparator<String> c = String::compareTo
3. Comparator that compares Person objects by their last name,
   1. Comparator<Person> byLastName = Comparator.comparing(Person::getLastName);
   2. Java doesn't support field reference similar to method reference, hence above style comparator may not work for List.val or Node.val
4. Java creating composite comparator
   1. Comparator<Point> cmp = Comparator.comparingInt(p -> p.x).thenComparingInt(p -> p.y);
5. Comparator.reverseOrder
   1. Comparator.comparingInt(String::length).reversed()
      1. // stream is now [test, foo, a], sorted by descending length
6. Sort the indices of two dimensional array named intevals: int[][]
   ```java
        Integer[] indices = IntStream.range(0, intervals.length).boxed().toArray(Integer[]::new);
        Arrays.sort(indices, Comparator.comparingInt( (Integer i) -> intervals[indices[i]][0] ).thenComparingInt((Integer i) -> intervals[indices[i]][1]));
   ```

## How String::compareTo method is type-casted as Comparator<String>

1. If type A has method, that accepts type B as parameter, java can convert that method into interface that can accepts type A and B.
   1. "The type to which the method belongs precedes the delimiter, and the invocation's receiver is the first parameter of the functional interface method"
   2. [State of lambda](https://cr.openjdk.org/%7Ebriangoetz/lambda/lambda-state-final.html)


## What is the type of String Comparator

* Comparator<? super E>
* Comparator<? super String>
* why?
    * Comparator accepts String

## Java generics - Type covariance

* The container types have the same relationship to each other as the payload types do. This is expressed using the extends keyword.
* If Cat extends Pet, then List<Cat> is a subtype of List<? extends Pet>
* It is read-only

## Java generics - Type contravariance

* The container types have the inverse relationship to each other as the payload types do. This is expressed using the super keyword.
* Container type that is acting purely as a consumer of instances of a type
* It can be used to write types into collection


## What is PECS?

* "PECS" is from the collection's point of view.
* If you are only pulling items from a generic collection, it is a producer, and you should use extends;
* if you are only stuffing items in, it is a consumer and you should use super.
* If you do both with the same collection, you shouldn't use neither extends nor super. You should use simple class/interface (without wildcards)
* What is a producer?
    * A producer is allowed to produce something more specific, hence extends, a consumer is allowed to accept something more general, hence super.
    * Producer refers to the return type of method.
* What is a consumer?
    * Consumer refers to the parameter type of method.
* A nice mnemonic you can use is to imagine returns for extends and accepts for super.
    * Tree<? extends T> reads Tree<? returns T>


## What are Integer related Comparators

```java
Integer.compare(int x, int y)   //static method
Integer.max(int x, int y)   //static method
Integer.min(int x, int y)   //static method
Integer.sum(int x, int y)   //static method
compareTo(Integer anotherInteger) //instance method
```

## How to sum the  Stack<Integer> using lambda?
```java
Optional<Integer> result = stack.stream().reduce(Integer::sum);
```

## In frequencyCountMap, find the key that has maximum frequency
```java
Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
```
## How to convert String into Stream<Character>?
```java
"String".chars().mapToObj(c -> (char)c)"
```

## How to add only non-null using stream</summary>
```java
Arrays.stream(new Integer[]{1,2,3,null}).filter(Objects::nonNull).forEach(System.out::println)
Arrays.stream(lists).filter(Objects::nonNull).forEach(pq::offer);
```

## How to check if there are any null
```java
stream.anyMatch(Objects::isNull)
stream.anyMatch(x -> x == null)
```

## How to convert List<Integer> containing Integers to primitive int array i.e, int[]?
```java
listOfIntegers.stream().mapToInt(Integer::intValue).toArray()
```

## How to String-Stream as Array?
```java
String[] stringArray = stringStream.toArray(String[]::new);
```

## Quick analysis for xception handling in java code
```java
find . -name \*java | grep -v “test.*est” | xargs grep -A 4 “catch.*xception” > exceptionHandling.txt
grep -A 4 catch.*xception `find . -type f -name \*java | grep -v test` > xception.log
```

## Initialize two dimensional array
```java
new int[][]{{1, 2}, {3}, {3}, {}}
```

## How to create K rows with 2 columns each
```java
int[][] closestPoints = new int[k][2];
jshell> var t = new int[5][2]
t ==> int[5][] { int[2] { 0, 0 }, int[2] { 0, 0 }, int[ ...  0, 0 }, int[2] { 0, 0 } }
```

## Sort List in reverse order
```Collections.sort(list, Collections.reverseOrder());```

## How many ways are there to find min/max?
1. 3 Ways (reduce-lambda, min, reduce+method-reference)
```java
int min = Stream.of(14, 35, -7, 46, 98).reduce(Integer::min).get();
min = Stream.of(14, 35, -7, 46, 98).min(Integer::compare).get();
min = Arrays.stream(nums).reduce(nums[0], (x,y) -> x<y ? x : y );
```

## How to iterate List in reverser order

```java
        Iterator lit = obj.descendingIterator();
        System.out.println("Backward Iterations");
        while(lit.hasNext()){
            System.out.println(lit.next());
        }
```

## How to check if String is already sorted?

```java
public boolean isSorted(String[] words) {
    return IntStream.range(0, words.length-1).noneMatch( i -> words[i+1].compareTo(words[i]) < 0 );
}
```


## Comparator - Sorting Strings - sortedByNaturalOrder()

```java
List<String> bonds = Arrays.asList("Connery","Lazenby","Moore", "Dalton", "Brosnan","Craig");
List<String> sortedByNaturalOrder = bonds.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
```
* output: sortedByNaturalOrder ==> [Brosnan, Connery, Craig, Dalton, Lazenby, Moore]

## Comparator.reverseOrder() - Static method

```java
List<String> sortedByReverseOrder = bonds.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
```
* output: sortedByReverseOrder ==> [Moore, Lazenby, Dalton, Craig, Connery, Brosnan]

## How to compare strings using lowercase

```java
List<String> sortedByLowerCase = bonds.stream().sorted(Comparator.comparing(String::toLowerCase)).collect(Collectors.toList());
```
* output: sortedByLowerCase ==> [Brosnan, Connery, Craig, Dalton, Lazenby, Moore]
* **Note**
   * The data is not changed to lowercase
   * Only while comparing, the Comparator uses toLowerCase of the all the input values

## How to compare strings using string length

```java
List<String> sortedByLength = bonds.stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
```
* output: sortedByLength ==> [Moore, Craig, Dalton, Lazenby, Connery, Brosnan]
* **Note**
   * The data is not changed to lowercase
   * Only while comparing, the Comparator uses length of the input string values

## Composite comparator - string.length and naturalOrder

```java
List<String> sortedByLengthThenByNaturalOrder = bonds.stream().sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())).
    collect(Collectors.toList());
```
* output: sortedByLengthThenByNaturalOrder ==> [Craig, Moore, Dalton, Brosnan, Connery, Lazenby]


## Where to find Comparator API
* [java.util.Comparator](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html)

## Static methods in java.util.Comparator

1. ```naturalOrder()```
2. ```reverseOrder()```
3. ```comparing(Function<? super T,? extends U> keyExtractor)```
4. ```comparing(Function<? super T,? extends U> keyExtractor, Comparator<? super U> keyComparator)```
5. ```comparingDouble(ToDoubleFunction<? super T> keyExtractor)```
6. ```comparingInt(ToIntFunction<? super T> keyExtractor)```
7. ```comparingLong(ToLongFunction<? super T> keyExtractor)```
8. ```nullsFirst(Comparator<? super T> comparator)```
9. ```nullsLast(Comparator<? super T> comparator)```

## Instance Methods in java.util.Comparator

1. ```thenComparing(Comparator<? super T> other)```
2. ```thenComparing(Function<? super T,? extends U> keyExtractor)```
3. ```thenComparing(Function<? super T,? extends U> keyExtractor, Comparator<? super U> keyComparator)```
4. ```thenComparingDouble(ToDoubleFunction<? super T> keyExtractor)```
5. ```thenComparingInt(ToIntFunction<? super T> keyExtractor)```
6. ```thenComparingLong(ToLongFunction<? super T> keyExtractor)```

## Examples from JDK - CASE_INSENSITIVE_ORDER

* To sort a collection of String based on the length and then case-insensitive natural ordering, the comparator can be composed using following code,
```java
Comparator<String> cmp = Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER);
```

## To obtain a Comparator that compares Person objects by their last name ignoring case differences,

```java
static <T,U> Comparator<T> comparing(Function<? super T,? extends U> keyExtractor, Comparator<? super U> keyComparator)
```
* Type Parameters:
* T - input type of element to be compared
* U - the type of the sort key
* Parameters:
* keyExtractor - the function used to extract the sort key
* keyComparator - the Comparator used to compare the sort key
* Returns:
* a comparator that compares by an extracted key using the specified Comparator

```java
Comparator<Person> cmp = Comparator.comparing(
    Person::getLastName,
    String.CASE_INSENSITIVE_ORDER);
```

## to obtain a Comparator that compares Person objects by their last name,

```java
Comparator<Person> byLastName = Comparator.comparing(Person::getLastName);
```

## How to Map<Integer, List<>> in one line

```java
    Map<Integer, List<Integer>> adj = new HashMap<>();
    for (int[] edge : edges) {
        int a = edge[0], b = edge[1];
        adj.computeIfAbsent(a, value -> new ArrayList<Integer>()).add(b);
        adj.computeIfAbsent(b, value -> new ArrayList<Integer>()).add(a);
    }
```

## Java format arguments while printing (or String manipulation)
```java
String.format("%1$10s-%2$-10s-%3$10s-%4$10d","1000",2500,"123",123);
//        "      1000-2500      -       123-       123"
String.format("%1$-10s-%2$10s-%3$-10s-%4$10d","1000",2500,"123",123);
//        "1000      -      2500-123       -       123"
```

## What are all important method in StringBuilder

```java
strBuilder.deleteCharAt(strBuilder.length() - 1)
public StringBuilder replace(int start,int end, String str); //end-1 would be the last character affected
```

## How to remove last character in StringBuilder

```java
StringBuilder.deleteCharAt(sb.length()-1);
```

## Java String methods, Initialize, Join and Strip

```java
String[] strings = {"abc", "dbca"};
jshell> String.join(", ", new String[]{"abc", "dbca"})
$16 ==> "abc, dbca"


jshell> " abc ".strip() //stripLeading and stripTrailing - also available
$18 ==> "abc"
jshell> "abc".repeat(3)
$19 ==> "abcabcabc"
```

## Java regular expression - tricks

```java
jshell>"\\mohan\\root".split("\\\\")
$11 ==> String[3] { "", "mohan", "root" }
jshell> System.out.print("\nmohan\nroot")

mohan
root
jshell> "/a/b".split("/")
$15 ==> String[3] { "", "a", "b" }
```

### How to find a digit inside array using binary search after index
```java
java.util.Arrays.binarySearch(sortedArr, index + 1, sortedArr.length, key);
//If return value >=0, we found the key
```
## Java primitive int[] sort reverse order

```java
    Arrays.sort(int[], Collections.reverseOrder()) // will not work for primitive
    Arrays.stream(candidates).boxed().sorted (Collections.reverseOrder()).mapToInt(Integer::intValue).toArray();
```

## How to convert List<int[]> into int[][]?

```java
   List<int[]> common = new ArrayList<int[]>();
   return (int[][])common.toArray(new int[common.size()][]);
```

## How to sort only elements after 1'st element (leave the 0th element in its place)
```java
Collections.sort(mergedAccount.subList(1, mergedAccount.size())); 
```

## How to reverse elements between two indices of java array?

```java
private void reverse(int[] nums, int first, int last) {
    while (first < last) {
        swap(nums, first++, last--);
    }
}
```

### How to reverse Java int[] Array?
```java
IntStream.rangeClosed(1, a.length).map(i -> a[a.length-i]).toArray();
Collections.reverse(Arrays.asList(yourArray));
```

## Find sum/count in int stream

```java
Integer sum = integers.stream().reduce(0, Integer::sum);
return (int)freq.values().stream().filter( v -> v>=2).count();
```


## How to generate List<Integer>  with 11212312341234512345612345671234567812345678912345678910 in java?
```java
    IntStream.rangeClosed(1, 10).flatMap(i -> IntStream.rangeClosed(1, i)).boxed().collect(Collectors.toList());
```

## How to ensure two arrays/lists are equal?

```
word1FrequencyList.equals(word2FrequencyList);
Arrays.equals(array1, array2);
```

## How to iterate ArrayDeque as stack

```java
Deque<String> deque = new ArrayDeque<String>();deque.push("1");deque.push("2");
for(String data: deque) { System.out.println(deque.remove());}  //2 and 1
for(String data: deque) { System.out.println(data);}  //2 and 1
```
* [Deque-API](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html)
* Deque: ![Alt Text][DequeImage]

## How to use ArrayDeque as stack while populating, and consume as queue

```java
Deque<String> deque = new ArrayDeque<String>();deque.push("1");deque.push("2");
while(!deque.isEmpty()) { System.out.println(deque.removeLast());}  //1,2
```
## What are new String methods added to JDK

* [isBlank, strip, chars, codePoint](https://javaconceptoftheday.com/java-new-string-methods-with-examples/)

## What does toMap API return type?
* Collector<T,?,Map<K,U>>

## What are all toMap API?

```java
toMap(Function<? super T,? extends K> keyMapper,  Function<? super T, ? extends U> valueMapper)
toMap(Function<? super T,? extends K> keyMapper, Function<? super T,? extends U> valueMapper, BinaryOperator<U> mergeFunction)
toMap(Function<? super T,? extends K> keyMapper, Function<? super T,? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier)
```

## What are the Map related comparators

1. Map.Entry.comparingByValue()
2. Map.Entry.comparingByValue(Comparator.reverseOrder())
3. Map.Entry.comparingByKey()
4. Map.Entry.comparingByKey(Comparator.reverseOrder())

## Predicate till condition matches 

* Can be used only when containers  are sorted
```java
List<Dish> slicedMenuDishes = specialMenu.stream()
     .takeWhile(dish -> dish.getCalories() < 320)
     .collect(toList());
List<Dish> slicedMenuDishes = specialMenu.stream()
    .dropWhile(dish -> dish.getCalories() < 320)
    .collect(toList());
```

## How to invoke script from url into JShell
```bat
jshell https://kishida.github.io/misc/jframe.jshell
jshell https://gist.githubusercontent.com/mohanmca/88de9d6115587f9b8c6e8ac73b80f46e/raw/a6f272479026f8bb5d79f01f9cbab631e04cb78c/jshell.jshell
```

## Sample equals and hashcode for order

```java
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Order order = (Order) o;
            return quantity == order.quantity 
                    && Float.compare(order.price, price) == 0 
                    && orderedTime == order.orderedTime 
                    && arrivedTime == order.arrivedTime 
                    && Objects.equals(orderId, order.orderId) 
                    && Objects.equals(parentOrderId, order.parentOrderId) 
                    && side == order.side && Objects.equals(instrument, order.instrument);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, parentOrderId, side, instrument, quantity, price, orderedTime, arrivedTime);
        }
```

## Reference
* [aruld/java-oneliners](https://github.com/aruld/java-oneliners/blob/master/src/main/java/com/github/aruld/oneliners/Item8.java)
* [java-8-stream-cheat-sheet](https://www.jrebel.com/system/files/java-8-streams-cheat-sheet.pdf)
* [Java collections cheat sheet](https://www.jrebel.com/system/files/java-collections-cheat-sheet.pdf)
* [Java Generics Cheat sheet](https://www.jrebel.com/system/files/java-generics-cheat-sheet.pdf)
* [Java cheatsheet](https://github.com/jsjtzyy/LeetCode/blob/master/Java%20cheat%20sheet%20for%20interview)
[DequeImage]: img/ArrayDeque.png "ArrayDeque"

 
## How to create anki from this markdown file
* mdanki java_oneliner.md Java_OneLiner.apkg --deck "Mohan::Core::Java::OneLiner"
* node /Users/alpha/.nvm/versions/node/v16.5.0/lib/node_modules/mdanki/src/index.js java_oneliner.md Java_OneLiner.apkg --deck "Mohan::Core::Java::OneLiner" -s TOTAL_MEMORY=26777216
