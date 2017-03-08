package com.yxt.spring.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yxt.spring.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Serializable> {

	User findById(Integer id);

	List<User> findByIdGreaterThanAndNameLike(Integer id, String name);

	@Query("select u from User u where name like :name")
	List<User> findByGivenQuery(@Param("name") String name);
}