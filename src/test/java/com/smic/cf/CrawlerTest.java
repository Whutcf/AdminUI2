package com.smic.cf;

import com.smic.cf.pojo.Crawler;
import com.smic.cf.pojo.DomesticTimeLine;
import com.smic.cf.pojo.ForeignCountryCovid19;
import com.smic.cf.pojo.ProvinceCovid19;
import com.smic.cf.service.DomesticService;
import com.smic.cf.service.ForeignCountryService;
import com.smic.cf.service.TimeLineService;
import com.smic.cf.service.impl.ForeignCountryServiceImpl;
import com.smic.cf.util.CrawlerParser;
import com.smic.cf.util.CrawlerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @ClassName CrawlerTest
 * @Author 蔡明涛
 * @Date 2020/3/28 9:33
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerTest {

    @Resource
    private ForeignCountryService foreignCountryService;
    @Resource
    private DomesticService domesticService;
    @Resource
    private TimeLineService timeLineService;

    @Test
    public void getData() {
        //获取页面数据
//        CrawlerUtils.getPage(Crawler.URL);

        //提取页面数据（JSON格式）
//        String foreignCountryInformation = CrawlerUtils.getInformation(Crawler.FOREIGN_STATIC_INFORMATION_REGEX_TEMPLATE, Crawler.ID, Crawler.FOREIGN_STATIC_INFORMATION_ATTRIBUTE);
//        String domesticInformation = CrawlerUtils.getInformation(Crawler.DOMESTIC_STATIC_INFORMATION_REGEX_TEMPLATE, Crawler.ID, Crawler.DOMESTIC_STATIC_INFORMATION_ATTRIBUTE);
//        String domesticTimeLine = CrawlerUtils.getInformation(Crawler.TIME_LINE_REGEX_TEMPLATE, Crawler.ID, Crawler.DOMESTIC_TIME_LINE_ATTRIBUTE);

        //解析json数据
//        List<ForeignCountryCovid19> foreignCountryCovid19List = CrawlerParser.parseForeignCountryInformation(foreignCountryInformation);
//        List<ProvinceCovid19> provinceCovid19List = CrawlerParser.parseDomesticInformation(domesticInformation);
//        List<DomesticTimeLine> domesticTimeLineList = CrawlerParser.parseDomesticTimelineInformation(domesticTimeLine);
        //将解析的数据存入DB
//        foreignCountryService.insertForeignCountryData(foreignCountryCovid19List);
//        domesticService.insertDomesticData(provinceCovid19List);
//        timeLineService.insertTimeLine(domesticTimeLineList);
//        timeLineService.saveOrUpdateBatch(domesticTimeLineList);

    }


}
