package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.Estate;
import com.jopool.chargingStation.www.vo.common.SearchEstateVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstateMapper {
    int deleteByPrimaryKey(String id);

    int insert(Estate record);

    int insertSelective(Estate record);

    Estate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Estate record);

    int updateByPrimaryKey(Estate record);

    /**
     * 物业 分页 查询
     *
     * @param searchEstateVo
     * @param page
     * @return
     */
    List<Estate> selectEstateVo(SearchEstateVo searchEstateVo, RowBounds page);

    /**
     * 手机号 查询
     *
     * @param phone
     * @return
     */
    Estate selectByPhone(@Param("phone") String phone);

    /**
     * 根据passportId 查询
     *
     * @param passportId
     * @return
     */
    Estate selectByPassportId(String passportId);


    /**
     * 按照 passportId  删除
     *
     * @param passportId
     * @return
     */
    int deleteByPassportId(String passportId);

    /**
     * 运营商 获取 物业数量
     *
     * @param operatorId
     * @return
     */
    List<Estate> selectByOperatorId(@Param("operatorId") String operatorId);


    /**
     * 获取所有物业
     * @return
     */
    List<Estate> selectGetEstateAll();
}