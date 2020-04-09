package com.bookstore.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bookstore.admin.repository.RoleRepository;
import com.bookstore.admin.service.UserService;

@SpringBootApplication
public class BookstoreAdminApplication {
	
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreAdminApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		User user1 = new User();
//		user1.setFirstName("John");
//		user1.setLastName("Adams");
//		user1.setUsername("j");
//		user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
//		user1.setEmail("JAdams@gmail.com");
//		Set<UserRole> userRoles = new HashSet<>();
//		Role role = roleRepository.findByname(RoleName.ROLE_USER);
//		if(role != null) {
//			userRoles.add(new UserRole(user1, role));
//		}else {
//			Role role1= new Role();
//			role1.setName(RoleName.ROLE_USER);
//			userRoles.add(new UserRole(user1, role1));
//		}
//		userService.createUser(user1, userRoles);
//	}

}