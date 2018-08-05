package com.booksys.dao;

import java.util.List;
import java.util.Map;

import com.booksys.base.IBaseDao;
import com.booksys.entity.OrderItem;

public interface IOrderItemDao extends IBaseDao<OrderItem> {

	public void add(OrderItem item);
	
	// 查询订单明细
	public List<OrderItem> findByIndex(Map<String, Object> info);
	
	// 计算当前书籍的订单数
	public int countByBookId(Integer bookId);
	
	// 查询所有订单
	public List<OrderItem> findAll();
	
	// 根据索引查询所有订单
	public List<OrderItem> findAllByIndex(Map<String, Object> map);
	
	// 根据状态索引查询所有订单
	public List<OrderItem> findByState(Map<String, Object> map);
}
