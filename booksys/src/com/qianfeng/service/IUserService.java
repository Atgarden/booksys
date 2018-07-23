package com.qianfeng.service;

import com.qianfeng.entity.User;
import com.qianfeng.vo.PageBean;

public interface IUserService {
	
	// 根据索引查找所有用户
	public PageBean<User> findUserByIndex(Integer page);
	
	// 根据用户名修改用户状态，并返回当前页
	public Integer updateIsLock(String str);
	
	// 根据id删除用户
	public Integer deleteUser(String str);
	
	// 根据输入进行查询
	public PageBean<User> findByInput(Integer page, String value, String input);
}
