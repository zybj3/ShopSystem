package com.osu.dianping.dao;


import com.osu.dianping.BaseTest;
import com.osu.dianping.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryArea();
        assertEquals(3, areaList.size());
        System.out.print(areaList.get(0).getAreaName());
    }
}
