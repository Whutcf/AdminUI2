package com.smic.cf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smic.cf.domain.Role;
import com.smic.cf.mapper.master.RoleMapper;
import com.smic.cf.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleMapper roleMapper;

	@Override
	public IPage<Role> selectPage(Page<Role> page) {
		log.info("查询用户角色清单！");
		return roleMapper.selectPage(page, null);
	}
	
	
}
