package com.osu.dianping.dao;

import com.osu.dianping.entity.Review;

import java.util.List;

public interface ReviewDao {
    int addReview(Review reviewCondition);
    List<Review> queryReviewList(long shopId);
}
