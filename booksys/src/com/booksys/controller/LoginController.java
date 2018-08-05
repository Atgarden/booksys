package com.booksys.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booksys.entity.User;
import com.booksys.service.ILoginService;
import com.booksys.vo.JsonBean;

@Controller
public class LoginController {
	
	@Autowired
	private ILoginService loginService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody JsonBean login(@RequestParam("userName") String username, @RequestParam("passWord") String password, HttpSession session, HttpServletResponse response) {
		JsonBean bean = new JsonBean();
		try {
			loginService.login(username, password);
			// 登录成功，将用户名存放到session对象里
			session.setAttribute("loginname", username);
			String sessionId = session.getId();
			Cookie cookie = new Cookie("JSESSIONID", sessionId);
			cookie.setMaxAge(1800);
			response.addCookie(cookie);
			bean.setCode(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public @ResponseBody JsonBean register(User user) {
		JsonBean bean = new JsonBean();
		try {
			loginService.register(user);
			bean.setCode(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping("/check")
	public @ResponseBody JsonBean check(String userName) {
		JsonBean bean = new JsonBean();
		try {
			boolean ret = loginService.userIsExist(userName);
			if (ret == true) {
				bean.setCode(-1);
			} else {
				bean.setCode(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping("/judge")
	public @ResponseBody JsonBean judge(HttpSession session) {
		JsonBean bean = new JsonBean();
		
		try {
			if (session.getAttribute("loginname") == null) {
				bean.setCode(-1);
			} else {
				bean.setCode(1);
				bean.setMsg(session.getAttribute("loginname"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping("/logout")
	public @ResponseBody JsonBean logout(HttpSession session) {
		JsonBean bean = new JsonBean();
		
		try {
			if (session.getAttribute("loginname") != null) {
				session.removeAttribute("loginname");
			}
			bean.setCode(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/alogin", method=RequestMethod.POST)
	public @ResponseBody JsonBean alogin(@RequestParam("adminName") String adminname, @RequestParam("passWord") String password, HttpSession session, HttpServletResponse response) {
		JsonBean bean = new JsonBean();
		try {
			loginService.adminLogin(adminname, password);
			// 登录成功，将用户名存放到session对象里
			session.setAttribute("loginadmin", adminname);
			String sessionId = session.getId();
			Cookie cookie = new Cookie("JSESSIONID", sessionId);
			cookie.setMaxAge(7200);
			response.addCookie(cookie);
			bean.setCode(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping(value="/getadmin", method=RequestMethod.GET)
	public @ResponseBody JsonBean getAdmin(HttpSession session) {
		JsonBean bean = new JsonBean();
		try {
			if (session.getAttribute("loginadmin") == null) {
				bean.setCode(-1);
				bean.setMsg("请先登录");
			} else {
				String name = session.getAttribute("loginadmin").toString();
				bean.setCode(1);
				bean.setMsg(name);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
	@RequestMapping("/alogout")
	public @ResponseBody JsonBean alogout(HttpSession session) {
		JsonBean bean = new JsonBean();
		
		try {
			if (session.getAttribute("loginadmin") != null) {
				session.removeAttribute("loginadmin");
			}
			bean.setCode(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
}
