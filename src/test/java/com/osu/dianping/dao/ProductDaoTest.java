package com.osu.dianping.dao;

import com.osu.dianping.BaseTest;
import com.osu.dianping.entity.Product;
import com.osu.dianping.entity.ProductCategory;
import com.osu.dianping.entity.ProductImg;
import com.osu.dianping.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Autowired
    private ProductDao productDao;

    @Test
    public void testInsertProduct(){
        Shop shop = new Shop();
        shop.setShopId(14L);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(3L);


        Product product = new Product();
        product.setCreateTime(new Date());
        product.setProductName("new product name");
        product.setImgAddr("new product img addr");
        product.setPriority(10);
        product.setEnableStatus(1);
        product.setShop(shop);
        product.setProductCategory(productCategory);

        productDao.insertProduct(product);
    }


    @Test
    public void testBatchInsertProductImg(){
        ProductImg productImg1 = new ProductImg();
        productImg1.setCreateTime(new Date());
        productImg1.setImgDesc("new img");
        productImg1.setPriority(10);
        productImg1.setProductId(10L);
        productImg1.setImgAddr("new img1 addr");


        ProductImg productImg2 = new ProductImg();
        productImg2.setCreateTime(new Date());
        productImg2.setImgAddr("new img2 addr");
        productImg2.setImgDesc("new img2");
        productImg2.setPriority(10);
        productImg2.setProductId(10L);

        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);

        int res = productImgDao.batchInsertProductImg(productImgList);
        Product product = productDao.queryProductById(10L);
        System.out.println(product.getProductImgList().size());
//        System.out.println(res);



    }

    @Test
    public void testDeleteProductImgByProductId(){
        int effectnum = productImgDao.deleteProductImgByProductId(10L);
        System.out.println(effectnum);
    }

    @Test
    public void testUpdateProduct(){
        Product product= new Product();
        ProductCategory productCategory = new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(14L);
        productCategory.setProductCategoryId(4L);
        product.setProductId(10L);
        product.setShop(shop);
        product.setProductName("update product");
        product.setProductCategory(productCategory);

        int effectnum = productDao.updateProduct(product);
        System.out.println(effectnum);
    }




}
