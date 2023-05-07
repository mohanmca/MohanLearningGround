1. vehicle_id,vehicle_type,toll_plaza_id
2. sudo mkdir -p /home/project/airflow/dags/finalassignment/staging
3. wget https://cf-courses-data.s3.us.cloud-object-storage.appdomain.cloud/IBM-DB0250EN-SkillsNetwork/labs/Final%20Assignment/tolldata.tgz -P
   1. https://cf-courses-data.s3.us.cloud-object-storage.appdomain.cloud/IBM-DB0250EN-SkillsNetwork/labs/Final%20Assignment/tolldata.tgz
   2. /home/project/airflow/dags/finalassignment
4. wget https://cf-courses-data.s3.us.cloud-object-storage.appdomain.cloud/IBM-DB0250EN-SkillsNetwork/labs/Final%20Assignment/tolldata.tgz | tar xvz --overwrite  -C /home/project/airflow/dags/finalassignment


When you extract the zip file you should see the following 3 files.

Filelist:

vehicle-data.csv
tollplaza-data.tsv
payment-data.txt

vehicle-data.csv:

vehicle-data.csv is a comma-separated values file.
It has the below 6 fields

Rowid  - This uniquely identifies each row. This is consistent across all the three files.
Timestamp - What time did the vehicle pass through the toll gate.
Anonymized Vehicle number - Anonymized registration number of the vehicle
Vehicle type - Type of the vehicle
Number of axles - Number of axles of the vehicle
Vehicle code - Category of the vehicle as per the toll plaza.


tollplaza-data.tsv:
tollplaza-data.tsv is a tab-separated values file.
It has the below 7 fields

Rowid  - This uniquely identifies each row. This is consistent across all the three files.
Timestamp - What time did the vehicle pass through the toll gate.
Anonymized Vehicle number - Anonymized registration number of the vehicle
Vehicle type - Type of the vehicle
Number of axles - Number of axles of the vehicle
Tollplaza id - Id of the toll plaza
Tollplaza code - Tollplaza accounting code.

payment-data.txt:

payment-data.txt is a fixed width file. Each field occupies a fixed number of characters.

It has the below 7 fields

Rowid  - This uniquely identifies each row. This is consistent across all the three files.
Timestamp - What time did the vehicle pass through the toll gate.
Anonymized Vehicle number - Anonymized registration number of the vehicle
Tollplaza id - Id of the toll plaza
Tollplaza code - Tollplaza accounting code.
Type of Payment code - Code to indicate the type of payment. Example : Prepaid, Cash.
Vehicle Code -  Category of the vehicle as per the toll plaza.

 1-2 - RowId
 4-28 - Timestamp
29-36 - Anonymized Vehicle number
40-44 - Toll plaza id
45-54 - Toll plaza code
55-58 - Payment code 
59-64 - Vehicle code

 4 Mon Aug  2 18:23:45 2021 5521221    4095 PC3E1512A PTP VC965
 5 Thu Jul 29 22:44:20 2021 3267767    4135 PCC943ECD PTE VC965
 6 Sat Aug 14 03:57:47 2021 8411850    4680 PCC422F4D PTE VC965
 7 Thu Aug 12 03:41:22 2021 6064250    4702 PCDBC3AC9 PTE VC965
 8 Sun Aug 22 10:29:58 2021 6871937    4592 PC627AA14 PTE VCD2F
 9 Fri Aug  6 14:23:08 2021 2055930    4100 PCC6DD8D5 PTP VC965
10 Sun Aug 15 13:43:51 2021 8775910    4143 PC5F47DCF PTC VC965
11 Wed Aug  4 15:52:13 2021 4525747    4466 PC6E93A05 PTE VC965

--
sudo mkdir -p /home/project/airflow/dags/finalassignment/staging
cd /home/project/airflow/dags/finalassignment/staging
sudo wget https://cf-courses-data.s3.us.cloud-object-storage.appdomain.cloud/IBM-DB0250EN-SkillsNetwork/labs/Final%20Assignment/tolldata.tgz
--

10 Sun Aug 15 13:43:51 2021 8775910    4143 PC5F47DCF PTC VC965
cut -b1-7,59-62,63-67 < payment-data.txt > 

cut -d"," -f1-4  /home/project/airflow/dags/finalassignment/vehicle-data.csv > ~/csv-data.csv
cut -d$'\t' -f5-7  /home/project/airflow/dags/finalassignment/tollplaza-data.tsv |  awk 'BEGIN{OFS=","} {print $1,$2,$3}' > ~/tsv-data.csv
cut -b1-7,59-62,63-67 < /home/project/airflow/dags/finalassignment/payment-data.txt | awk 'BEGIN{OFS=","} {print $1,$2,$3}' > ~/fixed_width_data.csv  

paste -d, csv-data.csv tsv-data.csv fixed_width_data.csv

cut -b1-7,59-62,63-67 < payment-data.txt | awk 'BEGIN{OFS=","} {print $1,$2,$3}'

----

https://github.com/oguzkedek/ETL-and-Data-Pipelines-with-Shell-Airflow-and-Kafka/blob/main/ETL_Server_Access_Log_Processing.py
create table toll

https://mohanmca-8080.theiadocker-1-labs-prod-theiak8s-4-tor01.proxy.cognitiveclass.ai

mysql --host=127.0.0.1 --port=3306 --user=root --password=MjI1MTctbW9oYW5t
create database tolldata;
use tolldata;
use tolldata;
create table livetolldata(timestamp datetime,vehicle_id int,vehicle_type char(15),toll_plaza_id smallint);
python3 -m pip install kafka-python
python3 -m pip install mysql-connector-python==8.0.31
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &
bin/kafka-topics.sh --create --topic toll --bootstrap-server localhost:9092 --partitions 2
bin/kafka-topics.sh --topic toll --bootstrap-server localhost:9092 --delete
wget https://cf-courses-data.s3.us.cloud-object-storage.appdomain.cloud/IBM-DB0250EN-SkillsNetwork/labs/Final%20Assignment/toll_traffic_generator.py

## Kafka installation

```bash
wget https://archive.apache.org/dist/kafka/2.8.0/kafka_2.12-2.8.0.tgz
tar -xzf kafka_2.12-2.8.0.tgz
cd kafka_2.12-2.8.0
bin/zookeeper-server-start.sh config/zookeeper.properties 
cd kafka_2.12-2.8.0
bin/kafka-server-start.sh config/server.properties
cd kafka_2.12-2.8.0
bin/kafka-topics.sh --create --topic news --bootstrap-server localhost:9092
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
bin/kafka-console-producer.sh --topic news --bootstrap-server localhost:9092
bin/kafka-console-consumer.sh --topic weather --bootstrap-server localhost:9092 --from-beginning
/tmp/kakfa-logs
bin/kafka-topics.sh --create --topic bankbranch --bootstrap-server localhost:9092 --partitions 2
bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic bankbranch
bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic bankbranch
{"atmid": 1, "transid": 100}
{"atmid": 1, "transid": 101}
{"atmid": 2, "transid": 200} 
{"atmid": 2, "transid": 200}
{"atmid": 2, "transid": 200} 

bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic bankbranch --property parse.key=true --property key.separator=:
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic bankbranch --group atm-app
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group atm-app
--to go back two messages for the entire consumer-groups
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092  --topic bankbranch --group atm-app --reset-offsets --shift-by -2 --execute
--we get number of consumer times 2 message, since above line offset was moved by 2
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic bankbranch --group atm-app
```
