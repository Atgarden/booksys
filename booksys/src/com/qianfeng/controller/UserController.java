package com.qianfeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.User;
import com.qianfeng.service.IUserService;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/user/page/{page}", method=RequestMethod.GET)
	public @ResponseBody JsonBean findUserInfo(@PathVariable("page") Integer page) {
		JsonBean bean = new JsonBean();
		
		try {
			PageBean<User> pageBean = userService.findUserByIndex(page);
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
	
	@RequestMapping(value="/updateislock", method=RequestMethod.POST)
	public @ResponseBody JsonBean updateIsLock(String str) {
		JsonBean bean = new JsonBean();
		Integer page = null;
		
		try {
			page = userService.updateIsLock(str);
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
	
	@RequestMapping(value="/deleteuser", method=RequestMethod.POST)
	public @ResponseBody JsonBean deleteUser(String str) {
		JsonBean bean = new JsonBean();
		Integer page = null;
		
		try {
			page = userService.deleteUser(str);
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
	
	@RequestMapping(value="/finduserinput/page/{page}", method=RequestMethod.GET)
	public @ResponseBody JsonBean findByInput(@PathVariable("page") Integer page, String value, String input) {
		JsonBean bean = new JsonBean();
		
		try {
			PageBean<User> pageBean = userService.findByInput(page, value, input);
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
