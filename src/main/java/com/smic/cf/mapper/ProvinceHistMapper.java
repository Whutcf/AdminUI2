package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.pojo.ProvinceCovid19Hist;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @ClassName ProvinceHistMapper
 * @Author 蔡明涛
 * @date 2020.03.04 23:52
 */
@Mapper
public interface ProvinceHistMapper extends BaseMapper<ProvinceCovid19Hist> {
}
