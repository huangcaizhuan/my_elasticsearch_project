package com.my.xunwu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.my.xunwu.entity.User;
import com.my.xunwu.service.IUserService;

/**
 * 自定义认证实现
 * @author huangcaizhuan
 *
 */
public class AuthProvider implements AuthenticationProvider{

	@Autowired 
	private IUserService userService;
	
	private final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String inputPassword = (String) authentication.getCredentials();
		User user = userService .findUserByName(userName);
		if(user == null) {
			throw new AuthenticationCredentialsNotFoundException("authError");
		}
		user.getPassword().equals(inputPassword);
		if(this.passwordEncoder.isPasswordValid(user.getPassword(), inputPassword, user.getId())) {
			
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
