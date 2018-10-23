package com.jopool.chargingStation.www.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * File Name             : FileUtil
 * Author                :
 * Create Date         : 13-11-16
 * Description           :
 * <p/>
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) OnlineGJ.cn / RealShopping.cn All Rights Reserved
 * *******************************************************************************************
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static final int BLACK = 0xFF66CC;
    private static final int WHITE = 0xFFFFFFFF;


    /**
     * @param srcInputStream
     * @param newFileParentFolderAbsolutePath
     * @param newFileName
     * @throws IOException
     */
    public static void saveFile(InputStream srcInputStream, String newFileParentFolderAbsolutePath, String newFileName) throws IOException {
        File parentFolderFile = new File(newFileParentFolderAbsolutePath);
        if (!parentFolderFile.exists()) {
            parentFolderFile.mkdirs();
        }
        OutputStream bos = new FileOutputStream(newFileParentFolderAbsolutePath + "/" + newFileName);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = srcInputStream.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        bos.close();
        srcInputStream.close();
    }
}
