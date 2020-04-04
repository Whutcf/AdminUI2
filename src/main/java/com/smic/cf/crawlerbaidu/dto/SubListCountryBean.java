package com.smic.cf.crawlerbaidu.dto;

/**
 * @Description 国家数据对象
 * @ClassName SubListCountryBean
 * @Author 蔡明涛
 * @Date 2020/4/4 15:10
 **/
public class SubListCountryBean {

    /**
     * relativeTime : 1585584000
     * country : 老挝
     * confirmedRelative :
     * died :
     * curConfirm : 10
     * confirmed : 10
     * crued :
     */

    private Long relativeTime;
    private String country;
    private String confirmedRelative;
    private String died;
    private String curConfirm;
    private String confirmed;
    private String crued;

    public Long getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(Long relativeTime) {
        this.relativeTime = relativeTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getConfirmedRelative() {
        return confirmedRelative;
    }

    public void setConfirmedRelative(String confirmedRelative) {
        this.confirmedRelative = confirmedRelative;
    }

    public String getDied() {
        return died;
    }

    public void setDied(String died) {
        this.died = died;
    }

    public String getCurConfirm() {
        return curConfirm;
    }

    public void setCurConfirm(String curConfirm) {
        this.curConfirm = curConfirm;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getCrued() {
        return crued;
    }

    public void setCrued(String crued) {
        this.crued = crued;
    }
}
