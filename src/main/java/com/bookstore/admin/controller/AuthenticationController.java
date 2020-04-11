package com.bookstore.admin.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.admin.Enum.RoleName;
import com.bookstore.admin.constant.USConstants;
import com.bookstore.admin.entity.Role;
import com.bookstore.admin.entity.ShoppingCart;
import com.bookstore.admin.entity.TokenVerification;
import com.bookstore.admin.entity.User;
import com.bookstore.admin.entity.UserRole;
import com.bookstore.admin.entity.UserShipping;
import com.bookstore.admin.repository.RoleRepository;
import com.bookstore.admin.repository.TokenVerificationRepository;
import com.bookstore.admin.service.UserSecurityService;
import com.bookstore.admin.service.UserService;
import com.bookstore.admin.utility.MailConstructor;
import com.bookstore.admin.utility.SecurityUtility;
import com.bookstore.admin.utility.ServiceUtility;


@Controller
public class AuthenticationController {
	
	@Autowired
	UserService userService;
	@Autowired
	MailConstructor mailConstructor;
	@Autowired
	MailSender mailSender;
	@Autowired
	UserSecurityService userSecurityService;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	TokenVerificationRepository tokenVerificationRepository;
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}
	
	/** Create the new User**/	
	@RequestMapping(value="/newUser", method = RequestMethod.POST)
	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,@ModelAttribute("username") String username, Model model) throws Exception{
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);
		
		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);
			
			return "myAccount";
		}
		
		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);
			return "myAccount";
		}
		
		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);
		
		String password = SecurityUtility.randomPassword();
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		Set<UserRole> userRoles = new HashSet<>();
		Role role = roleRepository.findByname(RoleName.ROLE_USER);
		if(role != null) {
			userRoles.add(new UserRole(user, role));
		} else {
			Role role1 = new Role();
			role1.setName(RoleName.ROLE_USER);
			userRoles.add(new UserRole(user, role1));
		}
		userService.createUser(user, userRoles);
		
		String token = UUID.randomUUID().toString();
		
		userService.createTokenVerificationForUser(user, token);
		
		String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(email);
		
		model.addAttribute("emailSent", "true");
		
		return "myAccount";
	}
	
	/** Verify the user token**/	
	@RequestMapping("/newUser")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
		TokenVerification passToken = userService.getVerificationToken(token);

		if (passToken == null) {
			String message = "Invalid Token.";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}
		
		if(!ServiceUtility.CheckForDateExpiry(passToken.getExpiryDate())) {
			String message = "Your signup token is expired. Please try again creating the account";
			model.addAttribute("message", message);
			userService.removeUser(passToken.getUser());
			return "redirect:/badRequest";
		}

		User user = passToken.getUser();
		String username = user.getUsername();
		
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		model.addAttribute("user", user);

		model.addAttribute("classActiveEdit", true);
		return "myProfile";
	}

	@RequestMapping(value="/updateUserInfo", method=RequestMethod.POST)
	public String updateUserInfo(
			@ModelAttribute("user") User user,
			@ModelAttribute("newPassword") String newPassword,
			Model model
			) throws Exception {
		User currentUser = userService.findById(user.getId());
		
		if(currentUser == null) {
			throw new Exception ("User not found");
		}
		
		/*check email already exists*/
		if (userService.findByEmail(user.getEmail())!=null) {
			if(userService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
				model.addAttribute("emailExists", true);
				return "myProfile";
			}
		}
		
		/*check username already exists*/
		if (userService.findByUsername(user.getUsername())!=null) {
			if(userService.findByUsername(user.getUsername()).getId() != currentUser.getId()) {
				model.addAttribute("usernameExists", true);
				return "myProfile";
			}
		}
		
//		update password
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			if(passwordEncoder.matches(user.getPassword(), dbPassword)){
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword", true);
				
				return "myProfile";
			}
		}
		
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		currentUser.setPhone(user.getPhone());
		currentUser.setShoppingCart(new ShoppingCart(currentUser));
		User savedUsaer = userService.save(currentUser);
		
		TokenVerification passToken = tokenVerificationRepository.findByUser(savedUsaer);
		tokenVerificationRepository.delete(passToken);
		
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);
		model.addAttribute("classActiveEdit", true);
		
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("listOfCreditCards", true);
		
		UserDetails userDetails = userSecurityService.loadUserByUsername(savedUsaer.getUsername());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		model.addAttribute("orderList", user.getOrderList());
		
		return "redirect:/";
	}
	
	
	/** Forgot Password Start**/
	@RequestMapping("/forgetPassword")
	public String forgetPassword(
			HttpServletRequest request,
			@ModelAttribute("email") String email,
			Model model
			) {

		model.addAttribute("classActiveForgetPassword", true);
		
		User user = userService.findByEmail(email);
		
		if (user == null) {
			model.addAttribute("emailNotExist", true);
			return "myAccount";
		}
		
		String password = SecurityUtility.randomPassword();
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		
		userService.save(user);
		
		String token = UUID.randomUUID().toString();
		userService.createTokenVerificationForUser(user, token);
		
		String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(newEmail);
		
		model.addAttribute("forgetPasswordEmailSent", "true");
		
		
		return "myAccount";
	}
	
	/** User Profile**/
	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());
		
		UserShipping userShipping = new UserShipping();
		model.addAttribute("userShipping", userShipping);
		
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		List<String> stateList = USConstants.listOfUSStatesName;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("classActiveEdit", true);
		
		return "myProfile";
	}

}
