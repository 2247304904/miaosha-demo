package com.miaoshaproject.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: miaosha
 * @description: 通用返回
 * @author: 张鹏宇
 * @create: 2022-04-04 19:03
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonReturnType {
    //表明对应请求返回处理结果"success" 或"fail"
    private String status;
    private Object data;

}
