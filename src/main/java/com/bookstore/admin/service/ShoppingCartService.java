package com.bookstore.admin.service;

import com.bookstore.admin.entity.ShoppingCart;

public interface ShoppingCartService {
	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);
	
	void clearShoppingCart(ShoppingCart shoppingCart);
	
	ShoppingCart saveShoppingCart(ShoppingCart shoppingCart);
}
