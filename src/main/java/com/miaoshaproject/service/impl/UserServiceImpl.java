package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.UserPasswordDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.dataobject.UserPasswordDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: miaosha
 * @description: UserService实现类
 * @author: 张鹏宇
 * @create: 2022-04-04 16:07
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        //调用userdaomapper获取对应的用户dataobject
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO == null){
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO,userPasswordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(StringUtils.isNoneEmpty(userModel.getName())
                ||userModel.getGender()==null
                ||userModel.getAge()==null
                ||StringUtils.isNoneEmpty(userModel.getTelephone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);

        }


        //实现model->dataObject的方法
        UserDO userDO = convertUserDOFromModel(userModel);
        userDOMapper.insertSelective(userDO);

        //密码
        UserPasswordDO userPasswordDO = convertPasswordDOFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);

    }
    //密码的转换
    private UserPasswordDO convertPasswordDOFromModel(UserModel userModel){
        if (userModel != null) {
            UserPasswordDO userPasswordDO = new UserPasswordDO();
            userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
            userPasswordDO.setUserId(userModel.getId());
            return userPasswordDO;
        }else {
            return null;
        }
    }
    //userDo的转换
    private UserDO convertUserDOFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserDO userDo = new UserDO();
        BeanUtils.copyProperties(userModel,userDo);
        return userDo;

    }



    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {

        if (userDO == null) {
            return null;
        }
        UserModel model = new UserModel();
        BeanUtils.copyProperties(userDO, model);
        if (userPasswordDO != null) {
            model.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }


        return model;
    }
}
