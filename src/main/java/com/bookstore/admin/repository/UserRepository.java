package com.bookstore.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.entity.User;


public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
	User findByEmail(String email);
}
