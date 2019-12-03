package com.osu.dianping.service.impl;


import com.osu.dianping.dao.AreaDao;
import com.osu.dianping.entity.Area;
import com.osu.dianping.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;

    public List<Area> getAreaList(){
        return areaDao.queryArea();
    }
}
