package com.osu.dianping.service.impl;

import com.osu.dianping.BaseTest;
import com.osu.dianping.dto.ImageHolder;
import com.osu.dianping.dto.ProductExecution;
import com.osu.dianping.entity.Product;
import com.osu.dianping.entity.ProductCategory;
import com.osu.dianping.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws FileNotFoundException {
        Product product = new Product();
        product.setProductId(10L);
        Shop shop = new Shop();
        shop.setShopId(14L);

        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(3L);

        product.setShop(shop);
        product.setProductCategory(pc);

        product.setProductName("update product 10L");
        product.setProductDesc("test product dest 1");
        product.setPriority(2);
        product.setCreateTime(new Date());
        product.setEnableStatus(1);

        File thumbnailfile = new File("/Users/yuanzhao/desktop/fengwenjuan.jpg");
        InputStream is = new FileInputStream(thumbnailfile);
        ImageHolder thumbnail = new ImageHolder(thumbnailfile.getName(), is);

        File image1 = new File("/Users/yuanzhao/desktop/fengwenjuan.jpg");
        InputStream is1 = new FileInputStream(image1);
        ImageHolder img1 = new ImageHolder(image1.getName(), is1);

        File image2 = new File("/Users/yuanzhao/desktop/fengwenjuan.jpg");
        InputStream is2 = new FileInputStream(image2);
        ImageHolder img2 = new ImageHolder(image2.getName(), is2);


        List<ImageHolder> imageHolders = new ArrayList<>();
        imageHolders.add(img1);
        imageHolders.add(img2);

        ProductExecution pe = productService.modifyProduct(product, thumbnail, imageHolders);
        System.out.println(pe.getStateInfo());

    }
}
