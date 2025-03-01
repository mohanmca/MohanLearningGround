## What is the prompt to quickly validate about Secrets in Kubernetes?
```bash
Can you ask quiz about secret-store, secret, external-operator, clusterSecretStore and let me choose answer to validate?
````

## What is helm chart?
1. Helm is a package manager for Kubernetes that allows developers and operators to more easily package, configure, and deploy applications.
2. Helm charts are packages of pre-configured Kubernetes resources.

## What does A Helm chart typically includes?
1. A Helm chart typically includes a set of Kubernetes resource definitions, templates, and default values for those resources.
2. Templates - Kubernetes manifest files with placeholders (like variables) that get dynamically filled.
3. Values.yaml - A configuration file where you define those variables.
4. Charts.yaml -  Metadata about the chart (name, version, description, etc.).
5. Templates/_helpers.tpl - A file with helper functions that can be used in the templates.

## What are Helm commands?
1. helm create my-chart - Create a new chart with the name "my-chart".
2. helm install my-release ./my-chart - Install a chart.
2. helm upgrade - Upgrade a release to a new version of a chart.
3. helm rollback - Rollback to a previous version of a release.
4. helm uninstall - Uninstall a release.

## Sample helm chart folder
```pre
my-chart/
├── charts/               # (Optional) Subcharts go here
├── templates/            # Template files (Kubernetes manifests)
│   ├── deployment.yaml   # A sample Deployment manifest
│   └── _helpers.tpl      # (Optional) Helper functions
├── Chart.yaml            # Chart metadata
└── values.yaml           # Default values for templates
```


## Chart.yaml

```roomsql
apiVersion: v2
name: my-chart
description: A simple Helm chart for Nginx
version: 0.1.0
appVersion: 1.25.0
```

## values.yaml

```pre
replicaCount: 1

image:
  repository: nginx
  tag: "1.25.0"
  pullPolicy: IfNotPresent

service:
  type: ClusterIP
  port: 80
```

## templates/deployment.yaml
```pre
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .Chart.Name }}
spec:
  replicas: {{ .Values.replicaCount }}
  selector:
    matchLabels:
      app: {{ .Chart.Name }}
  template:
    metadata:
      labels:
        app: {{ .Chart.Name }}
    spec:
      containers:
        - name: nginx
          image: "{{ .Values.image.repository }}:{{ .Values.image.tag }}"
          imagePullPolicy: {{ .Values.image.pullPolicy }}
          ports:
            - containerPort: 80
```

## What is SecretStore in AWS EKS?
1. In AWS EKS (Elastic Kubernetes Service), a SecretStore is part of the External Secrets Operator.
2. It’s a resource that tells Kubernetes where and how to fetch secrets from an external secrets management system
3. The External Secrets Operator is a Kubernetes operator that manages the lifecycle of external secrets.
4. External store in AWS EKS 
   5. AWS Secrets Manager
   6. AWS Systems Manager Parameter Store.

## What is the External Secrets Operator?
1. The External Secrets Operator (ESO) automates the synchronization of secrets from external services into Kubernetes Secrets.

## Create secret in AWS Secrets Manager
```bash 
aws secretsmanager create-secret \
--name my-aws-rds-db-password \
--secret-string "super-secure-password"
aws secretsmanager get-secret-value --secret-id my-aws-rds-db-password
```

## To allow the External Secrets Operator (ESO) to access AWS Secrets Manager, create an IAM role with the right policy.
```bash
aws iam create-policy \
    --policy-name ExternalSecretsPolicy \
    --policy-document '{
      "Version": "2012-10-17",
      "Statement": [
        {
          "Effect": "Allow",
          "Action": "secretsmanager:GetSecretValue",
          "Resource": "*"
        }
      ]
    }'
aws iam create-role --role-name eks-external-secrets-role --assume-role-policy-document file://trust-policy.json
aws iam attach-role-policy \
    --role-name eks-external-secrets-role \
    --policy-arn arn:aws:iam::YOUR_POLICY_ARN:policy/ExternalSecretsPolicy
```

## Fetch the value of aws-rds-db-external-secret from AWS Secrets Manager - aws-org-secret-store. Store it as a key called AWS_DB_PASSWORD in the Kubernetes Secret aws-rds-k8s-secret.

```yaml
apiVersion: external-secrets.io/v1beta1
kind: SecretStore
metadata:
  name: aws-org-secret-store
  namespace: default
spec:
  provider:
    aws:
      service: SecretsManager
      region: us-west-2
      auth:
        jwt:
          serviceAccountRef:
            name: external-secrets-service-account


apiVersion: external-secrets.io/v1beta1
kind: ExternalSecret
metadata:
  name: aws-rds-db-external-secret
  namespace: default
spec:
  refreshInterval: 1h
  secretStoreRef:
    name: aws-org-secret-store
    kind: SecretStore
  target:
    name: aws-rds-k8s-secret
    creationPolicy: Owner
  data:
    - secretKey: AWS_DB_PASSWORD
      remoteRef:
        key: my-aws-rds-db-password
```

## Above result is

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: aws-rds-k8s-secret
type: Opaque
data:
  AWS_DB_PASSWORD: bXktc2VjdXJlLXZhbHVl   # (Base64-encoded value)
```

## Validate them
```bash
kubectl get secret aws-rds-k8s-secret -o yaml
kubectl get secret aws-rds-k8s-secret -o jsonpath='{.data.AWS_DB_PASSWORD}' | base64 --decode
```

## In Prod
```roomsql
env:
  - name: DB_PASSWORD
    valueFrom:
      secretKeyRef:
        name: aws-rds-k8s-secret
        key: AWS_DB_PASSWORD
```