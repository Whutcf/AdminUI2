package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.crawlerbaidu.pojo.Covid19TrendHist;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @ClassName TrendMapper
 * @Author 蔡明涛
 * @Date 2020/4/4 16:24
 **/
@Mapper
public interface TrendMapper extends BaseMapper<Covid19TrendHist> {
}
