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


-----

git remote add origin https://github.com/mohanmca/PrologLearningGround

git checkout readme.txt

-----

Reader Monad

1) http://blog.originate.com/blog/2013/10/21/reader-monad-for-dependency-injection/
2) Jason Arhart
3) https://cdn.parleys.com/p/53a7d2d1e4b0543940d9e56f/ADm552EgQj9-ReaderMonadforDI.pdf
4) https://parleys.com/play/53a7d2d1e4b0543940d9e56f/chapter0/downloads


object creation impossible, since method foldr in trait List of type [B](z: B)(op: (Nothing, B) => B)B is not defined	
