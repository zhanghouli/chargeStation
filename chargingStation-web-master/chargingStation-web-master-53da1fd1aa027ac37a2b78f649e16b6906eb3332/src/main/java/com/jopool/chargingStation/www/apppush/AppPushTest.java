package com.jopool.chargingStation.www.apppush;

import com.alibaba.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.jopool.chargingStation.www.response.AppPushMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by synn on 2018/1/8.
 */
public class AppPushTest {
    private static String appId  = "07bRzcq82M6olBjEjDVyj9";
    private static String appkey = "fEz5VqLaEcALXdrVGpN7z8";
    private static String master = "3HddQ0j3rqATdluGF1c5a3";
    static         String host   = "http://sdk.open.api.igexin.com/apiex.htm";
    public static void main(String[] args) {
        AppPushService appPushService = new AppPushService();
        AppPushMessage appPushMessage = new AppPushMessage("1", "2","http://h5.h1d.com.cn/station/index.html#/member/feedback");
        String[] cids = {"e2b5e76519c7e07b31db89a2eb79a1e8", "75587f9b31b34e3b090d87be3eca6c8b","6680b891643f338b6e5f477b7e6b0774"};
       // appPushService.pushMessageToList(cids, appPushService.getTransmissionTemplateDemo(appPushMessage));
        String cid = "6680b891643f338b6e5f477b7e6b0774";
        IGtPush push = new IGtPush(host, appkey, master);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(transmissionTemplateDemo());
        message.setPushNetWorkType(0);
        Target target = new Target();

        target.setAppId(appId);
        target.setClientId(cid);

        target.setAlias("1026");
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("��������Ӧ�쳣");
        }
    }

    //透传消息
    public static TransmissionTemplate transmissionTemplateDemo() {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        template.setTransmissionType(1);
        Map<String, Object> mapObject = new HashMap<String, Object>();
        mapObject.put("NOTICE_URL", "http://h5.h1d.com.cn/station/index.html#/member/feedback");
        mapObject.put("NOTICE_TITLE", "透传标题");//标题
        mapObject.put("MSG_DETAIL", "透传内容"); //内容
        JSONObject obj = new JSONObject(mapObject);
        template.setTransmissionContent(obj.toJSONString());

        //特别说明：推送内容，如果后台加入了下面APNS内容，则可以走全局，哪怕应用退出，也可以弹开内容，而这段代码不会影响到ANDROID开发
        {
            APNPayload payload = new APNPayload();
            payload.setBadge(1);
            payload.setContentAvailable(1);
            payload.setSound("default");
            payload.setCategory("action");
            // 添加多媒体资源
            payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.audio)
                    .setResUrl("http://h5.h1d.com.cn/station/index.html#/member/feedback")
                    .setOnlyWifi(true));

            // 字典模式使用下者
            payload.setAlertMsg(getDictionaryAlertMsg());
            template.setAPNInfo(payload);
        }
        return template;
    }
    /**
     * 字典模式使用
     *
     * @return
     */
    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg() {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody("body");
        alertMsg.setActionLocKey("ActionLockey");
        alertMsg.setLocKey("123123213");
        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image");
        // iOS8.2以上版本支持
        alertMsg.setTitle("Title");
        alertMsg.setTitleLocKey("123123");
        alertMsg.addTitleLocArg("TitleL123123ocArg");
        return alertMsg;
    }

}
