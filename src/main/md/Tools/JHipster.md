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

## Toenable security
* Add following configuration on Rest Server serivce
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security.oauth.boot</groupId>
            <artifactId>spring-security-oauth2-autoconfigure</artifactId>
            <version>2.0.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-security</artifactId>
        </dependency>
```
* Add following configuration on Rest Server serivce
```properties
zuul.sensitive-headers=Cookie,Set-Cookie
hystrix.shareSecurityContext=true

security.oauth2.client.client-id=0oadx7zrsjjgIMgQZ0h7
security.oauth2.client.client-secret=vcBjfdFw9rDUsWGasf0ramwfSu_xHfENWWEinq-y
security.oauth2.client.access-token-uri=https://dev-158606.oktapreview.com/oauth2/default/v1/token
security.oauth2.client.user-authorization-uri=https://dev-158606.oktapreview.com/oauth2/default/v1/authorize
security.oauth2.client.scope=openid profile email
security.oauth2.resource.user-info-uri=https://dev-158606.oktapreview.com/oauth2/default/v1/userinfo
```
* Add anotation of @EnableOAuth2Sso on Secured applicaton
* Add interceptor to the application
```Java
    @Bean
    public RequestInterceptor getUserFeignClientInterceptor() {
        return new UserFeignClientInterceptor();
    }
```
* Add the interceptor code
```java
package com.example.edgeservice;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate template) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, details.getTokenValue()));
        }
    }
}
```
* Add SecurityConfig
```java
package com.example.edgeservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .requestMatcher(new RequestHeaderRequestMatcher("Authorization"))
            .authorizeRequests()
            .antMatchers("/**").authenticated();
    }
}
```

* Add WebSecurityConfigure
```java
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .httpBasic();
    }
}
```

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