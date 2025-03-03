```bash
sudo su
apt update
apt install mysql-server -y
sudo systemctl start mysql
sudo systemctl enable mysql
vi /etc/mysql/mysql.conf.d/mysqld.cnf

Change the bind address  bind-address = 0.0.0.0

sudo systemctl restart mysql

sudo mysql -u root -p
password : root
SHOW DATABASES
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
**Update these changes into application.properties**
**Run the Load test with 250 users with 100ms constant timer**

http://localhost:8081/transactions
