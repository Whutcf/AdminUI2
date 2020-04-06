package com.smic.cf.entities.vo;

import com.smic.cf.entities.dto.TimelineDto;
import lombok.Data;

import java.util.List;

/**
 * @Description 用来传递时间线给页面
 * @ClassName TimelineVo
 * @Author 蔡明涛
 * @Date 2020/4/6 10:16
 **/
@Data
public class TimelineVo {
    /**
     * ex. 2020-04-06
     */
    private String date;
    /**
     * ex. 2020-04-06
     */
    private List<TimelineDto> info;


}
