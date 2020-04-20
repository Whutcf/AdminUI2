package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @Description
 * @ClassName TrendMapper
 * @Author 蔡明涛
 * @Date 2020/4/4 16:24
 **/
@Mapper
public interface TrendMapper extends BaseMapper<Covid19TrendHist> {
    /**
     * 获取每日新增人数的结果集
     *
     * @param name sys_covid19_trend_hist中的分类名称：ex.中国疫情汇总
     * @param seriesName sys_covid19_trend_hist中的系列名称：ex.新增确诊
     * @return java.util.List<com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist>
     * @author 蔡明涛
     * @date 2020/4/20 22:49
     */
    List<Covid19TrendHist> getCaseCount(@Param("name") String name, @Param("seriesName") String seriesName);
}
