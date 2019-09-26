package com.my.xunwu.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.my.xunwu.entity.User;
import com.my.xunwu.entity.Role;
import com.my.xunwu.repository.RoleRepository;
import com.my.xunwu.repository.UserRepository;
import com.my.xunwu.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public User findUserByName(String userName) {
		User user = userRepository.findByName(userName);
		if(user == null) {
			return null;
		}
		
		List<Role> roles = roleRepository.findRolesByUserId(user.getId());
		if(roles == null || roles.isEmpty()) {
			throw new DisabledException("权限非法");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName())));
		user.setAuthorityList(authorities);
		return user;
	}

}
