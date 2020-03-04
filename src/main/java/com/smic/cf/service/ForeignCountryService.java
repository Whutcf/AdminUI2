package com.smic.cf.service;

import com.smic.cf.pojo.ForeignCountryCovid19Info;

import java.util.List;

/**
 * @Description TODO
 * @ClassName ForeignCountryService
 * @Author 蔡明涛
 * @date 2020.03.01 23:22
 */
public interface ForeignCountryService {
    /**
     * 处理采集的国外数据
     * @param foreignCountryCovid19InfoList 国外实体数据集合
     * @return java.lang.String
     * @author 蔡明涛
     * @date 2020.03.02 19:50
     **/
    String insertForeignCountryData(List<ForeignCountryCovid19Info> foreignCountryCovid19InfoList);
}
