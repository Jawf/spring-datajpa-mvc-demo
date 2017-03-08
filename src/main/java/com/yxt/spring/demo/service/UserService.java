package com.yxt.spring.demo.service;

import java.util.List;

import com.yxt.spring.demo.entity.User;

public interface UserService {
	
	User findById(Integer id);

	User save(String name);

	List<User> findAll();

	List<User> findByIdGreaterThanAndNameLike(Integer id, String name);

	List<User> findByNameLike(String name);
}