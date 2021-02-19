name := "MohanLearningGround"

version := "1.0"

scalaVersion := "2.12.13"
val akkaVersion = "2.5.18"
val akkaHttpVersion = "10.1.0"
val tomcatVersion = "9.0.14"

//enablePlugins(ScalaJSPlugin)
/*
    initialize := {
    val _ = initialize.value
    val specVersion = sys.props("java.specification.version")
    val required = "1.10"
    assert(required == specVersion, "Java 1.10.1 or above required")
    }
*/

transitiveClassifiers := Seq("sources")

sourceManaged in Compile := file("bin")


libraryDependencies ++= Seq(
    "org.apache.tomcat" % "tomcat-catalina" % "9.0.14",
    "org.apache.tomcat" % "tomcat" % tomcatVersion,
    "org.apache.tomcat" % "tomcat-coyote" % tomcatVersion,
    "org.apache.tomcat" % "tomcat-jasper" % tomcatVersion
)

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0" withSources(),
  "org.scalatra" %% "scalatra" % "2.5.+" withSources(),
  "org.scalatra" %% "scalatra-scalate" % "2.5.+" withSources(),
  "org.scalatra" %% "scalatra-specs2" % "2.5.+" % "test" withSources(),
  "ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime" withSources(),
  "javax.servlet" % "javax.servlet-api" % "4.0.0" % "provided" withSources(),
  "org.scalactic" % "scalactic_2.12" % "3.0.2" withSources(),
  "org.scalatest" % "scalatest_2.12" % "3.0.2" withSources(),
  "org.scalacheck" %% "scalacheck" % "1.13.5" withSources(),
  "org.scalaj" %% "scalaj-http" % "2.3.0"  withSources()  
)

//java library dependencies
libraryDependencies ++= Seq(
	"com.vladsch.flexmark" % "flexmark-all" % "0.32.18"  withSources(),
	"org.jsoup" % "jsoup" % "1.11.2"  withSources()
)

logLevel := Level.Warn

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2" withSources(),
  "org.typelevel" %% "cats-core" % "1.0.0-RC1" withSources(),
  "org.scalaz" %% "scalaz-core" % "7.2.17"  withSources()
)

libraryDependencies ++= Seq(
  "com.jcraft" % "jsch" % "0.1.55"
)

libraryDependencies ++= Seq(
  "javax.xml.bind" % "jaxb-api" % "2.3.0",
  "javax.annotation" % "javax.annotation-api" % "1.3.2",
  "javax.el" % "javax.el-api" % "3.0.0",
  "org.glassfish" % "javax.el" % "3.0.0"
)

libraryDependencies ++=  Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
	  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.scala-lang.modules" % "scala-parser-combinators_2.12" % "1.1.1"
  )

libraryDependencies ++=  Seq(
    "org.apache.commons" % "commons-text" % "1.3" withSources(),
    "org.apache.commons" % "commons-io" % "1.3.2" withSources(),
    "commons-lang" % "commons-lang" % "2.6" withSources(),
     "com.fasterxml.jackson.core" % "jackson-core" % "2.9.3" withSources(),
     "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.3",
    "junit" % "junit" % "4.12"
)

libraryDependencies ++= Seq(
    "io.reactivex" %% "rxscala" % "0.26.5" withSources(),
    "io.reactivex" % "rxswing" % "0.27.0" withSources(), // for Swing Scheduler in suggestions
    "org.json4s" %% "json4s-jackson" % "3.5.2" withSources(),
    "org.scala-lang.modules" %% "scala-swing" % "2.0.0" withSources(),
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.slf4j" % "slf4j-api" % "1.7.5" withSources(),
    "org.slf4j" % "slf4j-simple" % "1.7.5" withSources(),
    "com.squareup.retrofit" % "retrofit" % "1.0.0" withSources(),
    "org.scala-lang.modules" %% "scala-async" % "0.9.6"
)

val depsAkka = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion withSources(),
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion  withSources(),
    "com.typesafe.akka" %% "akka-persistence" % akkaVersion  withSources(),
    "com.typesafe.akka" %% "akka-stream" % akkaVersion  withSources(),
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion  withSources()
)

libraryDependencies ++= depsAkka

unmanagedSourceDirectories in Compile ++=   Seq(
    baseDirectory.value / "src/main/scala",
    baseDirectory.value / "src/main/java",
    baseDirectory.value / "src/main/resources",
    baseDirectory.value / "src/test/scala",
    baseDirectory.value / "src/test/java",
    baseDirectory.value / "src/main/js",
    baseDirectory.value / "src/main/webapp",
    baseDirectory.value / "src/main/python",
    baseDirectory.value / "src/main/md"
   )