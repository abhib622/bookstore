package com.bookstore.admin.repository;

import com.bookstore.admin.entity.BillingAddress;
import com.bookstore.admin.entity.UserBilling;

public interface BillingAddressService {
	BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}
