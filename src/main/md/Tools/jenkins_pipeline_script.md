```groovy
node {
    notify('Started')
    try {
        stage 'checkout'
        git 'https://github.com/mohanmca/jenkins2-course-spring-boot.git'
        def project_path = "spring-boot-samples/spring-boot-sample-atmosphere"
        
        jdk = tool name: 'jdk8'
        env.JAVA_HOME = "${jdk}"
        
        dir(project_path) {
            stage 'build and package'
            withEnv(['MAVEN_HOME=D:\\Apps\\apache-maven-3.6.0']) {
                bat '%MAVEN_HOME%\\bin\\mvn clean package'
            }
            stage 'archive'
            archiveArtifacts "target/*.[jw]ar"
       }
       
        notify('Build Completed!')
    } catch(err) {
        notify("Build Failed! - ${err}")
        currentBuild.result = "Failure!"
    }
}
def notify(status){
      emailext (
         to: "you@gmail.com",
         subject: "${status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
         body: """<p>${status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
         <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
      )
}
```


```groovy
//without stage
node {
    git 'https://github.com/g0t4/jenkins2-course-spring-boot.git'
    def project_path = "spring-boot-samples/spring-boot-sample-atmosphere"
    
    jdk = tool name: 'jdk8'
    env.JAVA_HOME = "${jdk}"
    
    dir(project_path) {
        withEnv(['MAVEN_HOME=D:\\Apps\\apache-maven-3.6.0']) {
          bat '%MAVEN_HOME%\\bin\\mvn clean package'
        }
        archiveArtifacts "target/*.jar"
    }
}
```

```groovy
node {
    stage 'checkout'
    git 'https://github.com/g0t4/jenkins2-course-spring-boot.git'
    def project_path = "spring-boot-samples/spring-boot-sample-atmosphere"
    
    jdk = tool name: 'jdk8'
    env.JAVA_HOME = "${jdk}"
    
    dir(project_path) {
        stage 'build and package'
        withEnv(['MAVEN_HOME=D:\\Apps\\apache-maven-3.6.0']) {
            bat '%MAVEN_HOME%\\bin\\mvn clean package'
        }
        stage 'archive'
        archiveArtifacts "target/*.jar"
    }
}
def notify(status){
    emailext (
      to: "you@gmail.com",
      subject: "${status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
      body: """<p>${status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
        <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
    )
}
```


```groovy
#!groovy
@Library('github.com/walkmod/jenkins-pipeline-shared@maven') _
pipeline {
   agent any
   stages {
      stage ('Fixing Release'){
         steps {
            walkmodApply(
            validatePatch: false, 
            branch: env.BRANCH_NAME, 
            alwaysApply: true,
            alwaysFail: true)
         }
      }
      stage('Build') {
         steps {
            sh "mvn package"
         }
      }
      stage('Results') {
         steps {
             archive 'target/*.jar'
         }
      }
   }
}
```

```groovy
node {
   def mvnHome
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/jglick/simple-maven-project-with-tests.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'M3'
   }
   stage('Build') {
      // Run the maven build
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      } else {
         bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
      }
   }
   stage('Results') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.jar'
   }
}
```

# References
* [Pipline Gist Search](https://gist.github.com/search?q=filename:Jenkinsfile)
* [Pipeline email](https://gist.github.com/g0t4/747cd20e8563aefc3eac444166983142)
* [Pipeline globals](http://localhost:8080/job/pipleline_job/pipeline-syntax/globals)