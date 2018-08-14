package com.cascv.oas.server.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cascv.oas.core.common.ResponseEntity;
import com.cascv.oas.core.model.UserModel;
import com.cascv.oas.core.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {
	
	@RequestMapping(value="/login", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody UserModel userModel,HttpServletResponse response) {
		log.info("name {}, password {}", userModel.getName(), userModel.getPassword());
		UsernamePasswordToken token = new UsernamePasswordToken(userModel.getName(), userModel.getPassword(), false);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            response.addHeader("token", subject.getSession().getId().toString());
            return new ResponseEntity.Builder<Integer>().setData(0).setStatus(0).setMessage("ok").build();
        } catch (AuthenticationException e)  {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return new ResponseEntity.Builder<Integer>().setData(0).setStatus(1).setMessage(msg).build();
        }
	}
}
