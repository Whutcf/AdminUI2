package com.smic.cf.entities.vo;

import lombok.Data;

/**
 * @Description
 * @ClassName DomesticSummaryVo
 * @Author 蔡明涛
 * @Date 2020/4/6 21:12
 **/
@Data
public class DomesticSummaryVo {
    private int currentConfirmedCount;
    private int currentConfirmedIncr;
    private int confirmedCount;
    private int confirmedIncr;
    private int foreignInCount;
    private int foreignInIncr;
    private int suspectedCount;
    private int suspectedCountIncr;
    private int curedCount;
    private int curedIncr;
    private int deadCount;
    private int deadIncr;
}
