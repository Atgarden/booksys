package com.booksys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booksys.dao.IUserDao;
import com.booksys.entity.User;
import com.booksys.service.IUserService;
import com.booksys.vo.PageBean;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Override
	public PageBean<User> findUserByIndex(Integer page) {
		// TODO Auto-generated method stub
		PageBean<User> pageInfo = new PageBean<>();
		pageInfo.setCurrentPage(page);
		
		try {
			int count = userDao.count();
			pageInfo.setCount(count);
			
			int totalPage = 0;
			if (count % pageInfo.getSize() == 0) {
				totalPage = count / pageInfo.getSize();
			} else {
				totalPage = count / pageInfo.getSize() + 1;
			}
			pageInfo.setTotalPage(totalPage);
			
			Map<String, Object> map = new HashMap<>();
			map.put("index", (page - 1) * pageInfo.getSize());
			map.put("size", pageInfo.getSize());
			List<User> items = userDao.findByIndex(map);
			
			pageInfo.setPageInfos(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return pageInfo;
	}

	@Override
	public Integer updateIsLock(String str) {
		// TODO Auto-generated method stub
		
		String[] infos = str.split(",");
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("userName", infos[0]);
		Integer isLock = Integer.parseInt(infos[1]);
		
		if (isLock == 3) {
			map.put("isLock", 0);
		}
		else {
			map.put("isLock", 3);
		}
		try {
			userDao.update(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return Integer.parseInt(infos[2]);
		
	}

	@Override
	public Integer deleteUser(String str) {
		// TODO Auto-generated method stub
		String[] infos = str.split(",");
		
		Integer userId = Integer.parseInt(infos[0]);
		
		try {
			userDao.delete(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return Integer.parseInt(infos[1]);
	}

	@Override
	public PageBean<User> findByInput(Integer page, String value, String input) {
		// TODO Auto-generated method stub
		PageBean<User> pageInfo = new PageBean<>();
		pageInfo.setCurrentPage(page);
		
		try {
			int count = 0;
			if (value.equals("0")) {
				count = 1;
			}
			pageInfo.setCount(count);
			
			int totalPage = 0;
			if (count % pageInfo.getSize() == 0) {
				totalPage = count / pageInfo.getSize();
			} else {
				totalPage = count / pageInfo.getSize() + 1;
			}
			pageInfo.setTotalPage(totalPage);
			
			Map<String, Object> map = new HashMap<>();
			map.put("index", (page - 1) * pageInfo.getSize());
			map.put("size", pageInfo.getSize());
			List<User> items = null;
			if (value.equals("0")) {
				map.put("userName", input);
				items = userDao.findNameByIndex(map);
			}
			
			pageInfo.setPageInfos(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return pageInfo;
	}

}
