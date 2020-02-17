CREATE DATABASE IF NOT EXISTS `iyydb_job` DEFAULT CHARACTER SET = utf8mb4;
Use `iyydb_job`;

#
# Structure for table "XXL_JOB_QRTZ_CALENDARS"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_CALENDARS`;
CREATE TABLE `xxl_job_qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL,
  `calendar_name` varchar(200) NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_CALENDARS"
#


#
# Structure for table "XXL_JOB_QRTZ_FIRED_TRIGGERS"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `xxl_job_qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(200) DEFAULT NULL,
  `job_group` varchar(200) DEFAULT NULL,
  `is_nonconcurrent` varchar(1) DEFAULT NULL,
  `requests_recovery` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_FIRED_TRIGGERS"
#

INSERT INTO `XXL_JOB_QRTZ_FIRED_TRIGGERS` VALUES ('scheduler','owen15737065848981573706584877','5','1','owen1573706584898',1573706661535,1573706665000,5,'ACQUIRED',NULL,NULL,'0','0');

#
# Structure for table "XXL_JOB_QRTZ_JOB_DETAILS"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_JOB_DETAILS`;
CREATE TABLE `xxl_job_qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `job_class_name` varchar(250) NOT NULL,
  `is_durable` varchar(1) NOT NULL,
  `is_nonconcurrent` varchar(1) NOT NULL,
  `is_update_data` varchar(1) NOT NULL,
  `requests_recovery` varchar(1) NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_JOB_DETAILS"
#

INSERT INTO `XXL_JOB_QRTZ_JOB_DETAILS` VALUES ('quartzScheduler','3','1',NULL,'com.open.capacity.core.jobbean.RemoteHttpJobBean','0','0','0','0',X'ACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800'),('quartzScheduler','4','1',NULL,'com.open.capacity.core.jobbean.RemoteHttpJobBean','0','0','0','0',X'ACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800'),('scheduler','5','1',NULL,'com.xxl.job.admin.core.jobbean.RemoteHttpJobBean','0','0','0','0',X'ACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800'),('scheduler','6','1',NULL,'com.xxl.job.admin.core.jobbean.RemoteHttpJobBean','0','0','0','0',X'ACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800');

#
# Structure for table "XXL_JOB_QRTZ_LOCKS"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_LOCKS`;
CREATE TABLE `xxl_job_qrtz_locks` (
  `sched_name` varchar(120) NOT NULL,
  `lock_name` varchar(40) NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_LOCKS"
#

INSERT INTO `XXL_JOB_QRTZ_LOCKS` VALUES ('quartzScheduler','STATE_ACCESS'),('quartzScheduler','TRIGGER_ACCESS'),('scheduler','STATE_ACCESS'),('scheduler','TRIGGER_ACCESS'),('schedulerFactoryBean','STATE_ACCESS'),('schedulerFactoryBean','TRIGGER_ACCESS');

#
# Structure for table "XXL_JOB_QRTZ_PAUSED_TRIGGER_GRPS"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `xxl_job_qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_PAUSED_TRIGGER_GRPS"
#


#
# Structure for table "XXL_JOB_QRTZ_SCHEDULER_STATE"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_SCHEDULER_STATE`;
CREATE TABLE `xxl_job_qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_SCHEDULER_STATE"
#

INSERT INTO `XXL_JOB_QRTZ_SCHEDULER_STATE` VALUES ('quartzScheduler','USERCHI-E29PEDA1525876570951',1525876886761,5000),('scheduler','owen1573706584898',1573706656964,5000),('schedulerFactoryBean','owen1526306081109',1526306155345,5000);

#
# Structure for table "XXL_JOB_QRTZ_TRIGGER_GROUP"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_TRIGGER_GROUP`;
CREATE TABLE `xxl_job_qrtz_trigger_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) NOT NULL COMMENT '执行器名称',
  `order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '排序',
  `address_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` varchar(512) DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_TRIGGER_GROUP"
#

INSERT INTO `XXL_JOB_QRTZ_TRIGGER_GROUP` VALUES (1,'open-xxl-job-demo','示例执行器',1,0,'10.253.8.205:9999');

#
# Structure for table "XXL_JOB_QRTZ_TRIGGER_INFO"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_TRIGGER_INFO`;
CREATE TABLE `xxl_job_qrtz_trigger_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_fail_strategy` varchar(50) DEFAULT NULL COMMENT '失败处理策略',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` text COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_TRIGGER_INFO"
#

INSERT INTO `XXL_JOB_QRTZ_TRIGGER_INFO` VALUES (5,1,'*/5 * * * * ?','有参任务','2018-05-12 06:30:38','2018-05-12 06:30:38','merlin','624191343@qq.com','FIRST','demoJobHandler','demoJobHandler','SERIAL_EXECUTION','FAIL_ALARM','BEAN','','GLUE代码初始化','2018-05-12 06:30:38',NULL),(6,1,'*/5 * * * * ?','无参任务','2018-05-12 06:32:16','2018-05-12 06:32:16','merlin','624191343@qq.com','FIRST','demoJobHandler','','SERIAL_EXECUTION','FAIL_ALARM','BEAN','','GLUE代码初始化','2018-05-12 06:32:16',NULL);

#
# Structure for table "XXL_JOB_QRTZ_TRIGGER_LOG"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_TRIGGER_LOG`;
CREATE TABLE `xxl_job_qrtz_trigger_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `executor_address` varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` varchar(2048) DEFAULT NULL COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` varchar(2048) DEFAULT NULL COMMENT '执行-日志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_TRIGGER_LOG"
#

INSERT INTO `XXL_JOB_QRTZ_TRIGGER_LOG` VALUES (1,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:51:25',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:51:36',200,''),(2,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:51:30',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:51:46',200,''),(3,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:51:35',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:51:56',200,''),(4,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:51:40',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:52:06',200,''),(5,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:51:45',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:52:16',200,''),(6,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:51:50',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:52:26',200,''),(7,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:51:55',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:52:36',200,''),(8,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:00',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:52:46',200,''),(9,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:05',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:52:56',200,''),(10,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:10',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:53:06',200,''),(11,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:15',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:53:16',200,''),(12,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:20',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:53:26',200,''),(13,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:25',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:53:36',200,''),(14,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:30',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:53:46',200,''),(15,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:35',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:53:56',200,''),(16,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:40',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [业务运行中，被强制终止]'),(17,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:45',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(18,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:50',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(19,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:52:55',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(20,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:53:00',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(21,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:01',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:53:11',200,''),(22,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:53:05',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(23,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:05',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:53:21',200,''),(24,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:53:10',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(25,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:10',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:53:31',200,''),(26,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:53:15',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(27,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:15',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:53:41',200,''),(28,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:53:20',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(29,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:20',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:53:51',200,''),(30,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:53:25',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(31,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:25',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:01',200,''),(32,1,5,'BEAN','192.168.45.1:9999','demoJobHandler','demoJobHandler','2018-05-14 21:53:30',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(33,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:30',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [业务运行中，被强制终止]'),(34,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:35',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(35,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:40',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(36,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:45',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(37,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:50',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(38,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:53:55',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(39,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:54:00',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(40,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:54:05',200,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：200<br>msg：null','2018-05-14 21:54:06',500,'Web容器销毁终止 [任务尚未执行，在调度队列中被终止]'),(41,1,6,'BEAN','192.168.45.1:9999','demoJobHandler','','2018-05-14 21:54:10',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[192.168.45.1:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：192.168.45.1:9999<br>code：500<br>msg：java.lang.RuntimeException: Network request error: Connect to 192.168.45.1:9999 [/192.168.45.1] failed: Connection refused: connect',NULL,0,NULL),(42,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:54:46',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(43,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:54:46',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(44,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:54:46',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(45,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:54:46',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(46,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:54:46',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(47,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:54:46',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(48,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:54:46',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(49,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:54:50',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(50,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:54:55',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(51,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:00',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(52,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:05',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(53,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2018-05-14 21:55:07',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(54,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:10',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(55,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:15',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(56,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:20',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(57,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:25',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(58,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:30',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(59,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2018-05-14 21:55:35',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(60,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:35',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(61,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2018-05-14 21:55:40',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(62,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:40',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(63,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2018-05-14 21:55:45',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(64,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:45',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(65,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2018-05-14 21:55:50',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(66,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:50',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(67,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2018-05-14 21:55:55',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(68,1,6,'BEAN',NULL,'demoJobHandler','','2018-05-14 21:55:55',500,'调度机器：192.168.45.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(69,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2019-11-14 04:43:16',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(70,1,6,'BEAN',NULL,'demoJobHandler','','2019-11-14 04:43:16',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(71,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2019-11-14 04:43:21',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(72,1,6,'BEAN',NULL,'demoJobHandler','','2019-11-14 04:43:21',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(73,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2019-11-14 04:43:26',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(74,1,6,'BEAN',NULL,'demoJobHandler','','2019-11-14 04:43:26',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(75,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2019-11-14 04:43:31',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(76,1,6,'BEAN',NULL,'demoJobHandler','','2019-11-14 04:43:31',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(77,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2019-11-14 04:43:36',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(78,1,6,'BEAN',NULL,'demoJobHandler','','2019-11-14 04:43:36',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(79,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2019-11-14 04:43:41',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(80,1,6,'BEAN',NULL,'demoJobHandler','','2019-11-14 04:43:41',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(81,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2019-11-14 04:43:46',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(82,1,6,'BEAN',NULL,'demoJobHandler','','2019-11-14 04:43:46',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(83,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2019-11-14 04:43:51',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(84,1,6,'BEAN',NULL,'demoJobHandler','','2019-11-14 04:43:51',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(85,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2019-11-14 04:43:56',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(86,1,6,'BEAN',NULL,'demoJobHandler','','2019-11-14 04:43:56',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(87,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2019-11-14 04:44:01',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(88,1,6,'BEAN',NULL,'demoJobHandler','','2019-11-14 04:44:01',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(89,1,5,'BEAN',NULL,'demoJobHandler','demoJobHandler','2019-11-14 04:44:06',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(90,1,6,'BEAN',NULL,'demoJobHandler','','2019-11-14 04:44:06',500,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br>----------------------<br>调度失败：执行器地址为空',NULL,0,NULL),(91,1,5,'BEAN','10.253.8.205:9999','demoJobHandler','demoJobHandler','2019-11-14 04:44:11',200,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：[10.253.8.205:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：10.253.8.205:9999<br>code：200<br>msg：null',NULL,0,NULL),(92,1,6,'BEAN','10.253.8.205:9999','demoJobHandler','','2019-11-14 04:44:11',200,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：[10.253.8.205:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：10.253.8.205:9999<br>code：200<br>msg：null',NULL,0,NULL),(93,1,5,'BEAN','10.253.8.205:9999','demoJobHandler','demoJobHandler','2019-11-14 04:44:16',200,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：[10.253.8.205:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：10.253.8.205:9999<br>code：200<br>msg：null',NULL,0,NULL),(94,1,6,'BEAN','10.253.8.205:9999','demoJobHandler','','2019-11-14 04:44:16',200,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：[10.253.8.205:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：10.253.8.205:9999<br>code：200<br>msg：null',NULL,0,NULL),(95,1,5,'BEAN','10.253.8.205:9999','demoJobHandler','demoJobHandler','2019-11-14 04:44:21',200,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：[10.253.8.205:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：10.253.8.205:9999<br>code：200<br>msg：null',NULL,0,NULL),(96,1,6,'BEAN','10.253.8.205:9999','demoJobHandler','','2019-11-14 04:44:21',200,'调度机器：10.253.8.205<br>执行器-注册方式：自动注册<br>执行器-地址列表：[10.253.8.205:9999]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：10.253.8.205:9999<br>code：200<br>msg：null',NULL,0,NULL);

#
# Structure for table "XXL_JOB_QRTZ_TRIGGER_LOGGLUE"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_TRIGGER_LOGGLUE`;
CREATE TABLE `xxl_job_qrtz_trigger_logglue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` text COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_TRIGGER_LOGGLUE"
#


#
# Structure for table "XXL_JOB_QRTZ_TRIGGER_REGISTRY"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_TRIGGER_REGISTRY`;
CREATE TABLE `xxl_job_qrtz_trigger_registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(255) NOT NULL,
  `registry_key` varchar(255) NOT NULL,
  `registry_value` varchar(255) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_TRIGGER_REGISTRY"
#

INSERT INTO `XXL_JOB_QRTZ_TRIGGER_REGISTRY` VALUES (5,'EXECUTOR','open-xxl-job-demo','10.253.8.205:9999','2019-11-14 04:44:14');

#
# Structure for table "XXL_JOB_QRTZ_TRIGGERS"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_TRIGGERS`;
CREATE TABLE `xxl_job_qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `next_fire_time` bigint(13) DEFAULT NULL,
  `prev_fire_time` bigint(13) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) DEFAULT NULL,
  `calendar_name` varchar(200) DEFAULT NULL,
  `misfire_instr` smallint(2) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `xxl_job_qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `xxl_job_qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_TRIGGERS"
#

INSERT INTO `XXL_JOB_QRTZ_TRIGGERS` VALUES ('quartzScheduler','3','1','3','1',NULL,1525885261000,-1,5,'PAUSED','CRON',1525876730000,0,NULL,2,X''),('quartzScheduler','4','1','4','1',NULL,1525885261000,-1,5,'PAUSED','CRON',1525876778000,0,NULL,2,X''),('scheduler','5','1','5','1',NULL,1573706665000,1573706660000,5,'ACQUIRED','CRON',1526305882000,0,NULL,2,X''),('scheduler','6','1','6','1',NULL,1573706665000,1573706660000,5,'WAITING','CRON',1526305980000,0,NULL,2,X'');

#
# Structure for table "XXL_JOB_QRTZ_SIMPROP_TRIGGERS"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `xxl_job_qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `str_prop_1` varchar(512) DEFAULT NULL,
  `str_prop_2` varchar(512) DEFAULT NULL,
  `str_prop_3` varchar(512) DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) DEFAULT NULL,
  `bool_prop_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `xxl_job_qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `xxl_job_qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_SIMPROP_TRIGGERS"
#


#
# Structure for table "XXL_JOB_QRTZ_SIMPLE_TRIGGERS"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `xxl_job_qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `xxl_job_qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `xxl_job_qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_SIMPLE_TRIGGERS"
#


#
# Structure for table "XXL_JOB_QRTZ_CRON_TRIGGERS"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_CRON_TRIGGERS`;
CREATE TABLE `xxl_job_qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `cron_expression` varchar(200) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `xxl_job_qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `xxl_job_qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_CRON_TRIGGERS"
#

INSERT INTO `XXL_JOB_QRTZ_CRON_TRIGGERS` VALUES ('quartzScheduler','3','1','1 1 1 * * ? *','Asia/Shanghai'),('quartzScheduler','4','1','1 1 1 * * ? *','Asia/Shanghai'),('scheduler','5','1','*/5 * * * * ?','GMT+08:00'),('scheduler','6','1','*/5 * * * * ?','GMT+08:00');

#
# Structure for table "XXL_JOB_QRTZ_BLOB_TRIGGERS"
#

DROP TABLE IF EXISTS `XXL_JOB_QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `xxl_job_qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `xxl_job_qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `xxl_job_qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "XXL_JOB_QRTZ_BLOB_TRIGGERS"
#

