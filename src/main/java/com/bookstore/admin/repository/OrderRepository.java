package com.bookstore.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
