package com.jopool.chargingStation.www.service.impl;

import com.jopool.chargingStation.www.apppush.AppPushService;
import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.dao.*;
import com.jopool.chargingStation.www.enums.*;
import com.jopool.chargingStation.www.response.EstateResp;
import com.jopool.chargingStation.www.response.PassportAccountLogsResp;
import com.jopool.chargingStation.www.response.common.LoginResp;
import com.jopool.chargingStation.www.service.*;
import com.jopool.chargingStation.www.vo.CarownerVo;
import com.jopool.chargingStation.www.vo.PassportOrEstateVo;
import com.jopool.chargingStation.www.vo.PassportOrGovernmentVo;
import com.jopool.chargingStation.www.vo.PassportOrOperatorVo;
import com.jopool.chargingStation.www.vo.common.DateParam;
import com.jopool.chargingStation.www.vo.common.SearchPassportVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.enums.ModeEnum;
import com.jopool.jweb.spring.SelfBeanAware;
import com.jopool.jweb.utils.PasswordHash;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.service.impl
 * @Author : soupcat
 * @Creation Date : 2017年08月23日 下午5:42
 */
@Service
public class PassportServiceImpl extends BaseServiceImpl implements PassportService, SelfBeanAware<PassportService> {
    private PassportService          selfService;
    @Resource
    private CommonService            commonService;
    @Resource
    private AccountService           accountService;
    @Resource
    private WxPushService            wxPushService;
    @Resource
    private AppPushService           appPushService;
    @Resource
    private PassportMapper           passportMapper;
    @Resource
    private StationService           stationService;
    @Resource
    private OrderService             orderService;
    @Resource
    private PassportAccountMapper    passportAccountMapper;
    @Resource
    private PassportAccountLogMapper passportAccountLogMapper;
    @Resource
    private WxInfoMapper             wxInfoMapper;
    @Resource
    private CarownerMapper           carownerMapper;
    @Resource
    private OperatorMapper           operatorMapper;
    @Resource
    private EstateMapper             estateMapper;
    @Resource
    private CommonPictureMapper      commonPictureMapper;
    @Resource
    private GovernmentMapper         governmentMapper;

    @Override
    public void setSelfBean(PassportService object) {
        this.selfService = object;
    }

    @Override
    public void add(Passport passport) {
        passportMapper.insertSelective(passport);
    }

    @Override
    public Result modify(Passport passport) {
        passportMapper.updateByPrimaryKeySelective(passport);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Passport getById(String id) {
        return passportMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Passport> getByUserNameAndTypes(String userName, String status, String[] types) {
        return passportMapper.selectByUserNameAndTypes(userName, status, types);
    }

    @Override
    public WxInfo getWxInfoByOpenId(String openId) {
        return wxInfoMapper.selectByOpenId(openId);
    }

    @Override
    public void addWxInfo(WxInfo wxInfo) {
        wxInfoMapper.insertSelective(wxInfo);
    }

    @Override
    public PassportAccount getPassportAmountByPassportId(String passportId) {
        return passportAccountMapper.selectByPrimaryKey(passportId);
    }

    @Override
    public Result modifyPassportInfo(String passportId, String deviceNumber, String avatar, String realName, String userName, int electricBill) {
        Passport passport = passportMapper.selectByPrimaryKey(passportId);
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        Carowner carowner = carownerMapper.selectByPassportId(passportId);
        if (carowner != null) {
            if (!StringUtils.isEmpty(deviceNumber)) {
                carowner.setDeviceNumber(deviceNumber);
                carownerMapper.updateByPrimaryKeySelective(carowner);
            }
        }
        if (!StringUtils.isEmpty(userName)) {
            Passport passportUserName = passportMapper.selectByUserNameAndType(userName, new String[]{PassportTypeEnum.CAROWNER.getValue()});
            if (passportUserName != null && !userName.equals(passportUserName.getUserName())) {
                return new Result(Code.ERROR, CodeMessage.USER_NAME_EXIST);
            }
            passport.setUserName(userName);
        }
        //物业 电费
        if (electricBill > 0) {
            Estate estate = estateMapper.selectByPassportId(passport.getId());
            estate.setElectricBill(electricBill);
            estate.setModifier(passportId);
            estateMapper.updateByPrimaryKeySelective(estate);
        }
        //用户 修改
        passport.setRealName(!StringUtils.isEmpty(realName) ? realName : passport.getRealName());
        passport.setUserName(!StringUtils.isEmpty(userName) ? userName : passport.getUserName());
        passport.setModifyTime(new Date());
        passport.setModifier(passportId);
        passportMapper.updateByPrimaryKeySelective(passport);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Passport getByPhoneAndType(String phone, String type) {
        return passportMapper.selectByPhoneAndType(phone, type);
    }

    @Override
    public Passport getByUserNameAndType(String userName, String[] types) {
        return passportMapper.selectByUserNameAndType(userName, types);
    }

    @Override
    public List<PassportOrGovernmentVo> selectPassportOrGovernmentVo(String type, SearchPassportVo searchPassportVo, RowBounds page) {
        return passportMapper.selectPassportGovernmentVo(type, searchPassportVo, page);
    }

    @Override
    public List<PassportOrOperatorVo> selectPassportOperatorVo(String type, SearchPassportVo searchPassportVo, RowBounds page) {
        List<PassportOrOperatorVo> passportOrOperatorVos = passportMapper.selectPassportOperatorVo(type, searchPassportVo, page);
        return passportOrOperatorVos;
    }

    @Override
    public Result modifyStatus(String passportId) {
        Passport passport = passportMapper.selectByPrimaryKey(passportId);
        if (PassportStatusEnum.NORMAL.getValue().equals(passport.getStatus())) {
            passport.setStatus(PassportStatusEnum.DISABLE.getValue());
        } else {
            passport.setStatus(PassportStatusEnum.NORMAL.getValue());
        }
        passportMapper.updateByPrimaryKey(passport);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Result removePassportId(String passportId) {
        passportMapper.deleteByPrimaryKey(passportId);
        commonPictureMapper.deletedByHostId(passportId);
        return new Result(Code.SUCCESS);
    }

    @Override
    public List<PassportAccountLogsResp> getPassportAmountLogsByPassportId(String passportId, DateParam dateParam, String type, RowBounds page) {
        List<PassportAccountLogsResp> resps = new ArrayList<PassportAccountLogsResp>();
        List<PassportAccountLog> accountLogs = passportAccountLogMapper.searchByPassportId(passportId, dateParam, type, page);
        for (PassportAccountLog accountLog : accountLogs) {
            PassportAccountLogsResp resp = new PassportAccountLogsResp(accountLog);
            resps.add(resp);
        }
        return resps;
    }

    @Override
    public Result login(String userName, String password, HttpServletRequest request) {
        //账号密码检查
        Passport passport = selfService.getByUserNameAndType(userName, new String[]{PassportTypeEnum.ESTATE.getValue()});
        if (null == passport) {
            passport = selfService.getByUserNameAndType(userName, new String[]{PassportTypeEnum.OPERATOR.getValue()});
        }
        if (null == passport) {
            passport = selfService.getByUserNameAndType(userName, new String[]{PassportTypeEnum.GOVERNMENT.getValue()});
        }
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        }
        if (PassportStatusEnum.DISABLE.getValue().equals(passport.getStatus())) {
            return new Result(Code.ERROR, CodeMessage.ACCOUNT_DISABLE);
        } else if (passport.getIsDeleted() || !PasswordHash.validatePassword(password, passport.getPassword())) {
            return new Result(Code.ERROR, CodeMessage.ACCOUNT_PWD_ERROR);
        }
        //根据不同类型获取userId
        String userId = selfService.getUserIdByType(passport);
        //生成并保存token
        String token = UUIDUtils.createId();
        if (ModeEnum.DEVELOP == ApplicationConfigHelper.getMode() || ModeEnum.SNAPSHOT == ApplicationConfigHelper.getMode() || ModeEnum.RELEASE == ApplicationConfigHelper.getMode()) {
            token = passport.getId();
        }
        cacheBean.put(Constants.CACHE_KEY_USER_TOKEN + userId, token);
        //resp
        LoginResp resp = new LoginResp();
        resp.setToken(token);
        resp.setPassportId(passport.getId());
        resp.setUserId(userId);
        resp.setUserType(passport.getType());
//        systemLogService.addSystemLog(userId, "用户登录", request);
        return new Result(Code.SUCCESS, resp);
    }

    @Override
    public Result recharge(String passportId, String rechargePackageId, int rechargeAmount) {
        Passport passport = selfService.getById(passportId);
        if (null == passport) {
            return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
        } else {
            //
            Carowner carowner = carownerMapper.selectByPassportId(passportId);
            RechargeOrder rechargeOrder = orderService.addRechargeOrder(carowner.getId(), passportId, rechargePackageId, null, rechargeAmount, RechargeOrderTypeEnum.RECHARGE_PACKAGE.getValue());
//            accountService.modifyPassportAccount(passport, rechargeAmount, PassportAccountLogTypeEnum.RECHARGE.getValue(), passportId, "账户充值");
            return new Result(Code.SUCCESS, Result.createJsonMap("rechargeOrderId", rechargeOrder.getId()));
        }
    }

    @Override
    public Carowner getCarownerByPassportId(String passportId) {
        return carownerMapper.selectByPassportId(passportId);
    }

    @Override
    public Operator getOperatorByPassportId(String passportId) {
        return operatorMapper.selectByPassportId(passportId);
    }

    @Override
    public Estate getEstateByPassportId(String passportId) {
        return estateMapper.selectByPassportId(passportId);
    }

    @Override
    public CarownerVo getCarownerVoByPhone(String phone) {
        Passport passport = passportMapper.selectByPhoneAndType(phone, PassportTypeEnum.CAROWNER.getValue());
        if (null == passport) {
            return null;
        }
        Carowner carowner = carownerMapper.selectByPassportId(passport.getId());
        if (null == carowner) {
            return null;
        }
        return new CarownerVo(passport, carowner);
    }

    @Override
    public void modifyCarOwner(Carowner carowner) {
        carownerMapper.updateByPrimaryKeySelective(carowner);
    }

    @Override
    public void addCarowner(Carowner carowner) {
        carownerMapper.insertSelective(carowner);
    }

    @Override
    public Result removeCarOwner(String passportId) {
        passportMapper.deleteByPrimaryKey(passportId);
        carownerMapper.deleteByPassportId(passportId);
        return new Result(Code.SUCCESS);
    }

    @Override
    public void addPassportAccount(PassportAccount passportAccount) {
        passportAccountMapper.insert(passportAccount);
    }

    @Override
    public List<Passport> getPhoneByAndTypes(String phone, String[] types) {
        return passportMapper.selectByPhoneAndTypes(phone, types);
    }

    @Override
    public List<Passport> searchPassportVo(String type, SearchPassportVo searchPassportVo, RowBounds page) {
        return passportMapper.selectPassportVo(type, searchPassportVo, page);
    }

    @Override
    public List<PassportOrEstateVo> searchPassportEstateVo(String type, SearchPassportVo searchPassportVo, RowBounds page) {
        return passportMapper.selectPassportEstateVo(type, searchPassportVo, page);
    }

    @Override
    public Estate getByEstateId(String estateId) {
        return estateMapper.selectByPrimaryKey(estateId);
    }

    @Override
    public List<EstateResp> getEstateAll() {
        List<EstateResp> estateResps = new ArrayList<>();
        List<Estate> estates = estateMapper.selectGetEstateAll();
        for (Estate estate : estates) {
            Passport passport = passportMapper.selectByPrimaryKey(estate.getPassportId());
            if (passport != null) {
                EstateResp estateResp = new EstateResp(passport, estate);
                estateResps.add(estateResp);
            }
        }
        return estateResps;
    }

    @Override
    public void addAccount(String passportId) {
        PassportAccount passportAccount = new PassportAccount();
        passportAccount.setPassportId(passportId);
        passportAccount.setAmount(0);
        passportAccountMapper.insert(passportAccount);
    }

    @Override
    public Operator getOperatorById(String operatorId) {
        return operatorMapper.selectByPrimaryKey(operatorId);
    }

    @Override
    public Carowner getCarownerByDeviceNumber(String deviceNumber) {
        return carownerMapper.selectByDeviceNumber(deviceNumber);
    }

    @Override
    public List<Carowner> getCarownersByGovernmentId(String governmentId) {
        return carownerMapper.selectByGovernmentId(governmentId);
    }

    @Override
    public String getUserIdByType(Passport passport) {
        String userId = "";
        if (PassportTypeEnum.ESTATE.getValue().equals(passport.getType())) {
            Estate estate = estateMapper.selectByPassportId(passport.getId());
            if (null != estate) {
                userId = estate.getId();
            }
        } else if (PassportTypeEnum.OPERATOR.getValue().equals(passport.getType())) {
            Operator operator = operatorMapper.selectByPassport(passport.getId());
            if (null != operator) {
                userId = operator.getId();
            }
        } else if (PassportTypeEnum.GOVERNMENT.getValue().equals(passport.getType())) {
            Government government = governmentMapper.selectPassportId(passport.getId());
            if (null != government) {
                userId = government.getId();
            }
        }
        return userId;
    }

    @Override
    public Carowner getCarownerById(String carownerId) {
        return carownerMapper.selectByPrimaryKey(carownerId);
    }

    @Override
    public Result modifyCarownerPostion(Carowner carowner, int lngE5, int latE5) {
        carowner.setLng(lngE5);
        carowner.setLat(latE5);
        carownerMapper.updateByPrimaryKeySelective(carowner);
        return new Result(Code.SUCCESS);
    }

    @Override
    public Government getGovernmentByPassportId(String passportId) {
        return governmentMapper.selectPassportId(passportId);
    }

    @Override
    public void stopUseTimeAccount(Carowner carowner, Order order) {
        //关闭使用
        carowner.setUseStatus(TimePayUseStatusEnum.NOT_USED.getValue());
        selfService.modifyCarOwner(carowner);
        MessageTemplate messageTemplate = commonService.searchMessageTemplateType(MessageTemplateEnum.TIME_CHARGE_COMPLETE.getValue());
    }

}
