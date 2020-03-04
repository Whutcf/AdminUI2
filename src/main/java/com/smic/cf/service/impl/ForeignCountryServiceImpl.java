package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smic.cf.mapper.master.ForeignCountryCovidHistMapper;
import com.smic.cf.mapper.master.ForeignCountryCovidInfoMapper;
import com.smic.cf.pojo.ForeignCountryCovid19Hist;
import com.smic.cf.pojo.ForeignCountryCovid19Info;
import com.smic.cf.service.ForeignCountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @ClassName ForeignCountryServiceImpl
 * @Author 蔡明涛
 * @date 2020.03.02 20:01
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ForeignCountryServiceImpl implements ForeignCountryService {

    @Resource
    private ForeignCountryCovidInfoMapper foreignCountryCovidInfoMapper;
    @Resource
    private ForeignCountryCovidHistMapper foreignCountryCovidHistMapper;

    @Override
    public String insertForeignCountryData(List<ForeignCountryCovid19Info> foreignCountryCovid19InfoList) {
        StringBuilder foreignCountryNews = new StringBuilder();
        ForeignCountryCovid19Hist foreignCountryCovid19Hist = new ForeignCountryCovid19Hist();
        for (ForeignCountryCovid19Info foreignCountryCovid19Info : foreignCountryCovid19InfoList) {
            //需要处理locationId 为0 的情况，他不是唯一值，包含多个对象
            if (foreignCountryCovid19Info.getLocationId()==0){
                //获取locationId为0的所有对象
                LambdaQueryWrapper<ForeignCountryCovid19Info> query = Wrappers.lambdaQuery();
                query.eq(ForeignCountryCovid19Info::getLocationId,0);
                List<ForeignCountryCovid19Info> foreignCountryCovid19Infos = foreignCountryCovidInfoMapper.selectList(query);
                //获取对象的所有简称
                List<String> shortCodeList = new ArrayList<>();
                for(ForeignCountryCovid19Info covid19Info:foreignCountryCovid19Infos){
                    shortCodeList.add(covid19Info.getCountryShortCode());
                }
                //当出现简称一样时
                if (shortCodeList.contains(foreignCountryCovid19Info.getCountryShortCode())){
                    //从DB中找到该笔记录
                    LambdaQueryWrapper<ForeignCountryCovid19Info> lambdaQuery = Wrappers.lambdaQuery();
                    lambdaQuery.eq(ForeignCountryCovid19Info::getCountryShortCode,foreignCountryCovid19Info.getCountryShortCode()).eq(ForeignCountryCovid19Info::getLocationId,0);
                    ForeignCountryCovid19Info oldCovid19Info = foreignCountryCovidInfoMapper.selectOne(lambdaQuery);
                    //比较直接相关属性
                    List<String> ignores = new ArrayList<>();
                    ignores.add("locationId");
                    ignores.add("createTime");
                    ignores.add("modifyTime");
                    ignores.add("tags");
                    ignores.add("countryType");
                    ignores.add("provinceId");
                    ignores.add("comment");
                    ignores.add("sort");
                    ignores.add("countryShortCode");
                    if (!com.smic.cf.util.BeanUtils.compareTwoObjs(ignores,foreignCountryCovid19Info,oldCovid19Info)){
                        //数据不同，更新记录，并保存历史
                        foreignCountryCovidInfoMapper.delete(lambdaQuery);
                        insertInfoAndHist(foreignCountryCovid19Hist, foreignCountryCovid19Info, foreignCountryNews);
                    }
                }else {
                    //简称不一样，直接保存进DB，顺便保存历史信息
                    insertInfoAndHist(foreignCountryCovid19Hist, foreignCountryCovid19Info, foreignCountryNews);
                }
            }else { // 处理常规情况，LocationId唯一时
                ForeignCountryCovid19Info oldForeignCountryCovid19Info = foreignCountryCovidInfoMapper.selectById(foreignCountryCovid19Info.getLocationId());
                if (StringUtils.isEmpty(oldForeignCountryCovid19Info)){
                    //新增数据直接保存,新城市疫情出现
                    insertInfoAndHist(foreignCountryCovid19Hist, foreignCountryCovid19Info, foreignCountryNews);
                }else {
                    if (!oldForeignCountryCovid19Info.equals(foreignCountryCovid19Info)){
                        updateInfoAndInsertHist(foreignCountryNews, foreignCountryCovid19Hist, foreignCountryCovid19Info);
                    }
                }
            }
        }
        return foreignCountryNews.toString();
    }

    /**
     * 更新实时数据，记录历史数据
     * @param foreignCountryNews, foreignCountryCovid19Hist, foreignCountryCovid19Info
     * @return void
     * @author 蔡明涛
     * @date 2020.03.04 22:44
     **/
    private void updateInfoAndInsertHist(StringBuilder foreignCountryNews, ForeignCountryCovid19Hist foreignCountryCovid19Hist, ForeignCountryCovid19Info foreignCountryCovid19Info) {
        foreignCountryCovidInfoMapper.updateById(foreignCountryCovid19Info);
        //复制信息到历史表
        setHistData( foreignCountryCovid19Info, foreignCountryCovid19Hist);
        foreignCountryCovidHistMapper.insert(foreignCountryCovid19Hist);
        foreignCountryNews.append(foreignCountryCovid19Info.getProvinceName()).append("累计确诊：").append(foreignCountryCovid19Info.getConfirmedCount()).append("例，死亡：")
                .append(foreignCountryCovid19Info.getDeadCount()).append("例，治愈：").append(foreignCountryCovid19Info.getCuredCount()).append("例！<br>");
    }

    /**
     * 保存实时信息和记录历史数据
     * @param foreignCountryCovid19Hist, foreignCountryCovid19Info
     * @return void
     * @author 蔡明涛
     * @date 2020.03.04 22:35
     **/
    private void insertInfoAndHist(ForeignCountryCovid19Hist foreignCountryCovid19Hist, ForeignCountryCovid19Info foreignCountryCovid19Info, StringBuilder foreignCountryNews) {
        foreignCountryCovidInfoMapper.insert(foreignCountryCovid19Info);
        //复制信息到历史表
        setHistData( foreignCountryCovid19Info, foreignCountryCovid19Hist);
        foreignCountryCovidHistMapper.insert(foreignCountryCovid19Hist);
        foreignCountryNews.append(foreignCountryCovid19Info.getProvinceName()).append("累计确诊：").append(foreignCountryCovid19Info.getConfirmedCount()).append("例，死亡：")
                .append(foreignCountryCovid19Info.getDeadCount()).append("例，治愈：").append(foreignCountryCovid19Info.getCuredCount()).append("例！<br>");
    }

    /**
     * 将INFO的属性赋值到HIST中
     * @param foreignCountryCovid19Hist, foreignCountryCovid19Info
     * @return void
     * @author 蔡明涛
     * @date 2020.03.04 00:25
     **/
    private void setHistData(ForeignCountryCovid19Info foreignCountryCovid19Info, ForeignCountryCovid19Hist foreignCountryCovid19Hist) {
        BeanUtils.copyProperties(foreignCountryCovid19Info,foreignCountryCovid19Hist);
        foreignCountryCovid19Hist.setCountryName(foreignCountryCovid19Info.getProvinceName());
    }
}
