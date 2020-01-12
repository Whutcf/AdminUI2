package com.smic.cf.configurations;

import javax.annotation.Resource;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import com.smic.cf.filter.AdminInterceptor;

@SpringBootConfiguration
public class SessionConfiguation extends WebMvcConfigurationSupport {

	@Resource
	AdminInterceptor adminInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(adminInterceptor).addPathPatterns("/tgls/**", "/admin/**").excludePathPatterns("/",
				"/tgls/iconfont.html");
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/")
				.addResourceLocations("classpath:/resources/").addResourceLocations("classpath:/static/")
				.addResourceLocations("classpath:/public/");
		super.addResourceHandlers(registry);

	}

}
