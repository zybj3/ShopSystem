package com.osu.shopsystem.web.user;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class RegisterController {
    @RequestMapping("/register")
    private String registerPage() {
        return "/user/register";
    }


}
