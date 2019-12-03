package com.osu.dianping.service.impl;

import com.osu.dianping.dao.ReviewDao;
import com.osu.dianping.entity.Review;
import com.osu.dianping.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewDao reviewDao;

    @Override
    public List<Review> getReviewList(long shopId) {
        List<Review> reviewList = reviewDao.queryReviewList(shopId);
        return reviewList;
    }

    @Override
    public int addReview(Review reviewCondition) {
        int effectiveNum = reviewDao.addReview(reviewCondition);
        return effectiveNum;
    }
}
