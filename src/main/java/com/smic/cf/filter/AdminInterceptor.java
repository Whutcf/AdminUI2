package com.smic.cf.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.smic.cf.domain.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AdminInterceptor extends HandlerInterceptorAdapter {

	
	/**
	 * 进入拦截器首先执行的方法，通过返回true，拦截返回false
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 
		log.info("拦截器开始拦截用户是否登录！");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userinfo");
		if (ObjectUtils.isEmpty(user)) {
			log.info("返回登录界面");
			response.sendRedirect(request.getContextPath()+"/toPermisson");
			return false;
		}
		log.info(user.getUsername()+"登入AdminUI！");
		return true;
	}

}
