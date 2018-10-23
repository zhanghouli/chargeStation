/* 
 * @(#)ApplicationConfigHelper.java    Created on 2012-1-19
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id: ApplicationConfigHelper.java 24913 2012-02-15 10:55:51Z huangwq $
 */
package com.jopool.chargingStation.www.base.helper;

import com.jopool.jweb.enums.ModeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * 系统配置
 *
 * @author gex
 * @version $Revision: 24913 $, $Date: 2013-11-06 18:55:51 +0800 $
 */
public abstract class ApplicationConfigHelper {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigHelper.class);

    private ApplicationConfigHelper() {
    }

    private static final String MODE = "app.mode";

    private static ModeEnum mode;
    private static String   filePath;
    private static String   jppushRestUrl;
    private static String   jppushAppId;
    private static String   jppushAppSecret;
    private static String   baseUrl;
    private static String   oSSAccessKeyId;
    private static String   oSSAccessKeySecret;
    private static String   oSSEndpoint;
    private static String   oSSbucketName;
    private static String   oSSOpenUrl;
    private static String   memcachedUrl;
    //
    private static String   jbrainPk;
    private static String   jbrainAppId;
    private static String   jbrainWebsite;
    private static String   scanWebsite;
    //
    private static String   mqttUrl;
    private static String   mqttClientId;
    private static String   mqttUserName;
    private static String   mqttPassword;
    //
    private static String   getuiAppId;
    private static String   getuiAppKey;
    private static String   getuiMasterSecret;

    static {
        Properties p = new Properties();
        try {
            p.load(ApplicationConfigHelper.class.getResourceAsStream("/applicationResources.properties"));
        } catch (IOException e) {
            logger.error("#### ApplicationConfigHelper", "", e);
        }

        filePath = p.getProperty("file.path", "");
        mode = ModeEnum.valueOf(Integer.parseInt(p.getProperty(MODE, "0")));
        jppushRestUrl = p.getProperty("jppush.rest.url");
        jppushAppId = p.getProperty("jppush.appId");
        jppushAppSecret = p.getProperty("jppush.appSecret");
        baseUrl = p.getProperty("base.url");
        oSSAccessKeyId = p.getProperty("oss.accessKeyId");
        oSSAccessKeySecret = p.getProperty("oss.accessKeySecret");
        oSSEndpoint = p.getProperty("oss.endpoint");
        oSSbucketName = p.getProperty("oss.bucketName");
        oSSOpenUrl = p.getProperty("oss.openUrl");
        memcachedUrl = p.getProperty("memcached.url");
        //
        jbrainPk = p.getProperty("jbrain.pk");
        jbrainAppId = p.getProperty("jbrain.appId");
        jbrainWebsite = p.getProperty("jbrain.website");
        scanWebsite = p.getProperty("scan.website");
        //
        mqttUrl = p.getProperty("mqtt.url");
        mqttClientId = p.getProperty("mqtt.clientId");
        mqttUserName = p.getProperty("mqtt.userName");
        mqttPassword = p.getProperty("mqtt.password");
        //
        getuiAppId = p.getProperty("getui.appid");
        getuiAppKey = p.getProperty("getui.appkey");
        getuiMasterSecret = p.getProperty("getui.master.secret");
    }


    public static String getFilePath() {
        return filePath;
    }

    public static ModeEnum getMode() {
        return mode;
    }

    public static String getJppushRestUrl() {
        return jppushRestUrl;
    }

    public static String getJppushAppId() {
        return jppushAppId;
    }

    public static String getJppushAppSecret() {
        return jppushAppSecret;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getoSSAccessKeyId() {
        return oSSAccessKeyId;
    }

    public static String getoSSAccessKeySecret() {
        return oSSAccessKeySecret;
    }

    public static String getoSSEndpoint() {
        return oSSEndpoint;
    }

    public static String getoSSbucketName() {
        return oSSbucketName;
    }

    public static String getoSSOpenUrl() {
        return oSSOpenUrl;
    }

    public static String getMemcachedUrl() {
        return memcachedUrl;
    }

    public static String getJbrainPk() {
        return jbrainPk;
    }

    public static String getJbrainAppId() {
        return jbrainAppId;
    }

    public static String getJbrainWebsite() {
        return jbrainWebsite;
    }

    public static String getScanWebsite() {
        return scanWebsite;
    }

    public static String getMqttUrl() {
        return mqttUrl;
    }

    public static String getMqttClientId() {
        return mqttClientId;
    }

    public static String getMqttUserName() {
        return mqttUserName;
    }

    public static String getMqttPassword() {
        return mqttPassword;
    }

    public static String getGetuiAppId() {
        return getuiAppId;
    }

    public static String getGetuiAppKey() {
        return getuiAppKey;
    }

    public static String getGetuiMasterSecret() {
        return getuiMasterSecret;
    }
}
