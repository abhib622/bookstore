package com.bookstore.admin.service;

import com.bookstore.admin.entity.ShippingAddress;
import com.bookstore.admin.entity.UserShipping;

public interface ShippingAddressService {
	
	ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
