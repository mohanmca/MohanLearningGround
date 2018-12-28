## Often used maven command

```bash
mvn clean package -Dmaven.test.skip=true
mvn clean package -Dmaven.test.skip=true -Dmaven.test.failure.ignore=true -Dmaven.test.haltafterfailure=no
mvn dependency:tree -Dverbose -Dincludes=commons-collections
mvn assembly:single
mvn -Dmaven.surefire.debug test –default remote debug port 5005
mvn -Dmaven.surefire.debug=”-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 -Xnoagent -Djava.compiler=NONE” test
```

## mvnDebug is handy.
* set MAVEN_DEBUG_OPTS="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000"

## References
* http://maven.apache.org/maven-1.x/plugins/test/properties.html
* [Cocurrent JUnit test runner for Parameters](http://stackoverflow.com/questions/10141648/concurrent-junit-tests-with-parameters)
* [JUnit parallel](http://java.dzone.com/articles/running-junit-tests-parallel)