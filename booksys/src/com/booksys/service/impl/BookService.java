package com.booksys.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booksys.dao.IBookDao;
import com.booksys.entity.Books;
import com.booksys.service.IBookService;
import com.booksys.util.StringUtil;
import com.booksys.vo.PageBean;

@Service
public class BookService implements IBookService {
	
	@Autowired
	private IBookDao bookDao;
	
	@Override
	public PageBean<Books> findByPage(Integer page) {
		// TODO Auto-generated method stub
		
		if (page == null || page < 1) {
			throw new RuntimeException("页码数据有误");
		}
		PageBean<Books> pageBean = new PageBean<>();
		pageBean.setCurrentPage(page);
		
		// 获取所有记录
		int count;
		try {
			count = bookDao.count();
			pageBean.setCount(count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		// 计算总页数
		if (count % pageBean.getSize() == 0) {
			pageBean.setTotalPage(count / pageBean.getSize());
		} else {
			pageBean.setTotalPage(count / pageBean.getSize() + 1);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("size", pageBean.getSize()); // 每页显示的记录数
		int index = (page - 1) * pageBean.getSize(); // 根据页码计算分页查询时开始的索引
		map.put("index", index); // 设置分页时的开始索引
		
		try {
			List<Books> books = bookDao.findByIndex(map);
			pageBean.setPageInfos(books);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return pageBean;
	}
	
	@Override
	public PageBean<Books> findByPageAll(Integer page) {
		// TODO Auto-generated method stub
		
		if (page == null || page < 1) {
			throw new RuntimeException("页码数据有误");
		}
		PageBean<Books> pageBean = new PageBean<>();
		pageBean.setCurrentPage(page);
		
		// 获取所有记录
		int count;
		try {
			count = bookDao.countAll();
			pageBean.setCount(count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		// 计算总页数
		if (count % pageBean.getSize() == 0) {
			pageBean.setTotalPage(count / pageBean.getSize());
		} else {
			pageBean.setTotalPage(count / pageBean.getSize() + 1);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("size", pageBean.getSize()); // 每页显示的记录数
		int index = (page - 1) * pageBean.getSize(); // 根据页码计算分页查询时开始的索引
		map.put("index", index); // 设置分页时的开始索引
		
		try {
			List<Books> books = bookDao.findAll(map);
			pageBean.setPageInfos(books);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return pageBean;
	}
	
	@Override
	public Books findById(String id) {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrEmpty(id)) {
			return null;
		}
		Books book = null;
		try {
			book = bookDao.findById(Integer.parseInt(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return book;
	}

	@Override
	public Integer updateState(String str, boolean info) {
		// TODO Auto-generated method stub
		
		String[] infos = str.split(",");
		
		if (info) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", Integer.parseInt(infos[0]));
			if (infos[1].equals("1")) {
				map.put("state", Integer.parseInt(infos[1]) - 1);
			}
			else {
				map.put("state", Integer.parseInt(infos[1]) + 1);
			}
			try {
				bookDao.updateState(map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
		}
		
		return Integer.parseInt(infos[2]);
	}

	@Override
	public void updateBook(String infos) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String[] msg = infos.split(",");
		for (String item : msg) {
			if (StringUtil.isNullOrEmpty(item)) {
				throw new RuntimeException("信息不完整，无法修改");
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("id", msg[0]);
		map.put("bookName", msg[1]);
		map.put("price", msg[2]);
		map.put("stock", msg[3]);
		map.put("state", msg[4]);
		map.put("img", msg[5]);
		try {
			bookDao.update(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean checkBook(String bookName) {
		// TODO Auto-generated method stub
		
		boolean result = false;
		
		try {
			int count = bookDao.countByName(bookName);
			if (count > 0) {
				result = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void addBook(String infos) {
		// TODO Auto-generated method stub
		String[] msg = infos.split(",");
		for (String item : msg) {
			if (StringUtil.isNullOrEmpty(item)) {
				throw new RuntimeException("信息不完整，无法添加");
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("bookName", msg[0]);
		map.put("price", msg[1]);
		map.put("stock", msg[2]);
		map.put("state", msg[3]);
		map.put("img", msg[4]);
		try {
			bookDao.add(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Books> findAllBooks() {
		// TODO Auto-generated method stub
		
		List<Books> list = null;
		try {
			list = bookDao.findAllBooks();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	@Override
	public PageBean<Books> findByInput(Integer page, String value, String input) {
		// TODO Auto-generated method stub
		PageBean<Books> pageInfo = new PageBean<>();
		pageInfo.setCurrentPage(page);
		System.out.println(input);
		try {
			int count = 0;
			if (value.equals("0")) {
				String bookName = input;
				count = bookDao.countByName(bookName);
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
			List<Books> items = null;
			if (value.equals("0")) {
				map.put("bookName", input);
				items = bookDao.findNameByIndex(map);
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
