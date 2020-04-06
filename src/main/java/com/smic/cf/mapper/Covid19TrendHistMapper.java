package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @ClassName Covid19TrendHistMapper
 * @Author 蔡明涛
 * @Date 2020/4/6 22:19
 **/
@Mapper
public interface Covid19TrendHistMapper extends BaseMapper<Covid19TrendHist> {
    /**
     * 获取昨日新增入境病例数据
     *
     * @return com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist
     * @author 蔡明涛
     * @date 2020/4/6 22:26
     */
    Covid19TrendHist getYesterdayForeignInIncr();
}
