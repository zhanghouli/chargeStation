package com.jopool.chargingStation.www.controller.web;

import com.jopool.chargingStation.www.base.entity.AliConfig;
import com.jopool.chargingStation.www.base.entity.WxConfig;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.service.PayAliService;
import com.jopool.chargingStation.www.service.PayWxService;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
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
 * Created by synn on 2017/8/31.
 */
@RestController
@RequestMapping("/pay")
public class PayController extends BaseController {

    @Resource
    private PayAliService payAliService;
    @Resource
    private PayWxService  payWxService;

    /**
     * alipay列表
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    @RequestMapping("aliPayList.htm")
    public ModelAndView aliPayList(SearchBaseVo searchBaseVo, Pagination page) {
        List<AliConfig> list = payAliService.search(searchBaseVo, page);
        //
        ModelAndView mv = getPageMv("pay/aliPayList", list, page);
        mv.addObject("keyword", searchBaseVo.getKeyword());
        return mv;
    }

    /**
     * payali详情
     *
     * @param payAliId
     * @return
     */
    @RequestMapping("aliPayInfo.htm")
    public ModelAndView aliPayInfo(String payAliId) {
        ModelAndView mv = getSessionUserMV("pay/aliPayInfo");
        AliConfig payAli = payAliService.getById(payAliId);
        mv.addObject("payAli", payAli);
        return mv;
    }

    /**
     * aypali添加或者修改
     *
     * @param payAliReq
     * @return
     */
    @RequestMapping("doAddOrModifyPayAli.htm")
    public Result doAddOrModifyPayAli(AliConfig payAliReq) {
        if (!StringUtils.isEmpty(payAliReq.getId())) {
            //modify
            payAliService.modify(payAliReq);
        } else {
            //add
            payAliReq.setId(UUIDUtils.createId());
            payAliReq.setCreator(getSessionUser().getUserId());
            payAliReq.setIsDeleted(false);
            payAliReq.setCreationTime(new Date());
            payAliService.add(payAliReq);
        }
        return new Result(Code.SUCCESS);
    }

    /**
     * payali删除
     *
     * @param payAliId
     * @return
     */
    @RequestMapping("doRemovePayAli.htm")
    public Result doRemovePayAli(String payAliId) {
        validateParam(payAliId);
        payAliService.removeById(payAliId);
        return new Result(Code.SUCCESS);
    }


    /**
     * 微信 支付 配置
     *
     * @return
     */
    @RequestMapping("wxPayList.htm")
    public ModelAndView wxPayList(SearchBaseVo searchBaseVo, Pagination page) {
        List<WxConfig> wxConfigs = payWxService.search(searchBaseVo, page.page());
        ModelAndView mv = getPageMv("pay/wxPayList", wxConfigs, page);
        return mv.addObject("keyWord", searchBaseVo.getKeyword());
    }

    /**
     * 添加 微信参数 页面
     *
     * @param payWxId
     * @return
     */
    @RequestMapping("wxPayInfo.htm")
    public ModelAndView wxPayInfo(String payWxId) {
        ModelAndView mv = getSessionUserMV("pay/wxPayInfo");
        WxConfig wxConfig = payWxService.getById(payWxId);
        mv.addObject("wxConfig", wxConfig);
        return mv;
    }

    /**
     * 微信 支付 参数 添加
     *
     * @param wxConfigReq
     * @return
     */
    @RequestMapping("addOrModify.htm")
    public Result addWxConfig(WxConfig wxConfigReq) {
        if(!StringUtils.isEmpty(wxConfigReq.getId())){
            payWxService.modify(wxConfigReq);
        }else {
            wxConfigReq.setCreator(getSessionUser().getPassportId());
            payWxService.add(wxConfigReq);
        }
        return new Result(Code.SUCCESS);
    }


    /**
     * Id  删除
     * @param payWxId
     * @return
     */
    @RequestMapping("removeWxConfig.htm")
    public Result removeWxConfig(String payWxId){
        validateParam(payWxId);
        payWxService.removeById(payWxId);
        return new Result(Code.SUCCESS);
    }

}
