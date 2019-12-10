package com.osu.dianping.service.impl;

import com.osu.dianping.dao.ShopDao;
import com.osu.dianping.dto.ImageHolder;
import com.osu.dianping.dto.ShopExecution;
import com.osu.dianping.entity.Shop;
import com.osu.dianping.enums.ShopStateEnum;
import com.osu.dianping.service.ShopService;
import com.osu.dianping.util.ImageUtil;
import com.osu.dianping.util.PageCalculator;
import com.osu.dianping.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            //添加店铺信息
            int effectiveNum = shopDao.insertShop(shop);
            if (effectiveNum<=0){
                throw new RuntimeException("fail");
            }else {
                if (thumbnail.getImage()!=null){
                    //save image
                    try {
                        addShopImg(shop, thumbnail);
                    }catch (Exception e){
                        throw new RuntimeException("addShopImg error" + e.getMessage());
                    }

                    //update image
                    effectiveNum = shopDao.updateShop(shop);
                    if (effectiveNum<=0){
                        throw new RuntimeException("cannot update image info");
                    }

                }
            }

        } catch (Exception e) {
            throw new RuntimeException("addShopError" + e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK,  shop);
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        String dest= PathUtil.getShopImgPath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        shop.setShopImg(shopImgAddr);
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws RuntimeException {
        //1是否需要处理图片
        if (shop==null || shop.getShopId()==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            try {
                if (thumbnail.getImage() != null && !thumbnail.getImageName().equals("")) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, thumbnail);
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectiveNum = shopDao.updateShop(shop);
                if (effectiveNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            }
            catch (Exception e){
                throw new RuntimeException("modifyshop error" + e.getMessage());
            }

        }
    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList!=null){
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return se;
    }

    @Override
    public ShopExecution deleteShopByShopID(long shopId) {

        int effectRow = shopDao.deleteShopByShopId(shopId);
        if (effectRow<=0){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            return new ShopExecution(ShopStateEnum.SUCCESS);
        }
    }
}
