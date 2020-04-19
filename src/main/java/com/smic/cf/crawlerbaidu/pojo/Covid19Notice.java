package com.smic.cf.crawlerbaidu.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 公告
 * @ClassName Notice
 * @Author 蔡明涛
 * @Date 2020/4/19 11:12
 **/
@Data
public class Covid19Notice implements Serializable {
    private static final long serialVersionUID = 2547596676415126591L;
    private String title;
    private String content;
    private String date;
}
