package com.osu.dianping.enums;

public enum ShopStateEnum {
    SUCCESS(1, "success"), NULL_SHOPID(-1002, "ShopId is null"), NULL_SHOP(-1003, "Shop is null");
    private int state;
    private String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }


    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopStateEnum stateOf(int state) {
        for (ShopStateEnum shopStateEnum : values()) {
            if (shopStateEnum.getState() == state) {
                return shopStateEnum;
            }
        }

        return null;
    }
}
