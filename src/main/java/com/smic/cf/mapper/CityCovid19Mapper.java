package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.entities.pojo.CityCovid19;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @ClassName CityMapper
 * @Author 蔡明涛
 * @date 2020.03.02 21:17
 */
@Mapper
public interface CityCovid19Mapper extends BaseMapper<CityCovid19> {
    /**
     * 获取当前境外输入总数
     *
     * @return int
     * @author 蔡明涛
     * @date 2020/4/6 22:36
     */
    int getCurrentCovid19ForeignIn();
}
