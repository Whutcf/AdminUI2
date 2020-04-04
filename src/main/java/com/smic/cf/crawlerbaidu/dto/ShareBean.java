package com.smic.cf.crawlerbaidu.dto;

/**
 * @Description 分享对象
 * @ClassName ShareBean
 * @Author 蔡明涛
 * @Date 2020/4/4 14:47
 **/
public class ShareBean {

    /**
     * icon : https://b.bdstatic.com/searchbox/image/cmsuploader/20161214/1481696369354077.png
     * title : 实时更新：新型冠状病毒肺炎疫情地图
     * type : url
     * content : 全国新型冠状病毒肺炎实时地图
     * url : http://voice.baidu.com/act/newpneumonia/newpneumonia
     */

    private String icon;
    private String title;
    private String type;
    private String content;
    private String url;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
