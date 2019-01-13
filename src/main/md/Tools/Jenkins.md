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
  * Manage Jenkins > "Global Tool Configuration" > "Add JDK"
  * Manage Jenkins > "Global Tool Configuration" > "Add Maven"
  * Poll SCM interpretation and help syntax and english interpretation for cron
* Master node and Agent Model
* Running Junit test across multiple machines

## Jenkins Poll SCM
* "* * * * * *"
  * Do you really mean "every minute" when you say "* * * * *"? 
  * Would last have run at Saturday, 12 January, 2019 10:49:05 AM SGT; would next run at Saturday, 12 January, 2019 11:49:05 AM SGT.
* "* * * * * *"
  * Do you really mean "every minute" when you say "* * * * *"?
* H/15 * * * *
  * every fifteen minutes (perhaps at :07, :22, :37, :52)
    
# Jenkins pipeline job
* Jenkins > create_new_pipeline_job > somebuild_dummy_pipeline > Pipeline Syntax
* Jenkins > pipleline_job > Pipeline Syntax > Generate Pipeline Script
* Open search can be used to search directly from browswer intead visiting Jenkins
  * type "configure" in jenkins search
* Find list of template by following to steps
  * Navigate to http://localhost:8080/job/pipleline_job/pipeline-syntax/
  * Fire this JS from console 
    ```javascript
    Array.from(document.getElementsBySelector("#main-panel > form > table > tbody > tr:nth-child(4) > td.setting-main > select")[0].childElements()).map(_ => _.innerText).join("\n")
     ```
  * stage in pipeline is optional, but quite useful. pipeline configuration can work without stage defined in groovy script.
  * stage names could be anything, generally "checkout", "build", "package" and "archive"
  * When test-reports are not displayed ensure, archival happens after all the steps, archival error might stop reporting test-reports.
  



# Docker would run on port 8080 (within its VM), It is acceisble to outside on port 8090.  
```sh
docker run -p 8090:8080 -p 50000:50000 -v /your/home:/var/jenkins_home jenkins
# Docker SMTP Server
docker run --restart unless-stopped --name mailhog -p 1025:1025 -p 8025:8025 -d mailhog/mailhog
```

# Jenkins URLS
* [Configure](http://localhost:8080/configure)
* [Test History](http://localhost:8080/job/buildname/6/testReport/history/)
## List of templates
* archiveArtifacts: Archive the artifacts
* bat: Windows Batch Script
* build: Build a job
* checkout: Check out from version control
* cleanWs: Delete workspace when build is done
* deleteDir: Recursively delete the current directory from the workspace
* dir: Change current directory
* echo: Print Message
* emailext: Extended Email
* emailextrecipients: Extended Email Recipients
* error: Error signal
* fileExists: Verify if file exists in workspace
* fingerprint: Record fingerprints of files to track usage
* git: Git
* input: Wait for interactive input
* isUnix: Checks if running on a Unix-like node
* junit: Archive JUnit-formatted test results
* library: Load a shared library on the fly
* libraryResource: Load a resource file from a shared library
* load: Evaluate a Groovy source file into the Pipeline script
* lock: Lock shared resource
* mail: Mail
* milestone: The milestone step forces all builds to go through in order
* node: Allocate node
* parallel: Execute in parallel
* powershell: PowerShell Script
* properties: Set job properties
* pwd: Determine current directory
* readFile: Read file from workspace
* readTrusted: Read trusted file from SCM
* resolveScm: Resolves an SCM from an SCM Source and a list of candidate target branch * names
* retry: Retry the body up to N times
* script: Run arbitrary Pipeline script
* sh: Shell Script
* sleep: Sleep
* stage: Stage
* stash: Stash some files to be used later in the build
* step: General Build Step
* svn: Subversion
* timeout: Enforce time limit
* timestamps: Timestamps
* tm: Expand a string containing macros
* tool: Use a tool from a predefined Tool Installation
* unstash: Restore files previously stashed
* validateDeclarativePipeline: Validate a file containing a Declarative Pipeline
* waitUntil: Wait for condition
* withAnt: With Ant
* withCredentials: Bind credentials to variables
* withEnv: Set environment variables
* wrap: General Build Wrapper
* writeFile: Write file to workspace
* ws: Allocate workspace
* — Advanced/Deprecated —
  * archive: Archive artifacts
  * catchError: Catch error and set build result
  * dockerFingerprintFrom: Record trace of a Docker image used in FROM
  * dockerFingerprintRun: Record trace of a Docker image run in a container
  * envVarsForTool: Fetches the environment variables for a given tool in a list of *   'FOO=bar' strings suitable for the withEnv step.
  * getContext: Get contextual object from internal APIs
  * unarchive: Copy archived artifacts into the workspace
  * withContext: Use contextual object from internal APIs within a block
  * withDockerContainer: Run build steps inside a Docker container
  * withDockerRegistry: Sets up Docker registry endpoint
  * withDockerServer: Sets up Docker server endpoint"

## References
* [Getting started with Jenkins 2](https://www.pluralsight.com/courses/jenkins-2-getting-started)
* https://github.com/g0t4/jenkins2-course-spring-boot
* https://gist.github.com/g0t4/747cd20e8563aefc3eac444166983142
* https://github.com/g0t4/jenkins2-course-spring-petclinic
* D:\project\jenkins\jenkins2-course-spring-boot
* [Jenkins pipeline docs](https://jenkins.io/doc/book/pipeline/)