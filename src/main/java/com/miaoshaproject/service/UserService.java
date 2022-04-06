package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.stereotype.Service;


public interface UserService {
    //通过用户ID获取用户的信息
    UserModel getUserById(Integer id);
    //用户注册
    void register(UserModel userModel) throws BusinessException;
}
