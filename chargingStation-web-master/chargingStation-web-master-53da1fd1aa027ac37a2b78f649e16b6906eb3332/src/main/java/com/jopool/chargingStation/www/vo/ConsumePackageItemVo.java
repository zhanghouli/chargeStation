package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.ConsumePackageItem;

/**
 * Created by synn on 2017/8/28.
 */
public class ConsumePackageItemVo extends ConsumePackageItem {
    private String consumeName;

    public ConsumePackageItemVo(ConsumePackageItem consumePackageItem){
        this.setId(consumePackageItem.getId());
        this.setConsumePackageId(consumePackageItem.getConsumePackageId());
        this.setStartTime(consumePackageItem.getStartTime());
        this.setEndTime(consumePackageItem.getEndTime());
        this.setPayment(consumePackageItem.getPayment());
        this.setTime(consumePackageItem.getTime());
        this.setIsEnabled(consumePackageItem.getIsEnabled());
        this.setCreationTime(consumePackageItem.getCreationTime());
        this.setModifyTime(consumePackageItem.getModifyTime());
    }

    public String getConsumeName() {
        return consumeName;
    }

    public void setConsumeName(String consumeName) {
        this.consumeName = consumeName;
    }
}
