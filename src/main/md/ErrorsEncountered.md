* Error: ER_NOT_SUPPORTED_AUTH_MODE: Client does not support authentication protocol requested by server; consider upgrading MySQL client
	* It happened in mysql-8
	* For above solution is ~~> ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root'	Error Code: 1290. 
	* But It is unable to The MySQL server is running with the --skip-grant-tables option so it cannot execute this statement	0.000 sec
* How to disable --skip-grant-tables	