package com.bookstore.admin.service;

import com.bookstore.admin.entity.BillingAddress;
import com.bookstore.admin.entity.Payment;
import com.bookstore.admin.entity.User;
import com.bookstore.admin.entity.UserBilling;
import com.bookstore.admin.entity.UserPayment;
import com.bookstore.admin.entity.UserShipping;

public interface PaymentService {
	
	Payment setByUserPayment(UserPayment userPayment, Payment payment);
	
	BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
	
	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);
	
    void updateUserShipping(UserShipping userShipping, User user);
    
    void setUserDefaultPayment(Long userPaymentId, User user);
	
	void setUserDefaultShipping(Long userShippingId, User user);

	UserPayment findPaymentById(Long id);
		
    void removePaymentById(Long id);
    
	UserShipping findShippingById(Long id);
		
	void removShippingeById(Long id);

}
