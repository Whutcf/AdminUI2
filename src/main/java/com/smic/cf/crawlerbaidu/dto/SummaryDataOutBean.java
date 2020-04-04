package com.smic.cf.crawlerbaidu.dto;

import lombok.Data;

/**
 * @Description 对外汇总数据
 * @ClassName SummaryDataOutBean
 * @Author 蔡明涛
 * @Date 2020/4/4 15:39
 **/
@Data
public class SummaryDataOutBean {

    /**
     * relativeTime : 1585843200
     * diedRelative : 4789
     * cured : 149973
     * confirmedRelative : 75775
     * died : 55693
     * curConfirm : 813960
     * curedRelative : 12888
     * curConfirmRelative : 58098
     * confirmed : 1019626
     */

    private String relativeTime;
    private String diedRelative;
    private String cured;
    private String confirmedRelative;
    private String died;
    private String curConfirm;
    private String curedRelative;
    private String curConfirmRelative;
    private String confirmed;
}
