package com.jopool.chargingStation.www.controller.web;

import com.jopool.chargingStation.www.base.entity.*;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.request.ConsumePackageItemReq;
import com.jopool.chargingStation.www.service.ConsumePackageService;
import com.jopool.chargingStation.www.service.OperatorService;
import com.jopool.chargingStation.www.service.PassportService;
import com.jopool.chargingStation.www.service.RechargePackageService;
import com.jopool.chargingStation.www.vo.ConsumePackageItemVo;
import com.jopool.chargingStation.www.vo.ConsumePackageVo;
import com.jopool.chargingStation.www.vo.common.SearchConsumePackageItemVo;
import com.jopool.chargingStation.www.vo.common.SearchConsumePackageVo;
import com.jopool.chargingStation.www.vo.common.SearchRechargePackageVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.utils.StringUtils;
import com.jopool.jweb.utils.UUIDUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by synn on 2017/8/28.
 */
@RestController
@RequestMapping("/consume")
public class ConsumePackageController extends BaseController {

    @Resource
    private ConsumePackageService  consumePackageService;
    @Resource
    private RechargePackageService rechargePackageService;
    @Resource
    private PassportService        passportService;
    @Resource
    private OperatorService        operatorService;


    /**
     * 套餐名称列表
     *
     * @param searchConsumePackageVo
     * @param page
     * @return
     */
    @RequestMapping("consumePackageList.htm")
    public ModelAndView consumePackageList(SearchConsumePackageVo searchConsumePackageVo, Pagination page) {
        List<ConsumePackageVo> consumePackages = consumePackageService.searchConsumePackageVo(searchConsumePackageVo, page.page());
        ModelAndView mv = getPageMv("consume/consumePackageList", consumePackages, page);
        return mv.addObject("status", searchConsumePackageVo.getStatus())
                .addObject("keyword", searchConsumePackageVo.getKeyword());
    }

    /**
     * 单独拎出来添加
     *
     * @param operatorId
     * @param searchConsumePackageVo
     * @param page
     * @return
     */
    @RequestMapping("consumePackageOperatorIdList.htm")
    public ModelAndView consumePackageList(String operatorId, SearchConsumePackageVo searchConsumePackageVo, Pagination page) {
        List<ConsumePackage> consumePackages = consumePackageService.listOperatorIdByConsumePackage(operatorId, searchConsumePackageVo, page.page());
        Operator operator = operatorService.getById(operatorId);
        Passport passport = passportService.getById(operator.getPassportId());
        ModelAndView mv = getPageMv("consume/consumePackageOperator", consumePackages, page);
        return mv.addObject("operatorId", operatorId)
                .addObject("keyword", searchConsumePackageVo.getKeyword())
                .addObject("status", searchConsumePackageVo.getStatus())
                .addObject("passport", passport);
    }

    /**
     * 套餐详情 列表
     *
     * @param consumePackageItemVo
     * @param page
     * @return
     */
    @RequestMapping("consumeItemList.htm")
    public ModelAndView consumeItemList(SearchConsumePackageItemVo consumePackageItemVo, Pagination page, String consumePackageId, String operatorId) {
        List<ConsumePackageItemVo> consumePackageItemVos = consumePackageService.searchConsumePackageItemVo(consumePackageId, consumePackageItemVo, page.page());
        ModelAndView mv = getPageMv("consume/consumeItemList", consumePackageItemVos, page);
        return mv.addObject("status", consumePackageItemVo.getStatus())
                .addObject("keyword", consumePackageItemVo.getKeyword())
                .addObject("consumePackageId", consumePackageId)
                .addObject("operatorId", operatorId);
    }

    /**
     * 充值 套餐 列表
     *
     * @param searchRechargePackageVo
     * @param page
     * @return
     */
    @RequestMapping("rechargePackageList.htm")
    public ModelAndView rechargePackageList(SearchRechargePackageVo searchRechargePackageVo, Pagination page) {
        List<RechargePackage> rechargePackages = rechargePackageService.searchRechargePackageVo(searchRechargePackageVo, page.page());
        ModelAndView mv = getPageMv("consume/rechargePackageList", rechargePackages, page);
        return mv.addObject("isEnabled", searchRechargePackageVo.getIsEnabled())
                .addObject("keyword", searchRechargePackageVo.getKeyword());
    }

    /**
     * 修改 套餐 名称
     *
     * @param consumePackageReq
     * @return
     */
    @RequestMapping("doModifyConsumePackage.htm")
    public Result doModifyConsumePackage(ConsumePackage consumePackageReq, ConsumePackageItemReq consumePackageItemReq) {

        if (!StringUtils.isEmpty(consumePackageReq.getId())) {
            ConsumePackage consumePackage = consumePackageService.searchConsumePackageById(consumePackageReq.getId());
            if (consumePackage == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            consumePackage.setName(consumePackageReq.getName());
            consumePackage.setModifyTime(new Date());
            consumePackage.setModifier(getSessionUser().getPassportId());
            consumePackageService.modifyConsumePackage(consumePackage);
        } else {
            ConsumePackage consumePackage = new ConsumePackage();
            ConsumePackageItem consumePackageItem = new ConsumePackageItem();
            consumePackage.setId(UUIDUtils.createId());
            consumePackage.setName(consumePackageReq.getName());
            consumePackage.setOperatorId(consumePackageReq.getOperatorId());
            consumePackage.setIsDeleted(false);
            consumePackage.setIsEnabled(true);
            consumePackage.setCreationTime(new Date());
            consumePackage.setCreator(getSessionUser().getPassportId());
            consumePackageItem.setId(UUIDUtils.createId());
            consumePackageItem.setConsumePackageId(consumePackage.getId());
            consumePackageItem.setStartTime(consumePackageItemReq.getStartTime());
            consumePackageItem.setEndTime(consumePackageItemReq.getEndTime());
            consumePackageItem.setTime(consumePackageItemReq.getTime());
            consumePackageItem.setPayment(consumePackageItemReq.getPayment());
            consumePackageItem.setIsDeleted(false);
            consumePackageItem.setIsEnabled(true);
            consumePackageItem.setCreator(getSessionUser().getPassportId());
            consumePackageService.addConsumePackage(consumePackage);
            consumePackageService.addConsumePackageItem(consumePackageItem);

        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 套餐 详情 添加修改
     *
     * @param consumePackageItemReq
     * @return
     */
    @RequestMapping("doAddOrModifyConsumeItem.htm")
    public Result doAddOrModifyConsumeItem(ConsumePackageItemReq consumePackageItemReq) {
        if (!StringUtils.isEmpty(consumePackageItemReq.getId())) {
            ConsumePackageItem consumePackageItem = consumePackageService.getById(consumePackageItemReq.getId());
            if (consumePackageItem == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            consumePackageItem.setStartTime(consumePackageItemReq.getStartTime());
            consumePackageItem.setEndTime(consumePackageItemReq.getEndTime());
            consumePackageItem.setTime(consumePackageItemReq.getTime());
            consumePackageItem.setPayment(consumePackageItemReq.getPayment());
            consumePackageItem.setModifyTime(new Date());

            return consumePackageService.modifyConsumePackageItem(consumePackageItem);
        } else {
            ConsumePackageItem consumePackageItem = new ConsumePackageItem();
            consumePackageItem.setId(UUIDUtils.createId());
            consumePackageItem.setConsumePackageId(consumePackageItemReq.getConsumePackageId());
            consumePackageItem.setConsumePackageId(consumePackageItemReq.getConsumePackageId());
            consumePackageItem.setStartTime(consumePackageItemReq.getStartTime());
            consumePackageItem.setEndTime(consumePackageItemReq.getEndTime());
            consumePackageItem.setIsEnabled(true);
            consumePackageItem.setTime(consumePackageItemReq.getTime());
            consumePackageItem.setPayment(consumePackageItemReq.getPayment());
            consumePackageItem.setCreationTime(new Date());
            consumePackageItem.setIsDeleted(false);
            consumePackageItem.setCreator(getSessionUser().getPassportId());
            consumePackageService.addConsumePackageItem(consumePackageItem);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 充值 套餐 添加 修改
     *
     * @param rechargePackageReq
     * @return
     */
    @RequestMapping("addOrModifyRechargePackage.htm")
    public Result addOrModifyRechargePackage(RechargePackage rechargePackageReq) {
        if (!StringUtils.isEmpty(rechargePackageReq.getId())) {
            RechargePackage rechargePackage = rechargePackageService.getById(rechargePackageReq.getId());
            if (rechargePackage == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            rechargePackage.setPayment(rechargePackageReq.getPayment());
            rechargePackage.setAmount(rechargePackageReq.getAmount());
            rechargePackage.setModifyTime(new Date());
            rechargePackageService.modifyRechargePackage(rechargePackage);
        } else {
            RechargePackage rechargePackage = new RechargePackage();
            rechargePackage.setId(UUIDUtils.createId());
            rechargePackage.setPayment(rechargePackageReq.getPayment());
            rechargePackage.setAmount(rechargePackageReq.getAmount());
            rechargePackage.setIsEnabled(true);
            rechargePackage.setIsDeleted(false);
            rechargePackage.setCreationTime(new Date());
            rechargePackage.setCreator(getSessionUser().getPassportId());
            rechargePackageService.add(rechargePackage);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 名称 详情
     *
     * @param consumeId
     * @return
     */
    @RequestMapping("getConsumePackageInfo.htm")
    public Result getConsumePackageInfo(String consumeId) {
        validateParam(consumeId);
        ConsumePackage consumePackage = consumePackageService.searchConsumePackageById(consumeId);
        return new Result(Code.SUCCESS, consumePackage);
    }

    /**
     * 套餐 详情
     *
     * @param consumeItemId
     * @return
     */
    @RequestMapping("getConsumeItemInfo.htm")
    public Result getConsumeItemInfo(String consumeItemId) {
        validateParam(consumeItemId);
        ConsumePackageItem consumePackageItem = consumePackageService.getById(consumeItemId);
        return new Result(Code.SUCCESS, consumePackageItem);
    }

    /**
     * 充值 套餐 详情
     *
     * @param rechargePackageId
     * @return
     */
    @RequestMapping("getRechargePackage.htm")
    public Result getRechargePackage(String rechargePackageId) {
        validateParam(rechargePackageId);
        RechargePackage rechargePackage = rechargePackageService.getById(rechargePackageId);
        return new Result(Code.SUCCESS, rechargePackage);
    }

    /**
     * id 删除
     *
     * @param consumeId
     * @return
     */
    @RequestMapping("doRemoveConsumeId.htm")
    public Result doRemoveConsumeId(String consumeId) {
        validateParam(consumeId);
        consumePackageService.removeConsumePackageById(consumeId);
        return new Result(Code.SUCCESS);
    }

    /**
     * 详情 id 删除
     *
     * @param consumeItemId
     * @return
     */
    @RequestMapping("doRemoveConsumeItemId.htm")
    public Result doRemoveConsumeItemId(String consumeItemId) {
        validateParam(consumeItemId);
        consumePackageService.removeConsumePackageItemId(consumeItemId);
        return new Result(Code.SUCCESS);
    }

    /**
     * id 删除 充值 套餐
     *
     * @param rechargePackageId
     * @return
     */
    @RequestMapping("removeRechargePackage.htm")
    public Result removeRechargePackage(String rechargePackageId) {
        validateParam(rechargePackageId);
        return rechargePackageService.removeRechargePackageId(rechargePackageId);
    }

    /**
     * 状态 修改
     *
     * @param consumeId
     * @return
     */
    @RequestMapping("modifyConsumePackageStatus.htm")
    public Result modifyConsumePackageStatus(String consumeId) {
        validateParam(consumeId);
        consumePackageService.modifyStatus(consumeId);
        return new Result(Code.SUCCESS);
    }

    /**
     * 详情 状态 修改
     *
     * @param consumeItemId
     * @return
     */
    @RequestMapping("modifyConsumeItemStatus.htm")
    public Result modifyConsumeItemStatus(String consumeItemId) {
        validateParam(consumeItemId);
        consumePackageService.modifyStatusConsumePackageItem(consumeItemId);
        return new Result(Code.SUCCESS);
    }

    /**
     * 充值 套餐 状态 修改
     *
     * @param rechargePackageId
     * @return
     */
    @RequestMapping("modifyRechargePackageIsEnabled.htm")
    public Result modifyRechargePackageIsEnabled(String rechargePackageId) {
        validateParam(rechargePackageId);
        rechargePackageService.modifyRechargePackageIsEnabled(rechargePackageId);
        return new Result(Code.SUCCESS);
    }

    /**
     * 充值 套餐 列表
     *
     * @param searchRechargePackageVo
     * @param page
     * @return
     */
    @RequestMapping("chargeCostPackageList.htm")
    public ModelAndView chargeCostPackageList(SearchRechargePackageVo searchRechargePackageVo, Pagination page) {
        List<ChargeCostPackage> rechargePackages = consumePackageService.searchChargeCostPackage(searchRechargePackageVo, page.page());
        ModelAndView mv = getPageMv("consume/chargeCostPackageList", rechargePackages, page);
        return mv.addObject("isEnabled", searchRechargePackageVo.getIsEnabled())
                .addObject("keyword", searchRechargePackageVo.getKeyword());
    }

    /**
     * 充值 套餐 添加 修改
     *
     * @param chargeCostPackageReq
     * @return
     */
    @RequestMapping("addOrModifyChargeCostPackage.htm")
    public Result addOrModifyChargeCostPackage(ChargeCostPackage chargeCostPackageReq) {
        if (!StringUtils.isEmpty(chargeCostPackageReq.getId())) {
            ChargeCostPackage chargeCostPackage = consumePackageService.getChargeCostPackageById(chargeCostPackageReq.getId());
            if (chargeCostPackage == null) {
                return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
            }
            chargeCostPackage.setMaxPow(chargeCostPackageReq.getMaxPow());
            chargeCostPackage.setMinPow(chargeCostPackageReq.getMinPow());
            chargeCostPackage.setCost(chargeCostPackageReq.getCost());
            chargeCostPackage.setModifier(getSessionUser().getPassportId());
            consumePackageService.modifyChargeCostPackage(chargeCostPackage);
        } else {
            ChargeCostPackage chargeCostPackage = new ChargeCostPackage(chargeCostPackageReq, getSessionUser().getPassportId());
            consumePackageService.addChargeCostPackage(chargeCostPackage);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 按量充值配置 详情
     *
     * @param chargeCostPackageId
     * @return
     */
    @RequestMapping("getChargeCostPackage.htm")
    public Result getChargeCostPackage(String chargeCostPackageId) {
        validateParam(chargeCostPackageId);
        ChargeCostPackage chargeCostPackage = consumePackageService.getChargeCostPackageById(chargeCostPackageId);
        return new Result(Code.SUCCESS, chargeCostPackage);
    }

    /**
     * id 删除 充值 套餐
     *
     * @param chargeCostPackageId
     * @return
     */
    @RequestMapping("removeChargeCostPackage.htm")
    public Result removeChargeCostPackage(String chargeCostPackageId) {
        validateParam(chargeCostPackageId);
        return consumePackageService.removeChargeCostPackage(chargeCostPackageId);
    }

    /**
     * 充值 套餐 状态 修改
     *
     * @param chargeCostPackageId
     * @return
     */
    @RequestMapping("modifyChargeCostPackageIsEnabled.htm")
    public Result modifyChargeCostPackageIsEnabled(String chargeCostPackageId) {
        validateParam(chargeCostPackageId);
        ChargeCostPackage chargeCostPackage = consumePackageService.getChargeCostPackageById(chargeCostPackageId);
        if (chargeCostPackage != null) {
            chargeCostPackage.setIsEnabled(chargeCostPackage.getIsEnabled() ? false : true);
            consumePackageService.modifyChargeCostPackage(chargeCostPackage);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * 获取 使用套餐的电站数量
     *
     * @param chargeCostPackageId
     * @return
     */
    @RequestMapping("getStationUserChargeCostPackageCounts.htm")
    public Result getStationUserChargeCostPackageCounts(String chargeCostPackageId) {
        validateParam(chargeCostPackageId);
        return new Result(Code.SUCCESS, consumePackageService.getStationUserPackage(chargeCostPackageId));
    }
}
