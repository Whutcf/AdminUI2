package com.smic.cf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.smic.cf.pojo.Role;
import com.smic.cf.pojo.User;

/**
 * @author 蔡明涛
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

	List<Role> selectRolesByUserId(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

}
