package com.osu.dianping.service.impl;

import com.osu.dianping.dao.ProductCategoryDao;
import com.osu.dianping.dao.ProductDao;
import com.osu.dianping.dto.ProductCategoryExecution;
import com.osu.dianping.entity.ProductCategory;
import com.osu.dianping.enums.ProductCategoryStateEnum;
import com.osu.dianping.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> queryProductCategoryListByShopId(long shopId) {
        List<ProductCategory> productCategoryDaoList = productCategoryDao.queryProductCategoryListByShopId(shopId);
        return productCategoryDaoList;
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList) {
        if (productCategoryList!=null || productCategoryList.size()>0){
            try {
                int effectnumber = productCategoryDao.batchInsertProductCatgoryList(productCategoryList);
                if (effectnumber<=0){
                    throw new RuntimeException("effective number is 0");
                }else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e){
                throw new RuntimeException("error:" + e.getMessage());
            }
        }else
        return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) {
        System.out.println(productCategoryId);
        try {
//            System.out.println("enter!");
            int effectNum = productDao.updateProductCategorytobenull(productCategoryId);
//            System.out.println("effective!");

            if (effectNum<0){
                throw new RuntimeException("update failed");
            }
        }catch (Exception e){
            throw new RuntimeException(e.toString());
        }


        try {
            int effectNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectNum<=0){
                throw  new RuntimeException("JDBC error");
            }else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        return null;
    }
}
