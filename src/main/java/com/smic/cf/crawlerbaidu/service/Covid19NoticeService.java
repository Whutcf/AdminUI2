package com.smic.cf.crawlerbaidu.service;

import com.smic.cf.crawlerbaidu.pojo.Covid19Notice;

import java.util.List;

/**
 * @Description 百度数据源公告服务
 * @ClassName Covid19NoticeService
 * @Author 蔡明涛
 * @Date 2020/4/19 12:13
 **/
public interface Covid19NoticeService {
    /**
     * 保存公告信息
     * @param baiduSourceInformation json字符串
     * @return void
     * @author 蔡明涛
     * @date 2020/4/19 14:21
     */
    void saveCovid19Notice(String baiduSourceInformation);

    /**
     * 获取公告信息的集合
     *
     * @return java.util.List<com.smic.cf.crawlerbaidu.pojo.Covid19Notice>
     * @author 蔡明涛
     * @date 2020/4/19 16:47
     */
    List<Covid19Notice> getNotices();
}
