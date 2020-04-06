package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist;
import com.smic.cf.mapper.Covid19TrendHistMapper;
import com.smic.cf.service.Covid19TrendHistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description
 * @ClassName Covid19TrendHistServiceImpl
 * @Author 蔡明涛
 * @Date 2020/4/6 22:21
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class Covid19TrendHistServiceImpl extends ServiceImpl<Covid19TrendHistMapper, Covid19TrendHist> implements Covid19TrendHistService {
    @Resource
    private Covid19TrendHistMapper covid19TrendHistMapper;

    /**
     * 获取昨日新增入境病例数据
     *
     * @return com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist
     * @author 蔡明涛
     * @date 2020/4/6 22:25
     */
    @Override
    public Covid19TrendHist getYesterdayForeignInIncr() {
        return covid19TrendHistMapper.getYesterdayForeignInIncr();
    }
}
