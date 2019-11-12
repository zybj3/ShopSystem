package com.osu.dianping.service.impl;

import com.osu.dianping.dao.HeadLineDao;
import com.osu.dianping.entity.HeadLine;
import com.osu.dianping.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;


    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws Exception {
        return headLineDao.queryHeadLine(headLineCondition);
    }
}
