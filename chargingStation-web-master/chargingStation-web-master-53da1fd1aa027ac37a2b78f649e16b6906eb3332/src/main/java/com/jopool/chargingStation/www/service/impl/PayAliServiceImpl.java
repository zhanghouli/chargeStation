package com.jopool.chargingStation.www.service.impl;


import com.jopool.chargingStation.www.base.entity.AliConfig;
import com.jopool.chargingStation.www.base.entity.AlipayResponse;
import com.jopool.chargingStation.www.dao.AliConfigMapper;
import com.jopool.chargingStation.www.dao.AlipayResponseMapper;
import com.jopool.chargingStation.www.service.OrderService;
import com.jopool.chargingStation.www.service.PayAliService;

import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class PayAliServiceImpl implements PayAliService, SelfBeanAware<PayAliService> {
    private static final Logger log = LoggerFactory.getLogger(PayAliServiceImpl.class);
    private PayAliService        selfService;
    @Resource
    private AlipayResponseMapper alipayResponseMapper;
    @Resource
    private OrderService         orderService;
    @Resource
    private AliConfigMapper      aliConfigMapper;


    @Override
    public void setSelfBean(PayAliService object) {
        this.selfService = object;
    }

    @Override
    public boolean alipay(AlipayResponse alipayResponse) {
        orderService.updateOrderPaySuccess(alipayResponse, null);
        //
        alipayResponse.setId(UUIDUtils.createId());
        alipayResponse.setCreationTime(new Date());
        alipayResponseMapper.insert(alipayResponse);
        log.debug("pay success : {}", alipayResponse.getOutTradeNo());
        return true;
    }

    @Override
    public List<AliConfig> search(SearchBaseVo searchBaseVo, Pagination page) {
        return aliConfigMapper.searchByCondition(searchBaseVo, page);
    }

    @Override
    public AliConfig getById(String payAliId) {
        return aliConfigMapper.selectByPrimaryKey(payAliId);
    }

    @Override
    public void modify(AliConfig payAliReq) {
        aliConfigMapper.updateByPrimaryKeySelective(payAliReq);
    }

    @Override
    public void add(AliConfig payAliReq) {
        aliConfigMapper.insertSelective(payAliReq);
    }

    @Override
    public void removeById(String payAliId) {
        aliConfigMapper.deleteByPrimaryKey(payAliId);
    }

    @Override
    public AliConfig getByCreationTimeDesc() {
        return aliConfigMapper.selectByCreationTimeDesc();
    }
}