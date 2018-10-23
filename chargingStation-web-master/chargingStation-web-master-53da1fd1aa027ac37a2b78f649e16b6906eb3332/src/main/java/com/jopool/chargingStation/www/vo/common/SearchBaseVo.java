package com.jopool.chargingStation.www.vo.common;

import java.io.Serializable;

/**
 * @Package Name : com.jopool.chargingStation.www.vo.common
 * @Author : soupcat
 * @Creation Date : 2017/8/24 下午6:30
 */
public class SearchBaseVo implements Serializable {
    private String keyword;

    private String type;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
