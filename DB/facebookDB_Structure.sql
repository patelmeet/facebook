-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: facebook_v01
-- ------------------------------------------------------
-- Server version	5.7.17-0ubuntu0.16.04.2

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
  CONSTRAINT `fk_COLLEGE_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMMENT`
--

DROP TABLE IF EXISTS `COMMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMMENT` (
  `comment_pk` int(11) NOT NULL AUTO_INCREMENT,
  `comment_text` varchar(5000) NOT NULL,
  `comment_created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `comment_updated` timestamp NULL DEFAULT NULL,
  `comment_by` int(11) NOT NULL,
  `comment_post_id` int(11) NOT NULL,
  `comment_like_count` int(11) DEFAULT '0',
  PRIMARY KEY (`comment_pk`),
  UNIQUE KEY `comments_pk_UNIQUE` (`comment_pk`),
  KEY `fk_comment_by_idx` (`comment_by`),
  KEY `fk_comment_post_id_idx` (`comment_post_id`),
  CONSTRAINT `fk_comment_by` FOREIGN KEY (`comment_by`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_post_id` FOREIGN KEY (`comment_post_id`) REFERENCES `POST` (`post_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMMENT_LIKE`
--

DROP TABLE IF EXISTS `COMMENT_LIKE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMMENT_LIKE` (
  `comment_like_pk` int(11) NOT NULL AUTO_INCREMENT,
  `comment_like_user_id` int(11) NOT NULL,
  `comment_like_comment_id` int(11) NOT NULL,
  `comment_like_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_like_pk`),
  KEY `fk_COMMENT_LIKE_1_idx` (`comment_like_user_id`),
  KEY `fk_COMMENT_LIKE_2_idx` (`comment_like_comment_id`),
  CONSTRAINT `fk_COMMENT_LIKE_1` FOREIGN KEY (`comment_like_user_id`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_COMMENT_LIKE_2` FOREIGN KEY (`comment_like_comment_id`) REFERENCES `COMMENT` (`comment_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CONTACT_AND_BASIC_INFO`
--

DROP TABLE IF EXISTS `CONTACT_AND_BASIC_INFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CONTACT_AND_BASIC_INFO` (
  `userdetails_pk` int(11) NOT NULL,
  `contact_and_basic_info_religious_view` varchar(500) DEFAULT NULL,
  `contact_and_basic_info_interested_in` char(1) DEFAULT NULL,
  `contact_and_basic_info_user_address` varchar(200) DEFAULT NULL,
  `religions_religion_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userdetails_pk`),
  CONSTRAINT `fk_CONTACT_AND_BASIC_INFO_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `EVENT`
--

DROP TABLE IF EXISTS `EVENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EVENT` (
  `event_pk` int(11) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(45) DEFAULT NULL,
  `event_location` varchar(45) DEFAULT NULL,
  `event_start_date` date DEFAULT NULL,
  `event_start_time` time DEFAULT NULL,
  `event_end_date` date DEFAULT NULL,
  `event_end_time` time DEFAULT NULL,
  `event_description` varchar(500) DEFAULT NULL,
  `event_photo` varchar(150) DEFAULT NULL,
  `userdetails_pk` int(11) DEFAULT NULL,
  `event_going` int(11) DEFAULT '0',
  `event_maybe` int(11) DEFAULT '0',
  `event_cant_go` int(11) DEFAULT '0',
  PRIMARY KEY (`event_pk`),
  KEY `fk_EVENT_1_idx` (`userdetails_pk`),
  CONSTRAINT `fk_EVENT_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `EVENTLIST`
--

DROP TABLE IF EXISTS `EVENTLIST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EVENTLIST` (
  `eventlist_pk` int(11) NOT NULL AUTO_INCREMENT,
  `eventlist_user` int(11) NOT NULL,
  `eventlist_friend` int(11) DEFAULT NULL,
  `eventlist_status` varchar(45) DEFAULT NULL,
  `event_pk` int(11) NOT NULL,
  PRIMARY KEY (`eventlist_pk`),
  KEY `fk_EVENTLIST_1_idx` (`eventlist_user`),
  KEY `fk_EVENTLIST_2_idx` (`eventlist_friend`),
  KEY `fk_EVENTLIST_1_idx1` (`event_pk`),
  CONSTRAINT `fk_EVENTLIST_3` FOREIGN KEY (`eventlist_friend`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_EVENTLIST_EVENT` FOREIGN KEY (`event_pk`) REFERENCES `EVENT` (`event_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_EVENTLIST_USER` FOREIGN KEY (`eventlist_user`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `GROUPDETAILS`
--

DROP TABLE IF EXISTS `GROUPDETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GROUPDETAILS` (
  `groupdetails_id` int(11) NOT NULL AUTO_INCREMENT,
  `groupdetails_name` varchar(45) NOT NULL,
  `groupdetails_createdby` int(11) NOT NULL,
  `groupdetails_privacy` int(11) NOT NULL DEFAULT '0',
  `groupdetails_membercount` int(11) DEFAULT '1',
  `groupdetails_datecreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `groupdetails_description` varchar(150) DEFAULT NULL,
  `groupdetails_web_email` varchar(45) DEFAULT NULL,
  `groupdetails_imageurl` varchar(100) DEFAULT 'groupdefault.png',
  PRIMARY KEY (`groupdetails_id`),
  UNIQUE KEY `groupdetails_groupname_UNIQUE` (`groupdetails_name`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `GROUPMEMBERS`
--

DROP TABLE IF EXISTS `GROUPMEMBERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GROUPMEMBERS` (
  `groupmembers_groupid` int(11) NOT NULL,
  `groupmembers_memberid` int(11) NOT NULL,
  `groupmembers_role` int(11) NOT NULL DEFAULT '2',
  `groupmembers_status` int(11) NOT NULL,
  PRIMARY KEY (`groupmembers_groupid`,`groupmembers_memberid`),
  KEY `fk_GROUPMEMBERS_1_idx` (`groupmembers_memberid`),
  KEY `fk_GROUPMEMBERS_2_idx` (`groupmembers_groupid`),
  CONSTRAINT `fk_GROUPMEMBERS_2` FOREIGN KEY (`groupmembers_groupid`) REFERENCES `GROUPDETAILS` (`groupdetails_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MESSAGES`
--

DROP TABLE IF EXISTS `MESSAGES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MESSAGES` (
  `messages_pk` int(11) NOT NULL AUTO_INCREMENT,
  `messages_sender_id` int(11) NOT NULL,
  `messages_receiver_id` int(11) NOT NULL,
  `messages_message` varchar(1000) DEFAULT NULL,
  `messages_time_stamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`messages_pk`),
  KEY `fk_MESSAGES_1_idx` (`messages_sender_id`),
  KEY `fk_MESSAGES_2_idx` (`messages_receiver_id`),
  CONSTRAINT `fk_MESSAGES_1` FOREIGN KEY (`messages_sender_id`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_MESSAGES_2` FOREIGN KEY (`messages_receiver_id`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  CONSTRAINT `fk_PLACES_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `post_groupId` int(11) DEFAULT NULL,
  `post_like_count` int(11) DEFAULT '0',
  `post_img_url` varchar(300) DEFAULT 'null',
  `post_eventId` int(11) DEFAULT NULL,
  PRIMARY KEY (`post_pk`),
  UNIQUE KEY `post_pk_UNIQUE` (`post_pk`),
  KEY `fk_post_1_idx` (`post_senderId`,`post_receiverId`),
  KEY `fk_post_2_idx` (`post_receiverId`),
  KEY `fk_post_3_idx` (`post_groupId`),
  KEY `fk_post_4_idx` (`post_eventId`),
  CONSTRAINT `fk_post_1` FOREIGN KEY (`post_senderId`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_post_2` FOREIGN KEY (`post_receiverId`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_post_3` FOREIGN KEY (`post_groupId`) REFERENCES `GROUPDETAILS` (`groupdetails_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_4` FOREIGN KEY (`post_eventId`) REFERENCES `EVENT` (`event_pk`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `POST_LIKE`
--

DROP TABLE IF EXISTS `POST_LIKE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POST_LIKE` (
  `post_like_pk` int(11) NOT NULL AUTO_INCREMENT COMMENT '	',
  `post_like_user_id` int(11) NOT NULL COMMENT '	',
  `post_like_post_pk` int(11) NOT NULL,
  `post_like_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`post_like_pk`),
  UNIQUE KEY `post_like_pk_UNIQUE` (`post_like_pk`),
  KEY `fk_post_like_post_pk_idx` (`post_like_post_pk`),
  KEY `fk_post_like_user_id_idx` (`post_like_user_id`),
  CONSTRAINT `fk_post_like_post_pk` FOREIGN KEY (`post_like_post_pk`) REFERENCES `POST` (`post_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_post_like_user_id` FOREIGN KEY (`post_like_user_id`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  CONSTRAINT `fk_SCHOOL_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SUGGESTION`
--

DROP TABLE IF EXISTS `SUGGESTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SUGGESTION` (
  `suggestion_pk` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `suggestion_suggested_by` int(11) NOT NULL,
  `suggestion_suggested_to` int(11) NOT NULL,
  `suggestion_suggested_whom` int(11) NOT NULL,
  `suggestion_tag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`suggestion_pk`),
  KEY `fk_suggested_by_idx` (`suggestion_suggested_by`),
  KEY `fk_suggested_to_idx` (`suggestion_suggested_to`),
  KEY `fk_suggested_whom_idx` (`suggestion_suggested_whom`),
  CONSTRAINT `fk_suggested_by` FOREIGN KEY (`suggestion_suggested_by`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_suggested_to` FOREIGN KEY (`suggestion_suggested_to`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_suggested_whom` FOREIGN KEY (`suggestion_suggested_whom`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `userdetails_coverpicurl` varchar(100) NOT NULL,
  `userdetails_friend_count` int(11) DEFAULT '0',
  `userdetails_book` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userdetails_pk`),
  UNIQUE KEY `username_UNIQUE` (`userdetails_username`),
  UNIQUE KEY `userdetails_mobile_UNIQUE` (`userdetails_mobile`),
  UNIQUE KEY `userdetails_email_UNIQUE` (`userdetails_email`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  CONSTRAINT `fk_WORK_1` FOREIGN KEY (`userdetails_pk`) REFERENCES `USERDETAILS` (`userdetails_pk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-28 11:04:23
