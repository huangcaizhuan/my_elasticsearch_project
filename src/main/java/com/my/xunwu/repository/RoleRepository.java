package com.my.xunwu.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.my.xunwu.entity.Role;

/**
 * 角色数据DAO
 * @author hcz
 * @date 2019.9.26
 */
public interface RoleRepository extends CrudRepository<Role, Long>{
	List<Role> findRolesByUserId(Long userId);
}
