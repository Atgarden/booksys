package com.qianfeng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IBookDao;
import com.qianfeng.dao.IOrderDao;
import com.qianfeng.dao.IOrderItemDao;
import com.qianfeng.dao.IUserDao;
import com.qianfeng.entity.Books;
import com.qianfeng.entity.OrderItem;
import com.qianfeng.entity.Orders;
import com.qianfeng.entity.User;
import com.qianfeng.service.IOrderService;
import com.qianfeng.util.StringUtil;
import com.qianfeng.vo.OrderBean;
import com.qianfeng.vo.PageBean;

@Service
public class OrderService implements IOrderService {
	
	@Autowired
	private IOrderDao orderDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IOrderItemDao itemDao;
	
	@Autowired
	private IBookDao bookDao;

	@Override
	public Orders addOrderInfo(Double totalprice, String username) {
		// TODO Auto-generated method stub
		
		// 添加订单
		Orders orders = new Orders();
		orders.setTotalPrice(totalprice);
		orders.setState(0);
		orders.setCreateDate(new Date());
		// 使用UUID作为订单编号
		orders.setOrderNum(UUID.randomUUID().toString());
		
		try {
			User buyer = userDao.findByName(username);
			orders.setBuyer(buyer);
			orderDao.add(orders);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return orders;
	}

	@Override
	public void addOrderItems(String[] ids, String[] nums, Orders orders) {
		// TODO Auto-generated method stub
		if (ids == null || ids.length == 0) {
			throw new RuntimeException("图书数据不存在");
		}
		if (nums == null || nums.length == 0) {
			throw new RuntimeException("购买数量不存在");
		}
		if (orders == null) {
			throw new RuntimeException("订单数据不存在");
		}
		
		// 添加明细
		try {
			for (int i = 0; i < ids.length; i++) {
				OrderItem item = new OrderItem();
				
				Books book = new Books();
				book.setId(Integer.parseInt(ids[i]));
				item.setBooks(book);
				item.setOrders(orders);
				item.setNum(Integer.parseInt(nums[i]));
				
				itemDao.add(item);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public PageBean<OrderItem> findItemByIndex(String name, Integer page) {
		// TODO Auto-generated method stub
		
		if (StringUtil.isNullOrEmpty(name)) {
			throw new RuntimeException("用户名为空，不能查询");
		}
		
		PageBean<OrderItem> pageInfo = new PageBean<>();
		pageInfo.setCurrentPage(page);
		
		try {
			int count = orderDao.countByName(name);
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
			map.put("name", name);
			List<OrderItem> items = itemDao.findByIndex(map);
			
			pageInfo.setPageInfos(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return pageInfo;
	}

	@Override
	public void checkStock(String[] ids, String[] nums) {
		// TODO Auto-generated method stub
		
		if (ids == null || ids.length == 0) {
			throw new RuntimeException("图书数据不存在");
		}
		if (nums == null || nums.length == 0) {
			throw new RuntimeException("购买数量不存在");
		}
		
		try {
			List<String> list = new ArrayList<>();
			for (int i = 0; i < ids.length; i++) {
				list.add(ids[i]);
			}
			List<Books> books = bookDao.findByIds(list);
			String infos = "";
			for (int i = 0; i < nums.length; i++) {
				int stock = books.get(i).getStock();
				if (stock < Integer.parseInt(nums[i])) {
					infos += books.get(i).getBookName() + "库存还有" + String.valueOf(stock) + "\n";
				}
			}
			if (infos.length() > 0) {
				infos += "请修改购买数量";
				throw new RuntimeException(infos);
			} else {
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = new HashMap<>();
					map.put("id", list.get(i));
					map.put("stock", books.get(i).getStock()-Integer.parseInt(nums[i]));
					bookDao.updateStock(map);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean checkBook(String str) {
		// TODO Auto-generated method stub
		
		String[] info = str.split(",");
		String bookId = info[0];
		boolean result = false;
		
		try {
			int count = itemDao.countByBookId(Integer.parseInt(bookId));
			if (count > 0) {
				result = true;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public OrderBean<OrderItem> findAll() {
		// TODO Auto-generated method stub
		
		OrderBean<OrderItem> infos = new OrderBean<>();
		
		try {
			// 获取订单数
			infos.setOrderNum(orderDao.count());
			// 获取订单明细
			List<OrderItem> list = itemDao.findAll();
			infos.setItems(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return infos;
	}

	@Override
	public PageBean<OrderItem> findAllByIndex(Integer page) {
		// TODO Auto-generated method stub
		PageBean<OrderItem> pageInfo = new PageBean<>();
		pageInfo.setCurrentPage(page);
		
		try {
			int count = orderDao.count();
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
			List<OrderItem> items = itemDao.findAllByIndex(map);
			
			pageInfo.setPageInfos(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return pageInfo;
	}

	@Override
	public Integer updateState(String str) {
		// TODO Auto-generated method stub
		String[] infos = str.split(",");
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("orderNum", infos[0]);
		Integer state = Integer.parseInt(infos[1]);
		Integer newstate = null;
		
		if (state == 0) {
			newstate = 1;
		} else if (state == 1) {
			newstate = 0;
		} else if (state == 3) {
			newstate = 4;
		} else if (state == 4) {
			newstate = 3;
		} else if (state == 5) {
			newstate = 6;
		}
		
		map.put("state", newstate);
		
		try {
			orderDao.updateState(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return Integer.parseInt(infos[2]);
	}

	@Override
	public PageBean<OrderItem> findByState(Integer state, Integer page) {
		// TODO Auto-generated method stub
		PageBean<OrderItem> pageInfo = new PageBean<>();
		pageInfo.setCurrentPage(page);
		
		try {
			int count = orderDao.countByState(state);
			pageInfo.setCount(count);
			
			int totalPage = 0;
			if (count % pageInfo.getSize() == 0) {
				totalPage = count / pageInfo.getSize();
			} else {
				totalPage = count / pageInfo.getSize() + 1;
			}
			pageInfo.setTotalPage(totalPage);
			
			Map<String, Object> map = new HashMap<>();
			map.put("state", state);
			map.put("index", (page - 1) * pageInfo.getSize());
			map.put("size", pageInfo.getSize());
			List<OrderItem> items = itemDao.findByState(map);
			
			pageInfo.setPageInfos(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return pageInfo;
	}

	@Override
	public Integer updateOrderState(String str) {
		// TODO Auto-generated method stub
		String[] infos = str.split(",");
		
		Map<String, Object> map = new HashMap<>();
		map.put("orderNum", infos[0]);
		
		Integer state = Integer.parseInt(infos[1]);
		Integer newstate = null;
		
		if (state == 1) {
			newstate = 2;
		} else if (state == 2) {
			newstate = 3;
		} else if (state == 4) {
			newstate = 5;
		}
		
		map.put("state", newstate);
		
		try {
			orderDao.updateState(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return Integer.parseInt(infos[2]);
	}

}
