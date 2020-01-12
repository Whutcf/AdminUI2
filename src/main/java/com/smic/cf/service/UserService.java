package com.smic.cf.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smic.cf.domain.Role;
import com.smic.cf.domain.User;

public interface UserService {
	User verifyUser(String username, String password);

	String findUserById(Integer userid);

	void updatePasswordById(Integer userid, String newPassword);

	User findUserByUsername(String username);

	List<User> findAllUsers();

	List<User> findAllUserWithRoles();

	void updateStateById(String state, Integer userId, String username);

	void deleteUserById(Integer userId);

	void deleteUsers(List<User> users);

	List<Role> findUserRolesByUserId(Integer userId);

	List<Role> findUnAddedRolesByUserId(Integer userId);

	void addRoles(List<Role> roles);

	void deleteRole(Integer roleId, Integer userId);

	void deleteRoles(List<Role> roles);

	int updateUserInfo(User user);

	void addUser(User user);

	IPage<User> selectPage(Page<User> page);
	
	//弃用的方法
//	void addUser(String username, String password, String state);
}
