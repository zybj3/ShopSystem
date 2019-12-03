package com.osu.dianping.service;

import com.osu.dianping.dto.ImageHolder;
import com.osu.dianping.dto.ProductExecution;
import com.osu.dianping.entity.Product;

import java.io.InputStream;
import java.util.List;

public interface ProductService {
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageHolderList) throws RuntimeException;

    Product getProductById(long productId);

    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageHolderList) throws RuntimeException;

    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
}
