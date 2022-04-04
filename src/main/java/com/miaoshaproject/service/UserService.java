package com.miaoshaproject.service;

import com.miaoshaproject.service.model.UserModel;
import org.springframework.stereotype.Service;


public interface UserService {
    //通过用户ID获取用户的信息
    UserModel getUserById(Integer id);
}
