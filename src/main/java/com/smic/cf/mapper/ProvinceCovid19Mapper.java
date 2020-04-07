package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.entities.pojo.ProvinceCovid19;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @ClassName ProvinceMapper
 * @Author 蔡明涛
 * @date 2020.03.02 21:16
 */
@Mapper
public interface ProvinceCovid19Mapper extends BaseMapper<ProvinceCovid19> {
    /**
     * 获取疑似病例总数
     *
     * @return int
     * @author 蔡明涛
     * @date 2020/4/7 20:49
     */
    int getTotalSuspectedCount();
}
