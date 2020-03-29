package com.smic.cf.service;

import com.smic.cf.pojo.ProvinceCovid19;

import java.util.List;

/**
 * @Description
 * @ClassName DomesticService
 * @Author 蔡明涛
 * @Date 2020/3/28 23:57
 **/
public interface DomesticService {
    /**
     * 保存国内疫情信息
     * @param provinceCovid19List 省市信息的集合
     * @return void
     * @author 蔡明涛
     * @date 2020/3/29 0:05
     */
    void insertDomesticData(List<ProvinceCovid19> provinceCovid19List);
}