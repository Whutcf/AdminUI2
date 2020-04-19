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
            // 默认只获取第一条公告信息
            TrumpetBean trumpetBean = trumpetBeanList.get(0);
            Covid19Notice notice = new Covid19Notice();
            BeanUtils.copyProperties(trumpetBean, notice);
            String date = DateUtils.getCurrentDate();
            notice.setDate(date);
            LambdaQueryWrapper<Covid19Notice> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(StringUtils.isNotBlank(date), Covid19Notice::getDate, date);
            List<Covid19Notice> covid19Notices = covid19NoticeMapper.selectList(queryWrapper);
            if (covid19Notices.size() > 0) {
                for (int i = covid19Notices.size() - 1; i < covid19Notices.size(); i++) {
                    if (!covid19Notices.get(i).getDate().equals(date)) {
                        covid19NoticeMapper.insert(notice);
                    }
                }
            } else {
                covid19NoticeMapper.insert(notice);
            }
        }
    }
}
