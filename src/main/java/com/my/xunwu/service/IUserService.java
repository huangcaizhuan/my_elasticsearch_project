package com.my.xunwu.service;

import com.my.xunwu.entity.User;

/**
 * 用户服务
 * @author huangcaizhuan
 *
 */
public interface IUserService {
	User findUserByName (String userName);
}
