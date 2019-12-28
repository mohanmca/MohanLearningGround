
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
gcloud pubsub subscriptions list
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


## Sixt lab - Spring Integration that abstracts from the underlying messaging system (rather integration with Cloud Pub/Sub)
```bash
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
gcloud pubsub topics list
dependencies@~/guestbook-frontend/pom.xml
<dependency>
   <groupId>org.springframework.integration</groupId>
   <artifactId>spring-integration-core</artifactId>
</dependency>
OutboundGateway.java@~/guestbook-frontend/src/main/java/com/example/frontend 
package com.example.frontend;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "messagesOutputChannel")
public interface OutboundGateway {
        void publishMessage(String message);
}
@~/guestbook-frontend/src/main/java/com/example/frontend/FrontendController.java
    @Autowired
    private PubSubTemplate pubSubTemplate; //remove this
    @Autowired
    private OutboundGateway outboundGateway; //add this
    outboundGateway.publishMessage(name + ": " + message); //instead of pubSubTemplate.publish("messages", name + ": " + message);

@~/guestbook-frontend/src/main/java/com/example/frontend/FrontendApplication.java

import org.springframework.context.annotation.*;
import org.springframework.cloud.gcp.pubsub.core.*;
import org.springframework.cloud.gcp.pubsub.integration.outbound.*;
import org.springframework.integration.annotation.*;
import org.springframework.messaging.*;

    @Bean
    @ServiceActivator(inputChannel = "messagesOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "messages");
    }

cd ~/guestbook-service
./mvnw -q spring-boot:run -Dserver.port=8081 -Dspring.profiles.active=cloud
cd ~/guestbook-frontend
./mvnw spring-boot:run -Dspring.profiles.active=cloud
gcloud pubsub subscriptions pull messages-subscription-1 --auto-ack
gcloud pubsub subscriptions list
```

* Spring Integration for Cloud Pub/Sub works for both inbound messages and outbound messages. 
* Cloud Pub/Sub also supports Spring Cloud Stream to create reactive microservices.

## Seventh lab - Add the ability to upload an image associated with a message. You store the image in Cloud Storage.
```bash
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
dependencies@~/guestbook-frontend/pom.xml
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-gcp-starter-storage</artifactId>
</dependency>
~/guestbook-frontend/src/main/resources/templates/index.html
<form action="/post" method="post"> (remove)
<!-- Set form encoding type to multipart form data -->
<form action="/post" method="post" enctype="multipart/form-data">

Insert the following tags before the <input type="submit" value="Post"/> tag:
            <!-- Add a file input -->
            <span>File:</span>
            <input type="file" name="file" accept=".jpg, image/jpeg"/>
@~/guestbook-frontend/src/main/java/com/example/frontend/FrontendController.java

import org.springframework.cloud.gcp.core.GcpProjectIdProvider;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import java.io.*;

//Insert the following code near the beginning of the FrontendController class definition, immediately before @GetMapping("/").
    // The ApplicationContext is needed to create a new Resource.
    @Autowired
    private ApplicationContext context;
    // Get the Project ID, as its Cloud Storage bucket name here
    @Autowired
    private GcpProjectIdProvider projectIdProvider;

Change the definition for the post method
You modify the post method to save uploaded images to Cloud Storage.
1.	Near the end of the file, change this line: public String post(@RequestParam String name, @RequestParam String message, Model model) {
To these lines:
    public String post(
       @RequestParam(name="file", required=false) MultipartFile file,
         @RequestParam String name,
         @RequestParam String message, Model model)
       throws IOException {

2.	Insert the following code immediately after the line model.addAttribute("name", name);:
        String filename = null;
        if (file != null && !file.isEmpty()
            && file.getContentType().equals("image/jpeg")) {
                // Bucket ID is our Project ID
                String bucket = "gs://" +
                      projectIdProvider.getProjectId();
                // Generate a random file name
                filename = UUID.randomUUID().toString() + ".jpg";
                WritableResource resource = (WritableResource)
                      context.getResource(bucket + "/" + filename);
                // Write the file to Cloud Storage
                try (OutputStream os = resource.getOutputStream()) {
                     os.write(file.getBytes());
                 }
        }
3.	Add the following code to insert the location of the uploaded file immediately before the client.add(payload); line:
            // Store the generated file name in the database
            payload.put("imageUri", filename);
The complete post method definition should look like the screenshot:

cd ~/guestbook-service
./mvnw -q spring-boot:run -Dserver.port=8081 -Dspring.profiles.active=cloud
cd ~/guestbook-frontend
./mvnw spring-boot:run -Dspring.profiles.active=cloud

@~/guestbook-frontend/src/main/java/com/example/frontend/FrontendController.java.
import org.springframework.http.*;
// ".+" is necessary to capture URI with filename extension
  @GetMapping("/image/{filename:.+}")
  public ResponseEntity<Resource> file(@PathVariable String filename) {
          String bucket = "gs://" + projectIdProvider.getProjectId();
          // Use "gs://" URI to construct
          // a Spring Resource object
          Resource image = context.getResource(bucket + "/" + filename);
          // Send it back to the client
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.IMAGE_JPEG);
          return new ResponseEntity<>(image, headers, HttpStatus.OK);
  }

main.container@~/guestbook-frontend/src/main/resources/templates/index.html   
<img th:src="'/image/' + ${message.imageUri}" alt="image" height="40px" th:unless="${#strings.isEmpty(message.imageUri)}"/>
cd ~/guestbook-frontend
./mvnw spring-boot:run -Dspring.profiles.active=cloud

```

## Eigth lab - Vision cloud API 
```bash
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
gcloud services enable vision.googleapis.com

dependencies@~/guestbook-frontend/pom.xml
    <dependency>
        <groupId>com.google.cloud</groupId>
        <artifactId>google-cloud-vision</artifactId>
    </dependency>

@~/guestbook-frontend/src/main/resources/application.properties
spring.cloud.gcp.credentials.scopes=https://www.googleapis.com/auth/cloud-platform

@~/guestbook-frontend/src/main/java/com/example/frontend/FrontendApplication.java
import java.io.IOException;
import com.google.cloud.vision.v1.*;
import com.google.api.gax.core.CredentialsProvider;


    // This configures the Vision API settings with a
    // credential using the the scope we specified in
    // the application.properties.
    @Bean
    public ImageAnnotatorSettings imageAnnotatorSettings(CredentialsProvider credentialsProvider) throws IOException {
                return ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(credentialsProvider).build();
    }

    @Bean
    public ImageAnnotatorClient imageAnnotatorClient(
                ImageAnnotatorSettings settings)
                throws IOException {
           return ImageAnnotatorClient.create(settings);
    }

@~/guestbook-frontend/src/main/java/com/example/frontend/FrontendController.java
import com.google.cloud.vision.v1.*;

 @Autowired
private ImageAnnotatorClient annotatorClient;

private void analyzeImage(String uri) {
    // After the image was written to GCS
    // analyze it with the GCS URI.It,s also 
    // possible to analyze an image embedded in 
    // the request as a Base64 encoded payload. //
    List<AnnotateImageRequest> requests = new ArrayList<>();
    ImageSource imgSrc = ImageSource.newBuilder()
            .setGcsImageUri(uri).build();
    Image img = Image.newBuilder().setSource(imgSrc).build();
    Feature feature = Feature.newBuilder()
            .setType(Feature.Type.LABEL_DETECTION).build();
    AnnotateImageRequest request = AnnotateImageRequest
            .newBuilder()
            .addFeatures(feature)
            .setImage(img)
            .build();
    requests.add(request);
    BatchAnnotateImagesResponse responses =
            annotatorClient.batchAnnotateImages(requests);
        // We send in one image, expecting just
        // one response in batch
    AnnotateImageResponse response =responses.getResponses(0);
    System.out.println(response);
}

@~/guestbook-frontend/src/main/java/com/example/frontend/FrontendController.java
            // After written to GCS, analyze the image.
            analyzeImage(bucket + "/" + filename);

export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gcloud iam service-accounts create guestbook
gcloud projects add-iam-policy-binding ${PROJECT_ID}   --member serviceAccount:guestbook@${PROJECT_ID}.iam.gserviceaccount.com   --role roles/editor
gcloud iam service-accounts keys create ~/service-account.json --iam-account guestbook@${PROJECT_ID}.iam.gserviceaccount.com

cd ~/guestbook-service
./mvnw -q spring-boot:run -Dserver.port=8081 -Dspring.profiles.active=cloud
cd ~/guestbook-frontend
./mvnw spring-boot:run -Dspring.profiles.active=cloud -Dspring.cloud.gcp.credentials.location=file:///$HOME/service-account.json

```

## Ninth lab - Deploying to APP Engine
```bash
gcloud config set compute/zone asia-southeast1
gcloud app create --region=asia-southeast1
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
#gcloud app create --region=asia-southeast1
gcloud app create --region=us-central

dependencies@~/guestbook-frontend/pom.xml
    <plugin>
            <groupId>com.google.cloud.tools</groupId>
            <artifactId>appengine-maven-plugin</artifactId>
            <version>1.3.1</version>
            <configuration>
                <version>1</version>
            </configuration>
    </plugin>

mkdir -p ~/guestbook-frontend/src/main/webapp/WEB-INF/
appengine-web.xml@~/guestbook-frontend/src/main/webapp/WEB-INF/
<appengine-web-app xmlns="http://appengine.google.com/ns/1.0">
  <service>default</service>
  <version>1</version>
  <threadsafe>true</threadsafe>
  <runtime>java8</runtime>
  <instance-class>B4_1G</instance-class>
  <sessions-enabled>true</sessions-enabled>
  <manual-scaling>
    <instances>2</instances>
  </manual-scaling>
  <system-properties>
    <property name="spring.profiles.active" value="cloud" />
  </system-properties>
</appengine-web-app>

gcloud beta runtime-config configs variables set messages.endpoint "https://guestbook-service-dot-${PROJECT_ID}.appspot.com/guestbookMessages" --config-name frontend_cloud
cd ~/guestbook-frontend
./mvnw appengine:deploy -DskipTests
gcloud app browse

~/guestbook-service/pom.xml
    <plugin>
            <groupId>com.google.cloud.tools</groupId>
            <artifactId>appengine-maven-plugin</artifactId>
            <version>1.3.1</version>
            <configuration>
                <version>1</version>
            </configuration>
    </plugin>
mkdir -p ~/guestbook-service/src/main/webapp/WEB-INF/

appengine-web.xml@~/guestbook-service/src/main/webapp/WEB-INF/
<appengine-web-app xmlns="http://appengine.google.com/ns/1.0">
  <service>guestbook-service</service>
  <version>1</version>
  <threadsafe>true</threadsafe>
  <runtime>java8</runtime>
  <instance-class>B4_1G</instance-class>
  <manual-scaling>
    <instances>2</instances>
  </manual-scaling>
  <system-properties>
    <property name="spring.profiles.active" value="cloud" />
  </system-properties>
</appengine-web-app>

cd ~/guestbook-service
./mvnw appengine:deploy -DskipTests
gcloud app browse -s guestbook-service
```


## Tenth lab - Stackdriver debugger appengine
```bash
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
gcloud app browse

Stackdriver > Logging > Logs
GAE Application > Default Service > 1
Stackdriver > Debug

gcloud beta debug source upload --project=qwiklabs-gcp-00-f82b10a6fe39 --branch=64CC16049CF564197B0F LOCAL_PATH

gcloud services enable sourcerepo.googleapis.com
gcloud source repos create google-source-captures
cd ~/guestbook-frontend
git config --global user.email $(gcloud config get-value core/account)
git config --global user.name "devstar"
gcloud beta debug source upload --project=$PROJECT_ID  --branch=64CC16049CF564197B0F ./src/

Stackdriver > Monitoring
Create Workspace
Skip AWS Setup
Resources > GCP > App Engine
```

## Elevent lab - Cloud Spanner database
```bash
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gsutil ls gs://$PROJECT_ID
gsutil -m cp -r gs://$PROJECT_ID/* ~/
chmod +x ~/guestbook-frontend/mvnw
chmod +x ~/guestbook-service/mvnw
gcloud app browse
gcloud services enable spanner.googleapis.com
gcloud spanner instances create guestbook --config=regional-us-central1 --nodes=1 --description="Guestbook messages"
gcloud spanner databases create messages --instance=guestbook

cd ~/guestbook-service
mkdir db
spanner.ddl@~/guestbook-service/db/
CREATE TABLE guestbook_message (
    id STRING(36) NOT NULL,
    name STRING(255) NOT NULL,
    image_uri STRING(255),
    message STRING(255)
) PRIMARY KEY (id);
gcloud spanner databases ddl update messages --instance=guestbook --ddl="$(<db/spanner.ddl)"

GCP Console > Storage >__ Spanner._ > Guestbook messages > guestbook_message  > Data 

pom.xml@~/guestbook-service/pom.xml
<dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-gcp-starter-data-spanner</artifactId>
</dependency>

~/guestbook-service/src/main/resources/application-cloud.properties
spring.cloud.gcp.sql.enabled=true
spring.cloud.gcp.sql.database-name=messages
spring.cloud.gcp.sql.instance-connection-name=...
spring.cloud.gcp.spanner.instance-id=guestbook
spring.cloud.gcp.spanner.database=messages


~/guestbook-service/src/main/java/com/example/guestbook/GuestbookMessage.java

package com.example.guestbook;

import lombok.*;
import org.springframework.cloud.gcp.data.spanner.core.mapping.*;
import org.springframework.data.annotation.Id;

@Data
@Table(name = "guestbook_message")
public class GuestbookMessage {
        @PrimaryKey
        @Id
        private String id;

        private String name;

        private String message;

        @Column(name = "image_uri")
        private String imageUri;

        public GuestbookMessage() {
                this.id = java.util.UUID.randomUUID().toString();
        }
}

~/guestbook-service/src/main/java/com/example/guestbook/GuestbookMessageRepository.java

public interface GuestbookMessageRepository extends  PagingAndSortingRepository<GuestbookMessage, String> {
            java.util.List<GuestbookMessage> findByName(String name);
}
cd ~/guestbook-service
./mvnw spring-boot:run -Dserver.port=8081 -Dspring.profiles.active=cloud
curl -XPOST -H "content-type: application/json"   -d '{"name": "Ray", "message": "Hello Cloud Spanner"}'  http://localhost:8081/guestbookMessages
curl http://localhost:8081/guestbookMessages
curl http://localhost:8081/guestbookMessages/search/findByName?name=Ray
gcloud spanner databases execute-sql messages --instance=guestbook --sql="SELECT * FROM guestbook_message WHERE name = 'Ray'"
Spanner > Guestbook messages > messages > guestbook_message > data 
cd ~/guestbook-service
./mvnw clean appengine:deploy -DskipTests
gcloud app browse -s guestbook-service
gcloud app browse -s default
```

## Google app-engine commands
```bash
gcloud app logs tail -s default
gcloud app browse
```

## Reference
* [Cloud shell](https://cloud.google.com/shell/docs/)
* [Spring cloud GCP samples](https://github.com/spring-cloud/spring-cloud-gcp/tree/master/spring-cloud-gcp-samples/spring-cloud-gcp-config-sample)
* [Regions and Zones](https://cloud.google.com/compute/docs/regions-zones/)
* [Spring guestbook app](https://github.com/saturnism/spring-cloud-gcp-guestbook)


## Follow-up?

* How to  import structured data stored on Cloud Storage directly into BigQuery for ad hoc data analytics using standard SQL?