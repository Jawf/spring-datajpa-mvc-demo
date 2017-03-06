package com.yxt.spring.demo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxt.spring.demo.entity.User;
import com.yxt.spring.demo.repository.UserRepository;
import com.yxt.spring.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userDao;

	@Override
	public User findById(Integer id) {
		return userDao.findById(id);
	}

	@Override
	public User save(String name) {
		return userDao.save(new User(name));
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public List<User> findByIdGreaterThanAndNameLike(Integer id, String name) {
		return userDao.findByIdGreaterThanAndNameLike(id, name + "%");
	}
}