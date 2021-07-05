## Around 2018, I most often use following settings while running scala program for some reason

1. set JAVA_OPTS=-Dhttp.proxySet=true -Dhttp.proxyHost=proxy.server.com -Dhttp.proxyPort=8080
1. java -Xmx1G -Dsbt.log.noformat=true -Dsbt.repository.config=project/sbt.repositories -Dsbt.override.build.repos=true -jar build/sbt-launch.jar compile package publish

## SBT Settings

1. sbt eclipse update-sbt-classifiers
1. sbt update-sbt-classifiers
1. Decorate classifier in SBT (to attach source code)
    1. "com.typesafe.akka" %% "akka-actor" % "2.3.3" withSources()
    1. transitiveClassifiers := Seq("sources")

## SBT Launcher details

1. http://www.scala-sbt.org/0.12.4/docs/Detailed-Topics/Library-Management.html
1. http://www.scala-sbt.org/0.12.1/docs/Detailed-Topics/Launcher.html


## Reader Monad

1. http://blog.originate.com/blog/2013/10/21/reader-monad-for-dependency-injection/
1. Jason Arhart
1. https://cdn.parleys.com/p/53a7d2d1e4b0543940d9e56f/ADm552EgQj9-ReaderMonadforDI.pdf
1. https://parleys.com/play/53a7d2d1e4b0543940d9e56f/chapter0/downloads

## Some scala notes

1. object creation impossible, since method foldr in trait List of type [B](z: B)(op: (Nothing, B) => B)B is not defined	
1. Find side-effect-free short-cut for debugging.. on the lines..
1. val output = expression ~~ println

## Tonnie notes

1. I write all the personal notes to LectureNotes directory with prefix of Tonnie_***, It is in the memory of my friend Tonnie
