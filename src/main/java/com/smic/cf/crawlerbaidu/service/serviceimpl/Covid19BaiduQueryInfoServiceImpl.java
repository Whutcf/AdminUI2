package com.smic.cf.crawlerbaidu.service.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smic.cf.crawlerbaidu.dto.ComponentBean;
import com.smic.cf.crawlerbaidu.dto.QueryInfoBean;
import com.smic.cf.crawlerbaidu.pojo.Covid19BaiduQueryInfo;
import com.smic.cf.crawlerbaidu.service.Covid19BaiduQueryInfoService;
import com.smic.cf.mapper.Covid19BaiduQueryInfoMapper;
import com.smic.cf.util.CrawlerParser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 热词，谣言，常识服务实现类
 * @ClassName Covid19BaiduQueryInfoServiceImpl
 * @Author 蔡明涛
 * @Date 2020/4/19 12:46
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class Covid19BaiduQueryInfoServiceImpl extends ServiceImpl<Covid19BaiduQueryInfoMapper, Covid19BaiduQueryInfo> implements Covid19BaiduQueryInfoService {
    /**
     * 存储百度查询热词，谣言和常识资源
     *
     * @param baiduSourceInformation json字符串
     * @return java.util.List<com.smic.cf.crawlerbaidu.pojo.Covid19BaiduQueryInfo>
     * @author 蔡明涛
     * @date 2020/4/19 14:30
     */
    @Override
    public List<Covid19BaiduQueryInfo> getBaiduQueryInfoList(String baiduSourceInformation) {
        List<Covid19BaiduQueryInfo> queryInfos = new ArrayList<>();
        // 获取百度数据源的实体对象
        ComponentBean componentBean = CrawlerParser.getComponentBean(baiduSourceInformation);
        if (componentBean != null) {
            List<QueryInfoBean> gossips = componentBean.getGossips();
            for (QueryInfoBean gossip : gossips) {
                addQueryInfoList(queryInfos, gossip);
            }
            List<QueryInfoBean> knowledges = componentBean.getKnowledges();
            for (QueryInfoBean knowledge : knowledges) {
                addQueryInfoList(queryInfos, knowledge);
            }

            List<QueryInfoBean> hotwords = componentBean.getHotwords();
            for (QueryInfoBean hotword : hotwords) {
                if (hotword.getType().equals("0")) {
                    hotword.setType("1");
                }
                addQueryInfoList(queryInfos, hotword);
            }
        }
        return queryInfos;
    }

    /**
     * 将
     * @param queryInfos 搜集查询信息的集合
     * @param infoBean 查询信息对象
     * @return void
     * @author 蔡明涛
     * @date 2020/4/19 14:33
     */
    public void addQueryInfoList(List<Covid19BaiduQueryInfo> queryInfos, QueryInfoBean infoBean) {
        Covid19BaiduQueryInfo queryInfo = new Covid19BaiduQueryInfo();
        BeanUtils.copyProperties(infoBean, queryInfo);
        queryInfos.add(queryInfo);
    }
}
