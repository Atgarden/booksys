package com.booksys.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booksys.dao.IAdminDao;
import com.booksys.dao.IUserDao;
import com.booksys.entity.Admin;
import com.booksys.entity.User;
import com.booksys.service.ILoginService;
import com.booksys.util.StringUtil;

@Service
public class LoginService implements ILoginService {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IAdminDao adminDao;
	
	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrEmpty(username)) {
			throw new RuntimeException("用户名为空");
		}
		if (StringUtil.isNullOrEmpty(password)) {
			throw new RuntimeException("密码为空");
		}
		
		User user = null;
		
		try {
			user = userDao.findByName(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		if (user == null) {
			throw new RuntimeException("用户名不存在");
		} else {
			if (user.getIsLock() == 3) {
				throw new RuntimeException("密码输入错误三次，账户已锁定");
			}
			Map<String, Object> map = new HashMap<>();
			map.put("userName", username);
			if (!user.getPassWord().equals(password)) {
				map.put("isLock", user.getIsLock()+1);
				try {
					userDao.update(map);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw e;
				}
				if (user.getIsLock() == 2) {
					throw new RuntimeException("密码输入错误三次，账户已锁定");
				} else {
					throw new RuntimeException("密码错误，还有" + (2-user.getIsLock()) + "次将锁定用户");
				}
			} else if (user.getIsLock() > 0) {
				map.put("isLock", 0);
				try {
					userDao.update(map);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw e;
				}
			}
		}
	}

	@Override
	public void register(User user) {
		// TODO Auto-generated method stub
		if (user == null) {
			throw new RuntimeException("用户信息不能为空");
		}
		if (!user.getPassWord().equals(user.getrePassWord())) {
			throw new RuntimeException("两次密码不一致");
		}
		
		try {
			userDao.add(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

	@Override
	public boolean userIsExist(String username) {
		// TODO Auto-generated method stub
		User user = null;
		try {
			user = userDao.findByName(username);
			if (user == null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

	@Override
	public void adminLogin(String adminname, String password) {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrEmpty(adminname)) {
			throw new RuntimeException("用户名为空");
		}
		if (StringUtil.isNullOrEmpty(password)) {
			throw new RuntimeException("密码为空");
		}
		
		Admin admin = null;
		
		try {
			admin = adminDao.findByName(adminname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		if (admin == null) {
			throw new RuntimeException("用户名不存在");
		} else if (!admin.getPassWord().equals(password)) {
			throw new RuntimeException("密码错误");
		}
	}
	
}
