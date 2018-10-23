package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.ConsumePackageItem;
import com.jopool.chargingStation.www.vo.ConsumePackageItemVo;
import com.jopool.chargingStation.www.vo.common.SearchConsumePackageItemVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConsumePackageItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(ConsumePackageItem record);

    int insertSelective(ConsumePackageItem record);

    ConsumePackageItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ConsumePackageItem record);

    int updateByPrimaryKey(ConsumePackageItem record);

    /**
     * 分页 查询
     * @param searchConsumePackageItemVo
     * @param page
     * @return
     */
    List<ConsumePackageItem> selectConsumePackageItemVo(@Param("consumePackageId")String consumePackageId, @Param("searchConsumePackageItemVo")SearchConsumePackageItemVo searchConsumePackageItemVo, RowBounds page);

    /**
     * 根据套餐id查找
     *
     * @param consumePackageId
     * @return
     */
    List<ConsumePackageItem> selectByConsumePackageId(String consumePackageId);


    /**
     * 套餐名称删除
     *
     * @param consumePackageId
     * @return
     */
    int deletedByConsumePackageId(String consumePackageId);
}