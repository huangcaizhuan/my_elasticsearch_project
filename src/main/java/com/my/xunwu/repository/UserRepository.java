package com.my.xunwu.repository;

import org.springframework.data.repository.CrudRepository;

import com.my.xunwu.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByName(String userName);
}
