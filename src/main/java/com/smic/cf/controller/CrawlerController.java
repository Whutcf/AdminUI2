package com.smic.cf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smic.cf.constants.CrawlerConstants;
import com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist;
import com.smic.cf.crawlerbaidu.service.TrendService;
import com.smic.cf.entities.pojo.Crawler;
import com.smic.cf.entities.pojo.ForeignCountryCovid19;
import com.smic.cf.entities.pojo.ForeignStatisticsTrendChartData;
import com.smic.cf.entities.pojo.ProvinceCovid19;
import com.smic.cf.service.DomesticService;
import com.smic.cf.service.ForeignCountryService;
import com.smic.cf.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 手动刷新获取爬虫信息
 * @ClassName CrawlerController
 * @Author 蔡明涛
 * @date 2020.03.01 12:15
 */
@RestController
@Slf4j
@RequestMapping("/crawler")
public class CrawlerController {

    @Resource
    private ForeignCountryService foreignCountryService;
    @Resource
    private DomesticService domesticService;
    @Resource
    private TrendService trendService;

    @GetMapping("/refresh")
    public ResultBean<String> refresh() {
        log.info("{}开启手动刷新, 数据源: {}", DateUtils.getCurrentDateTime(), Crawler.URL1);
        String foreignCountryInformation = CrawlerUtils.getJsonString(Crawler.URL1, Crawler.FOREIGN_STATIC_INFORMATION_REGEX_TEMPLATE, Crawler.FOREIGN_STATIC_INFORMATION_ATTRIBUTE);
        String domesticInformation = CrawlerUtils.getJsonString(Crawler.URL1, Crawler.DOMESTIC_STATIC_INFORMATION_REGEX_TEMPLATE, Crawler.DOMESTIC_STATIC_INFORMATION_ATTRIBUTE);
        //解析json数据
        List<ForeignCountryCovid19> foreignCountryCovid19List = CrawlerParser.parseForeignCountryInformation(foreignCountryInformation);
        List<ProvinceCovid19> provinceCovid19List = CrawlerParser.parseDomesticInformation(domesticInformation);
        //将解析的数据存入DB
        foreignCountryService.insertForeignCountryData(foreignCountryCovid19List);
        domesticService.insertDomesticData(provinceCovid19List);
        log.info("{} 手动刷新结束, 数据源: {}", DateUtils.getCurrentDateTime(), Crawler.URL1);

        log.info("手动刷新开启，时间{},数据来源:{}", DateUtils.getCurrentDateTime(),Crawler.URL2);
        String information = CrawlerUtils.getJsonString(Crawler.URL2, Crawler.BAIDU_DATA_REGEX_TEMPLATE, Crawler.BAIDU_DATA_ATTRIBUTE);
        // 解析json数据 并生成集合
        List<Covid19TrendHist> covid19TrendHists = trendService.getTrendHistData(information);
        // 存入DB
        trendService.saveOrUpdateBatch(covid19TrendHists);

        log.info("手动刷新结束，时间{},数据来源:{}", DateUtils.getCurrentDateTime(),Crawler.URL2);


        return ResultBeanUtil.success();
    }

    /**
     * 获取所有全球疫情数据
     *
     * @param page  起始页
     * @param limit 页面显示条数
     * @return com.smic.cf.util.ResultBean<com.alibaba.fastjson.JSONObject>
     * @author 蔡明涛
     * @date 2020/3/29 19:51
     */
    @PostMapping("/getForeignStatistics")
    public ResultBean<JSONObject> getForeignStatistics(@RequestParam("page") Integer page,
                                                       @RequestParam("limit") Integer limit,
                                                       @RequestParam("continents") String continents,
                                                       @RequestParam("provinceName") String provinceName,
                                                       @RequestParam("field") String field,
                                                       @RequestParam("order") String order) {
        JSONObject jsonObject = new JSONObject();
        IPage<ForeignCountryCovid19> iPage = foreignCountryService.selectPage(page, limit, continents, provinceName, field, order);
        jsonObject.put("total", iPage.getTotal());
        jsonObject.put("rows", iPage.getRecords());
        return ResultBeanUtil.success(jsonObject);
    }

    /**
     * 获取大洲下拉框的值
     *
     * @return com.smic.cf.util.ResultBean<java.util.List < java.lang.String>>
     * @author 蔡明涛
     * @date 2020/3/29 19:55
     */
    @GetMapping("/getContinents")
    public ResultBean<JSONArray> getContinents() {
        List<String> continents = foreignCountryService.getContinents();
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(continents));
        return ResultBeanUtil.success(jsonArray);
    }

    /**
     * 获取大洲下拉框的值
     *
     * @return com.smic.cf.util.ResultBean<java.util.List < java.lang.String>>
     * @author 蔡明涛
     * @date 2020/3/29 19:55
     */
    @GetMapping("/getCountries")
    public ResultBean<JSONArray> getCountries(@RequestParam("continents") String continents) {
        List<String> counties = foreignCountryService.getCountries(continents);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(counties));
        return ResultBeanUtil.success(jsonArray);
    }

    /**
     * 获取柱状图数据 by各大洲分别统计疫情情况
     *
     * @return com.alibaba.fastjson.JSONObject
     * @author 蔡明涛
     * @date 2020/4/3 2:00
     */
    @GetMapping("/getSummaryBarChartData")
    public JSONObject getSummaryBarChartData() {
        return foreignCountryService.getSummaryBarChartData();
    }

    /**
     * 获取各大洲当前确诊人数占比
     *
     * @return com.alibaba.fastjson.JSONObject
     * @author 蔡明涛
     * @date 2020/4/3 2:05
     */
    @GetMapping("/getSummaryPieChartData")
    public JSONObject getSummaryPieChartData() {
        return foreignCountryService.getSummaryPieChartData();
    }

    /**
     *  获取该地区的疫情历史数据 用于前台桌面展示
     * @param locationId 地理位置
     * @return com.smic.cf.util.ResultBean<java.util.List<com.smic.cf.entitis.pojo.ForeignStatisticsTrendChartData>>
     * @author 蔡明涛
     * @date 2020/4/5 20:52
     */
    @PostMapping("/getForeignStatisticsHist")
    public ResultBean<List<ForeignStatisticsTrendChartData>> getTrendDataForTable(@RequestParam(value = "locationId") Long locationId){
        // 获取历史数据,为了能看到最新的数据 才有倒序排列
        List<ForeignStatisticsTrendChartData> trendChartDataList = foreignCountryService.getTrendChartDataList(locationId);
        return ResultBeanUtil.success(trendChartDataList);
    }

    /**
     * 根据地理位置id查找该区域的历史数据 需要返回一个二维数组
     *
     * @param locationId 地理位置
     * @return Object[][]
     * @author 蔡明涛
     * @date 2020/4/5 19:21
     */
    @GetMapping("/getForeignCountryDataSet")
    public ResultBean<Object[][]> getTrendData(@RequestParam(value = "locationId") Long locationId) {
        // 获取历史数据
        List<ForeignStatisticsTrendChartData> trendChartDataList = foreignCountryService.getTrendChartDataList(locationId);
        // trendChartDataList的长度+1 就是二维数组的长度
        int length = trendChartDataList.size();
        // 初始化二维数组，用于存储数据, 4行 length+1列
        Object[][] data = new Object[4][length+1];
        // 二维数组的第一行的第一个值对应的是折线图的系列名称
        data[0][0] = CrawlerConstants.SERIES_NAME;
        data[1][0] = CrawlerConstants.HIST_CURRENT_CONFIRMED_COUNT_NAME;
        data[2][0] = CrawlerConstants.HIST_CURED_COUNT_NAME;
        data[3][0] = CrawlerConstants.HIST_DEAD_COUNT_NAME;
        // 二维数组的第一行的第二个数开始，对应的是折线图的x轴的坐标值
        for (int i = 0; i<length; i++) {
            // 由于是倒序排列，所以去最后一个值为第一个值，日期只去后四位 日期格式 0123
            data[0][i+1] = trendChartDataList.get(length-i-1).getDateId().substring(4,8);
            data[1][i+1] = trendChartDataList.get(length-i-1).getCurrentConfirmedCount();
            data[2][i+1] = trendChartDataList.get(length-i-1).getCuredCount();
            data[3][i+1] = trendChartDataList.get(length-i-1).getDeadCount();
        }
        return ResultBeanUtil.success(data);
    }

    /**
     * 获取全国当前确诊人数的集合 [{name:北京,value:131},{...}]
     *
     * @param flag 1:当前确诊 2:累计确诊
     * @return com.smic.cf.util.ResultBean<com.alibaba.fastjson.JSONArray>
     * @author 蔡明涛
     * @date 2020/4/7 21:24
     */
    @GetMapping("/getProvinceCovidMapData")
    public ResultBean<JSONArray> getProvinceCovidMapData(@RequestParam("flag")Integer flag){
        JSONArray jsonArray = domesticService.getProvinceCovid19MapData(flag);
        return ResultBeanUtil.success(jsonArray);
    }

    /**
     * 获取世界当前确诊或累计确诊人数的集合 [{name:美国,value:678999},{...}]
     *
     * @param flag 1:当前确诊 2:累计确诊
     * @return com.smic.cf.util.ResultBean<com.alibaba.fastjson.JSONArray>
     * @author 蔡明涛
     * @date 2020/4/21 22:26
     */
    @GetMapping("/getWorldCovidMapData")
    public ResultBean<JSONArray> getWorldCovidMapData(@RequestParam("flag")Integer flag){
        JSONArray jsonArray = foreignCountryService.getWorldCovid19MapData(flag);
        return ResultBeanUtil.success(jsonArray);
    }

    /**
     * 从sys_covid19_trend_hist获取Case人数
     * @param name sys_covid19_trend_hist中的分类名称：ex.中国疫情汇总
     * @param seriesName sys_covid19_trend_hist中的系列名称：ex.新增确诊
     * @return com.smic.cf.util.ResultBean<com.alibaba.fastjson.JSONArray>
     * @author 蔡明涛
     * @date 2020/4/20 22:36
     */
    @GetMapping("/getCaseCount")
    public ResultBean<JSONArray> getCaseCount(@RequestParam("name")String name,@RequestParam("seriesName")String seriesName){
        JSONArray jsonArray = trendService.getCaseCount(name,seriesName);
        return ResultBeanUtil.success(jsonArray);
    }
}
