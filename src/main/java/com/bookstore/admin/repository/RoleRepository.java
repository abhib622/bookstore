package com.bookstore.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.Enum.RoleName;
import com.bookstore.admin.entity.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByname(RoleName name);
}
