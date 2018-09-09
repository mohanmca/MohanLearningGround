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

# You can create a configuration file my.ini and use to set configuration parameters to database:

# my.ini file content:
[mysqld]
# set basedir to your installation path
basedir=<MYSQLDIR>
# set datadir to the location of your data directory
datadir=<MYSQLDIR>\data

<MYSQLDIR>\bin\mysqld --init-file=<MYSQLDIR>\my.ini
