package com.smic.cf.schedual;

import com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist;
import com.smic.cf.crawlerbaidu.service.serviceimpl.TrendServiceImpl;
import com.smic.cf.pojo.Crawler;
import com.smic.cf.pojo.DomesticTimeLine;
import com.smic.cf.pojo.ForeignCountryCovid19;
import com.smic.cf.pojo.ProvinceCovid19;
import com.smic.cf.service.DomesticService;
import com.smic.cf.service.ForeignCountryService;
import com.smic.cf.service.TimeLineService;
import com.smic.cf.util.CrawlerParser;
import com.smic.cf.util.CrawlerUtils;
import com.smic.cf.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 定时爬虫
 * @ClassName SysCrawlerSchedule
 * @Author 蔡明涛
 * @date 2020.03.01 21:02
 */
@Component
@Configuration
@EnableScheduling
@Slf4j
public class SysCrawlerSchedule {

    @Resource
    private TimeLineService timeLineService;
    @Resource
    private ForeignCountryService foreignCountryService;
    @Resource
    private DomesticService domesticService;
    @Resource
    private TrendServiceImpl trendService;

    /**
     * 获取疫情基本数据 执行时间，每小时更新
     *
     * @return void
     * @author 蔡明涛
     * @date 2020/3/29 10:57
     */
    @Scheduled(cron = "0 5 0/1 * * * ")
    public void getCovid19Data() {
        log.info("定时脚本开启，时间{} ,数据来源:{}", DateUtils.getCurrentDateTime(),Crawler.URL1);
        //获取页面数据
        String foreignCountryInformation = CrawlerUtils.getJsonString(Crawler.URL1, Crawler.FOREIGN_STATIC_INFORMATION_REGEX_TEMPLATE, Crawler.FOREIGN_STATIC_INFORMATION_ATTRIBUTE);
        String domesticInformation = CrawlerUtils.getJsonString(Crawler.URL1,Crawler.DOMESTIC_STATIC_INFORMATION_REGEX_TEMPLATE, Crawler.DOMESTIC_STATIC_INFORMATION_ATTRIBUTE);

        //解析json数据
        List<ForeignCountryCovid19> foreignCountryCovid19List = CrawlerParser.parseForeignCountryInformation(foreignCountryInformation);
        List<ProvinceCovid19> provinceCovid19List = CrawlerParser.parseDomesticInformation(domesticInformation);

        //将解析的数据存入DB
        foreignCountryService.insertForeignCountryData(foreignCountryCovid19List);
        domesticService.insertDomesticData(provinceCovid19List);

        log.info("定时脚本结束，时间{},数据来源:{}", DateUtils.getCurrentDateTime(),Crawler.URL1);
    }

    /**
     * 获取TimeLine的信息，每10分钟执行一次
     * @return void
     * @author 蔡明涛
     * @date 2020/3/29 11:10
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void getTimeLineData() {
        log.info("定时脚本开启，时间{},数据来源:{}", DateUtils.getCurrentDateTime(),Crawler.URL1);
        //获取页面数据
        String domesticTimeLine = CrawlerUtils.getJsonString(Crawler.URL1, Crawler.TIME_LINE_REGEX_TEMPLATE, Crawler.DOMESTIC_TIME_LINE_ATTRIBUTE);
        //解析json数据
        List<DomesticTimeLine> domesticTimeLineList = CrawlerParser.parseDomesticTimelineInformation(domesticTimeLine);
        // 存入DB
        timeLineService.saveOrUpdateBatch(domesticTimeLineList);

        log.info("定时脚本结束，时间{},数据来源:{}", DateUtils.getCurrentDateTime(),Crawler.URL1);
    }

    /**
     * 每天早10点晚8点去抓取Trend历史数据
     * @return void
     * @author 蔡明涛
     * @date 2020/4/4 20:47
     */
    @Scheduled(cron = "0 0 10,20 * * ?")
    public void getTrendHistData(){
        log.info("定时脚本开启，时间{},数据来源:{}", DateUtils.getCurrentDateTime(),Crawler.URL2);
        String information = CrawlerUtils.getJsonString(Crawler.URL2, Crawler.BAIDU_DATA_REGEX_TEMPLATE, Crawler.BAIDU_DATA_ATTRIBUTE);
        // 解析json数据 并生成集合
        List<Covid19TrendHist> covid19TrendHists = trendService.getTrendHistData("[{"+information+"}]");
        // 存入DB
        trendService.saveOrUpdateBatch(covid19TrendHists);

        log.info("定时脚本结束，时间{},数据来源:{}", DateUtils.getCurrentDateTime(),Crawler.URL2);
    }


}
