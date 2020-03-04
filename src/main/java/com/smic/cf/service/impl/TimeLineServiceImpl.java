package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smic.cf.pojo.TimeLine;
import com.smic.cf.mapper.master.TimeLineMapper;
import com.smic.cf.service.TimeLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @ClassName TimeLineServiceImpl
 * @Author 蔡明涛
 * @date 2020.03.01 19:44
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TimeLineServiceImpl extends ServiceImpl<TimeLineMapper,TimeLine> implements TimeLineService {

    @Resource
    private TimeLineMapper timeLineMapper;

    @Override
    public String insertTimeLine(List<TimeLine> timeLines) {
        StringBuilder timeLineNews = new StringBuilder();
        for (TimeLine timeLine : timeLines) {
            TimeLine oldTimeLine = timeLineMapper.selectById(timeLine.getId());
            if (StringUtils.isEmpty(oldTimeLine)){
                timeLineMapper.insert(timeLine);
                log.debug("当前插入的对象是{}",timeLine.toString());
                timeLineNews.append(timeLine.getProvinceName()).append("<br>").append(timeLine.getTitle()).append("<br>").append(timeLine.getSummary()).append("<br><br>");
                log.debug("{}",timeLineNews);
            }else {
                timeLineMapper.updateById(timeLine);
                log.debug("更新当前TimeLine的值为{}",timeLine.toString());
                timeLineNews.append(timeLine.getProvinceName()).append("<br>").append(timeLine.getTitle()).append("<br>").append(timeLine.getSummary()).append("<br><br>");
            }
        }
        // todo: 2020/3/1 20:40 蔡明涛 this.saveBatch(timeLines) 的方法执行报错，有时间需要确认一下为啥出现主键冲突的错
        return timeLineNews.toString();
    }
}
