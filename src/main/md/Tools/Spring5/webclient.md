## Spring webclient

* [WebClient API](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html)


## [Cookie Management in WebClient](https://stackoverflow.com/questions/55557623/cookie-management-for-webflux-webclient)

```java
WebClient webClient = WebClient.create("https://remoteServer");
MultiValueMap<String, String> myCookies = new LinkedMultiValueMap<String, String>()

webClient
  .post()
  .uri("uri/login")
  .body(Mono.just(myLoginObject), MyLogin.class)
  .exchange()
  .subscribe(r -> 
      for (String key: r.cookies().keySet()) {
        myCookies.put(key, Arrays.asList(r.cookies().get(key).get(0).getValue()));
      }
   );

webClient
  .post()
  .uri("/uri/data")
  .cookies(cookies -> cookies.addAll(myCookies))
  .body(....)
  .exchange();
```