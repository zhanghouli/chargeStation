--系统参数
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000001', 'sms.token.valid.minute', '10', now() ,now() , '手机验证码有效时间（单位为分钟）');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000002', 'electric.bill.default.value', '100', now() ,now() , '电费默认值(单位：分)');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000003', 'order.continue.charging.time', '10', now() ,now() , '断电后多久可以续充（单位：分钟）');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000004', 'pay.order.pay.wait.timeout', '15', now() ,now() , '支付订单超时未付款关闭时间（单位为分钟）');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000005', 'max.power.default.value', '30', now() ,now() , '最大功率');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000006', 'min.power.default.value', '80', now() ,now() , '最小功率');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000007', 'trickle.time.default.value', '15', now() ,now() , '涓流时间');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000008', 'is.auto.stop.default.value', '1', now() ,now() , '是否开启充满自停');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000009', 'is.large.power.default.value', '1', now() ,now() , '是否开启最大功率');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000010', 'heart.time.value', '20', now() ,now() , '心跳时间间隔，数字，单位秒');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000011', 'update.time.value', '20', now() ,now() , '自动上报时间间隔，数字，单位秒');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000012', 'wait.time.value', '30', now() ,now() , '收到开命令后多久时间没有插入插头自动断开，数字，单位秒');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000013', 'template.max', '60', now() ,now() , '温度上限，温度超过后报警');
---2017 11 10
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000014', 'operator.divided.proportion', '10', now() ,now() , '运营商分成比例(%)');
INSERT INTO `common_system_config` (`id`, `name`, `val`, `creation_time`, `modify_time`, `remark`) VALUES ('00000000000000000000000000000015', 'estate.divided.proportion', '30', now() ,now() , '物业分成比例(%)');











