package com.qianfeng.service;

import java.util.List;

import com.qianfeng.entity.Books;

public interface ICartService {
	
	public String addCart(String[] bookIds, String cartId);
	
	// 查询购物车的数据
	public List<Books> findCartInfo(String ids);
	
	// 删除购物车数据
	public String deleteBook(String cartId, String bookId);
}
