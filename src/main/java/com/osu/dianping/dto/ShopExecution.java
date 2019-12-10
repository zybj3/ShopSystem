package com.osu.dianping.dto;

import com.osu.dianping.entity.Shop;
import com.osu.dianping.enums.ShopStateEnum;

import java.util.List;

public class ShopExecution {
    private int state;
    private String stateInfo;
    private int count;
    private Shop shop;
    private List<Shop> shopList;


    public ShopExecution() {
    }

    public ShopExecution(ShopStateEnum shopStateEnum) {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();

    }

    public ShopExecution(ShopStateEnum shopStateEnum, Shop shop) {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shop = shop;

    }

    public ShopExecution(ShopStateEnum shopStateEnum, List<Shop> shopList) {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shopList = shopList;

    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

}
