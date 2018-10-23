-----
ALTER TABLE `intelcharge`.`carowner`
ADD COLUMN `positioner` VARCHAR(45) NULL DEFAULT NULL COMMENT '定位器设备号' AFTER `open_id`;
---政府
CREATE TABLE `intelcharge`.`government` (
  `id` CHAR(32) NOT NULL,
  `passport_id` CHAR(32) NULL,
  `area` VARCHAR(500) NULL DEFAULT NULL COMMENT '地区码,逗号分开',
  `area_des` VARCHAR(500) NULL DEFAULT NULL COMMENT '地区码文字,逗号分开',
  `is_enabled` BIT(1) NULL DEFAULT b'1' COMMENT '是否可用 1 可用 0 不可用',
  `is_deleted` BIT(1) NULL DEFAULT b'0',
  `creator` CHAR(32) NULL DEFAULT NULL COMMENT '创建人',
  `creation_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` VARCHAR(45) NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '政府';
