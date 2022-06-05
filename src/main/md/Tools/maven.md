## Often used maven command

```bash
mvn clean package -Dmaven.test.skip=true
mvn clean package -Dmaven.test.skip=true -Dmaven.test.failure.ignore=true -Dmaven.test.haltafterfailure=no
mvn dependency:tree -Dverbose -Dincludes=commons-collections
mvn assembly:single
mvn -Dmaven.surefire.debug test –default remote debug port 5005
mvn -Dmaven.surefire.debug=”-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 -Xnoagent -Djava.compiler=NONE” test
mvn exec:java -Dexec.mainClass="com.nikias.App"
```

## Find dependency tree of any artifact without creating pom

```bash
mvn com.github.ferstl:depgraph-maven-plugin:3.3.0:for-artifact -DgroupId=org.jboss.ws -DartifactId=jbossws-common-tools -Dversion=1.3.2.Final -DgraphFormat=text -DshowGroupIds=true -DshowVersions=true
```

## mvnDebug is handy.
* set MAVEN_DEBUG_OPTS="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000"

## Configuring resources directory
```xml
 <build>
   <resources>
     <resource>
       <directory>[your folder here]</directory>
     </resource>
   </resources>
     <resource>
       <directory>resource3</directory>
     </resource>
</build>
```

## Configuring test-scope and program
```xml
        <configuration>
          <executable>java</executable>
          <arguments>
            <argument>-classpath</argument>
            <argument>--module-path</argument>
            <classpath/>
            <argument>com.example.Main</argument>
          </arguments>
          <classpathScope>test</classpathScope>          
        </configuration>
```

## Maven dependency related issues troubleshoot
1. When wrong dependency were introduced
   1. NoSuchMethodError
   2. NoSuchFieldError
   3. NoClassDefFoundError
2. mvn dependency:tree -Dverbose
3. mvn dependency:tree -Dverbose -Dincludes=com.google.guava:guava
4. Dependency
   1. Maven nearest dependency wins
   2. classpath - first class wins
5. Always use Maven enforcer
   1. mvn enforcer:enforce -Drules=alwaysPass,alwaysFail -- without using enforcer in the pom.xml
   2. https://maven.apache.org/enforcer/enforcer-rules/requireSameVersions.html
6. Use exclusions to remove un-necessary versions, and avoid transitive dependency
7. Never add SNAPSHOTS to dependency managements
8. Maven Shade plugin can rename the library package at byte-code level, so we can use two different versions of same library
   1. [Shade plugin](https://maven.apache.org/plugins/maven-shade-plugin/)
    
## References
* http://maven.apache.org/maven-1.x/plugins/test/properties.html
* [Google Best Practices for Java Libraries](https://jlbp.dev/)
* [Cocurrent JUnit test runner for Parameters](http://stackoverflow.com/questions/10141648/concurrent-junit-tests-with-parameters)
* [JUnit parallel](http://java.dzone.com/articles/running-junit-tests-parallel)
* [Using Maven in IntelliJ IDEA](https://www.youtube.com/watch?v=D1sRK8JLCQ4)
* [Understanding Apache Maven – The Series](https://cguntur.me/2020/05/20/understanding-apache-maven-the-series/)