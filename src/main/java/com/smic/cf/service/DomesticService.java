package com.smic.cf.service;

import com.alibaba.fastjson.JSONArray;
import com.smic.cf.entities.pojo.ProvinceCovid19;

import java.util.List;

/**
 * @Description
 * @ClassName DomesticService
 * @Author 蔡明涛
 * @Date 2020/3/28 23:57
 **/
public interface DomesticService {
    /**
     * 保存国内疫情信息
     * @param provinceCovid19List 省市信息的集合
     * @return void
     * @author 蔡明涛
     * @date 2020/3/29 0:05
     */
    void insertDomesticData(List<ProvinceCovid19> provinceCovid19List);

    /**
     * 获取当前境外输入总数
     *
     * @return int
     * @author 蔡明涛
     * @date 2020/4/6 22:35
     */
    int getCurrentCovid19ForeignIn();

    /**
     * 获取疑似病例总数
     *
     * @return int
     * @author 蔡明涛
     * @date 2020/4/7 20:47
     */
    int getTotalSuspectedCount();

    /**
     * 获取全国当前确诊人数的集合 [{name:北京,value:131},{...}]
     *
     * @return com.alibaba.fastjson.JSONArray
     * @author 蔡明涛
     * @date 2020/4/7 21:35
     * @param flag 标记 1 代表当前确诊，2 代表累计确诊
     */
    JSONArray getProvinceCovidMapData(Integer flag);
}
