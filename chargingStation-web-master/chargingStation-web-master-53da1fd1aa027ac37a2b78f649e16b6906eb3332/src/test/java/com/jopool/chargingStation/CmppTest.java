package com.jopool.chargingStation;

import com.huawei.insa2.comm.cmpp30.message.CMPP30SubmitMessage;
import com.huawei.insa2.comm.cmpp30.message.CMPP30SubmitRepMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Cfg;
import com.huawei.smproxy.SMProxy30;

import java.io.IOException;

/**
 * Created by gexin on 2017/12/21.
 */
public class CmppTest {
    private static String path = Thread.currentThread().getContextClassLoader().getResource("cmpp.xml").getPath().toString();

    public static void main(String[] args) throws IOException {
        Args args1 = new Cfg(path).getArgs("ismg");
        SMProxy30 smProxy = new SMProxy30(args1); //CMPP3.0的话就是  SMProxy30 smProxy = new SMProxy30(args1);
        String[] dest_Terminal_Id = {"15868474170"};
        byte[] msg_Content = "CMPP测试短信".getBytes("GB2312");
        CMPP30SubmitMessage submitMsg = new CMPP30SubmitMessage(
                1,//@pk_Total 相同msg_Id消息总条数,短短信这里是1
                1,//@pk_Number 相同msg_Id的消息序号
                1,//@registered_Delivery 是否要求返回状态报告
                1,//@msg_Level  信息级别
                "",// @service_Id 业务类型 用户自定义 用来分类查询
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
                "hyd",//@src_Terminal_Id 移动所提供的服务代码  此处需修改
                dest_Terminal_Id,//@dest_Terminal_Id 接收业务的MSISDN号码,就是接收短信的手机号,String数组
                0,//@dest_Terminal_Type
                msg_Content,//@msg_Content 消息内容 byte[],发送的消息内容,需要转化为byte[]
                "" //预留
        );
        CMPP30SubmitRepMessage sub = (CMPP30SubmitRepMessage) smProxy.send(submitMsg);//这里的smProxy就是第2点中用单例创建的smProxy对象
        if (sub.getResult() == 0) {
            //发送成功
            System.out.println("发送成功");
        } else {
            System.out.println("发送失败");
        }
    }
}
