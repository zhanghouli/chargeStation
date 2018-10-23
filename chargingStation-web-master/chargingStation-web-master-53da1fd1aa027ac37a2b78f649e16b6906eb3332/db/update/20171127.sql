CREATE TABLE `intelcharge`.`message_template` (
  `id` CHAR(32) NOT NULL,
  `type` VARCHAR(58) NULL DEFAULT NULL COMMENT '模版类型',
  `template_id` VARCHAR(58) NULL DEFAULT NULL COMMENT '模版ID',
  `is_deleted` BIT(1) NULL DEFAULT b'0',
  `creator` CHAR(32) NULL DEFAULT NULL,
  `creation_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` VARCHAR(45) NULL DEFAULT NULL,
  `modify_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '模板消息配置';


CREATE TABLE `alipay_response` (
  `id` char(32) NOT NULL,
  `notify_time` varchar(45) DEFAULT NULL,
  `notify_type` varchar(64) DEFAULT NULL,
  `notify_id` varchar(200) DEFAULT NULL,
  `app_id` varchar(45) DEFAULT NULL,
  `charset` varchar(45) DEFAULT NULL,
  `version` varchar(45) DEFAULT NULL,
  `sign_type` varchar(45) DEFAULT NULL,
  `sign` varchar(450) DEFAULT NULL,
  `trade_no` varchar(64) DEFAULT NULL,
  `out_trade_no` varchar(64) DEFAULT NULL,
  `out_biz_no` varchar(64) DEFAULT NULL,
  `buyer_id` varchar(45) DEFAULT NULL,
  `buyer_logon_id` varchar(100) DEFAULT NULL,
  `seller_id` varchar(45) DEFAULT NULL,
  `seller_email` varchar(100) DEFAULT NULL,
  `trade_status` varchar(45) DEFAULT NULL,
  `total_amount` varchar(45) DEFAULT NULL,
  `receipt_amount` varchar(45) DEFAULT NULL,
  `invoice_amount` varchar(45) DEFAULT NULL,
  `buyer_pay_amount` varchar(45) DEFAULT NULL,
  `point_amount` varchar(45) DEFAULT NULL,
  `refund_fee` varchar(45) DEFAULT NULL,
  `subject` varchar(256) DEFAULT NULL,
  `body` varchar(450) DEFAULT NULL,
  `gmt_create` varchar(45) DEFAULT NULL,
  `gmt_payment` varchar(45) DEFAULT NULL,
  `gmt_refund` varchar(45) DEFAULT NULL,
  `gmt_close` varchar(45) DEFAULT NULL,
  `fund_bill_list` varchar(512) DEFAULT NULL,
  `passback_params` varchar(512) DEFAULT NULL,
  `voucher_detail_list` varchar(1000) DEFAULT NULL,
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付宝返回';

ALTER TABLE `intelcharge`.`alipay_response`
ADD COLUMN `auth_app_id` VARCHAR(45) NULL AFTER `voucher_detail_list`;

