## ExternalSecrets

1. Syncs from external secret store such as AWS Secrets Manager
1. Uses a SecretStore to 
1. Stores secret value in K8S secret that is accessed by application
```yaml
 API Version:  external-secrets.io/v1beta1                                                                                                                                                                                                         │
 Kind:         ExternalSecret
```

## Why Do We Need SecretStore?
1. Separation of Concerns
1. SecretStore = Provider configuration (auth + region + driver).
1. ExternalSecret = What secret to fetch and how to map it.
2. Centralized Authentication & Provider Logic
   2.1 Instead of embedding AWS config in every ExternalSecret, you define it once in SecretStore.


## How does external-secret uses SecretStore
1. Secret-Store has which service-account (IRSA)
2. It is centralized, multiple external-secrets can use it
