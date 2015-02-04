name := "MohanLearningGround"

version := "1.0"

scalaVersion := "2.11.4"

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
  
/*  
libraryDependencies  ++= (
	Seq(
		"com.propensive" %% "rapture-core" % "1.1.0",
		"com.propensive" %% "rapture-json" % "1.1.0",
		"com.propensive" %% "rapture-io" % "0.9.1",
		"com.propensive" %% "rapture-net" % "0.10.0",
		"com.propensive" %% "rapture-uri" % "1.0.0",
		"com.propensive" %% "rapture-fs" % "0.10.0",
		"com.propensive" %% "rapture-mime" % "0.9.0"
	))
*/
