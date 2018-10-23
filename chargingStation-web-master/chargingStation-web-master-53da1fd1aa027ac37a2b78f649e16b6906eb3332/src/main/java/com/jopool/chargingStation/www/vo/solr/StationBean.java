package com.jopool.chargingStation.www.vo.solr;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by gexin on 2017/4/6.
 */
public class StationBean {
    @Field("station_id")
    private String stationId;

    @Field("station_address")
    private String stationAddress;

    @Field("station_position")
    private String stationPosition;

    @Field("station_category")
    private String stationCategory;

    @Field("dist")
    private double dist;

    @Field("score")
    private float    score;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getStationPosition() {
        return stationPosition;
    }

    public void setStationPosition(String stationPosition) {
        this.stationPosition = stationPosition;
    }

    public String getStationCategory() {
        return stationCategory;
    }

    public void setStationCategory(String stationCategory) {
        this.stationCategory = stationCategory;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
