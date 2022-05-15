## find the jar of the className
```sh
java -verbose:class -classpath $(echo *.jar | sed ‘s/ /:/g’)  com.anything.yourclass | grep “yourclass”
```
[//]: # "Order of document - Array-Initialization, IntStream, List, String, Map"

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

## How to convert List<Integer> containing Integers to primitive int array i.e, int[]?
```java
listOfIntegers.stream().mapToInt(Integer::intValue).toArray()
```

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


## How to String-Stream as Array?
```java
String[] stringArray = stringStream.toArray(String[]::new);
```

## How to check if String is already sorted?

```java
public boolean isSorted(String[] words) {
    return IntStream.range(0, words.length-1).noneMatch( i -> words[i+1].compareTo(words[i]) < 0 );
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

## How to sort an IntStream and collect as List<Integer> (sorted output)
```java
verticalOrder.get(key).stream().sorted().collect(Collectors.toList())
```

## How to sum the  Stack<Integer> using lambda?
```java
stack.stream().reduce(0, Integer::sum);
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

## Find Maximum Frequency in HashMap
```java
Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
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
* mdanki /Users/alpha/git/MohanLearningGround/src/main/md/Java/java_oneliner.md java_api.apkg --deck "Mohan::CodeInterview::Java::API"


[DequeImage]: img/ArrayDeque.png "ArrayDeque"