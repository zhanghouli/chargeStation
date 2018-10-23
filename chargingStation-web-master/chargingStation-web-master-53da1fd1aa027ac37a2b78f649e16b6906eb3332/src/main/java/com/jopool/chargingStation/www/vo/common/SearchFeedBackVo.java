package com.jopool.chargingStation.www.vo.common;

/**
 * Created by synn on 2017/8/31.
 */
public class SearchFeedBackVo extends SearchBaseVo {
    private int isViewed = -1;

    private String type;

    public int getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(int isViewed) {
        this.isViewed = isViewed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
