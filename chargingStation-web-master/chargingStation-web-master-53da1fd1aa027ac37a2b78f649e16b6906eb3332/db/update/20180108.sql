ALTER TABLE `intelcharge`.`carowner`
ADD COLUMN `client_id` VARCHAR(2048) NULL DEFAULT NULL COMMENT 'APP推送CID，多个' AFTER `history_rest_time`;

alter table `station_port_real_time_listen` add index index_station_port_id (station_port_id);