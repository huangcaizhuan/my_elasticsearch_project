package com.my.xunwu.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.xunwu.entity.User;
import com.my.xunwu.repository.UserRepository;
import com.my.xunwu.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findUserByName(String userName) {
		return userRepository.findByName(userName);
	}

}
