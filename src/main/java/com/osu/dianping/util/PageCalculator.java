package com.osu.dianping.util;

public class PageCalculator {
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return pageIndex > 0 ? (pageIndex - 1) * pageSize : 0;
    }

    public static int calculateTotalPage(int totalCount, int pageSize){
        if (totalCount%pageSize==0){
            return totalCount/pageSize;
        }else {
            return totalCount/pageSize + 1;
        }
    }
}
