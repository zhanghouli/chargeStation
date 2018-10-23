--个人中心
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `passport_type`, `name`) VALUES ('00000000000000000000000000000099', 'admin,government', '个人中心');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000001004', '00000000000000000000000000000100', 'admin,government', '/passport/myPassport.htm', '我的账号');

--用户管理
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `passport_type`, `name`) VALUES ('00000000000000000000000000001000', 'admin,government', '用户管理');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000001005', '00000000000000000000000000001000', 'admin,government', '/passport/governmentList.htm', '政府');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000001001', '00000000000000000000000000001000', 'admin,government', '/passport/passportList.htm', '管理员');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000001002', '00000000000000000000000000001000', 'admin,government', '/passport/operatorList.htm', '运营商');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000001003', '00000000000000000000000000001000', 'admin,government', '/passport/estateList.htm', '物业');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000001004', '00000000000000000000000000001000', 'admin,government', '/passport/carOwnerList.htm', '车主列表');


--电站管理
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `passport_type`, `name`) VALUES ('00000000000000000000000000002001', 'admin,government', '电站管理');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000002002', '00000000000000000000000000002001', 'admin,government', '/station/stationList.htm', '所有电站');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000002003', '00000000000000000000000000002001', 'admin,government', '/station/warningList.htm', '电站警告');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000002004', '00000000000000000000000000002001', 'admin,government', '/order/orderList.htm', '所有订单');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000002005', '00000000000000000000000000002001', 'admin,government', '/station/errorStationList.htm', '异常电站');
--套餐管理
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `passport_type`, `name`) VALUES ('00000000000000000000000000003001', 'admin,government', '套餐管理');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000003002', '00000000000000000000000000003001', 'admin,government', '/consume/consumePackageList.htm', '套餐名称');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000003004', '00000000000000000000000000003001', 'admin,government', '/consume/rechargePackageList.htm', '充值套餐');

--意见反馈
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `passport_type`, `name`) VALUES ('00000000000000000000000000004001', 'admin,government', '意见反馈');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000004002', '00000000000000000000000000004001', 'admin,government', '/feedback/feedBackList.htm', '反馈列表');

--故障报修
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `passport_type`, `name`) VALUES ('00000000000000000000000000005001', 'admin,government', '故障报修');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000005002', '00000000000000000000000000005001', 'admin,government', '/station/stationFaultList.htm', '故障电站');
--资金管理
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `passport_type`, `name`) VALUES ('00000000000000000000000000006001', 'admin,government', '资金管理');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000006002', '00000000000000000000000000006001', 'admin,government', '/account/accountRecharge.htm', '充值记录');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000006003', '00000000000000000000000000006001', 'admin,government', '/account/accountPay.htm', '消费记录');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`, `key`) VALUES ('00000000000000000000000000006004', '00000000000000000000000000006001', 'admin,government', '/account/financialInfo.htm', '财务管理', '73');
--系统参数
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `passport_type`, `name`) VALUES ('00000000000000000000000000007001', 'admin,government', '系统设置');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000007002', '00000000000000000000000000007001', 'admin,government', '/system/systemConfigList.htm', '系统参数');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000007005', '00000000000000000000000000007001', 'admin,government', '/message/wxMessageList.htm', '微信模版配置');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000007003', '00000000000000000000000000007001', 'admin,government', '/pay/wxPayList.htm', '微信支付配置');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000007004', '00000000000000000000000000007001', 'admin,government', '/system/openAreaList.htm', '开通城市');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000007007', '00000000000000000000000000007001', 'admin,government', '/system/appVersionList.htm', 'APP更新配置');

--消息中心
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `passport_type`, `name`) VALUES ('00000000000000000000000000009001', 'admin', '消息中心');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000009002', '00000000000000000000000000009001', 'admin', '/msg/cmppSmsSendList.htm', '发送消息');
INSERT INTO `intelcharge`.`common_base_auth` (`id`, `parent_id`, `passport_type`, `type`, `name`) VALUES ('00000000000000000000000000009003', '00000000000000000000000000009001', 'admin', '/msg/cmppSmsReceiveList.htm', '历史消息');



----sort  修改
UPDATE `intelcharge`.`common_base_auth` SET `sort`='1' WHERE `id`='00000000000000000000000000000099';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='2' WHERE `id`='00000000000000000000000000001000';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='3' WHERE `id`='00000000000000000000000000002001';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='4' WHERE `id`='00000000000000000000000000003001';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='5' WHERE `id`='00000000000000000000000000008001';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='6' WHERE `id`='00000000000000000000000000006001';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='7' WHERE `id`='00000000000000000000000000005001';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='8' WHERE `id`='00000000000000000000000000004001';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='9' WHERE `id`='00000000000000000000000000009001';
UPDATE `intelcharge`.`common_base_auth` SET `sort`='10' WHERE `id`='00000000000000000000000000007001';












