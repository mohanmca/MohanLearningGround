*	Setting new project
	```bash 
	$ sbt new sbt/scala-seed.g8
	$ cd hello
	$ sbt
	> run
	> exit
	```
* Sample build.sbt
  *  ```build.sbt
	  lazy val root = (project in file("."))
	  .settings(
	    name         := "hello",
	    organization := "com.example",
	    scalaVersion := "2.12.2",
	    version      := "0.1.0-SNAPSHOT"
	  )  
    ```  
    	
* SBT Theory
  * build definition is defined in build.sbt,  it consists of a set of projects
  * subproject is the term used to define project in sbt documentation
  * subproject defined by sequence of key-value pairs called setting (task) expressions
  * built-in Keys can be found at (https://www.scala-sbt.org/0.13/sxr/sbt/Keys.scala.html)
  * SettingKey[T] -  computed once, 
  * TaskKey[T] - recomputed each time potentially with side effects 
    * Tasks are operations such as compile or package.
  * InputKey[T] - a key for a task that has command line arguments 
  * 
  

* projectDirectory\project :=> can contain .scala, .sbt file, They define helper objects and one-off plugins
* projectDirectory\target :=> compiled classes, packaged jars, managed files, caches, and documentation
* .gitignore should contain "target/" (as-is). So target directory from project and root-directory would be ignored

* Impact of proxy settting
  * Sometimes better to disable/set proxy using following
  * set HTTP_PROXY=;set HTTPS_PROXY=
  * set JAVA_OPTS=-Dhttp.proxySet=true -Dhttp.proxyHost=proxy.com -Dhttp.proxyPort=8080

* sbt commands
  * clean, compile, test, testQuick, console
  * run <args>*   
  * reload
  * libraryDependencies
  * [Some of the commands](https://www.scala-sbt.org/0.13.15/sxr/sbt/Main.scala.html#sbt.BuiltinCommands.ConsoleCommands)

* sbt history commands
  * !, !:, !:n  
  * !!, !n, !-n, !string, !?, !?, !string
  
* SBT launcher
  * java -jar build/sbt-launch.jar -Dsbt.override.build.repos=true -Dsbt.repository.config=project/sbt.repositories
  * IVY Home can be set using :=> -Dsbt.ivy.home=C:/Users/mohan/.ivy2
  * EclipseKeys.withSource := true
  * EclipseKeys.withJavadoc := true

* Sbt plugins
  * addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")
  * addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.1")        

* build.sbt settings
  * logLevel := Level.Debug [Level](https://www.scala-sbt.org/0.13.15/sxr/sbt/Level.scala.html)
  * scalaVersion := "2.11.8"

* project/build.properties - Some settings are better at properties file, Predominantly used only for sbt.version since 0.9 sbt.
  * sbt.version=0.13.17

* .sbt file
  * .sbt file can contain vals and defs in addition to settings, 
  * vals and defs are evaluated before settings regardless of where they are defined in the file
  * lazy vals are used instead of vals to avoid initialization order problems.
  * lazy val hello = taskKey[Unit]("An example task") //sample for custom taskKey
  * .settings(    hello := { println("Hello!") }  ) //invoke the task
  *  
  
* Sbt basic usages
  * sbt "~runMain ground.learning.App.FetchJavascriptStyleGuide"*
