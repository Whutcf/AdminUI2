package com.smic.cf.schedual;

import com.smic.cf.pojo.*;
import com.smic.cf.service.ForeignCountryService;
import com.smic.cf.service.ProvinceService;
import com.smic.cf.service.StatisticsService;
import com.smic.cf.service.TimeLineService;
import com.smic.cf.util.CrawlerPaser;
import com.smic.cf.util.CrawlerUtils;
import com.smic.cf.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    private StatisticsService statisticsService;
    @Resource
    private TimeLineService timeLineService;
    @Resource
    private ProvinceService provinceService;

    @Resource
    private ForeignCountryService foreignCountryService;

    @Scheduled(cron="0 0/20 * * * ? ")
    public void getData(){
        log.info("定时脚本开启，时间{}", DateUtils.getCurrentDateTime());
        //获取页面数据
        CrawlerUtils.getPage(Crawler.URL);

        //提取页面数据（JSON格式）
        String staticInformation = CrawlerUtils.getInformation(Crawler.STATIC_INFORMATION_REGEX_TEMPLATE, "id", Crawler.STATIC_INFORMATION_ATTRIBUTE);
        String timeLineInformation = CrawlerUtils.getInformation(Crawler.TIME_LINE_REGEX_TEMPLATE,"id",Crawler.TIME_LINE_ATTRIBUTE);
        String areaInformation = CrawlerUtils.getInformation(Crawler.AREA_INFORMATION_REGEX_TEMPLATE, "id", Crawler.AREA_INFORMATION_ATTRIBUTE);
        String provinceInformation = CrawlerUtils.getInformation(Crawler.PROVINCE_INFORMATION_REGEX_TEMPLATE, "id", Crawler.PROVINCE_INFORMATION_ATTRIBUTE);
        String foreignCountryInformation = CrawlerUtils.getInformation(Crawler.FOREIGN_STATIC_INFORMATION_REGEX_TEMPLATE, "id", Crawler.FOREIGN_STATIC_INFORMATION_ATTRIBUTE);

        //解析json数据
        Statistics statistics = CrawlerPaser.parseStatisticsInformation(staticInformation);
        List<TimeLine> timeLines = CrawlerPaser.parseTimelineInformation(timeLineInformation);
        List<ProvinceCovid19Info> areaList = CrawlerPaser.parseAreaInformation(areaInformation);
        List<ProvinceCovid19Info> provinceCovid19InfoList = CrawlerPaser.parseAreaInformation(provinceInformation);
        List<ForeignCountryCovid19Info> foreignCountryCovid19InfoList = CrawlerPaser.parseForeignCountryInformation(foreignCountryInformation);

        //将解析的数据存入DB
        String timeLineNews = timeLineService.insertTimeLine(timeLines);
        String statisticsNews = statisticsService.insertStatics(statistics);
        if (StringUtils.isEmpty(statisticsNews)){
            String chinaAreaNews = provinceService.insertProvinceAndCitys(areaList, provinceCovid19InfoList);
            log.debug("{}",chinaAreaNews);
        }
        String foreignCountryNews = foreignCountryService.insertForeignCountryData(foreignCountryCovid19InfoList);

        log.debug("{}",statisticsNews);
        log.debug("{}",timeLineNews);
        log.debug("{}",foreignCountryNews);

        log.info("定时脚本结束，时间{}", DateUtils.getCurrentDateTime());
    }

}
