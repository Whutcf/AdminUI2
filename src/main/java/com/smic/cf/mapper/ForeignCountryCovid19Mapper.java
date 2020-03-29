package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.pojo.ForeignCountryCovid19;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @return
 * @author 蔡明涛
 * @date 2020/3/28 20:06
 */
@Mapper
public interface ForeignCountryCovid19Mapper extends BaseMapper<ForeignCountryCovid19> {
    /**
     * 获取大洲的值
     * @return java.util.List<java.lang.String>
     * @author 蔡明涛
     * @date 2020/3/29 19:59
     */
    List<String> getContinents();

    /**
     * 获取大洲对应的国家
     * @param continents
     * @return java.util.List<java.lang.String>
     * @author 蔡明涛
     * @date 2020/3/29 20:38
     */
    List<String> getCountries(String continents);
}
