package com.smic.cf.crawlerbaidu.dto;

/**
 * @Description 城市对象
 * @ClassName SubListBean
 * @Author 蔡明涛
 * @Date 2020/4/4 15:06
 **/
public class SubListCityBean {
    /**
     * city : 拉萨
     * cityCode : 100
     * confirmedRelative : 0
     * died :
     * curConfirm : 0
     * confirmed : 1
     * crued : 1
     */

    private String city;
    private String cityCode;
    private String confirmedRelative;
    private String died;
    private String curConfirm;
    private String confirmed;
    private String crued;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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
