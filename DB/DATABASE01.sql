CREATE DATABASE  IF NOT EXISTS `facebook_v01` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `facebook_v01`;
-- MySQL dump 10.13  Distrib 5.5.54, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: facebook_v01
-- ------------------------------------------------------
-- Server version	5.5.54-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `USERDETAILS`
--

DROP TABLE IF EXISTS `USERDETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERDETAILS` (
  `userdetails_pk` int(11) NOT NULL AUTO_INCREMENT,
  `userdetails_username` varchar(50) NOT NULL,
  `userdetails_firstname` varchar(50) NOT NULL,
  `userdetails_lastname` varchar(50) NOT NULL,
  `userdetails_mobile` varchar(50) DEFAULT NULL,
  `userdetails_email` varchar(50) DEFAULT NULL,
  `userdetails_password` varchar(50) NOT NULL,
  `userdetails_gender` char(1) NOT NULL,
  `userdetails_dob` date NOT NULL,
  PRIMARY KEY (`userdetails_pk`),
  UNIQUE KEY `username_UNIQUE` (`userdetails_username`),
  UNIQUE KEY `userdetails_mobile_UNIQUE` (`userdetails_mobile`),
  UNIQUE KEY `userdetails_email_UNIQUE` (`userdetails_email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERDETAILS`
--

LOCK TABLES `USERDETAILS` WRITE;
/*!40000 ALTER TABLE `USERDETAILS` DISABLE KEYS */;
INSERT INTO `USERDETAILS` VALUES (1,'1','GUNJAN','ACHARYA','23456489','ABS@GMAIL.COM','ASD','m','0000-00-00');
/*!40000 ALTER TABLE `USERDETAILS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-31 13:11:16
