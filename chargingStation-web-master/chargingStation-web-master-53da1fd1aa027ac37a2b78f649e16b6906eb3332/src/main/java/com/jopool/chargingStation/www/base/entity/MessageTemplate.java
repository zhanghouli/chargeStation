package com.jopool.chargingStation.www.base.entity;

import java.util.Date;

public class MessageTemplate {
    private String id;

    private String type;

    private String templateId;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    private String modifier;

    private Date modifyTime;

    public MessageTemplate() {

    }

    public MessageTemplate(MessageTemplate messageTemplate) {
        this.id = messageTemplate.getId();
        this.type = messageTemplate.getType();
        this.templateId = messageTemplate.getTemplateId();
        this.isDeleted = messageTemplate.getIsDeleted();
        this.creator = messageTemplate.getCreator();
        this.creationTime = messageTemplate.getCreationTime();
        this.modifier = messageTemplate.getModifier();
        this.modifyTime = messageTemplate.getModifyTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
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
}