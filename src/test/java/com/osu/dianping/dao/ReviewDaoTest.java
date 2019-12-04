package com.osu.dianping.dao;

import com.osu.dianping.BaseTest;
import com.osu.dianping.entity.PersonInfo;
import com.osu.dianping.entity.Review;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ReviewDaoTest extends BaseTest {
    @Autowired
    ReviewDao reviewDao;


    @Test
    public void testAddReview(){
        PersonInfo user = new PersonInfo();
        user.setUserId(3L);

        Review review = new Review();
        review.setPersonInfo(user);
        review.setEditTime(new Date());
        review.setShopId(53L);
        review.setReviewText("big mac is not pretty good");

        int effectivenum = reviewDao.addReview(review);
        System.out.println(effectivenum);
    }

    @Test
    public void testqueryReviewList(){
//        PersonInfo user = new PersonInfo();
//        user.setUserId(2L);
//
//        Review review = new Review();
//        review.setPersonInfo(user);

        List<Review> reviewList = reviewDao.queryReviewList(53L);
        System.out.println(reviewList.get(0).getEditTime());
    }

}
