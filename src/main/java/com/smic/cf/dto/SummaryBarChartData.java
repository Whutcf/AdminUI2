package com.smic.cf.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 按照大洲计算得到的汇总数据实体
 * @ClassName SummaryBarChartData
 * @Author 蔡明涛
 * @Date 2020/4/3 0:19
 **/
@Data
public class SummaryBarChartData implements Serializable {
    private static final long serialVersionUID = 4067263096585521675L;
    private String continents;
    private int currentConfirmedTotal;
    private int confirmedTotal;
    private int deadTotal;
    private int cureTotal;
}
