*	Setting new project
	```bash 
	$ sbt new sbt/scala-seed.g8
	$ cd hello
	$ sbt
	> run
	> exit
	```
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
  * logLevel := Level.Debug
  * scalaVersion := "2.11.8"

* project/build.properties - Some settings are better at properties file, Predominantly used only for sbt.version since 0.9 sbt.
  * sbt.version=0.13.16