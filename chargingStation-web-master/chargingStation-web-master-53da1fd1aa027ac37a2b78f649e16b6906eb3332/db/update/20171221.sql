CREATE TABLE `intelcharge`.`cmpp_sms_send` (
  `id` CHAR(32) NOT NULL,
  `phone` VARCHAR(45) NULL DEFAULT NULL COMMENT '接收手机号',
  `content` VARCHAR(2014) NULL DEFAULT NULL COMMENT '发送内容',
  `result` BIT(1) NULL DEFAULT b'0' COMMENT '是否成功发送:1 成功 0 失败',
  `creator` CHAR(32) NULL DEFAULT NULL,
  `is_deleted` TINYINT(1) NULL DEFAULT NULL,
  `creation_time` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '发送消息';

CREATE TABLE `intelcharge`.`cmpp_sms_receive` (
  `id` CHAR(32) NOT NULL,
  `phone` VARCHAR(45) NULL DEFAULT NULL COMMENT '收息手机号',
  `content` VARCHAR(2014) NULL DEFAULT NULL COMMENT '内容',
  `is_deleted` TINYINT(1) NULL DEFAULT NULL,
  `creation_time` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '收到消息';
