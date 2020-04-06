package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smic.cf.entities.pojo.User;
import com.smic.cf.entities.pojo.UserRole;
import com.smic.cf.mapper.UserMapper;
import com.smic.cf.mapper.UserRoleMapper;
import com.smic.cf.service.UserService;
import com.smic.cf.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName UsersServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @author cai feng
 * @date 2019年6月21日
 *
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UsersServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private UserRoleMapper userRoleMapper;

	@Override
	public User verifyUser(String userName, String password) {
		log.debug("进入UsersService层:verifyUser!");
		
		LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
		
		lambdaQuery.eq(User::getUserName,userName).eq(User::getPassword, password);
		
		User user = userMapper.selectOne(lambdaQuery);
		
		return user;
	}

	@Override
	public String findPasswordById(Integer userid) {
		log.info("查看用户的旧密码！");
		String password = userMapper.findUserPasswordById(userid);
		return password;
	}

	@Override
	public void updatePasswordById(Integer userid, String newPassword) {
		log.info("修改密码！");
		userMapper.updatePasswordById(userid, newPassword);
	}

	@Override
	public User findUserByUsername(String userName) {
		
		LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
				
		lambdaQuery.eq(User::getUserName, userName);	
		
		return userMapper.selectOne(lambdaQuery);
	}

	@Override
	public List<User> findAllUsers() {
		return userMapper.selectList(null);
	}

	@Override
	public void updateStateById(String state, Integer userId, String userName) {
		userMapper.updateStateById(state, userId);
		// 记录更新时间及记录更新人员
		User user = userMapper.selectById(userId);
		user.setUpdateTime(new Date());
		user.setUpdatePerson(userName);
		LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
		lambdaQuery.eq(User::getUserId, userId);
		userMapper.update(user, lambdaQuery);
		
		log.info("{} 更新了 {} 的状态",user.getUpdatePerson(),user.getUserName());
	}

	@Override
	public void deleteUserById(Integer userId) {
		log.info("删除用户角色信息！");
		LambdaQueryWrapper<UserRole> lambdaQuery = Wrappers.lambdaQuery();
		userRoleMapper.delete(lambdaQuery.eq(UserRole::getUserId, userId));
		log.info("删除用户！");
		userMapper.deleteById(userId);
	}

	@Override
	public void deleteUsers(List<User> users) {
		for (User user : users) {
			log.info("删除用户 {} 角色信息！",user.getUserName());
			LambdaQueryWrapper<UserRole> lambdaQuery = Wrappers.lambdaQuery();
			userRoleMapper.delete(lambdaQuery.eq(UserRole::getUserId, user.getUserId()));
			log.info("删除用户 {}!" , user.getUserName());
			userMapper.deleteById(user.getUserId());
		}
	}

	@Override
	public List<User> findAllUserWithRoles() {

		return userMapper.findAllUserWithRoles();
	}


	@Override
	public int updateUserInfo(User user) {

		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", user.getUserId());
		int i = userMapper.update(user, queryWrapper);
		return i;

	}

	@Override
	public void addUser(User user) {
		log.info("处理注册用户！");
		userMapper.insert(user);
	}

	@Override
	public Result<User> selectPage(Integer currentPage,Integer limit,String userName) {
		
		LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
		
		lambdaQuery.like(!StringUtils.isEmpty(userName), User::getUserName, userName).orderByDesc(User::getCreateTime).orderByDesc(User::getUpdateTime);
		
		Page<User> page = new Page<>(currentPage, limit);
		
		IPage<User> iPage = userMapper.selectPage(page, lambdaQuery);
		
		Result<User> result = new Result<>();
		
		result.setCount((int) iPage.getTotal());
		
		result.setData(iPage.getRecords());

		return result;
	}

	@Override
	public String findUserNameById(Integer userId) {
		String userName = userMapper.selectById(userId).getUserName();
		return userName;
	}

	@Override
	public User findUserById(Integer userId) {
		User user = userMapper.selectById(userId);
		return user;
	}


}
