package com.smic.cf.entities.dto;

import lombok.Data;

/**
 * @Description 用来传递时间线给页面
 * @ClassName TimelineVo
 * @Author 蔡明涛
 * @Date 2020/4/6 10:16
 **/
@Data
public class TimelineDto {
    /**
     * ex. 2020-04-06
     */
    private String date;
    /**
     * ex. 09:32
     */
    private String time;
    /**
     * 时间线标题
     */
    private String title;
    /**
     * 时间线主要描述内容
     */
    private String content;
    /**
     * 信息来源
     */
    private String infoSource;
    /**
     * 信息来源地址
     */
    private String sourceUrl;
}
