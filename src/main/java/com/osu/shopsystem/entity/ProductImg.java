package com.osu.shopsystem.entity;

import java.util.Date;

public class ProductImg {
    private Long productImgId;
    private String imgAddr;
    private String imgDesc;
    private Integer priority;
    private Date createTime;
    private Long productId;


    public Long getPriductImgId() {
        return productImgId;
    }

    public ProductImg setPriductImgId(Long priductImgId) {
        this.productImgId = priductImgId;
        return this;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public ProductImg setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
        return this;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public ProductImg setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public ProductImg setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProductImg setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public ProductImg setProductId(Long productId) {
        this.productId = productId;
        return this;
    }
}
