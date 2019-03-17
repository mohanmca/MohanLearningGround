## Give an example for local variable type inference
```java
var list = new ArrayList<String>();  // infers ArrayList<String>
var stream = list.stream();          // infers Stream<String>
```

## Additional local-variable syntax for lambda parameters
```java
list.stream()
      .map((@Notnull var s) -> s.toLowerCase())
      .collect(Collectors.toList());
```

## What are the changes to HTTP Client
```java
// Additional package java.net.http
java.net.http.HttpClient
java.net.http.HttpRequest
java.net.http.HttpResponse
java.net.http.WebSocket
```

## How to negate predicate in Java-11
```
lines.stream()
      .filter(Predicate.not(String::isBlank))
```

## What are sequence of https protocol?
* Browser requests a secure page with https url
* WebServer sends it's public-key with server certificate
* Browser ensures the certifcate is valid
  * Certificate is not expired
  * Not revoked
  * Issued by a Trusted 3rd party
* Browser creates a Symmetric Key and sends it to the Server (after encrypting using webserver's public-key)
* WebServer decrypts the symmetric key using its private key
* WebServer sends page encrypted with the Symmetric key (of the client)
* Browser decrypts the page using symmetric key and displays the content

## How to configure passwordless ssh on the server?
1. Create keys on the client machine
   * ssh-keygen -t rsa
   * ~/.ssh/id_rsa and ~/.ssh/id_rsa.pub
2. copy id_rsa.pub on remote host
3. Edit your sshd_config file on the remote server
  * Append the contents of id_rsa.pub to ServerHome/.ssh/authorized_keys
  * cat id_rsa_of_client.pub >> ServerHome/.ssh/authorized_keys
  * File permission should be 0600 ServerHome/.ssh/authorized_keys  
