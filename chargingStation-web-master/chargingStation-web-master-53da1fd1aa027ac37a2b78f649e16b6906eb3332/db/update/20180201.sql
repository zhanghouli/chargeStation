ALTER TABLE `intelcharge`.`order`
ADD COLUMN `actual_payment` INT(11) NULL DEFAULT NULL COMMENT '实际金额' AFTER `payment`;
