package com.smic.cf.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smic.cf.domain.Role;
import com.smic.cf.domain.User;
import com.smic.cf.service.RoleService;
import com.smic.cf.service.UserService;
import com.smic.cf.util.Result;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
/**
 * AdminUI的admin模块
 * 
 * @author cai feng
 *
 */
public class AdminController {

	private final String STATE_DEFULT = "true";

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("/toAdminMaintain")
	public String toAdminMaintain(Model model) {
		log.info("进入管理员维护页面！");
		List<User> userList = userService.findAllUsers();
		System.out.println(userList.size());
		for (User user : userList) {
			System.out.println(user.getUserId());
		}
		model.addAttribute("userList", userList);
		return "admin/admin_maintain";
	}

	/**
	 * 页面跳转的新方法，进入页面后会直接加载用户信息调用 /showUserListWithRoles
	 * 
	 * @return
	 */
	@RequestMapping("/toAdminMaintain2")
	public String toAdminMaintain2() {
		log.info("进入管理员维护页面2！");
		return "admin/admin_maintain2";
	}

	@RequestMapping("/showUserList")
	@ResponseBody
	public Result<User> userList(
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
		log.info("查询所有用户信息给前台！");
//		修改为分页展示
//		List<User> userList = userService.findAllUsers();
//		Map<String, Object> users = new HashMap<String, Object>(16);
//		users.put("code", 0);
//		users.put("data", userList);
		Page<User> page = new Page<>(currentPage,limit,true);
		IPage<User> iPage = userService.selectPage(page);
		Result<User> result = new Result<>();
		result.setData(iPage.getRecords());
		result.setCode(0);
		result.setCount((int) iPage.getTotal());
		log.info("传送用户信息给前台！");
		return result;
	}

	@RequestMapping("/showUserListWithRoles")
	@ResponseBody
	public Object userListWithRoles() {
		log.info("查询用户信息（包含角色信息）给前台！");
		List<User> userListWithRoles = userService.findAllUserWithRoles();
		Map<String, Object> users = new HashMap<String, Object>(16);
		users.put("code", 0);
		users.put("data", userListWithRoles);
		log.info("传送用户信息（包含角色信息）给前台！");
		return JSON.toJSON(users);
	}

	@RequestMapping("/changeState")
	@ResponseBody
	public String changeState(@RequestParam Map<String, String> jsonMap,HttpServletRequest request) {
		log.info("修改用户的状态！");
		String state = jsonMap.get("state").toString();
		String strUserId = jsonMap.get("userId").toString();
		User userInfo = (User) request.getSession().getAttribute("userinfo");
		String username = userInfo.getUsername();
		Integer userId = (Integer) Integer.parseInt(strUserId);
		if (STATE_DEFULT.equalsIgnoreCase(state)) {
			userService.updateStateById("T", userId, username);
			log.info("用户的状态被修改为有效！");
		} else {
			userService.updateStateById("F", userId, username);
			log.info("用户的状态被修改为无效！");
		}
		return "success";
	}

	
	
	@RequestMapping("/deleteUser")
	@ResponseBody
	public String deleteUser(@RequestParam("userId") Integer userId) {
		log.info("删除用户！");
		userService.deleteUserById(userId);
		return "success";
	}

	@RequestMapping("/deleteAll")
	@ResponseBody
	public String deleteAll(@RequestBody List<User> users) {
		log.info("删除多个用户！");
		userService.deleteUsers(users);
		log.info("用户删除成功！");
		return "SUCCESS";
	}

	@RequestMapping("/toAdminMaintainUser")
	public String toAdminMaintainUser() {
		log.info("进入管理员添加角色页面！");
		return "admin/admin_maintain_user";
	}

	@RequestMapping("/updateUserInfo")
	@ResponseBody
	@CrossOrigin
	public String updateUserInfo(@RequestBody User user,HttpServletRequest request) {
		log.info("更新用户信息！");
		User userinfo = (User) request.getSession().getAttribute("userinfo");
		user.setUpdatePerson(userinfo.getUsername());
		user.setUpdatetime(new Date());
		int i = userService.updateUserInfo(user);
		System.out.println(i);
		log.info("用户信息更新成功！");
		return "SUCCESS";
	}

	/* 用户角色处理模块！ */

	@RequestMapping("/toAdminMaintainRole")
	public String toAdminMaintainRoles(String userId, Model model) {
		log.info("进入管理员角色维护页面！");
		model.addAttribute("userId", userId);
		return "admin/admin_maintain_roles";
	}

	@RequestMapping("/findUserRolesByUserId")
	@ResponseBody
	public Map<String, Object> findUserRolesByUserId(@RequestParam("userId") Integer userId) {
		log.info("查询用户的角色信息！");
		List<Role> roleList = userService.findUserRolesByUserId(userId);
		Map<String, Object> roles = new HashMap<String, Object>(16);
		roles.put("code", 0);
		roles.put("data", roleList);
		log.info("返回用户角色信息！");
		return roles;
	}

	@RequestMapping("/toAddminAddRoles")
	public String toAddminAddRoles(String userId, Model model) {
		log.info("进入管理员添加角色页面！");
		model.addAttribute("userId", userId);
		return "admin/admin_add_roles";
	}

	@RequestMapping("/findUnAddedRolesByUserId")
	@ResponseBody
	public String findUnAddedRolesByUserId(@RequestParam("userId") Integer userId) {
		log.info("查询当前用户为添加的角色信息！");
		List<Role> roleList = userService.findUnAddedRolesByUserId(userId);
		Map<String, Object> roles = new HashMap<String, Object>(16);
		roles.put("code", 0);
		roles.put("data", roleList);
		log.info("返回未添加的用户角色信息！");
		return JSON.toJSONString(roles);
	}

	@RequestMapping("/addRoles")
	@ResponseBody
	public String addRoles(@RequestBody List<Role> roles) {
		log.info("为用户添加角色！");
		userService.addRoles(roles);
		log.info("为用户添加角色！");
		return "SUCCESS";
	}

	@RequestMapping("/deleteRole")
	@ResponseBody
	public String deleteRole(@RequestParam("roleId") Integer roleId, @RequestParam("userId") Integer userId) {
		log.info("为用户删除某个角色！");
		userService.deleteRole(roleId, userId);
		log.info("成功为用户删除某个角色！");
		return "SUCCESS";
	}

	@RequestMapping("/deleteRoles")
	@ResponseBody
	public String deleteRoles(@RequestBody List<Role> roles) {
		log.info("为用户删除多个角色！");
		userService.deleteRoles(roles);
		log.info("成功为用户删除多个角色！");
		return "SUCCESS";
	}
	
	
	//角色模块
	@RequestMapping("/showRoleList")
	@ResponseBody
	public Result<Role> roleList(@RequestParam(value="page",required=false,defaultValue="1")Integer currentPage,
			@RequestParam(value="limit",required=false,defaultValue="10")Integer limit){
		log.info("查询角色清单！");
		
		Page<Role> page = new Page<Role>(currentPage,limit);
		IPage<Role> iPage = roleService.selectPage(page);
		Result<Role> result = new Result<Role>();
		result.setCode(0);
		result.setCount((int) iPage.getTotal());
		result.setData(iPage.getRecords());
		log.info("返回角色清单给前台！");
		return result;
	}

	
	//文件上传下载模块
}
