## Keeping up to date with Java

* [Google Guava](https://github.com/google/guava/wiki)
* [Junit 5 user guide](https://junit.org/junit5/docs/current/user-guide/)
* [mocking framework for unit tests in Java](https://site.mockito.org/)
  * [Mockito user guide](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
  * [Mockito](https://dzone.com/refcardz/mockito)
* [Trending in Java](https://github.com/trending/java)
* [Java 9 to 17](https://www.javaworld.com/article/3164262/java-language/java-9s-other-new-enhancements-part-1.html)
* [doov](https://github.com/doov-org/doov)
* [DSL Doov](https://static.rainfocus.com/oracle/oraclecode18/sess/1525874149985001Q3XY/PF/DSL.using%28java%29.toGoBeyond%28BeanValidation%29.at%28OracleCode%29%3B_1530359519477001SbsB.pdf)

## Google Guice
  * Fluent API, Type safe
  * Failure validation is human readable
* # [Guice](https://github.com/google/guice)
* Guice : Module => Inector
* Injector::injectMembers = (Object instance) => void
* Injector::getInstance:: = (Class<T> type) => T
* Guice can't inject non-static-inner class


# Code read
* com\google\inject\internal\util
  * Classes - find if concrete, abstract, find member types