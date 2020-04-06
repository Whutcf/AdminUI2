package com.smic.cf.service;

import java.util.List;

import com.smic.cf.entities.pojo.UserRole;

/**
 * @Description 用户角色处理接口
 * @ClassName UserRoleService
 * @author 蔡明涛
 * @date 2020/3/6 21:03
 */
public interface UserRoleService {

	/**
	 * 添加用户角色集合
	 * @param userRoles 用户角色集合
	 * @return void
	 * @author 蔡明涛
	 * @date 2020/3/6 21:04
	 */
	void add(List<UserRole> userRoles);

	/**
	 * 根据用户ID和角色ID删除角色
	 * @param roleId userId  角色ID和用户ID
	 * @return void
	 * @author 蔡明涛
	 * @date 2020/3/6 21:05
	 */
	void deleteRole(Integer roleId, Integer userId);

	/**
	 * 删除用户角色
	 * @param userRoles 用户角色合集
	 * @return void
	 * @author 蔡明涛
	 * @date 2020/3/6 21:00
	 */
	void deleteRoles(List<UserRole> userRoles);

}
