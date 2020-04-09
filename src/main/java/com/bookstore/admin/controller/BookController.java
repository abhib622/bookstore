package com.bookstore.admin.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookstore.admin.entity.Book;
import com.bookstore.admin.entity.User;
import com.bookstore.admin.service.BookService;
import com.bookstore.admin.service.UserService;



@Controller
public class BookController {
	
	@Autowired
	BookService bookService;
	@Autowired
	UserService userService;
	
	@RequestMapping("/bookshelf")
	public String bookshelf(Model model) {
		List<Book> bookList = bookService.findAll();
		model.addAttribute("bookList", bookList);
		model.addAttribute("activeAll", true);
		return "bookshelf";
	}
	
	@RequestMapping("/bookDetail")
	public String bookDetail(@PathParam("id") Long id, Model model, Principal principal
			) {
		if(principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		Book book = bookService.findOne(id);
		
		model.addAttribute("book", book);
		
		List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);
		
		return "bookDetail";
	}

}
