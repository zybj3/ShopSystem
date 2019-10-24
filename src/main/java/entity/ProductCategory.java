package entity;

import java.util.Date;

public class ProductCategory {
    private Long productCategoryId;
    private Long shopId;
    private String productCategoryName;
    private Integer priority;
    private Date createTime;

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public ProductCategory setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public ProductCategory setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public ProductCategory setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public ProductCategory setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProductCategory setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }


}
