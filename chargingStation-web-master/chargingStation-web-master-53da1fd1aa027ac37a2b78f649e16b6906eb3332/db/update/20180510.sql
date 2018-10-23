
ALTER TABLE `intelcharge`.`order`
ADD COLUMN `estate_id` CHAR(32) NULL DEFAULT NULL COMMENT '物业id' AFTER `station_port_snapshot_id`,
ADD COLUMN `operator_id` CHAR(32) NULL DEFAULT NULL COMMENT '运营商id' AFTER `estate_id`;
