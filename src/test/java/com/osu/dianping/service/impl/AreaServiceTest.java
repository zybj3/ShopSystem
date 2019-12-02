package com.osu.dianping.service.impl;


import com.osu.dianping.BaseTest;
import com.osu.dianping.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;
    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("NineStreet", areaList.get(0).getAreaName());
    }

}
