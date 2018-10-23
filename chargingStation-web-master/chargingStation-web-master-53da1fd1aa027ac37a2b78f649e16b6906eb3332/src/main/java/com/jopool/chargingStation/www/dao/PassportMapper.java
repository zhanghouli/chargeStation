package com.jopool.chargingStation.www.dao;

import com.jopool.chargingStation.www.base.entity.Passport;
import com.jopool.chargingStation.www.vo.PassportOrEstateVo;
import com.jopool.chargingStation.www.vo.PassportOrGovernmentVo;
import com.jopool.chargingStation.www.vo.PassportOrOperatorVo;
import com.jopool.chargingStation.www.vo.common.SearchPassportVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassportMapper {
    int deleteByPrimaryKey(String id);

    int insert(Passport record);

    int insertSelective(Passport record);

    Passport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Passport record);

    int updateByPrimaryKey(Passport record);

    /**
     * 登录验证查询
     *
     * @param userName
     * @param status
     * @param types
     * @return
     */
    List<Passport> selectByUserNameAndTypes(@Param("userName") String userName, @Param("status") String status, @Param("types") String[] types);

    /**
     * 运营商 物业 手机 不重复
     *
     * @param phone
     * @param types
     * @return
     */
    List<Passport> selectByPhoneAndTypes(@Param("phone") String phone, @Param("types") String[] types);

    /**
     * 手机号 用户状态
     *
     * @param phone
     * @param type
     * @return
     */
    Passport selectByPhoneAndType(@Param("phone") String phone, @Param("type") String type);

    /**
     * 登录名 判断
     *
     * @param userName
     * @param types
     * @return
     */
    Passport selectByUserNameAndType(@Param("userName") String userName, @Param("types") String[] types);

    /**
     * 分页查询
     *
     * @param type
     * @param passportVo
     * @param page
     * @return
     */
    List<Passport> selectPassportVo(@Param("type") String type, @Param("passportVo") SearchPassportVo passportVo, RowBounds page);


    /**
     * 政府 多表查询
     *
     * @param type
     * @param passportVo
     * @param page
     * @return
     */
    List<PassportOrGovernmentVo> selectPassportGovernmentVo(@Param("type") String type, @Param("passportVo") SearchPassportVo passportVo, RowBounds page);

    /**
     * 运营商 多表查询
     *
     * @param type
     * @param passportVo
     * @param page
     * @return
     */
    List<PassportOrOperatorVo> selectPassportOperatorVo(@Param("type") String type, @Param("passportVo") SearchPassportVo passportVo, RowBounds page);


    /**
     * 物业 查询
     *
     * @param type
     * @param passportVo
     * @param page
     * @return
     */
    List<PassportOrEstateVo> selectPassportEstateVo(@Param("type") String type, @Param("passportVo") SearchPassportVo passportVo, RowBounds page);
}