package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Fence;

/**
 * Created by synn on 2017/10/25.
 */
public class FenceTreeVo {
    private String id;
    private String governmentId;
    private String text;
    private String fencePoints;

    public FenceTreeVo(){

    }
    public FenceTreeVo(Fence fence){
        this.id=fence.getId();
        this.governmentId=fence.getGovernmentId();
        this.text=fence.getName();
        this.fencePoints=fence.getFencePoints();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGovernmentId() {
        return governmentId;
    }

    public void setGovernmentId(String governmentId) {
        this.governmentId = governmentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFencePoints() {
        return fencePoints;
    }

    public void setFencePoints(String fencePoints) {
        this.fencePoints = fencePoints;
    }
}
