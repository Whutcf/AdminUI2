package com.smic.cf.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smic.cf.entities.pojo.Role;
import com.smic.cf.entities.pojo.User;
import com.smic.cf.mapper.RoleMapper;
import com.smic.cf.mapper.UserRoleMapper;
import com.smic.cf.service.RoleService;
import com.smic.cf.util.Result;


/**
 * @author 蔡明涛
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

	@Resource
	RoleMapper roleMapper;
	
	@Resource
	UserRoleMapper userRoleMapper;

	@Override
	public Result<Role> selectPage(Integer currentPage, Integer limit) {
		
		Page<Role> page = new Page<>(currentPage,limit);
		
		IPage<Role> iPage = roleMapper.selectPage(page, null);
		
		Result<Role> result = new Result<>();
		
		result.setCount((int) iPage.getTotal());
		
		result.setData(iPage.getRecords());
		
		return result;
	}

	@Override
	public Result<Role> findUserRolesByUserId(Integer userId) {

		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		
		queryWrapper.eq("c.user_id", userId);
		
		List<Role> roles = roleMapper.selectRolesByUserId(queryWrapper);
		
		Result<Role> result = new Result<>();
		
		result.setData(roles);
		
		return result;
	}

	@Override
	public Result<Role> findUnAddedRolesByUserId(Integer userId) {
		
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();

		queryWrapper.eq("user_id", userId);
		
		List<Integer> roleIds = userRoleMapper.selectRoleIds(queryWrapper);
						
		LambdaQueryWrapper<Role> lambdaQuery = Wrappers.lambdaQuery();
		
		lambdaQuery.notIn(!roleIds.isEmpty(), Role::getRoleId, roleIds);
		
		List<Role> roles = roleMapper.selectList(lambdaQuery);
		
		Result<Role> result = new Result<>();
		
		result.setData(roles);
		
		return result;
	}
	
	
}
