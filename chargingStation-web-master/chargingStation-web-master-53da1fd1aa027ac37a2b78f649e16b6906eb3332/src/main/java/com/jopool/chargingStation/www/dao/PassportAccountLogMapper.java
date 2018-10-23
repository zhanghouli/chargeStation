package com.jopool.chargingStation.www.dao;


import com.jopool.chargingStation.www.base.entity.PassportAccountLog;
import com.jopool.chargingStation.www.vo.PassportAccountLogVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassportAccountLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(PassportAccountLog record);

    int insertSelective(PassportAccountLog record);

    PassportAccountLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PassportAccountLog record);

    int updateByPrimaryKey(PassportAccountLog record);

    /**
     * 根据passportId查询
     *
     * @param passportId
     * @param dateParam
     * @param type
     * @param page
     * @return
     */
    List<PassportAccountLog> searchByPassportId(@Param("passportId") String passportId, @Param("dateParam") DateParam dateParam, @Param("type") String type, RowBounds page);

    /**
     * 查询今日记录
     *
     * @param passportId
     * @param today
     * @return
     */
    Integer selectByToday(@Param("passportId") String passportId, @Param("today") String today);

    /**
     * 查询月记录
     *
     * @param passportId
     * @param month
     * @return
     */
    Integer selectByMonth(@Param("passportId") String passportId, @Param("month") String month);

    /**
     * 流水日志 状态 查询
     *
     * @param type
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<PassportAccountLogVo> selectAccountTypeAll(@Param("dateParam") DateParam dateParam, @Param("type") String type, @Param("searchBaseVo") SearchBaseVo searchBaseVo, RowBounds page);

    /**
     * 类型 汇总
     *
     * @param type
     * @param dateParam
     * @return
     */
    Integer selectTypeDateSumAmount(@Param("type") String type, @Param("dateParam") DateParam dateParam);

    /**
     * @param types
     * @param dateParam
     * @param passportId
     * @return
     */
    Integer selectTypeDateSumAmountOperatorOrEstate(@Param("types") String[] types, @Param("dateParam") DateParam dateParam, @Param("passportId") String passportId);
}