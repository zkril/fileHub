/*
SQLyog Community v13.2.0 (64 bit)
MySQL - 8.0.37 : Database - filehub
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`filehub` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `filehub`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `countid` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uname` varchar(10) NOT NULL COMMENT '账号名称',
  `account_password` varchar(20) DEFAULT NULL COMMENT '账号密码',
  PRIMARY KEY (`countid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

/*Data for the table `account` */

insert  into `account`(`countid`,`uname`,`account_password`) values 
(1,'姚单铭','1234'),
(2,'魏忠强','1234'),
(3,'张瑾涵','1234'),
(4,'耿乐乐','1234'),
(6,'姚单','1234');

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `courseid` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_name` varchar(20) NOT NULL COMMENT '课程名称',
  `accountid` int NOT NULL COMMENT '关联账号id',
  PRIMARY KEY (`courseid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;

/*Data for the table `course` */

insert  into `course`(`courseid`,`course_name`,`accountid`) values 
(1,'数据结构',1),
(2,'数据库',1),
(3,'计算机网络',1),
(4,'操作系统',2),
(5,'kk',1),
(10,'000',1),
(13,'91919',1);

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `messageid` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mid` varchar(10) NOT NULL COMMENT '平台显示信息id',
  `message_name` varchar(50) NOT NULL COMMENT '信息标题',
  `message_body` varchar(300) DEFAULT NULL COMMENT '信息主体',
  `accountid` int NOT NULL COMMENT '关联账号id',
  `courseid` int NOT NULL COMMENT '关联课程id',
  `ddl` varchar(50) DEFAULT NULL COMMENT '截止时间',
  PRIMARY KEY (`messageid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;

/*Data for the table `message` */

insert  into `message`(`messageid`,`mid`,`message_name`,`message_body`,`accountid`,`courseid`,`ddl`) values 
(2,'1002','第二条','bkk',2,4,NULL),
(3,'1003','第三条','ckk',1,2,'2024-05-02 17:26:58'),
(12,'4','4','4',1,2,'2024-05-02 20:01:32'),
(13,'6','6','6',1,2,'2024-05-02 20:01:32');

/*Table structure for table `messagefile` */

DROP TABLE IF EXISTS `messagefile`;

CREATE TABLE `messagefile` (
  `fileid` int NOT NULL AUTO_INCREMENT,
  `messageid` int NOT NULL,
  `oldname` varchar(100) DEFAULT NULL,
  `newname` varchar(200) DEFAULT NULL,
  `filesize` int DEFAULT NULL,
  `path` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`fileid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `messagefile` */

insert  into `messagefile`(`fileid`,`messageid`,`oldname`,`newname`,`filesize`,`path`) values 
(9,3,'3章 动态规划-参考解答.pdf','20240512142640d66c8c3e32b84b74a19d807afbba414f3章 动态规划-参考解答.pdf',156039,'/files/2024-05-12'),
(10,3,'第2章 递归与分治策略-参考解答.pdf','20240512142646012a43cf07a64f7ead4d8103f62258fa第2章 递归与分治策略-参考解答.pdf',168527,'/files/2024-05-12'),
(11,3,'第1章 算法概述-参考解答.pdf','202405121426500bcae71a67894896bc0cc0f9e670a3d2第1章 算法概述-参考解答.pdf',131711,'/files/2024-05-12'),
(12,2,'实验2  DBMS数据控制（安全性部分）-题目-update.doc','202405121606462c038233d2ef4ff68e8c297601bd4114实验2  DBMS数据控制（安全性部分）-题目-update.doc',37888,'/files/2024-05-12'),
(13,3,'实验2  DBMS数据控制（安全性部分）-题目-update.doc','20240512160707eebf7b44e192490c9e142ed2f0bbfb87实验2  DBMS数据控制（安全性部分）-题目-update.doc',37888,'/files/2024-05-12'),
(14,3,'3章+动态规划-参考解答 (7).pdf','202405121726289551e743e861484db86c6cf80b8cc67a3章+动态规划-参考解答 (7).pdf',156039,'/files/2024-05-12');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
