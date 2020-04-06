package com.smic.cf.controller;

import com.smic.cf.service.TimeLineService;
import com.smic.cf.entities.vo.TimelineVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 处理时间线 需要传参给页面 结合thymeleaf
 * @ClassName Covid19TimelineController
 * @Author 蔡明涛
 * @Date 2020/4/6 10:01
 **/
@Controller
public class Covid19TimelineController {
    @Resource
    private TimeLineService timeLineService;

    @GetMapping("/tgls/reportForm/covid19Timeline")
    public String getTimeline(Model model){
        List<TimelineVo> timelineList = timeLineService.getTimeline();
        model.addAttribute("timelineList",timelineList);
        return "tgls/reportForm/covid19Timeline";
    }
}
