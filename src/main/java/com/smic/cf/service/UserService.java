package com.smic.cf.service;

import java.util.List;

import com.smic.cf.entities.pojo.User;
import com.smic.cf.util.Result;

public interface UserService {
	User verifyUser(String username, String password);

	String findPasswordById(Integer userid);

	void updatePasswordById(Integer userid, String newPassword);

	User findUserByUsername(String username);

	List<User> findAllUsers();

	List<User> findAllUserWithRoles();

	void updateStateById(String state, Integer userId, String username);

	void deleteUserById(Integer userId);

	void deleteUsers(List<User> users);

	int updateUserInfo(User user);

	void addUser(User user);

	Result<User> selectPage(Integer currentPage, Integer limit, String userName);

	String findUserNameById(Integer userId);

	User findUserById(Integer userId);
	
}
