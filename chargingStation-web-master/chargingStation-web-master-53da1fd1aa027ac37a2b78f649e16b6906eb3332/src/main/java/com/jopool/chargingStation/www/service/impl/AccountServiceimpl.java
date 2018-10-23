package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.base.entity.PassportAccount;
import com.jopool.chargingStation.www.base.entity.PassportAccountLog;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.dao.CarownerMapper;
import com.jopool.chargingStation.www.dao.PassportAccountLogMapper;
import com.jopool.chargingStation.www.dao.PassportAccountMapper;
import com.jopool.chargingStation.www.dao.PassportMapper;
import com.jopool.chargingStation.www.enums.PassportAccountLogTypeEnum;
import com.jopool.chargingStation.www.enums.PassportTypeEnum;
import com.jopool.chargingStation.www.response.AccountDetailResp;
import com.jopool.chargingStation.www.response.FinancialResp;
import com.jopool.chargingStation.www.service.AccountService;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.service.WxPushService;
import com.jopool.chargingStation.www.vo.AccountOpetatorOrEstate;
import com.jopool.chargingStation.www.vo.PassportAccountLogVo;
import com.jopool.chargingStation.www.vo.PassportOrEstateVo;
import com.jopool.chargingStation.www.vo.PassportOrOperatorVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.chargingStation.www.vo.common.SearchPassportVo;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.DateUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.service.impl
 * @Author : soupcat
 * @Creation Date : 2017年08月29日 下午7:09
 */
@Service
public class AccountServiceimpl extends BaseServiceImpl implements AccountService, SelfBeanAware<AccountService> {
    private AccountService           selfService;
    @Resource
    private PassportAccountMapper    passportAccountMapper;
    @Resource
    private PassportAccountLogMapper passportAccountLogMapper;
    @Resource
    private PassportMapper           passportMapper;
    @Resource
    private WxPushService            wxPushService;
    @Resource
    private CommonService            commonService;
    @Resource
    private CarownerMapper           carownerMapper;

    @Override
    public void setSelfBean(AccountService object) {
        this.selfService = object;
    }

    @Override
    public void modifyPassportAccount(String passportId, int rechargeAmount, String type, String creator, String remark) {
        PassportAccount passportAccount = passportAccountMapper.selectByPrimaryKey(passportId);
        if (null != passportAccount) {
            PassportAccountLog passportAccountLog = new PassportAccountLog();
            passportAccountLog.setId(UUIDUtils.createId());
            passportAccountLog.setAmount(rechargeAmount);
            passportAccountLog.setAmountBefore(passportAccount.getAmount());
            passportAccountLog.setAmountAfter(passportAccount.getAmount() + rechargeAmount);
            passportAccountLog.setPassportId(passportId);
            passportAccountLog.setRemark(remark);
            passportAccountLog.setType(type);
            passportAccountLog.setIsDeleted(false);
            passportAccountLog.setCreationTime(new Date());
            passportAccountLog.setCreator(creator);
            passportAccountLogMapper.insert(passportAccountLog);
            passportAccount.setAmount(passportAccount.getAmount() + rechargeAmount);
            passportAccountMapper.updateByPrimaryKeySelective(passportAccount);
        }
    }

    @Override
    public AccountDetailResp getAccountDetail(String passportId) {
        AccountDetailResp resp = new AccountDetailResp();
        PassportAccount passportAccount = passportAccountMapper.selectByPrimaryKey(passportId);
        if (null != passportAccount) {
            resp.setAmount(passportAccount.getAmount());
            Date date = new Date();
            String today = DateUtils.date2StringBySecond(date);
            Integer todayLogsAmount = passportAccountLogMapper.selectByToday(passportId, today);
            String thisMonth = DateUtils.date2String(date, "yyyy-MM-dd HH:mm:ss");
            Integer thisMonthLogsAmount = passportAccountLogMapper.selectByMonth(passportId, thisMonth);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, -1);
            System.out.println(calendar.getTime());
            String nextMonth = DateUtils.date2String(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
            Integer lastMonthLogsAmount = passportAccountLogMapper.selectByMonth(passportId, nextMonth);
            resp.setIncomeToday(todayLogsAmount);
            resp.setIncomeThisMonth(thisMonthLogsAmount);
            resp.setIncomeLastMonth(lastMonthLogsAmount);
        }
        return resp;
    }

    @Override
    public List<PassportAccountLogVo> listPassportAccountLog(DateParam dateParam, String type, SearchBaseVo searchBaseVo, RowBounds page) {
        return passportAccountLogMapper.selectAccountTypeAll(dateParam, type, searchBaseVo, page);
    }

    @Override
    public Integer passportAccountLogSum(DateParam dateParam, String type) {
        return passportAccountLogMapper.selectTypeDateSumAmount(type, dateParam);
    }

    @Override
    public FinancialResp getFinancialInfo(DateParam dateParam, RowBounds page) {
        int electricityFees = 0;
        int platformFees = 0;
        int investorFees = 0;
        int operatorFees = 0;
        int estateFees = 0;
        FinancialResp resp = new FinancialResp();
        List<PassportAccountLog> systemAccountLogs = passportAccountLogMapper.searchByPassportId(Constants.SYSTEM_ID, dateParam, null, page);
        for (PassportAccountLog log : systemAccountLogs) {
//            electricityFees
            electricityFees += log.getAmount() * Constants.FINANCIAL_MANAGEMENT_ELECTRICITY_FEES_PERCENT;
            platformFees += log.getAmount() * Constants.FINANCIAL_MANAGEMENT_PLATFORM_PERCENT;
            investorFees += log.getAmount() * Constants.FINANCIAL_MANAGEMENT_INVESTOR_PERCENT;
        }
        List<AccountOpetatorOrEstate> getOperatorAccounts = selfService.getOperatorAccount(dateParam, null, PassportTypeEnum.OPERATOR.getValue(), Pagination.nullPage());
        for (AccountOpetatorOrEstate opetator : getOperatorAccounts) {
            operatorFees += (opetator.getAccountSum() == null ? 0 : opetator.getAccountSum());
        }
        List<AccountOpetatorOrEstate> getEstateAccounts = selfService.getEstateAccount(dateParam, null, PassportTypeEnum.ESTATE.getValue(), Pagination.nullPage());
        for (AccountOpetatorOrEstate estate : getEstateAccounts) {
            estateFees += (estate.getAccountSum() == null ? 0 : estate.getAccountSum());
        }
        resp.setElectricityFees(electricityFees);
        resp.setPlatformFees(platformFees);
        resp.setInvestorFees(investorFees);
        resp.setOperatorFees(operatorFees);
        resp.setEstateFees(estateFees);
        return resp;
    }

    @Override
    public List<AccountOpetatorOrEstate> getOperatorAccount(DateParam dateParam, String keyword, String type, RowBounds page) {
        List<AccountOpetatorOrEstate> accountOpetatorOrEstates = new ArrayList<>();
        SearchPassportVo searchPassportVo = new SearchPassportVo();
        searchPassportVo.setKeyword(keyword);
        List<PassportOrOperatorVo> passportList = passportMapper.selectPassportOperatorVo(type, searchPassportVo, page);
        for (PassportOrOperatorVo passportOrOperatorVo : passportList) {
            AccountOpetatorOrEstate accountOpetatorOrEstate = new AccountOpetatorOrEstate();
            accountOpetatorOrEstate.setPassportOrOperatorVo(passportOrOperatorVo);
            accountOpetatorOrEstate.setAccountSum(passportAccountLogMapper.selectTypeDateSumAmountOperatorOrEstate(new String[]{PassportAccountLogTypeEnum.INCOME.getValue(), PassportAccountLogTypeEnum.RETURN.getValue()}, dateParam, passportOrOperatorVo.getId()));
            accountOpetatorOrEstates.add(accountOpetatorOrEstate);
        }
        return accountOpetatorOrEstates;
    }

    @Override
    public List<AccountOpetatorOrEstate> getEstateAccount(DateParam dateParam, String keyword, String
            type, RowBounds page) {
        List<AccountOpetatorOrEstate> accountOpetatorOrEstates = new ArrayList<>();
        SearchPassportVo searchPassportVo = new SearchPassportVo();
        searchPassportVo.setKeyword(keyword);
        List<PassportOrEstateVo> passportList = passportMapper.selectPassportEstateVo(type, searchPassportVo, page);
        for (PassportOrEstateVo passportOrOperatorVo : passportList) {
            AccountOpetatorOrEstate accountOpetatorOrEstate = new AccountOpetatorOrEstate();
            accountOpetatorOrEstate.setPassportOrEstateVo(passportOrOperatorVo);
            accountOpetatorOrEstate.setAccountSum(passportAccountLogMapper.selectTypeDateSumAmountOperatorOrEstate(new String[]{PassportAccountLogTypeEnum.INCOME.getValue(), PassportAccountLogTypeEnum.RETURN.getValue()}, dateParam, passportOrOperatorVo.getId()));
            accountOpetatorOrEstates.add(accountOpetatorOrEstate);
        }
        return accountOpetatorOrEstates;
    }


}
