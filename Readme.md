```bash
sudo su
apt update
apt install mysql-server -y
sudo systemctl start mysql
sudo systemctl enable mysql
vi /etc/mysql/mysql.conf.d/mysqld.cnf

Change the bind address  bind-address = 0.0.0.0

sudo systemctl restart mysql

mysql -u root
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin@123';
CREATE USER 'root'@'%' IDENTIFIED BY 'admin@123';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
SHOW DATABASES;
CREATE DATABASE realbanking;
CREATE USER 'realbank_user'@'%' IDENTIFIED BY 'Password123';
GRANT ALL PRIVILEGES ON realbanking.* TO 'realbank_user'@'%';
FLUSH PRIVILEGES;
EXIT;
sudo systemctl restart mysql

SELECT user, host FROM mysql.user;
USE realbanking;
SHOW GRANTS FOR 'realbank_user'@'%';

sudo ufw status
sudo ufw allow 3306/tcp
sudo ufw reload
```
**Configure the Database External IP Address**
```bash
HostName: <External VM IP>

Database : realbanking

username: realbank_user

password: Password123
```
```bash
mysql -h <ubuntu_server_ip> -u realbank_user -p
```
**Update these changes into application.properties**
**Run the Load test with 250 users with 100ms constant timer**

http://localhost:8081/transactions

# Validation from App Server
```bash
sudo apt update
sudo apt install mysql-client -y
mysql -h 35.186.176.58 -P 3306 -u root -p
```

**Setup MySQL plugin in Dynatrace Hub**
<img width="711" alt="image" src="https://github.com/user-attachments/assets/421bfc80-75a3-4765-b9a7-2a55e0ec8585" />

Once installed , Create Dynatrace user and provide neccessary permission

Creating a MySQL user
```bash
CREATE USER 'dynatrace'@'%' IDENTIFIED WITH mysql_native_password BY 'password';
```
Give the user the permissions:
```bash
GRANT SELECT ON performance_schema.* TO 'dynatrace'@'%';
```
Allows the user to query the performance_schema schema
```bash
GRANT PROCESS ON *.* TO 'dynatrace'@'%';
```
Allows the user to see thread and connection metrics for other users
```bash
GRANT SHOW DATABASES ON *.* TO 'dynatrace'@'%';
```
Allows the user to see database metrics for all databases
```bash
GRANT SELECT ON mysql.slow_log TO 'dynatrace'@'%';
```
Allows the user to query slow queries
```bash
GRANT SELECT ON sys.x$memory_global_by_current_bytes TO 'dynatrace'@'%';
```
Allow the user to query memory statistics
NOTE: Due to a MySQL limitation, to calculate database sizes you MUST grant SELECT permissions on the individual databases where you want to collect size from.
Collecting Infrastructure metrics
To enable CPU metrics collection, run this query on the MySQL instance:
```bash
SET GLOBAL innodb_monitor_enable='cpu%';
```
Collecting Top Slow Queries
Enable slow queries logging to a table:
```bash
SET GLOBAL log_output = 'TABLE';
SET GLOBAL slow_query_log = 'ON';
```
The default slow query threshold is 10 seconds You can chose the threshold of what is a "slow query" by executing:
```bash
SET GLOBAL long_query_time = 2;
```
This would set slow queries threshold to 2 seconds.
Execution Plan Fetching
To fetch execution plans, you must create a stored procedure for the dynatrace user:
```bash
CREATE SCHEMA IF NOT EXISTS dynatrace;
```
```bash
DELIMITER $$
```
```bash
CREATE PROCEDURE dynatrace.dynatrace_execution_plan(IN query TEXT)
    SQL SECURITY DEFINER
BEGIN
    SET @explain := CONCAT('EXPLAIN FORMAT=JSON ', query);
    PREPARE stmt FROM @explain;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END $$
```
```bash
DELIMITER ;
```
And then grant execution permission for the dynatrace user
```bash
GRANT EXECUTE ON PROCEDURE dynatrace.dynatrace_execution_plan TO 'dynatrace'@'%';
```

**Click Configure**
<img width="710" alt="image" src="https://github.com/user-attachments/assets/6535c68e-df6b-4e5f-a26f-6f859f5efb02" />
```bash
Database connection
Host: 34.21.14.215
Port :3306
Database name :mysql
Authentication
Username:dynatrace
password:password
```
<img width="655" alt="image" src="https://github.com/user-attachments/assets/eb5978fe-72fb-47d0-afcc-3dde9cccdb04" />

Click Save

Thats it !!
<img width="718" alt="image" src="https://github.com/user-attachments/assets/a3bfaf55-1fef-434a-b536-8bbecee471c0" />

