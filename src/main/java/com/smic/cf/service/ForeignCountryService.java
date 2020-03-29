package com.smic.cf.service;

import com.smic.cf.pojo.ForeignCountryCovid19;

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
}
