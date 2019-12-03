package com.osu.dianping.service;

import com.osu.dianping.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewList(long shopId) throws RuntimeException;
    int addReview(Review reviewCondition) throws RuntimeException;
}
