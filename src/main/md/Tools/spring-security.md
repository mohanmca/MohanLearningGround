## Security

* Castle approach / Multiple layers of defense
* Browser, Url, Classes and methods

## Lombox
# @Getter
# @Setter
# @AllArgsConstructor
# @RequiredArgsConstructor
# @EqualsAndHashCode
# Delombok
# @Delegate
# @Cleanup
# @Builder


## Spring Security
* Heavy use of filter
* [Filter](https://tomcat.apache.org/tomcat-9.0-doc/servletapi/javax/servlet/http/HttpFilter.html)
 * init(servletcofig)
 * doFilter(req, res)
 * destrory()
* [DelegatingFilterSecuity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/DelegatingFilterProxy.html)
  * It would forward all the request to other spring security filter
  * Other security filter would be responsible for authentication/authorization
  * registered in web.xml
* DelegatingFilterSecuity delegates to FilterChainProxy
* FilterChainProxy delegates to SecurityFilterChain
* Sample XML
```xml
  <filter>
    <filter-name>springSecurityFilterChain</filter-name>
    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>springSecurityFilterChain</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
```
* There could be multiple filter chain for different url pattern
  * /portfolio - BasicAuthenticaionFilter
  * /admin - DigestAuthenticaionFilter
* [SecurityFilterChain](https://docs.spring.io/spring-security/site/docs/3.0.x/reference/security-filter-chain.html)
  * 
  ```java
    interface SecurityFilterChain{
      boolean matches(HttpServletRequest req);
      List<Filter> getFilters();
    }
  ```
* data class SecurityContext(auhtentication)
* data class SecurityContextHolder(securityContext)
* FilterChain
  * SecurityContextPersistenceFilter
  * AuthenticationFilters
    * Basic
    * Digest
    * OIDC
  * RememberMeAuthenticationFilter
  * AnonymousAuthenticationFilter
  * ExceptionTranslationFilter
  * FilterSecurityInterceptor
    * Interceptor performs authroization
* SecurityContextPersistenceFilter
  * Manages security context
  * Tries to find securityContext from SecurityContextRepository
  * In WebApplication SecurityContextRepository is Session using HttpSessionRepository
  * SecurityContextHolder is ThreadLocal


## Authentication
* Many flavours of authentication
  * BasicAuthenticationFilter
  * OpenIDAuthenticationFilter
  * DigestAuthenticationFilter
  * UsernamePasswordAuthenticationFilter
* Authentication filter intercepts requests and extracts authenticationToken
  * There are many flavours of tokens are there
  * UsernamePasswordAuthenticationToken
  * OpenIDAuthenticationToken
* Filter delegates authentication to AuthenticationManager
* AuthenticationManager delegates to AuthenticationProvider
  * OpenIDAuthenticationProvider
  * DaoAuthenticationProvider
  * LdapAuthenticationProvider
* 
  ```java  
  public interface AuthenticationManager
  { 
    public Authentication authenticate(Authentication authentication)  throws AuthenticationException
  }
  ```
* Authentication
```java  
  public interface Authentication
  { 
    public boolean isAuthenticated();
    public boolean getPrincipal();
    Object getCredentials();
    Collection<? extends GrantedAuthority> getAuthorities();
  }
```

## Reference
* Spring security authentication/authorizaion - building effective layers of defense - pluralsite course
* [Spring Security Primer](https://spring.io/guides/topicals/spring-security-architecture/)