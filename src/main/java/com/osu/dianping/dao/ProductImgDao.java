package com.osu.dianping.dao;

import com.osu.dianping.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    List<ProductImg> queryProductImgList(long productID);

    int batchInsertProductImg(List<ProductImg> productImgList);

    int deleteProductImgByProductId(long productID);

}
