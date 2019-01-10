* Jenkins - Build pipeline tool
* [Alternative to Jenkins](https://www.slant.co/options/2477/alternatives/~jenkins-alternatives)
* (https://jenkins.io/doc/book/pipeline/)
* Jenkins tasks
  * Cron on steroids
  * Automate Mundanity
    * Dev to Production
    * Continious Integration
    * Continious Delivery
  * Reliable, fast and feedback via mail
* Jenkins configuration and commands
  * /home/nikias/.jenkins/secrets/initialAdminPassword
  * D:\Apps\jenkins-2.157\secrets\initialAdminPassword
  * http://localhost:8080/
* Jenkins History
  * CruiseControl - 2001 - XML
  * Hudson - Kohsuke Kawaguchi @SUN - 2004  
  * Hudons first release @SUN - 2005
  * Oracle acquires Sun - 2009 -   
  * Oracle retains Hudson - 2011   
  * Jenkins - 2011
  * CloudBees - Jenkins SAAS - 2014
  * CloudBees - Jenkins2 - 2016
* Jenkins Security
  * Jenkins > Configure Global Security > Access Control > Security Realm
  * Jenkins > Configure Global Security > Access Control > Authorization
  * Jenkins > Configure Global Security > Authorization > Allow anonymous read access
  * Jenkins > Manage > Manager Users > Create User

* Jenkins using docker
```
https://hub.docker.com/_/jenkins
docker run -p 8090:8080 -p 50000:50000 -v /your/home:/var/jenkins_home jenkins
docker run --name myjenkins -p 8080:8080 -p 50000:50000 -v /your/home:/var/jenkins_home jenkins  
```
## Anatomy of the build
* Clone
* Compile
* Unit Test
* Package

* Jenkins workspace contains all the checked out project
* Job and build are different
* Jobs are template, and build are instances of the job
* DISABLE AUTO REFRESH/ENABLE AUTO REFRESH
* "General" > "Discard old builds" > "Max # of builds to keep"
* Features
  * Post-build actions
    * Archive artifacts
      * http://localhost:8080/job/Project/12/artifact/artifacts/spring-boot-sample-atmosphere-1.4.0.BUILD-SNAPSHOT.jar
      * http://localhost:8080/job/Project/13/artifact/artifacts/spring-boot-sample-atmosphere-1.4.0.BUILD-SNAPSHOT.jar
    * Send email notifications
    * Build trend
    * Build staibility: 1 out of last 5 builds failed. 80%
  * Reload configurations from the disk
  * Jenkins > System Log >  All Jenkins Log
    



# Docker would run on port 8080 (within its VM), It is acceisble to outside on port 8090.  
docker run -p 8090:8080 -p 50000:50000 -v /your/home:/var/jenkins_home jenkins

## References
* https://github.com/g0t4/jenkins2-course-spring-boot
* https://gist.github.com/g0t4/747cd20e8563aefc3eac444166983142
* D:\project\jenkins\jenkins2-course-spring-boot