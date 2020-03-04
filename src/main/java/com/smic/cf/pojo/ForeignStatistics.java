package com.smic.cf.pojo;

import lombok.Data;

/**
 * @Description 国外总记录
 * @ClassName ForeignStatistics
 * @Author 蔡明涛
 * @date 2020.03.01 11:42
 */
@Data
public class ForeignStatistics {
    private int currentConfirmedCount;
    private int confirmedCount;
    private int suspectedCount;
    private int curedCount;
    private int deadCount;
    private int suspectedIncr;
    private int currentConfirmedIncr;
    private int confirmedIncr;
    private int curedIncr;
    private int deadIncr;
    private String updateTime;
    private String countRemark;
}
