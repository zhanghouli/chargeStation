package com.jopool.chargingStation.www.base.entity;

import java.util.Date;

public class CommonOutsideSnsSendHis {
    private String id;

    private String snsCategory;

    private String title;

    private String receiverSnsNo;

    private String receiverType;

    private Long receiverId;

    private Date sendDatetime;

    private String sendPurpose;

    private String status;

    private String statusResult;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    private String modifier;

    private Date modifyTime;

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSnsCategory() {
        return snsCategory;
    }

    public void setSnsCategory(String snsCategory) {
        this.snsCategory = snsCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReceiverSnsNo() {
        return receiverSnsNo;
    }

    public void setReceiverSnsNo(String receiverSnsNo) {
        this.receiverSnsNo = receiverSnsNo;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Date getSendDatetime() {
        return sendDatetime;
    }

    public void setSendDatetime(Date sendDatetime) {
        this.sendDatetime = sendDatetime;
    }

    public String getSendPurpose() {
        return sendPurpose;
    }

    public void setSendPurpose(String sendPurpose) {
        this.sendPurpose = sendPurpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusResult() {
        return statusResult;
    }

    public void setStatusResult(String statusResult) {
        this.statusResult = statusResult;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}