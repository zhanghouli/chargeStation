package com.jopool.chargingStation.www.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by synn on 2017/9/5.
 */
public class AuthTreeVo {
    private String              id;
    private String              text;
    private List<AuthTreeVo>    children;
    private Map<String, Object> state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<AuthTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<AuthTreeVo> children) {
        this.children = children;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }
}
