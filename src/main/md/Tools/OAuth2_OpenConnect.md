## How to create anki from this boot mock question file

mdanki OAuth2_OpenConnect.md OAuth2.apkg --deck "Work::Mohan::OAuth2"

## What does OAuth2.0 sovles

* Delegated authorization
* It was only designed to solve authorization
* social-network companies hacked and added their own layer
* OIDC - OpenId Connect - added common layer on top of OAuth2.0 to serve authentication purpose


## Authentication history

* Cookie based authentication
* SAML (Distributed webservice was driving force)

## Social network challenge

* How do I allow xyz-website to access my data without my password
* How do I allow news-website to access my facebook data without my facebook password

## OAuth 2.0 terminology

* Resource Owner - (Example: FB user)
* Client - (Yelp/Stackoverflow/Cousersa)
* Authorization server - (accounts.google.com/accounts.fb.com)
* Resource server - (contact.google.com/calendar.google.com)
* Authorization grant - Proof that user accepted "client application" to access underlying resource
  * Client can specify the type of "Authorization Grant"
* Redirect URI - call-back point
* Access Code - given by accouts.google.com
  * Client submits access-code to Authorization-server and get access Token
* Access Token (whole point of OAuth2.0)
  * Should be treated like password
* Scope - (Email.Read/Email.Write/Contacts/Chat/)

## OAuth 2.0 terminology - extra

* Back Channel (highly secure) (server to server)
  * Access code to access token exachange only happen via back-channel
* Front Channel (less secure) (browswer based)
  * View source code is possible
  * Dev-Tools and network tools can view the data
  * Someone else can view over the shoulder


## OAuth 2.0 flow

* Authroization code (front channel + back channel)
* Implicit (front channel only)
* Resource owned password credential (back channel only)
* Client credentials (back channel only)



## What is password equivalent in Oauth?

* Access Token (whole point of OAuth2.0)
  * Should be treated like password
* Don't store acess token on the browswer

# What is the role of Authorization server?

* Creating one time client-Id and client-secret (bearer token)
* Client-Id - uniquely idenfies the consumer of the API (Coursera/Yelp/Other 3rd party website)


## What is the role of Resource server?

* Validate "Access Token"
* Use token scope for authorization



## How does authorization server ui-screen has shows list of scopes

* Client-application requsets them


## What is bearer token?


* Bearer tokens are like cash
* Give the bearer of this token access. Bearer of the token doesn't require PoP (Proof of possession)
* PoP - is similar to MFA
* Bearer tokens are created part of onboarding - It is one time process. Should be kept in valet and secured
* Header for HTTP (Authorization: Bearer <access_token>)
```yaml
POST /rsvp?eventId=123 HTTP/1.1
Host: events-organizer.com
Authorization: Bearer AbCdEf123456
Content-Type: application/x-www-form-urlencoded
User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/1.0 (KHTML, like Gecko; Gmail Actions)

rsvpStatus=YES
```

## What is PKCE?

* PKCE (RFC 7636) is an extension to the Authorization Code flow to prevent several attacks and to be able to securely perform the OAuth exchange from public clients.
* It is used in most of the mobile-application


## What are all the OAuth tools

* OAuth debugger
* https://oauth.tools/collection/1610694071233-2wYOA
* https://oauthdebugger.com/


## What are additional enrichment on OAuth2.0 using OpenId Connect

* OpenId Connect - Initiative to enrich OAuth2.0 to support Authentication
* It supports following 4 details related to authentication
  * ID Token
  * Userinfo endpoint for getting more user information
  * Standard set of scopes
  * Standardized Implementation

## What is ID Token

* JSON Web token (JWT)
* It has few segments
* Header segment
* Details about user segment
* Signature segment
* We can validate the JWT details again user-info endpoint
  * Example: Example: http://www/googleapis.com/oauth2/v4/userinfo

## What are all the HTTP Authorization schmes are there?

* Authorization: <type> <credentials>
* Type: Basic/Bearer/Digest/

## How to set the token?

```C#
   Byte[] credential = System.Text.Encoding.UTF8.GetBytes($"{yourusername}:{yourpwd}");
   String BasicHeader = Convert.ToBase64String(credential)
   request.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", BasicHeader);
```

```C#
   httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", "Your Oauth token");
```


## References

* [Open connect  - Nate Barbettini](https://youtu.be/996OiexHze0)
* [https://speakerdeck.com/nbarbettini/oauth-and-openid-connect-in-plain-english](Slides)
* [REST vs gRPC vs GraphQL](https://speakerdeck.com/nbarbettini/api-throwdown-rpc-vs-rest-vs-graphql?slide=49)