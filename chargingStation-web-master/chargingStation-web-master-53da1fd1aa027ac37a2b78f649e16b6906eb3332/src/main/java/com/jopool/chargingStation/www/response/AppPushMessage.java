package com.jopool.chargingStation.www.response;

/**
 * Created by synn on 2018/1/8.
 */
public class AppPushMessage {
    private String title;//推送标题
    private String text;//推送内容
    private String transText;//设置穿透内容
    private String logo;//通知栏图标
    private String logoUrl;//通知栏图标打开连接
    private String url;//推送打开连接

    public AppPushMessage() {

    }

    public AppPushMessage(String title, String text) {
        this(title, text, null);
    }

    public AppPushMessage(String title, String text, String url) {
        this.title = title;
        this.text = text;
        this.url = url;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTransText() {
        return transText;
    }

    public void setTransText(String transText) {
        this.transText = transText;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
