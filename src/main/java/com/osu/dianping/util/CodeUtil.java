package com.osu.dianping.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
//        System.out.println(request.getSession().getId());
        String verifyCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
//        System.out.println(verifyCodeActual);
        if (verifyCode.equals(verifyCodeActual)){
            return true;
        }
        return false;

    }
}
