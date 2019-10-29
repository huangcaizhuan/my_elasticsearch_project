package com.my.xunwu.base;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.springframework.security.core.context.SecurityContextHolder;

import com.my.xunwu.entity.User;

/**
 * 获取登录用户信息
 * @author hcz
 *
 */
public class LoginUserUtil {
	public static User load() {
		Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal != null && principal instanceof User) {
			return (User) principal;
		}
		return null;
	}
	
	public static Long getLonginUserId() {
		User user = load();
		if(user == null) {
			return -1L;
		}
		return user.getId();
	}
}
