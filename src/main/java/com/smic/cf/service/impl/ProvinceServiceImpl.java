package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smic.cf.mapper.CityCovidHistMapper;
import com.smic.cf.mapper.CityCovidInfoMapper;
import com.smic.cf.mapper.ProvinceHistMapper;
import com.smic.cf.mapper.ProvinceMapper;
import com.smic.cf.pojo.CityCovid19Hist;
import com.smic.cf.pojo.CityCovid19Info;
import com.smic.cf.pojo.ProvinceCovid19Hist;
import com.smic.cf.pojo.ProvinceCovid19Info;
import com.smic.cf.service.ProvinceService;
import com.smic.cf.util.BeanUtils;
import com.smic.cf.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @ClassName ProvinceServiceImpl
 * @Author 蔡明涛
 * @date 2020.03.02 21:11
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ProvinceServiceImpl implements ProvinceService {

    @Resource
    private ProvinceMapper provinceMapper;
    @Resource
    private ProvinceHistMapper provinceHistMapper;
    @Resource
    private CityCovidInfoMapper cityCovidInfoMapper;
    @Resource
    private CityCovidHistMapper cityCovidHistMapper;

    @Override
    public String insertProvinceAndCitys(List<ProvinceCovid19Info> areaList, List<ProvinceCovid19Info> provinceCovid19InfoList) {
        StringBuilder chinaAreaNews = new StringBuilder();
        //添加城市信息
        saveOrUpdateCity(areaList);
        Integer cityCount = cityCovidInfoMapper.selectCount(null);
        //添加省份信息
        saveOrUpdateProvince(provinceCovid19InfoList);
        Integer provinceCount = provinceMapper.selectCount(null);
        chinaAreaNews.append("当前全国有").append(provinceCount).append("省").append(cityCount).append("市疫情出现变化！");
        return chinaAreaNews.toString();
    }

    /**
     * 添加城市信息
     * @param areaList 区域信息的集合，包含城市信息和省份部分信息
     * @return void
     * @author 蔡明涛
     * @date 2020.03.03 00:56
     **/
    private void saveOrUpdateCity(List<ProvinceCovid19Info> areaList) {
        for(ProvinceCovid19Info provinceCovid19Info : areaList){
            String provinceName = provinceCovid19Info.getProvinceName();
            String provinceShortName = provinceCovid19Info.getProvinceShortName();
            List<CityCovid19Info> cities = provinceCovid19Info.getCities();
            CityCovid19Hist cityCovid19Hist = new CityCovid19Hist();
            for(CityCovid19Info cityCovid19Info : cities){
                //爬虫过来的数据cities是不带省的信息，后续都会用到，直接在此处更新
                cityCovid19Info.setProvinceName(provinceName);
                cityCovid19Info.setProvinceShortName(provinceShortName);
                if (cityCovid19Info.getLocationId() != 0 && cityCovid19Info.getLocationId() !=1){
                    CityCovid19Info oldCityCovid19Info = cityCovidInfoMapper.selectById(cityCovid19Info.getLocationId());
                    if (!StringUtils.isEmpty(oldCityCovid19Info)){
                        List<String> ignoreFields = new ArrayList<>();
                        ignoreFields.add("updateTime");
                        ignoreFields.add("provinceName");
                        ignoreFields.add("provinceShortName");
                        if (!BeanUtils.compareTwoObjs(ignoreFields, oldCityCovid19Info, cityCovid19Info)){
                            //当城市信息发生变更时
                            cityCovid19Info.setUpdateTime(DateUtils.getCurrentDateTime());
                             cityCovidInfoMapper.updateById(cityCovid19Info);
                            org.springframework.beans.BeanUtils.copyProperties(cityCovid19Info, cityCovid19Hist);
                            cityCovidHistMapper.insert(cityCovid19Hist);

                        }
                    }else {
                        //添加新城市信息
                        cityCovid19Info.setUpdateTime(DateUtils.getCurrentDateTime());
                        cityCovidInfoMapper.insert(cityCovid19Info);
                        org.springframework.beans.BeanUtils.copyProperties(cityCovid19Info, cityCovid19Hist);
                        cityCovidHistMapper.insert(cityCovid19Hist);
                    }
                }else {
                    for0or1Case(cityCovid19Info,cityCovid19Hist);
                }
            }
        }
    }

    /**
     * 当LocationId为0或-1时，会出现多个城市，此时共用一个id，只有cityName可以作为唯一键了
     * 当cityName为待明确地区时，还需要添加省市信息才能比较，为了减少不必要的麻烦，直接在传参的时候将对应的省市信息填充
     * 特别注意的是我只有一笔数据需要处理
     * @param cityCovid19Info 城市实体
     * @param cityCovid19Hist 历史实体
     * @return void
     * @author 蔡明涛
     * @date 2020.03.03 20:08
     **/
    private void for0or1Case(CityCovid19Info cityCovid19Info, CityCovid19Hist cityCovid19Hist) {
        LambdaQueryWrapper<CityCovid19Info> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CityCovid19Info::getLocationId, cityCovid19Info.getLocationId());
        List<CityCovid19Info> oldCities = cityCovidInfoMapper.selectList(queryWrapper);
        //得到传入对象的城市名
        String newCityName = cityCovid19Info.getCityName();
        List<String> oldCityNames = new ArrayList<>();
        //遍历得到当前id为0和-1的所有城市名
        for (CityCovid19Info oldCityCovid19Info : oldCities) {
            oldCityNames.add(oldCityCovid19Info.getCityName());
        }
        //当结果集存在新名称时，比较同名数据是否需要更新
        if(oldCityNames.contains(newCityName)){
            //获取同名对象，考虑待明确地区同名较多，加上省的条件可覆盖这种情况
            LambdaQueryWrapper<CityCovid19Info> lambdaQuery = Wrappers.lambdaQuery();
            lambdaQuery.eq(CityCovid19Info::getCityName, newCityName);
            lambdaQuery.eq(CityCovid19Info::getProvinceName,cityCovid19Info.getProvinceName());
            lambdaQuery.eq(CityCovid19Info::getProvinceShortName,cityCovid19Info.getProvinceShortName());
            CityCovid19Info oldCityCovid19Info = cityCovidInfoMapper.selectOne(lambdaQuery);
            if (StringUtils.isEmpty(oldCityCovid19Info)){
                //同名对象不存在，直接添加入DB
                cityCovid19Info.setUpdateTime(DateUtils.getCurrentDateTime());
                cityCovidInfoMapper.insert(cityCovid19Info);
                org.springframework.beans.BeanUtils.copyProperties(cityCovid19Info, cityCovid19Hist);
                cityCovidHistMapper.insert(cityCovid19Hist);
            }else {
                List<String> ignoreFields = new ArrayList<>();
                ignoreFields.add("locationId");
                ignoreFields.add("updateTime");
                //比较同名对象，不相同更新
                if (!BeanUtils.compareTwoObjs(ignoreFields, oldCityCovid19Info, cityCovid19Info)){
                    //更新城市信息,此时没有id作为更新条件，先删除再插入较为方便
                    cityCovidInfoMapper.delete(lambdaQuery);
                    cityCovid19Info.setUpdateTime(DateUtils.getCurrentDateTime());
                    cityCovidInfoMapper.insert(cityCovid19Info);
                    org.springframework.beans.BeanUtils.copyProperties(cityCovid19Info, cityCovid19Hist);
                    cityCovidHistMapper.insert(cityCovid19Hist);
                }
            }
        }else {
            //不存在同名，直接新增数据
            cityCovid19Info.setUpdateTime(DateUtils.getCurrentDateTime());
            cityCovidInfoMapper.insert(cityCovid19Info);
            org.springframework.beans.BeanUtils.copyProperties(cityCovid19Info, cityCovid19Hist);
            cityCovidHistMapper.insert(cityCovid19Hist);
        }
    }


    /**
     * 添加省份信息
     * @param provinceCovid19InfoList 省份实体的集合
     * @return void
     * @author 蔡明涛
     * @date 2020.03.03 00:55
     **/
    private void saveOrUpdateProvince(List<ProvinceCovid19Info> provinceCovid19InfoList) {
        ProvinceCovid19Hist provinceCovid19Hist = new ProvinceCovid19Hist();
        for(ProvinceCovid19Info provinceCovid19Info : provinceCovid19InfoList){
            ProvinceCovid19Info oldProvinceCovid19Info = provinceMapper.selectById(provinceCovid19Info.getLocationId());
            if(StringUtils.isEmpty(oldProvinceCovid19Info)){
                //只有第一次爬取的省份才会用到
                provinceMapper.insert(provinceCovid19Info);
            }else {
                List<String> ignores = new ArrayList<>();
                ignores.add("modifyTime");
                ignores.add("createTime");
                //特别注意，一定要过滤掉cities 属性，页面传来有但是DB里没有，会报null异常
                ignores.add("cities");
                if(!BeanUtils.compareTwoObjs(ignores,oldProvinceCovid19Info,provinceCovid19Info)){
                    provinceMapper.updateById(provinceCovid19Info);
                    //复制实时数据属性到历史实体
                    org.springframework.beans.BeanUtils.copyProperties(provinceCovid19Info,provinceCovid19Hist);
                    provinceHistMapper.insert(provinceCovid19Hist);
                }
            }
        }
    }
}
