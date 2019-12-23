```bash
sudo apt-get update
sudo apt-get install default-jdk
sudo apt-get install software-properties-common
sudo add-apt-repository "deb http://ppa.launchpad.net/webupd8team/java/ubuntu xenial main"
sudo apt-get update
sudo apt-get install oracle-java8-installer

ls -ltar /usr/bin/java
/usr/bin/java -> /etc/alternatives/java
ls -ltar /etc/alternatives/java
/etc/alternatives/java -> /usr/lib/jvm/java-11-openjdk-amd64/bin/java
```