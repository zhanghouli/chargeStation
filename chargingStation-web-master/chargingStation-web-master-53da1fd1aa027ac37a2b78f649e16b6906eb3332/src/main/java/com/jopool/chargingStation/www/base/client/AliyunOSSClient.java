package com.jopool.chargingStation.www.base.client;

import com.aliyun.oss.OSSClient;
import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;


/**
 * Created by gexin on 16/7/25.
 */
public class AliyunOSSClient {
    private static OSSClient ossClient;

    public static OSSClient getInstance() {
        if (null == ossClient) {
            synchronized (AliyunOSSClient.class) {
                if (null == ossClient) {
                    String endpoint = ApplicationConfigHelper.getoSSEndpoint();
                    String accessKeyId = ApplicationConfigHelper.getoSSAccessKeyId();
                    String accessKeySecret = ApplicationConfigHelper.getoSSAccessKeySecret();
                    ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                }
            }
        }
        return ossClient;
    }
}
