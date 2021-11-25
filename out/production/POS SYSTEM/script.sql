DROP DATABASE IF EXISTS JSuperMarket;
CREATE DATABASE IF NOT EXISTS JSuperMarket;
SHOW DATABASES;
USE JSuperMarket;

__________________________

DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    ItemCode VARCHAR(6),
    Discription VARCHAR(50),
    PackSize VARCHAR(20),
    UnitPrice  DECIMAL(6,2),
    QtyOnHand INT(5),
    CONSTRAINT PRIMARY KEY (ItemCode));
SHOW TABLES;
DESCRIBE Item;
ALTER TABLE Item
    ADD Discount INT(3);

_____________________________________________________

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    id VARCHAR(6),
    custTitle VARCHAR(5),
    name VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    address TEXT,
    city VARCHAR(20),
    province VARCHAR(20),
    postalCode VARCHAR(9),
    CONSTRAINT PRIMARY KEY (id)
    );
SHOW TABLES ;
DESCRIBE Customer;

_________________________________________________________

DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
    orderId VARCHAR(6),
    orderDate DATE,
    cId VARCHAR(15),
    CONSTRAINT PRIMARY KEY (orderId),
    CONSTRAINT FOREIGN KEY (cId) REFERENCES Customer(id) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Order`;

___________________________________________________________________________________

DROP TABLE IF EXISTS `Order Detail`;
CREATE TABLE IF NOT EXISTS `Order Detail`(
    itemCode VARCHAR(6),
    orderId VARCHAR(6),
    orderQTY INT(11),
    discount DECIMAL(6,2),
    CONSTRAINT PRIMARY KEY (itemCode, orderId),
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES Item(ItemCode) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES `Order`(orderId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Order Detail`;

____________________________________________________________________________

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User`(
                       User_Name VARCHAR (10),
                       `Name` VARCHAR (50) ,
                       Address VARCHAR (50),
                       Tele_No VARCHAR (10),
                       `Role` VARCHAR (8),
                       User_Password VARCHAR(100),
                       CONSTRAINT PRIMARY KEY (User_Name)
);
SHOW TABLES ;
DESCRIBE `User`;
________________________________________________________________________________
