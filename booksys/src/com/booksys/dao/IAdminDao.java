package com.booksys.dao;

import com.booksys.base.IBaseDao;
import com.booksys.entity.Admin;

public interface IAdminDao extends IBaseDao<Admin> {
	
	public Admin findByName(String name);
}
