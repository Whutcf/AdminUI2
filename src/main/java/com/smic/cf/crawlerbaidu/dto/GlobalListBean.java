package com.smic.cf.crawlerbaidu.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description 全球疫情信息对象
 * @ClassName GlobalListBean
 * @Author 蔡明涛
 * @Date 2020/4/4 15:16
 **/
@Data
public class GlobalListBean {

    /**
     * area : 亚洲
     * subList : [{"relativeTime":1585584000,"country":"老挝","confirmedRelative":"","died":"","curConfirm":"10","confirmed":"10","crued":""}]
     * confirmedRelative : 5591
     * died : 4710
     * curConfirm : 88884
     * crued : 29718
     * confirmed : 123312
     */

    private String area;
    private String confirmedRelative;
    private String died;
    private String curConfirm;
    private String crued;
    private String confirmed;
    private List<SubListCountryBean> subList;

}
