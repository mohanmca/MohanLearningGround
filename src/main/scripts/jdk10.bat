@echo on
echo Setting JAVA_HOME
set JAVA_HOME=D:\Apps\Java\jdk-10.0.1

REM Setting M2_HOME
SET M2_HOME=D:\Apps\apache-maven-3.5.4
echo "M2_HOME - %M2_HOME%"


REM Setting SCALA_HOME
SET SCALA_HOME=D:\apps\scala-2.12.6
echo "SCALA_HOME - %SCALA_HOME%"

REM Setting SBT_HOME
SET SBT_HOME=D:\Apps\sbt-1.2.6\sbt
echo "SBT_HOME - %SBT_HOME%"

REM Setting MYSQL in Path
REM C:\Program Files\MySQL\MySQL Server 8.0\bin

SET MYSQL_SERVER_HOME=C:\Program Files\MySQL\MySQL Server 8.0

REM include jdk bin first
set PATH=%JAVA_HOME%\bin;%M2_HOME%\bin;%SCALA_HOME%\bin;%SBT_HOME%\bin;%MYSQL_SERVER_HOME%\bin;%PATH%

echo Display java version
java -version