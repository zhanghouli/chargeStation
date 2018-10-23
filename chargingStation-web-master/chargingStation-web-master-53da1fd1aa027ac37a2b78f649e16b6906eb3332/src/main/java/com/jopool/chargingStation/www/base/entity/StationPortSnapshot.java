package com.jopool.chargingStation.www.base.entity;

import com.jopool.jweb.utils.UUIDUtils;

import java.util.Date;

public class StationPortSnapshot {
    private String id;

    private String stationSnapshotId;

    private Integer seq;

    private String number;

    private Boolean isEnabled;

    private Boolean isDeleted;

    private String creator;

    private Date creationTime;

    private String modifier;

    private Date modifyTime;

    public StationPortSnapshot(String creator, StationPort stationPort) {
        this.id = UUIDUtils.createId();
        this.stationSnapshotId = stationPort.getId();
        this.seq = stationPort.getSeq();
        this.number = stationPort.getNumber();
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationSnapshotId() {
        return stationSnapshotId;
    }

    public void setStationSnapshotId(String stationSnapshotId) {
        this.stationSnapshotId = stationSnapshotId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}