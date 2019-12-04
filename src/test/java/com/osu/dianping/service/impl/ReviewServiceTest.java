package com.osu.dianping.service.impl;

import com.osu.dianping.BaseTest;
import com.osu.dianping.dao.ReviewDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ReviewServiceTest extends BaseTest {
    @Autowired
    ReviewDao reviewDao;
    @Test
    public void testgetReviewList(){
        reviewDao.queryReviewList(53L);
    }

}
