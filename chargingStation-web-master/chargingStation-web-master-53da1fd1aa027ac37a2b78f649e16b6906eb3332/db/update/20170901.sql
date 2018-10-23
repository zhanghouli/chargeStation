ALTER TABLE `intelcharge`.`carowner`
CHANGE COLUMN `open_id` `open_id` VARCHAR(45) NULL AFTER `passport_id`;

ALTER TABLE `intelcharge`.`station`
ADD COLUMN `electric_bill` INT(11) NULL AFTER `consume_package_id`;

ALTER TABLE `intelcharge`.`order`
ADD COLUMN `station_id` CHAR(32) NULL AFTER `carowner_id`,
ADD COLUMN `station_port_id` CHAR(32) NULL AFTER `station_id`;

CREATE TABLE `recharge_package_snapshot` (
  `id` char(32) NOT NULL,
  `rcharge_package_id` char(32) DEFAULT NULL,
  `payment` int(11) DEFAULT NULL COMMENT '实际支付金额，单位分',
  `amount` int(11) DEFAULT NULL COMMENT '得到的金额，单位分',
  `is_enabled` bit(1) DEFAULT b'1' COMMENT '是否可用',
  `is_deleted` bit(1) DEFAULT b'0',
  `creator` char(32) DEFAULT NULL,
  `creation_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(45) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值套餐';


CREATE TABLE `recharge_order` (
  `id` char(32) NOT NULL,
  `carowner_id` char(32) DEFAULT NULL COMMENT '车主id',
  `order_id` char(32) DEFAULT NULL,
  `rcharge_package_snapshot_id` char(32) DEFAULT NULL,
  `payment` int(11) DEFAULT NULL COMMENT '支付金额，单位分',
  `amount` int(11) DEFAULT NULL COMMENT '得到的金额，单位分',
  `type` varchar(45) DEFAULT NULL COMMENT '类型',
  `status` varchar(45) DEFAULT NULL COMMENT '状态',
  `is_deleted` bit(1) DEFAULT b'0',
  `creator` char(32) DEFAULT NULL,
  `creation_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(45) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易订单';





