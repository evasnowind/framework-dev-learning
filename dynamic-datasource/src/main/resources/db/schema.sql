DROP DATABASE IF EXISTS product_master;
CREATE DATABASE product_master;
CREATE TABLE product_master.product(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  price DOUBLE(10,2) NOT NULL DEFAULT 0
);
INSERT INTO product_master.product (name, price) VALUES('master', '1');


DROP DATABASE IF EXISTS product_slave_0;
CREATE DATABASE product_slave_0;
CREATE TABLE product_slave_0.product(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  price DOUBLE(10,2) NOT NULL DEFAULT 0
);
INSERT INTO product_slave_0.product (name, price) VALUES('slave0', '1');

DROP DATABASE IF EXISTS product_slave_1;
CREATE DATABASE product_slave_1;
CREATE TABLE product_slave_1.product(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  price DOUBLE(10,2) NOT NULL DEFAULT 0
);
INSERT INTO product_slave_1.product (name, price) VALUES('slave1', '1');
