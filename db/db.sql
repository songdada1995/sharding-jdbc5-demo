/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.7.44 : Database - demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`demo` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `demo`;

/*Table structure for table `t_report_202401` */

DROP TABLE IF EXISTS `t_report_202401`;

CREATE TABLE `t_report_202401` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `in_account_period` varchar(7) NOT NULL COMMENT '入账期间',
  `qty` int(11) DEFAULT NULL COMMENT '数量',
  `sku_code` varchar(150) DEFAULT NULL COMMENT 'SKU',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_report_202412` */

DROP TABLE IF EXISTS `t_report_202412`;

CREATE TABLE `t_report_202412` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `in_account_period` varchar(7) NOT NULL COMMENT '入账期间',
  `qty` int(11) DEFAULT NULL COMMENT '数量',
  `sku_code` varchar(150) DEFAULT NULL COMMENT 'SKU',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
