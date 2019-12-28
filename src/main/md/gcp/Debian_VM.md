```bash
sudo passwd //TO: Change root password
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

```bash
sudo snap install docker
```

```bash
sudo install unzip
sudo bash
sudo mkdir -p /project/elasticsearch/
cd /project/elasticsearch/
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.8.6.zip
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.8.6.zip.sha512
shasum -a 512 -c elasticsearch-6.8.6.zip.sha512 
unzip elasticsearch-6.8.6.zip
cd elasticsearch-6.8.6/ 
```

```bash
mkdir -p  /project/elasticsearch/
useradd -d /project/elasticsearch/ elasticsearch
chown elasticsearch:elaticsearch /project/elasticsearch
passwd elasticsearch
```