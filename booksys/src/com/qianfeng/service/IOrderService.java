package com.qianfeng.service;

import com.qianfeng.entity.OrderItem;
import com.qianfeng.entity.Orders;
import com.qianfeng.vo.OrderBean;
import com.qianfeng.vo.PageBean;

public interface IOrderService {
	
	// 添加订单信息
	public Orders addOrderInfo(Double price, String username);
	
	// 添加订单明细
	public void addOrderItems(String[] ids, String[] nums, Orders orders);
	
	// 根据页码查询订单明细信息（按照订单中数据分页）
	public PageBean<OrderItem> findItemByIndex(String name, Integer page);
	
	// 检查库存是否足够，若足够则更新，不足够则提示
	public void checkStock(String[] ids, String[] nums);
	
	// 检查当前书籍是否有订单
	public boolean checkBook(String str);
	
	// 查询所有订单明细
	public OrderBean<OrderItem> findAll();
	
	// 根据索引查询所有订单
	public PageBean<OrderItem> findAllByIndex(Integer page);
	
	// 根据状态查询订单
	public PageBean<OrderItem> findByState(Integer state, Integer page);
	
	// 根据订单编号修改状态（后台）
	public Integer updateState(String str);
	
	// 根据订单编号修改状态（前台）
	public Integer updateOrderState(String str);
}
