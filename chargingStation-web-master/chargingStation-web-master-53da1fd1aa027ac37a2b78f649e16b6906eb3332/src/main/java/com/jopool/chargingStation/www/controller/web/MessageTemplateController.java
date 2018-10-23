package com.jopool.chargingStation.www.controller.web;

import com.jopool.chargingStation.www.base.entity.MessageTemplate;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by synn on 2017/11/27.
 */
@RestController
@RequestMapping("/message")
public class MessageTemplateController extends BaseController {
    @Resource
    private CommonService commonService;

    /**
     * 模版id 列表
     *
     * @param type
     * @param page
     * @return
     */
    @RequestMapping("/wxMessageList.htm")
    public ModelAndView wxMessageList(String type, Pagination page) {
        List<MessageTemplate> messageTemplateList = commonService.searchMessageTemplate(type, page);
        ModelAndView mv = getPageMv("message/wxMessageList", messageTemplateList, page);
        return mv.addObject("type", type);
    }

    /**
     * 修改添加
     *
     * @param messageTemplate
     * @return
     */
    @RequestMapping("/addOrModifyMessage.htm")
    public Result addOrModifyMessage(MessageTemplate messageTemplate) {
        return commonService.addOrModifyMessageTemplate(messageTemplate, getSessionUser());
    }

    /**
     * 数据返回
     *
     * @param messageId
     * @return
     */
    @RequestMapping("/getMessageId.htm")
    public Result getMessageId(String messageId) {
        validateParam(messageId);
        MessageTemplate messageTemplate = commonService.getMessageId(messageId);
        return new Result(Code.SUCCESS, messageTemplate);
    }

    /**
     * 删除
     *
     * @param messageId
     * @return
     */
    @RequestMapping("/removeMessageId.htm")
    public Result removeMessageId(String messageId) {
        validateParam(messageId);
        commonService.removeMessageTemplate(messageId);
        return new Result(Code.SUCCESS);
    }
}
