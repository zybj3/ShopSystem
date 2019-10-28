package com.osu.shopsystem.web.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osu.shopsystem.entity.PersonInfo;
import com.osu.shopsystem.service.PersonInfoService;
import com.osu.shopsystem.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping("/login")
    private String loginPage() {
        return "user/login";
    }

    @RequestMapping(value = "/verifyuser", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> verifyUser(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String userStr = HttpServletRequestUtil.getString(request, "user");
        PersonInfo personInfo = null;
        try {
            personInfo = objectMapper.readValue(userStr, PersonInfo.class);
            personInfo = personInfoService.queryIfUserExist(personInfo.getName(), personInfo.getPassword());
            if (personInfo == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "wrong username or password");
                return modelMap;
            } else {
                request.getSession().setAttribute("currentUser", personInfo);
                modelMap.put("success", true);
                return modelMap;
            }
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }
}
