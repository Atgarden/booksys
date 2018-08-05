package com.booksys.dao;

import java.util.Map;

import com.booksys.base.IBaseDao;
import com.booksys.entity.Orders;

public interface IOrderDao extends IBaseDao<Orders> {
	
	// 根据用户名计算总订单数
	public int countByName(String userName);
	
	// 根据订单编号修改状态
	public void updateState(Map<String, Object> map);
	
	// 计算当前状态的订单数
	public int countByState(Integer state);
}
