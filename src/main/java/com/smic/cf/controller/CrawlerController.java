package com.smic.cf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smic.cf.pojo.Crawler;
import com.smic.cf.pojo.ForeignCountryCovid19;
import com.smic.cf.pojo.ProvinceCovid19;
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

    @GetMapping("/refresh")
    public ResultBean<String> refresh(){
        log.info("{}开启手动刷新, 数据源: {}", DateUtils.getCurrentDateTime(),Crawler.URL1);
        String foreignCountryInformation = CrawlerUtils.getJsonString(Crawler.URL1, Crawler.FOREIGN_STATIC_INFORMATION_REGEX_TEMPLATE, Crawler.FOREIGN_STATIC_INFORMATION_ATTRIBUTE);
        String domesticInformation = CrawlerUtils.getJsonString(Crawler.URL1,Crawler.DOMESTIC_STATIC_INFORMATION_REGEX_TEMPLATE, Crawler.DOMESTIC_STATIC_INFORMATION_ATTRIBUTE);
        //解析json数据
        List<ForeignCountryCovid19> foreignCountryCovid19List = CrawlerParser.parseForeignCountryInformation(foreignCountryInformation);
        List<ProvinceCovid19> provinceCovid19List = CrawlerParser.parseDomesticInformation(domesticInformation);
        //将解析的数据存入DB
        foreignCountryService.insertForeignCountryData(foreignCountryCovid19List);
        domesticService.insertDomesticData(provinceCovid19List);
        log.info("{} 手动刷新结束, 数据源: {}", DateUtils.getCurrentDateTime(),Crawler.URL1);
        return ResultBeanUtil.success();
    }

    /**
     * 获取所有全球疫情数据
     * @param page 起始页
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
                                                       @RequestParam("order") String order){
        JSONObject jsonObject = new JSONObject();
        IPage<ForeignCountryCovid19> iPage =  foreignCountryService.selectPage(page,limit,continents,provinceName,field,order);
        jsonObject.put("total",iPage.getTotal());
        jsonObject.put("rows",iPage.getRecords());
        return ResultBeanUtil.success(jsonObject);
    }

    /**
     * 获取大洲下拉框的值
     * @return com.smic.cf.util.ResultBean<java.util.List<java.lang.String>>
     * @author 蔡明涛
     * @date 2020/3/29 19:55
     */
    @GetMapping("/getContinents")
    public ResultBean<JSONArray> getContinents(){
        List<String> continents = foreignCountryService.getContinents();
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(continents));
        return ResultBeanUtil.success(jsonArray);
    }

    /**
     * 获取大洲下拉框的值
     * @return com.smic.cf.util.ResultBean<java.util.List<java.lang.String>>
     * @author 蔡明涛
     * @date 2020/3/29 19:55
     */
    @GetMapping("/getCountries")
    public ResultBean<JSONArray> getCountries(@RequestParam("continents") String continents){
        List<String> counties = foreignCountryService.getCountries(continents);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(counties));
        return ResultBeanUtil.success(jsonArray);
    }

    /**
     * 获取柱状图数据 by各大洲分别统计疫情情况
     * @return com.alibaba.fastjson.JSONObject
     * @author 蔡明涛
     * @date 2020/4/3 2:00
     */
    @GetMapping("/getSummaryBarChartData")
    public JSONObject getSummaryBarChartData(){
        return foreignCountryService.getSummaryBarChartData();
    }

    /**
     * 获取各大洲当前确诊人数占比
     * @return com.alibaba.fastjson.JSONObject
     * @author 蔡明涛
     * @date 2020/4/3 2:05
     */
    @GetMapping("/getSummaryPieChartData")
    public JSONObject getSummaryPieChartData(){
        return foreignCountryService.getSummaryPieChartData();
    }
}
