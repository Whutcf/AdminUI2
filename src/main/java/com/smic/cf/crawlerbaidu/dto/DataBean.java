package com.smic.cf.crawlerbaidu.dto;

/**
 * @Description 存储非集合的data
 * @ClassName DataBean
 * @Author 蔡明涛
 * @Date 2020/4/4 14:53
 **/
public class DataBean {

    /**
     * iconSrc : https://mms-res.cdn.bcebos.com/mms-res/voicefe/captain/images/ea36e628ea60507f5d928abc1ed0da87.png?size=90*90
     * title : 同乘查询
     * url : https://u1qa5f.smartapps.cn/pages/epidemic/index
     */

    private String iconSrc;
    private String title;
    private String url;

    public String getIconSrc() {
        return iconSrc;
    }

    public void setIconSrc(String iconSrc) {
        this.iconSrc = iconSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
