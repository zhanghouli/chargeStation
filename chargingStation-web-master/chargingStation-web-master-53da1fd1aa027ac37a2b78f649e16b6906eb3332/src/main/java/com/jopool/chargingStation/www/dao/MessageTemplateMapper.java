package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.MessageTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageTemplateMapper {
    int deleteByPrimaryKey(String id);

    int insert(MessageTemplate record);

    int insertSelective(MessageTemplate record);

    MessageTemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MessageTemplate record);

    int updateByPrimaryKey(MessageTemplate record);

    /**
     * 分页  查找
     *
     * @param type
     * @param page
     * @return
     */
    List<MessageTemplate> selectMessageTemplate(@Param("type") String type, RowBounds page);

    /**
     * 类型 查找
     *
     * @param type
     * @return
     */
    MessageTemplate selectMessageType(@Param("type") String type);
}