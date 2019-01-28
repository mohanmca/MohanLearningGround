```ini
#D:\Apps\mysql-8.0.13-winx64\my.ini
[mysqld]
port=3306
basedir=D:\\Apps\\mysql-8.0.13-winx64
datadir=D:\\Apps\\mysql-8.0.13-winx64\\data
max_allowed_packet=16M

[client]
port=3306
```

# Initialize database in insecure-more
```bash
D:\Apps\mysql-8.0.13-winx64\bin\mysqld --defaults-file=D:\Apps\mysql-8.0.13-winx64\my.ini --initialize-insecure --console

mysqld
mysql -u root -p
CREATE USER 'euler'@'localhost' IDENTIFIED BY 'euler';
GRANT ALL PRIVILEGES ON * . * TO 'euler'@'localhost';
FLUSH PRIVILEGES;
GRANT type_of_permission ON database_name.table_name TO ‘username’@'localhost’;
REVOKE type_of_permission ON database_name.table_name FROM ‘username’@‘localhost’;
SHOW GRANTS username;
```

# Change root password (MySQL 8.0.14 on Windows)
```
mysql -u root
use mysql;
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
FLUSH PRIVILEGES;
```

# Now the password was changed, start database normally:

<MYSQLDIR>\bin\mysqld
# ACCESS database shell
<MYSQLDIR>\bin\mysql -u root -p

```SQL
mysqld --initialize-insecure set password='root'
mysqld --console --port 3306
mysql -u root -p
CREATE DATABASE thales CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```


# Most useful MySQL SQL
```sql

SELECT table_name FROM information_schema.tables where table_schema='emp';

SELECT * 
FROM   information_schema.tables 
WHERE  table_type = 'BASE TABLE' 
       AND table_schema = 'thales' 

SELECT columns.* 
FROM   information_schema.columns columns, 
       information_schema.tables tables 
WHERE  tables.table_schema = 'thales' 
       AND columns.table_name = tables.table_name

ALTER TABLE perforredingtonvmwaretrivandrumheldon2registrationreport ALTER j SET DEFAULT 1000;

ALTER TABLE thales.perforredingtonvmwaretrivandrumheldon2registrationreport
ADD COLUMN TS DATETIME  DEFAULT 20190101020000 AFTER source;
```

<MYSQLDIR>\bin\mysqld --init-file=<MYSQLDIR>\my.ini
  
# MYSQL
```sql
mysql> use mysql;
mysql> show tables;
mysql> show databases;
mysql> describe tableName;
mysql> CREATE DATABASE thales;
mysql> CREATE USER 'project_user'@'localhost' IDENTIFIED BY 'PASSWORD';
mysql> GRANT ALL ON `project_database`.* TO 'project_user'@'localhost';
```

## Mysql Reset Root password
```bash
net stop mysql 
mysqld --defaults-file="..." --skip-grant-tables 
mysql (Another parallel session)
select user,host,authentication_string from mysql.user; 
-- reset authentication_string (password before 5.5.7) for the admin user ...
update mysql.user  
set authentication_string=PASSWORD('new_pass'),password_expired='N', where user='root'; 
flush privileges; 
exit;  (Another parallel session - stops)
mysqladmin shutdown 
net stop mysql 
```
  
# Refernces
* [Mysql manual] (http://g2pc1.bu.edu/~qzpeng/manual/MySQL%20Commands.htm)
