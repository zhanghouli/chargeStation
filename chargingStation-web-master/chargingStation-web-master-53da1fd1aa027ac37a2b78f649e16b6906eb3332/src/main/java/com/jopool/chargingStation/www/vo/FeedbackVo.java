package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Feedback;

import java.util.List;

/**
 * Created by synn on 2017/8/31.
 */
public class FeedbackVo extends Feedback {
    private List<String> picList;
    private String       passportName;

    public FeedbackVo() {

    }

    public FeedbackVo(Feedback feedback) {
        this.setId(feedback.getId());
        this.setTitle(feedback.getTitle());
        this.setType(feedback.getType());
        this.setPics(feedback.getPics());
        this.setIsViewed(feedback.getIsViewed());
        this.setCreationTime(feedback.getCreationTime());
        this.setContent(feedback.getContent());
        this.setModifyTime(feedback.getModifyTime());
        this.setBizDealResult(feedback.getBizDealResult());
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
    }
}
