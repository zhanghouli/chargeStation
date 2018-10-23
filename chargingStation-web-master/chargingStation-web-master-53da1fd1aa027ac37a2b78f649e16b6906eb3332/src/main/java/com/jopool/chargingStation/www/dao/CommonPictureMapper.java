package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.CommonPicture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonPictureMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommonPicture record);

    int insertSelective(CommonPicture record);

    CommonPicture selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommonPicture record);

    int updateByPrimaryKey(CommonPicture record);

    /**
     * 根据hostId获取图片
     *
     * @param hostId
     * @return
     */
    CommonPicture selectByHostIdOrType(@Param("hostId") String hostId, @Param("type") String type);

    /**
     * 关联 id 删除
     *
     * @param hostId
     * @return
     */
    int deletedByHostId(String hostId);
}