CREATE TABLE `ali_config` (
  `id` char(32) NOT NULL COMMENT 'id',
  `app_id` varchar(100) DEFAULT NULL COMMENT '支付宝分配给开发者的应用ID',
  `notify_url` varchar(200) DEFAULT NULL COMMENT '支付完成后通知地址',
  `return_url` varchar(200) DEFAULT NULL COMMENT '页面跳转同步通知页面路径',
  `private_key` varchar(10000) DEFAULT NULL COMMENT '秘钥',
  `public_key` varchar(2000) DEFAULT NULL COMMENT '公钥',
  `creator` char(32) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `creation_time` datetime DEFAULT NULL,
  `platform` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付宝支付配置';


INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`, `key`) VALUES ('00000000000000000000000000007006', '00000000000000000000000000007001', 'admin,government', '/pay/aliPayList.htm', '支付宝支付配置', '95');

UPDATE `intelcharge`.`common_base_auth` SET `sort`='91' WHERE `id`='00000000000000000000000000007002';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='93' WHERE `id`='00000000000000000000000000007003';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='92' WHERE `id`='00000000000000000000000000007006';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='94' WHERE `id`='00000000000000000000000000007005';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='95' WHERE `id`='00000000000000000000000000007004';
