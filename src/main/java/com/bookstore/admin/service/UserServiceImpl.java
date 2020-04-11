package com.bookstore.admin.service;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.entity.TokenVerification;
import com.bookstore.admin.entity.User;
import com.bookstore.admin.entity.UserPayment;
import com.bookstore.admin.entity.UserRole;
import com.bookstore.admin.entity.UserShipping;
import com.bookstore.admin.repository.TokenVerificationRepository;
import com.bookstore.admin.repository.UserPaymentRepository;
import com.bookstore.admin.repository.RoleRepository;
import com.bookstore.admin.repository.UserRepository;
import com.bookstore.admin.repository.UserShippingRepository;

@Service
public class UserServiceImpl implements UserService {
	
private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserShippingRepository userShippingRepository;
	@Autowired
	private UserPaymentRepository userPaymentRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private TokenVerificationRepository tokenVerificationRepository;
	
	
	@Override
	public TokenVerification getVerificationToken(final String token) {
		return tokenVerificationRepository.findByToken(token);
	}
	
	@Override
	public void createTokenVerificationForUser(final User user, final String token) {
		final TokenVerification myToken = new TokenVerification(token, user);
		tokenVerificationRepository.save(myToken);
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public User findByEmail (String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public User createUser(User user, Set<UserRole> userRoles){
		User localUser = userRepository.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(userRoles);
			
			localUser = userRepository.save(user);
		}
		
		return localUser;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	public void removeUser(User user) {
		userRepository.delete(user);
	}

	@Override
	public UserShipping findByuserShippingId(Long id) {
		Optional<UserShipping> userShipping = userShippingRepository.findById(id);
		return userShipping.get();
	}

	@Override
	public UserPayment findByuserPaymentId(Long id) {
		Optional<UserPayment> UserPayment = userPaymentRepository.findById(id);
		return UserPayment.get();
	}
	
	
}
