package com.jopool.chargingStation.www.base.helper;

import com.jopool.jweb.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gexin on 15/9/12.
 */
public abstract class IPUtils {

    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if(!StringUtils.isEmpty(ip)&&ip.startsWith("192.168")){
            ip="218.109.31.150";
        }
        return ip;
    }
}
