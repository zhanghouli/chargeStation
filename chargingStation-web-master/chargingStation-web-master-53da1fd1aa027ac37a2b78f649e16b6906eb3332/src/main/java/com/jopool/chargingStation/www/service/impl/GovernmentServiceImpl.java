package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.entity.Government;
import com.jopool.chargingStation.www.dao.GovernmentMapper;
import com.jopool.chargingStation.www.service.GovernmentService;
import com.jopool.jweb.utils.UUIDUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by synn on 2017/10/24.
 */
@Service
public class GovernmentServiceImpl implements GovernmentService {

    @Resource
    private GovernmentMapper governmentMapper;

    @Override
    public void addGovernment(String passportId, Government government) {
        government.setPassportId(passportId);
        government.setId(UUIDUtils.createId());
        government.setCreationTime(new Date());
        government.setIsEnabled(true);
        government.setIsDeleted(false);
        governmentMapper.insert(government);
    }

    @Override
    public Government getById(String governmentId) {
        return governmentMapper.selectByPrimaryKey(governmentId);
    }

    @Override
    public Government getByPassportId(String passportId) {
        return governmentMapper.selectPassportId(passportId);
    }

    @Override
    public void modifyGovernment(Government government) {
        governmentMapper.updateByPrimaryKeySelective(government);
    }

    @Override
    public void removeGovernmentId(String governmentId) {
        governmentMapper.deleteByPrimaryKey(governmentId);
    }

}
