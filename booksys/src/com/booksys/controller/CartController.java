package com.booksys.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booksys.entity.Books;
import com.booksys.service.ICartService;
import com.booksys.vo.JsonBean;

@Controller
public class CartController {
	
	@Autowired
	private ICartService cartService;
	
	// 向购物车添加数据，将相关数据存放到cookie
	@RequestMapping(value="/carts", method=RequestMethod.POST)
	// @CookieValue读取指定的cookie
	public @ResponseBody JsonBean addCart(String[] bookIds, @CookieValue(value="cartids", defaultValue="") String cartId, HttpServletResponse response) {
		
		JsonBean bean = new JsonBean();
		
		String info= "";
		try {
			info = cartService.addCart(bookIds, cartId);
			Cookie cookie = new Cookie("cartids", info);
			cookie.setMaxAge(30 * 24 * 3600);
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
	
	@RequestMapping(value="/carts", method=RequestMethod.GET)
	public @ResponseBody JsonBean carts(@CookieValue("cartids") String cartId) {
		
		JsonBean bean = new JsonBean();
		
		try {
			List<Books> infos = cartService.findCartInfo(cartId);
			bean.setCode(1);
			bean.setMsg(infos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping("/delete")
	public @ResponseBody JsonBean delete(@CookieValue("cartids") String cartId, String bookId, HttpServletResponse response) {
		JsonBean bean = new JsonBean();
		
		String info= "";
		try {
			info = cartService.deleteBook(cartId, bookId);
			Cookie cookie = new Cookie("cartids", info);
			cookie.setMaxAge(30 * 24 * 3600);
			response.addCookie(cookie);
			bean.setCode(1);
			bean.setMsg(bookId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
}
