CREATE TABLE `charge_cost_package` (
  `id` char(32) NOT NULL,
  `min_pow` int(11) DEFAULT NULL COMMENT '最低功率',
  `max_pow` int(11) DEFAULT NULL COMMENT '最高功率',
  `cost` int(11) DEFAULT NULL COMMENT '价格',
  `remark` VARCHAR(45) DEFAULT NULL COMMENT '备注',
  `is_enabled` bit(1) DEFAULT b'1' COMMENT '是否可用',
  `is_deleted` bit(1) DEFAULT b'0',
  `creator` char(32) DEFAULT NULL,
  `creation_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(45) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按量充值规则';

INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`, `key`) VALUES ('00000000000000000000000000003005', '00000000000000000000000000003001', 'admin,government', '/consume/chargeCostPackageList.htm', '电量充值配置', '42');
