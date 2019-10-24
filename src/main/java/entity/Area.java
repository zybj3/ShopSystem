package entity;

import javax.xml.crypto.Data;
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

    public Data getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Data lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    private Data lastEditTime;

    public Date getCreaTime() {
        return creaTime;
    }

    public void setCreaTime(Date creaTime) {
        this.creaTime = creaTime;
    }
}
