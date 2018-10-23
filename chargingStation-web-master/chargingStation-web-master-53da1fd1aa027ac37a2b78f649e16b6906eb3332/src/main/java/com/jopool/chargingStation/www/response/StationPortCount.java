package com.jopool.chargingStation.www.response;

/**
 * @Project : chargingStation-web
 * @Package Name : com.jopool.chargingStation.www.response
 * @Author : soupcat
 * @Creation Date : 2017年09月19日 上午10:37
 */
public class StationPortCount {
    private int allCount;
    private int workingCount;
    private int freeCount;

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getWorkingCount() {
        return workingCount;
    }

    public void setWorkingCount(int workingCount) {
        this.workingCount = workingCount;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }
}
