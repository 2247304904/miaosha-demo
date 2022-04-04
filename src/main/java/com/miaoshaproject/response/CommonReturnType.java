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

    //若status==success 则data内返回前端需要的json数据
    //若status==fail 则data内使用通用的错误码格式
    private Object data;

    //定义一个通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }


    public static CommonReturnType create(Object result,String status){
        CommonReturnType type = new CommonReturnType(status,result);

        return type;
    }
}
