# Initialize database
<MYSQLDIR>\bin\mysqld --initialize

# Initialize database without password
mysqld --initialize-insecure
set password='yourPass'

# Start the database
<MYSQLDIR>\bin\mysqld --console --port 3306

# Change root password

## Create file mypassword.ini with the following content (change the passord):
* shutdown mysqld process
```
ALTER USER 'root'@'localhost' IDENTIFIED BY 'MyNewPass';
<MYSQLDIR>\bin\mysqld --init-file=<PATH>\mypassword.ini
```

# Now the password was changed, start database normally:

<MYSQLDIR>\bin\mysqld
# ACCESS database shell
<MYSQLDIR>\bin\mysql -u root -p

```SQL
mysqld --initialize-insecure set password='root'
mysqld --console --port 3306
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';  //this content should be inside the password.ini
SET GLOBAL default_storage_engine = 'InnoDB';
mysqld --init-file=password.ini
mysql -u root -p
CREATE DATABASE thales CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

# You can create a configuration file my.ini and use to set configuration parameters to database:

# my.ini file content:
[mysqld]
# set basedir to your installation path
basedir=<MYSQLDIR>
# set datadir to the location of your data directory
datadir=<MYSQLDIR>\data

<MYSQLDIR>\bin\mysqld --init-file=<MYSQLDIR>\my.ini
  
  # MYSQL
  ```sql
  mysql> use mysql;
  mysql> show tables;
  mysql> show databases;
  mysql> describe tableName;
  mysql> CREATE DATABASE thales;
  ```
  
# Most often used SQLs
```sql
SELECT table_name FROM information_schema.tables where table_schema='emp';
```  
  
# Refernces
* [Mysql manual] (http://g2pc1.bu.edu/~qzpeng/manual/MySQL%20Commands.htm)
