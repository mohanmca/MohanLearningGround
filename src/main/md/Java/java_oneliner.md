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
### How to String-Stream as Array?
```java
String[] stringArray = stringStream.toArray(String[]::new);
```

## How to check if String is already sorted?

```java
public boolean isSorted(String[] words) {
    return IntStream.range(0, words.length-1).noneMatch( i -> words[i+1].compareTo(words[i]) < 0 );
}
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
## How to reverse elements between two indices of java array?

```java
private void reverse(int[] nums, int first, int last) {
    while (first < last) {
        swap(nums, first++, last--);
    }
}
```

## How to generate List<Integer>  with 11212312341234512345612345671234567812345678912345678910 in java?

```java
    IntStream.rangeClosed(1, 10).mapToObj(i -> IntStream.rangeClosed(1, i).boxed()
    .collect(Collectors.toList())).flatMap(id -> id.stream()).collect(Collectors.toList());
```

### How to sort an IntStream and collect as List<Integer> (sorted output)
```java
verticalOrder.get(key).stream().sorted().collect(Collectors.toList())
```

### How to sum the  Stack<Integer> using lambda?
```java
stack.stream().reduce(0, Integer::sum);
```

## How to collect Stream<T> as Map<T, Long>

```java
class HelloWorld {

    public static <Character> Map<Character, Long>  frequencyMap(Stream<Character> elements) {
        return elements.collect(
            Collectors.groupingBy(
            Function.identity(),
            HashMap::new, // can be skipped
            Collectors.counting()
            )
        );
    }

    public static void main( String args[] ) {
        String aString = "abc";
        System.out.println(frequencyMap(aString.chars().mapToObj(c -> (char) c)));
    }
}
```

## How to sort the map by value?

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

* mdanki /Users/alpha/git/MohanLearningGround/src/main/md/Java/java_oneliner.md java_api.apkg --deck "Mohan::CodeInterview::Java::API"