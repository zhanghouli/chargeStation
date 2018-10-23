package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.Operator;
import com.jopool.chargingStation.www.base.entity.OpetatorStationKey;
import com.jopool.chargingStation.www.vo.common.SearchOperatorVo;
import com.jopool.jweb.entity.Result;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by synn on 2017/8/27.
 */

public interface OperatorService {

    /**
     * id 查找
     * @param id
     * @return
     */
    Operator getById(String id);
    /**
     * 添加运营商
     * @param operator
     * @return
     */
    void addOperator(Operator operator);

    /**
     * 修改
     * @param operator
     */
    void modifyOperator(Operator operator);

    /**
     * 状态修改
     * @param operatorId
     * @return
     */
    Result modifyStatus(String operatorId);

    /**
     * 按照Id删除
     * @param passportId
     * @return
     */
    Result removeOperatorById(String passportId);

    /**
     * 按照Id 查找运营商
     * @param operatorId
     * @return
     */
    Operator searchById(String operatorId);

    /**
     * 分页查询
     * @param searchOperatorVo
     * @param page
     * @return
     */
    List<Operator>  searchOperatorPage(SearchOperatorVo searchOperatorVo, RowBounds page);

    /**
     * 运营商 关联 电站
     * @param stationKey
     */
    void addOperatorStation(OpetatorStationKey stationKey);

    /**
     * passportId 查找 运营商 地址
     * @param passportId
     * @return
     */
    Operator getByPassportId(String passportId);
}
