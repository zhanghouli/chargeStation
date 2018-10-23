CREATE TABLE `station_real_time_listen` (
  `id` CHAR(32) NOT NULL,
  `station_id` CHAR(32) NOT NULL,
  `temperature` VARCHAR(20) DEFAULT NULL COMMENT '充电站温度，数字，单位摄氏度',
  `voltage` VARCHAR(20) DEFAULT NULL COMMENT '电源电压，数字，单位V',
  `energy` VARCHAR(20) DEFAULT NULL COMMENT '电能量，数字，单位kwh',
  `version` VARCHAR(20) DEFAULT NULL COMMENT '版本号',
  `data_time` DATETIME DEFAULT NULL COMMENT '充电站实时时间，数字，世纪秒',
  `is_deleted` BIT(1) NULL DEFAULT b'0',
  `creator` CHAR(32) NULL DEFAULT NULL COMMENT '创建人',
  `creation_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '电站实时监控信息';

CREATE TABLE `station_port_real_time_listen` (
  `id` CHAR(32) NOT NULL,
  `station_id` CHAR(32) NOT NULL,
  `station_port_id` CHAR(32) NOT NULL COMMENT '充电口id',
  `station_port_seq` INT(11) DEFAULT NULL COMMENT '充电口序号',
  `current` VARCHAR(20) DEFAULT NULL COMMENT '插座电流：数字，单位A',
  `power` VARCHAR(20) DEFAULT NULL COMMENT '有功功率，数字，单位W',
  `energy` VARCHAR(20) DEFAULT NULL COMMENT '电能量，数字，单位kwh',
  `remaining_time` INT(11) DEFAULT NULL COMMENT '插座剩余时间，数字，单位分钟',
  `status` BIT(1) NULL DEFAULT NULL COMMENT '插座通断状态，数字，0-断开，（非0-接通）1-待插头，2-充电中，3-涓流中' ,
  `is_deleted` BIT(1) NULL DEFAULT b'0',
  `creator` CHAR(32) NULL DEFAULT NULL COMMENT '创建人',
  `creation_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '充电口实时监控信息';

CREATE TABLE `warning` (
  `id` CHAR(32) NOT NULL,
  `station_id` CHAR(32) NOT NULL,
  `data_time` DATETIME DEFAULT NULL COMMENT '充电站实时时间，数字，世纪秒',
  `temperature` VARCHAR(20) DEFAULT NULL COMMENT '充电站温度，数字，单位摄氏度',
  `remark` VARCHAR(50) DEFAULT NULL COMMENT '信息描述',
  `type` BIT(1) NULL DEFAULT NULL COMMENT '1-温度超限，2-机箱门被打开，3-停电' ,
  `is_deleted` BIT(1) NULL DEFAULT b'0',
  `creator` CHAR(32) NULL DEFAULT NULL COMMENT '创建人',
  `creation_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '报警信息';

INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`, `key`) VALUES ('00000000000000000000000000002003', '00000000000000000000000000002001', 'admin,government', '/station/warningList.htm', '电站警告', '32');

ALTER TABLE `intelcharge`.`carowner`
ADD COLUMN `history_rest_time` INT(11) NULL COMMENT '历史剩余时间' AFTER `avatar`,
ADD COLUMN `use_status` VARCHAR(20) NULL COMMENT '历史使用时间  当前使用状态' AFTER `history_rest_time`;

ALTER TABLE `intelcharge`.`order`
ADD COLUMN `payType` VARCHAR(20) NULL COMMENT '支付类型' AFTER `status`;

