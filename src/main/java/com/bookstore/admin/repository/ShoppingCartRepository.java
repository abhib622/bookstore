package com.bookstore.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.entity.ShoppingCart;


public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

}
