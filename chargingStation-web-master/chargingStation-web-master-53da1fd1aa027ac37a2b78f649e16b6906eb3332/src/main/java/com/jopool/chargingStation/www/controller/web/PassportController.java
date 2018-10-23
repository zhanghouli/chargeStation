package com.jopool.chargingStation.www.controller.web;

import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.constants.SystemParamKeys;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.enums.*;
import com.jopool.chargingStation.www.service.*;
import com.jopool.chargingStation.www.vo.*;
import com.jopool.chargingStation.www.vo.common.SearchPassportVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.utils.PasswordHash;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by synn on 2017/8/25.
 */
@RestController
@RequestMapping("/passport")
public class PassportController extends BaseController {

    @Resource
    private PassportService       passportService;
    @Resource
    private CommonService         commonService;
    @Resource
    private OperatorService       operatorService;
    @Resource
    private EstateService         estateService;
    @Resource
    private ConsumePackageService consumePackageService;
    @Resource
    private GovernmentService     governmentService;
    @Resource
    private StationService        stationService;

    /**
     * 我的账号
     *
     * @return
     */
    @RequestMapping("myPassport.htm")
    public ModelAndView myPassport(HttpServletRequest request) {
        Passport passport = passportService.getById(getSessionUser().getPassportId());
        CommonPicture commonPicture = commonService.getPictureByHostId(passport.getId(), CommonPictureTypeEnum.AVATAR.getValue());
        PassportVo passportVo = new PassportVo(passport);
        if (commonPicture != null) {
            passportVo.setPic(commonPicture.getRemoteRelativeUrl());
            passportVo.setPicId(commonPicture.getId());
        }
        ModelAndView mv = getSessionUserMV("passport/myPassport");
        return mv.addObject("passport", passportVo);
    }

    /**
     * 政府
     *
     * @param searchPassportVo
     * @param page
     * @return
     */
    @RequestMapping("governmentList.htm")
    public ModelAndView governmentList(SearchPassportVo searchPassportVo, Pagination page) {
        List<PassportOrGovernmentVo> list = passportService.selectPassportOrGovernmentVo(PassportTypeEnum.GOVERNMENT.getValue(), searchPassportVo, page);
        List<PassportOrGovernmentVo> passportOrGovernmentVos = new ArrayList<PassportOrGovernmentVo>();
        for (PassportOrGovernmentVo passportOrGovernmentVo : list) {
            CommonPicture commonPicture = commonService.getPictureByHostId(passportOrGovernmentVo.getId(), CommonPictureTypeEnum.AVATAR.getValue());
            if (commonPicture != null) {
                passportOrGovernmentVo.setPic(commonPicture.getRemoteRelativeUrl());
                passportOrGovernmentVo.setPicId(commonPicture.getId());
            }
            passportOrGovernmentVos.add(passportOrGovernmentVo);
        }
        ModelAndView mv = getPageMv("passport/governmentList", passportOrGovernmentVos, page);
        return mv.addObject("keyword", searchPassportVo.getKeyword())
                .addObject("status", searchPassportVo.getStatus());
    }

    /**
     * 管理员列表
     *
     * @param page
     * @return
     */
    @RequestMapping("passportList.htm")
    public ModelAndView passportList(SearchPassportVo searchPassportVo, Pagination page) {
        List<Passport> passportList = passportService.searchPassportVo(PassportTypeEnum.ADMIN.getValue(), searchPassportVo, page.page());
        List<PassportVo> passportVos = new ArrayList<PassportVo>();
        for (Passport passport : passportList) {
            CommonPicture commonPicture = commonService.getPictureByHostId(passport.getId(), CommonPictureTypeEnum.AVATAR.getValue());
            PassportVo passportVo = new PassportVo(passport);
            if (commonPicture != null) {
                passportVo.setPic(commonPicture.getRemoteRelativeUrl());
            }
            passportVos.add(passportVo);
        }
        ModelAndView mv = getPageMv("passport/passportList", passportVos, page);
        return mv.addObject("keyword", searchPassportVo.getKeyword())
                .addObject("status", searchPassportVo.getStatus())
                .addObject("type", PassportTypeEnum.ADMIN.getValue())
                .addObject("isEnabled", searchPassportVo.getIsEnabled());
    }

    /**
     * 运营商列表
     *
     * @param searchPassportVo
     * @param page
     * @return
     */
    @RequestMapping("operatorList.htm")
    public ModelAndView operatorList(SearchPassportVo searchPassportVo, Pagination page) {
        List<PassportOrOperatorVo> list = passportService.selectPassportOperatorVo(PassportTypeEnum.OPERATOR.getValue(), searchPassportVo, page);
        List<PassportOrOperatorVo> passportOrOperatorVos = new ArrayList<PassportOrOperatorVo>();
        for (PassportOrOperatorVo passportOrOperatorVo : list) {
            CommonPicture commonPicture = commonService.getPictureByHostId(passportOrOperatorVo.getId(), CommonPictureTypeEnum.AVATAR.getValue());
            if (commonPicture != null) {
                passportOrOperatorVo.setPic(commonPicture.getRemoteRelativeUrl());
                passportOrOperatorVo.setPicId(commonPicture.getId());
            }
            passportOrOperatorVos.add(passportOrOperatorVo);
        }
        ModelAndView mv = getPageMv("passport/operatorList", passportOrOperatorVos, page);
        return mv.addObject("keyword", searchPassportVo.getKeyword())
                .addObject("status", searchPassportVo.getStatus());
    }

    /**
     * 物业列表
     *
     * @param searchPassportVo
     * @param page
     * @return
     */
    @RequestMapping("estateList.htm")
    public ModelAndView property(SearchPassportVo searchPassportVo, Pagination page) {
        List<PassportOrEstateVo> list = passportService.searchPassportEstateVo(PassportTypeEnum.ESTATE.getValue(), searchPassportVo, page.page());
        List<PassportOrEstateVo> passportOrEstateVos = new ArrayList<PassportOrEstateVo>();
        for (PassportOrEstateVo passportOrEstateVo : list) {
            CommonPicture commonPicture = commonService.getPictureByHostId(passportOrEstateVo.getId(), CommonPictureTypeEnum.AVATAR.getValue());
            if (commonPicture != null) {
                passportOrEstateVo.setPic(commonPicture.getRemoteRelativeUrl());
                passportOrEstateVo.setPicId(commonPicture.getId());
            }
            passportOrEstateVos.add(passportOrEstateVo);
        }
        Operator operator = passportService.getOperatorById(searchPassportVo.getOperatorId());
        ModelAndView mv = getPageMv("passport/estateList", passportOrEstateVos, page);
        return mv.addObject("keyword", searchPassportVo.getKeyword())
                .addObject("status", searchPassportVo.getStatus())
                .addObject("operatorId", searchPassportVo.getOperatorId())
                .addObject("operator", operator);
    }

    /**
     * 车主 列表
     *
     * @param searchPassportVo
     * @param page
     * @return
     */
    @RequestMapping("carOwnerList.htm")
    public ModelAndView carOwnerList(SearchPassportVo searchPassportVo, Pagination page) {
        List<Passport> passportList = passportService.searchPassportVo(PassportTypeEnum.CAROWNER.getValue(), searchPassportVo, page.page());
        List<PassportOrCarOwnerVo> passportOrCarOwnerVos = new ArrayList<PassportOrCarOwnerVo>();
        for (Passport passport : passportList) {
            Carowner carowner = passportService.getCarownerByPassportId(passport.getId());
            CommonPicture commonPicture = commonService.getPictureByHostId(passport.getId(), CommonPictureTypeEnum.AVATAR.getValue());
            PassportOrCarOwnerVo passportOrCarOwnerVo = new PassportOrCarOwnerVo(passport, carowner);
            if (commonPicture != null) {
                passportOrCarOwnerVo.setPic(commonPicture.getRemoteRelativeUrl());
                passportOrCarOwnerVo.setPicId(commonPicture.getId());
            }
            passportOrCarOwnerVos.add(passportOrCarOwnerVo);
        }
        ModelAndView mv = getPageMv("passport/carOwnerList", passportOrCarOwnerVos, page);
        return mv.addObject("keyword", searchPassportVo.getKeyword())
                .addObject("status", searchPassportVo.getStatus());
    }

    /**
     * 政府添加
     *
     * @param passportReq
     * @param commonPictureReq
     * @param area
     * @param areaDes
     * @param pic
     * @param picId
     * @return
     */
    @RequestMapping("doAddOrModifyGovernment.htm")
    public Result doAddOrModifyGovernment(Passport passportReq, CommonPicture commonPictureReq, String[] area, String areaDes, String pic, String picId) {
        //修改
        if (!StringUtils.isEmpty(passportReq.getId())) {
            Passport passport = passportService.getById(passportReq.getId());
            if (passport == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            //手机号验证
            List<Passport> passportPhone = passportService.getPhoneByAndTypes(passportReq.getPhone(), new String[]{PassportTypeEnum.OPERATOR.getValue(), PassportTypeEnum.ESTATE.getValue()});
            if (passportPhone.size() > 0 && !passport.getPhone().equals(passportReq.getPhone())) {
                return new Result(Code.ERROR, CodeMessage.PHONE_EXIST);
            }
            //登录名验证
            Passport passportUserName = passportService.getByUserNameAndType(passportReq.getUserName(), new String[]{PassportTypeEnum.GOVERNMENT.getValue(), PassportTypeEnum.ESTATE.getValue(), PassportTypeEnum.OPERATOR.getValue()});
            if (passportUserName != null && !passportReq.getUserName().equals(passport.getUserName())) {
                return new Result(Code.ERROR, CodeMessage.USER_NAME_EXIST);
            }
            //passport
            passport.setRealName(passportReq.getRealName());
            passport.setUserName(passportReq.getUserName());
            passport.setPhone(passportReq.getPhone());
            if (!StringUtils.isEmpty(passportReq.getPassword())) {
                passport.setPassword(PasswordHash.createHash(passportReq.getPassword(), UUIDUtils.createId()));
            }
            passport.setModifyTime(new Date());
            passport.setModifier(getSessionUser().getPassportId());
            passportService.modify(passport);
            //修改图片
            if (!StringUtils.isEmpty(picId) && commonPictureReq.getWidth() != null) {
                modifyCommonPicture(commonPictureReq, passport.getId());
            } else if (StringUtils.isEmpty(picId) && !StringUtils.isEmpty(pic)) {
                addCommonPicture(commonPictureReq, passport.getId(), pic);
            }
            //政府
            Government government = governmentService.getByPassportId(passport.getId());
            if (government != null) {
                government.setModifier(getSessionUser().getPassportId());
                government.setModifyTime(new Date());
                government.setArea(area[0].toString());
                government.setAreaDes(areaDes.toString());
            }
            governmentService.modifyGovernment(government);
        } else {
            //新增
            //手机号判断
            List<Passport> passportPhone = passportService.getPhoneByAndTypes(passportReq.getPhone(), new String[]{PassportTypeEnum.GOVERNMENT.getValue()});
            if (passportPhone.size() > 0) {
                return new Result(Code.ERROR, CodeMessage.PHONE_EXIST);
            }
            //登录账号重复判断
            Passport passportUserName = passportService.getByUserNameAndType(passportReq.getUserName(), new String[]{PassportTypeEnum.GOVERNMENT.getValue(), PassportTypeEnum.ESTATE.getValue(), PassportTypeEnum.OPERATOR.getValue()});
            if (passportUserName != null) {
                return new Result(Code.ERROR, CodeMessage.USER_NAME_EXIST);
            }
            Passport passport = new Passport();
            passport.setId(UUIDUtils.createId());
            passport.setRealName(passportReq.getRealName());
            passport.setUserName(passportReq.getUserName());
            passport.setStatus(PassportStatusEnum.NORMAL.getValue());
            passport.setType(PassportTypeEnum.GOVERNMENT.getValue());
            passport.setPhone(passportReq.getPhone());
            passport.setPassword(PasswordHash.createHash(passportReq.getPassword(), UUIDUtils.createId()));
            passport.setCreator(getSessionUser().getUserId());
            passport.setIsEnabled(true);
            passport.setIsDeleted(false);
            passport.setCreationTime(new Date());
            //新增的时候 有图片路径了 代表图片上传了
            if (!StringUtils.isEmpty(pic)) {
                addCommonPicture(commonPictureReq, passport.getId(), pic);
            }
            //政府
            Government government = new Government();
            government.setArea(area[0].toString());
            government.setAreaDes(areaDes.toString());
            government.setCreator(getSessionUser().getPassportId());
            //账户金额
            PassportAccount passportAccount = new PassportAccount();
            passportAccount.setPassportId(passport.getId());
            passportAccount.setAmount(0);
            //政府添加
            governmentService.addGovernment(passport.getId(), government);
            //金额添加
            passportService.add(passport);
            //passport
            passportService.addPassportAccount(passportAccount);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 管理员 添加修改
     *
     * @param passReq
     * @param pic
     * @return
     */
    @RequestMapping("doAddOrModifyPassport.htm")
    public Result doAddOrModifyPassport(Passport passReq, CommonPicture commonPicture, String pic, String picId) {
        //id判断新增或修改`
        if (!StringUtils.isEmpty(passReq.getId())) {
            //Id判断
            Passport passport = passportService.getById(passReq.getId());
            if (passport == null) {
                return new Result(Code.ERROR, CodeMessage.PASSPORT_NOT_EXIST);
            }
            //手机号 重复
            Passport passportPhone = passportService.getByPhoneAndType(passReq.getPhone(), passReq.getType());
            if (passportPhone != null && !passReq.getId().equals(passportPhone.getId())) {
                return new Result(Code.ERROR, CodeMessage.NAME_EXIST);
            }
            //登录名 重复
            Passport passportUserName = passportService.getByUserNameAndType(passReq.getUserName(), new String[]{passReq.getType()});
            if (passportUserName != null && !passReq.getUserName().equals(passportUserName.getUserName())) {
                return new Result(Code.ERROR, CodeMessage.USER_NAME_EXIST);
            }
            passport.setUserName(passReq.getUserName());
            passport.setRealName(passReq.getRealName());
            passport.setPhone(passReq.getPhone());
            if (!StringUtils.isEmpty(passReq.getPassword())) {
                passport.setPassword(PasswordHash.createHash(passReq.getPassword(), passReq.getId()));
            }  //修改图片
            if (!StringUtils.isEmpty(picId) && commonPicture.getWidth() != null) {
                modifyCommonPicture(commonPicture, passport.getId());
            } else if (StringUtils.isEmpty(picId) && !StringUtils.isEmpty(pic)) {
                addCommonPicture(commonPicture, passport.getId(), pic);
            }
            passport.setModifyTime(new Date());

            passportService.modify(passport);
        } else {
            //手机号 重复
            Passport passportPhone = passportService.getByPhoneAndType(passReq.getPhone(), passReq.getType());
            if (passportPhone != null) {
                return new Result(Code.ERROR, CodeMessage.PHONE_EXIST);
            }
            //登录名 重复
            Passport passportUserName = passportService.getByUserNameAndType(passReq.getUserName(), new String[]{passReq.getType()});
            if (passportUserName != null) {
                return new Result(Code.ERROR, CodeMessage.USER_NAME_EXIST);
            }
            Passport passport = new Passport();
            passport.setId(UUIDUtils.createId());
            passport.setRealName(passReq.getRealName());
            passport.setUserName(passReq.getUserName());
            passport.setStatus(PassportStatusEnum.NORMAL.getValue());
            passport.setType(passReq.getType());
            passport.setPhone(passReq.getPhone());
            passport.setPassword(PasswordHash.createHash(passReq.getPassword(), UUIDUtils.createId()));
            passport.setCreator(getSessionUser().getUserId());
            passport.setIsEnabled(true);
            passport.setIsDeleted(false);
            passport.setCreationTime(new Date());
            //新增的时候 有图片路径了 代表图片上传了
            if (!StringUtils.isEmpty(pic)) {
                addCommonPicture(commonPicture, passport.getId(), pic);
            }
            PassportAccount passportAccount = new PassportAccount();
            passportAccount.setPassportId(passport.getId());
            passportAccount.setAmount(0);
            passportService.add(passport);
            passportService.addPassportAccount(passportAccount);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 运营商登录 账号 新增 修改
     *
     * @param passportReq
     * @param commonPictureReq
     * @param area
     * @param areaDes
     * @param pic
     * @param picId
     * @return
     */
    @RequestMapping("doAddOrModifyOperator.htm")
    public Result doAddOrModifyOperator(Passport passportReq, CommonPicture commonPictureReq, String[] area, String areaDes, String pic, String picId) {
        if (!StringUtils.isEmpty(passportReq.getId())) {
            Passport passport = passportService.getById(passportReq.getId());
            if (passport == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            //手机号验证
            List<Passport> passportPhone = passportService.getPhoneByAndTypes(passportReq.getPhone(), new String[]{PassportTypeEnum.OPERATOR.getValue(), PassportTypeEnum.ESTATE.getValue()});
            if (passportPhone.size() > 0 && !passport.getPhone().equals(passportReq.getPhone())) {
                return new Result(Code.ERROR, CodeMessage.PHONE_EXIST);
            }
            //登录名验证
            Passport passportUserName = passportService.getByUserNameAndType(passportReq.getUserName(), new String[]{PassportTypeEnum.GOVERNMENT.getValue(), PassportTypeEnum.ESTATE.getValue(), PassportTypeEnum.OPERATOR.getValue()});
            if (passportUserName != null && !passportReq.getUserName().equals(passport.getUserName())) {
                return new Result(Code.ERROR, CodeMessage.USER_NAME_EXIST);
            }
            passport.setRealName(passportReq.getRealName());
            passport.setUserName(passportReq.getUserName());
            passport.setPhone(passportReq.getPhone());
            if (!StringUtils.isEmpty(passportReq.getPassword())) {
                passport.setPassword(PasswordHash.createHash(passportReq.getPassword(), UUIDUtils.createId()));
            }
            passport.setModifyTime(new Date());
            passport.setModifier(getSessionUser().getPassportId());
            passportService.modify(passport);
            //修改图片
            if (!StringUtils.isEmpty(picId) && commonPictureReq.getWidth() != null) {
                modifyCommonPicture(commonPictureReq, passport.getId());
            } else if (StringUtils.isEmpty(picId) && !StringUtils.isEmpty(pic)) {
                addCommonPicture(commonPictureReq, passport.getId(), pic);
            }
            Operator operator = operatorService.getByPassportId(passport.getId());
            operator.setModifyTime(new Date());
            operator.setModifier(getSessionUser().getPassportId());
            operator.setArea(area[0].toString());
            operator.setAreaDes(areaDes.toString());
            operatorService.modifyOperator(operator);
        } else {
            //手机号验证
            List<Passport> passportPhone = passportService.getPhoneByAndTypes(passportReq.getPhone(), new String[]{PassportTypeEnum.OPERATOR.getValue(), PassportTypeEnum.ESTATE.getValue()});
            if (passportPhone.size() > 0) {
                return new Result(Code.ERROR, CodeMessage.PHONE_EXIST);
            }
            //登录名验证
            Passport passportUserName = passportService.getByUserNameAndType(passportReq.getUserName(), new String[]{PassportTypeEnum.GOVERNMENT.getValue(), PassportTypeEnum.ESTATE.getValue(), PassportTypeEnum.OPERATOR.getValue()});
            if (passportUserName != null) {
                return new Result(Code.ERROR, CodeMessage.USER_NAME_EXIST);
            }
            Passport passport = new Passport();
            passport.setId(UUIDUtils.createId());
            passport.setRealName(passportReq.getRealName());
            passport.setUserName(passportReq.getUserName());
            passport.setPhone(passportReq.getPhone());
            passport.setPassword(PasswordHash.createHash(passportReq.getPassword(), passport.getId()));
            passport.setType(PassportTypeEnum.OPERATOR.getValue());
            passport.setStatus(PassportStatusEnum.NORMAL.getValue());
            passport.setCreator(getSessionUser().getPassportId());
            passport.setCreationTime(new Date());
            passport.setIsDeleted(false);
            passport.setIsEnabled(true);
            //新增的时候 有图片路径了 代表图片上传了
            if (!StringUtils.isEmpty(pic)) {
                addCommonPicture(commonPictureReq, passport.getId(), pic);
            }
            passportService.add(passport);
            Operator operator = new Operator();
            operator.setPassportId(passport.getId());
            operator.setArea(area[0].toString());
            operator.setAreaDes(areaDes.toString());
            operator.setCreator(getSessionUser().getPassportId());
            operatorService.addOperator(operator);
            PassportAccount passportAccount = new PassportAccount();
            passportAccount.setPassportId(passport.getId());
            passportAccount.setAmount(0);
            passportService.addPassportAccount(passportAccount);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 物业  新增  修改
     *
     * @param passportReq
     * @param estateReq
     * @param commonPictureReq
     * @param pic
     * @param picId
     * @return
     */
    @RequestMapping("doAddOrModifyEstate.htm")
    public Result doAddOrModifyEstate(Passport passportReq, Estate estateReq, CommonPicture commonPictureReq, String pic, String picId) {
        //id 判断  add  or  modify
        if (!StringUtils.isEmpty(passportReq.getId())) {
            Passport passport = passportService.getById(passportReq.getId());
            if (passport == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            //手机号 验证
            List<Passport> passportPhone = passportService.getPhoneByAndTypes(passportReq.getPhone(), new String[]{PassportTypeEnum.OPERATOR.getValue(), PassportTypeEnum.ESTATE.getValue()});
            if (passportPhone.size() > 0 && !passport.getPhone().equals(passportReq.getPhone())) {
                return new Result(Code.ERROR, CodeMessage.PHONE_EXIST);
            }
            //登录名验证
            Passport passportUserName = passportService.getByUserNameAndType(passportReq.getUserName(), new String[]{PassportTypeEnum.GOVERNMENT.getValue(), PassportTypeEnum.ESTATE.getValue(), PassportTypeEnum.OPERATOR.getValue()});
            if (passportUserName != null && !passport.getUserName().equals(passportReq.getUserName())) {
                return new Result(Code.ERROR, CodeMessage.USER_NAME_EXIST);
            }
            //登录 修改
            passport.setRealName(passportReq.getRealName());
            passport.setUserName(passportReq.getUserName());
            passport.setPhone(passportReq.getPhone());
            if (!StringUtils.isEmpty(passportReq.getPassword())) {
                passport.setPassword(PasswordHash.createHash(passportReq.getPassword(), UUIDUtils.createId()));
            }
            passport.setModifier(getSessionUser().getPassportId());
            passport.setModifyTime(new Date());
            //修改图片
            if (!StringUtils.isEmpty(picId) && commonPictureReq.getWidth() != null) {
                modifyCommonPicture(commonPictureReq, passport.getId());
            } else if (StringUtils.isEmpty(picId) && !StringUtils.isEmpty(pic)) {
                addCommonPicture(commonPictureReq, passport.getId(), pic);
            }
            //物业修改
            Estate estate = estateService.getByPassportId(passport.getId());
            estate.setAddress(estateReq.getAddress());
            estate.setContactName(estateReq.getContactName());
            estate.setContactPhone(estateReq.getContactPhone());
            estate.setElectricBill(estateReq.getElectricBill());
            passportService.modify(passport);
            estateService.modifyEstate(estate);
        } else {
            //手机号 验证
            List<Passport> passportPhone = passportService.getPhoneByAndTypes(passportReq.getPhone(), new String[]{PassportTypeEnum.OPERATOR.getValue(), PassportTypeEnum.ESTATE.getValue()});
            if (passportPhone.size() > 0) {
                return new Result(Code.ERROR, CodeMessage.PHONE_EXIST);
            }
            //登录名验证
            Passport passportUserName = passportService.getByUserNameAndType(passportReq.getUserName(), new String[]{PassportTypeEnum.GOVERNMENT.getValue(), PassportTypeEnum.ESTATE.getValue(), PassportTypeEnum.OPERATOR.getValue()});
            if (passportUserName != null) {
                return new Result(Code.ERROR, CodeMessage.USER_NAME_EXIST);
            }
            //登录账号添加
            Passport passport = new Passport();
            passport.setId(UUIDUtils.createId());
            passport.setRealName(passportReq.getRealName());
            passport.setUserName(passportReq.getUserName());
            passport.setPhone(passportReq.getPhone());
            passport.setPassword(PasswordHash.createHash(passportReq.getPassword(), UUIDUtils.createId()));
            passport.setType(PassportTypeEnum.ESTATE.getValue());
            passport.setStatus(PassportStatusEnum.NORMAL.getValue());
            passport.setIsEnabled(true);
            passport.setIsDeleted(false);
            passport.setCreator(getSessionUser().getPassportId());
            passport.setCreationTime(new Date());
            //新增的时候 有图片路径了 代表图片上传了
            if (!StringUtils.isEmpty(pic)) {
                addCommonPicture(commonPictureReq, passport.getId(), pic);
            }
            //物业信息添加
            Estate estate = new Estate();
            estate.setId(UUIDUtils.createId());
            estate.setPassportId(passport.getId());
            estate.setOperatorId(estateReq.getOperatorId());
            estate.setAddress(estateReq.getAddress());
            estate.setContactName(estateReq.getContactName());
            estate.setContactPhone(estateReq.getContactPhone());
            estate.setElectricBill(estateReq.getElectricBill());
            estate.setIsEnabled(true);
            estate.setIsDeleted(false);
            estate.setCreationTime(new Date());
            estate.setCreator(getSessionUser().getPassportId());
            //流水信息
            PassportAccount passportAccount = new PassportAccount();
            passportAccount.setPassportId(passport.getId());
            passportAccount.setAmount(0);
            passportService.add(passport);
            estateService.addEstate(estate);
            passportService.addPassportAccount(passportAccount);
        }
        return new Result(Code.SUCCESS);
    }


    /**
     * 车主新增  修改
     *
     * @param passportReq
     * @param carownerReq
     * @param commonPictureReq
     * @param pic
     * @param picId
     * @return
     */
    @RequestMapping("addCarOwner.htm")
    public Result addCarOwner(Passport passportReq, Carowner carownerReq, CommonPicture commonPictureReq, String pic, String picId) {
        if (!StringUtils.isEmpty(passportReq.getId())) {
            Passport passport = passportService.getById(passportReq.getId());
            Carowner carowner = passportService.getCarownerByPassportId(passport.getId());
            //验证 是否 存在
            if (passport == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            if (carowner == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            //验证手机号
            Passport passportPhone = passportService.getByPhoneAndType(passportReq.getPhone(), PassportTypeEnum.CAROWNER.getValue());
            if (passportPhone != null && !passport.getPhone().equals(passportPhone.getPhone())) {
                return new Result(Code.ERROR, CodeMessage.PHONE_EXIST);
            }
            //登录名验证
            Passport passportUserName = passportService.getByUserNameAndType(passportReq.getUserName(), new String[]{PassportTypeEnum.CAROWNER.getValue()});
            if (passportUserName != null && !passport.getUserName().equals(passportUserName.getUserName())) {
                return new Result(Code.ERROR, CodeMessage.USER_NAME_EXIST);
            }
            passport.setUserName(passportReq.getUserName());
            passport.setRealName(passportReq.getRealName());
            if (!StringUtils.isEmpty(passportReq.getPassword())) {
                passport.setPassword(PasswordHash.createHash(passportReq.getPassword(), UUIDUtils.createId()));
            }
            passport.setPhone(passportReq.getPhone());
            passport.setModifier(getSessionUser().getPassportId());
            passport.setModifyTime(new Date());
            passportService.modify(passport);
            carowner.setAvatar(carownerReq.getAvatar());
            carowner.setModifier(getSessionUser().getPassportId());
            carowner.setDeviceNumber(carownerReq.getDeviceNumber());
            carowner.setModifyTime(new Date());
            passportService.modifyCarOwner(carowner);
            //修改图片
            if (!StringUtils.isEmpty(picId) && commonPictureReq.getWidth() != null) {
                modifyCommonPicture(commonPictureReq, passport.getId());
            } else if (StringUtils.isEmpty(picId) && !StringUtils.isEmpty(pic)) {
                addCommonPicture(commonPictureReq, passport.getId(), pic);
            }
        } else {
            //验证手机号
            Passport passportPhone = passportService.getByPhoneAndType(passportReq.getPhone(), PassportTypeEnum.CAROWNER.getValue());
            if (passportPhone != null) {
                return new Result(Code.ERROR, CodeMessage.PHONE_EXIST);
            }
            //登录名验证
            Passport passportUserName = passportService.getByUserNameAndType(passportReq.getUserName(), new String[]{PassportTypeEnum.CAROWNER.getValue()});
            if (passportUserName != null) {
                return new Result(Code.ERROR, CodeMessage.USER_NAME_EXIST);
            }
            //新建账号
            Passport passport = new Passport();
            passport.setId(UUIDUtils.createId());
            passport.setRealName(passportReq.getRealName());
            passport.setUserName(passportReq.getUserName());
            passport.setPhone(passportReq.getPhone());
            passport.setPassword(PasswordHash.createHash(passportReq.getPassword(), UUIDUtils.createId()));
            passport.setType(PassportTypeEnum.CAROWNER.getValue());
            passport.setStatus(PassportStatusEnum.NORMAL.getValue());
            passport.setIsEnabled(true);
            passport.setIsDeleted(false);
            passport.setCreator(getSessionUser().getPassportId());
            passport.setCreationTime(new Date());
            passportService.add(passport);
            //新增的时候 有图片路径了 代表图片上传了
            if (!StringUtils.isEmpty(pic)) {
                addCommonPicture(commonPictureReq, passport.getId(), pic);
            }
            //车主 添加
            Carowner carowner = new Carowner();
            carowner.setId(UUIDUtils.createId());
            carowner.setPassportId(passport.getId());
            carowner.setAvatar(carownerReq.getAvatar());
            carowner.setDeviceNumber(carownerReq.getDeviceNumber());
            carowner.setDeviceStatus(DeviceStatusEnum.OFFLINE.getValue());
            carowner.setFenceStatus(FenceStatusEnum.UNKNOW.getValue());
            carowner.setIsEnabled(true);
            carowner.setIsDeleted(false);
            carowner.setCreator(getSessionUser().getPassportId());
            carowner.setCreationTime(new Date());
            passportService.addCarowner(carowner);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 充值套餐 添加
     *
     * @param consumePackage
     * @return
     */
    @RequestMapping("doAddOrModifyConsume.htm")
    public Result doAddOrModifyConsume(ConsumePackage consumePackage) {
        consumePackage.setId(UUIDUtils.createId());
        consumePackage.setIsDeleted(false);
        consumePackage.setIsEnabled(true);
        consumePackage.setCreationTime(new Date());
        consumePackageService.addConsumePackage(consumePackage);
        return new Result(Code.SUCCESS);
    }


    /**
     * 管理员 账号详情
     *
     * @param passportId
     * @return
     */
    @RequestMapping("getPassportInfo.htm")
    public Result getPassportInfo(String passportId) {
        validateParam(passportId);
        Passport passport = passportService.getById(passportId);
        PassportVo passportVo = new PassportVo(passport);
        CommonPicture commonPicture = commonService.getPictureByHostId(passportId, CommonPictureTypeEnum.AVATAR.getValue());
        if (commonPicture != null) {
            passportVo.setPic(commonPicture.getRemoteRelativeUrl());
            passportVo.setPicId(commonPicture.getId());
        }
        return new Result(Code.SUCCESS, passportVo);
    }

    /**
     * 运营商  详情
     *
     * @param operatorId
     * @return
     */
    @RequestMapping("getOperatorId.htm")
    public Result getOperatorId(String operatorId) {
        validateParam(operatorId);
        Operator operator = operatorService.searchById(operatorId);
        Map<String, String> map = new HashMap<String, String>();
        List<ConsumePackage> consumePackages = consumePackageService.searchOperatorId(operatorId);
        for (ConsumePackage consumePackage : consumePackages) {
            map.put(consumePackage.getId(), consumePackage.getName());
        }
        ConsumePackageIdVo consumePackageIdVo = new ConsumePackageIdVo();
        consumePackageIdVo.setMaps(map);
        if (operator != null) {
            consumePackageIdVo.setArea(operator.getArea());
        }
        consumePackageIdVo.setOperatorId(operatorId);
        consumePackageIdVo.setOperatorDivided(commonService.getConfigValueByName(SystemParamKeys.OPERATOR_DIVIDED, "10"));
        consumePackageIdVo.setEstateDivided(commonService.getConfigValueByName(SystemParamKeys.ESTATE_DIVIDED, "30"));
        return new Result(Code.SUCCESS, consumePackageIdVo);
    }

    /**
     * 政府 修改
     *
     * @param passportId
     * @param governmentId
     * @return
     */
    @RequestMapping("getPassportGovernment.htm")
    public Result getPassportGovernment(String passportId, String governmentId) {
        validateParam(passportId, governmentId);
        Passport passport = passportService.getById(passportId);
        Government government = governmentService.getById(governmentId);
        PassportOrGovernmentVo passportOrGovernmentVo = new PassportOrGovernmentVo(passport, government);
        CommonPicture commonPicture = commonService.getPictureByHostId(passportId, CommonPictureTypeEnum.AVATAR.getValue());
        if (commonPicture != null) {
            passportOrGovernmentVo.setPic(commonPicture.getRemoteRelativeUrl());
            passportOrGovernmentVo.setPicId(commonPicture.getId());
        }
        return new Result(Code.SUCCESS, passportOrGovernmentVo);
    }


    /**
     * 运营商 查看 修改
     *
     * @param passportId
     * @return
     */
    @RequestMapping("getPassportOrOperator.htm")
    public Result getPassportOrOperator(String passportId) {
        validateParam(passportId);
        Passport passport = passportService.getById(passportId);
        Operator operator = operatorService.getByPassportId(passportId);
        PassportOrOperatorVo passportOrOperatorVo = new PassportOrOperatorVo(passport, operator);
        CommonPicture commonPicture = commonService.getPictureByHostId(passportId, CommonPictureTypeEnum.AVATAR.getValue());
        if (commonPicture != null) {
            passportOrOperatorVo.setPic(commonPicture.getRemoteRelativeUrl());
            passportOrOperatorVo.setPicId(commonPicture.getId());
        }
        return new Result(Code.SUCCESS, passportOrOperatorVo);
    }

    /**
     * 物业 详情
     *
     * @param estateId
     * @param passportId
     * @return
     */
    @RequestMapping("getEstateInfo.htm")
    public Result getEstateInfo(String estateId, String passportId) {
        validateParam(estateId, passportId);
        Estate estate = estateService.searchEstateById(estateId);
        Passport passport = passportService.getById(passportId);
        PassportOrEstateVo passportOrEstateVo = new PassportOrEstateVo(passport, estate);
        CommonPicture commonPicture = commonService.getPictureByHostId(passportId, CommonPictureTypeEnum.AVATAR.getValue());
        if (commonPicture != null) {
            passportOrEstateVo.setPic(commonPicture.getRemoteRelativeUrl());
            passportOrEstateVo.setPicId(commonPicture.getId());
        }
        return new Result(Code.SUCCESS, passportOrEstateVo);
    }


    /**
     * 车主 详情
     *
     * @param passportId
     * @param carOwnerId
     * @return
     */
    @RequestMapping("getCarOwner.htm")
    public Result getCarOwner(String passportId, String carOwnerId) {
        validateParam(passportId, carOwnerId);
        CommonPicture commonPicture = commonService.getPictureByHostId(passportId, CommonPictureTypeEnum.AVATAR.getValue());
        PassportOrCarOwnerVo passportOrCarOwnerVo = new PassportOrCarOwnerVo(passportService.getById(passportId), passportService.getCarownerByPassportId(passportId));
        if (commonPicture != null) {
            passportOrCarOwnerVo.setPicId(commonPicture.getId());
            passportOrCarOwnerVo.setPic(commonPicture.getRemoteRelativeUrl());
        }
        return new Result(Code.SUCCESS, passportOrCarOwnerVo);
    }

    /**
     * 管理员 修改状态
     *
     * @param passportId
     * @return
     */
    @RequestMapping("toggleCustomService.htm")
    public Result toggleCustomService(String passportId) {
        validateParam(passportId);
        return passportService.modifyStatus(passportId);
    }

    /**
     * 运营商 状态修改
     *
     * @param operatorId
     * @return
     */
    @RequestMapping("toggleOperatorIsEnabled.htm")
    public Result toggleOperatorIsEnabled(String operatorId) {
        validateParam(operatorId);
        return operatorService.modifyStatus(operatorId);
    }

    /**
     * 物业 状态修改
     *
     * @param estateId
     * @return
     */
    @RequestMapping("toggleEstateIsEnabled.htm")
    public Result toggleEstateIsEnabled(String estateId) {
        validateParam(estateId);
        return estateService.modifyStatus(estateId);
    }

    /**
     * 管理员Id 删除
     *
     * @param passportId
     * @return
     */
    @RequestMapping("doRemovePassport.htm")
    public Result doRemovePassport(String passportId) {
        validateParam(passportId);
        return passportService.removePassportId(passportId);
    }

    /**
     * 政府信息删除
     *
     * @param passportId
     * @param governmentId
     * @return
     */
    @RequestMapping("doRemoveGovernmentId.htm")
    public Result doRemoveGovernmentId(String passportId, String governmentId) {
        validateParam(passportId, governmentId);
        passportService.removePassportId(passportId);
        governmentService.removeGovernmentId(governmentId);
        return new Result(Code.SUCCESS);
    }

    /**
     * 运营商 删除
     *
     * @param passportId
     * @return
     */
    @RequestMapping("doRemoveOperatorId.htm")
    public Result doRemoveOperatorId(String passportId) {
        validateParam(passportId);
        return operatorService.removeOperatorById(passportId);
    }

    /**
     * 运营商 获取 电站 数量
     *
     * @return
     */
    @RequestMapping("getStationCountsOrEstateCounts.htm")
    public Result getStationCountsOrEstateCounts(String operatorId) {
        validateParam(operatorId);
        return new Result(Code.SUCCESS, stationService.getStationCounts(operatorId, null));
    }

    /**
     * 物业 删除
     *
     * @param passportId
     * @return
     */
    @RequestMapping("doRemoveEstate.htm")
    public Result doRemoveEstate(String passportId) {
        validateParam(passportId);
        return estateService.removeEstateById(passportId);
    }

    /**
     * 删除 车主
     *
     * @param passportId
     * @return
     */
    @RequestMapping("doRemoveCarOwner.htm")
    public Result doRemoveCarOwner(String passportId) {
        validateParam(passportId);
        return passportService.removeCarOwner(passportId);
    }

    /**
     * 添加图片
     *
     * @param commonPicture
     * @param passportId
     * @param pic
     */
    public void addCommonPicture(CommonPicture commonPicture, String passportId, String pic) {
        CommonPicture commonPictureNew = new CommonPicture();
        commonPictureNew.setName(commonPicture.getName());
        commonPictureNew.setHostId(passportId);
        commonPictureNew.setHeight(commonPicture.getHeight());
        commonPictureNew.setWidth(commonPicture.getWidth());
        commonPictureNew.setSize(commonPicture.getSize());
        commonPictureNew.setOrgName(commonPicture.getOrgName());
        commonPictureNew.setRelativeFolder(commonPicture.getRelativeFolder());
        commonPictureNew.setRemoteRelativeUrl(pic);
        commonPictureNew.setCreator(getSessionUser().getPassportId());
        commonService.addCommonPicture(commonPictureNew, CommonPictureTypeEnum.AVATAR.getValue());
    }

    /**
     * 修改图片
     *
     * @param commonPicture
     */
    public void modifyCommonPicture(CommonPicture commonPicture, String passportId) {
        CommonPicture picture = commonService.getPictureByHostId(passportId, CommonPictureTypeEnum.AVATAR.getValue());
        picture.setName(commonPicture.getName());
        picture.setHeight(commonPicture.getHeight());
        picture.setWidth(commonPicture.getWidth());
        picture.setSize(commonPicture.getSize());
        picture.setOrgName(commonPicture.getOrgName());
        picture.setRemoteRelativeUrl(commonPicture.getRemoteRelativeUrl());
        picture.setRelativeFolder(commonPicture.getRelativeFolder());
        picture.setModifier(getSessionUser().getPassportId());
        picture.setModifyTime(new Date());
        commonService.modifyCommonPicture(picture);
    }
}
