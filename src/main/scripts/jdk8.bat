@echo off
echo Setting JAVA_HOME
set JAVA_HOME=D:\Apps\Java\jdk1.8.0_181
echo setting PATH
set PATH=%JAVA_HOME%\bin;%PATH%
echo "JDK path - %JAVA_HOME%"
echo Display java version
java -version