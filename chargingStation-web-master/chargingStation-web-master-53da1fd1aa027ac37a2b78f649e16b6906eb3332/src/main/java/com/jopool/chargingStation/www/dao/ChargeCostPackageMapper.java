package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.ChargeCostPackage;
import com.jopool.chargingStation.www.vo.common.SearchRechargePackageVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeCostPackageMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChargeCostPackage record);

    int insertSelective(ChargeCostPackage record);

    ChargeCostPackage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChargeCostPackage record);

    int updateByPrimaryKey(ChargeCostPackage record);

    /**
     * search
     *
     * @param searchRechargePackageVo
     * @param page
     * @return
     */
    List<ChargeCostPackage> search(SearchRechargePackageVo searchRechargePackageVo, RowBounds page);

    /**
     * select list 4
     *
     * @return
     */
    List<ChargeCostPackage> selectChargeCostPackage();

    /**
     * select by pow
     *
     * @param pow
     * @return
     */
    ChargeCostPackage selectByPow(String pow);
}