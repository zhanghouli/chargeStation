package com.jopool.chargingStation.www.base.pay.wxpay.message;

/**
 * Created with IntelliJ IDEA.
 * User: xiaomao
 * Date: 15-5-27
 * Time: 下午1:51
 * To change this template use File | Settings | File Templates.
 * 模板消息数据对象
 */
public abstract class TemplateMessage {
    private String       touser;
    private String       template_id;
    private String       url;
    private String       topcolor;
    private TemplateData data;

    public TemplateMessage(String touser, String template_id, String url,String topcolor, TemplateData data) {
        this.touser = touser;
        this.template_id = template_id;
        this.url = url;
        this.topcolor=topcolor;
        this.data = data.getData();
    }


    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public TemplateData getData() {
        return data;
    }

    public void setData(TemplateData data) {
        this.data = data;
    }

}
