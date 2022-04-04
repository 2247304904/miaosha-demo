package com.miaoshaproject.controller.viewobject;

import lombok.Data;

/**
 * @program: miaosha
 * @description: UserView 返回给前端展示 要隐藏密码
 * @author: 张鹏宇
 * @create: 2022-04-04 18:07
 **/
@Data
public class UserVO {
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telephone;


}
