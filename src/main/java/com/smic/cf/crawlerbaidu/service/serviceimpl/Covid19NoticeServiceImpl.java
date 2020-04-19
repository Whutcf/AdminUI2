package com.smic.cf.crawlerbaidu.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.smic.cf.crawlerbaidu.dto.ComponentBean;
import com.smic.cf.crawlerbaidu.dto.TrumpetBean;
import com.smic.cf.crawlerbaidu.pojo.Covid19Notice;
import com.smic.cf.crawlerbaidu.service.Covid19NoticeService;
import com.smic.cf.mapper.Covid19NoticeMapper;
import com.smic.cf.util.CrawlerParser;
import com.smic.cf.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName Covid19NoticeServiceImpl
 * @Author 蔡明涛
 * @Date 2020/4/19 12:16
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class Covid19NoticeServiceImpl implements Covid19NoticeService {

    @Resource
    private Covid19NoticeMapper covid19NoticeMapper;

    /**
     * 保存公告信息
     *
     * @param baiduSourceInformation json字符串
     * @return void
     * @author 蔡明涛
     * @date 2020/4/19 14:21
     */
    @Override
    public void saveCovid19Notice(String baiduSourceInformation) {
        ComponentBean componentBean = CrawlerParser.getComponentBean(baiduSourceInformation);
        if (componentBean != null) {
            List<TrumpetBean> trumpetBeanList = componentBean.getTrumpet();
            String date = DateUtils.getCurrentDate();
            for (TrumpetBean trumpetBean : trumpetBeanList) {
                Covid19Notice notice = new Covid19Notice();
                BeanUtils.copyProperties(trumpetBean, notice);
                notice.setDate(date);
                // 获取当天数据库中的所有公告
                List<Covid19Notice> covid19Notices = getNotices();
                if (covid19Notices.size() > 0) {
                    // 首先要判断查出来的数据是否包含该公告
                    if (!covid19Notices.contains(notice)) {
                        for (Covid19Notice covid19Notice : covid19Notices) {
                            // 其次还需要比较除了日期以外的值是否不相等,排除昨天的数据未刷新的情况
                            List<String> ignoreList = new ArrayList<>();
                            ignoreList.add("date");
                            boolean compareTwoObjs = com.smic.cf.util.BeanUtils.compareTwoObjs(ignoreList, notice, covid19Notice);
                            if (!compareTwoObjs) {
                                covid19NoticeMapper.insert(notice);
                            }
                        }
                    }
                } else {
                    covid19NoticeMapper.insert(notice);
                }
            }

        }
    }

    /**
     * 获取公告信息的集合
     *
     * @return java.util.List<com.smic.cf.crawlerbaidu.pojo.Covid19Notice>
     * @author 蔡明涛
     * @date 2020/4/19 16:47
     */
    @Override
    public List<Covid19Notice> getNotices() {
        String date = DateUtils.getCurrentDate();
        LambdaQueryWrapper<Covid19Notice> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Covid19Notice::getDate, date);
        return covid19NoticeMapper.selectList(queryWrapper);
    }
}
