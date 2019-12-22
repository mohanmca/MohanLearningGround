## Basic commands
```bash
gcloud config list account
gcloud config list project
gcloud config set compute/zone asia-southeast1
gcloud config list
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
gcloud services enable pubsub.googleapis.com
gcloud services enable container.googleapis.com
gcloud services enable containerregistry.googleapis.com
gcloud pubsub topics create messages
```

## Twelth lab (deploying simple application in GCP without db)

```bash
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
gcloud services enable container.googleapis.com
gcloud services enable containerregistry.googleapis.com

## Create a Kubernetes Engine cluster that has Stackdriver Logging and Monitoring enabled.
gcloud container clusters create guestbook-cluster --zone=us-central1-a --num-nodes=2
    --machine-type=n1-standard-2 \
    --enable-autorepair \
    --enable-cloud-monitoring \
    --enable-cloud-logging
gcloud config list --format 'value(core.project)'
~/guestbook-frontend/pom.xml
remove this line - tomcat<provided>
  <plugin>
        <groupId>com.google.cloud.tools</groupId>
        <artifactId>jib-maven-plugin</artifactId>
        <version>0.9.6</version>
        <configuration>
          <to><image>gcr.io/[PROJECT_ID]/guestbook-frontend</image></to>
        </configuration>
  </plugin>
Replace the PROJECT_ID with actual project id
cd ~/guestbook-frontend
./mvnw clean compile jib:build
cd ~/guestbook-service
./mvnw clean compile jib:build

# you create a service account with permissions to access your GCP services. 
# You then store the service account that you generated earlier in Kubernetes as a secret so that it is accessible from the containers.
gcloud iam service-accounts create guestbook
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gcloud projects add-iam-policy-binding ${PROJECT_ID} --member serviceAccount:guestbook@${PROJECT_ID}.iam.gserviceaccount.com --role roles/editor
gcloud iam service-accounts keys create ~/service-account.json --iam-account guestbook@${PROJECT_ID}.iam.gserviceaccount.com
## Kubernetes server version 
kubectl version
kubectl create secret generic guestbook-service-account --from-file=$HOME/service-account.json
kubectl describe secret guestbook-service-account
~/kubernetes/guestbook-frontend-deployment.yaml
image: saturnism/spring-gcp-guestbook-frontend:latest replace with gcr.io/[PROJECT_ID]/guestbook-frontend
~/kubernetes/guestbook-service-deployment.yaml.
image: saturnism/spring-gcp-guestbook-frontend:latest replace with gcr.io/[PROJECT_ID]/guestbook-service
kubectl apply -f ~/kubernetes/
kubectl get svc guestbook-frontend
kubectl get svc
http://[EXTERNAL_IP]:8080
```

## Thirteen events, monitoring (promethus kubernetes monitoring)

```bash
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
gcloud services enable container.googleapis.com
gcloud services enable containerregistry.googleapis.com

gcloud container clusters get-credentials guestbook-cluster --zone=us-central1-a
kubectl apply -f https://storage.googleapis.com/stackdriver-prometheus-documentation/rbac-setup.yml --as=admin --as-group=system:masters
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
curl -s https://storage.googleapis.com/stackdriver-prometheus-documentation/prometheus-service.yml |   sed -e "s/\(\s*_kubernetes_cluster_name:*\).*/\1 'guestbook-cluster'/g" |   sed -e "s/\(\s*_kubernetes_location:*\).*/\1 'us-central1'/g" |   sed -e "s/\(\s*_stackdriver_project_id:*\).*/\1 '${PROJECT_ID}'/g" |   kubectl apply -f -
kubectl get pods -n stackdriver
~/guestbook-frontend/pom.xml
  <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
        <scope>runtime</scope>
  </dependency>
@~/guestbook-frontend/src/main/resources/application.properties
management.server.port=8081
management.endpoints.web.exposure.include=*
cd ~/guestbook-frontend
./mvnw clean compile jib:build
~/kubernetes/guestbook-frontend-deployment.yaml

```
