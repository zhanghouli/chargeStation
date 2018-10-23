package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.Government;

/**
 * Created by synn on 2017/10/24.
 */
public interface GovernmentService {

    /**
     * 政府 人员 添加
     *
     * @param passportId
     * @param government
     */
    void addGovernment(String passportId, Government government);

    /**
     * id  查找
     *
     * @param governmentId
     * @return
     */
    Government getById(String governmentId);


    /**
     * 登录 账号Id  查找政府
     * @param passportId
     * @return
     */
    Government getByPassportId(String passportId);


    /**
     * 修改 政府
     *
     * @param government
     */
    void modifyGovernment(Government government);

    /**
     * 删除 政府
     *
     * @param governmentId
     */
    void removeGovernmentId(String governmentId);

}
