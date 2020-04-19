package com.smic.cf.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smic.cf.constants.CrawlerConstants;
import com.smic.cf.mapper.CityCovid19Mapper;
import com.smic.cf.mapper.DomesticStatisticTrendChartDataMapper;
import com.smic.cf.mapper.ProvinceCovid19Mapper;
import com.smic.cf.entities.pojo.CityCovid19;
import com.smic.cf.entities.pojo.DomesticStatisticsTrendChartData;
import com.smic.cf.entities.pojo.ProvinceCovid19;
import com.smic.cf.service.DomesticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @Description
 * @ClassName DomesticServiceImpl
 * @Author 蔡明涛
 * @Date 2020/3/28 23:58
 **/
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class DomesticServiceImpl implements DomesticService {
    @Resource
    private ProvinceCovid19Mapper provinceCovid19Mapper;
    @Resource
    private CityCovid19Mapper cityCovid19Mapper;
    @Resource
    private DomesticStatisticTrendChartDataMapper domesticStatisticTrendChartDataMapper;

    /**
     * 保存国内疫情信息
     *
     * @param provinceCovid19List 省市信息的集合
     * @return void
     * @author 蔡明涛
     * @date 2020/3/29 0:05
     */
    @Override
    public void insertDomesticData(List<ProvinceCovid19> provinceCovid19List) {
        if (null != provinceCovid19List && provinceCovid19List.size() > 0) {
            for (ProvinceCovid19 provinceCovid19 : provinceCovid19List) {
                ProvinceCovid19 provinceCovid19Old = provinceCovid19Mapper.selectById(provinceCovid19.getLocationId());
                // 保存或更新省份信息
                if (StringUtils.isEmpty(provinceCovid19Old)) {
                    provinceCovid19Mapper.insert(provinceCovid19);
                } else {
                    if (!provinceCovid19.equals(provinceCovid19Old)) {
                        provinceCovid19Mapper.updateById(provinceCovid19);
                    }
                }
                // 保存省份历史数据
                List<DomesticStatisticsTrendChartData> trendChartDataList = provinceCovid19.getDomesticStatisticsTrendChartDataList();
                if (null != trendChartDataList && trendChartDataList.size() > 0) {
                    LocalDate localDate = LocalDate.now();
                    for (DomesticStatisticsTrendChartData trendChartData : trendChartDataList) {
                        String dateId = trendChartData.getDateId();
                        LocalDate date = LocalDate.of(Integer.parseInt(dateId.substring(0, 4))
                                , Integer.parseInt(dateId.substring(4, 6))
                                , Integer.parseInt(dateId.substring(6, 8)));
                        // 只更新最近前一天的数据 Period.between(date,localDate).getDays()<=1 他抓的是日期对应的天，与我们想要的不符合
                        if (ChronoUnit.DAYS.between(date, localDate) <= 1) {
                            LambdaQueryWrapper<DomesticStatisticsTrendChartData> queryWrapper = Wrappers.lambdaQuery();
                            queryWrapper.eq(DomesticStatisticsTrendChartData::getDateId, trendChartData.getDateId())
                                    .eq(DomesticStatisticsTrendChartData::getLocationId, trendChartData.getLocationId())
                                    .eq(DomesticStatisticsTrendChartData::getProvinceName, trendChartData.getProvinceName());
                            List<DomesticStatisticsTrendChartData> trendChartDataOlds = domesticStatisticTrendChartDataMapper.selectList(queryWrapper);
                            // 只有找不到记录的时候，才会插入数据
                            if (trendChartDataOlds.size() == 0) {
                                domesticStatisticTrendChartDataMapper.insert(trendChartData);
                            } else if (trendChartDataOlds.size() == 1) {
                                domesticStatisticTrendChartDataMapper.update(trendChartData, queryWrapper);
                            } else {
                                domesticStatisticTrendChartDataMapper.delete(queryWrapper);
                                domesticStatisticTrendChartDataMapper.insert(trendChartData);
                            }
                        }
                    }
                }

                // 处理城市数据
                List<CityCovid19> cities = provinceCovid19.getCities();
                if (null != cities && cities.size() > 0) {
                    for (CityCovid19 city : cities) {
                        if (city.getLocationId() != 0 && city.getLocationId() != -1) {
                            if (!StringUtils.isEmpty(cityCovid19Mapper.selectById(city.getLocationId()))) {
                                cityCovid19Mapper.deleteById(city);
                            }
                            cityCovid19Mapper.insert(city);
                        } else {
                            LambdaQueryWrapper<CityCovid19> queryWrapper = Wrappers.lambdaQuery();
                            // 突然出现了-1的代码
                            queryWrapper.nested(qw -> qw.eq(CityCovid19::getLocationId, 0).or().eq(CityCovid19::getLocationId, -1))
                                    .eq(CityCovid19::getCityName, city.getCityName())
                                    .eq(CityCovid19::getProvinceShortName, city.getProvinceShortName())
                                    .eq(CityCovid19::getProvinceId, city.getProvinceId());
                            List<CityCovid19> cityCovid19Olds = cityCovid19Mapper.selectList(queryWrapper);
                            // 如果原来数据库中不存在历史数据，就新增，存在就更新
                            if (cityCovid19Olds.size() == 0) {
                                cityCovid19Mapper.insert(city);
                            } else if (cityCovid19Olds.size() == 1) {
                                cityCovid19Mapper.update(city, queryWrapper);
                            } else {
                                cityCovid19Mapper.delete(queryWrapper);
                                cityCovid19Mapper.insert(city);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取当前境外输入总数
     *
     * @return int
     * @author 蔡明涛
     * @date 2020/4/6 22:35
     */
    @Override
    public int getCurrentCovid19ForeignIn() {
        return cityCovid19Mapper.getCurrentCovid19ForeignIn();
    }

    /**
     * 获取疑似病例总数
     *
     * @return int
     * @author 蔡明涛
     * @date 2020/4/7 20:47
     */
    @Override
    public int getTotalSuspectedCount() {
        return provinceCovid19Mapper.getTotalSuspectedCount();
    }

    /**
     * 获取全国当前确诊人数的集合 [{name:北京,value:131},{...}]
     *
     * @return com.alibaba.fastjson.JSONArray
     * @author 蔡明涛
     * @date 2020/4/7 21:35
     * @param flag
     */
    @Override
    public JSONArray getProvinceCovidMapData(Integer flag) {
        JSONArray jsonArray = new JSONArray();
        List<ProvinceCovid19> provinceCovid19s = provinceCovid19Mapper.selectList(null);
        for (ProvinceCovid19 provinceCovid19 : provinceCovid19s) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CrawlerConstants.ECHARTS_NAME, provinceCovid19.getProvinceShortName());
            if (flag == 1){
                jsonObject.put(CrawlerConstants.ECHARTS_VALUE, provinceCovid19.getCurrentConfirmedCount());
            }else {
                jsonObject.put(CrawlerConstants.ECHARTS_VALUE,provinceCovid19.getConfirmedCount());
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
