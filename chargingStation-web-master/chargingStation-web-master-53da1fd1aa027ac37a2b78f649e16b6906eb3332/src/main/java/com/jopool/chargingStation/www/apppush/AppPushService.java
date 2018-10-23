package com.jopool.chargingStation.www.apppush;

import com.alibaba.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;
import com.jopool.chargingStation.www.response.AppPushMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by synn on 2018/1/8.
 */
public class AppPushService {
    private static final Logger          log               = LoggerFactory.getLogger(AppPushService.class);
    private static final ExecutorService executor          = Executors.newFixedThreadPool(10);
    //个推参数
    private static       String          getuiAppId        = ApplicationConfigHelper.getGetuiAppId();
    private static       String          getuiAppKey       = ApplicationConfigHelper.getGetuiAppKey();
    private static       String          getuiMasterSecret = ApplicationConfigHelper.getGetuiMasterSecret();
    static               String          host              = "http://sdk.open.api.igexin.com/apiex.htm";


    /**
     * 单个推送消息
     *
     * @param cid
     * @param appPushMessage
     * @return
     */
    public IPushResult pushMsgToSingle(String cid, AppPushMessage appPushMessage) {
        // 代表在个推注册的一个 app，调用该类实例的方法来执行对个推的请求
        IGtPush push = new IGtPush(getuiAppKey, getuiMasterSecret);
        // 创建信息模板
        NotificationTemplate template = getNotifacationTemplate(appPushMessage);
        //定义消息推送方式为，单推
        SingleMessage message = new SingleMessage();
        // 设置推送消息的内容
        message.setData(template);
        // 设置推送目标
        Target target = new Target();
        target.setAppId(getuiAppId);
        IPushResult result = null;
        // 设置cid
        target.setClientId(cid);
        // 获得推送结果
        result = push.pushMessageToSingle(message, target);
        return result;
    }

    /**
     * 多个推送
     *
     * @param cids
     * @param iTemplate (安卓 || IOS)
     * @return
     */
    public void pushMessageToList(final String[] cids, final ITemplate iTemplate) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // 推送主类
                IIGtPush push = new IGtPush(host, getuiAppKey, getuiMasterSecret);
                IPushResult result = null;
                ListMessage message = new ListMessage();
                //安卓 || IOS
                message.setData(iTemplate);
                //用户当前不在线，默认不存储 可选
                message.setOffline(true);
                //离线有效时间，单位毫秒 可选
                message.setOfflineExpireTime(72 * 3600 * 1000);
                //接收者
                List<Target> targetList = new ArrayList<>();
                for (String cid : cids) {
                    Target target = new Target();
                    target.setAppId(getuiAppId);
                    target.setClientId(cid);
                    targetList.add(target);
                }
                //推送前通过该接口申请“ContentID”
                String contentId = push.getContentId(message);
                result = push.pushMessageToList(contentId, targetList);
            }
        });

    }

    /**
     * 设置通知消息模板（安卓）
     *
     * @param appPushMessage
     * @return
     */
    public NotificationTemplate getNotifacationTemplate(AppPushMessage appPushMessage) {
        // 在通知栏显示一条含图标、标题等的通知，用户点击后激活您的应用
        NotificationTemplate template = new NotificationTemplate();
        // 设置appid，appkey
        template.setAppId(getuiAppId);
        template.setAppkey(getuiAppKey);
        // 穿透消息设置为，1 强制启动应用
        template.setTransmissionType(1);
        // 设置穿透内容
        template.setTransmissionContent(appPushMessage.getTransText());
        Style0 style0 = new Style0();
        style0.setVibrate(true);
        style0.setClearable(true);
        style0.setRing(true);
        style0.setText(appPushMessage.getText());
        style0.setTitle(appPushMessage.getTitle());
        template.setStyle(style0);
        return template;
    }

    /**
     * IOS 推送 附带 透传内容
     *
     * @param appPushMessage
     * @return
     */
    public TransmissionTemplate getTransmissionTemplateDemo(AppPushMessage appPushMessage) {
        TransmissionTemplate transmissionTemplate = new TransmissionTemplate();
        transmissionTemplate.setAppId(getuiAppId);
        transmissionTemplate.setAppkey(getuiAppKey);
        transmissionTemplate.setTransmissionType(1);
        //透传内容
        Map<String, Object> mapObject = new HashMap<String, Object>();
        mapObject.put("NOTICE_TITLE", appPushMessage.getTitle());//标题
        mapObject.put("MSG_DETAIL", appPushMessage.getText()); //内容
        mapObject.put("NOTICE_URL", appPushMessage.getUrl());//打开连接
        JSONObject jsonObject = new JSONObject(mapObject);
        transmissionTemplate.setTransmissionContent(jsonObject.toJSONString());
        //特别说明：推送内容，如果后台加入了下面APNS内容，则可以走全局，哪怕应用退出，也可以弹开内容，而这段代码不会影响到ANDROID开发
        {
            APNPayload payload = new APNPayload();
            payload.setBadge(1);
            payload.setContentAvailable(1);
            payload.setSound("default");
            payload.setCategory("action");
            APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
            alertMsg.setTitle(appPushMessage.getTitle());
            alertMsg.setBody(appPushMessage.getText());
            payload.setAlertMsg(alertMsg);
            payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.audio)
                    .setResUrl(appPushMessage.getUrl())
                    .setOnlyWifi(true));
            // 字典模式使用下者
            payload.setAlertMsg(getDictionaryAlertMsg(appPushMessage));
            transmissionTemplate.setAPNInfo(payload);
        }
        return transmissionTemplate;
    }

    /**
     * 字典模式使用
     *
     * @return
     */
    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(AppPushMessage appPushMessage) {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody("body");
        alertMsg.setActionLocKey("ActionLockey");
        alertMsg.setLocKey(appPushMessage.getText());
        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image");
        // iOS8.2以上版本支持
        alertMsg.setTitle(appPushMessage.getTitle());
        alertMsg.setTitleLocKey(appPushMessage.getTitle());
        alertMsg.addTitleLocArg("TitleLocArg");
        return alertMsg;
    }
}
