package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.entity.Estate;
import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.chargingStation.www.base.entity.Station;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.dao.EstateMapper;
import com.jopool.chargingStation.www.enums.PassportStatusEnum;
import com.jopool.chargingStation.www.service.EstateService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.service.StationService;
import com.jopool.chargingStation.www.vo.EstateVo;
import com.jopool.chargingStation.www.vo.common.SearchEstateVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by synn on 2017/8/28.
 */
@Service
public class EstateServiceImpl extends BaseServiceImpl implements EstateService {

    @Resource
    private PassportService passportService;
    @Resource
    private EstateMapper    estateMapper;
    @Resource
    private StationService  stationService;

    @Override
    public void addEstate(Estate estate) {
        estateMapper.insert(estate);
    }

    @Override
    public Result modifyEstate(Estate estate) {
        estateMapper.updateByPrimaryKeySelective(estate);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Estate searchEstateById(String estateId) {
        return estateMapper.selectByPrimaryKey(estateId);
    }

    @Override
    public Estate getByPhone(String phone) {
        return estateMapper.selectByPhone(phone);
    }

    @Override
    public Result removeEstateById(String passportId) {
        Estate estate = estateMapper.selectByPassportId(passportId);
        passportService.removePassportId(passportId);
        estateMapper.deleteByPassportId(passportId);
        //物业 删除  关联 电站 删除
        List<Station> stations = stationService.getStationByEstateIdOrOperatorId(null, estate.getId());
        stationService.removeStationOrStationPort(stations);
        return new Result(Code.SUCCESS);
    }

    @Override
    public List<EstateVo> searchEstateVo(SearchEstateVo searchEstateVo, RowBounds page) {
        List<EstateVo> estateVos = new ArrayList<EstateVo>();
        List<Estate> estates = estateMapper.selectEstateVo(searchEstateVo, page);
        for (Estate estate : estates) {
            EstateVo estateVo = new EstateVo(estate);
            Passport passport = passportService.getById(estate.getPassportId());
            estateVo.setRealName(passport.getRealName());
            estateVos.add(estateVo);
        }
        return estateVos;
    }

    @Override
    public Result modifyStatus(String estateId) {
        Passport passport = passportService.getById(estateId);
        if (passport == null) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        if (PassportStatusEnum.NORMAL.getValue().equals(passport.getStatus())) {
            passport.setStatus(PassportStatusEnum.DISABLE.getValue());
        } else {
            passport.setStatus(PassportStatusEnum.NORMAL.getValue());
        }
        passportService.modify(passport);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Estate getByPassportId(String passportId) {
        return estateMapper.selectByPassportId(passportId);
    }

    @Override
    public Estate getById(String id) {
        return estateMapper.selectByPrimaryKey(id);
    }
}
