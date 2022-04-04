package com.miaoshaproject.error;

/**
 * @program: miaosha
 * @description: 异常类 包装器异常类实现
 * @author: 张鹏宇
 * @create: 2022-04-04 19:37
 **/

public class BusinessException extends Exception implements CommonError{

    private CommonError commonError;

    //直接接收EmBusinessError的传参数用于构造业务异常
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    //接收自定义errMsg 的方式构造业务异常
    public BusinessException(CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
