package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.pojo.ProvinceCovid19Info;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO
 * @ClassName ProvinceMapper
 * @Author 蔡明涛
 * @date 2020.03.02 21:16
 */
@Mapper
public interface ProvinceMapper extends BaseMapper<ProvinceCovid19Info> {
}
