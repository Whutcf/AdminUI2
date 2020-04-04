package com.smic.cf.crawlerbaidu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smic.cf.crawlerbaidu.dto.TrendBean;
import com.smic.cf.crawlerbaidu.dto.TrendListBean;
import com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist;

import java.util.List;

/**
 * @Description
 * @ClassName TrendService
 * @Author 蔡明涛
 * @Date 2020/4/4 16:20
 **/
public interface TrendService extends IService<Covid19TrendHist> {


    /**
     *  获取各地区的trend的历史数据
     * @param information 页面解析的json字符串
     * @return java.util.List<com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist>
     * @author 蔡明涛
     * @date 2020/4/4 19:36
     */
   List<Covid19TrendHist> getTrendHistData(String information);
}
