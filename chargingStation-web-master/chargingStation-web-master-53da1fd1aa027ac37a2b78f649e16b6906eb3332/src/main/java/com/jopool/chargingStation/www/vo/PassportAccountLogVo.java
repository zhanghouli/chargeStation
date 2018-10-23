package com.jopool.chargingStation.www.vo;


import java.util.Date;

/**
 * Created by synn on 2017/9/8.
 */
public class PassportAccountLogVo {
    private String  id;
    private String  realName;
    private Integer amount;
    private Integer amountBefore;
    private Integer amountAfter;
    private String  remark;
    private String  type;
    private Date    creationTime;
    private Date    modifyTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmountBefore() {
        return amountBefore;
    }

    public void setAmountBefore(Integer amountBefore) {
        this.amountBefore = amountBefore;
    }

    public Integer getAmountAfter() {
        return amountAfter;
    }

    public void setAmountAfter(Integer amountAfter) {
        this.amountAfter = amountAfter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
