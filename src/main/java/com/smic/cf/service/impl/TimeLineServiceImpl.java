package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smic.cf.entities.dto.TimelineDto;
import com.smic.cf.mapper.TimeLineMapper;
import com.smic.cf.entities.pojo.TimeLine;
import com.smic.cf.service.TimeLineService;
import com.smic.cf.entities.vo.TimelineVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName TimeLineServiceImpl
 * @Author 蔡明涛
 * @date 2020.03.01 19:44
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TimeLineServiceImpl extends ServiceImpl<TimeLineMapper, TimeLine> implements TimeLineService {

    @Resource
    private TimeLineMapper timeLineMapper;

    /**
     * 获取时间线信息 并处理成界面需要的内容
     * 搞的很复杂，但是生命在于折腾！
     *
     * @return java.util.List<com.smic.cf.entities.vo.TimelineVo>
     * @author 蔡明涛
     * @date 2020/4/6 10:12
     */
    @Override
    public List<TimelineVo> getTimeline() {
        List<TimelineVo> timelineVos = new ArrayList<>();
        // 获取取值日期
        List<String> dates = timeLineMapper.getThreeDaysStr();
        for (String date : dates) {
            TimelineVo timelineVo = new TimelineVo();
            timelineVo.setDate(date);
            timelineVos.add(timelineVo);
        }
        // 只获取三天之前的数据
        List<TimeLine> timeLines = timeLineMapper.getThreeDaysData();
        for (TimelineVo timelineVo : timelineVos) {
            List<TimelineDto> timelineDtos = new ArrayList<>();
            for (TimeLine timeLine : timeLines) {
                TimelineDto timelineDto = new TimelineDto();
                String pubDate = timeLine.getPubDate();
                String date = pubDate.substring(0,10);
                // 只有当日期相同时，才会进行添加动作
                if (date.equals(timelineVo.getDate())){
                    timelineDto.setDate(date);
                    timelineDto.setTime(pubDate.substring(11,16));
                    timelineDto.setTitle(timeLine.getTitle());
                    timelineDto.setContent(timeLine.getSummary());
                    timelineDto.setInfoSource(timeLine.getInfoSource());
                    timelineDto.setSourceUrl(timeLine.getSourceUrl());
                    timelineDtos.add(timelineDto);
                }
            }
            timelineVo.setInfo(timelineDtos);
        }
        return timelineVos;
    }
}
