CREATE TABLE `mqtt_message_log` (
  `id` CHAR(32) NOT NULL,
  `number` VARCHAR(45) NULL COMMENT '设备number',
  `message` VARCHAR(4096) NULL COMMENT '消息内容',
  `creation_time` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'mqtt消息记录';
