package com.booksys.dao;

import java.util.List;
import java.util.Map;

import com.booksys.base.IBaseDao;
import com.booksys.entity.Books;

public interface IBookDao extends IBaseDao<Books> {

	// 进行分页查询，从哪个索引开始，每页显示几条记录
	// public List<Books> findByIndex(Integer index, Integer size);
	public List<Books> findByIndex(Map<String, Object> pageInfo);
	
	// 根据相关的书籍id进行查询
	public List<Books> findByIds(List<String> ids);
	
	// 根据id查询书籍
	public Books findById(Integer id);
	
	// 根据id修改库存
	public void updateStock(Map<String, Object> map);
	
	// 根据索引查找所有书籍
	public List<Books> findAll(Map<String, Object> pageInfo);
	
	//查找所有书籍
	public List<Books> findAllBooks();
	
	// 计算所有书籍数量
	public int countAll();
	
	// 根据id修改状态
	public void updateState(Map<String, Object> map);
	
	// 根据id修改书籍信息
	public void update(Map<String, Object> map);
	
	// 根据书籍名称判断该书籍是否已存在
	public int countByName(String bookName);
	
	// 添加书籍
	public void add(Map<String, Object> map);
	
	// 根据索引查找书籍
	public List<Books> findNameByIndex(Map<String, Object> map);
}
