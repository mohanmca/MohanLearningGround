### If you have access to SSH, can you connect to remote machine to another port from localhost?
1. Yes, Use SSL tunnelling
2. ssh -L localhost:23473:remotehost:23473 mohan.narayanaswamy@remotehost -N -v -f
3. Finally connect to localhost:23473

## Kill All Java
```bash
kill -9 $(ps -ef | grep java  | awk '{print $2}'  | tr '\n' ' ')
```


## Find docker.service log from systemd journal
```
sudo journalctl -u docker.service
```

## How to recursively grep for multiple words in specific extension file

```
grep -r --include="*.bazel" -e "JSW_IDX_FIELD" -e "JSW_FIELD" .
```

## How to confirm if port is open without connecting to it?
1. ```nc -v -z localhost 9038``` Or ```lsof -i :9038```
1. NC is telling netcat to perform a verbose port scan on port 9038 of the localhost (the local machine).
2. Without actually establishing a connection to any listening service on that port.
3. It will simply report whether the port is open or not.


## How to grep for log in multiple machine
```bash
for x in 2 3; do ssh aws-worker-00$x.aws-sandbox.host 'grep  -i xception /var/log/'; done
for x in 2 3; do ssh aws-worker-00$x.aws-sandbox.host 'cat /proc/cmdline'; done
```

## List of commands
1. List of files that are more than G - ```du -sh * | grep "G\s"``
2. How to stop nomand jobs - ```nomad job stop -purge job_name;nomad job status job_name```
3. How to find list of changes made in AWS host -- 'search in cloudtrail - ResouceType == AWS::RDS::DBCluster'
4. How to find ipaddress for a hostname ```dig +short hostname```
5. Find list of cronjob for user root ```sudo crontab -l -u root```
6. How to troubleshoot ssl connection ```openssl s_client --connect hostname:8200 --showcerts -verify 1```


## When multiple files are released, and disk space is not relesed, what to do?
1. Restart the rsyslog
2. ```sudo lsof | grep containers;sudo systemctl restart rsyslog```

## Sed invalid json into json by introducing [ and ] and , ?
1. introduce , between }{
2. introduce , between } | {, "So consider | {"
3. introduce [ and ]
5. ```grep "Failed to process" extract-2024-08-07T07_35_23.171Z.csv | egrep -o "{.*}" | sed 's/""/"/g' | head -1 | sed -e 's/}{/},{/g' -e 's/|{/,{/g' -e 's/| {/,{/g' -e  's/^/[&/' -e 's/$/&]/' | jq ".[0]"```

### Find Java Environemnt Variable from Shell
<details><summary>show</summary>
<p>

```bash
ps e -ww -p 11404 | tr ' ' '\n'  | sort
```
</p>
</details> 

### What are all SSH commands
<details><summary>show</summary>
<p>

```bash
killall ssh-agent 
ssh-agent  - is a helper program that keeps track of users' identity keys and their passphrases.
ssh-add - command prompts the user for a private key password and adds it to the list maintained by ssh-agent.
```
</p>
</details>


## To list the agents
1. ssh-add -l
2. ssh-add -L > /Users/mohan.narayanaswamy/.ssh/id_piv.pub
3. Ensure SSH keys are added to AD to connect to AWS

## Forward your keys to AWS box
1. ssh -A devbox-aws.host.com
2. ForwardAgent yes will just save you from adding -A each time (in the ssh config file)

# What are the Environment variable for CLIENT CERT PATH
export CLIENT_PKCS12_PATH=${KAFKA_CERTPATH}/kafka-ssl.p12
export CLIENT_PKCS12_PASSWORD=${KAFKA_SSL_PASSWORD}

## Where is JDK installed in Ubuntu
/bin/java -> /etc/alternatives/java
/etc/alternatives/java -> /usr/lib/jvm/adoptopenjdk-11-hotspot-jre-amd64/bin/java

## Find and grep files in MAC
find . -type f -print0 | xargs -0 grep -l "wire\[BackendEmails"

### Sum all the numbers in 1st column

<details><summary>show</summary>
<p>

```bash
gfind -name "*.scala" -exec wc -l {} \; | awk '{print $1}' | awk '{s+=$1} END {print s}'
```
</p>
</details> 


### First line of each bash script

<details><summary>show</summary>
<p>

```bash
#!/bin/bash
set -euo pipefail
In this example:

-e: The script exits on any error.
-u: The script treats unset variables as errors.
-o pipefail: The script fails if any command in a pipeline fails. (otherwise fails only if lastcommand fails)
```
</p>
</details> 

## Find the Canonical path from actual path

readlink -f -- /root/data/app/data.txt --> /local/1/apps/rates_etrading/

###  Grep log file that within 10 minutes of interval
<details><summary>show</summary>
<p>

```bash
CURRENT_10_MIN=`date +'%Y-%m-%d %H:%M'|cut -c 1-15`
PREV_10_MIN=`eval date -d \"10 min ago\"  \"+'%Y-%m-%d %H:%M'\"|cut -c 1-15`
echo "grep \"${CURRENT_10_MIN}\" ${SERVER_LOGDIR}/${SERVER_LOG}|grep -v \"time string: 00000000T00\"
echo "grep \"${PREV_10_MIN}\" ${SERVER_LOGDIR}/${SERVER_LOG}|grep -v \"time string: 00000000T00\"
```
</p>
</details>  

### Grep between two lines
<details><summary>show</summary>
<p>

```bash
awk '/FromText/,/ToText/' logfile.log
```
</p>
</details>

### Grep first and last occurence and in-between lines
<details><summary>show</summary>
<p>

```bash
$ read first last <<< $(grep -n stackoverflow your_file | awk -F: 'NR==1 {printf "%d ", $1}; END{print $1}')
$ awk -v f=$first -v l=$last 'NR>=f && NR<=l' your_file
```
</p>
</details> 

### Find block of code in set of files that contains some word
<details><summary>show</summary>
<p>

for file in $(egrep -l "report" `find . -name "*.ext"`); do awk '/Block {/,/}/' $file; done
</p>
</details>  

/In sed wherever ' should be the target key, we should use '\'' (3 characters)
<details><summary>show</summary>
<p>

```bash
sed -e 's/^/('\''/' -e 's/$/'\'')/'  csv.txt > Ac.txt
```
</p>
</details>  

### Bulk rename file.. remove first 30 characters.
<details><summary>show</summary>
<p>

```bash
ls -1 *.pdf | awk '{print  "mv " $1, substr($1,31,30)}' > ren.sh
chmod 777  ren.sh
./ren.sh
```
</p>
</details>  

## Concatenate multiple files into one file
<details><summary>show</summary>
<p>

```bash
export files=`gfind . -type f`; 
export concat=output.txt
for file in $files; do echo $file >> $concat; cat $file >> $concat; done 
</p>
</details>  

### Find duplicate lines that are more than 5 characters to find duplicate lines.
<details><summary>show</summary>
<p>

```bash
gawk "length($0)>5" passport.js | gsort | sed -e 's/^[ \t]*//' | sed -e 's/[ \t]*$//' | gsort | uniq  -c | gsort -nr | grep -v "1 "
```
</p>
</details>  

### Analyze one particular line
<details><summary>show</summary>
<p>
```bash
egrep --no-filename -r "distribute " * | sed -r -e "s/[\t]+//g" | sed -r -e "s/^[/t ]*//g" | sed -r -e "s/[/t ]*$//g"| sort | uniq | egrep -v -i site > /tmp/distribute.txt
```
</p>
</details>  


### find the class inside multiple jars.
<details><summary>show</summary>
<p>

```bash
for file in `find . -type f -name "*.jar"`; do unzip -l  $file; done | egrep -i className
```
</p>
</details>

### find the json inside default link.
<details><summary>show</summary>
<p>

```bash
for a in `find . -maxdepth 2 -type l -name "default*"`; do find $a/conf/ -type f -name "*.json"; done;


### Merge all the file with name..
<details><summary>show</summary>
<p>

```bash
for file in  `find . -name "*.yml"` ; do echo $file; cat $file; done


### find command line arguments
<details><summary>show</summary>
<p>

```bash
ps -ww -fp 28862
cat  /proc/28862/cmdline

### Find all the process that is connecting to remote port 1468
<details><summary>show</summary>
<p>

```bash
lsof+-i+:1468

### Recent Files
<details><summary>show</summary>
<p>

```bash
find . -mmin -$((60*24))  -name "*[^0-9].log" | grep -v something.log 

### Old Files
<details><summary>show</summary>
<p>

```bash
find . -mmin +$((60*24))  -name "*[^0-9].log" | grep -v something.log 

### Merge all the files
<details><summary>show</summary>
<p>

```bash
find . -iname '*.java' -exec cat {} \; -exec echo \; > concatenated_source.java

### file with 1 to 100
<details><summary>show</summary>
<p>

```bash
for var in `seq 1 100`; do echo $var; done > seq.txt

### recursively egrep only in html files
<details><summary>show</summary>
<p>

```bash
egrep -r -l --include=*.html some_text  *


### SBT command line behind proxy
<details><summary>show</summary>
<p>

```bash
set JAVA_OPTS=-Dhttp.proxySet=true -Dhttp.proxyHost=proxy.com -Dhttp.proxyPort=8080 -Dhttp.proxyUser=test  -Dhttp.proxyPassword=Password

### Execute script that is available in URL
<details><summary>show</summary>
<p>

```bash
curl -sSL https://get.docker.io/ubuntu/ | sudo sh

### Find and replace set of files
<details><summary>show</summary>
<p>

```bash
sed -i ‘s/textToFind/replacementText/g’ `grep -ril ‘textToFind’ *`

### Find all the file extensions recursively from current folder.
<details><summary>show</summary>
<p>

```bash
find -type f | awk -F. '{print $NF}' | sort | uniq -c | sort -nr
```
</p>
</details>  

### recursively unzip files
<details><summary>show</summary>
<p>

```bash
find . -name "*.gz" -exec gunzip '{}' \;
```
</p>
</details>  

### Bulk rename of gz to xml
<details><summary>show</summary>
<p>

```bash
ls -1 | awk -F. '{print "mv \"" $1".gz\"", "\""$1".xml\""}'
```
</p>
</details>  

### Sort based on substring of filename
<details><summary>show</summary>
<p>

```bash
find -type f -name \*gz | awk -F"/" '{print substr($4,1,16)}' | sort -nr
```
</p>
</details>  

### Find the number of files group by directory
<details><summary>show</summary>
<p>

```bash
find . -type d -exec sh -c "fc=\$(find '{}' -type f | wc -l); echo -e \"\$fc\t{}\"" \; | sort -nr
```
</p>
</details>  

### Find if className available in any of the jar file
<details><summary>show</summary>
<p>

```bash
find /home/user/project/ -name "dependency*.jar" -exec jar tvf '{}' \; | grep -i "classname"
```
</p>
</details>  

### Count number of lines in all successive directory in certain files alone.
<details><summary>show</summary>
<p>

```bash
for file in `find -name "InputLog.csv"`; do echo $file; grep -v 'NotInterestedLines' $file | grep -E -w -c InteresteLine; done
```
</p>
</details>  

### Print rows into columns
<details><summary>show</summary>
<p>

```bash
awk 'BEGIN { FS = "," }; {for (i=1;i<NF; i++) {print i,$i} }' FavourteCSV.txt | head -10
```
</p>
</details>  

### Grep set of string in single file.
<details><summary>show</summary>
<p>

```bash

while read i ; do egrep ",$i," input_text.log; done setOfStringInMultipleLine.txt &
```
</p>
</details>  

### Or Generate egrep using following command
<details><summary>show</summary>
<p>

```bash
cat files.txt | sort -r | tr '\n' '|' | sed s'/.$//' | sed s'/|/_|_/g' | sed s'/^/_/' | sed s'/$/_/' | sed s'/^/egrep "(/' | sed s'/$/)"/'
sdiff -w 175 file1.csv file2.csv |egrep '\||>| diff.txt
```
</p>
</details>  

### Find which svn commit, a souce line removed/added inside code/configuration file.
<details><summary>show</summary>
<p>

```bash
C:\Mohan\Workspaces\workspace\TRUNK\application-component\src\main\resources>svn log -l 30 -v --diff configuration_server.xml > svn_log_entries.txt
cat svn_log_entries.txt | grep "ChangedLine"
```
</p>
</details>  

### Find all the Epoch seconds (date) that are not not fall in 10:00AM
<details><summary>show</summary>
<p>

```bash
gawk '{print $1, $2, $3, strftime("%c", $6)}' isProblematic.txt | grep -v "10:00:00 AM"
```
</p>
</details>  

### Given key value tex file, replace all the key with value recusively in text file.
<details><summary>show</summary>
<p>

```bash
while read line;do export oldTime=`echo $line | awk '{print $1}'`; export newTime=`echo $line | awk '{print $2}'`; find ./ -type f -name "*.properties" -exec perl -p -i -e s/$oldTime/$newTime/g {} \; ; done < /var/tmp/Mohan/find_and_replace/diff_time.csv

echo "[{\"toto\":5},{\"toto2\":5}]" | python -c 'import json,sys;obj=json.load(sys.stdin);print str(obj[0]["toto"]) + "," + str(obj[0]["toto"])'
```
</p>
</details>  

### Create sbt directories using shell script
<details><summary>show</summary>
<p>

```bash
#!/bin/sh
mkdir -p src/{main,test}/{java,resources,scala}
mkdir lib project target
```
</p>
</details>  


## Cut the password file and filter 3 to 6 fields

1. cut -d":" -f3-6  /etc/passwd
2. cut -d":" -f1,3,6 /etc/passwd


## Transform lowercase to uppercase
1. echo "Shell Scripting" | tr "[a-z]" "[A-Z]" 
2. echo "Shell Scripting" | tr "[a-z]" "[A-Z]" 

## Squeezing repeating occurrences of characters
1. ps | tr -s " "
2. echo "Delete my pin from the prompt 5634" | tr -d "[:digit:]"

## Generate mdanki
mdanki shell.md shell.apkg --deck "Mohan::DeepWork::shell"
