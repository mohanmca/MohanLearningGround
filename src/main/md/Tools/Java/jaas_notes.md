# JAAS Notes
 
* An implementation for a particular authentication technology to be used is determined at runtime. That implementation is specified in a login configuration file.
* java.security.Principal  - This interface represents the abstract notion of a principal, which can be used to represent any entity, such as an individual, a corporation, and a login id.
* Code for authenticating the user is very simple, consisting of just two steps:
* Instantiating a LoginContext
  1. javax.security.auth.login.LoginContext.LoginContext lc =new LoginContext("Sample",new MyCallbackHandler());
  1. First argument is the name of the configuration in Jaas configuration file
  1. LoginModule is housed in javax.security.auth.spi package
  1. LoginModule uses javax.security.auth.callback.CallbackHandler to obtain username and password. They are decoupled due to different channel involved in obtaining credentials
* Calling the LoginContext's login Method
* -Djava.security.auth.login.config==sample_jaas.config  - is used to configure Jaas security
* -Djava.security.policy==sampleacn.policy 

## Krb5LoginModule

* list of configuration options supported for Krb5LoginModule:
   1. refreshKrb5Config
   1. useTicketCache=[false] (default value is false)
      1.  /tmp/krb5cc_uid (on linux)
      1.  c:/users/linda/krb5cc_linda
   1. ticketCache=[false] (default value is false)
   1. renewTGT=[false]


### Keytab file

1. Kerberos server machines need a keytab file
1. Used to authenticate (host) to the KDC.
1. Keytab file should be readable only by root, and should exist only on the machine's local disk.
1. A user can type his/her password when authenticating.A service cannot . Hence the need to persist the password in a file.
1. Keytab can be considered as password for a service or host,used to authenticate the service itself to another service on the network.

```sample_jass.config
Sample {
  sample.module.SampleLoginModule required debug=true;
};
```
```sampleacn.policy
grant codebase "file:./SampleLM.jar" {
   permission javax.security.auth.AuthPermission "modifyPrincipals";
};
grant codebase "file:./SampleAcn.jar" {
   permission javax.security.auth.AuthPermission "createLoginContext.Sample";
};
```