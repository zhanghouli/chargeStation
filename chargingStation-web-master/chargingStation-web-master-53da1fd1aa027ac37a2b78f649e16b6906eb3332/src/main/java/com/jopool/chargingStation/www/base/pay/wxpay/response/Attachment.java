package com.jopool.chargingStation.www.base.pay.wxpay.response;

import java.io.BufferedInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: xiaomao
 * Date: 15-5-27
 * Time: 上午11:59
 * To change this template use File | Settings | File Templates.
 * 下载文件对象
 */
public class Attachment {

    private String fileName;
    private String fullName;
    private String suffix;
    private String contentLength;
    private String contentType;
    private BufferedInputStream fileStream;
    private String error;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public BufferedInputStream getFileStream() {
        return fileStream;
    }

    public void setFileStream(BufferedInputStream fileStream) {
        this.fileStream = fileStream;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
