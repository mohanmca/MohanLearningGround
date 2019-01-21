## Security
* Secure the core service
* Add filter that would validate for security token or redirect for authentication
* Add all service interceptor and populate with security token to probagate security to server
  * For example, this would probagate security token to servers - UserFeignClientInterceptor implements RequestInterceptor

##  OAuth Token based security
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
* ResourceServerConfig should match with security header that comes in request
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
* All the service invocation should pass the token to the servers, let us add code to interceptor
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