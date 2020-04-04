package com.smic.cf.crawlerbaidu.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description 具体信息对象
 * @ClassName CaseListBean
 * @Author 蔡明涛
 * @Date 2020/4/4 15:22
 **/
@Data
public class CaseListBean {

    /**
     * relativeTime : 1585843200
     * area : 西藏
     * subList : [{"city":"拉萨","cityCode":"100","confirmedRelative":"0","died":"","curConfirm":"0","confirmed":"1","crued":"1"}]
     * diedRelative : 0
     * confirmedRelative : 0
     * died :
     * icuDisable : 1
     * curedRelative : 0
     * curConfirm : 0
     * curConfirmRelative : 0
     * confirmed : 1
     * crued : 1
     */

    private String relativeTime;
    private String area;
    private String diedRelative;
    private String confirmedRelative;
    private String died;
    private String icuDisable;
    private String curedRelative;
    private String curConfirm;
    private String curConfirmRelative;
    private String confirmed;
    private String crued;
    private List<SubListCityBean> subList;

}
