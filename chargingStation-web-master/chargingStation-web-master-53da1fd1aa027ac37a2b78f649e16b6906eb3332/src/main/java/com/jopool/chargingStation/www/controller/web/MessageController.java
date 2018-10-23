package com.jopool.chargingStation.www.controller.web;

import com.jopool.chargingStation.www.base.entity.CmppSmsReceive;
import com.jopool.chargingStation.www.base.entity.CmppSmsSend;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.service.CmppProxyService;
import com.jopool.chargingStation.www.vo.common.SearchBaseVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by synn on 2017/12/21.
 */
@RestController
@RequestMapping("/msg")
public class MessageController extends BaseController {
    @Resource
    private CmppProxyService cmppProxyService;

    /**
     * 发送 消息 列表
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    @RequestMapping("/cmppSmsSendList.htm")
    public ModelAndView cmppSmsSendList(SearchBaseVo searchBaseVo, Pagination page) {
        List<CmppSmsSend> list = cmppProxyService.searchCmppSmsSendList(searchBaseVo, page.page());
        ModelAndView modelAndView = getPageMv("msg/cmppSmsSendList", list, page);
        return modelAndView.addObject("keyword", searchBaseVo.getKeyword());
    }

    /**
     * 消息发送
     *
     * @param phone
     * @param content
     * @return
     */
    @RequestMapping("/addCmppSmsSend.htm")
    public Result addCmppSmsSend(String phone, String content) {
        boolean result = cmppProxyService.send(phone, content);
        if (result) {
            return new Result(Code.SUCCESS, "发送成功");
        } else {
            return new Result(Code.SUCCESS, "发送失败");
        }
    }

    /**
     * 发送消息 删除
     *
     * @param cmppSmsSendId
     * @return
     */
    @RequestMapping("/removeCmppSmsSend.htm")
    public Result removeCmppSmsSend(String cmppSmsSendId) {
        validateParam(cmppSmsSendId);
        cmppProxyService.removeCmppSmsSendById(cmppSmsSendId);
        return new Result(Code.SUCCESS);
    }

    /**
     * 接收 消息 列表
     *
     * @param searchBaseVo
     * @param page
     * @return
     */
    @RequestMapping("/cmppSmsReceiveList.htm")
    public ModelAndView cmppSmsReceiveList(SearchBaseVo searchBaseVo, Pagination page) {
        List<CmppSmsReceive> list = cmppProxyService.searchCmppSmsReceiveList(searchBaseVo, page.page());
        ModelAndView modelAndView = getPageMv("msg/cmppSmsReceiveList", list, page);
        return modelAndView.addObject("keyword", searchBaseVo.getKeyword());
    }

    /**
     * 接收消息删除
     *
     * @param cmppSmsReceiveId
     * @return
     */
    @RequestMapping("/removeCmppSmsReceiveList.htm")
    public Result removeSmsReceiveList(String cmppSmsReceiveId) {
        validateParam(cmppSmsReceiveId);
        cmppProxyService.removeCmppSmsReceiveById(cmppSmsReceiveId);
        return new Result(Code.SUCCESS);
    }
}
