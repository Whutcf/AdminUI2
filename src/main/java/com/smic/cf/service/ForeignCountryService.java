package com.smic.cf.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smic.cf.pojo.ForeignCountryCovid19;

import java.util.List;

/**
 * @Description
 * @ClassName ForeignCountryService
 * @Author 蔡明涛
 * @date 2020.03.01 23:22
 */
public interface ForeignCountryService {
    /**
     * 将国外数据存入数据库
     * @param foreignCountryCovid19InfoList 国外实体数据集合
     * @author 蔡明涛
     * @date 2020.03.02 19:50
     **/
    void insertForeignCountryData(List<ForeignCountryCovid19> foreignCountryCovid19InfoList);

    /**
     * 获取国外疫情数据
     * @param page 当前页
     * @param limit 页面记录数
     * @param continents 大洲名
     * @param provinceName 国家名
     * @param field 排序的栏位
     * @param order 排序的顺序 ASE，null，DESC
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.smic.cf.pojo.ForeignCountryCovid19>
     * @author 蔡明涛
     * @date 2020/3/29 16:34
     */
    IPage<ForeignCountryCovid19> selectPage(Integer page, Integer limit, String continents, String provinceName, String field, String order);

    /**
     * 获取大洲数
     * @return java.util.List<java.lang.String>
     * @author 蔡明涛
     * @date 2020/3/29 19:55
     */
    List<String> getContinents();

    /**
     * 获取大洲对应的国家
     * @param continents 大洲
     * @return java.util.List<java.lang.String>
     * @author 蔡明涛
     * @date 2020/3/29 20:36
     */
    List<String> getCountries(String continents);

    /**
     * 获取echarts的summaryBarChartData所需要的数据
     * @return com.alibaba.fastjson.JSONObject
     * @author 蔡明涛
     * @date 2020/4/3 0:28
     */
    JSONObject getSummaryBarChartData();

    /**
     * 获取echarts的世界疫情各大洲人数占比
     * @return com.alibaba.fastjson.JSONObject
     * @author 蔡明涛
     * @date 2020/4/3 2:02
     */
    JSONObject getSummaryPieChartData();
}
