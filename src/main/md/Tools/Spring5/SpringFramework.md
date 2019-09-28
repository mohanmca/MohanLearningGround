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
  * ConversationId - Can be treated like "cacheRegion", it invalidates automatically when conversation ends
    * HTTPRequest, Session (scope)
    * This is better than Singleton as the scope is narrower
* org.springframework.beans.factory.config.CustomScopeConfigurer (resolves for request or session)
* @org.springframework.context.annotation.Scope("thread")
  * ThreadAnnouncer

## Introduce new behaviour using BeanFactory PostProcessors
* Add/change behaviour of the beans
* Overrides/Viteos the actual behaviour using proxy layer
* Spring itself uses this feature a lot
* It can change before object is constructed, but can't do anything about constrcution process itself
* Possible to add auditing layer using this feature
* Spring MVC binds methods to HTTP_ENDPOINT, this feature was implemented by Beanfactory PostProcessor
* BeanFactoryPostProcessor implementation should be returned via staic method in Configuration class (as it should be loaded before all the objects are created.)
* All BeanFactoryPostProcessor should be static
* BeanFactoryPostProcessor method interceptor better to check for annotation (instead of blanket feature)
  *  For example, check for @Timed annotation, so we can add time method invocation
* https://github.com/joshlong/a-walking-tour-of-all-of-springdom/blob/master/ioc/src/main/java/com/joshlong/spring/walkingtour/ioc/manybeans/bfpp/SoxComplianceSuite.java  

## Reference
* [Josh Long - Spring Core](https://github.com/joshlong/a-walking-tour-of-all-of-springdom)
* [Proxy method logger example](https://github.com/joshlong/a-walking-tour-of-all-of-springdom/blob/master/ioc/src/main/java/com/joshlong/spring/walkingtour/ioc/manybeans/bpp/MethodTimeLoggingBeanPostProcessor.java)
* [AspectJ Auto Proxy - AOP](https://github.com/joshlong/a-walking-tour-of-all-of-springdom/blob/master/ioc/src/main/java/com/joshlong/spring/walkingtour/ioc/manybeans/aop/MethodTimeLoggingAspect.java)

## Introduce new behaviour using AOP (not beanfactory)
* No convenient way to introduce behaviour to all the methods in OOP (failure of OOP?)
* PointCut
  * Pattern of Objects and Methods
  * Globs 
    * Example: beginTxn* (methods), execute* (methods)
* JointPoint - Current method under execution

## How to get reference of object managed outside the Spring Container
* FactoryBean
  * FactoryBean{ T getObject(); Class<?> getObjectType(); boolean isSingleton() }
  * [FactoryBean](https://github.com/spring-projects/spring-framework/blob/master/spring-beans/src/main/java/org/springframework/beans/factory/FactoryBean.java)


## TaskExecutorFactoryBean
* [TaskExecutorFactoryBean](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/main/java/org/springframework/scheduling/config/TaskExecutorFactoryBean.java)

## SpEL
* Spring Expression Language
* [Spel Config](https://github.com/joshlong/a-walking-tour-of-all-of-springdom/blob/master/ioc/src/main/java/com/joshlong/spring/walkingtour/ioc/strangebeans/spel/Config.java)
*  "#{ T(Math).random()}"

## Spring Bean Profiles
* We may need to use bean with some setting in development and some other settings in production
  * Spring Profile is there to rescue
  * InMemory during testing/developement, whereas something concrete while on production
* Bean would be available only when profile related to it is active
* We can tag bean with - @Profile("embedded"), @Profile("dev"), @Profile("production")
* We need to pass the profiles.active properties - -Dspring.profiles.active=embedded
* applicationContext.getEnvironment().setActiveProfiles("default") - Api to activate profile
* We can pass multiple value comma separated -Dspring.profiles.active=embedded,dev,uat


## Spring Configuration
* We can split configuration and import and aggregate them
* org.springframework.core.env.Environment has all the environment varaibles
* org.springframework.core.env.PropertyResolver
  * Works with PropertySources
  * PropertSources -> PropertisFiles, JNDI, Env
  * We can extend PropertSources to read from rdbms tables

## Spring Manage threads, Scheduling task
* JEE supports Workmanager API - javax.resource.spi.work.WorkManager
* JSDK doesn't have equivalent to that.
* Spring TaskExecutor - predates Java5
* It works in any container and/or app-server based on the environment
* Example for Glassfish Spring implements - https://github.com/ndimiduk/spring-framework/blob/master/org.springframework.transaction/src/main/java/org/springframework/jca/work/glassfish/GlassFishWorkManagerTaskExecutor.java
* @EnableAsync - Method can return TaskExecutor
  * @Async annotation for any method, would leads to asnchronous call
  * @Async method call would be invoked inside runnable, caller will not be blocked
* @EnableScheduling
  * @EnableScheduling will search for TaskScheduler inside the configuration
  * @ConcurrentTaskScheduler, @ThreadPoolTaskScheduler, @TimerMangerTaskScheduler
  * Enables 3 kinds of scheduling
  * We can annotate any method with Cron confiugration
    * @Schduled(cron="*/10 * * * * *")
    * @Schduled(fixedRate=15 * 1000)  // Every 15 seconds, doesn't worry about how long it takes
    * @Schduled(fixedDelay=20 * 1000) // Every 20 seconds  only if prior invocation finished

## Caching
* To increase performance, cache the prior result or data
* org.springframework.cache.{CacheManager, Cache}
* Backend adapters for EhCache, Gemfire, Coherence, JSR107, Redis
* Spring-data-redis provides redis support
* @EnableCaching works with CacheManger
* @Cacheable (for loaders and getters) and @CacheEvict (for delete and remove methods)
  * @Cacheable("customers")
  * @Cacheable(value="customers", condition="name.length<10")
    * condition="name.length<10" - is SpEL
    * customers is regionName or Cache name
  * @Cacheable(value="customers", key="id)
    * if method has multiple argument, still id would be considered as key ex: loadOwnersByIdAndName()
  * @CacheEvict("customers") (generall with delete method)

## Spring relational data access

### Relation data access problems

* Resource acquisition code will be boiler-plate code
* Mapping and converting between relational and java-bean
* There are multiple ways to access data, JDBC, JPA, JDO
* PrimaryKeyViolationException exception manifiest into multiple different exception based on persistence framework being used (JPA, JDO), but we need common exception
* Java has checkedException, but we may need non-checked exception to save rest of the layer
* Transaction might needs to be handled using different way based on transaction manager
* We might swtich from one vendor to another

### Solution provided by Spring

* Template Objects
* Peristence exception translation
* Transaction management
* Repositories
  * Knows how to access data from underlying data-storage
  * A repository is a datasource, it knows how to read from underlying storage, data access functionality
* Object Mapping and Serialization
  * JdbcObjectMapper, RowMapper
  * Hibernate Provides for JDBC, but spring provides generic one that could be used for any data-source


### Transaction Management

* begin; do n task; commitl iff error => rollbackl end;
* JTA (vendor API)
* JPA EntityManager API
* JDBC Connection and PreparedStatement

### Transaction Management - PlatformTransactionManger (and TransactionTemplate)

* Spring generic framework to support transaction
* PlatformTransactionManger{ TransactionStatus getTransaction(@Nullable TransactionDefinition definition); Commit(TransactionStatus status) ; rollback(TransactionStatus status)  }
* PlatformTransactionManager
  * (https://github.com/spring-projects/spring-framework/blob/master/spring-tx/src/main/java/org/springframework/transaction/PlatformTransactionManager.java)
  * JpaTransactionManager
  * JdoTransactionManager
  * JmsTransactionManager
  * HibernateTransactionManger
  * RabbitTransactionManager

### Transaction Management - TransactionTemplate

* TransactionTemplate requires TransactionManager (like jdbcTemplate requires dataSource)
* https://github.com/spring-projects/spring-framework/blob/master/spring-tx/src/main/java/org/springframework/transaction/support/TransactionTemplate.java
* To construct TransactionTemplate,  PlatformTransactionManger should be passed as an argument to TransactionTemplate
  ```java
  transactionTemplate.execute(new TransactionCallbackWithoutResult({
    public void doInTransactionWithoutResult(TransactionStatus status){
      jdbcTemplate.execute();
    }
  ));
```
* TransactionTemplate and TransactionManager - are too low -level, we can manage txns with annotations

### With Annotation (higher level, interally TransactionTemplate being used)

* @EnableTransactionManagement requires @Bean of type PlatformTransactionManger
* @Transactional annotation can be used to any method that requires annotation
* @Transactional requires @EnableTransactionManagement
  * Simlar to @Cacheable requires @EnableCaching
  * This pattern occurs in SpringFramework


### Database can be accesed via jdbcTemplate or Command Object (SimpleJdbcInsert)

* JDBC Command object is not as famous as jdbcTemplate
* Jdbc Command object is quite useful in certain cases, we can generalize few methods like generic inserts
* (https://github.com/joshlong/a-walking-tour-of-all-of-springdom/blob/master/services/src/main/java/com/joshlong/spring/walkingtour/services/jdbc/JdbcCustomerService.java)
* Multiple datasource hidden using spring data access framework
  * (https://github.com/joshlong/a-walking-tour-of-all-of-springdom/blob/master/services/src/main/java/com/joshlong/spring/walkingtour/services/CustomerServiceMain.java)
  * 



----------------------------
```java
public class MethodTimeLoggingBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(new TimeLoggingMethodInterceptor());
        factory.setTarget(bean);
        return (Object) factory.getProxy();
    }


    /**
     * logs the method invocation times
     */
    private class TimeLoggingMethodInterceptor implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {

            Object result = null;
            if (invocation.getMethod().getAnnotation(Timed.class) != null) {
                long start = System.currentTimeMillis();
                result = invocation.proceed();
                long stop = System.currentTimeMillis();
                System.out.println(invocation.getMethod().getName() + ": " + (stop - start) + "ms");
            } else {
                result = invocation.proceed();
            }
            return result;
        }
    }
}
```
---
```java
@Configuration
public class ServiceConfig {

    @Bean
    public TransferService transferService(AccountRepository accountRepository) {
        return new TransferServiceImpl(accountRepository);
    }
}

@Configuration
public class RepositoryConfig {

    @Bean
    public AccountRepository accountRepository(DataSource dataSource) {
        return new JdbcAccountRepository(dataSource);
    }
}

@Configuration
@Import({ServiceConfig.class, RepositoryConfig.class})
public class SystemTestConfig {

    @Bean
    public DataSource dataSource() {
        // return new DataSource
    }
}

public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SystemTestConfig.class);
    // everything wires up across configuration classes...
    TransferService transferService = ctx.getBean(TransferService.class);
    transferService.transfer(100.00, "A123", "C456");
}
```

// Command Object
```java

    public Customer createCustomer(String fn, String ln) {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("first_name", fn);
        args.put("last_name", ln);

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        simpleJdbcInsert.setTableName("customer");
        simpleJdbcInsert.setColumnNames(new ArrayList<String>(args.keySet()));
        simpleJdbcInsert.setGeneratedKeyName("id");

        Number id = simpleJdbcInsert.executeAndReturnKey(args);  // the ID of the inserted record.
        Long longId = (Long) id;
        BigInteger bigInteger = BigInteger.valueOf(longId);
        return getCustomerById(bigInteger);
    }

```