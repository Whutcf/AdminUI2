package com.smic.cf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smic.cf.pojo.TimeLine;

import java.util.List;

/**
 * @Description TODO
 * @ClassName TimeLineService
 * @Author 蔡明涛
 * @date 2020.03.01 19:41
 */
public interface TimeLineService extends IService<TimeLine> {
    /**
     * 存储时间线的数据
     * @param timeLines 时间线集合
     * @return void
     * @author 蔡明涛
     * @date 2020.03.01 19:42
     **/
    String insertTimeLine(List<TimeLine> timeLines);
}
