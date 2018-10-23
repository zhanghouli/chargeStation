package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.RechargePackage;
import com.jopool.chargingStation.www.vo.common.SearchRechargePackageVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RechargePackageMapper {
    int deleteByPrimaryKey(String id);

    int insert(RechargePackage record);

    int insertSelective(RechargePackage record);

    RechargePackage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RechargePackage record);

    int updateByPrimaryKey(RechargePackage record);

    /**
     * 充值 套餐 分页 查询
     * @param searchRechargePackageVo
     * @param page
     * @return
     */
    List<RechargePackage> selectRechargePackageVo(SearchRechargePackageVo searchRechargePackageVo, RowBounds page);

    /**
     * 套餐
     *
     * @return
     */
    List<RechargePackage> selectAll();
}