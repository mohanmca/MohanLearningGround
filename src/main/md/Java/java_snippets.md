## JDK-9 Reactive Streams - Publish subscribe framework - java.util.concurrent.Flow
* 
  


 ```java
 var fruits = List.of("apple", "orange", "banana");
 var anotherSetOfFruits = Set.of("apple", "orange", "banana");
 var map = Map.of("a","b","c","d");
 // Use diamond operator, don't specify type while creating, compiler could infer 
 List<String> list2 = new ArrayList<>();
 BiFunction<Integer, String, String> biss = (i, _) -> String.valueOf(i);
 var p = new ProcessBuilder("notepad.exe").start();
 System.out.println(p.pid())
 System.out.println(ProcessHandle.current().info().toString());
 ProcessHandle.allProcesses().filter(ph -> ph.info().command().isPresent()).limit(4).forEach(p -> System.out.printf("Process id: %s%n", p.info().toString()));
 
 //--
 StackWalker.getInstance(java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass()
 StackWalker.getInstance(java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE).forEach(System.out::println)
 ```
## You can have private static method as helper method inside interface
## Multi-release JAR files
```
final FileInputStream fis = new FileInputStream("movie.mp4");

try (fis) 
{
//dodo
} 
catch (IOException e) 
{
  // ...
}
```

```jdk11
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>2.4.0-b180608.0325</version>
</dependency>
```

## Modern Java Issues
### [ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.7.0:compile (default-cli) on project auth-web-basic: Fatal error compiling: java.lang.ExceptionInInitializerError: com.sun.tools.javac.code.TypeTags -> [Help 1]
```xml
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.18.4</version>
		</dependency>
```
### [Error] XML parser not in JDK

<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.2.11</version>
</dependency>
<dependency>
    <groupId>com.sun.xml.bind</groupId>
    <artifactId>jaxb-core</artifactId>
    <version>2.2.11</version>
</dependency>
<dependency>
    <groupId>com.sun.xml.bind</groupId>
    <artifactId>jaxb-impl</artifactId>
    <version>2.2.11</version>
</dependency>
<dependency>
    <groupId>javax.activation</groupId>
    <artifactId>activation</artifactId>
    <version>1.1.1</version>
</dependency>


## Flow References
* [Reactive Streams example - Java 9](https://aboullaite.me/reactive-streams-example-java-9/)
* [Java 9 Features with Examples](https://www.journaldev.com/13121/java-9-features-with-examples)
* [Reactive Streams In Java 9](https://www.javagists.com/reactive-streams-java-9)
* [Publish-Subscribe mit der Flow-API in Java 9](https://blog.oio.de/2018/05/04/publish-subscribe-mit-der-flow-api-in-java-9/)
* [Java 9 series: Concurrency Updates](https://www.voxxed.com/2016/10/java-9-series-concurrency-updates/)
* [Java Platform, Standard Edition What’s New in Oracle JDK 9](https://docs.oracle.com/javase/9/whatsnew/toc.htm)
* [Reactive Programming with JDK 9 Flow API](https://community.oracle.com/docs/DOC-1006738)
* [The Essential Java 9 Feature You Probably Never Heard Of](https://blog.takipi.com/the-essential-java-9-feature-you-probably-never-heard-of/)
* [JEP 266: More Concurrency Updates](http://openjdk.java.net/jeps/266)
* [Reactive Streams in Java 9](https://dzone.com/articles/reactive-streams-in-java-9)
* [Java 9 Flow API – Reactive Streams](https://grokonez.com/java/java-9/java-9-flow-api-reactive-streams)
* [](https://docs.oracle.com/javase/9/docs/api/java/util/concurrent/Flow.html)