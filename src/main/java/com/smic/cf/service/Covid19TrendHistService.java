package com.smic.cf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist;

/**
 * @Description
 * @ClassName Covid19TrendHistService
 * @Author 蔡明涛
 * @Date 2020/4/6 22:20
 **/
public interface Covid19TrendHistService extends IService<Covid19TrendHist> {
    /**
     * 获取昨日新增入境病例数据
     *
     * @return com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist
     * @author 蔡明涛
     * @date 2020/4/6 22:25
     */
    Covid19TrendHist getYesterdayForeignInIncr();
}
