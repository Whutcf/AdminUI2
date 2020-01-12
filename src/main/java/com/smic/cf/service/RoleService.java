package com.smic.cf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smic.cf.domain.Role;

public interface RoleService {

	IPage<Role> selectPage(Page<Role> page);

}
