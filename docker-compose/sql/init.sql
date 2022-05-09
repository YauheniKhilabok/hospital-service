CREATE DATABASE IF NOT EXISTS hospitaldb;
USE hospitaldb;

CREATE USER 'hospital'@'localhost';
GRANT ALL ON *.* TO 'hospital'@'localhost';
ALTER USER 'hospital'@'localhost' IDENTIFIED  WITH mysql_native_password BY 'hospital';