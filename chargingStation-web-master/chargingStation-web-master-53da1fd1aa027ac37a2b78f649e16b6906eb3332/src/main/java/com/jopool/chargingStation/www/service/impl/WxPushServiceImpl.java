package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.base.pay.wxpay.message.TemplateData;
import com.jopool.chargingStation.www.base.pay.wxpay.message.TemplateMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.constants.SystemParamKeys;
import com.jopool.chargingStation.www.enums.ChargeTypeEnum;
import com.jopool.chargingStation.www.enums.PassportTypeEnum;
import com.jopool.chargingStation.www.request.TemplateMessageReq;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.WxPushService;
import com.jopool.chargingStation.www.vo.WxPushVo;
import com.jopool.jweb.utils.DateUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Service;
import weixin.popular.api.API;
import weixin.popular.bean.message.templatemessage.TemplateMessageResult;
import weixin.popular.client.LocalHttpClient;
import weixin.popular.util.JsonUtil;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by synn on 2017/11/27.
 */
@Service
public class WxPushServiceImpl extends BaseServiceImpl implements WxPushService {
    private static final ExecutorService executor      = Executors.newFixedThreadPool(10);
    private static final DecimalFormat   decimalFormat = new DecimalFormat("0.00");

    @Resource
    private CommonService commonService;

    @Override
    public void pushRechargeAmount(final WxPushVo wxPushVo, final Integer amount, final PassportAccount passportAccount) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (wxPushVo.getCarowner().getOpenId() == null) {
                    return;
                }
                if (wxPushVo.getTokenResp().getAccess_token() == null) {
                    return;
                }
                TemplateData templateData = new TemplateData();

                //抬头
                templateData.put("first", templateData.new Item("您的充值成功啦"));
                //充值时间
                templateData.put("keyword1", templateData.new Item(DateUtils.date2StringByMinute(new Date())));
                //充值金额
                templateData.put("keyword2", templateData.new Item(decimalFormat.format(Double.parseDouble(String.valueOf(amount)) / 100) + "元"));
                //赠送金额
                templateData.put("keyword3", templateData.new Item(decimalFormat.format(0) + "元"));
                //账户余额
                templateData.put("keyword4", templateData.new Item(decimalFormat.format(Double.parseDouble(String.valueOf(passportAccount.getAmount())) / 100) + "元"));
                //备注
                templateData.put("remark", templateData.new Item("有问题请联系客服 "));
                TemplateMessageReq templateMessage = new TemplateMessageReq(wxPushVo.getCarowner().getOpenId(), wxPushVo.getMessageTemplate().getTemplateId(), commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL) + "member/wallet", "", templateData);
                messageTemplateSend(wxPushVo.getTokenResp().getAccess_token(), templateMessage);
            }
        });
    }

    @Override
    public void pushStartCharge(final WxPushVo wxPushVo, final Station station, final StationPort stationPort, final Order order) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (wxPushVo.getCarowner().getOpenId() == null) {
                    return;
                }
                if (wxPushVo.getTokenResp().getAccess_token() == null) {
                    return;
                }
                TemplateData templateData = new TemplateData();
                //抬头
                //电功率充值
                if (order.getChargeType().equals(ChargeTypeEnum.ELECTRICITY.getValue())) {
                    templateData.put("first", templateData.new Item("充电开始,本次充电套餐 : 按电量电功率充值"));
                }
                if (order.getChargeType().equals(ChargeTypeEnum.TIME.getValue())) {
                    templateData.put("first", templateData.new Item("充电开始,本次充电套餐 : " + decimalFormat.format(Double.parseDouble(String.valueOf(order.getPayment())) / 100) + "元" + decimalFormat.format(order.getPayment() / 60) + "小时"));
                }
                //电站地址 详情
                templateData.put("keyword1", templateData.new Item(station.getAddress() + station.getName() + "桩第" + (stationPort.getSeq() + 1) + "号插座编号" + stationPort.getQrCode()));
                //开始时间
                templateData.put("keyword2", templateData.new Item(DateUtils.date2StringByMinute(new Date())));
                //预付金额
                templateData.put("keyword3", templateData.new Item(decimalFormat.format(Double.parseDouble(String.valueOf(order.getPayment())) / 100) + "元"));
                //账户余额
                templateData.put("keyword4", templateData.new Item(decimalFormat.format(Double.parseDouble(String.valueOf(wxPushVo.getPassportAccount().getAmount())) / 100) + "元"));
                //电站地址
                //备注信息
                templateData.put("remark", templateData.new Item(
                        "充电过程中，出现下列情况可能会停止充电\n" +
                                "1、电池已经充满\n" +
                                "2、充电器或者电池存在故障\n" +
                                "3、充电功率高出充电插座允许的最大功率\n" +
                                "4、充电器被拔出\n" +
                                "5、自行终止充电"
                        , "#FF0000"));

                TemplateMessageReq templateMessage = new TemplateMessageReq(wxPushVo.getCarowner().getOpenId(), wxPushVo.getMessageTemplate().getTemplateId(), commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL) + "member/chaRecDetail?orderId=" + order.getId(), "", templateData);
                messageTemplateSend(wxPushVo.getTokenResp().getAccess_token(), templateMessage);

            }
        });
    }

    @Override
    public void pushChargeComlete(final WxPushVo wxPushVo, final Station station, final StationPort stationPort, final Order order, final Integer account, final String remark) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (wxPushVo.getCarowner().getOpenId() == null) {
                    return;
                }
                if (wxPushVo.getTokenResp().getAccess_token() == null) {
                    return;
                }
                TemplateData templateData = new TemplateData();
                //抬头
                templateData.put("first", templateData.new Item("充电结束"));
                //电站地址 详情
                templateData.put("keyword1", templateData.new Item(station.getAddress() + station.getName() + "桩第" + (stationPort.getSeq() + 1) + "号插座编号" + stationPort.getQrCode()));
                //结束时间
                templateData.put("keyword2", templateData.new Item(DateUtils.date2StringByMinute(new Date())));
                //消费金额
                templateData.put("keyword3", templateData.new Item(decimalFormat.format(Double.parseDouble(String.valueOf(order.getPayment())) / 100) + "元"));
                //退还金额 TODO
                templateData.put("keyword4", templateData.new Item(decimalFormat.format(Double.parseDouble(String.valueOf(account)) / 100) + "元"));
                //账户余额
                templateData.put("keyword5", templateData.new Item(decimalFormat.format(Double.parseDouble(String.valueOf(wxPushVo.getPassportAccount().getAmount())) / 100) + "元"));
                //备注
                templateData.put("remark", templateData.new Item(remark));
                TemplateMessageReq templateMessage = new TemplateMessageReq(wxPushVo.getCarowner().getOpenId(), wxPushVo.getMessageTemplate().getTemplateId(), commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL) + "member/chaRecDetail?orderId=" + order.getId(), "", templateData);
                messageTemplateSend(wxPushVo.getTokenResp().getAccess_token(), templateMessage);
            }
        });
    }

    @Override
    public void pushChargeTime(WxPushVo wxPushVo, Station station, StationPort stationPort, Order order) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (wxPushVo.getCarowner().getOpenId() == null) {
                    return;
                }
                if (wxPushVo.getTokenResp().getAccess_token() == null) {
                    return;
                }
                TemplateData templateData = new TemplateData();
                //抬头
                //电功率充值
                if (order.getChargeType().equals(ChargeTypeEnum.ELECTRICITY.getValue())) {
                    templateData.put("first", templateData.new Item("充电开始,本次充电套餐 : 按电量电功率充值"));
                }
                if (order.getChargeType().equals(ChargeTypeEnum.TIME.getValue())) {
                    templateData.put("first", templateData.new Item("充电开始,本次充电套餐 : " + decimalFormat.format(Double.parseDouble(String.valueOf(order.getPayment())) / 100) + "元" + decimalFormat.format(order.getPayment() / 60) + "小时"));
                }
                //电站地址 详情
                templateData.put("keyword1", templateData.new Item(station.getAddress() + station.getName() + "桩第" + (stationPort.getSeq() + 1) + "号插座编号" + stationPort.getQrCode()));
                //开始时间
                templateData.put("keyword2", templateData.new Item(DateUtils.date2StringByMinute(new Date())));
                //套餐时长 TODO
                //templateData.put("keyword3",templateData.new Item(order.getPayTime()+"分钟"));
                //当前时长
                templateData.put("keyword3", templateData.new Item(wxPushVo.getCarowner().getHistoryRestTime() + "分钟"));
                //备注
                templateData.put("remark", templateData.new Item(
                        "充电过程中，出现下列情况可能会停止充电\n" +
                                "1、电池已经充满\n" +
                                "2、充电器或者电池存在故障\n" +
                                "3、充电功率高出充电插座允许的最大功率\n" +
                                "4、充电器被拔出\n" +
                                "5、自行终止充电"
                        , "#FF0000"));

                TemplateMessageReq templateMessage = new TemplateMessageReq(wxPushVo.getCarowner().getOpenId(), wxPushVo.getMessageTemplate().getTemplateId(), commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL)+ "member/chaRecDetail?orderId=" + order.getId(), "", templateData);
                messageTemplateSend(wxPushVo.getTokenResp().getAccess_token(), templateMessage);
            }
        });
    }

    @Override
    public void pushChargeTimeComlete(WxPushVo wxPushVo, Station station, StationPort stationPort, Order order, String remark) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (wxPushVo.getCarowner().getOpenId() == null) {
                    return;
                }
                if (wxPushVo.getTokenResp().getAccess_token() == null) {
                    return;
                }
                TemplateData templateData = new TemplateData();
                //抬头
                templateData.put("first", templateData.new Item("充电结束"));
                //电站地址 详情
                templateData.put("keyword1", templateData.new Item(station.getAddress() + station.getName() + "桩第" + (stationPort.getSeq() + 1) + "号插座编号" + stationPort.getQrCode()));
                //结束时间
                templateData.put("keyword2", templateData.new Item(DateUtils.date2StringByMinute(new Date())));
                //充电时长
                templateData.put("keyword3", templateData.new Item(order.getActualTime() + "分钟"));
                //退还时长
                if (ChargeTypeEnum.TIME.getValue().equals(order.getChargeType())) {
                    templateData.put("keyword4", templateData.new Item((order.getPayTime() - order.getActualTime()) + "分钟"));
                }
                if (ChargeTypeEnum.ELECTRICITY.getValue().equals(order.getChargeType())) {
                    templateData.put("keyword4", templateData.new Item((order.getActualTime()) + "分钟"));
                }
                //剩余时长
                templateData.put("keyword5", templateData.new Item(wxPushVo.getCarowner().getHistoryRestTime() + "分钟"));
                //备注
                templateData.put("remark", templateData.new Item(remark));
                TemplateMessageReq templateMessage = new TemplateMessageReq(wxPushVo.getCarowner().getOpenId(), wxPushVo.getMessageTemplate().getTemplateId(), commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL) + "member/chaRecDetail?orderId=" + order.getId(), "", templateData);
                messageTemplateSend(wxPushVo.getTokenResp().getAccess_token(), templateMessage);
            }
        });
    }

    @Override
    public void pushTimeCharge(final WxPushVo wxPushVo, final Carowner carowner) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (wxPushVo.getCarowner().getOpenId() == null) {
                    return;
                }
                if (wxPushVo.getTokenResp().getAccess_token() == null) {
                    return;
                }
                TemplateData templateData = new TemplateData();
                templateData.put("first", templateData.new Item("亲爱的车主，您好！\n" + "您于" + DateUtils.date2String(new Date(), "yyyy年MM月dd日") + "，使用时间余量进行了充电服务"));
                //温馨提示
                templateData.put("keyword1", templateData.new Item(""));
                //当前余量
                templateData.put("keyword2", templateData.new Item("当前时间余量:" + carowner.getHistoryRestTime() + "分钟"));
                //日期
                templateData.put("keyword2", templateData.new Item(DateUtils.date2StringByMinute(new Date())));
                //备注
                templateData.put("remark", templateData.new Item("有问题请联系客服"));

                TemplateMessageReq templateMessage = new TemplateMessageReq(wxPushVo.getCarowner().getOpenId(), wxPushVo.getMessageTemplate().getTemplateId(), commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL) + "member/wallet", "", templateData);
                messageTemplateSend(wxPushVo.getTokenResp().getAccess_token(), templateMessage);
            }
        });
    }


    @Override
    public void pushConsumption(final WxPushVo wxPushVo, final Integer amount, final PassportAccount passportAccount) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (wxPushVo.getCarowner().getOpenId() == null) {
                    return;
                }
                if (wxPushVo.getTokenResp().getAccess_token() == null) {
                    return;
                }
                TemplateData templateData = new TemplateData();

                //抬头
                templateData.put("first", templateData.new Item("亲爱的车主，您好！\n" + "您于" + DateUtils.date2String(new Date(), "yyyy年MM月dd日") + "进行了充电消费"));
                //消费金额
                templateData.put("keyword1", templateData.new Item(decimalFormat.format(Double.parseDouble(String.valueOf(amount)) / 100) + "元"));
                //剩余金额
                templateData.put("keyword2", templateData.new Item(decimalFormat.format(Double.parseDouble(String.valueOf(passportAccount.getAmount())) / 100) + "元"));
                //消费日期
                templateData.put("keyword3", templateData.new Item(DateUtils.date2StringByMinute(new Date())));
                templateData.put("remark", templateData.new Item("有问题请联系客服 "));
                TemplateMessageReq templateMessage = new TemplateMessageReq(wxPushVo.getCarowner().getOpenId(), wxPushVo.getMessageTemplate().getTemplateId(), commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL) + "member/wallet", "", templateData);
                messageTemplateSend(wxPushVo.getTokenResp().getAccess_token(), templateMessage);
            }
        });
    }

    @Override
    public void pushRefundTips(final WxPushVo wxPushVo, final Integer amount) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (wxPushVo.getCarowner().getOpenId() == null) {
                    return;
                }
                if (wxPushVo.getTokenResp().getAccess_token() == null) {
                    return;
                }
                TemplateData templateData = new TemplateData();
                //抬头
                templateData.put("first", templateData.new Item("您好，欢迎使用好驿达充电"));
                //退款原因
                templateData.put("keyword1", templateData.new Item("因长时间没有进行充电，并取消了本次订单，我们已将" + decimalFormat.format(Double.parseDouble(String.valueOf(amount)) / 100) + "元返还到您的余额，请注意查看！如有疑问请联系我们的客服"));
                //退款金额
                templateData.put("keyword2", templateData.new Item(decimalFormat.format(Double.parseDouble(String.valueOf(amount)) / 100) + "元"));
                //退款时间
                templateData.put("keyword3", templateData.new Item(DateUtils.date2StringByMinute(new Date())));
                templateData.put("remark", templateData.new Item("感谢使用好驿达充电，祝您生活愉快！ "));
                TemplateMessageReq templateMessage = new TemplateMessageReq(wxPushVo.getCarowner().getOpenId(), wxPushVo.getMessageTemplate().getTemplateId(), commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL)+ "member/wallet", "", templateData);
                messageTemplateSend(wxPushVo.getTokenResp().getAccess_token(), templateMessage);
            }
        });
    }


    @Override
    public void pushNotSufficientFunds(final WxPushVo wxPushVo) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (wxPushVo.getCarowner().getOpenId() == null) {
                    return;
                }
                if (wxPushVo.getTokenResp().getAccess_token() == null) {
                    return;
                }
                TemplateData templateData = new TemplateData();
                //抬头
                templateData.put("first", templateData.new Item("您好，欢迎使用好驿达充电"));
                //温馨提示  余额不足
                //TODO
                templateData.put("keyword1", templateData.new Item("提示语句"));
                //日期
                templateData.put("keyword1", templateData.new Item(DateUtils.date2StringByMinute(new Date())));
                //备注
                templateData.put("remark", templateData.new Item("感谢使用好驿达充电，祝您生活愉快！ "));
                TemplateMessageReq templateMessage = new TemplateMessageReq(wxPushVo.getCarowner().getOpenId(), wxPushVo.getMessageTemplate().getTemplateId(), commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL) + "member/wallet", "", templateData);
                messageTemplateSend(wxPushVo.getTokenResp().getAccess_token(), templateMessage);
            }
        });

    }

    @Override
    public void pushDisconnectCharging(final WxPushVo wxPushVo, final Station station, final StationPort stationPort, final String type, final Order order) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (wxPushVo.getCarowner().getOpenId() == null) {
                    return;
                }
                if (wxPushVo.getTokenResp().getAccess_token() == null) {
                    return;
                }
                TemplateData templateData = new TemplateData();
                //抬头
                templateData.put("first", templateData.new Item("亲爱的车主，您好！\n" + "您于" + DateUtils.date2String(new Date(), "yyyy年MM月dd日") + "车辆断开了充电"));
                //电站名称
                templateData.put("keyword1", templateData.new Item(station.getName()));
                //电口号
                templateData.put("keyword2", templateData.new Item(stationPort.getNumber() + "号"));
                //日期
                templateData.put("keyword3", templateData.new Item(DateUtils.date2StringByMinute(new Date())));
                //地址
                templateData.put("keyword4", templateData.new Item(station.getAddress()));
                //断开原因
                templateData.put("keyword5", templateData.new Item(type));
                //备注
                templateData.put("remark", templateData.new Item("有问题请联系客服 "));
                TemplateMessageReq templateMessage = new TemplateMessageReq(wxPushVo.getCarowner().getOpenId(), wxPushVo.getMessageTemplate().getTemplateId(), commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL) + "member/chaRecDetail?orderId=" + order.getId(), "", templateData);
                messageTemplateSend(wxPushVo.getTokenResp().getAccess_token(), templateMessage);
            }
        });
    }


    @Override
    public void pushSocketException() {
        //TODO  短信通知
    }

    @Override
    public void pushOrderClosure(final WxPushVo wxPushVo, final Passport passport, final Station station, final StationPort stationPort, final Order order) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (wxPushVo.getCarowner().getOpenId() == null) {
                    return;
                }
                if (wxPushVo.getTokenResp().getAccess_token() == null) {
                    return;
                }
                TemplateData templateData = new TemplateData();
                //抬头
                templateData.put("first", templateData.new Item("亲爱的车主，您好！\n" + "您于" + DateUtils.date2String(new Date(), "yyyy年MM月dd日") + "充值订单被关闭"));
                //电站
                templateData.put("keyword1", templateData.new Item(station.getName()));
                //电口
                templateData.put("keyword2", templateData.new Item(stationPort.getNumber()));
                //日期
                templateData.put("keyword3", templateData.new Item(DateUtils.date2StringByMinute(new Date())));
                //地址
                templateData.put("keyword4", templateData.new Item(station.getAddress()));
                //关闭人类型
                templateData.put("keyword5", templateData.new Item(PassportTypeEnum.getEnumName(passport.getType())));
                //备注
                templateData.put("remark", templateData.new Item("有问题请联系客服 "));
                TemplateMessageReq templateMessage = new TemplateMessageReq(wxPushVo.getCarowner().getOpenId(), wxPushVo.getMessageTemplate().getTemplateId(), commonService.getConfigValueByName(SystemParamKeys.WX_TUI, Constants.WX_TUI_URL) + "member/chaRecDetail?orderId=" + order.getId(), "", templateData);
                messageTemplateSend(wxPushVo.getTokenResp().getAccess_token(), templateMessage);
            }
        });
    }

    //推送 数据发送

    public TemplateMessageResult messageTemplateSend(String access_token, TemplateMessage templateMessage) {
        String messageJson = JsonUtil.toJSONString(templateMessage);
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(BASE_URI + "/cgi-bin/message/template/send")
                .addParameter(PARAM_ACCESS_TOKEN, API.accessToken(access_token))
                .setEntity(new StringEntity(messageJson, Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, TemplateMessageResult.class);
    }

}
