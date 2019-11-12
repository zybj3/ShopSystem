package com.osu.dianping.service;

import com.osu.dianping.entity.HeadLine;

import java.util.List;

public interface HeadLineService {
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws Exception;
}
