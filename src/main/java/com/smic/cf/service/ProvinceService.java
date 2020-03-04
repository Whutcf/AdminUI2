package com.smic.cf.service;

import com.smic.cf.pojo.ProvinceCovid19Info;

import java.util.List;

/**
 * @Description TODO
 * @ClassName ProvinceService
 * @Author 蔡明涛
 * @date 2020.03.01 23:21
 */
public interface ProvinceService {
    /**
     * 收集各省市的基本信息
     * @param areaList 城市和省份的合集, provinceList 省份单独的集合
     * @return java.lang.String
     * @author 蔡明涛
     * @date 2020.03.02 21:09
     **/
    String insertProvinceAndCitys(List<ProvinceCovid19Info> areaList, List<ProvinceCovid19Info> provinceCovid19InfoList);
}
