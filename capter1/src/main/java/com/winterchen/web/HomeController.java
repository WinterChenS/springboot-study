package com.winterchen.web;

import com.winterchen.domain.QQUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * index控制类
 * Created by winterchen on 2017/11/29.
 */
@Controller
public class HomeController {





    @ApiOperation(value = "index页面的跳转", notes = "")
    @GetMapping({"/","/index", "home"})
    public String root(){
        return "/index";
    }

    @ApiOperation(value = "登录页跳转", notes = "进入登录页")
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @ApiOperation(value = "用户注册", notes = "进入注册页面")
    @GetMapping("/register")
    public String register(){
        return "register";
    }



   /* @PostMapping("/register")
    public String doRegister(UserEntity userEntity){
        // 此处省略校验逻辑
        if (userService.insert(userEntity))
            return "redirect:register?success";
        return "redirect:register?error";
    }*/
}
