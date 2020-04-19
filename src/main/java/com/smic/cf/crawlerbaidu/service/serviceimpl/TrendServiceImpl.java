package com.smic.cf.crawlerbaidu.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smic.cf.crawlerbaidu.dto.*;
import com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist;
import com.smic.cf.crawlerbaidu.service.TrendService;
import com.smic.cf.mapper.TrendMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName TrendServiceImpl
 * @Author 蔡明涛
 * @Date 2020/4/4 16:23
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TrendServiceImpl extends ServiceImpl<TrendMapper, Covid19TrendHist> implements TrendService {

    @Resource
    private TrendMapper trendMapper;

    /**
     *  获取各地区的trend的历史数据
     * @param information 页面解析的json字符串
     * @return java.util.List<com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist>
     * @author 蔡明涛
     * @date 2020/4/4 19:36
     */
    @Override
    public List<Covid19TrendHist> getTrendHistData(String information) {
        // 新建一个对象用于存储各地区trend的所有历史数据
        List<Covid19TrendHist> covid19TrendHists = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(information);
        log.info(jsonArray.toJSONString());
        for (Object o : jsonArray) {
            CrawlerBaidu componentBean = JSON.toJavaObject((JSON) o, CrawlerBaidu.class);
            List<ComponentBean> components = componentBean.getComponent();
            // 从数据结果来看只有一个对象，所以这里就直接去第一个值
            ComponentBean component = components.get(0);
            // 添加疫情趋势数据
            TrendBean foreignTotalTrend = component.getAllForeignTrend();
            addTrendHist(covid19TrendHists, "国外疫情汇总", foreignTotalTrend);
            TrendBean chinaTotalTrend = component.getTrend();
            addTrendHist(covid19TrendHists, "中国疫情汇总", chinaTotalTrend);
            List<TrendListBean> provinceTrendList = component.getProvinceTrendList();
            addTrendList(covid19TrendHists, provinceTrendList);
            List<TrendListBean> foreignTrendList = component.getForeignTrendList();
            addTrendList(covid19TrendHists, foreignTrendList);

        }
        return covid19TrendHists;
    }
    /**
     * 将单个区域的疫情趋势信息添加到 covid19TrendHists 中去
     *
     * @param covid19TrendHists 区域疫情历史信息的存储集合
     * @param name              区域名称
     * @param trend             单个区域疫情历史数据
     * @author 蔡明涛
     * @date 2020/4/4 18:12
     */
    public void addTrendHist(List<Covid19TrendHist> covid19TrendHists, String name, TrendBean trend) {
        // 获取对应日期集合
        List<String> updateDate = trend.getUpdateDate();
        // 获取系列名称和对应数据的集合
        List<ListBean> listBeans = trend.getList();
        for (ListBean listBean : listBeans) {
            String seriesName = listBean.getName();
            List<Integer> data = listBean.getData();
            int j = data.size();
            // 判断数据库是否有数据，有则只更新前一天的数据，无则添加全部数据
            if (trendMapper.selectCount(null)>0){
                j = j-1;
            }else {
                j = 0;
            }
            for (int i = j; i < data.size(); i++) {
                Covid19TrendHist covid19TrendHist = new Covid19TrendHist();
                covid19TrendHist.setSeriesName(seriesName);
                covid19TrendHist.setValue(data.get(i));
                covid19TrendHist.setName(name);
                // 日期和data是一一对应的，所以如此操作
                covid19TrendHist.setDate(updateDate.get(i));
                covid19TrendHists.add(covid19TrendHist);
                if (j>0){
                    LambdaQueryWrapper<Covid19TrendHist> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(Covid19TrendHist::getDate,updateDate.get(i))
                            .eq(Covid19TrendHist::getName,name)
                            .eq(Covid19TrendHist::getSeriesName,seriesName)
                            .eq(Covid19TrendHist::getValue,data.get(i));
                    trendMapper.delete(queryWrapper);
                }
            }
        }
    }

    /**
     * 将区域集合的疫情趋势信息添加到 covid19TrendHists 中去
     * @param covid19TrendHists 区域疫情历史信息的存储集合
     * @param trendList 区域疫情趋势结合
     * @return void
     * @author 蔡明涛
     * @date 2020/4/4 18:21
     */
    public void addTrendList(List<Covid19TrendHist> covid19TrendHists, List<TrendListBean> trendList) {
        for (TrendListBean trendListBean : trendList) {
            // 获取区域名称
            String name = trendListBean.getName();
            TrendBean trend = trendListBean.getTrend();
            addTrendHist(covid19TrendHists, name, trend);
        }
    }
}
