package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smic.cf.mapper.TimeLineMapper;
import com.smic.cf.pojo.DomesticTimeLine;
import com.smic.cf.service.TimeLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description
 * @ClassName TimeLineServiceImpl
 * @Author 蔡明涛
 * @date 2020.03.01 19:44
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TimeLineServiceImpl extends ServiceImpl<TimeLineMapper, DomesticTimeLine> implements TimeLineService {

    @Resource
    private TimeLineMapper timeLineMapper;

}
