package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.ConsumePackage;
import com.jopool.chargingStation.www.vo.ConsumePackageVo;
import com.jopool.chargingStation.www.vo.common.SearchConsumePackageVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumePackageMapper {
    int deleteByPrimaryKey(String id);

    int insert(ConsumePackage record);

    int insertSelective(ConsumePackage record);

    ConsumePackage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ConsumePackage record);

    int updateByPrimaryKey(ConsumePackage record);

    /**
     * 套餐 名称  分页 查询
     *
     * @param searchConsumePackageVo
     * @param page
     * @return
     */
    List<ConsumePackageVo> selectConsumePackageVo(SearchConsumePackageVo searchConsumePackageVo, RowBounds page);

    /**
     * 运营商 id 查找
     *
     * @param operatorId
     * @return
     */
    List<ConsumePackage> selectOperatorId(String operatorId);

    /**
     * 运营商 删除  关联 删除
     *
     * @param operatorId
     * @return
     */
    int deleteByOperatorId(String operatorId);

    /**
     * 运营商 id 分页 查询
     * @param operatorId
     * @param searchConsumePackageVo
     * @param page
     * @return
     */
    List<ConsumePackage> selectByOperatorId(@Param("operatorId") String operatorId, @Param("searchConsumePackageVo")SearchConsumePackageVo searchConsumePackageVo, RowBounds page);
}