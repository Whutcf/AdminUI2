package com.smic.cf.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smic.cf.pojo.ForeignCountryCovid19Info;
import com.smic.cf.pojo.ProvinceCovid19Info;
import com.smic.cf.pojo.Statistics;
import com.smic.cf.pojo.TimeLine;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 将json格式的string装换成对象
 * @ClassName CrawlerPaser
 * @Author 蔡明涛
 * @date 2020.03.01 12:56
 */
public class CrawlerPaser {
    /**
     * @Description 解析Statistics的Json数据
     * @Param [staticInformation]
     * @return com.smic.cf.domain.Statistics
     * @Author 蔡明涛
     * @Date 2020.03.01 13:23
     **/
    public static Statistics parseStatisticsInformation(String staticInformation){
        JSONObject jsonObject = JSON.parseObject(staticInformation);
        Statistics statistics = JSON.toJavaObject(jsonObject, Statistics.class);
        statistics.setCreateTime(DateUtils.getDate(Long.parseLong(statistics.getCreateTime())));
        statistics.setModifyTime(DateUtils.getDate(Long.parseLong(statistics.getModifyTime())));
        return statistics;
    }

    /**
     * 获取时间线信息
     * @param timeLineInformation 时间线集合的JSON字符串
     * @return java.util.List<com.smic.cf.domain.TimeLine>
     * @author 蔡明涛
     * @date 2020.03.01 19:36
     **/
    public static List<TimeLine> parseTimelineInformation(String timeLineInformation) {
        List<TimeLine> timeLines = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(timeLineInformation);
        for (Object jsonObject: jsonArray) {
            TimeLine timeLine = JSON.toJavaObject((JSON) jsonObject,TimeLine.class);
            timeLine.setPubDate(DateUtils.getDate(Long.parseLong(timeLine.getPubDate())));
            timeLine.setCreateTime(DateUtils.getDate(Long.parseLong(timeLine.getCreateTime())));
            timeLine.setModifyTime(DateUtils.getDate(Long.parseLong(timeLine.getModifyTime())));
            timeLine.setDataInfoTime(DateUtils.getDate(Long.parseLong(timeLine.getDataInfoTime())));
            timeLines.add(timeLine);
        }
        return timeLines;
    }

    public static List<ProvinceCovid19Info> parseAreaInformation(String areaInformation) {
        List<ProvinceCovid19Info> provinceCovid19Infos = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(areaInformation);
        for(Object obj : jsonArray){
            ProvinceCovid19Info provinceCovid19Info = JSON.toJavaObject((JSON) obj, ProvinceCovid19Info.class);
            if(!StringUtils.isEmpty(provinceCovid19Info.getCreateTime())){
                provinceCovid19Info.setCreateTime(DateUtils.getDate(Long.parseLong(provinceCovid19Info.getCreateTime())));
                provinceCovid19Info.setModifyTime(DateUtils.getDate(Long.parseLong(provinceCovid19Info.getModifyTime())));
            }
            provinceCovid19Infos.add(provinceCovid19Info);
        }
        return provinceCovid19Infos;
    }

    public static List<ForeignCountryCovid19Info> parseForeignCountryInformation(String foreignCountryInformation) {
        List<ForeignCountryCovid19Info> countryList = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(foreignCountryInformation);
        for(Object obj : jsonArray){
            ForeignCountryCovid19Info country = JSON.toJavaObject((JSON) obj, ForeignCountryCovid19Info.class);
            country.setCreateTime(DateUtils.getDate(Long.parseLong(country.getCreateTime())));
            country.setModifyTime(DateUtils.getDate(Long.parseLong(country.getModifyTime())));
            countryList.add(country);
        }
        return countryList;
    }
}
