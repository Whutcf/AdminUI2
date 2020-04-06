package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.entities.pojo.TimeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description
 * @ClassName TimeLineMapper
 * @Author 蔡明涛
 * @date 2020.03.01 19:46
 */
@Mapper
public interface TimeLineMapper extends BaseMapper<TimeLine> {
    /**
     * 获取3天内的数据
     *
     * @return java.util.List<com.smic.cf.entitis.pojo.TimeLine>
     * @author 蔡明涛
     * @date 2020/4/6 10:54
     */
    List<TimeLine> getThreeDaysData();

    /**
     * 获取三天的日期
     * @return java.util.List<java.lang.String>
     * @author 蔡明涛
     * @date 2020/4/6 11:32
     */
    List<String> getThreeDaysStr();
}
