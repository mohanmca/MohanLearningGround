## What is new in JDK-20

1. Language Features
   1. Record Patterns
   2. Pattern Matching for Switch
2. Concurrency Changes
   1. Virtual Threads
   2. Scoped Values
   3. Structured Concurrency
3. Libraries improvements
   1. Vector API
   2. Foreign Function & Memory API

## Pattern matching in if clause

```java
if (Object instanceof String s) {
    //use s   --from JDK16
}
```

## Record Pattern

```record Point(int x, int y){}```


## Pattern matching and Record classes Combined

```java
if(obj instanceof Point(int x, int y)) {
    System.out.println(x+y)
}
```

## Pattern matching and More Complicated Object Graphs

```java
record Point(int x, int y){}
enum Color{RED, GREEN, BLUE}
record ColoredPoint(Point p, Color c)
record Rectangle(ColoredPoint upperLeft, ColoredPoint loweredRight lr)

static void printUpperLeftColoredPoint(Rectangle r) {
    if(r instanceof Recangle(ColoredPoint(Point p, Color c), var lr)) {
        System.out.println(c);    
    }    
}
```

## record Pattern for enhanced for loop
```java
for(Point(int x, int y): points) {
    System.out.println(x)    
}
```

## Pattern matching for switch

```java
static String formatterPatternSwitch(Object obj) {
    return switch (obj) {
        case String s when s.legnth() > 10 -> String.format("Longer string %s", s);
        case Integer i -> String.format("int %d", i);
        case Long l    -> String.format("long %d", l);
        case Double d  -> String.format("double %f", d);
        case String s  -> String.format("String %s", s);
        default        -> obj.toString();
    };
}
```