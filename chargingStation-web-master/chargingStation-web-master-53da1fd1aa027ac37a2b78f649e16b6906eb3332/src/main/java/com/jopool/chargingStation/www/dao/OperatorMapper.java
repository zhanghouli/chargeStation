package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.Operator;
import com.jopool.chargingStation.www.vo.common.SearchOperatorVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorMapper {
    int deleteByPrimaryKey(String id);

    int insert(Operator record);

    int insertSelective(Operator record);

    Operator selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Operator record);

    int updateByPrimaryKey(Operator record);

    /**
     * 分页 查询
     * @param searchOperatorVo
     * @param page
     * @return
     */
    List<Operator> selectOperatorPage(SearchOperatorVo searchOperatorVo, RowBounds page);

    /**
     *passportId  查找 运营商 地址
     * @param passportId
     * @return
     */
    Operator selectByPassport(String passportId);

    /**
     * select by passportId
     *
     * @param passportId
     * @return
     */
    Operator selectByPassportId(String passportId);

    /**
     * passportId 关联删除
     * @param passportId
     * @return
     */
    int deleteByPassportId(String passportId);
}