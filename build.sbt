name := "MohanLearningGround"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies <++= (scalaVersion)(sv =>
  Seq(
    "org.scala-lang" % "scala-reflect" % "2.11.4",
    "org.scala-lang" % "scala-compiler" % "2.11.4",
	"org.scala-lang" % "scala-reflect" % "2.11.4",    
    "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.3"
  )
)