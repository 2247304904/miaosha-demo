package com.miaoshaproject.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: miaosha
 * @description: UserModel返回给前端
 * @author: 张鹏宇
 * @create: 2022-04-04 16:11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telephone;
    private String registerMode;
    private String thirdPartyId;

    private String encrptPassword;


}
