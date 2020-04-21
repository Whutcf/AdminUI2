package com.smic.cf.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smic.cf.entities.pojo.ForeignCountryCovid19;
import com.smic.cf.entities.pojo.ForeignStatisticsTrendChartData;

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
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.smic.cf.entitis.pojo.ForeignCountryCovid19>
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

    /**
     * 根据Id找对象
     * @param locationId 地理位置Id
     * @return com.smic.cf.entities.pojo.ForeignCountryCovid19
     * @author 蔡明涛
     * @date 2020/4/5 1:18e
     */
    ForeignCountryCovid19 getForeignCountryById(Long locationId);

    /**
     * 根据Id找对象
     * @param locationId 地理位置id
     * @return java.util.List<com.smic.cf.entities.pojo.ForeignStatisticsTrendChartData>
     * @author 蔡明涛
     * @date 2020/4/5 1:24
     */
    List<ForeignStatisticsTrendChartData> getTrendChartDataList(Long locationId);

    /**
     *  根据id查找数据库最新一条记录
     * @param locationId 地理位置id
     * @return com.smic.cf.entities.pojo.ForeignStatisticsTrendChartData
     * @author 蔡明涛
     * @date 2020/4/6 18:53
     */
    ForeignStatisticsTrendChartData getYesterdayCountryDataById(Long locationId);

    /**
     * 获取世界当前确诊或累计确诊人数的集合 [{name:美国,value:678999},{...}]
     *
     * @param flag 1:当前确诊 2:累计确诊
     * @return com.alibaba.fastjson.JSONArray
     * @author 蔡明涛
     * @date 2020/4/21 22:30
     */
    JSONArray getWorldCovid19MapData(Integer flag);
}
