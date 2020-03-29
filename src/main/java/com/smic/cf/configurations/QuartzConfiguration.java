package com.smic.cf.configurations;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Description Quartz配置类
 * @ClassName QuartzConfiguration
 * @Author 蔡明涛
 * @Date 2020/3/11 20:00
 **/
@Configuration
public class QuartzConfiguration {
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        //覆盖已存在的任务
        factoryBean.setOverwriteExistingJobs(true);
        //延时60s启动定时任务，避免系统未完全启动而重跑
        factoryBean.setStartupDelay(60);
        return factoryBean;
    }

    /**
     * 创建scheduler
     * @return org.quartz.Scheduler
     * @author 蔡明涛
     * @date 2020/3/11 20:06
     */
    @Bean(name = "scheduler")
    public Scheduler scheduler(){
        return schedulerFactoryBean().getScheduler();
    }

}
