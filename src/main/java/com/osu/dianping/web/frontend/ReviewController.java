package com.osu.dianping.web.frontend;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.osu.dianping.entity.PersonInfo;
import com.osu.dianping.entity.Review;
import com.osu.dianping.service.ReviewService;
import com.osu.dianping.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")

public class ReviewController {
    @Autowired
    private ReviewService reviewService;


    @RequestMapping(value = "/getreview", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> getRiewByShopId(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();

        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        System.out.println(shopId);
        List<Review> reviewList = null;
        if (shopId!=-1) {
            try {
                reviewList = reviewService.getReviewList(shopId);
                modelMap.put("success", true);
                modelMap.put("reviewList", reviewList);
            }catch (RuntimeException e){
                modelMap.put("success",false);
                modelMap.put("errMst", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "null shopId");
        }

        return modelMap;
    }

    @RequestMapping(value = "/addreview", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> addreview(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("currentUser");
        ObjectMapper objectMapper = new ObjectMapper();
        String reviewStr = request.getParameter("reviewStr");
        System.out.println(reviewStr);
        Review review = null;

        try {
            review = objectMapper.readValue(reviewStr, Review.class);
            if (review.getReviewText()==null || review.getReviewText().equals("")){
                System.out.println("text" + review.getReviewText().length());
                modelMap.put("success", false);
                modelMap.put("errMsg", "review can not be null");
                return modelMap;
            }
            review.setPersonInfo(personInfo);
            review.setEditTime(new Date());
            int effectiveNum = reviewService.addReview(review);
            if (effectiveNum<=0){
                modelMap.put("success",false);
                modelMap.put("errMsg", "can not insert this item");
            }else {
                modelMap.put("success", true);
            }

        }catch (IOException e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }
}
