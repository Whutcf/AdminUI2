package com.smic.cf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smic.cf.crawlerbaidu.pojo.Covid19Notice;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 百度数据源公告mapper
 * @ClassName Covid19NoticeMapper
 * @Author 蔡明涛
 * @Date 2020/4/19 12:14
 **/
@Mapper
public interface Covid19NoticeMapper extends BaseMapper<Covid19Notice> {
}
