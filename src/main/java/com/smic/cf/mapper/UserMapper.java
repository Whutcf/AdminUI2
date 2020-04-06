package com.smic.cf.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.entities.pojo.Role;
import com.smic.cf.entities.pojo.User;

/**
 * @author 蔡明涛
 * @date 2020.03.05 00:12
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {

	String findUserPasswordById(Integer userid);

	void updatePasswordById(@Param("userid") Integer userid, @Param("newpassword") String newPassword);

	void insertUser(HashMap<String, String> userMap);

	void updateStateById(@Param("state") String state, @Param("userId") Integer userId);

	void deleteUsers(List<User> users);

	List<User> findAllUserWithRoles();

	List<Role> findUserRolesByUserId(Integer userId);

	void insertRoles(@Param("roleId") Integer roleId, @Param("userId") Integer userId);

	List<Role> findAllRoles();

	void deleteRole(Integer roleId, Integer userId);

	int countRols();

	void deleteUserRoles(Integer userId);

}
