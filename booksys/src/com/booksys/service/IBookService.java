package com.booksys.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.booksys.entity.Books;
import com.booksys.vo.PageBean;

public interface IBookService {

	public PageBean<Books> findByPage(Integer page);
	
	// 根据索引查询所有书籍
	public PageBean<Books> findByPageAll(Integer page);
	
	// 查找所有书籍
	public List<Books> findAllBooks();
	
	// 根据id查询书籍
	public Books findById(String id);
	
	// 更新状态
	public Integer updateState(String str, boolean info);
	
	// 根据id更新书籍信息
	public void updateBook(String infos) throws UnsupportedEncodingException;
	
	// 根据书籍名称判断该书籍是否已存在
	public boolean checkBook(String bookName);
	
	// 添加书籍
	public void addBook(String infos);
	
	// 根据输入进行查询
	public PageBean<Books> findByInput(Integer page, String value, String input);
}
