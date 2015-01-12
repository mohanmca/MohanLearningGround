sbt eclipse
sbt update-sbt-classifiers

//Decorate classifier in SBT
"com.typesafe.akka" %% "akka-actor" % "2.3.3" withSources()

transitiveClassifiers := Seq("sources")

http://www.scala-sbt.org/0.12.4/docs/Detailed-Topics/Library-Management.html
http://www.scala-sbt.org/0.12.1/docs/Detailed-Topics/Launcher.html

----
git log master

//Find all the branches
git branch -a

git fetch origin
git diff master remotes/origin/master..origin/master
