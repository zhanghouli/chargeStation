package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.CmppSmsSend;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmppSmsSendMapper {
    int deleteByPrimaryKey(String id);

    int insert(CmppSmsSend record);

    int insertSelective(CmppSmsSend record);

    CmppSmsSend selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmppSmsSend record);

    int updateByPrimaryKey(CmppSmsSend record);

    /**
     * select by searchBaseVo
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<CmppSmsSend> selectBySearchBaseVo(@Param("searchBaseVo") SearchBaseVo searchBaseVo, RowBounds page);
}