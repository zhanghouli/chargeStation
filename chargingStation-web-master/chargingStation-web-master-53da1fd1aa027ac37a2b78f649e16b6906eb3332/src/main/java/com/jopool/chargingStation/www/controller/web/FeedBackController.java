package com.jopool.chargingStation.www.controller.web;

import com.jopool.chargingStation.www.base.entity.Feedback;
import com.jopool.chargingStation.www.constants.CodeMessage;
import com.jopool.chargingStation.www.controller.BaseController;
import com.jopool.chargingStation.www.service.CommonService;
import com.jopool.chargingStation.www.vo.FeedbackVo;
import com.jopool.chargingStation.www.vo.common.SearchFeedBackVo;
import com.jopool.jweb.entity.Result;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.mybatis.page.Pagination;
import com.jopool.jweb.utils.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by synn on 2017/8/31.
 */
@RestController
@RequestMapping("/feedback")
public class FeedBackController extends BaseController {

    @Resource
    private CommonService commonService;

    /**
     * 反馈 意见 列表
     *
     * @return
     */
    @RequestMapping("feedBackList.htm")
    public ModelAndView feedBackList(SearchFeedBackVo searchFeedBackVo, Pagination page) {
        List<FeedbackVo> feedBacks = commonService.searchFeedBackVo(searchFeedBackVo, page.page());
        ModelAndView mv = getPageMv("feedback/feedBackList", feedBacks, page);
        return mv.addObject("type", searchFeedBackVo.getType())
                .addObject("keyword", searchFeedBackVo.getKeyword())
                .addObject("isViewed", searchFeedBackVo.getIsViewed());

    }

    /**
     * 查看 反馈 信息
     *
     * @param feedbackId
     * @return
     */
    @RequestMapping("feedBackInfo.htm")
    public ModelAndView feedBackInfo(String feedbackId) {
        Feedback feedback = commonService.getById(feedbackId);
        String[] pics = {};
        if (!StringUtils.isEmpty(feedback.getPics())) {
            pics = feedback.getPics().split(",");
        }
        Arrays.asList(pics);
        ModelAndView mv = getSessionUserMV("feedback/feedBackInfo");
        return mv.addObject("feedback", feedback)
                .addObject("pics", pics);
    }

    /**
     * 添加 反馈 意见
     *
     * @param feedbackReq
     * @return
     */
    @RequestMapping("modifyFeedback.htm")
    public Result modifyFeedback(Feedback feedbackReq) {
        Feedback feedback = commonService.getById(feedbackReq.getId());
        if (feedback == null) {
            return new Result(Code.ERROR, CodeMessage.DATA_NOT_EXIST);
        }
        feedback.setModifyTime(new Date());
        feedback.setBizDealResult(feedbackReq.getBizDealResult());
        return commonService.modifyFeedBackIsViewed(feedback);
    }

    /**
     * 删除反馈意见
     *
     * @param feedbackId
     * @return
     */
    @RequestMapping("removeFeedback.htm")
    public Result removeFeedback(String feedbackId) {
        validateParam(feedbackId);
        commonService.removeFeedbackId(feedbackId);
        return new Result(Code.SUCCESS);
    }
}
