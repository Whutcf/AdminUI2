package com.smic.cf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 蔡明涛
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.smic.cf.mapper")
public class AdminUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminUiApplication.class, args);
        log.info("项目启动了。。。。。。。。。。。。。");
    }

}
