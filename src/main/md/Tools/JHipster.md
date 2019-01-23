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

## Security
* OAuth 2.0 and OIDC
* Having a secret in SPA is big anti-pattern
* Find provider like Keycloak or Okta
* Spring security 
  * Handles security redirect
  * Intercepts
  * Sets security session
* OAuth
  * Delegated authentication system using 3rd party provider.
  * Authenticate using 3rd party, but not authentication prototocol.
  * Obtain ID Token and/or access token
  * No way to get user information in OAuth
* Open Id Connect : Facebook, LinkedIn, Google or Microsoft
  * Built on top of OAuth
  * JWT - is just a string token (pronounced as JAAT) Json web token
  * User-end-point is returned, we can find more information about the user

# JHipster documentations
* [Jhipster Presentation] (https://www.jhipster.tech/presentation/#/)



# JHipster
* Goals
  * High performance, Robust Java service side Spring Boot application
  * Mobile first front using React/Angular and BootStrap
  * Microservice using Netflix-OSS, ELK and Docker
  * Powerful workflow build using NPM, Webpack, Yeoman and Maven/Gradle
* UAA - User authentication and authorization server // JHipster can generate  
* Always generate Gatway server
  * Everything related to UI sits on the gateway
  * Uses zull and hystrix
  * Handles/Proxies all backend microservices  
* JHipster 5 = JHipster + React + Spring Boot 2 
* KeyCloak - Open Source Identity and Access Management
* Subgenerator is a upgrade tool for JHipster
* .yo-rc.json - Contains all the selection made, This file could be used to upgrade/generate using newer JHipster
* JHispter mini-book is written using ASCIDoctor
```bash
npm install -g yo generator-jhipster or yarn global add generator-jhipster
yarn global add e2e
yo jhipster
npm start
./mvnw spring-boot:run
./gradlew bootRun #alternative to mvn
jhipster entity Foo User
yarn e2e #Test end-2-end
yo jhipster:entity
jhipster entity #(alternative to above syntax)
```
* Architecture: ![Micoservices Architecture][Arch]




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

## Progressive Web App
* Mobile first web development
* Answer to slow internet connection, and web-app has to load in less than 3 seconds
* Firefox supports using service worker, service worker can intercept network call, and could provide offfline content
* Google trying to reduce number of mobile-apps
* Use PRPL pattern
  * Push critical resources for the initial URL route
  * Render iniital route
  * Pre-cache remaining route
  * Lazl-load and create reamining routes on demand

# Reference
* [Microservices](https://martinfowler.com/articles/microservices.html)
* (https://www.jhipster.tech/microservices-architecture/)
* Play by Play: Developing Micorservices Matt Raible 
* [Get Started with JHipster 4](http://www.eclipse.org/community/eclipse_newsletter/2017/january/article3.php)
* [The JHipster Mini-Book 4.5](https://www.infoq.com/minibooks/jhipster-4x-mini-book)
* [Microservices-for-the-masses-with-spring-boot-angular-and-jhipster-codeone-2018](https://speakerdeck.com/mraible/microservices-for-the-masses-with-spring-boot-angular-and-jhipster-codeone-2018)
* [spring-boot-microservices-example](https://github.com/oktadeveloper/spring-boot-microservices-example)
* [Security with spring-boot-microservices-example  - Okta Oauth branch](oktaoath@https://github.com/oktadeveloper/spring-boot-microservices-example)
* [Build a Microservices Architecture for Microbrews with Spring Boot](https://developer.okta.com/blog/2017/06/15/build-microservices-architecture-spring-boot)
* spring.io/guides
* https://github.com/mraible/jhipster5-demo
* https://asciidoctor.org/
* https://scotch.io/tutorials/the-ultimate-guide-to-progressive-web-applications

[Arch]: ../img/microservices_architecture_2.png "JHipster micoservices architecture"  
