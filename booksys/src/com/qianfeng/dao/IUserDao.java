package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.User;

public interface IUserDao extends IBaseDao<User> {
	
	public User findByName(String name);
	
	public void update(Map<String, Object> map);
	
	public void add(User user);
	
	// 根据索引查询所有用户
	public List<User> findByIndex(Map<String, Object> map);
	
	// 根据id删除用户
	public void delete(Integer id);
	
	// 根据索引查找用户
	public List<User> findNameByIndex(Map<String, Object> map);
}
