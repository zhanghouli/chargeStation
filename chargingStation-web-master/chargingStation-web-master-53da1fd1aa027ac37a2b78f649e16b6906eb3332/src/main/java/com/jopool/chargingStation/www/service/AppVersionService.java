package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.AppVersion;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by gexin on 16/7/26.
 */
public interface AppVersionService {
    AppVersion getByAppIdAndOS(String appId, String os);

    /**
     * get all
     *
     * @param page
     * @return
     */
    List<AppVersion> getAll(RowBounds page);

    /**
     * get by id
     *
     * @param id
     * @return
     */
    AppVersion getById(String id);

    /**
     * add
     *
     * @param appVersion
     */
    void add(AppVersion appVersion);

    /**
     * modify
     *
     * @param appVersion
     */
    void modify(AppVersion appVersion);

    /**
     * remove
     *
     * @param id
     */
    void removeById(String id);
}
