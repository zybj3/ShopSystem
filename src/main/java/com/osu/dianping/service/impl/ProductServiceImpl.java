package com.osu.dianping.service.impl;

import com.osu.dianping.dao.ProductDao;
import com.osu.dianping.dao.ProductImgDao;
import com.osu.dianping.dto.ImageHolder;
import com.osu.dianping.dto.ProductExecution;
import com.osu.dianping.entity.Product;
import com.osu.dianping.entity.ProductImg;
import com.osu.dianping.enums.ProductStateEnum;
import com.osu.dianping.service.ProductService;
import com.osu.dianping.util.ImageUtil;
import com.osu.dianping.util.PageCalculator;
import com.osu.dianping.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;


    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageHolderList) throws RuntimeException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());

            product.setEnableStatus(1);

            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                int effectNum = productDao.insertProduct(product);
                if (effectNum <= 0) {
                    throw new RuntimeException("can not add shop");
                }
            } catch (Exception e) {
                throw new RuntimeException("can not add shop" + e.toString());
            }

            if (imageHolderList != null && imageHolderList.size() > 0) {
                addProductImgList(product, imageHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageHolderList) throws RuntimeException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            System.out.println("enter!");
            product.setLastEditTime(new Date());
            Product tempProduct = productDao.queryProductById(product.getProductId());
            if (thumbnail != null) {
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);

            }

            if (imageHolderList != null && imageHolderList.size() > 0) {
                if (tempProduct.getProductImgList() != null && tempProduct.getProductImgList().size() > 0) {
                    deleteProductImgList(product.getProductId());
                }
                addProductImgList(product, imageHolderList);
            }

            try {
                System.out.println("enter updateproduct!");
                int effectivenum = productDao.updateProduct(product);
                if (effectivenum <= 0) {
                    throw new RuntimeException("database error");
                } else {
                    return new ProductExecution(ProductStateEnum.SUCCESS, product);
                }
            } catch (RuntimeException e) {
                throw new RuntimeException();
            }


        } else
            return new ProductExecution(ProductStateEnum.EMPTY);

    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        // 页码转换成数据库的行码，并调用dao层取回指定页码的商品列表
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        // 基于同样的查询条件返回该查询条件下的商品总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }


    private void deleteProductImgList(Long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);

        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }

        productImgDao.deleteProductImgByProductId(productId);
    }


    private void addProductImgList(Product product, List<ImageHolder> imageHolderList) {
        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();

        for (ImageHolder imageHolder : imageHolderList) {
            String imageAddr = ImageUtil.generateNormalImg(imageHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imageAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }

        if (productImgList.size() > 0) {
            try {
                int effectNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectNum <= 0) {
                    throw new RuntimeException("can not add image list");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e.toString());
            }
        }
    }

    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }
}
