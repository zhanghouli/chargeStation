CREATE TABLE `intelcharge`.`station_maintain_log` (
  `id` VARCHAR(32) NOT NULL,
  `station_id` VARCHAR(32) NULL DEFAULT NULL COMMENT '电站ID',
  `passport_id` VARCHAR(32) NULL DEFAULT NULL COMMENT '内容填写者',
  `content` VARCHAR(1024) NULL DEFAULT NULL COMMENT '维护内容',
  `is_deleted` BIT(1) NULL DEFAULT b'0',
  `creator` VARCHAR(32) NULL DEFAULT NULL,
  `creation_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` VARCHAR(32) NULL DEFAULT NULL,
  `modify_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '电站维护日志';

UPDATE `intelcharge`.`common_base_auth` SET `sort`='1' WHERE `id`='00000000000000000000000000002002';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='2' WHERE `id`='00000000000000000000000000002005';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='3' WHERE `id`='00000000000000000000000000002003';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='4' WHERE `id`='00000000000000000000000000002004';


