package com.cascv.oas.server.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cascv.oas.core.model.UserModel;
import com.cascv.oas.server.mapper.UserModelMapper;

@Service
public class UserService {
	@Autowired
	private UserModelMapper userModelMapper;
	
  public UserModel findUserByName(String name){
    UserModel userModel = new UserModel();
    userModel.setName(name);;
    userModel.setNickname(name+"NICK");
    userModel.setPassword("29c605bf7f42e96b72d803cb34e2562a");//密码明文是123456
    userModel.setSalt("wxKYXuTPST5SG0jMQzVPsg==");//加密密码的盐值
    userModel.setId(new Random().nextLong());//随机分配一个id
    //userModel.setCreated(new Date());
    return userModel;
  }
  //用户注册
  public Integer insertUser(UserModel userModel) {
	return userModelMapper.insertUser(userModel);
	  
  }
  //删除用户
  public Integer deleteUser(UserModel userModel) {
	userModelMapper.deleteUser(userModel);
	return userModelMapper.deleteUser(userModel);
	  
  }
  //查看用户个人信息
  public List<UserModel> selectUserInfoById(String id){
	return userModelMapper.selectUserInfoById(id);
	  
  }
  //用户登录
  public UserModel login(UserModel userModel) {
	  userModel = this.userModelMapper.login(userModel.getName(), userModel.getPassword());
	  return userModel;
	  
  }
  
  
}
