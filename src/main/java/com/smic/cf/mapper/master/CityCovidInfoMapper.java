package com.smic.cf.mapper.master;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.pojo.CityCovid19Info;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO
 * @ClassName CityMapper
 * @Author 蔡明涛
 * @date 2020.03.02 21:17
 */
@Mapper
public interface CityCovidInfoMapper extends BaseMapper<CityCovid19Info> {
}
