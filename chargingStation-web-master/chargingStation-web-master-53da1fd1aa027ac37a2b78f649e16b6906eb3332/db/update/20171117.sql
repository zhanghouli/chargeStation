ALTER TABLE `intelcharge`.`order`
ADD COLUMN `charge_type` VARCHAR(20) NULL COMMENT '充电类型：time-按时间套餐 electricity-按电量电功率充值' AFTER `status`;

ALTER TABLE `intelcharge`.`carowner`
ADD COLUMN `lat` INT(11) NULL DEFAULT NULL COMMENT '人坐标 维度' AFTER `fence_status`,
ADD COLUMN `lng` INT(11) NULL DEFAULT NULL COMMENT '人坐标经度' AFTER `lat`;

