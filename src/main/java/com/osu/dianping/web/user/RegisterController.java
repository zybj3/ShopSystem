package com.osu.dianping.web.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osu.dianping.entity.PersonInfo;
import com.osu.dianping.service.PersonInfoService;
import com.osu.dianping.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class RegisterController {
    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping("/register")
    private String registerPage() {
        return "/user/register";
    }


    @RequestMapping("/adduser")
    @ResponseBody
    Map<String, Object> addUser(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "wrong verify code!");
            return modelMap;
        }

        String userStr = request.getParameter("user");
        PersonInfo personInfo = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            System.out.println(userStr);
            personInfo = objectMapper.readValue(userStr, PersonInfo.class);
            String name = personInfo.getName();
            int checkNameRes = personInfoService.queryPersonInfoByUserName(name);
            if (checkNameRes >= 1) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "this username already existed!");
                return modelMap;
            }

            int effectiveNum = personInfoService.addPersonInfo(personInfo);
            if (effectiveNum < 0) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "jdbc error");
                return modelMap;
            }

        } catch (IOException e) {

            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        modelMap.put("success", true);

        return modelMap;
    }
}
