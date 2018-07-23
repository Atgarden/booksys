package com.qianfeng.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qianfeng.entity.Books;
import com.qianfeng.service.IBookService;
import com.qianfeng.service.IOrderService;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class BookController {
	
	@Autowired
	private IBookService bookService;
	
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping(value="/books/page/{page}", method=RequestMethod.GET)
	// @PathVariable表示从路径中取对应变量的值
	public @ResponseBody JsonBean findByPage(@PathVariable("page") Integer page) {
		JsonBean bean = new JsonBean();
		
		try {
			PageBean<Books> infos = bookService.findByPage(page);
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
	
	@RequestMapping(value="/books/pageall/{page}", method=RequestMethod.GET)
	// @PathVariable表示从路径中取对应变量的值
	public @ResponseBody JsonBean findByPageAll(@PathVariable("page") Integer page) {
		JsonBean bean = new JsonBean();
		
		try {
			PageBean<Books> infos = bookService.findByPageAll(page);
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
	
	@RequestMapping(value="/revise", method=RequestMethod.POST)
	public @ResponseBody JsonBean revise(String bookId, HttpServletResponse response) {
		
		JsonBean bean = new JsonBean();

		try {
			Cookie cookie = new Cookie("bookId", bookId);
			cookie.setMaxAge(300);
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
	
	@RequestMapping(value="/operate", method=RequestMethod.POST)
	public @ResponseBody JsonBean revise(String str) {
		
		JsonBean bean = new JsonBean();
		Integer page = null;
		try {
			if (!orderService.checkBook(str)) {
				page = bookService.updateState(str, true);
				bean.setCode(1);
			} else {
				page = bookService.updateState(str, false);
				bean.setCode(-1);
			}
			bean.setMsg(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/book", method=RequestMethod.GET)
	public @ResponseBody JsonBean findBook(@CookieValue(value="bookId", defaultValue="") String bookId) {
		JsonBean bean = new JsonBean();
		
		try {
			Books book = bookService.findById(bookId);
			bean.setCode(1);
			bean.setMsg(book);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/bookall", method=RequestMethod.GET)
	public @ResponseBody JsonBean findAllBooks() {
		JsonBean bean = new JsonBean();
		
		try {
			List<Books> list = bookService.findAllBooks();
			bean.setCode(1);
			bean.setMsg(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/imgUpload", method=RequestMethod.POST)
    public @ResponseBody String imgUpload(@RequestParam("file") MultipartFile file, String id, String bookName, String price, String stock, String state, String img) throws UnsupportedEncodingException {
        String bean = "0";
		String infos = "";
		infos += id;
		infos += "," + bookName;
		infos += "," + price;
		infos += "," + stock;
		infos += "," + state;
        if(!file.isEmpty()) {
        	//System.currentTimeMillis() + file.getOriginalFilename()时间戳加原文件名
            String message = file.getOriginalFilename();
            String path = "E:/upload";
            File imgfile = new File(path, message);
			try {
				infos += "," + "images/" + message;
				file.transferTo(imgfile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
        	infos += "," + img;
        }
        try {
			bookService.updateBook(infos);
			bean = "1";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return bean;
    }
	
	@RequestMapping(value="/checkBook", method=RequestMethod.POST)
	public @ResponseBody JsonBean checkBook(String bookName) {
		JsonBean bean = new JsonBean();
		
		try {
			if (bookService.checkBook(bookName)) {
				bean.setCode(-1);
			} else {
				bean.setCode(1);
			}
			bean.setMsg(bookName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		
		return bean;
	}
	
	@RequestMapping(value="/addBook", method=RequestMethod.POST)
    public @ResponseBody String addBook(@RequestParam("file") MultipartFile file, String bookName, String price, String stock, String state) throws UnsupportedEncodingException {
        String bean = "0";
		String infos = "";
		infos += bookName;
		infos += "," + price;
		infos += "," + stock;
		infos += "," + state;
        if (!file.isEmpty()) {
        	//System.currentTimeMillis() + file.getOriginalFilename()时间戳加原文件名
            String message = file.getOriginalFilename();
            String path = "E:/upload";
            File imgfile = new File(path, message);
			try {
				infos += "," + "images/" + message;
				file.transferTo(imgfile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        try {
			bookService.addBook(infos);
			bean = "1";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return bean;
    }
	
	@RequestMapping(value="/findbookinput/page/{page}", method=RequestMethod.POST)
	public @ResponseBody JsonBean findByInput(@PathVariable("page") Integer page, String value, String input) throws UnsupportedEncodingException {
		JsonBean bean = new JsonBean();
		try {
			PageBean<Books> pageBean = bookService.findByInput(page, value, input);
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
