package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.pojo.TrendChart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description TODO
 * @ClassName TrendChartMapper
 * @Author 蔡明涛
 * @date 2020.03.01 15:39
 */
@Mapper
public interface TrendChartMapper extends BaseMapper<TrendChart> {
    /**
     * 删除所有记录
     * @Param []
     * @return void
     * @Author 蔡明涛
     * @Date 2020.03.01 17:24
     **/
    void deleteAll();

    /**
     * 插入所有记录
     * @param trendCharts 所有trendChart的集合
     * @return void
     * @author 蔡明涛
     * @date 2020.03.01 17:27
     **/
    void insertAll(@Param("chartList") List<TrendChart> trendCharts);

    /**
     * 更新图片生成日期
     * @param currentDate 当天日期
     * @return void
     * @author 蔡明涛
     * @date 2020.03.01 17:37
     **/
    void updateDate(String currentDate);
}
