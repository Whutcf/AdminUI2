package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.entities.pojo.ForeignStatisticsTrendChartData;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @ClassName ForeignStatisticTrendChartDataMapper
 * @Author 蔡明涛
 * @Date 2020/3/28 21:31
 **/
@Mapper
public interface ForeignStatisticTrendChartDataMapper extends BaseMapper<ForeignStatisticsTrendChartData> {
    /**
     * 获取最大日期即昨天的日期
     * @param locationId 地理位置id
     * @return java.lang.String
     * @author 蔡明涛
     * @date 2020/4/6 18:59
     */
    String getMaxDateId(Long locationId);
}
