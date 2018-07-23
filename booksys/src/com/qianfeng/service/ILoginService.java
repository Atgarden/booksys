package com.qianfeng.service;

import com.qianfeng.entity.User;

public interface ILoginService {

	// 用户登录
	public void login(String username, String password);
	
	// 用户注册
	public void register(User user);
	
	// 判断用户是否存在
	public boolean userIsExist(String username);
	
	// 管理员登录
	public void adminLogin(String adminname, String password);
}
