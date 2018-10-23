package com.jopool.chargingStation.www.service;


import com.jopool.chargingStation.www.response.AccountDetailResp;
import com.jopool.chargingStation.www.response.FinancialResp;
import com.jopool.chargingStation.www.vo.AccountOpetatorOrEstate;
import com.jopool.chargingStation.www.vo.PassportAccountLogVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.service
 * @Author : soupcat
 * @Creation Date : 2017年08月29日 下午7:08
 */
public interface AccountService {

    /**
     * 更新账户
     *
     * @param passportId
     * @param rechargeAmount
     * @param type
     */
    void modifyPassportAccount(String passportId, int rechargeAmount, String type, String creator, String remark);

    /**
     * 收入信息
     *
     * @param passportId
     * @return
     */
    AccountDetailResp getAccountDetail(String passportId);

    /**
     * 流水 日志 查询
     *
     * @param type
     * @param searchBaseVo
     * @param page
     * @return
     */
    List<PassportAccountLogVo> listPassportAccountLog(DateParam dateParam, String type, SearchBaseVo searchBaseVo, RowBounds page);

    /**
     * 类型 汇总
     *
     * @param dateParam
     * @param type
     * @return
     */
    Integer passportAccountLogSum(DateParam dateParam, String type);

    /**
     * 财务数据
     *
     * @param dateParam
     * @return
     */
    FinancialResp getFinancialInfo(DateParam dateParam, RowBounds page);

    /**
     * 运营商
     *
     * @param dateParam
     * @param type
     * @return
     */
    List<AccountOpetatorOrEstate> getOperatorAccount(DateParam dateParam, String keyword, String type, RowBounds page);

    /**
     * 物业
     *
     * @param dateParam
     * @param type
     * @return
     */
    List<AccountOpetatorOrEstate> getEstateAccount(DateParam dateParam, String keyword, String type, RowBounds page);
}
