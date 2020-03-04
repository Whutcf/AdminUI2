package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.pojo.Statistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO
 * @ClassName StatisticsMapper
 * @Author 蔡明涛
 * @date 2020.03.01 13:46
 */
@Mapper
public interface StatisticsMapper extends BaseMapper<Statistics> {
   /**
    * 选择最新的一笔记录
    * @Param []
    * @return com.smic.cf.domain.Statistics
    * @Author 蔡明涛
    * @Date 2020.03.01 16:49
    **/
    Statistics selectLatestOne();
}
