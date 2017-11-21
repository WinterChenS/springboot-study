package com.winterchen.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Created by Administrator on 2017/11/15.
 */
@Controller
public class HelloController {

    @ResponseBody
    @GetMapping("/hello")
    public String index() throws  Exception{
        throw new Exception("发生错误");
    }

    @GetMapping("/")
    public String tPage(ModelMap map){
        map.addAttribute("host","http://winterchen.com");
        return "index";
    }
}
