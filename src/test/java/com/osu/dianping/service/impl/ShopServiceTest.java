package com.osu.dianping.service.impl;

import com.osu.dianping.BaseTest;
import com.osu.dianping.dao.ShopDao;
import com.osu.dianping.dto.ImageHolder;
import com.osu.dianping.dto.ShopExecution;
import com.osu.dianping.entity.Area;
import com.osu.dianping.entity.PersonInfo;
import com.osu.dianping.entity.Shop;
import com.osu.dianping.entity.ShopCategory;
import com.osu.dianping.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;


    @Test
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setOwner(owner);
        shop.setShopName("Test Shop 10.2");
        shop.setShopDesc("test new ");
        shop.setShopAddr("test new");
        shop.setPhone("test new");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setLastEditTime(new Date());
        shop.setAdvice("test");

        File file = new File("/Users/yuanzhao/desktop/fengwenjuan.jpg");
        InputStream inputStream = new FileInputStream(file);
        ImageHolder imageHolder= new ImageHolder("fengwenjuan.jpg", inputStream);
        ShopExecution shopExecution = shopService.addShop(shop, imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());


    }

    @Test
    public void testModifyShop() throws RuntimeException, FileNotFoundException{
        Shop shop = shopService.getByShopId(1);
        //shop.setShopName("modified name");
        //shopService.modifyShop(shop,null, "");
        File ShopImg = new File("/Users/yuanzhao/desktop/flower.jpeg");
        InputStream is = new FileInputStream(ShopImg);
        ImageHolder imageHolder = new ImageHolder("flower.jpg", is);
        ShopExecution shopExecution = shopService.modifyShop(shop,imageHolder);
        System.out.println(shopExecution.getShop().getShopImg());
    }

    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);
        shopCondition.setShopCategory(shopCategory);

        ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
        System.out.println(se.getShopList().size());
        System.out.println(se.getCount());
    }

    @Test
    public void testDeleteShop(){
        long shopID = 16;
        System.out.println(shopService.deleteShopByShopID(shopID).getStateInfo());

    }
}
