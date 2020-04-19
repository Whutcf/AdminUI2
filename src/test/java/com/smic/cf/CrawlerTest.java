package com.smic.cf;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smic.cf.crawlerbaidu.dto.ComponentBean;
import com.smic.cf.crawlerbaidu.dto.QueryInfoBean;
import com.smic.cf.crawlerbaidu.dto.TrumpetBean;
import com.smic.cf.crawlerbaidu.pojo.Covid19BaiduQueryInfo;
import com.smic.cf.crawlerbaidu.pojo.Covid19Notice;
import com.smic.cf.crawlerbaidu.service.Covid19BaiduQueryInfoService;
import com.smic.cf.entities.pojo.Crawler;
import com.smic.cf.mapper.Covid19NoticeMapper;
import com.smic.cf.util.CrawlerParser;
import com.smic.cf.util.CrawlerUtils;
import com.smic.cf.util.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private Covid19NoticeMapper covid19NoticeMapper;
    @Resource
    private Covid19BaiduQueryInfoService covid19BaiduQueryInfoService;

    @Test
    public void getData() {
//        //获取页面数据
//        String information = CrawlerUtils.getJsonString(Crawler.URL2, Crawler.BAIDU_DATA_REGEX_TEMPLATE, Crawler.BAIDU_DATA_ATTRIBUTE);
//        ComponentBean componentBean = CrawlerParser.getComponentBean(information);
//        if (componentBean != null) {
//            List<TrumpetBean> trumpetBeanList = componentBean.getTrumpet();
//            // 已知只有一个值
//            TrumpetBean trumpetBean = trumpetBeanList.get(0);
//            Covid19Notice notice = new Covid19Notice();
//            BeanUtils.copyProperties(trumpetBean, notice);
//            String date = DateUtils.getCurrentDate();
//            notice.setDate(date);
//            LambdaQueryWrapper<Covid19Notice> queryWrapper = Wrappers.lambdaQuery();
//            queryWrapper.eq(StringUtils.isNotBlank(date), Covid19Notice::getDate, date);
//            List<Covid19Notice> covid19Notices = covid19NoticeMapper.selectList(queryWrapper);
//            if (covid19Notices.size() > 0) {
//                for (int i = covid19Notices.size() - 1; i < covid19Notices.size(); i++) {
//                    if (!covid19Notices.get(i).getDate().equals(date)) {
//                        covid19NoticeMapper.insert(notice);
//                    }
//                }
//            } else {
//                covid19NoticeMapper.insert(notice);
//            }
//
//            List<Covid19BaiduQueryInfo> queryInfos = new ArrayList<>();
//            List<QueryInfoBean> gossips = componentBean.getGossips();
//            for (QueryInfoBean gossip : gossips) {
//                addQueryInfoList(queryInfos, gossip);
//            }
//            List<QueryInfoBean> knowledges = componentBean.getKnowledges();
//            for (QueryInfoBean knowledge : knowledges) {
//                addQueryInfoList(queryInfos, knowledge);
//            }
//
//            List<QueryInfoBean> hotwords = componentBean.getHotwords();
//            for (QueryInfoBean hotword : hotwords) {
//                if (hotword.getType().equals("0")){
//                    hotword.setType("1");
//                }
//                addQueryInfoList(queryInfos,hotword);
//            }
//            covid19BaiduQueryInfoService.saveOrUpdateBatch(queryInfos);
//        }


    }

//    public void addQueryInfoList(List<Covid19BaiduQueryInfo> queryInfos, QueryInfoBean infoBean) {
//        Covid19BaiduQueryInfo queryInfo = new Covid19BaiduQueryInfo();
//        BeanUtils.copyProperties(infoBean, queryInfo);
//        queryInfos.add(queryInfo);
//    }


}
