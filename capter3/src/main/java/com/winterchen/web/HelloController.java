package com.winterchen.web;


import com.winterchen.exception.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Created by Administrator on 2017/11/15.
 */
@Controller
public class HelloController {

    @ResponseBody
    @GetMapping("/hello")
    public String index(@RequestParam String name) throws  Exception{
        return "Hello:" + name;
    }

    @GetMapping("/")
    public String tPage(ModelMap map){
        map.addAttribute("host","http://winterchen.com");
        return "index";
    }

    @GetMapping("/json")
    public String json() throws MyException{
        throw new MyException("发生错误");
    }
}
