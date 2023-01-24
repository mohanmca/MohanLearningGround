## find the jar of the className
```sh
java -verbose:class -classpath $(echo *.jar | sed ‘s/ /:/g’)  com.anything.yourclass | grep “yourclass”
```
[//]: # "Order of document - Array-Initialization, IntStream, List, String, Map"

## [Java Deque API](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayDeque.html)

```
1. Deque behaves differently based on its interface
2. Stack (Interface)
   1. push - addFirst
   2. pop - removeFirst
   3. peek - getFirst
3. Queue (Interface)
   1. add(e) - addLast(e)
   2. offer - offerLast
   3. remove() - removeFirst()
   4. poll - pollFirst()
   5. element - getFirst()
   6. peek - peekFirst
```

## [Java Deque API](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayDeque.html)
1. [1438. Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit](https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/discuss/609771/JavaC%2B%2BPython-Deques-O(N))
2. [1425. Constrained Subsequence Sum](https://leetcode.com/problems/constrained-subsequence-sum/)
3. [862. Shortest Subarray with Sum at Least K](https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/discuss/143726/C%2B%2BJavaPython-O(N)-Using-Deque/386606/)
4. [1499. Max Value of Equation](https://leetcode.com/problems/max-value-of-equation/discuss/709231/JavaPython-Priority-Queue-and-Deque-Solution-O(N))


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

## Stream Most often used FAQ
1. How to add only non-null using stream
   1. ```Arrays.stream(lists).filter(Objects::nonNull).forEach(pq::offer);```
2. How to check if there are any null
   1. ```stream.anyMatch(Objects::isNull)```
   2. ```stream.anyMatch(x -> x == null)```
3. How to convert List<Integer> containing Integers to primitive int array i.e, int[]?
   1. ```listOfIntegers.stream().mapToInt(Integer::intValue).toArray()```
4. How to String-Stream as Array?
   1. ```String[] stringArray = stringStream.toArray(String[]::new);```
5. How to sum the  Stack<Integer> using lambda?
   1. ```stack.stream().reduce(0, Integer::sum);```
6. In frequencyCountMap, find the key that has maximum frequency
   1. ```Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();```
7. How to sort an IntStream and collect as List<Integer> (sorted output)
   1. ```verticalOrder.get(key).stream().sorted().collect(Collectors.toList())```

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

## Integer Stream sort, collect as Set
```java
    Arrays.asList(3,1,4,1,5,9,6,2).stream().sorted(Integer::compare).toList()
    Set<Integer> set = Arrays.stream(nums).boxed().collect(Collectors.toSet())
    new HashSet<Integer>(Arrays.asList(1,2,3)) -- This would work..
    ~~~~~new HashSet<>(Arrays.asList(new int[]{1,2,3})) //This will not work, for primitive integer arrays
    |  no suitable constructor found for HashSet(java.util.List<int[]>)
    |      constructor java.util.HashSet.HashSet(java.util.Collection<? extends java.lang.Integer>) is not applicable
```

## Find maximum in a array

```java
int maxValue = Arrays.stream(nums).reduce(nums[0], (x,y) -> x>y ? x : y );
int min = Stream.of(14, 35, -7, 46, 98).reduce(Integer::min).get();
min = Stream.of(14, 35, -7, 46, 98).min(Integer::compare).get();

int max = Stream.of(14, 35, -7, 46, 98).reduce(Integer::max).get();
max = Stream.of(14, 35, -7, 46, 98).max(Integer::compare).get();
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

1. ```comparing(Function<? super T,? extends U> keyExtractor)```
2. ```comparing(Function<? super T,? extends U> keyExtractor, Comparator<? super U> keyComparator)```
3. ```comparingDouble(ToDoubleFunction<? super T> keyExtractor)```
4. ```comparingInt(ToIntFunction<? super T> keyExtractor)```
5. ```comparingLong(ToLongFunction<? super T> keyExtractor)```
6. ```naturalOrder()```
7. ```nullsFirst(Comparator<? super T> comparator)```
8. ```nullsLast(Comparator<? super T> comparator)```
9. ```reverseOrder()```

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
* T - the type of element to be compared
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

## How to collect Stream<T> as Map<T, Long>

```java
class HelloWorld {

    public static <Character> Map<Character, Long> frequencyMap(Stream<Character> elements) {
        return elements.collect(
                Collectors.groupingBy(
                        Function.identity(),
                        HashMap::new, // can be skipped
                        Collectors.counting()
                )
        );
    }

    public static void main(String[] args) {
        String aString = "abc";
        System.out.println(frequencyMap(aString.chars().mapToObj(c -> (char) c)));
    }
}
```

## How to sort the map by value?
* toMap(Function<? super T,? extends K> keyMapper, Function<? super T,? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier)
```java
    LinkedHashMap<String, Long> countByWordSorted = collect.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> {
                            throw new IllegalStateException();
                        },
                        LinkedHashMap::new
                ));
```

## Predicate till condition matches (only when they are sorted)

```java
List<Dish> slicedMenuDishes = specialMenu.stream()
     .takeWhile(dish -> dish.getCalories() < 320)
     .collect(toList());
List<Dish> slicedMenuDishes = specialMenu.stream()
    .dropWhile(dish -> dish.getCalories() < 320)
    .collect(toList());
```

## JShell
```bat
jshell https://kishida.github.io/misc/jframe.jshell
jshell https://gist.githubusercontent.com/mohanmca/88de9d6115587f9b8c6e8ac73b80f46e/raw/a6f272479026f8bb5d79f01f9cbab631e04cb78c/jshell.jshell
```


## Reference
* [aruld/java-oneliners](https://github.com/aruld/java-oneliners/blob/master/src/main/java/com/github/aruld/oneliners/Item8.java)
* [java-8-stream-cheat-sheet](https://www.jrebel.com/system/files/java-8-streams-cheat-sheet.pdf)
* [Java collections cheat sheet](https://www.jrebel.com/system/files/java-collections-cheat-sheet.pdf)
* [Java Generics Cheat sheet](https://www.jrebel.com/system/files/java-generics-cheat-sheet.pdf)
* [Java cheatsheet](https://github.com/jsjtzyy/LeetCode/blob/master/Java%20cheat%20sheet%20for%20interview)
* mdanki /Users/alpha/git/MohanLearningGround/src/main/md/Java/java_oneliner.md java_api.apkg --deck "Mohan::CodeInterview::Java::API"
[DequeImage]: img/ArrayDeque.png "ArrayDeque"
* 
## How to create anki from this markdown file
* mdanki java_oneliner.md java_oneliner.apkg --deck "Mohan::Work::Java::java_oneliner"