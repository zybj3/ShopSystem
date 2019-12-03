package com.osu.dianping.dao;

import com.osu.dianping.BaseTest;
import com.osu.dianping.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testqueryProductCategoryListByShopId(){
        List<ProductCategory> productCategory = productCategoryDao.queryProductCategoryListByShopId(29L);
        for (ProductCategory productCategory1: productCategory){
            System.out.println(productCategory1.getProductCategoryId());
        }
    }

    @Test
    public void testBatchInsertProductCatgeory(){
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setPriority(12);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(14L);
        productCategory1.setProductCategoryName("new cat1");


        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setPriority(12);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(14L);
        productCategory2.setProductCategoryName("new cat2");

        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);

        int res = productCategoryDao.batchInsertProductCatgoryList(productCategoryList);
        assertEquals(res, 2);


    }


    @Test
    public void testdeleteProductCategory(){
        long shopId = 14;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryListByShopId(shopId);
        int num = 0;
        for (ProductCategory productCategory: productCategoryList){
            if (productCategory.getProductCategoryName().equals("new")){
                num = productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(), shopId);
            }
        }

        assertEquals(num, 1);
    }
}
