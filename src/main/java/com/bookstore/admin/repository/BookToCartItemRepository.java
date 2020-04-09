package com.bookstore.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.admin.entity.BookToCartItem;
import com.bookstore.admin.entity.CartItem;



@Transactional
public interface BookToCartItemRepository extends CrudRepository<BookToCartItem, Long> {
	void deleteByCartItem(CartItem cartItem);
}
