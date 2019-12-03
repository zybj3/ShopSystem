package com.osu.dianping.service;

import com.osu.dianping.dto.ProductCategoryExecution;
import com.osu.dianping.entity.Product;
import com.osu.dianping.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> queryProductCategoryListByShopId(long shopId);
    ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList);
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId);

}
