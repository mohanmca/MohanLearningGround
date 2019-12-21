
## Basic commands
```bash
gcloud config list account
gcloud config list project
gcloud config set compute/zone asia-southeast1
gcloud config list
```

## First lab (deploying simple application in GCP without db)
```bash
cd ~/
git clone https://github.com/saturnism/spring-cloud-gcp-guestbook.git
cp -a ~/spring-cloud-gcp-guestbook/1-bootstrap/guestbook-service  ~/guestbook-service
cd ~/guestbook-service
./mvnw -q spring-boot:run -Dserver.port=8081
curl http://localhost:8081/guestbookMessages
curl -XPOST -H "content-type: application/json"   -d '{"name": "Ray", "message": "Hello"}'  http://localhost:8081/guestbookMessages
curl http://localhost:8081/guestbookMessages

cp -a ~/spring-cloud-gcp-guestbook/1-bootstrap/guestbook-frontend  ~/guestbook-frontend
curl -s http://localhost:8081/guestbookMessages
curl -s http://localhost:8081/guestbookMessages  | jq -r '._embedded.guestbookMessages[] | {name: .name, message: .message}'
```

## second lab (deploying spring database application in GCP with Cloud SQL)
```bash
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
# Edit guestbook-service/pom.xml
    <dependencyManagement>
        <dependencies>
          <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-gcp-dependencies</artifactId>
             <version>1.2.0.RELEASE</version>
             <type>pom</type>
             <scope>import</scope>
          </dependency>
        </dependencies>
    </dependencyManagement>

      <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-gcp-starter-sql-mysql</artifactId>
      </dependency>
Update guestbook-service/src/main/resources/application.properties
spring.cloud.gcp.sql.enabled=false
gcloud sql instances describe guestbook --format='value(connectionName)'
qwiklabs-gcp-4d0ab38f9ff2cc4c:us-central1:guestbook
application-cloud.properties
spring.cloud.gcp.sql.enabled=true
spring.cloud.gcp.sql.database-name=messages
spring.cloud.gcp.sql.instance-connection-name=YOUR_INSTANCE_CONNECTION_NAME
spring.datasource.hikari.maximum-pool-size=5

cd ~/guestbook-service
./mvnw spring-boot:run -Dserver.port=8081 -Dspring.profiles.active=cloud
curl -XPOST -H "content-type: application/json"   -d '{"name": "Ray", "message": "Hello Cloud SQL"}'   http://localhost:8081/guestbookMessages
curl http://localhost:8081/guestbookMessages
gcloud sql connect guestbook;
use messages;
select * from guestbook_message;


```
## Enable cloud sql
```bash
gcloud services enable sqladmin.googleapis.com
gcloud services list | grep sqladmin
gcloud sql instances list
gcloud sql instances create guestbook --region=asia-southeast1
gcloud sql databases create messages --instance guestbook
gcloud sql connect guestbook
show databases;
use messages;
CREATE TABLE guestbook_message (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name CHAR(128) NOT NULL,
  message CHAR(255),
  image_uri CHAR(255),
  PRIMARY KEY (id)
);
exit
```

## Thrid lab (Cloud configuration)
```bash
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
@guestbook-frontend/pom.xml
        <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-gcp-starter-config</artifactId>
        </dependency>
        <dependency>
           <groupId>com.google.guava</groupId>
           <artifactId>guava</artifactId>
           <version>20.0</version>
        </dependency>
        <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

<dependencyManagement>@guestbook-frontend/pom.xml
<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-gcp-dependencies -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-gcp-dependencies</artifactId>
    <version>1.2.0.RELEASE</version>
    <type>pom</type>
</dependency>
<repositories>@guestbook-frontend/pom.xml
    <repositories>
         <repository>
              <id>spring-milestones</id>
              <name>Spring Milestones</name>
              <url>https://repo.spring.io/milestone</url>
              <snapshots>
                  <enabled>false</enabled>
              </snapshots>
         </repository>
    </repositories>

guestbook-frontend/src/main/resources/bootstrap.properties
spring.cloud.gcp.config.enabled=false

guestbook-frontend/src/main/resources/bootstrap-cloud.properties
spring.cloud.gcp.config.enabled=true
spring.cloud.gcp.config.name=frontend
spring.cloud.gcp.config.profile=cloud

guestbook-frontend/src/main/resources/application.properties
management.endpoints.web.exposure.include=*

@guestbook-frontend/src/main/java/com/example/frontend/FrontendController.java
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope

gcloud services enable runtimeconfig.googleapis.com
gcloud beta runtime-config configs create frontend_cloud
gcloud beta runtime-config configs variables set greeting   "Hi from Runtime Config"   --config-name frontend_cloud
gcloud beta runtime-config configs variables list --config-name=frontend_cloud
gcloud beta runtime-config configs variables get-value greeting --config-name=frontend_cloud

cd ~/guestbook-service
./mvnw -q spring-boot:run -Dserver.port=8081 -Dspring.profiles.active=cloud
cd ~/guestbook-frontend
./mvnw spring-boot:run -Dspring.profiles.active=cloud

gcloud beta runtime-config configs variables set greeting   "Hi from Updated Config"   --config-name frontend_cloud
curl -XPOST http://localhost:8080/actuator/refresh
curl http://localhost:8080/actuator/configprops | jq
```

## Fourth lab  - Tracing, configuration management, and integration
```bash
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
gcloud services enable cloudtrace.googleapis.com
dependencies@~/guestbook-service/pom.xml
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-gcp-starter-trace</artifactId>
</dependency>
dependencies@~/guestbook-frontend/pom.xml
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-gcp-starter-trace</artifactId>
</dependency>
@guestbook-frontend/src/main/resources/application.properties
spring.cloud.gcp.trace.enabled=false
@guestbook-service/src/main/resources/application.properties
spring.cloud.gcp.trace.enabled=false
@guestbook-service/src/main/resources/application-cloud.properties
spring.cloud.gcp.trace.enabled=true
spring.sleuth.sampler.probability=1
spring.sleuth.web.skipPattern=(^cleanup.*|.+favicon.*)
guestbook-frontend/src/main/resources/application-cloud.properties
spring.cloud.gcp.trace.enabled=true
spring.sleuth.sampler.probability=1
spring.sleuth.web.skipPattern=(^cleanup.*|.+favicon.*)

#Create a service account with permissions to propagate trace data to Stackdriver Trace
gcloud iam service-accounts create guestbook
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gcloud projects add-iam-policy-binding ${PROJECT_ID}   --member serviceAccount:guestbook@${PROJECT_ID}.iam.gserviceaccount.com   --role roles/editor

#Generate the JSON key file to be used by the application to identify itself using the service account
gcloud iam service-accounts keys create ~/service-account.json --iam-account guestbook@${PROJECT_ID}.iam.gserviceaccount.com
# Above command creates service account credentials that are stored in the $HOME/service-account.json file. Treat the service-account.json file as your own username/password

cd ~/guestbook-service
./mvnw spring-boot:run -Dserver.port=8081 -Dspring.profiles.active=cloud   -Dspring.cloud.gcp.credentials.location=file:///$HOME/service-account.json
cd ~/guestbook-frontend
./mvnw spring-boot:run -Dspring.profiles.active=cloud -Dspring.cloud.gcp.credentials.location=file:///$HOME/service-account.json
curl http://localhost:8080
Gconsole > Stackdriver > Trace > Trace List.
```

## Fifth lab  -message handling service with Cloud Pub/Sub
```bash
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
gcloud services enable pubsub.googleapis.com
gcloud pubsub topics create messages

<dependencies>@~/guestbook-frontend/pom.xml
     <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-gcp-starter-pubsub</artifactId>
     </dependency>
@guestbook-frontend/src/main/java/com/example/frontend/FrontendController.java
import org.springframework.cloud.gcp.pubsub.core.*;
@Autowired
private PubSubTemplate pubSubTemplate;
# /** Add inside the if statement to process messages that aren't null or empty, just above the comment Post  **/

pubSubTemplate.publish("messages", name + ": " + message);

cd ~/guestbook-service
./mvnw -q spring-boot:run -Dserver.port=8081 -Dspring.profiles.active=cloud

cd ~/guestbook-frontend
./mvnw spring-boot:run -Dspring.profiles.active=cloud

gcloud pubsub subscriptions create messages-subscription-1  --topic=messages
//Post message using UI and run below command
gcloud pubsub subscriptions pull messages-subscription-1
//Post message using UI and run below command, but below one would remove
gcloud pubsub subscriptions pull messages-subscription-1 --auto-ack
```

## Fifth lab (part-2) Process messages in subscription
```bash
cd ~
curl https://start.spring.io/starter.tgz  -d dependencies=cloud-gcp-pubsub -d baseDir=message-processor | tar -xzvf -
@~/message-processor/pom.xml 
<dependencies>
      <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-gcp-starter-pubsub</artifactId>
      </dependency>
      <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-test</artifactId>
         <scope>test</scope>
      </dependency>
</dependencies>
@~/message-processor/src/main/java/com/example/demo/DemoApplication.java
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gcp.pubsub.core.*;


    @Bean
    public CommandLineRunner cli(PubSubTemplate pubSubTemplate) {
        return (args) -> {
            pubSubTemplate.subscribe("messages-subscription-1",
               (msg, ackConsumer) -> {
                    System.out.println(msg.getData().toStringUtf8());
                    ackConsumer.ack();
               });
        };
    }
cd ~/message-processor
./mvnw -q spring-boot:run
//Open the browser with the frontend application, and post a few messages.
//Verify that the Cloud Pub/Sub messages are received in the message processor
```






## Reference
* [Cloud shell](https://cloud.google.com/shell/docs/)
* [Spring cloud GCP samples](https://github.com/spring-cloud/spring-cloud-gcp/tree/master/spring-cloud-gcp-samples/spring-cloud-gcp-config-sample)
* [Regions and Zones](https://cloud.google.com/compute/docs/regions-zones/)
* [Spring guestbook app](https://github.com/saturnism/spring-cloud-gcp-guestbook)
