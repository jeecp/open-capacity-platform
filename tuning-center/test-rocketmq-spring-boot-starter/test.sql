# Host: 59.110.164.254  (Version 5.7.22)
# Generator: MySQL-Front 5.4  (Build 4.153) - http://www.mysqlfront.de/

/*!40101 SET NAMES utf8 */;

#
# Structure for table "business_data"
#

DROP TABLE IF EXISTS `business_data`;
CREATE TABLE `business_data` (
  `id` varchar(45) NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "business_data"
#

INSERT INTO `business_data` VALUES ('46390b57-faa6-422e-99c7-b696e3625a95','gitgeek','1'),('47ba4495-8792-4a81-9fd7-f37c2efccbd1','gitgeek','1');

#
# Structure for table "mq_trancation_business_log"
#

DROP TABLE IF EXISTS `mq_trancation_business_log`;
CREATE TABLE `mq_trancation_business_log` (
  `id` varchar(45) NOT NULL,
  `transaction_id` varchar(45) NOT NULL COMMENT '事物ID',
  `description` varchar(45) NOT NULL COMMENT '日志描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "mq_trancation_business_log"
#

INSERT INTO `mq_trancation_business_log` VALUES ('46390b57-faa6-422e-99c7-b696e3625a95','d8eafa3a-914d-4c9e-af22-28094845fa7a','测试'),('47ba4495-8792-4a81-9fd7-f37c2efccbd1','5c551846-1124-4f68-83b7-3229bfb52df4','测试');
