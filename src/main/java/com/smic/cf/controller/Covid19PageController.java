package com.smic.cf.controller;

import com.smic.cf.constants.CrawlerConstants;
import com.smic.cf.crawlerbaidu.pojo.Covid19Notice;
import com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist;
import com.smic.cf.crawlerbaidu.service.Covid19NoticeService;
import com.smic.cf.entities.pojo.ForeignCountryCovid19;
import com.smic.cf.entities.pojo.ForeignStatisticsTrendChartData;
import com.smic.cf.entities.vo.DomesticSummaryVo;
import com.smic.cf.entities.vo.TimelineVo;
import com.smic.cf.service.Covid19TrendHistService;
import com.smic.cf.service.DomesticService;
import com.smic.cf.service.ForeignCountryService;
import com.smic.cf.service.TimeLineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 结合thymeleaf 专为后台传递参数给前台使用
 * @ClassName Covid19PageController
 * @Author 蔡明涛
 * @Date 2020/4/6 10:01
 **/
@Controller
public class Covid19PageController {
    @Resource
    private TimeLineService timeLineService;
    @Resource
    private ForeignCountryService foreignCountryService;
    @Resource
    private Covid19TrendHistService covid19TrendHistService;
    @Resource
    private DomesticService domesticService;
    @Resource
    private Covid19NoticeService noticeService;


    /**
     * 获取时间线的信息并传递给前台
     *
     * @param model 用于页面传参
     * @return java.lang.String
     * @author 蔡明涛
     * @date 2020/4/6 18:12
     */
    @GetMapping("/tgls/reportForm/covid19Timeline")
    public String getTimeline(Model model) {
        List<TimelineVo> timelineList = timeLineService.getTimeline();
        model.addAttribute("timelineList", timelineList);
        return "tgls/reportForm/covid19Timeline";
    }

    /**
     * 获取国内汇总信息，展示给前台
     *
     * @param model      页面传参
     * @param locationId 地理位置代称
     * @return java.lang.String
     * @author 蔡明涛
     * @date 2020/4/6 18:16
     */
    @GetMapping("/tgls/reportForm/domesticCovid19")
    public String getDomesticInfo(Model model, @RequestParam(CrawlerConstants.LOCATION_ID) Long locationId) {
        DomesticSummaryVo summaryVo = new DomesticSummaryVo();
        // 获取当前最新数据 现有确诊 累计确诊 累计治愈 累计死亡
        ForeignCountryCovid19 domesticInfo = foreignCountryService.getForeignCountryById(locationId);
        summaryVo.setConfirmedCount(domesticInfo.getConfirmedCount());
        summaryVo.setCurrentConfirmedCount(domesticInfo.getCurrentConfirmedCount());
        summaryVo.setCuredCount(domesticInfo.getCuredCount());
        summaryVo.setDeadCount(domesticInfo.getDeadCount());
        // 获取昨日新增数据 新增现有确诊 新增累计确诊 新增累计治愈 新增累计死亡
        ForeignStatisticsTrendChartData yesterdayInfo = foreignCountryService.getYesterdayCountryDataById(locationId);
        summaryVo.setCurrentConfirmedIncr(yesterdayInfo.getCurrentConfirmedIncr());
        summaryVo.setConfirmedIncr(yesterdayInfo.getConfirmedIncr());
        summaryVo.setCuredIncr(yesterdayInfo.getCuredIncr());
        summaryVo.setDeadIncr(yesterdayInfo.getDeadIncr());
        // 疑似病例 新增疑似病例 数据存在问题，暂时这么操作
        int suspectedCount = domesticService.getTotalSuspectedCount();
        summaryVo.setSuspectedCount(suspectedCount);
        int suspectedIncr = covid19TrendHistService.getYesterdaySuspectedIncr();
        summaryVo.setSuspectedCountIncr(suspectedIncr);
        // 获取境外病例和新增境外病例
        int currentCovid19ForeignIn = domesticService.getCurrentCovid19ForeignIn();
        summaryVo.setForeignInCount(currentCovid19ForeignIn);
        Covid19TrendHist covid19TrendHist = covid19TrendHistService.getYesterdayForeignInIncr();
        summaryVo.setForeignInIncr(covid19TrendHist.getValue());

        // 获取公告信息
        List<Covid19Notice> notices = noticeService.getNotices();

        model.addAttribute("summaryVo", summaryVo);
        model.addAttribute("notices", notices);
        return "tgls/reportForm/domesticCovid19";
    }
}
