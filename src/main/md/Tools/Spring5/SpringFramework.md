# Spring-Core Framework

* Gloryfied hashmap (applicationContext) that maintains toplogy of beans
* 

* Java.lang.Object is surrogated with 3 features
  * Dependency Injection
  * Aspect Oriented Programming
  * Enterprise Service Abstractions



* STS - Is a tool (Eclipse based)


##  Enterprise Service Abstractions

* Cacheable annotation can cache the result of method invocation
  * @Cacheable("customers")
    getCustomerById(long customerId)
  * You can plug any cache provider
* Controller
  * @Controller - Spring automatically connect methods in the controller bean to an http-endpoint
* Configuration
  * Toplogy between beans are specified via config (xml, annotation or JavaConfig)
  * Toplogy - how one bean related to other beans
  * In xml, every bean has an id and type
  * In JavaConfig - Spring assigns id
    * All @Bean annotated methods are invoked and keep the bean inside applicationContext (not lazy)
* @PropertySource - can load all environment properties or external configuration files    
* By default, Spring in UnOpinionated, hence we have explicitly turn-on the feature using annotations
  * Annotation would require other annotations to enable annotations
  * @EnableTransactionManger (without this Transaction annotations won't be effective)
  * @ComponentScan (without this @Repository, @Controller won't be effective)
  

## Configuration
* ClassPathXmlApplicationContext("service-config.xml")
* FileSystemXmlApplicationContext(new File("/service-config.xml"))
  * XML - all configruation at one-place is good, no compile time checking is bad.
* @Configuration
  * Compile time checking, but not a single location to view the config, a bit of boiler-plate code for simple beans
* AnnotationConfigApplicationContext(ServiceConfigurationPackage.class)
  * AnnotationConfigApplicationContext(ServiceConfigurationPackage.class.getPackage().getName())
  * Nice compromize between xml and @Configuration, Reduces boiler plate @Bean declarations
  * Auto detects beans from package using @ComponentScan

## Injections (3 ways to do)
* @Autowired
  * Classic spring annotaion for injection
* @Inject
  * Java.Inject
* @Resource
  * JSR - 250 based annotation

## Beans
* Bean lifecycle callbacks
* Bean scopes
* Bean post-processors (BeanFactory methods)
* AOP - Add new features to existing beans using AOP


# Bean lifecycle callbacks
* We have three options (interface, SmartLifeCycle interface, annotation)
* Interface like InitializingBean or DisposableBean - Spring-legacy way
  * init-method, andd destroy-method
  * InitializingBean gives access to afterPropertiesSet()
  * DisposableBean gives access to destroy()
* @PostConstruct, @PreDestroy

# Bean lifecycle callbacks (SmartLifeCycle interface)
* start(<<Optional Runnable>>)
* stop(<<Optional Runnable>>)
* isAutoStartup()
* isRuning()

# Bean scopes
* Default Singleton
* Prototype 
* @Scope("request") - Custom new instance for every http request @Scope("request")
* @Scope("session")
* org.springframework.beans.factory.config.Scope - itself a contract (and bean) within Spring Framework
  * ConversationId -- could be sessionId or any custom scope id
* org.springframework.beans.factory.config.CustomScopeConfigurer (resolves for request or session)
* @org.springframework.context.annotation.Scope("thread")
  * ThreadAnnouncer

## Reference
* [Josh Long - Spring Core](https://github.com/joshlong/a-walking-tour-of-all-of-springdom)
