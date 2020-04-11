package com.bookstore.admin.service;

import java.util.List;

import com.bookstore.admin.entity.Book;
import com.bookstore.admin.entity.CartItem;
import com.bookstore.admin.entity.Order;
import com.bookstore.admin.entity.ShoppingCart;
import com.bookstore.admin.entity.User;

public interface CartItemService {
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
	CartItem addBookToCartItem(Book book, User user, int qty);
	
	CartItem findById(Long id);
	
	void removeCartItem(CartItem cartItem);
	
	List<CartItem> findByOrder(Order order);
}
