package com.cascv.oas.server.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cascv.oas.core.common.ResponseData;
import com.cascv.oas.core.common.ResponseEntity;
import com.cascv.oas.core.model.UserModel;
import com.cascv.oas.core.utils.StringUtils;
import com.cascv.oas.server.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {

/*	@RequestMapping(value="/login", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody UserModel userModel,HttpServletResponse response) {
		log.info("authentication name {}, password {}", userModel.getName(), userModel.getPassword());
		UsernamePasswordToken token = new UsernamePasswordToken(userModel.getName(), userModel.getPassword(), false);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            response.addHeader("token", subject.getSession().getId().toString());
            return new ResponseEntity.Builder<Integer>().setData(0).setStatus(0).setMessage("ok").build();
        } catch (AuthenticationException e)  {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return new ResponseEntity.Builder<Integer>().setData(0).setStatus(1).setMessage(msg).build();
        }
	}
	
	@RequestMapping(value="/userCenter/register", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> register(@RequestBody UserModel userModel) {
		log.info("register name {}, password {}", userModel.getName(), userModel.getPassword());
		return new ResponseEntity.Builder<Integer>().setData(0).setStatus(0).setMessage("ok").build();
	}*/
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/register",method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseData register(UserModel userModel) {
		
		//随机产生6位邀请码
		long i = (int)((Math.random()*9+1)*10000000);
        String inviteCode = Long.toString(i, 36);
		userModel.setInviteCode(inviteCode);;
		//创建日期
		Date date=new Date();
	    DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String created=format.format(date);
	    userModel.setCreated(created);
	    
		userService.insertUser(userModel);
		ResponseData responseData = new ResponseData();
		responseData.success("注册成功");
		return responseData;
		
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseData doUserLogin(UserModel userModel, HttpServletRequest request, HttpSession session) {
		
		ResponseData responseData = new ResponseData();
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		userModel.setName(name);
		userModel.setPassword(password);
		
		userModel = this.userService.login(userModel);
		
		if(userModel != null) {			
			session.setAttribute("userModel", userModel);
			responseData.success("登录成功");
			return responseData;			
		}
		else {			
			session.setAttribute("error", "账号或者密码错误！请重新输入");			
		}
		responseData.success("success login");
		return responseData;
		
	}
	
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseData deleteUser(UserModel userModel) {
		
		userService.deleteUser(userModel);
		ResponseData responseData = new ResponseData();
		responseData.success("删除方案集成功");
		return responseData;
		
	}
	
	@RequestMapping(value="/selectUserInfoById",method=RequestMethod.GET, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseData selectUserInfoById(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		List<UserModel> list = userService.selectUserInfoById(id);
		ResponseData responseData = new ResponseData();
		if(list.size()>0){
			responseData.putDataValue("list", list);
			responseData.success("成功");
		}
		return responseData;
		
	}
}
