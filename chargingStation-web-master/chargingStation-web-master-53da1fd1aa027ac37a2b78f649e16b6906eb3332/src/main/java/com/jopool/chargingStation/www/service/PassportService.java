package com.jopool.chargingStation.www.service;

import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.response.EstateResp;
import com.jopool.chargingStation.www.response.PassportAccountLogsResp;
import com.jopool.chargingStation.www.vo.CarownerVo;
import com.jopool.chargingStation.www.vo.PassportOrEstateVo;
import com.jopool.chargingStation.www.vo.PassportOrGovernmentVo;
import com.jopool.chargingStation.www.vo.PassportOrOperatorVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchPassportVo;
import com.jopool.jweb.entity.Result;
import org.apache.ibatis.session.RowBounds;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.service
 * @Author : soupcat
 * @Creation Date : 2017年08月23日 下午5:41
 */
public interface PassportService {

    /**
     * 添加用户
     */
    void add(Passport passport);

    /**
     * 修改用户
     */

    Result modify(Passport passport);

    /**
     * 根据id查询
     *
     * @param id
     */
    Passport getById(String id);

    /**
     * 根据openid获取微信信息
     *
     * @param openId
     * @return
     */
    WxInfo getWxInfoByOpenId(String openId);

    /**
     * 新增微信信息
     *
     * @param wxInfo
     */
    void addWxInfo(WxInfo wxInfo);

    /**
     * 根据id获取通行证账户
     *
     * @param passportId
     * @return
     */
    PassportAccount getPassportAmountByPassportId(String passportId);

    /**
     * 修改用户基本信息
     *
     * @param passportId
     * @param deviceNumber
     * @param avatar
     * @param realName
     * @param userName
     */
    Result modifyPassportInfo(String passportId, String deviceNumber, String avatar, String realName, String userName, int electricBill);

    /**
     * 登录验证查询
     *
     * @param userName
     * @param status
     * @param types
     * @return
     */
    List<Passport> getByUserNameAndTypes(String userName, String status, String[] types);

    /**
     * 手机号 用户状态
     *
     * @param phone
     * @param type
     * @return
     */
    Passport getByPhoneAndType(String phone, String type);

    /**
     * 登录名 验证
     *
     * @param userName
     * @param types
     * @return
     */
    Passport getByUserNameAndType(String userName, String[] types);


    /**
     * 政府 多表 分页查询
     *
     * @param type
     * @param searchPassportVo
     * @param page
     * @return
     */
    List<PassportOrGovernmentVo> selectPassportOrGovernmentVo(String type, SearchPassportVo searchPassportVo, RowBounds page);

    /**
     * 运营商 多表 分页查询
     *
     * @param type
     * @param searchPassportVo
     * @param page
     * @return
     */
    List<PassportOrOperatorVo> selectPassportOperatorVo(String type, SearchPassportVo searchPassportVo, RowBounds page);

    /**
     * 修改状态
     *
     * @param passportId
     * @return
     */
    Result modifyStatus(String passportId);

    /**
     * 按照Id删除
     *
     * @param passportId
     * @return
     */
    Result removePassportId(String passportId);

    /**
     * 获取用户账户流水
     *
     * @param passportId
     * @return
     */
    List<PassportAccountLogsResp> getPassportAmountLogsByPassportId(String passportId, DateParam dateParam, String type, RowBounds page);

    /**
     * 运营商 物业登录
     *
     * @param userName
     * @param password
     * @param request
     * @return
     */
    Result login(String userName, String password, HttpServletRequest request);

    /**
     * 在线充值
     *
     * @param passportId
     * @param rechargeAmount
     */
    Result recharge(String passportId, String rechargePackageId, int rechargeAmount);

    /**
     * 获取车主信息 by passportId
     *
     * @param passportId
     * @return
     */
    Carowner getCarownerByPassportId(String passportId);

    /**
     * 账户
     *
     * @param passportAccount
     */
    void addPassportAccount(PassportAccount passportAccount);

    /**
     * 运营商 物业 手机号 不重复
     *
     * @param phone
     * @param types
     * @return
     */
    List<Passport> getPhoneByAndTypes(String phone, String[] types);

    /**
     * 获取运营商 by passportId
     *
     * @param passportId
     * @return
     */
    Operator getOperatorByPassportId(String passportId);

    /**
     * 获取物业 by passportId
     *
     * @param passportId
     * @return
     */
    Estate getEstateByPassportId(String passportId);

    /**
     * 获取车主信息
     *
     * @param phone
     * @return
     */
    CarownerVo getCarownerVoByPhone(String phone);

    /**
     * 修改车主信息
     *
     * @param carowner
     */
    void modifyCarOwner(Carowner carowner);

    /**
     * 添加车主
     *
     * @param carowner
     */
    void addCarowner(Carowner carowner);


    /**
     * 按照 passportId 删除
     *
     * @param passportId
     * @return
     */
    Result removeCarOwner(String passportId);


    /**
     * 管理员 分页 查询
     *
     * @param type
     * @param searchPassportVo
     * @param page
     * @return
     */
    List<Passport> searchPassportVo(String type, SearchPassportVo searchPassportVo, RowBounds page);

    /**
     * 物业 分页 查询
     *
     * @param type
     * @param searchPassportVo
     * @param page
     * @return
     */
    List<PassportOrEstateVo> searchPassportEstateVo(String type, SearchPassportVo searchPassportVo, RowBounds page);


    /**
     * 获取 物业
     *
     * @param estateId
     * @return
     */
    Estate getByEstateId(String estateId);


    /**
     * 获取所有 物业
     * @return
     */
    List<EstateResp> getEstateAll();


    /**
     * init 账户
     *
     * @param passportId
     */
    void addAccount(String passportId);

    /**
     * get operator by id
     *
     * @param operatorId
     * @return
     */
    Operator getOperatorById(String operatorId);

    /**
     * 根据设备number获取che车主
     *
     * @param deviceNumber
     * @return
     */
    Carowner getCarownerByDeviceNumber(String deviceNumber);

    /**
     * 根据政府id获取车辆
     *
     * @param governmentId
     * @return
     */
    List<Carowner> getCarownersByGovernmentId(String governmentId);

    /**
     * 根据passport获取用户id
     *
     * @param passport
     * @return
     */
    String getUserIdByType(Passport passport);

    /**
     * 根据车主id获取车主
     *
     * @param carownerId
     * @return
     */
    Carowner getCarownerById(String carownerId);

    /**
     * 更新人员位置
     *
     * @param carowner
     * @param lngE5
     * @param latE5
     * @return
     */
    Result modifyCarownerPostion(Carowner carowner, int lngE5, int latE5);

    /**
     * 根据passportId 获取政府人员信息
     *
     * @param passportId
     * @return
     */
    Government getGovernmentByPassportId(String passportId);

    /**
     * 未通知到电站 停止使用
     *
     * @param carowner
     */
    void stopUseTimeAccount(Carowner carowner, Order order);
}
