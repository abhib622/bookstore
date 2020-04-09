package com.bookstore.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.entity.Book;
import com.bookstore.admin.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> findAll() {
		return (List<Book>) bookRepository.findAll();
	}
	
	public Book findOne(Long id) {
		Optional<Book> bookOpt = bookRepository.findById(id);
		if(bookOpt.isPresent()) {
			 return bookOpt.get();
		} else {
			return null;
		}	
	}
	
	public List<Book> findByCategory(String category){
		List<Book> bookList = bookRepository.findByCategory(category);
		
		List<Book> activeBookList = new ArrayList<>();
		
		for (Book book: bookList) {
			if(book.isActive()) {
				activeBookList.add(book);
			}
		}
		
		return activeBookList;
	}
	
	public List<Book> blurrySearch(String title) {
		List<Book> bookList = bookRepository.findByTitleContaining(title);
List<Book> activeBookList = new ArrayList<>();
		
		for (Book book: bookList) {
			if(book.isActive()) {
				activeBookList.add(book);
			}
		}
		
		return activeBookList;
	}

}
