package com.jopool.chargingStation.www.service.impl;


import com.jopool.chargingStation.www.base.entity.ConsumePackage;
import com.jopool.chargingStation.www.base.entity.Estate;
import com.jopool.chargingStation.www.base.entity.Operator;
import com.jopool.chargingStation.www.base.entity.OpetatorStationKey;
import com.jopool.chargingStation.www.dao.*;
import com.jopool.chargingStation.www.service.OperatorService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.service.StationService;
import com.jopool.chargingStation.www.vo.common.SearchOperatorVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.utils.UUIDUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by synn on 2017/8/27.
 */
@Service
public class OperatorServiceImpl extends BaseServiceImpl implements OperatorService {
    @Resource
    private PassportMapper           passportMapper;
    @Resource
    private OperatorMapper           operatorMapper;
    @Resource
    private OpetatorStationMapper    opetatorStationMapper;
    @Resource
    private ConsumePackageMapper     consumePackageMapper;
    @Resource
    private ConsumePackageItemMapper consumePackageItemMapper;
    @Resource
    private EstateMapper             estateMapper;
    @Resource
    private PassportService          passportService;
    @Resource
    private StationService           stationService;

    @Override
    public Operator getById(String id) {
        return operatorMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addOperator(Operator operator) {
        operator.setId(UUIDUtils.createId());
        operator.setCreationTime(new Date());
        operator.setIsEnabled(true);
        operator.setIsDeleted(false);
        operatorMapper.insert(operator);
    }

    @Override
    public void modifyOperator(Operator operator) {
        operatorMapper.updateByPrimaryKeySelective(operator);
    }

    @Override
    public Result modifyStatus(String operatorId) {
        Operator operator = operatorMapper.selectByPrimaryKey(operatorId);
        if (operator.getIsEnabled()) {
            operator.setIsEnabled(false);
        } else {
            operator.setIsEnabled(true);
        }
        operatorMapper.updateByPrimaryKey(operator);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result removeOperatorById(String passportId) {
        Operator operator = operatorMapper.selectByPassport(passportId);
        // 运营商删除 在判断一下电站
        int stations = stationService.getStationCounts(operator.getId(), null);
        if (stations > 0) {
            return new Result(Code.ERROR, "运营商还有电站存在");
        }
        passportMapper.deleteByPrimaryKey(passportId);
        operatorMapper.deleteByPassportId(passportId);
        List<ConsumePackage> consumePackages = consumePackageMapper.selectOperatorId(operator.getId());
        if (consumePackages.size() > 0) {
            for (ConsumePackage consumePackage : consumePackages) {
                consumePackageMapper.deleteByPrimaryKey(consumePackage.getId());
                consumePackageItemMapper.deletedByConsumePackageId(consumePackage.getId());
            }
        }
        // 物业
        List<Estate> estates = estateMapper.selectByOperatorId(operator.getId());
        if (estates.size() > 0) {
            for (Estate estate : estates) {
                passportService.removePassportId(estate.getPassportId());
                estateMapper.deleteByPassportId(estate.getPassportId());
            }
        }
        return new Result(Code.SUCCESS);
    }


    @Override
    public Operator searchById(String operatorId) {
        return operatorMapper.selectByPrimaryKey(operatorId);
    }

    @Override
    public List<Operator> searchOperatorPage(SearchOperatorVo searchOperatorVo, RowBounds page) {
        return operatorMapper.selectOperatorPage(searchOperatorVo, page);
    }

    @Override
    public void addOperatorStation(OpetatorStationKey stationKey) {
        opetatorStationMapper.insert(stationKey);
    }

    @Override
    public Operator getByPassportId(String passportId) {
        return operatorMapper.selectByPassport(passportId);
    }
}
