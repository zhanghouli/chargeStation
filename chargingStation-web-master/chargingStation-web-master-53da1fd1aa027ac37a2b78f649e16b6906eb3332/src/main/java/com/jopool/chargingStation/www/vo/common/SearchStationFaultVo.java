package com.jopool.chargingStation.www.vo.common;

/**
 * Created by synn on 2017/9/1.
 */
public class SearchStationFaultVo extends SearchBaseVo {
    private int isViewed = -1;

    public int getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(int isViewed) {
        this.isViewed = isViewed;
    }
}
