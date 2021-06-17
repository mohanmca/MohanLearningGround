@echo off
echo Setting JAVA_HOME
set JAVA_HOME=D:\Apps\Java\jdk-13.0.2


REM Setting SCALA_HOME
SET SCALA_HOME=D:\apps\scala-2.12.6
echo "SCALA_HOME - %SCALA_HOME%"

REM Setting M2_HOME
SET M2_HOME=D:\Apps\apache-maven-3.5.4
echo "M2_HOME - %M2_HOME%"

REM Setting SBT_HOME
SET SBT_HOME=D:\apps\sbt-1.1.5
echo "SBT_HOME - %SBT_HOME%"


echo setting PATH
set PATH=%JAVA_HOME%\bin;%M2_HOME%\bin;%SCALA_HOME%\bin;%SBT_HOME%\bin;%PATH%
echo "JDK path - %JAVA_HOME%"
echo Display java version
java -version