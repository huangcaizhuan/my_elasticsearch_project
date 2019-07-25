package com.my.xunwu.entity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.my.xunwu.ApplicationTests;
import com.my.xunwu.repository.UserRepository;

public class UserRepositoryTest extends ApplicationTests{

	@Autowired
	private UserRepository  userRepository;
	
	@Test
	public void testFindOne() {
		User user =userRepository.findOne(1l);
		Assert.assertEquals("wali", user.getName());
	}
}
