package com.booksys.vo;

import java.util.List;

public class OrderBean<T> {
	
	private Integer orderNum;
	private List<T> items;
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
}
