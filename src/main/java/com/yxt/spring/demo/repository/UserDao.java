package com.yxt.spring.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yxt.spring.demo.entity.User;

public interface UserDao extends JpaRepository<User, Serializable> {
	User findById(Integer id);

	List<User> findByIdGreaterThanAndNameLike(Integer id, String name);
}