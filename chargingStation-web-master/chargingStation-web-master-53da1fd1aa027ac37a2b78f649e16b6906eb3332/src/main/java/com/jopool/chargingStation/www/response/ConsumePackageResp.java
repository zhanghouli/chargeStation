package com.jopool.chargingStation.www.response;

import com.jopool.chargingStation.www.base.entity.ConsumePackage;
import com.jopool.chargingStation.www.base.entity.ConsumePackageItem;

import java.util.List;

/**
 * @Project : chargingStation
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年09月07日 上午10:24
 */
public class ConsumePackageResp {
    private String                       id;
    private String                       name;
    private List<ConsumePackageItemResp> consumePackageItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConsumePackageItemResp> getConsumePackageItems() {
        return consumePackageItems;
    }

    public void setConsumePackageItems(List<ConsumePackageItemResp> consumePackageItems) {
        this.consumePackageItems = consumePackageItems;
    }
}
