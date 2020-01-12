package com.smic.cf.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * 
 * @Description: 
 * @author cai feng
 * @date 2019年7月24日
 */
@Configuration
public class MybatisConfiguration {

	 /*
	    * 分页插件，自动识别数据库类型
	    * 多租户，请参考官网【插件扩展】
	    */
	    @Bean
	    public PaginationInterceptor paginationInterceptor() {
	    	PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
	    	//设置方言
	    	paginationInterceptor.setDialectType("mysql");
	    	//设置默认单页显示条目数，默认单页显示500条
	    	paginationInterceptor.setLimit(10);
	        return paginationInterceptor;
	    }
}
