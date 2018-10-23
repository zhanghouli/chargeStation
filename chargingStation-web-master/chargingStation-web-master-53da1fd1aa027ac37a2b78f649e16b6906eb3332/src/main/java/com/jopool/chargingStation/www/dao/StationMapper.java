package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.Station;
import com.jopool.chargingStation.www.vo.StationVo;
import com.jopool.chargingStation.www.vo.common.SearchStationVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationMapper {
    int deleteByPrimaryKey(String id);

    int insert(Station record);

    int insertSelective(Station record);

    Station selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Station record);

    int updateByPrimaryKey(Station record);

    /**
     * 根据number查询
     *
     * @param number
     * @return
     */
    Station selectByNumber(String number);

    /**
     * 分页 查询
     *
     * @param searchStationVo
     * @param page
     * @return
     */
    List<StationVo> selectSearchStationVo(@Param("searchStationVo") SearchStationVo searchStationVo, RowBounds page);

    /**
     * 根据运营id 或者 物业 查询
     *
     * @param operatorId
     * @return
     */
    List<Station> selectByOperatorId(@Param("operatorId") String operatorId, @Param("estateId") String estateId, @Param("city") String city, @Param("keyword") String keyword, @Param("orderBySql") String orderBySql);

    /**
     * 全部电站
     *
     * @param page
     * @return
     */
    List<Station> selectAll(RowBounds page);

    /**
     * 电站 关键字 查询
     *
     * @param id
     * @param keyword
     * @return
     */
    Station selectStationKeyWord(@Param("id") String id, @Param("keyword") String keyword);

    /**
     * 获取 关联套餐 电站数量
     *
     * @param chargeCostPackageId
     * @return
     */
    int selectStationByPackage(@Param("chargeCostPackageId") String chargeCostPackageId);


    /**
     * 获取 电站 数量
     *
     * @param operatorId
     * @param estateId
     * @return
     */
    int selectStationByOperatorIdAndEstateId(@Param("operatorId") String operatorId, @Param("estateId") String estateId);

    /**
     * select station byEstateIdOrOperatorId
     *
     * @param operatorId
     * @param estateId
     * @return
     */
    List<Station> selectStationByEstateIdOrOperatorId(@Param("operatorId") String operatorId, @Param("estateId") String estateId);

}