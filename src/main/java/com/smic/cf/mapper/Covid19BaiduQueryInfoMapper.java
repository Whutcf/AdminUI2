package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.crawlerbaidu.pojo.Covid19BaiduQueryInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @ClassName Covid19BaiduQueryInfoMapper
 * @Author 蔡明涛
 * @Date 2020/4/19 12:44
 **/
@Mapper
public interface Covid19BaiduQueryInfoMapper extends BaseMapper<Covid19BaiduQueryInfo> {
}
