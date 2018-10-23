package com.jopool.chargingStation.www.vo;

import java.util.Date;

/**
 * Created by synn on 2017/8/29.
 */
public class StationVo {
    private String  id;
    private String  name;
    private String  number;
    private String  area;
    private String  areaDes;
    private String  address;
    private Integer portCount;
    private Integer electricBill;
    private String  status;
    private Date    creationTime;
    private Date    modifyTime;
    private String  operatorName;
    private String  estateName;
    private String  estatePhone;
    private String  consumePackageName;
    private String  consumePackageId;
    private String  stationAddress;

    public StationVo() {

    }

    public StationVo(StationVo stationVo) {
        this.id = stationVo.getId();
        this.name = stationVo.getName();
        this.number = stationVo.getNumber();
        this.area = stationVo.getArea();
        this.areaDes = stationVo.getAreaDes();
        this.address = stationVo.getAddress();
        this.portCount = stationVo.getPortCount();
        this.electricBill = stationVo.getElectricBill();
        this.status = stationVo.getStatus();
        this.creationTime = stationVo.getCreationTime();
        this.modifyTime = stationVo.getModifyTime();
        this.operatorName = stationVo.getOperatorName();
        this.estateName = stationVo.getEstateName();
        this.estatePhone = stationVo.getEstatePhone();
        this.consumePackageName = stationVo.getConsumePackageName();
        this.consumePackageId = stationVo.getConsumePackageId();
        this.stationAddress = stationVo.getStationAddress();
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public String getEstatePhone() {
        return estatePhone;
    }

    public void setEstatePhone(String estatePhone) {
        this.estatePhone = estatePhone;
    }

    public String getConsumePackageName() {
        return consumePackageName;
    }

    public void setConsumePackageName(String consumePackageName) {
        this.consumePackageName = consumePackageName;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaDes() {
        return areaDes;
    }

    public void setAreaDes(String areaDes) {
        this.areaDes = areaDes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPortCount() {
        return portCount;
    }

    public void setPortCount(Integer portCount) {
        this.portCount = portCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getElectricBill() {
        return electricBill;
    }

    public void setElectricBill(Integer electricBill) {
        this.electricBill = electricBill;
    }

    public String getConsumePackageId() {
        return consumePackageId;
    }

    public void setConsumePackageId(String consumePackageId) {
        this.consumePackageId = consumePackageId;
    }
}
