name := "MohanLearningGround"

version := "1.0"

scalaVersion := "2.12.2"

enablePlugins(ScalaJSPlugin)

initialize := {
   val _ = initialize.value
   val specVersion = sys.props("java.specification.version")
   val required = "1.8"
   assert(required == specVersion, "Java 1.8 or above required")
}

transitiveClassifiers := Seq("sources")

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.5.+" withSources(),
  "org.scalatra" %% "scalatra-scalate" % "2.5.+" withSources(),
  "org.scalatra" %% "scalatra-specs2" % "2.5.+" % "test" withSources(),
  "ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime" withSources(),
  "javax.servlet" % "servlet-api" % "2.5" % "provided" withSources(),
  "org.scalactic" % "scalactic_2.12" % "3.0.1" withSources(),
  "org.scalatest" % "scalatest_2.12" % "3.0.1" % "test" withSources(),
  "org.scalatest" % "scalatest_2.12" % "3.0.1" % "test"
)

logLevel := Level.Debug

libraryDependencies <++= (scalaVersion)(sv =>
  Seq(
    "org.scala-lang" % "scala-reflect" % "2.12.2",
    "org.scala-lang" % "scala-compiler" % "2.12.2",
	"org.scala-lang" % "scala-reflect" % "2.12.2",
    "org.scala-lang.modules" % "scala-parser-combinators_2.12" % "1.0.3"
  ))

libraryDependencies <++= (scalaVersion)(sv =>
  Seq(
        "org.apache.commons" % "commons-io" % "1.3.2" withSources(),
        "commons-lang" % "commons-lang" % "2.6" withSources(),
        "junit" % "junit" % "4.12"
  ))

resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
libraryDependencies += "org.scalaz" % "scalaz-core_2.12" % "7.2.12"  withSources()

libraryDependencies ++= Seq(
    "io.reactivex" %% "rxscala" % "0.26.5" withSources(),
    "io.reactivex" % "rxswing" % "0.27.0" withSources(), // for Swing Scheduler in suggestions
    "org.json4s" %% "json4s-native" % "3.5.2" withSources(),
    "org.scala-lang.modules" %% "scala-swing" % "2.0.0" withSources(),
//    "net.databinder.dispatch" % "dispatch-core_2.10" % "0.11.3",
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.slf4j" % "slf4j-api" % "1.7.5" withSources(),
    "org.slf4j" % "slf4j-simple" % "1.7.5" withSources(),
    "com.squareup.retrofit" % "retrofit" % "1.0.0" withSources(),
    "org.scala-lang.modules" %% "scala-async" % "0.9.6"
  )



val depsAkka = Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.1" withSources(),
    "com.typesafe.akka" %% "akka-testkit" % "2.5.1" withSources(),
    "com.typesafe.akka" %% "akka-persistence" % "2.5.1"  withSources()
)

libraryDependencies ++= depsAkka
