# What are microservices?
* Do one thing well!
* The phrase “Micro-Web-Services” was first used at a cloud computing conference by Dr. Peter Rodgers in 2005
* It is not new, May-2011 - first time this term was used
* Composing application like unix like business services
* Fine grained web service @ webscale by Adrian Cockcroft
* Martin Fowler and  James Lewis - Micro Service article


# Spring Boot
* Introduced in 2014, and removed configuration
* Build executable jar instead of war (directly execute)
* Spring Boot 2 - Reactive based
* [Spring Initializr](start.spring.io)
* @RepositoryRestResource - Can expose DAO on Rest end points
* Feign - Declarative REST clients with spring-cloud-netflix Feign
* Zuul - Intelligent and programmable routing with spring-cloud-netflix Zuul
* Hystrix - Circuit breaker with spring-cloud-netflix Hystrix


# JHipster documentations
* [Jhipster Presentation] (https://www.jhipster.tech/presentation/#/)



# JHipster
* JHipster 5 = JHipster + React + Spring Boot 2 
* KeyCloak - Open Source Identity and Access Management
* JHispter mini-book is written using ASCIDoctor
```bash
npm install -g yo generator-jhipster or yarn global add generator-jhipster
yarn global add e2e
yo jhipster
npm start
./mvnw spring-boot:run
./gradlew bootRun #alternative to mvn
jhipster entity Foo User
```

## Tutorial for eureka-server
* Add 		<defaultGoal>spring-boot:run</defaultGoal> in pom.xml, as part of <build> for default goal
* Alternatively using  ./mvnw spring-boot:run if you don't have default goal
* Access Eureka service via http://localhost:8761/

## Add "beer-catalog-service"
* Generate project using following
  * Actuator for monitoring
  * JPA, H2
  * Rest repostitories
  * Devtools
  * Lombok - Java annotation library which helps to reduce boilerplate code and code faster 


# Reference
* [Microservices](https://martinfowler.com/articles/microservices.html)
* Play by Play: Developing Micorservices Matt Raible 
* [Get Started with JHipster 4](http://www.eclipse.org/community/eclipse_newsletter/2017/january/article3.php)
* [The JHipster Mini-Book 4.5](https://www.infoq.com/minibooks/jhipster-4x-mini-book)
* [Microservices-for-the-masses-with-spring-boot-angular-and-jhipster-codeone-2018](https://speakerdeck.com/mraible/microservices-for-the-masses-with-spring-boot-angular-and-jhipster-codeone-2018)
* [spring-boot-microservices-example](https://github.com/oktadeveloper/spring-boot-microservices-example)
* [Security with spring-boot-microservices-example  - Okta Oauth branch](oktaoath@https://github.com/oktadeveloper/spring-boot-microservices-example)
* [Build a Microservices Architecture for Microbrews with Spring Boot](https://developer.okta.com/blog/2017/06/15/build-microservices-architecture-spring-boot)
* spring.io/guides
* https://asciidoctor.org/