package com.smic.cf.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smic.cf.domain.User;
import com.smic.cf.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes("hasmsg")
@Controller
/**
 * 
 * @ClassName UserController
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @author cai feng
 * @date 2019年6月22日
 *
 */
public class UserController {
	
	private static String STATE = "F";
	
	@Autowired
	private UserService usersService;
	
	@RequestMapping("/login")
	public String login(Model model,HttpServletRequest request) {
		log.info("进入用户登录模块！");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (password != null && username !=null) {			
			User user = usersService.verifyUser(username, password);
			if(!ObjectUtils.isEmpty(user)) {
				String state = user.getState();
				if(STATE.equalsIgnoreCase(state)) {
					log.info("用户已失效！");
					model.addAttribute("loginError",true);
					model.addAttribute("error", "用户已失效，请联系管理员！");
					return "login";
				}
				request.getSession().setAttribute("userid", user.getUserId());
				request.getSession().setAttribute("userinfo", user);
				log.info("进入主页！");
				return "frame";
			}
		}
		log.info("密码错误！");
		model.addAttribute("username", username);
		model.addAttribute("loginError",true);
		model.addAttribute("error", "密码错误！");
		return "login";
	}
	
	@RequestMapping("/modifyPassword")
	public String modifyPassword(Model model,HttpServletRequest request) {
		log.info("修改用户密码！");
		String oldPassword = request.getParameter("oldpassword");
		String newPassword = request.getParameter("password");
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		String initialPassword = usersService.findUserById(userid);
		if(!initialPassword.equalsIgnoreCase(oldPassword)) {
			model.addAttribute("verifyFailure",true);
			model.addAttribute("error", "密码不正确！");
			log.info("返回修改密码页面！");
 			return "tgls/modify_password";
		}
		usersService.updatePasswordById(userid,newPassword);
		log.info("密码已修改！");
		model.addAttribute("hasmsg",true);
		model.addAttribute("msg", "密码已修改，请重新登录！");
		return "login";
	}
	
	@RequestMapping("/checkUserName")
	@ResponseBody
	public String checkUserName(Model model,@RequestParam("username")String username) {
		log.info("验证用户名是否存在！");
		User user = usersService.findUserByUsername(username);
		if(ObjectUtils.isEmpty(user)) {
			log.info("用户名不存在，请注册帐号！");
			model.addAttribute("username", username);
			model.addAttribute("nameNotExist",true);
			model.addAttribute("error", "用户名不存在，请注册帐号！");
			return "SUCCESS";
		}
		log.info("用户名存在！");
		return null;
	}
	
	@RequestMapping("/regist")
	public String regist(Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		log.info("新用户注册！");
		String username = request.getParameter("username");
		String password = request.getParameter("passWord");
		String state = request.getParameter("state");
		User verifyUser = usersService.findUserByUsername(username);
		if(!ObjectUtils.isEmpty(verifyUser)) {
			model.addAttribute("username", username);
			model.addAttribute("verifyFailure",true);
			model.addAttribute("error", "用户名已存在！");
			log.info("用户名已存在!");
			return "regist";
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setState(state);
		user.setCreatetime(new Date());
		user.setUpdatetime(null);
		usersService.addUser(user);
		//usersService.addUser(username,password,state);
		log.info("用户注册成功！");
		redirectAttributes.addAttribute("hasmsg", true);
		redirectAttributes.addAttribute("msg", "您已注册成功，请登录！");
		return "redirect:/toLogin";
	}
	
	@RequestMapping("/logout")
	public  String logout(HttpServletRequest request) {
		log.info("登出系统！");
		request.getSession().removeAttribute("userinfo");
		return "login";
	}
	

}
