package com.smic.cf.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 
 * @ClassName DruidConfiguration
 * @Description 可以将Druid作为连接池使用，durid访问地址localhost:8080/druid/
 * @author cai feng
 * @date 2019年6月15日
 *
 */
@Configuration
public class DruidConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);
	
	@Bean
	public ServletRegistrationBean<StatViewServlet> druidStatViewServle() {
		// 注册服务
		ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(),"/druid/*");
		// 白名单(为空表示,所有的都可以访问,多个IP的时候用逗号隔开)
		servletRegistrationBean.addInitParameter("allow", "192.168.1.3,127.0.0.1,192.168.1.8");
		// IP黑名单 (存在共同时，deny优先于allow)
		servletRegistrationBean.addInitParameter("deny", "127.0.0.2");
		// 设置登录的用户名和密码
		servletRegistrationBean.addInitParameter("loginUsername", "root");
		servletRegistrationBean.addInitParameter("loginPassword", "0316");
		// 是否能够重置数据.
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<WebStatFilter> druidStatFilter() {
		FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>(new WebStatFilter());
		// 添加过滤规则
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加不需要忽略的格式信息
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		logger.info("druid初始化成功!");
		return filterRegistrationBean;
	}
}