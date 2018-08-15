package com.cascv.oas.server.mapper;


import java.util.List;

import com.cascv.oas.core.model.UserModel;

public interface UserModelMapper {
	 Integer insertUser(UserModel userModel);
	 Integer deleteUser(UserModel userModel);
	 List<UserModel> selectUserInfoById(String id);
	 UserModel login(String name, String password);
}
