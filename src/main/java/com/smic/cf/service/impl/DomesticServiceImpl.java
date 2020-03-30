package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smic.cf.mapper.CityCovid19Mapper;
import com.smic.cf.mapper.DomesticStatisticTrendChartDataMapper;
import com.smic.cf.mapper.ProvinceCovid19Mapper;
import com.smic.cf.pojo.CityCovid19;
import com.smic.cf.pojo.DomesticStatisticsTrendChartData;
import com.smic.cf.pojo.ProvinceCovid19;
import com.smic.cf.service.DomesticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
        if (null != provinceCovid19List && provinceCovid19List.size()>0){
            for (ProvinceCovid19 provinceCovid19 : provinceCovid19List) {
                ProvinceCovid19 provinceCovid19Old = provinceCovid19Mapper.selectById(provinceCovid19.getLocationId());
                // 保存或更新省份信息
                if (StringUtils.isEmpty(provinceCovid19Old)){
                    provinceCovid19Mapper.insert(provinceCovid19);
                }else {
                    if(!provinceCovid19.equals(provinceCovid19Old)){
                        provinceCovid19Mapper.updateById(provinceCovid19);
                    }
                }
                // 保存省份历史数据
                List<DomesticStatisticsTrendChartData> trendChartDataList = provinceCovid19.getDomesticStatisticsTrendChartDataList();
                if (null != trendChartDataList && trendChartDataList.size()>0){
                    for (DomesticStatisticsTrendChartData trendChartData : trendChartDataList) {
                        LambdaQueryWrapper<DomesticStatisticsTrendChartData> queryWrapper = Wrappers.lambdaQuery();
                        queryWrapper.eq(DomesticStatisticsTrendChartData::getDateId,trendChartData.getDateId())
                                .eq(DomesticStatisticsTrendChartData::getLocationId,trendChartData.getLocationId())
                                .eq(DomesticStatisticsTrendChartData::getProvinceName,trendChartData.getProvinceName());
                        DomesticStatisticsTrendChartData trendChartDataOld = domesticStatisticTrendChartDataMapper.selectOne(queryWrapper);
                        // 只有找不到记录的时候，才会插入数据
                        if (StringUtils.isEmpty(trendChartDataOld)) {
                            domesticStatisticTrendChartDataMapper.insert(trendChartData);
                        } else {
                            if (!trendChartData.equals(trendChartDataOld)){
                                domesticStatisticTrendChartDataMapper.update(trendChartData,queryWrapper);
                            }
                        }
                    }
                }

                // 处理城市数据
                List<CityCovid19> cities = provinceCovid19.getCities();
                if (null != cities && cities.size()>0){
                    for (CityCovid19 city : cities) {
                        if (city.getLocationId() != 0){
                            if(!StringUtils.isEmpty(cityCovid19Mapper.selectById(city.getLocationId()))){
                                cityCovid19Mapper.deleteById(city);
                            }
                            cityCovid19Mapper.insert(city);
                        }else {
                            LambdaQueryWrapper<CityCovid19> queryWrapper = Wrappers.lambdaQuery();
                            queryWrapper.eq(CityCovid19::getLocationId,0)
                                    .eq(CityCovid19::getCityName,city.getCityName())
                                    .eq(CityCovid19::getProvinceShortName,city.getProvinceShortName())
                                    .eq(CityCovid19::getProvinceId,city.getProvinceId());
                            CityCovid19 cityCovid19Old = cityCovid19Mapper.selectOne(queryWrapper);
                            // 如果原来数据库中不存在历史数据，就新增，存在就更新
                            if (StringUtils.isEmpty(cityCovid19Old)){
                                cityCovid19Mapper.insert(city);
                            }else {
                                if(!city.equals(cityCovid19Old)){
                                    cityCovid19Mapper.update(city,queryWrapper);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
