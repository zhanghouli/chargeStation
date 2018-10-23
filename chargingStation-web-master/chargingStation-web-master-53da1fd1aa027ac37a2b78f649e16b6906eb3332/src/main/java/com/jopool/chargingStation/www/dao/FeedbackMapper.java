package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.Feedback;
import com.jopool.chargingStation.www.vo.FeedbackVo;
import com.jopool.chargingStation.www.vo.common.SearchFeedBackVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackMapper {
    int deleteByPrimaryKey(String id);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKeyWithBLOBs(Feedback record);

    int updateByPrimaryKey(Feedback record);

    /**
     * 分页 查询
     * @param searchFeedBackVo
     * @param page
     * @return
     */
    List<FeedbackVo> selectSearchFeedBackVo(SearchFeedBackVo searchFeedBackVo, RowBounds page);
}