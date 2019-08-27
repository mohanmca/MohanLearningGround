### Sum all the numbers in 1st collumn
<details><summary>show</summary>
<p>
```bash
  gfind -name "*.scala" -exec wc -l {} \; | awk '{print $1}' | awk '{s+=$1} END {print s}'
```
</p>
</details>  
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

//Find block of code in set of files that contains some word
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

export files=`gfind . -type f`; 
export concat=output.txt
for file in $files; do echo $file >> $concat; cat $file >> $concat; done 

//Find duplicate lines that are more than 5 characters to find duplicate lines.
<details><summary>show</summary>
<p>
```bash
gawk "length($0)>5" passport.js | gsort | sed -e 's/^[ \t]*//' | sed -e 's/[ \t]*$//' | gsort | uniq  -c | gsort -nr | grep -v "1 "
```
</p>
</details>  

Analyze one particular line

egrep --no-filename -r "distribute " * | sed -r -e "s/[\t]+//g" | sed -r -e "s/^[/t ]*//g" | sed -r -e "s/[/t ]*$//g"| sort | uniq | egrep -v -i site > /tmp/distribute.txt


### find the class inside multiple jars.
for file in `find . -type f -name "*.jar"`; do unzip -l  $file; done | egrep -i className

### find the json inside default link.
for a in `find . -maxdepth 2 -type l -name "default*"`; do find $a/conf/ -type f -name "*.json"; done;


### Merge all the file with name..
for file in  `find . -name "*.yml"` ; do echo $file; cat $file; done


### find command line arguments
ps -ww -fp 28862
cat  /proc/28862/cmdline

### Find all the process that is connecting to remote port 1468
lsof+-i+:1468

### Recent Files
find . -mmin -$((60*24))  -name "*[^0-9].log" | grep -v something.log 

### Old Files
find . -mmin +$((60*24))  -name "*[^0-9].log" | grep -v something.log 

### Merge all the files
find . -iname '*.java' -exec cat {} \; -exec echo \; > concatenated_source.java

### file with 1 to 100
for var in `seq 1 100`; do echo $var; done > seq.txt

### recursively egrep only in html files
egrep -r -l --include=*.html some_text  *


### SBT command line behind proxy
set JAVA_OPTS=-Dhttp.proxySet=true -Dhttp.proxyHost=proxy.com -Dhttp.proxyPort=8080 -Dhttp.proxyUser=test  -Dhttp.proxyPassword=Password

### Execute script that is available in URL
curl -sSL https://get.docker.io/ubuntu/ | sudo sh

### Find and replace set of files
sed -i ‘s/textToFind/replacementText/g’ `grep -ril ‘textToFind’ *`

### Find all the file extensions recursively from current folder.

find -type f | awk -F. '{print $NF}' | sort | uniq -c | sort -nr

### recursively unzip files
find . -name "*.gz" -exec gunzip '{}' \;

### Bulk rename of gz to xml
ls -1 | awk -F. '{print "mv \"" $1".gz\"", "\""$1".xml\""}'

### Sort based on substring of filename
find -type f -name \*gz | awk -F"/" '{print substr($4,1,16)}' | sort -nr

### Find the number of files group by directory
find . -type d -exec sh -c "fc=\$(find '{}' -type f | wc -l); echo -e \"\$fc\t{}\"" \; | sort -nr

### Find if className available in any of the jar file
find /home/user/project/ -name "dependency*.jar" -exec jar tvf '{}' \; | grep -i "classname"

### Count number of lines in all successive directory in certain files alone.
for file in `find -name "InputLog.csv"`; do echo $file; grep -v 'NotInterestedLines' $file | grep -E -w -c InteresteLine; done

### Print rows into columns
awk 'BEGIN { FS = "," }; {for (i=1;i<NF; i++) {print i,$i} }' FavourteCSV.txt | head -10

### Grep set of string in single file.
while read i ; do egrep ",$i," input_text.log; done setOfStringInMultipleLine.txt &

### Or Generate egrep using following command
cat files.txt | sort -r | tr '\n' '|' | sed s'/.$//' | sed s'/|/_|_/g' | sed s'/^/_/' | sed s'/$/_/' | sed s'/^/egrep "(/' | sed s'/$/)"/'

sdiff -w 175 file1.csv file2.csv |egrep '\||>| diff.txt

### Find which svn commit, a souce line removed/added inside code/configuration file.
C:\Mohan\Workspaces\workspace\TRUNK\application-component\src\main\resources>svn log -l 30 -v --diff configuration_server.xml > svn_log_entries.txt
cat svn_log_entries.txt | grep "ChangedLine"

### Find all the Epoch seconds (date) that are not not fall in 10:00AM
gawk '{print $1, $2, $3, strftime("%c", $6)}' isProblematic.txt | grep -v "10:00:00 AM"

### Given key value tex file, replace all the key with value recusively in text file.
while read line;do export oldTime=`echo $line | awk '{print $1}'`; export newTime=`echo $line | awk '{print $2}'`; find ./ -type f -name "*.properties" -exec perl -p -i -e s/$oldTime/$newTime/g {} \; ; done < /var/tmp/Mohan/find_and_replace/diff_time.csv

echo "[{\"toto\":5},{\"toto2\":5}]" | python -c 'import json,sys;obj=json.load(sys.stdin);print str(obj[0]["toto"]) + "," + str(obj[0]["toto"])'

### Create sbt directories using shell script
#!/bin/sh
mkdir -p src/{main,test}/{java,resources,scala}
mkdir lib project target