package entity;

import java.util.Date;
import java.util.List;

public class Product {
    private Long productId;
    private String productName;
    private String productDesc;

    //简略图
    private String imgAddr;
    private String normalPrice;
    private String promotionPrice;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;

    //0:下架， 1:展示
    private Integer enableStatus;
    private List<ProductImg> productImgList;
    private ProductCategory productCategory;
    private Shop shop;


    public Long getProductId() {
        return productId;
    }

    public Product setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public Product setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public Product setProductDesc(String productDesc) {
        this.productDesc = productDesc;
        return this;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public Product setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
        return this;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public Product setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
        return this;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public Product setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public Product setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Product setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public Product setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
        return this;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public Product setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
        return this;
    }

    public List<ProductImg> getProductImgList() {
        return productImgList;
    }

    public Product setProductImgList(List<ProductImg> productImgList) {
        this.productImgList = productImgList;
        return this;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public Product setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public Shop getShop() {
        return shop;
    }

    public Product setShop(Shop shop) {
        this.shop = shop;
        return this;
    }
}
