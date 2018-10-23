CREATE TABLE `app_version` (
  `id` char(32) NOT NULL,
  `app_id` varchar(45) DEFAULT NULL COMMENT '包名',
  `os` varchar(45) DEFAULT NULL COMMENT '系统ANDROID/IOS',
  `last_version` varchar(45) DEFAULT NULL COMMENT '当前版本',
  `url` varchar(450) DEFAULT NULL COMMENT '更新url',
  `is_force_update` tinyint(1) DEFAULT NULL COMMENT '是否强制升级',
  `description` varchar(1024) DEFAULT NULL COMMENT '升级说明',
  `is_deleted` tinyint(1) DEFAULT '0',
  `creator` char(32) DEFAULT NULL,
  `creation_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='版本更新';
