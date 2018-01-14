set JAVA_OPTS=-Dhttp.proxySet=true -Dhttp.proxyHost=proxy.server.com -Dhttp.proxyPort=8080
java -Xmx1G -Dsbt.log.noformat=true -Dsbt.repository.config=project/sbt.repositories -Dsbt.override.build.repos=true -jar build/sbt-launch.jar compile package publish

----

sbt eclipse update-sbt-classifiers
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

--find untracked files
git ls-files --others --exclude-standard

git remote add origin https://github.com/mohanmca/PrologLearningGround
git checkout readme.txt

-----

Reader Monad

1) http://blog.originate.com/blog/2013/10/21/reader-monad-for-dependency-injection/
2) Jason Arhart
3) https://cdn.parleys.com/p/53a7d2d1e4b0543940d9e56f/ADm552EgQj9-ReaderMonadforDI.pdf
4) https://parleys.com/play/53a7d2d1e4b0543940d9e56f/chapter0/downloads


object creation impossible, since method foldr in trait List of type [B](z: B)(op: (Nothing, B) => B)B is not defined	
----

Todo:

Find side-effect-free short-cut for debugging.. on the lines..
val output = expression ~~ println

----

I write all the personal notes to LectureNotes directory with Tonnie_***



[warn]  :: com.typesafe.scala-logging#scala-logging_2.12;3.1.0: not found
[warn]  :: org.scala-lang.modules#scala-async_2.12;0.9.2: not found
[warn]  :: com.typesafe.akka#akka-persistence-experimental_2.12;2.5.1: not found