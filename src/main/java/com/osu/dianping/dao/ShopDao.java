package com.osu.dianping.dao;

import com.osu.dianping.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    int insertShop(Shop shop);
    int updateShop(Shop shop);
    Shop queryByShopId(long shopId);
    /*
    Rowindex 从第几行开始
    pagesize 返回的条数
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    int deleteShopByShopId(long shopID);
}
