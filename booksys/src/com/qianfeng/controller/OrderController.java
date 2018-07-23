package com.qianfeng.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.OrderItem;
import com.qianfeng.entity.Orders;
import com.qianfeng.service.IOrderService;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.OrderBean;
import com.qianfeng.vo.PageBean;

@Controller
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping(value="/orders", method=RequestMethod.POST)
	public @ResponseBody JsonBean addOrder(String[] ids, String[] nums, Double totalPrice, HttpSession session, HttpServletResponse response) {
		
		JsonBean bean = new JsonBean();
		
		try {
			// 获取session对象中存放的用户名
			if (session.getAttribute("loginname") == null) {
				throw new RuntimeException("请先登录");
			}
			
			orderService.checkStock(ids, nums);
			
			String username = session.getAttribute("loginname").toString();
			
			// 添加订单信息
			Orders orders = orderService.addOrderInfo(totalPrice, username);
			
			// 添加订单明细
			orderService.addOrderItems(ids, nums, orders);
			
			// 添加订单成功，清空购物车
			Cookie cookie = new Cookie("cartids", "");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			
			bean.setCode(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/orders/page/{page}", method=RequestMethod.GET)
	public @ResponseBody JsonBean findOrderInfo(@PathVariable("page") Integer page, HttpSession session) {
		JsonBean bean = new JsonBean();
		
		String name = session.getAttribute("loginname").toString();
		try {
			PageBean<OrderItem> pageBean = orderService.findItemByIndex(name, page);
			bean.setCode(1);
			bean.setMsg(pageBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/orderall", method=RequestMethod.GET)
	public @ResponseBody JsonBean findOrderAll() {
		JsonBean bean = new JsonBean();
		
		try {
			OrderBean<OrderItem> orderBean = orderService.findAll();
			bean.setCode(1);
			bean.setMsg(orderBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/allorders/page/{page}", method=RequestMethod.GET)
	public @ResponseBody JsonBean findAllOrders(@PathVariable("page") Integer page) {
		JsonBean bean = new JsonBean();
		
		try {
			PageBean<OrderItem> pageBean = orderService.findAllByIndex(page);
			bean.setCode(1);
			bean.setMsg(pageBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/updateorder", method=RequestMethod.POST)
	public @ResponseBody JsonBean updateOrder(String str) {
		
		JsonBean bean = new JsonBean();
		Integer page = null;
		try {
			page = orderService.updateState(str);
			bean.setCode(1);
			bean.setMsg(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/updatestate", method=RequestMethod.POST)
	public @ResponseBody JsonBean updateState(String str) {
		
		JsonBean bean = new JsonBean();
		Integer page = null;
		try {
			page = orderService.updateOrderState(str);
			bean.setCode(1);
			bean.setMsg(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/findbystate/page/{page}", method=RequestMethod.GET)
	public @ResponseBody JsonBean findByState(@PathVariable("page") Integer page, String state) {
		JsonBean bean = new JsonBean();
		
		try {
			PageBean<OrderItem> pageBean = orderService.findByState(Integer.parseInt(state), page);
			bean.setCode(1);
			bean.setMsg(pageBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
}
