package com.jopool.chargingStation.www.service;

import com.huawei.insa2.comm.cmpp30.message.CMPP30DeliverMessage;
import com.jopool.chargingStation.www.base.entity.CmppSmsReceive;
import com.jopool.chargingStation.www.base.entity.CmppSmsSend;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by gexin on 2017/10/24.
 */
public interface CmppProxyService {
    /**
     * 发消息
     *
     * @param phone
     * @param content
     */
    boolean send(String phone, String content);

    /**
     * 发送消息 的列表
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<CmppSmsSend> searchCmppSmsSendList(SearchBaseVo searchBaseVo, RowBounds page);


    /**
     * remove  cmppSmsSendId by id
     *
     * @param cmppSmsSendId
     */
    void removeCmppSmsSendById(String cmppSmsSendId);

    /**
     * 收消息
     *
     * @param msg
     */
    void onReceive(CMPP30DeliverMessage msg);

    /**
     * 接收消息 的列表
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<CmppSmsReceive> searchCmppSmsReceiveList(SearchBaseVo searchBaseVo, RowBounds page);


    /**
     * remove cmppSmsReceive by id
     *
     * @param cmppSmsReceiveId
     */
    void removeCmppSmsReceiveById(String cmppSmsReceiveId);
}
