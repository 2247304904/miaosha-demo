package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.UserVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * @program: miaosha
 * @description: 用户
 * @author: 张鹏宇
 * @create: 2022-04-04 16:03
 **/
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;


    //用户注册接口
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType register(@RequestParam (name="telephone")String telephone,
                                     @RequestParam(name="otpCode")String otpCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")Integer gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //验证手机号和对应的otpCode相符合
        String inSessionOtpCode = (String) this.request.getSession().getAttribute("telephone");

        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        //用户注册的流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setTelephone(telephone);
        userModel.setGender(Byte.valueOf(String.valueOf(gender)));
        userModel.setAge(age);
        userModel.setRegisterMode("byPhone");
        userModel.setEncrptPassword(this.EncodeByMD5(password));

        userService.register(userModel);

        return CommonReturnType.create(null);
    }

    //MD5加密
    public String EncodeByMD5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String newstr= base64Encoder.encode(md5.digest(str.getBytes("utf-8")));

        return newstr;
    }

    //用户获取otp短信接口
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone) {

        //需要按照一定的规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        //将OTP验证码同对应用户的手机号关联，使用httpsession的方式绑定手机号与OTPCDOE
        request.getSession().setAttribute(telphone, otpCode);

        //将OTP验证码通过短信通道发送给用户，省略
        System.out.println("telphone=" + telphone + "&otpCode=" + otpCode);

        return CommonReturnType.create(null);
    }


    @RequestMapping(value = "/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        //调用service服务获取对应的id用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        //若获取的对应用户信息不存在
        if (userModel == null) {

            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }


        //将核心领域模型用户对象转化为可供UI使用的viewObject
        UserVO userVO = convertFromModel(userModel);

        return CommonReturnType.create(userVO);

    }

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        } else {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userModel, userVO);
            return userVO;
        }

    }


}
