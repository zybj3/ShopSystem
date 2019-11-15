package com.osu.dianping.dao;

import com.osu.dianping.BaseTest;
import com.osu.dianping.entity.Area;
import com.osu.dianping.entity.PersonInfo;
import com.osu.dianping.entity.Shop;
import com.osu.dianping.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area1 = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area1.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setArea(area1);
        shop.setShopCategory(shopCategory);
        shop.setOwner(owner);
        shop.setShopName("Test Shop");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test44");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setLastEditTime(new Date());
        shop.setAdvice("test");

        int effectivenum = shopDao.insertShop(shop);
        assertEquals(1, effectivenum);


    }

    @Test
    public void testUpdateShop(){
        Area area = new Area();
        area.setAreaId(11);
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("test desc 12");
        
        shop.setShopAddr("test address22");
        shop.setPhone("test phone22");
        shop.setAdvice("test advice22");
        shop.setArea(area);
        int effectivenum = shopDao.updateShop(shop);
        assertEquals(1, effectivenum);
    }

    @Test
    public void testQueryByShopId(){
        long shopid = 11;
        Shop shop = shopDao.queryByShopId(shopid);
        System.out.println(shop.getArea().getAreaName());
    }

    @Test
    public void testQueryShopList(){
        Shop shopCondition = new Shop();
//        PersonInfo owner = new PersonInfo();
//        owner.setUserId(1L);
//        shopCondition.setOwner(owner);
        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(1L);
        shopCategory.setParent(parent);
        shopCondition.setShopName("aaa");
        shopCondition.setShopCategory(shopCategory);

        List<Shop> res = shopDao.queryShopList(shopCondition, 0, 5);
        System.out.println(res.size());
    }

    @Test
    public void testQueryShopCount(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        int res = shopDao.queryShopCount(shopCondition);
        System.out.println(res);
    }

    @Test
    public void testDeletShopByShopId(){
        long shopId = 15;
        int res = shopDao.deleteShopByShopId(shopId);
        System.out.println(res);

    }
}
