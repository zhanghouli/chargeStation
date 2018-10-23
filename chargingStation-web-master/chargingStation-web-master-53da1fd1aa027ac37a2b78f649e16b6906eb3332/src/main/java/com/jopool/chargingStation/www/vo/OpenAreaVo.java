package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.OpenArea;

/**
 * Created by synn on 2017/9/4.
 */
public class OpenAreaVo  extends OpenArea{
    private String id;
    private String areaId;
    //城市名字
    private String name;
    private String  provinceCode;
    private String cityCode;

    public OpenAreaVo(){

    }

    public OpenAreaVo(OpenArea openArea,String name){
        this.id=openArea.getId();
        this.areaId=openArea.getAreaId();
        this.name=name;
        this.setCreationTime(openArea.getCreationTime());
        this.setModifyTime(openArea.getModifyTime());
        this.setIsEnabled(openArea.getIsEnabled());
    }

    public OpenAreaVo(OpenArea openArea,String provinceCode,String cityCode){
        this.id=openArea.getId();
        this.provinceCode=provinceCode;
        this.cityCode=cityCode;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getAreaId() {
        return areaId;
    }

    @Override
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
