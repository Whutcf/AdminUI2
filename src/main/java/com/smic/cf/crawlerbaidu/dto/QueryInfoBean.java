package com.smic.cf.crawlerbaidu.dto;

/**
 * @Description 小提示对象
 * @ClassName GossipsBean
 * @Author 蔡明涛
 * @Date 2020/4/4 15:01
 **/
public class QueryInfoBean {

    /**
     * query : 纸币会传播冠状病毒
     * degree : 85560
     * type : 7
     * url : https://m.baidu.com/s?word=纸币会传播冠状病毒&sa=osari_yaoyan
     */

    private String query;
    private String degree;
    private String type;
    private String url;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
