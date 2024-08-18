package com.hloong.hlinterface.controller;

import com.hloong.client_sdk.model.User;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name, HttpServletRequest request){
        System.out.println(request.getHeader("H-Loong"));
        return "GET 你的名字是:" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name){
        return "POST 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user){
        return "POST 你的名字是：" + user.getUsername();
    }

}
