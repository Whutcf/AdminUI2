package com.smic.cf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smic.cf.domain.Role;
import com.smic.cf.domain.User;
import com.smic.cf.mapper.master.UserMapper;
import com.smic.cf.service.UserService;

import lombok.extern.slf4j.Slf4j;

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

	@Autowired
	private UserMapper userMapper;

	@Override
	public User verifyUser(String username, String password) {
		log.info("进入UsersService层:verifyUser!");
//		return userMapper.verifyUser(username, password); 使用MybatisPlus替换
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		queryWrapper.eq("password", password);
		User user = userMapper.selectOne(queryWrapper);
		return user;
	}

	@Override
	public String findUserById(Integer userid) {
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
	public User findUserByUsername(String username) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		// return userMapper.findUserByUserName(username);使用MybatisPlus替换
		return userMapper.selectOne(queryWrapper);
	}

	@Override
	public List<User> findAllUsers() {
		// return userMapper.findAllUsers(); 使用MybatisPlus替换
		return userMapper.selectList(null);
	}

	@Override
	public void updateStateById(String state, Integer userId, String username) {
		userMapper.updateStateById(state, userId);
		// 记录更新时间及记录更新人员
		User user = userMapper.selectById(userId);
		user.setUpdatetime(new Date());
		user.setUpdatePerson(username);
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq("user_id", userId);
		userMapper.update(user, queryWrapper);
	}

	@Override
	public void deleteUserById(Integer userId) {
		log.info("删除用户角色信息！");
		userMapper.deleteUserRoles(userId);
		log.info("删除用户！");
//		userMapper.deleteUserById(userId);
		userMapper.deleteById(userId);
	}

	@Override
	public void deleteUsers(List<User> users) {
		for (User user : users) {
			log.info("删除用户" + user.getUsername() + "的所有角色");
			userMapper.deleteUserRoles(user.getUserId());
			log.info("删除用户" + user.getUsername() + "！");
			userMapper.deleteById(user.getUserId());
		}
//		userMapper.deleteUsers(users); 舍弃该方法，为了在删除用户的同时能删除该用户的角色
	}

	@Override
	public List<User> findAllUserWithRoles() {

		return userMapper.findAllUserWithRoles();
	}

	@Override
	public List<Role> findUserRolesByUserId(Integer userId) {

		return userMapper.findUserRolesByUserId(userId);
	}

	@Override
	public List<Role> findUnAddedRolesByUserId(Integer userId) {
		List<Role> roles = userMapper.findUnAddedRolesByUserId(userId);// 查询未添加的角色
		List<Role> existRoles = userMapper.findUserRolesByUserId(userId);// 查询已添加的角色
		int rolesCount = userMapper.countRols();
		log.info("查询角色表当前的角色数是：" + rolesCount);
		log.info("用户已经拥有的角色数是：" + existRoles.size());
		if (roles.size() == rolesCount || (roles.size() == existRoles.size() && roles.size() == 0)) {
			log.info("当前用户未添加角色！");
			List<Role> roles2 = userMapper.findAllRoles();
			for (Role role : roles2) {
				User user = new User();
				user.setUserId(userId);
				ArrayList<User> users = new ArrayList<>();
				users.add(user);
				role.setUsers(users);
			}
			return roles2;
		}
		if (roles.isEmpty()) {
			log.info("当前用户无需添加角色！");
			return null;
		}
		return roles;
	}

	@Override
	public void addRoles(List<Role> roles) {
		for (Role role : roles) {
			ArrayList<User> users = role.getUsers();
			User user = users.get(0);
			userMapper.insertRoles(role.getRoleId(), user.getUserId());
			log.info("成功添加角色" + role.getRoleName());
		}

	}

	@Override
	public void deleteRole(Integer roleId, Integer userId) {
		userMapper.deleteRole(roleId, userId);
	}

	@Override
	public void deleteRoles(List<Role> roles) {
		for (Role role : roles) {
			ArrayList<User> users = role.getUsers();
			User user = users.get(0);
			userMapper.deleteRole(role.getRoleId(), user.getUserId());
		}

	}

	@Override
	public int updateUserInfo(User user) {

//		int i = userMapper.updateUserInfo(user);
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

	/**
	 * mybatisPlus的分页插件失效,暂时未找到解决方法，只能出此下策。
	 */
	@Override
	public IPage<User> selectPage(Page<User> page) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		Integer selectCount = userMapper.selectCount(queryWrapper);

		// TODO 需要解决Page失效问题
		page.setTotal(selectCount);
		long currentPage = (page.getCurrent() - 1) * page.getSize();
		queryWrapper.apply("1=1 order by user_id asc limit {0},{1}", currentPage, page.getSize());

		return userMapper.selectPage(page, queryWrapper);
	}

	/**
	 * 被MybatisPlus取代的方法
	 */
//	@Override
//	public void addUser(String username, String password, String state) {
//		log.info("处理注册用户！");
//		HashMap<String, String> userMap = new HashMap<>(16);
//		userMap.put("username", username);
//		userMap.put("password", password);
//		userMap.put("state", state);
//		userMapper.insertUser(userMap);
//	}

}
