package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smic.cf.pojo.ForeignStatistics;
import com.smic.cf.pojo.Marquee;
import com.smic.cf.pojo.Statistics;
import com.smic.cf.pojo.TrendChart;
import com.smic.cf.mapper.ForeignStatisticsMapper;
import com.smic.cf.mapper.MarqueeMapper;
import com.smic.cf.mapper.StatisticsMapper;
import com.smic.cf.mapper.TrendChartMapper;
import com.smic.cf.service.StatisticsService;
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
 * @Description 处理国内外汇总数据及相关chart图
 * @ClassName StatisticsServiceImpl
 * @Author 蔡明涛
 * @date 2020.03.01 13:39
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;
    @Resource
    private ForeignStatisticsMapper foreignStatisticsMapper;
    @Resource
    private MarqueeMapper marqueeMapper;
    @Resource
    private TrendChartMapper trendChartMapper;

    @Override
    public String insertStatics(Statistics statistics) {
        StringBuilder statisticNews = new StringBuilder();
        //使用Mybatis-plus提供的方法取代selectLastOne()
        LambdaQueryWrapper<Statistics> query = Wrappers.lambdaQuery();
        query.orderByDesc(Statistics::getModifyTime).last("limit 1");
        Statistics oldStatistics = statisticsMapper.selectOne(query);
        if (!StringUtils.isEmpty(oldStatistics)){
            if (oldStatistics.getModifyTime().equalsIgnoreCase(statistics.getModifyTime())){
                log.debug("不需要更新数据： {}",statistics.toString());
                return null;
            }else {
                //更新国内信息总表
                updateStatistics(statistics, statisticNews);
                //更新国外信息总表
                updateForeignStatistics(statistics.getForeignStatistics());
                //保存所有TrendChar
                insertTrendChart(statistics);
                //保存跑马灯信息
                insertMarquee(statistics.getMarquee());
            }
        }else {
            //第一次抓取到数据会用到
            updateStatistics(statistics, statisticNews);
            updateForeignStatistics(statistics.getForeignStatistics());
            insertTrendChart(statistics);
            insertMarquee(statistics.getMarquee());
        }

        return statisticNews.toString();
    }

    /**
     * 更新跑马灯信息,只记录第一笔数据
     * @param marquees 跑马灯实体的集合
     * @return void
     * @author 蔡明涛
     * @date 2020.03.01 17:58
     **/
    private void insertMarquee(List<Marquee> marquees) {
        for (Marquee marquee: marquees ) {
            //只记录了一笔，直接根据Label，content和link判断是否存在
            LambdaQueryWrapper<Marquee> lambdaQueryWrapper = Wrappers.lambdaQuery();
            lambdaQueryWrapper.eq(Marquee::getMarqueeContent,marquee.getMarqueeContent()).eq(Marquee::getMarqueeLabel,marquee.getMarqueeLabel())
                    .eq(Marquee::getMarqueeLink,marquee.getMarqueeLink());
            Marquee oldMarquee = marqueeMapper.selectOne(lambdaQueryWrapper);
            if (StringUtils.isEmpty(oldMarquee)){
                marquee.setCreateTime(DateUtils.getCurrentDateTime());
                marqueeMapper.insert(marquee);
            }else {
                List<String> ignoreFields = new ArrayList<>();
                ignoreFields.add("id");
                ignoreFields.add("createTime");
                if (BeanUtils.compareTwoObjs(ignoreFields,oldMarquee,marquee)){
                    //如果相等，无需添加
                    log.debug("无需添加新的跑马灯！");
                }else {
                    //如果不相等，删除原来的替换成新的
                    marqueeMapper.delete(lambdaQueryWrapper);
                    marquee.setCreateTime(DateUtils.getCurrentDateTime());
                    marqueeMapper.insert(marquee);
                    log.debug("新添加的跑马灯信息是{}",marquee.toString());
                }
            }
        }
    }

    /**
     * 保存当天的即时图片
     * @param statistics 基础信息实体
     * @return void
     * @author 蔡明涛
     * @date 2020.03.01 17:41
     **/
    private void insertTrendChart(Statistics statistics) {
        List<TrendChart> trendCharts = new ArrayList<>(statistics.getQuanguoTrendChart());
        trendCharts.addAll(statistics.getHbFeiHbTrendChart());
        trendCharts.addAll(statistics.getForeignTrendChart());
        trendCharts.addAll(statistics.getImportantForeignTrendChart());
        for (TrendChart trendChart : trendCharts) {
            log.debug("需要保存的图片信息为{}", trendChart.toString());
        }
        trendChartMapper.deleteAll();
        trendChartMapper.insertAll(trendCharts);
        trendChartMapper.updateDate(DateUtils.getCurrentDate());
    }

    /**
     * 更新国外整体情况
     * @Param [statistics]
     * @return void
     * @Author 蔡明涛
     * @Date 2020.03.01 17:02
     **/
    private void updateForeignStatistics(ForeignStatistics foreignStatistics) {
        ForeignStatistics oldForeignStatistics = foreignStatisticsMapper.selectLatestOne();
        if (!StringUtils.isEmpty(oldForeignStatistics.getCountRemark())){
            // 当新旧不相等时需要更新
            List<String> ignoreFields = new ArrayList<>();
            ignoreFields.add("updateTime");
            ignoreFields.add("countRemark");
            if(!BeanUtils.compareTwoObjs(ignoreFields,oldForeignStatistics,foreignStatistics)){
                foreignStatistics.setCountRemark("确诊"+foreignStatistics.getCurrentConfirmedCount()+"例，治愈"+
                        foreignStatistics.getCuredCount()+"例，死亡"+foreignStatistics.getDeadCount()+"例!");
                log.debug("国外最新情报,截至{},外国总共：{}", DateUtils.getCurrentDateTime(),foreignStatistics.getCountRemark());
                foreignStatistics.setUpdateTime(DateUtils.getCurrentDateTime());
                foreignStatisticsMapper.insert(foreignStatistics);
            }else {
                log.debug("国外当前状况,截至{},外国总共：{}",oldForeignStatistics.getUpdateTime(),oldForeignStatistics.getCountRemark());
            }
        }else {
            foreignStatistics.setCountRemark("确诊"+foreignStatistics.getCurrentConfirmedCount()+"例，治愈"+
                    foreignStatistics.getCuredCount()+"例，死亡"+foreignStatistics.getDeadCount()+"例!");
            log.debug("国外最新情报,截至{},外国总共：{}", DateUtils.getCurrentDateTime(),foreignStatistics.getCountRemark());
            foreignStatistics.setUpdateTime(DateUtils.getCurrentDateTime());
            foreignStatisticsMapper.insert(foreignStatistics);
        }
    }

    /**
     * @Description 新增国内外基本信息
     * @Param [statistics, statisticNews]
     * @return void
     * @Author 蔡明涛
     * @Date 2020.03.01 15:49
     **/
    private void updateStatistics(Statistics statistics, StringBuilder statisticNews) {
        statistics.setCountRemark("当前确诊"+statistics.getCurrentConfirmedCount()+"例,疑似"+statistics.getSuspectedCount()+"例，治愈"+
                statistics.getCuredCount()+"例，死亡"+statistics.getDeadCount()+"例!");
        statisticNews.append(statistics.getCountRemark());
        log.debug("最新情报：{}",statisticNews.toString());
        statisticsMapper.insert(statistics);
    }
}
