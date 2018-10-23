package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.entity.AppVersion;
import com.jopool.chargingStation.www.dao.AppVersionMapper;
import com.jopool.chargingStation.www.service.AppVersionService;
import com.jopool.jweb.cache.annotation.CacheFlush;
import com.jopool.jweb.cache.annotation.Cacheable;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.UUIDUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by gexin on 16/7/26.
 */
@Service
public class AppVersionServiceImpl extends BaseServiceImpl implements AppVersionService, SelfBeanAware<AppVersionService> {
    private AppVersionService selfService;
    @Resource
    private AppVersionMapper  appVersionMapper;


    @Override
    public AppVersion getByAppIdAndOS(String appId, String os) {
        return appVersionMapper.selectByAppIdAndOS(appId, os);
    }

    @Override
    public List<AppVersion> getAll(RowBounds page) {
        return appVersionMapper.selectAll(page);
    }

    @Override
    @Cacheable
    public AppVersion getById(String id) {
        return appVersionMapper.selectByPrimaryKey(id);
    }

    @Override
    @CacheFlush
    public void add(AppVersion appVersion) {
        appVersion.setId(UUIDUtils.createId());
        appVersion.setIsDeleted(false);
        appVersion.setCreationTime(new Date());
        appVersionMapper.insert(appVersion);
    }

    @Override
    @CacheFlush
    public void modify(AppVersion appVersion) {
        appVersionMapper.updateByPrimaryKeySelective(appVersion);
    }

    @Override
    @CacheFlush
    public void removeById(String id) {
        appVersionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void setSelfBean(AppVersionService appVersionService) {
        this.selfService = appVersionService;
    }
}
