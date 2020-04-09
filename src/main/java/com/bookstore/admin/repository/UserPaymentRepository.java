package com.bookstore.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.entity.UserPayment;


public interface UserPaymentRepository extends CrudRepository<UserPayment, Long>{

}
