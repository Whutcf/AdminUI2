package com.smic.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smic.cf.entities.pojo.UserRole;
import com.smic.cf.mapper.UserRoleMapper;
import com.smic.cf.service.UserRoleService;

import javax.annotation.Resource;

/**
 * @author 蔡明涛
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl implements UserRoleService {

	@Resource
	private UserRoleMapper userRoleMapper;
	
	@Override
	public void add(List<UserRole> userRoles) {
		for (UserRole userRole : userRoles) {			
			userRoleMapper.insert(userRole);
		}
		

	}

	@Override
	public void deleteRole(Integer roleId, Integer userId) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("role_id", roleId);
		map.put("user_id", userId);		
		userRoleMapper.deleteByMap(map);
		
	}

	@Override
	public void deleteRoles(List<UserRole> userRoles) {
		for (UserRole userRole : userRoles) {
			LambdaQueryWrapper<UserRole> lambdaQuery = Wrappers.lambdaQuery();
			lambdaQuery.eq(UserRole::getRoleId, userRole.getRoleId()).eq(UserRole::getUserId, userRole.getUserId());
			userRoleMapper.delete(lambdaQuery);
		}
		
	}

}
