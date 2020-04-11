package com.bookstore.admin.service;

import com.bookstore.admin.entity.BillingAddress;
import com.bookstore.admin.entity.Order;
import com.bookstore.admin.entity.Payment;
import com.bookstore.admin.entity.ShippingAddress;
import com.bookstore.admin.entity.ShoppingCart;
import com.bookstore.admin.entity.User;

public interface OrderService {
	Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress,
			Payment payment, String shippingMethod, User user);
	
	Order findOne(Long id);
}
