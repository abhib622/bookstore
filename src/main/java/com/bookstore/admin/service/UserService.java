package com.bookstore.admin.service;

import java.util.Set;

import com.bookstore.admin.entity.TokenVerification;
import com.bookstore.admin.entity.User;
import com.bookstore.admin.entity.UserPayment;
import com.bookstore.admin.entity.UserRole;
import com.bookstore.admin.entity.UserShipping;

public interface UserService {
	
	TokenVerification getVerificationToken(final String token);
	
	void createTokenVerificationForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail (String email);
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);

	User findById(Long id);
	
	UserShipping findByuserShippingId(Long id);
	
	UserPayment findByuserPaymentId(Long id);
	
	void removeUser(User user);

}
