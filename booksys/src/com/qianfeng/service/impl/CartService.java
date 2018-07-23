package com.qianfeng.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IBookDao;
import com.qianfeng.entity.Books;
import com.qianfeng.service.ICartService;
import com.qianfeng.util.StringUtil;

@Service
public class CartService implements ICartService {

	@Autowired
	private IBookDao bookDao;
	
	@Override
	public String addCart(String[] bookIds, String cartId) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		if (!StringUtil.isNullOrEmpty(cartId)) {
			String[] split = cartId.split(",");
			for (String v : split) {
				list.add(v);
			}
		}
		
		if (bookIds == null || bookIds.length == 0) {
			throw new RuntimeException("没有选择相关的图书");
		}
		
		for (int i = 0; i < bookIds.length; i++) {
			if (!list.contains(bookIds[i]))
				list.add(bookIds[i]);
		}
		
		String info = "";
		for (String v : list) {
			info += v + ",";
		}
		info = info.substring(0, info.length() - 1);
		return info;
	}

	@Override
	public List<Books> findCartInfo(String ids) {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrEmpty(ids)) {
			throw new RuntimeException("购物车中没有数据");
		}
		
		try {
			String[] split = ids.split(",");
			List<String> list = new ArrayList<>();
			for (String info : split) {
				list.add(info);
			}
			List<Books> books = bookDao.findByIds(list);
			return books;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public String deleteBook(String cartId, String bookId) {
		// TODO Auto-generated method stub
		
		List<String> list = new ArrayList<>();
		if (!StringUtil.isNullOrEmpty(cartId)) {
			String[] split = cartId.split(",");
			for (String v : split) {
				list.add(v);
			}
		}
		
		if (list.contains(bookId)) {
			list.remove(bookId);
		}
		
		String info = "";
		for (String v : list) {
			info += v + ",";
		}
		if (info.length() > 0)
			info = info.substring(0, info.length() - 1);
		return info;
	}

}
