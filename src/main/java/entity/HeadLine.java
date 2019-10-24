package entity;
import java.util.Date;

public class HeadLine {
    private Long lineId;
    private String lineName;
    private String lineLink;
    private String lineImg;
    private Integer priority;
    //0不可用 1可用
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;

    public Long getLineId() {
        return lineId;
    }

    public HeadLine setLineId(Long lineId) {
        this.lineId = lineId;
        return this;
    }

    public String getLineName() {
        return lineName;
    }

    public HeadLine setLineName(String lineName) {
        this.lineName = lineName;
        return this;
    }

    public String getLineLink() {
        return lineLink;
    }

    public HeadLine setLineLink(String lineLink) {
        this.lineLink = lineLink;
        return this;
    }

    public String getLineImg() {
        return lineImg;
    }

    public HeadLine setLineImg(String lineImg) {
        this.lineImg = lineImg;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public HeadLine setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public HeadLine setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public HeadLine setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public HeadLine setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
        return this;
    }
}
