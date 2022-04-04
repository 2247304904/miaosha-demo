package com.miaoshaproject.controller;

import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: miaosha
 * @description: 用户
 * @author: 张鹏宇
 * @create: 2022-04-04 16:03
 **/
@Controller("user")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public UserModel getUser(@RequestParam(name="id")Integer id){
        //调用service服务获取对应的id用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);
        return userModel;

    }
}
