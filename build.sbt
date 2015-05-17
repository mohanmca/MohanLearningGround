name := "MohanLearningGround"

version := "1.0"

scalaVersion := "2.11.6"

transitiveClassifiers := Seq("sources")

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.3.1" withSources(),
  "org.scalatra" %% "scalatra-scalate" % "2.3.1" withSources(),
  "org.scalatra" %% "scalatra-specs2" % "2.3.1" % "test" withSources(),
  "ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime" withSources(),
  "javax.servlet" % "servlet-api" % "2.5" % "provided" withSources(),
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test" withSources(),
  "org.scalacheck" % "scalacheck_2.11" % "1.12.2"  withSources() 
)

libraryDependencies <++= (scalaVersion)(sv =>
  Seq(
    "org.scala-lang" % "scala-reflect" % "2.11.4",
    "org.scala-lang" % "scala-compiler" % "2.11.4",
	"org.scala-lang" % "scala-reflect" % "2.11.4",    
    "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.3"
  ))

libraryDependencies <++= (scalaVersion)(sv =>
  Seq(
    "org.apache.commons" % "commons-io" % "1.3.2" withSources(),
    "commons-lang" % "commons-lang" % "2.6" withSources(),
    "junit" % "junit" % "4.12"
      ))
 
resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
 
libraryDependencies += "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.1.0"
 
libraryDependencies ++= Seq(
    "io.reactivex" %% "rxscala" % "0.23.0" withSources(),
    "io.reactivex" % "rxswing" % "0.21.0" withSources(), // for Swing Scheduler in suggestions
    "org.json4s" %% "json4s-native" % "3.2.11" withSources(),
    "org.scala-lang.modules" %% "scala-swing" % "1.0.1" withSources(),
    "net.databinder.dispatch" %% "dispatch-core" % "0.11.0" withSources(),
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.slf4j" % "slf4j-api" % "1.7.5" withSources(),
    "org.slf4j" % "slf4j-simple" % "1.7.5" withSources(),
    "com.squareup.retrofit" % "retrofit" % "1.0.0" withSources(),
    "org.scala-lang.modules" %% "scala-async" % "0.9.2"
  )
  
val depsAkka = Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.3.9" withSources(),
    "com.typesafe.akka" %% "akka-testkit" % "2.3.9" withSources(),
    "com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.9"  withSources()
)
  
libraryDependencies ++= depsAkka
