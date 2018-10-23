package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.Carowner;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Repository
public interface CarownerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Carowner record);

    int insertSelective(Carowner record);

    Carowner selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Carowner record);

    int updateByPrimaryKey(Carowner record);

    /**
     * 通过passportId 获取车主信息
     *
     * @param passportId
     * @return
     */
    Carowner selectByPassportId(String passportId);

    /**
     * passportId 删除
     *
     * @param passportId
     * @return
     */
    int deleteByPassportId(String passportId);

    /**
     * 根据设备编号获取
     *
     * @param deviceNumber
     * @return
     */
    Carowner selectByDeviceNumber(String deviceNumber);

    /**
     * 根据政府id获取车辆
     *
     * @param governmentId
     * @return
     */
    List<Carowner> selectByGovernmentId(String governmentId);
}