-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: facebook_v01
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `college`
--

DROP TABLE IF EXISTS `college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `college` (
  `college_pk` int(11) NOT NULL AUTO_INCREMENT,
  `college_name` varchar(45) DEFAULT NULL,
  `college_start_year` int(11) DEFAULT NULL,
  `college_end_year` int(11) DEFAULT NULL,
  `college_graduated` char(1) DEFAULT NULL,
  `college_description` varchar(250) DEFAULT NULL,
  `college_concentration` varchar(250) DEFAULT NULL,
  `college_attended_for` varchar(45) DEFAULT NULL,
  `userdetails_pk` int(11) NOT NULL,
  PRIMARY KEY (`college_pk`),
  KEY `fk_COLLEGE_1_idx` (`userdetails_pk`),
  CONSTRAINT `fk_COLLEGE_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `userdetails` (`userdetails_pk`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `college`
--

LOCK TABLES `college` WRITE;
/*!40000 ALTER TABLE `college` DISABLE KEYS */;
INSERT INTO `college` VALUES (1,'MS University',2012,2016,'Y','MSU','B.Tech CSE','Grad',1),(2,'GECG',2012,2015,'Y','Govt college','BE CE','Grad',2),(3,'DDIT',2011,2015,'Y','DDU','B.Tech IT','Grad',3),(4,'DDIT',2011,2015,'Y','DDU','B.Tech EC','Grad',4),(5,'DDIT',2011,2015,'Y','DDU','B.Tech CE','Grad',5),(6,'GPG',2009,2012,'Y','Govt Polytechnic','DE CE','Diploma',2);
/*!40000 ALTER TABLE `college` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friendlist`
--

DROP TABLE IF EXISTS `friendlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friendlist` (
  `friendlist_pk` int(11) NOT NULL AUTO_INCREMENT,
  `friendlist_user` int(11) NOT NULL,
  `friendlist_friend` int(11) NOT NULL,
  `friendlist_status` varchar(15) DEFAULT 'pending',
  `friendlist_timestamp_request_accepted` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`friendlist_pk`),
  KEY `fk_FRIENDLIST_1_idx` (`friendlist_user`),
  KEY `fk_FRIENDLIST_2_idx` (`friendlist_friend`),
  CONSTRAINT `fk_FRIENDLIST_FRIEND` FOREIGN KEY (`friendlist_friend`) REFERENCES `userdetails` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_FRIENDLIST_USER` FOREIGN KEY (`friendlist_user`) REFERENCES `userdetails` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friendlist`
--

LOCK TABLES `friendlist` WRITE;
/*!40000 ALTER TABLE `friendlist` DISABLE KEYS */;
INSERT INTO `friendlist` VALUES (7,1,6,'accepted','2017-02-15 17:28:53'),(8,6,2,'accepted','2017-02-15 17:30:16'),(9,6,3,'accepted','2017-02-15 17:45:20'),(10,6,4,'pending','2017-02-15 17:43:36'),(11,6,5,'pending','2017-02-15 18:47:43');
/*!40000 ALTER TABLE `friendlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `places`
--

DROP TABLE IF EXISTS `places`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `places` (
  `places_pk` int(11) NOT NULL AUTO_INCREMENT,
  `places_current_city` varchar(45) DEFAULT NULL,
  `places_hometown` varchar(45) DEFAULT NULL,
  `userdetails_pk` int(11) NOT NULL,
  PRIMARY KEY (`places_pk`),
  KEY `fk_PLACES_1_idx` (`userdetails_pk`),
  CONSTRAINT `fk_PLACES_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `userdetails` (`userdetails_pk`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `places`
--

LOCK TABLES `places` WRITE;
/*!40000 ALTER TABLE `places` DISABLE KEYS */;
/*!40000 ALTER TABLE `places` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school`
--

DROP TABLE IF EXISTS `school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `school` (
  `school_pk` int(11) NOT NULL AUTO_INCREMENT,
  `school_name` varchar(50) DEFAULT NULL,
  `school_start_year` int(11) DEFAULT NULL,
  `school_end_year` int(11) DEFAULT NULL,
  `userdetails_pk` int(11) NOT NULL,
  `school_description` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`school_pk`),
  KEY `fk_SCHOOL_1_idx` (`userdetails_pk`),
  CONSTRAINT `fk_SCHOOL_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `userdetails` (`userdetails_pk`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school`
--

LOCK TABLES `school` WRITE;
/*!40000 ALTER TABLE `school` DISABLE KEYS */;
/*!40000 ALTER TABLE `school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userdetails`
--

DROP TABLE IF EXISTS `userdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userdetails` (
  `userdetails_pk` int(11) NOT NULL AUTO_INCREMENT,
  `userdetails_username` varchar(50) NOT NULL,
  `userdetails_firstname` varchar(50) NOT NULL,
  `userdetails_lastname` varchar(50) NOT NULL,
  `userdetails_mobile` varchar(50) DEFAULT NULL,
  `userdetails_email` varchar(50) DEFAULT NULL,
  `userdetails_password` varchar(50) NOT NULL,
  `userdetails_gender` char(1) NOT NULL,
  `userdetails_dob` date NOT NULL,
  `userdetails_picurl` varchar(100) NOT NULL,
  `userdetails_smallpicurl` varchar(100) NOT NULL,
  `userdetails_friend_count` int(11) DEFAULT '0',
  PRIMARY KEY (`userdetails_pk`),
  UNIQUE KEY `username_UNIQUE` (`userdetails_username`),
  UNIQUE KEY `userdetails_mobile_UNIQUE` (`userdetails_mobile`),
  UNIQUE KEY `userdetails_email_UNIQUE` (`userdetails_email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userdetails`
--

LOCK TABLES `userdetails` WRITE;
/*!40000 ALTER TABLE `userdetails` DISABLE KEYS */;
INSERT INTO `userdetails` VALUES (1,'1','GUNJAN','ACHARYA','23456489','ABS@GMAIL.COM','ASD','m','0000-00-00','jkhjhl','kgvjjbk',1),(2,'2','SHACHI','BHAVSAR','9687534498','shachi@gmail.com','shachi@123','f','1994-02-12','ddhh','hddhd',1),(3,'3','PRIYESH','DOSHI','9898989898','doshi@gmail.com','doshi@123','m','1994-01-01','uiiu','ui',1),(4,'4','RAJ','PARIKH','9696969696','raju@gmail.com','raju@123','m','1993-11-02','djjddj','djjd',0),(5,'5','AVANI','THAKKER','9797979797','avani@gmail.com','avani@123','f','1993-09-07','jfjffj','djdjd',0),(6,'6','Meet','Patel','9595959559','meet@gmail.com','123','m','1995-05-08','defsult','default',3),(7,'7','Sumedh','Gupte','9876543211',NULL,'abc','M','1990-04-04','vdxv','sdc',0);
/*!40000 ALTER TABLE `userdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work`
--

DROP TABLE IF EXISTS `work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work` (
  `work_pk` int(11) NOT NULL AUTO_INCREMENT,
  `work_company` varchar(50) DEFAULT NULL,
  `work_position` varchar(50) DEFAULT NULL,
  `work_city` varchar(50) DEFAULT NULL,
  `work_description` varchar(200) DEFAULT NULL,
  `work_start_year` int(11) DEFAULT NULL,
  `work_end_year` int(11) DEFAULT NULL,
  `userdetails_pk` int(11) NOT NULL,
  PRIMARY KEY (`work_pk`),
  KEY `fk_WORK_1_idx` (`userdetails_pk`),
  CONSTRAINT `fk_WORK_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `userdetails` (`userdetails_pk`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work`
--

LOCK TABLES `work` WRITE;
/*!40000 ALTER TABLE `work` DISABLE KEYS */;
INSERT INTO `work` VALUES (1,'xyz','manager','ahmedabad','jsnckjdnckjsnckjsdn',2011,2012,1),(2,'infy','manager','ahmedabad','jsnckjdnckjsnckjsdn',2015,2017,1);
/*!40000 ALTER TABLE `work` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-26 21:24:29
