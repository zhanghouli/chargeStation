/* 
 * @(#)ShortUrlUtil.java    Created on 2014年8月15日
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.jopool.chargingStation.www.base.helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jopool.jweb.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;

/**
 * @author gex
 * @version $Revision: 1.0 $, $Date: 2014年8月15日 上午11:18:47 $
 */
public class ShortUrlUtil {
    private static final Logger logger = LoggerFactory.getLogger(ShortUrlUtil.class);

    // 获取短连接接口
    public static String getShortUrl(String longUrl) {
        String shortUrl = "";
        try {
            String reqUrl = "http://api.t.sina.com.cn/short_url/shorten.json?source=3658320649&url_long=" + URLEncoder.encode(longUrl, "utf-8");
            String sb = HttpUtils.getStringByGet(reqUrl);
            logger.debug("新浪短连接接口返回结果:" + sb);
            JSONArray jsonArr = JSONArray.parseArray(sb);
            if (jsonArr.size() <= 0) {
                return longUrl;
            }
            JSONObject o = (JSONObject) jsonArr.get(0);
            shortUrl = o.getString("url_short");
        } catch (Exception e) {
            shortUrl = longUrl;
        }
        return shortUrl;
    }
}
