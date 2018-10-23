package com.jopool.chargingStation.www.cmpp;

import com.huawei.insa2.comm.cmpp30.message.CMPP30DeliverMessage;
import com.huawei.insa2.comm.cmpp30.message.CMPP30SubmitMessage;
import com.huawei.insa2.comm.cmpp30.message.CMPP30SubmitRepMessage;
import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;
import com.jopool.chargingStation.www.service.CmppProxyService;
import com.jopool.jweb.enums.ModeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * mqtt服务，用户订阅消息，发布消息
 * Created by gexin on 2017/9/9.
 */
public class CmppService {
    private static final Logger log = LoggerFactory.getLogger(CmppService.class);
    private CustomSMProxy    smProxy;
    @Resource
    private CmppProxyService cmppProxyService;

    @PostConstruct
    public void init() throws Exception {
//        if (ApplicationConfigHelper.getMode() == ModeEnum.RELEASE) {
//            String path = Thread.currentThread().getContextClassLoader().getResource("cmpp.xml").getPath().toString();
//            Args args1 = new Cfg(path).getArgs("ismg");
//            smProxy = new CustomSMProxy(args1, new OnDeliverListener() {
//                @Override
//                public void onDeliver(CMPP30DeliverMessage msg) {
//                    cmppProxyService.onReceive(msg);
//                }
//            });
//        }
    }

    @PreDestroy
    public void destory() {
    }

    /**
     * 发布消息
     *
     * @param phone
     */
    public boolean send(String phone, String content) {
        if (ApplicationConfigHelper.getMode() != ModeEnum.RELEASE) {
            return false;
        }
        try {
            String[] dest_Terminal_Id = {phone};
            byte[] msg_Content = new byte[0];
            msg_Content = content.getBytes("GB2312");
            CMPP30SubmitMessage submitMsg = new CMPP30SubmitMessage(
                    1,//@pk_Total 相同msg_Id消息总条数,短短信这里是1
                    1,//@pk_Number 相同msg_Id的消息序号
                    1,//@registered_Delivery 是否要求返回状态报告
                    1,//@msg_Level  信息级别
                    "hyd",// @service_Id 业务类型 用户自定义 用来分类查询
                    2,//@fee_UserType 0对目的终端计费；1对源终端计费；2对SP计费;
                    "",//@fee_Terminal_Id 被计费用户的号码
                    2,//@fee_Terminal_Type 0对目的终端计费；1对源终端计费；2对SP计费;
                    0,//@tp_Pid GSM协议类型 一般文本的时候设0,铃声图片设1
                    0,//@tp_Udhi GSM协议类型 0不包含头部信息 1包含头部信息
                    15,//@msg_Fmt 消息格式
                    "110329",//@msg_Src 消息内容来源 6位的企业代码,这里需修改
                    "02",// @fee_Type 资费类别 一般为02：按条计信息费
                    "0",//@fee_Code 资费代码(以分为单位)
                    null,//@valid_Time 存活有效期
                    null,//@at_Time 定时发送时间
                    "1064899110329",//@src_Terminal_Id 移动所提供的服务代码  此处需修改
                    dest_Terminal_Id,//@dest_Terminal_Id 接收业务的MSISDN号码,就是接收短信的手机号,String数组
                    0,//@dest_Terminal_Type
                    msg_Content,//@msg_Content 消息内容 byte[],发送的消息内容,需要转化为byte[]
                    "" //预留
            );
            CMPP30SubmitRepMessage sub = (CMPP30SubmitRepMessage) smProxy.send(submitMsg);//这里的smProxy就是第2点中用单例创建的smProxy对象
            if (sub.getResult() == 0) {
                //发送成功
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 接受消息回调
     */
    public interface OnDeliverListener {
        void onDeliver(CMPP30DeliverMessage msg);
    }
}
