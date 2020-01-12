package com.smic.cf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class AdminUiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AdminUiApplication.class, args);
		log.info("项目启动了。。。。。。。。。。。。。");
	}

}
