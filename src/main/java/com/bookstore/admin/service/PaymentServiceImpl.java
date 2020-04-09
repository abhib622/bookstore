package com.bookstore.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.entity.BillingAddress;
import com.bookstore.admin.entity.Payment;
import com.bookstore.admin.entity.User;
import com.bookstore.admin.entity.UserBilling;
import com.bookstore.admin.entity.UserPayment;
import com.bookstore.admin.entity.UserShipping;
import com.bookstore.admin.repository.UserPaymentRepository;
import com.bookstore.admin.repository.UserShippingRepository;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	UserService userService;
	@Autowired
	UserPaymentRepository userPaymentRepository;
	@Autowired
	UserShippingRepository userShippingRepository;

	@Override
	public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
		userPayment.setUser(user);
		userPayment.setUserBilling(userBilling);
		userPayment.setDefaultPayment(true);
		userBilling.setUserPayment(userPayment);
		user.getUserPaymentList().add(userPayment);
		userService.save(user);
	}
	
	@Override
	public void updateUserShipping(UserShipping userShipping, User user){
		userShipping.setUser(user);
		userShipping.setUserShippingDefault(true);
		user.getUserShippingList().add(userShipping);
		userService.save(user);
	}

	@Override
	public void setUserDefaultPayment(Long userPaymentId, User user) {
		List<UserPayment> userPaymentList = (List<UserPayment>) userPaymentRepository.findAll();
		
		for (UserPayment userPayment : userPaymentList) {
			if(userPayment.getId() == userPaymentId) {
				userPayment.setDefaultPayment(true);
				userPaymentRepository.save(userPayment);
			} else {
				userPayment.setDefaultPayment(false);
				userPaymentRepository.save(userPayment);
			}
		}
	}
	
	@Override
	public void setUserDefaultShipping(Long userShippingId, User user) {
		List<UserShipping> userShippingList = (List<UserShipping>) userShippingRepository.findAll();
		
		for (UserShipping userShipping : userShippingList) {
			if(userShipping.getId() == userShippingId) {
				userShipping.setUserShippingDefault(true);
				userShippingRepository.save(userShipping);
			} else {
				userShipping.setUserShippingDefault(false);
				userShippingRepository.save(userShipping);
			}
		}
	}
	
	@Override
	public UserPayment findPaymentById(Long id) {
		 Optional<UserPayment> userPayment = userPaymentRepository.findById(id);
		 if(userPayment.isPresent()) {
			 return userPayment.get();
		 } else {
			 return null;
		 }
	}
	
	@Override
	public void removePaymentById(Long id) {
		 Optional<UserPayment> userPayment = userPaymentRepository.findById(id);
		 if(userPayment.isPresent()) {
			 userPaymentRepository.delete(userPayment.get());
		 }		
	}
	
	public UserShipping findShippingById(Long id) {
		Optional<UserShipping> UserShipping = userShippingRepository.findById(id);
		 if(UserShipping.isPresent()) {
			 return UserShipping.get();
		 } else {
			 return null;
		 }
	}
	
	public void removShippingeById(Long id) {
		 Optional<UserShipping> UserShipping = userShippingRepository.findById(id);
		 if(UserShipping.isPresent()) {
			 userShippingRepository.delete(UserShipping.get());
		 }	
	}

	@Override
	public Payment setByUserPayment(UserPayment userPayment, Payment payment) {
		payment.setType(userPayment.getType());
		payment.setHolderName(userPayment.getHolderName());
		payment.setCardNumber(userPayment.getCardNumber());
		payment.setExpiryMonth(userPayment.getExpiryMonth());
		payment.setExpiryYear(userPayment.getExpiryYear());
		payment.setCvc(userPayment.getCvc());
		
		return payment;
	}
	
	@Override
	public BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress) {
		billingAddress.setBillingAddressName(userBilling.getUserBillingName());
		billingAddress.setBillingAddressStreet1(userBilling.getUserBillingStreet1());
		billingAddress.setBillingAddressStreet2(userBilling.getUserBillingStreet2());
		billingAddress.setBillingAddressCity(userBilling.getUserBillingCity());
		billingAddress.setBillingAddressState(userBilling.getUserBillingState());
		billingAddress.setBillingAddressCountry(userBilling.getUserBillingCountry());
		billingAddress.setBillingAddressZipcode(userBilling.getUserBillingZipcode());
		
		return billingAddress;
	}

	

}
