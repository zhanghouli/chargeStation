package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.entity.WxConfig;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.dao.WxConfigMapper;
import com.jopool.chargingStation.www.service.PayWxService;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PayWxServiceImpl implements PayWxService, SelfBeanAware<PayWxService> {
    private PayWxService   selfService;
    @Resource
    private WxConfigMapper wxConfigMapper;


    @Override
    public void setSelfBean(PayWxService object) {
        this.selfService = object;
    }

    @Override
    public List<WxConfig> search(SearchBaseVo searchBaseVo, RowBounds page) {
        return wxConfigMapper.searchByCondition(searchBaseVo, page);
    }

    @Override
    public WxConfig getById(String id) {
        return wxConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public int add(WxConfig payWx) {
        WxConfig wxConfig=new WxConfig();
        wxConfig.setId(UUIDUtils.createId());
        wxConfig.setPlatformId(payWx.getPlatformId());
        wxConfig.setPlatform(payWx.getPlatform());
        wxConfig.setPayKey(payWx.getPayKey());
        wxConfig.setVeryfy(payWx.getVeryfy());
        wxConfig.setCert(payWx.getCert());
        wxConfig.setPayAppid(payWx.getPayAppid());
        wxConfig.setPaySecret(payWx.getPaySecret());
        wxConfig.setMchId(payWx.getMchId());
        wxConfig.setCertLocalPath(payWx.getCertLocalPath());
        wxConfig.setCertPassword(payWx.getCertPassword());
        wxConfig.setNotifyUrl(payWx.getNotifyUrl());
        wxConfig.setSubMchId(payWx.getSubMchId());
        wxConfig.setIp(payWx.getIp());
        wxConfig.setIsDeleted(false);
        wxConfig.setCreator(wxConfig.getCreator());
        wxConfig.setCreationTime(new Date());
        return wxConfigMapper.insert(wxConfig);
    }

    @Override
    public Result modify(WxConfig payWx) {
        WxConfig wxConfig=wxConfigMapper.selectByPrimaryKey(payWx.getId());
        if(wxConfig==null){
            return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
        }
        wxConfig.setPlatformId(payWx.getPlatformId());
        wxConfig.setPlatform(payWx.getPlatform());
        wxConfig.setPayKey(payWx.getPayKey());
        wxConfig.setVeryfy(!StringUtils.isEmpty(payWx.getVeryfy())?payWx.getVeryfy():wxConfig.getVeryfy());
        wxConfig.setCert(!StringUtils.isEmpty(payWx.getCert())?payWx.getCert():wxConfig.getCert());
        wxConfig.setPayAppid(payWx.getPayAppid());
        wxConfig.setPaySecret(payWx.getPaySecret());
        wxConfig.setMchId(payWx.getMchId());
        wxConfig.setCertLocalPath(payWx.getCertLocalPath());
        wxConfig.setCertPassword(payWx.getCertPassword());
        wxConfig.setNotifyUrl(payWx.getNotifyUrl());
        wxConfig.setSubMchId(payWx.getSubMchId());
        wxConfig.setIp(payWx.getIp());
        wxConfig.setModifyTime(new Date());
        wxConfigMapper.updateByPrimaryKeySelective(wxConfig);
        return new Result(Code.SUCCESS);
    }

    @Override
    public void removeById(String id) {
        wxConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public WxConfig getByShopIdPlatform(String platformId, String platform) {
        return wxConfigMapper.selectByPlatformIdAndPlatForm(platformId, platform);
    }

}