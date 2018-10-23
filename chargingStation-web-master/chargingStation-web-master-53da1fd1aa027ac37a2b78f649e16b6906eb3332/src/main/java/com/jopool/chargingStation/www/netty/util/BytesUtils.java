package com.jopool.chargingStation.www.netty.util;

import com.jopool.chargingStation.www.netty.common.TPMSConsts;

/**
 * Created by gexin on 2017/10/24.
 */
public class BytesUtils {
    /**
     * 转字符串
     *
     * @param data
     * @param startIndex
     * @param lenth
     * @return
     */
    public static String parseStringFromBytes(byte[] data, int startIndex, int lenth) {
        return parseStringFromBytes(data, startIndex, lenth, null);
    }

    /**
     * 转字符串
     *
     * @param data
     * @param startIndex
     * @param lenth
     * @param defaultVal
     * @return
     */
    public static String parseStringFromBytes(byte[] data, int startIndex, int lenth, String defaultVal) {
        try {
            byte[] tmp = new byte[lenth];
            System.arraycopy(data, startIndex, tmp, 0, lenth);
            return new String(tmp, TPMSConsts.string_charset);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultVal;
        }
    }

    /**
     * 转字符串
     *
     * @param data
     * @param startIndex
     * @param lenth
     * @return
     */
    public static String parseHexStringFromBytes(byte[] data, int startIndex, int lenth) {
        try {
            byte[] tmp = new byte[lenth];
            System.arraycopy(data, startIndex, tmp, 0, lenth);
            return HexStringUtils.toHexString(tmp);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
