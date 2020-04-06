package com.smic.cf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smic.cf.entities.pojo.TimeLine;
import com.smic.cf.entities.vo.TimelineVo;

import java.util.List;

/**
 * @Description
 * @ClassName TimeLineService
 * @Author 蔡明涛
 * @date 2020.03.01 19:41
 */
public interface TimeLineService extends IService<TimeLine> {

    /**
     * 获取时间线信息 并处理成界面需要的内容
     *
     * @return java.util.List<com.smic.cf.entitis.vo.TimelineVo>
     * @author 蔡明涛
     * @date 2020/4/6 10:12
     */
    List<TimelineVo> getTimeline();
}
