package com.smic.cf.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smic.cf.crawlerbaidu.service.serviceimpl.TrendServiceImpl;
import com.smic.cf.entities.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 将json格式的string装换成对象
 * @ClassName CrawlerParser
 * @Author 蔡明涛
 * @date 2020.03.01 12:56
 */
@Slf4j
public class CrawlerParser {

    @Resource
    private TrendServiceImpl trendService;

    /**
     * 解析国外基本数据
     *
     * @param foreignCountryInformation json字符串
     * @return java.util.List<com.smic.cf.entitis.pojo.ForeignCountryCovid19Info>
     * @author 蔡明涛
     * @date 2020/3/28 18:14
     */
    public static List<ForeignCountryCovid19> parseForeignCountryInformation(String foreignCountryInformation) {
        List<ForeignCountryCovid19> countryList = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(foreignCountryInformation);
        for (Object obj : jsonArray) {
            ForeignCountryCovid19 country = JSON.toJavaObject((JSON) obj, ForeignCountryCovid19.class);
            try {
                // 不考虑中国的情况
                if (!StringUtils.isEmpty(country.getCreateTime())) {
                    country.setCreateTime(DateUtils.getDate(Long.parseLong(country.getCreateTime())));
                    country.setModifyTime(DateUtils.getDate(Long.parseLong(country.getModifyTime())));
                }
                // 获取每日新增数据，需要判断是否有值
                IncrVo incrVo = country.getIncrVo();
                if(!StringUtils.isEmpty(incrVo)){
                    incrVo.setId(country.getLocationId());
                    incrVo.setCountryShortCode(country.getCountryShortCode());
                    country.setIncrVo(incrVo);
                }
                // 获取服务器上的json数据并转换成字符串，主要是该地区的历史数据,前提是有值存在
                if (!StringUtils.isEmpty(country.getStatisticsData())){
                    String stringData = CrawlerUtils.getJsonStringData(country.getStatisticsData());
                    String statisticDataStr = CrawlerUtils.getJsonInformation(Crawler.JSON_REGEX_TEMPLATE, stringData);
                    JSONArray parseArray = JSON.parseArray(statisticDataStr);
                    List<ForeignStatisticsTrendChartData> list = new ArrayList<>();
                    if (null != parseArray) {
                        for (Object o : parseArray) {
                            JSONObject jsonObject = JSON.parseObject(o.toString());
                            ForeignStatisticsTrendChartData statisticsTrendChartData = JSON.toJavaObject(jsonObject, ForeignStatisticsTrendChartData.class);
                            statisticsTrendChartData.setLocationId(country.getLocationId());
                            statisticsTrendChartData.setCountryShortCode(country.getCountryShortCode());
                            list.add(statisticsTrendChartData);
                        }
                    }
                    country.setStatisticsTrendChartDataList(list);
                }
                countryList.add(country);

            } catch (Exception e) {
                log.error("异常 {}",e.getMessage());
                e.printStackTrace();
            }
        }
        return countryList;
    }

    /**
     * 国内各省数据集合
     * @param domesticInformation json字符串
     * @return java.util.List<com.smic.cf.entitis.pojo.ProvinceCovid19>
     * @author 蔡明涛
     * @date 2020/3/28 20:01
     */
    public static List<ProvinceCovid19> parseDomesticInformation(String domesticInformation) {
        List<ProvinceCovid19> provinceCovid19List = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(domesticInformation);
        if (null != jsonArray) {
            for (Object o : jsonArray) {
                try {
                    // 解析省份信息
                    ProvinceCovid19 provinceCovid19 = JSON.toJavaObject((JSON) JSON.parse(o.toString()), ProvinceCovid19.class);
                    // 处理省份的历史数据
                    String jsonStringData = CrawlerUtils.getJsonStringData(provinceCovid19.getStatisticsData());
                    String domesticTrendChartDataStr = CrawlerUtils.getJsonInformation(Crawler.JSON_REGEX_TEMPLATE, jsonStringData);
                    JSONArray parseArray = JSON.parseArray(domesticTrendChartDataStr);
                    List<DomesticStatisticsTrendChartData> trendChartDataList = new ArrayList<>();
                    if (!StringUtils.isEmpty(parseArray)) {
                        for (Object o1 : parseArray) {
                            // 获取每个历史数据并添加到集合中
                            DomesticStatisticsTrendChartData trendChartData = JSON.toJavaObject((JSON) JSON.parse(o1.toString()),DomesticStatisticsTrendChartData.class);
                            trendChartData.setLocationId(provinceCovid19.getLocationId());
                            trendChartData.setProvinceName(provinceCovid19.getProvinceName());
                            trendChartDataList.add(trendChartData);
                        }
                    }
                    provinceCovid19.setDomesticStatisticsTrendChartDataList(trendChartDataList);
                    // 处理省份内部的城市信息
                    List<CityCovid19> cities = provinceCovid19.getCities();
                    List<CityCovid19> cityCovid19List = new ArrayList<>();
                    if (!StringUtils.isEmpty(cities)) {
                        for (CityCovid19 city : cities) {
                            city.setProvinceId(provinceCovid19.getLocationId());
                            city.setProvinceShortName(provinceCovid19.getProvinceShortName());
                            cityCovid19List.add(city);
                        }
                    }
                    provinceCovid19.setCities(cityCovid19List);
                    // 将省份信息添加到集合中
                    provinceCovid19List.add(provinceCovid19);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return provinceCovid19List;
    }

    /**
     * 获取国内时间线信息
     * @param timeLineInformation 时间线解析内容
     * @return java.util.List<com.smic.cf.entitis.pojo.DomesticTimeLine>
     * @author 蔡明涛
     * @date 2020/3/29 10:28
     */
    public static List<TimeLine> parseDomesticTimelineInformation(String timeLineInformation) {
        List<TimeLine> timeLines = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(timeLineInformation);
        for (Object jsonObject : jsonArray) {
            TimeLine timeLine = JSON.toJavaObject((JSON) jsonObject, TimeLine.class);
            timeLine.setPubDate(DateUtils.getDate(Long.parseLong(timeLine.getPubDate())));
            timeLines.add(timeLine);
        }
        return timeLines;
    }

}
