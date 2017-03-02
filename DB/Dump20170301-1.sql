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
-- Table structure for table `COLLEGE`
--

DROP TABLE IF EXISTS `COLLEGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COLLEGE` (
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
  CONSTRAINT `fk_COLLEGE_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COLLEGE`
--

LOCK TABLES `COLLEGE` WRITE;
/*!40000 ALTER TABLE `COLLEGE` DISABLE KEYS */;
INSERT INTO `COLLEGE` VALUES (1,'MS University',2012,2016,'Y','MSU','B.Tech CSE','Grad',1),(2,'GECG',2012,2015,'Y','Govt college','BE CE','Grad',2),(3,'DDIT',2011,2015,'Y','DDU','B.Tech IT','Grad',3),(4,'DDIT',2011,2015,'Y','DDU','B.Tech EC','Grad',4),(5,'DDIT',2011,2015,'Y','DDU','B.Tech CE','Grad',5),(6,'GPG',2009,2012,'Y','Govt Polytechnic','DE CE','Diploma',2);
/*!40000 ALTER TABLE `COLLEGE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EVENT`
--

DROP TABLE IF EXISTS `EVENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EVENT` (
  `event_pk` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `event_name` varchar(45) DEFAULT NULL,
  `event_location` varchar(45) DEFAULT NULL,
  `event_start_date` date DEFAULT NULL,
  `event_start_time` time DEFAULT NULL,
  `event_end_date` date DEFAULT NULL,
  `event_end_time` time DEFAULT NULL,
  `event_description` varchar(500) DEFAULT NULL,
  `event_photo` varchar(150) DEFAULT NULL,
  `userdetails_pk` int(11) DEFAULT NULL,
  PRIMARY KEY (`event_pk`),
  KEY `fk_EVENT_1_idx` (`userdetails_pk`),
  CONSTRAINT `fk_EVENT_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EVENT`
--

LOCK TABLES `EVENT` WRITE;
/*!40000 ALTER TABLE `EVENT` DISABLE KEYS */;
INSERT INTO `EVENT` VALUES (1,'abc','jkdhckj','0000-00-00','00:00:00','0000-00-00','00:00:00','','',1),(2,'abc','jkdhckj','0000-00-00','00:00:00','0000-00-00','00:00:00','','',1);
/*!40000 ALTER TABLE `EVENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FRIENDLIST`
--

DROP TABLE IF EXISTS `FRIENDLIST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FRIENDLIST` (
  `friendlist_pk` int(11) NOT NULL AUTO_INCREMENT,
  `friendlist_user` int(11) NOT NULL,
  `friendlist_friend` int(11) NOT NULL,
  `friendlist_status` varchar(15) DEFAULT 'pending',
  `friendlist_timestamp_request_accepted` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`friendlist_pk`),
  KEY `fk_FRIENDLIST_1_idx` (`friendlist_user`),
  KEY `fk_FRIENDLIST_2_idx` (`friendlist_friend`),
  CONSTRAINT `fk_FRIENDLIST_FRIEND` FOREIGN KEY (`friendlist_friend`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_FRIENDLIST_USER` FOREIGN KEY (`friendlist_user`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FRIENDLIST`
--

LOCK TABLES `FRIENDLIST` WRITE;
/*!40000 ALTER TABLE `FRIENDLIST` DISABLE KEYS */;
INSERT INTO `FRIENDLIST` VALUES (10,6,4,'pending','2017-02-15 17:43:36'),(11,6,5,'pending','2017-02-15 18:47:43'),(12,6,7,'accepted','2017-02-28 07:48:00'),(14,1,2,'accepted','2017-03-01 07:51:47'),(15,6,1,'accepted','2017-03-01 10:50:09');
/*!40000 ALTER TABLE `FRIENDLIST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PLACES`
--

DROP TABLE IF EXISTS `PLACES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PLACES` (
  `places_pk` int(11) NOT NULL AUTO_INCREMENT,
  `places_current_city` varchar(45) DEFAULT NULL,
  `places_hometown` varchar(45) DEFAULT NULL,
  `userdetails_pk` int(11) NOT NULL,
  PRIMARY KEY (`places_pk`),
  KEY `fk_PLACES_1_idx` (`userdetails_pk`),
  CONSTRAINT `fk_PLACES_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PLACES`
--

LOCK TABLES `PLACES` WRITE;
/*!40000 ALTER TABLE `PLACES` DISABLE KEYS */;
/*!40000 ALTER TABLE `PLACES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POST`
--

DROP TABLE IF EXISTS `POST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POST` (
  `post_pk` int(11) NOT NULL AUTO_INCREMENT,
  `post_statusText` varchar(5000) DEFAULT NULL,
  `post_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `post_senderId` int(11) NOT NULL,
  `post_receiverId` int(11) NOT NULL,
  PRIMARY KEY (`post_pk`),
  UNIQUE KEY `post_pk_UNIQUE` (`post_pk`),
  KEY `fk_post_1_idx` (`post_senderId`,`post_receiverId`),
  KEY `fk_post_2_idx` (`post_receiverId`),
  CONSTRAINT `fk_post_1` FOREIGN KEY (`post_senderId`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_post_2` FOREIGN KEY (`post_receiverId`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POST`
--

LOCK TABLES `POST` WRITE;
/*!40000 ALTER TABLE `POST` DISABLE KEYS */;
INSERT INTO `POST` VALUES (1,'Hello World How Are You Doing Today?','2017-02-27 11:43:55',1,1),(2,'This is second post!','2017-02-27 14:05:32',2,2),(12,'Demo post','2017-03-01 07:43:19',6,1),(13,'Post 1 to 6','2017-03-01 09:16:55',1,6),(14,'Post 5 to 6','2017-03-01 09:17:25',5,6),(15,'Post 3 to 4','2017-03-01 09:18:37',3,4),(16,'I feel like flying high...!!!!','2017-03-01 10:04:53',1,1),(17,'fhfhf','2017-03-01 10:10:25',1,1),(18,'gdfbv','2017-03-01 10:14:14',1,1),(19,'rdbfcbvc','2017-03-01 10:17:33',1,1),(20,'qqewqewds','2017-03-01 10:18:10',1,1),(21,'dsvsdvdvds','2017-03-01 10:20:03',1,1),(22,'vcxvxcvxd','2017-03-01 10:20:51',1,1),(23,'ccvg','2017-03-01 10:21:52',1,1);
/*!40000 ALTER TABLE `POST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SCHOOL`
--

DROP TABLE IF EXISTS `SCHOOL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCHOOL` (
  `school_pk` int(11) NOT NULL AUTO_INCREMENT,
  `school_name` varchar(50) DEFAULT NULL,
  `school_start_year` int(11) DEFAULT NULL,
  `school_end_year` int(11) DEFAULT NULL,
  `userdetails_pk` int(11) NOT NULL,
  `school_description` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`school_pk`),
  KEY `fk_SCHOOL_1_idx` (`userdetails_pk`),
  CONSTRAINT `fk_SCHOOL_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SCHOOL`
--

LOCK TABLES `SCHOOL` WRITE;
/*!40000 ALTER TABLE `SCHOOL` DISABLE KEYS */;
/*!40000 ALTER TABLE `SCHOOL` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `USERDETAILS`
--

LOCK TABLES `USERDETAILS` WRITE;
/*!40000 ALTER TABLE `USERDETAILS` DISABLE KEYS */;
INSERT INTO `USERDETAILS` VALUES (1,'1','GUNJAN','ACHARYA','23456489','ABS@GMAIL.COM','ASD','m','1995-05-23','gunjan','kgvjjbk',2),(2,'2','SHACHI','BHAVSAR','9687534498','shachi@gmail.com','shachi@123','f','1994-02-12','shachi','hddhd',1),(3,'3','PRIYESH','DOSHI','9898989898','doshi@gmail.com','doshi@123','m','1994-01-01','default','ui',0),(4,'4','RAJ','PARIKH','9696969696','raju@gmail.com','raju@123','m','1993-11-02','default','djjd',0),(5,'5','AVANI','THAKKER','9797979797','avani@gmail.com','avani@123','f','1993-09-07','default','djdjd',0),(6,'6','Meet','Patel','9595959559','meet@gmail.com','123','m','1995-05-08','meet','default',2),(7,'7','Sumedh','Gupte','9876543211',NULL,'abc','M','1990-04-04','sumedh','sdc',1);
/*!40000 ALTER TABLE `USERDETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WORK`
--

DROP TABLE IF EXISTS `WORK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WORK` (
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
  CONSTRAINT `fk_WORK_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WORK`
--

LOCK TABLES `WORK` WRITE;
/*!40000 ALTER TABLE `WORK` DISABLE KEYS */;
INSERT INTO `WORK` VALUES (1,'xyz','manager','ahmedabad','jsnckjdnckjsnckjsdn',2011,2012,1),(2,'infy','manager','ahmedabad','jsnckjdnckjsnckjsdn',2015,2017,1);
/*!40000 ALTER TABLE `WORK` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-01 17:39:53
