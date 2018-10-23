package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.entity.RechargePackage;
import com.jopool.chargingStation.www.base.entity.RechargePackageSnapshot;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.dao.RechargePackageMapper;
import com.jopool.chargingStation.www.dao.RechargePackageSnapshotMapper;
import com.jopool.chargingStation.www.service.RechargePackageService;
import com.jopool.chargingStation.www.vo.common.SearchRechargePackageVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.UUIDUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package Name : com.jopool.chargingStation.www.service.impl
 * @Author : soupcat
 * @Creation Date : 2017/8/29 下午6:59
 */
@Service
public class RechargePackageServiceImpl extends BaseServiceImpl implements RechargePackageService, SelfBeanAware<RechargePackageService> {
    private RechargePackageService        selfService;
    @Resource
    private RechargePackageMapper         rechargePackageMapper;
    @Resource
    private RechargePackageSnapshotMapper rechargePackageSnapshotMapper;

    @Override
    public void setSelfBean(RechargePackageService object) {
        this.selfService = object;
    }

    @Override
    public RechargePackage getById(String rechargePackageId) {
        return rechargePackageMapper.selectByPrimaryKey(rechargePackageId);
    }

    @Override
    public void add(RechargePackage rechargePackage) {
        rechargePackageMapper.insert(rechargePackage);
    }

    @Override
    public Result modifyRechargePackageIsEnabled(String rechargePackageId) {
        RechargePackage rechargePackage = rechargePackageMapper.selectByPrimaryKey(rechargePackageId);
        if (rechargePackage == null) {
            return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
        }
        if (rechargePackage.getIsEnabled()) {
            rechargePackage.setIsEnabled(false);
        } else {
            rechargePackage.setIsEnabled(true);
        }
        rechargePackageMapper.updateByPrimaryKeySelective(rechargePackage);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result modifyRechargePackage(RechargePackage rechargePackage) {
        rechargePackageMapper.updateByPrimaryKey(rechargePackage);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result removeRechargePackageId(String rechargePackageId) {
        rechargePackageMapper.deleteByPrimaryKey(rechargePackageId);
        return new Result(Code.SUCCESS);
    }

    @Override
    public List<RechargePackage> searchRechargePackageVo(SearchRechargePackageVo searchRechargePackageVo, RowBounds page) {
        return rechargePackageMapper.selectRechargePackageVo(searchRechargePackageVo, page);
    }

    @Override
    public RechargePackageSnapshot addRechargePackageSnapshot(RechargePackage rechargePackage, int rechargeAmount) {
        RechargePackageSnapshot rechargePackageSnapshot = new RechargePackageSnapshot();
        rechargePackageSnapshot.setId(UUIDUtils.createId());
        if (rechargePackage != null) {
            rechargePackageSnapshot.setRchargePackageId(rechargePackage.getId());
            rechargePackageSnapshot.setPayment(rechargeAmount);
            rechargePackageSnapshot.setAmount(rechargePackage.getAmount());
        } else {
            rechargePackageSnapshot.setPayment(rechargeAmount);
            rechargePackageSnapshot.setAmount(rechargeAmount);
        }
        rechargePackageSnapshot.setCreator(Constants.SYSTEM_ID);
        rechargePackageSnapshotMapper.insertSelective(rechargePackageSnapshot);
        return rechargePackageSnapshot;
    }

}
