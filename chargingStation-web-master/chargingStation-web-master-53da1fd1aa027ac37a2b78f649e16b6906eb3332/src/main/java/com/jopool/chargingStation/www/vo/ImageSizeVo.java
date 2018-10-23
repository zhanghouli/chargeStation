package com.jopool.chargingStation.www.vo;

/**
 * Created by synn on 2017/9/4.
 */
public class ImageSizeVo {
    //上传后的名称
    private String name;
    private int    height;
    private int    width;
    private int    size;
    //图片原始名字
    private String orgName;
    //相对路径
    private String relativeFolder;
    //绝对路径
    private String remoteRelativeUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRelativeFolder() {
        return relativeFolder;
    }

    public void setRelativeFolder(String relativeFolder) {
        this.relativeFolder = relativeFolder;
    }

    public String getRemoteRelativeUrl() {
        return remoteRelativeUrl;
    }

    public void setRemoteRelativeUrl(String remoteRelativeUrl) {
        this.remoteRelativeUrl = remoteRelativeUrl;
    }
}
