package com.osu.dianping.dao;

import com.osu.dianping.entity.Area;

import java.util.List;

public interface AreaDao {
    /*
    列出区域列表
    * @return areaList
     */
    List<Area> queryArea();
}
