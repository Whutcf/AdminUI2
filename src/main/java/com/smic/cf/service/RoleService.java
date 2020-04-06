package com.smic.cf.service;

import com.smic.cf.entities.pojo.Role;
import com.smic.cf.util.Result;

/**
 * @author 蔡明涛
 */
public interface RoleService {

	Result<Role> selectPage(Integer currentPage,Integer limit);

	Result<Role> findUserRolesByUserId(Integer userId);

	Result<Role> findUnAddedRolesByUserId(Integer userId);

}
