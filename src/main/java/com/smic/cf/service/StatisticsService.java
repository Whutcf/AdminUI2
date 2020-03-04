package com.smic.cf.service;

import com.smic.cf.pojo.Statistics;

/**
 * @Description TODO
 * @ClassName StatisticsService
 * @Author 蔡明涛
 * @date 2020.03.01 13:35
 */
public interface StatisticsService {
    /**
     * 更新或插入基础信息
     * @Param statistics 解析后的json字符串
     * @return void
     * @Author 蔡明涛
     * @Date 2020.03.01 13:36
     **/
    String insertStatics(Statistics statistics);
}
