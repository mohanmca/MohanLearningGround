## Security

* Castle approach / Multiple layers of defense
* Browser, Url, Classes and methods

## Lombok
* @Getter
* @Setter
* @AllArgsConstructor
* @RequiredArgsConstructor
* @EqualsAndHashCode
* @Delegate
* @Cleanup
* @Builder

### Delombok
* Can be used to learn the effect of Lombok

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
  * Authentication filter generates AutenticationRequest
  * Authentication filter delegates AutenticationMnager to authenticate AutenticationRequest
  * There are many flavours of tokens are there
  * UsernamePasswordAuthenticationToken
  * OpenIDAuthenticationToken
* Filter delegates authentication to AuthenticationManager
* AuthenticationManager delegates to one ore more AuthenticationProvider
  * OpenIDAuthenticationProvider
  * DaoAuthenticationProvider
  * LdapAuthenticationProvider
  * Provider delegates to UserDetailsService
  * Identity store is accessed by UserDetailService
  * Every identity service requires its own Service and AuthenticationProvider
  * DigestAuthenticationFilter is an exception, it doesn't delegates rather directly interacts with UserDetailsService
* 
  ```java  
  public interface AuthenticationManager
  { 
    public Authentication authenticate(Authentication authentication)  throws AuthenticationException
  }
  ```
* 
  ```java  
    public interface Authentication
    { 
      public boolean isAuthenticated();
      public boolean getPrincipal();
      Object getCredentials(); //password or ssl
      Collection<? extends GrantedAuthority> getAuthorities();
    }
  ```
* 
  ```java  
    public interface AuthenticationProvider
    { 
      public Autentication authenticate(Aunthentication authentication) throws AutenticationException
      public boolean supprts(class<?> authentication) 
    }
    public interface UserDetailsInterface{
      UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    }
  ```

## Reference
* Spring security authentication/authorizaion - building effective layers of defense - pluralsite course
* [Spring Security Primer](https://spring.io/guides/topicals/spring-security-architecture/)
