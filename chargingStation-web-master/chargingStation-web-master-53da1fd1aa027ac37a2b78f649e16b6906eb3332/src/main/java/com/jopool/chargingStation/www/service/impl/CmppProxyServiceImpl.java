package com.jopool.chargingStation.www.service.impl;

import com.huawei.insa2.comm.cmpp30.message.CMPP30DeliverMessage;
import com.jopool.chargingStation.www.base.entity.CmppSmsReceive;
import com.jopool.chargingStation.www.base.entity.CmppSmsSend;
import com.jopool.chargingStation.www.cmpp.CmppService;
import com.jopool.chargingStation.www.dao.CmppSmsReceiveMapper;
import com.jopool.chargingStation.www.dao.CmppSmsSendMapper;
import com.jopool.chargingStation.www.service.CmppProxyService;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.UUIDUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by gexin on 2017/10/24.
 */
@Service
public class CmppProxyServiceImpl extends BaseMsgProcessService implements CmppProxyService, SelfBeanAware<CmppProxyService> {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private CmppProxyService     cmppProxyService;

    @Resource
    private CmppService          cmppService;
    @Resource
    private CmppSmsSendMapper    cmppSmsSendMapper;
    @Resource
    private CmppSmsReceiveMapper cmppSmsReceiveMapper;

    @Override
    public boolean send(String phone, String content) {
        boolean result = cmppService.send(phone, content);
        log.info("send msg:{},{}", phone, content);
        //保存到cmpp_sms_send[phone,content,result]中
        CmppSmsSend cmppSmsSend = new CmppSmsSend();
        cmppSmsSend.setId(UUIDUtils.createId());
        cmppSmsSend.setPhone(phone);
        cmppSmsSend.setContent(content);
        cmppSmsSend.setResult(result);
        cmppSmsSend.setCreationTime(new Date());
        cmppSmsSend.setIsDeleted(false);
        cmppSmsSendMapper.insert(cmppSmsSend);
        return result;
    }

    @Override
    public List<CmppSmsSend> searchCmppSmsSendList(SearchBaseVo searchBaseVo, RowBounds page) {
        return cmppSmsSendMapper.selectBySearchBaseVo(searchBaseVo, page);
    }

    @Override
    public void removeCmppSmsSendById(String cmppSmsSendId) {
        cmppSmsSendMapper.deleteByPrimaryKey(cmppSmsSendId);
    }

    @Override
    public void onReceive(CMPP30DeliverMessage deliverMsg) {
        String phone = null, content = null;
        if (deliverMsg.getRegisteredDeliver() == 0) {
            try {
                phone = deliverMsg.getSrcterminalId();
                if (deliverMsg.getMsgFmt() == 8) {
                    content = new String(deliverMsg.getMsgContent(), "UTF-16BE");
                } else {
                    content = new String(deliverMsg.getMsgContent());
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }
        log.info("receive msg:{},{}", phone, content);
        //保存到cmpp_sms_receive[phone,content]中
        CmppSmsReceive cmppSmsReceive = new CmppSmsReceive();
        cmppSmsReceive.setId(UUIDUtils.createId());
        cmppSmsReceive.setPhone(phone);
        cmppSmsReceive.setContent(content);
        cmppSmsReceive.setIsDeleted(false);
        cmppSmsReceive.setCreationTime(new Date());
        cmppSmsReceiveMapper.insert(cmppSmsReceive);
    }

    @Override
    public List<CmppSmsReceive> searchCmppSmsReceiveList(SearchBaseVo searchBaseVo, RowBounds page) {
        return cmppSmsReceiveMapper.selectBySearchBaseVo(searchBaseVo, page);
    }

    @Override
    public void removeCmppSmsReceiveById(String cmppSmsReceiveId) {
        cmppSmsReceiveMapper.deleteByPrimaryKey(cmppSmsReceiveId);
    }

    @Override
    public void setSelfBean(CmppProxyService object) {
        this.cmppProxyService = object;
    }
}
