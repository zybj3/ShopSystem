package com.osu.dianping.service;

import com.osu.dianping.dto.ImageHolder;
import com.osu.dianping.dto.ShopExecution;
import com.osu.dianping.entity.Shop;

public interface ShopService {
    ShopExecution addShop(Shop shop, ImageHolder imageHolder);
    Shop getByShopId(long shopId);
    ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) throws RuntimeException;
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
    ShopExecution deleteShopByShopID(long shopId);

}
