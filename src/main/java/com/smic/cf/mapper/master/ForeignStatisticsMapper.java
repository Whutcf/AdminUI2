package com.smic.cf.mapper.master;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.pojo.ForeignStatistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO
 * @ClassName ForeignStatisticsMapper
 * @Author 蔡明涛
 * @date 2020.03.01 15:37
 */
@Mapper
public interface ForeignStatisticsMapper extends BaseMapper<ForeignStatistics> {
    /**
     * 选择表格里的最新记录
     * @Param []
     * @return com.smic.cf.domain.ForeignStatistics
     * @Author 蔡明涛
     * @Date 2020.03.01 16:47
     **/
    ForeignStatistics selectLatestOne();
}
