#	Why SBT?
* A reactive development environment. (You change your source code, and sbt reruns your tests.)
* Allowing developers to use the Scala REPL on their projects.

# Archaeology
* Ant - target and no workflow
* Maven - Workflow -> Phases(*) -> Targets (*)
  * Opinionated workflow
  * Maven lifecycle is a series of sequential phases. 
  * Each phase can contain zero or more tasks (tasks == goals)
  * A goal is actually a goal within a plugin. 
  * Validate, Compile, Test, Package, Integration Test, Verify, Install and Deploy
  * Maven watcher plugins - https://github.com/rzymek/watcher-maven-plugin
  * a goal is executed in a phase, and the phases are executed sequentially.
* Gradle
  * build.gradle is Groovy based, follows maven convention
  * Gradle build files are more concise
  * Lacks - Interactive console features (as of 2014)
    * Continious build is available - https://docs.gradle.org/current/userguide/command_line_interface.html#sec:continuous_build
  * Gradle’s task dependency mechanism isn’t explicit
 * Maven, Gradle, and Ant aren’t really interactive environments.

# Why SBT?
*  With sbt, each task has an output and explicit dependencies
*  build typessafety, flexibility and the ability to insert custom tasks easily
*  tasks can be executed in parallel by default. 
*  Sbt uses the dependency tree to ensure that tasks are executed in the right order.
*  SBT has no phases, goals or executions (it has only tasks)
*  SBT := Tasks + Settings ( + Configurations)
*   crossScalaVersions - supports crossVersion of scala output library

# What is SBT?
* If you run the test task, sbt will run the compile task beforehand.
* Once task is generic enough or useful enough, you can turn the task into a plugin
* Every task that was executed has a value in SBT (lack of value shows that it wasn't executed)
* sbt uses Ivy for its dependency resolution, whereas Maven uses Aether.
* The ability to explore your project using the Scala REPL

# Settings and initialization
* ":="  Assigns an initialization expression to a key. Overrides any previous value.
* In sbt all settings can be implemented in terms of the := operator. For example, foo += bar.value is just foo := foo.value + bar.value.
* You can access the value of another setting using the value method, version.value, organization.value
* libraryDependencies  :: Seq[ModuleID]
  * Has += and ++= (one for single value append and another to append Seq[ModuleID]
* Initializations are code
  *  Initializations can read environment variables, properties, files, or anything else available in the JVM.
* Settings can depend on other settings like spread-sheet, be careful about circular dependency
* := operator used to assign value, whereas "=" define the placeholder for the variable of task or settings

# SBT Configuration
* Configurations are namespaces for keys. (like packages) [sources in Compile vs sources in Test]
* Configurations allow the same key, or column, to be reused to serve different purposes.
* Compile, Test, Runtime, IntegrationTest
* ThisBuild configuration would be used for catch-all scenario

# SBT Session
```
sbt
console
projects
reload #sbt has been changed in another console
tasks
settings <tab>
test
testOnly <tab>
```

# Checklists
*  A rule of thumb, if there is an Exception-InInitalization when starting an sbt, grep of your build.sbt files for a val without the lazy. lazy may solve the problem as some values are used before it is being executed
* In sbt, the fundamental unit of concurrency is the task. To increase parallelism of your builds, you need to have more tasks that can run simultaneously. 
 
* Define a task in sbt that takes the output of a shell command and retrieves the first line:
```SBT
val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")
gitHeadCommitSha := Process("git rev-parse HEAD").lines.head
```