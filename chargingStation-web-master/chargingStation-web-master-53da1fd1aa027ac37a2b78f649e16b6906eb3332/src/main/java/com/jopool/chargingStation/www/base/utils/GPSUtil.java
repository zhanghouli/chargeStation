package com.jopool.chargingStation.www.base.utils;

import com.alibaba.fastjson.JSONObject;
import com.jopool.jweb.utils.HttpUtils;

/**
 * 坐标转换工具
 * Created by gexin on 2017/10/27.
 */
public class GPSUtil {
    public static class Gps {
        public double lng;
        public double lat;

        public Gps() {
        }

        public Gps(double lng, double lat) {
            this.lng = lng;
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return lng + "," + lat;
        }
    }

    public static Gps toBaiduGPS(double lng, double lat) {
        JSONObject json = HttpUtils.getJsonByGet("http://api.map.baidu.com/geoconv/v1/?coords=" + lng + "," + lat + "&from=1&to=5&ak=5ylayfeKso2O4RaQep2K8AWC183X2QM6");
        if (null == json || json.isEmpty()) {
            return null;
        }
        int status = json.getInteger("status");
        if (0 != status) {
            return null;
        }
        JSONObject result = json.getJSONArray("result").getJSONObject(0);
        return new Gps(result.getDoubleValue("x"), result.getDoubleValue("y"));
    }


    public static void main(String[] args) {
        double lat = 39.990912172420714;
        double lng = 116.32715863448607;
        Gps gps = GPSUtil.toBaiduGPS(lng, lat);
        System.out.println(gps);
    }
}
