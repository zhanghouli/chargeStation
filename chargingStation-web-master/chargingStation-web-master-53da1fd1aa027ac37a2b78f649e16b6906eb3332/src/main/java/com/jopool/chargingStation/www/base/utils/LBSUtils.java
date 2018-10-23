package com.jopool.chargingStation.www.base.utils;

import com.jopool.jweb.utils.MathUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by gexin on 15/11/18.
 */
public class LBSUtils {
    private static       int    DEFAULT_RECT = 1;//默认每个格子2公里,1km=0.01°
    private final static double PI           = 3.14159265358979323; // 圆周率
    private final static double R            = 6371229; // 地球的半径

    /**
     * 获取key
     *
     * @param lng
     * @param lat
     * @return
     */
    public static String getKey(int lng, int lat) {
        lng = getNearbyL(lng);
        lat = getNearbyL(lat);
        String key = String.format("%1$04d%2$04d", lng, lat);
        return key;
    }

    /**
     * 获取keys
     *
     * @param lng
     * @param lat
     * @param scope
     * @return
     */
    public static Set<String> getBoundKeys(int lng, int lat, int scope) {
        Set<String> keys = new HashSet<String>();
        int lngE5 = getNearbyL(lng);
        int latE5 = getNearbyL(lat);
        for (int i = -scope; i <= scope; i++) {
            for (int j = -scope; j <= scope; j++) {
                keys.add(String.format("%1$04d%2$04d", lngE5 + i, latE5 + j));
            }
        }
        return keys;
    }

    /**
     * 算距离,单位米
     *
     * @param lngE51
     * @param latE51
     * @param lngE52
     * @param latE52
     * @return
     */
    public static double distance(int lngE51, int latE51, int lngE52, int latE52) {
        double x, y, distance;
        x = (MathUtils.div(lngE52, 1E5, 5) - MathUtils.div(lngE51, 1E5, 5)) * PI * R
                * Math.cos(((MathUtils.div(latE51, 1E5, 5) + MathUtils.div(latE52, 1E5, 5)) / 2) * PI / 180) / 180;
        y = (MathUtils.div(latE52, 1E5, 5) - MathUtils.div(latE51, 1E5, 5)) * PI * R / 180;
        distance = Math.hypot(x, y);
        return distance;
    }

    /**
     * 获取最近的公里
     *
     * @param l
     * @return
     */
    private static int getNearbyL(int l) {
        if (l / 1000 % DEFAULT_RECT != 0 && (l % (DEFAULT_RECT * 1000) > DEFAULT_RECT * 500)) {
            l = l / 1000 / DEFAULT_RECT + 1;
        } else {
            l = l / 1000 / DEFAULT_RECT;
        }
        return l * DEFAULT_RECT;
    }

    public static void main(String[] args) {
//        int L_LNG = 12012345;
//        int R_LNG = 12014345;
//        int T_LAT = 3014345;
//        int B_LAT = 3012345;
//        int RATE = 1 * 1000;
//        Set<String> set = new HashSet<String>();
//        for (int i = L_LNG; i < R_LNG; i += RATE) {
//            for (int j = T_LAT; j > B_LAT; j -= RATE) {
//                set.add(getKey(i, j));
//            }
//        }
//        System.out.println(set.size());

//        Set<String> keys = getBoundKeys(12012079, 3028644, 2);
//        System.out.println(keys.size());
        System.out.println(distance(12143001, 2864781, 12140633, 2866224));
        System.out.println(getKey(12143001, 2864781));
        System.out.print(LBSUtils.getBoundKeys(12143001, 2864781, 10));
    }

}
