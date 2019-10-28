package com.osu.dianping.entity;

import java.util.Date;

public class Area {


    private Integer areaId;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    private String areaName;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    private Integer priority;
    private Date creaTime;

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    private Date lastEditTime;

    public Date getCreaTime() {
        return creaTime;
    }

    public void setCreaTime(Date creaTime) {
        this.creaTime = creaTime;
    }
}
