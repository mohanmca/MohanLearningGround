```find the jar of the className
java -verbose:class -classpath $(echo *.jar | sed ‘s/ /:/g’)  com.anything.yourclass | grep “yourclass”
```

```Quick analysis for xception handling in java code
find . -name \*java | grep -v “test.*est” | xargs grep -A 4 “catch.*xception” > exceptionHandling.txt
grep -A 4 catch.*xception `find . -type f -name \*java | grep -v test` > xception.log
```