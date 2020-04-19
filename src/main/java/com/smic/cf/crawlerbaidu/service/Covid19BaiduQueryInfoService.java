package com.smic.cf.crawlerbaidu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smic.cf.crawlerbaidu.pojo.Covid19BaiduQueryInfo;

import java.util.List;

/**
 * @Description
 * @ClassName Covid19BaiduQueryInfoService
 * @Author 蔡明涛
 * @Date 2020/4/19 12:45
 **/
public interface Covid19BaiduQueryInfoService extends IService<Covid19BaiduQueryInfo> {
    /**
     * 存储百度查询热词，谣言和常识资源
     * @param baiduSourceInformation json字符串
     * @return java.util.List<com.smic.cf.crawlerbaidu.pojo.Covid19BaiduQueryInfo>
     * @author 蔡明涛
     * @date 2020/4/19 14:30
     */
    List<Covid19BaiduQueryInfo> getBaiduQueryInfoList(String baiduSourceInformation);
}
