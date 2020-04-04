package com.smic.cf.crawlerbaidu.dto;

/**
 * @Description 喇叭提示信息内容
 * @ClassName TrumpetBean
 * @Author 蔡明涛
 * @Date 2020/4/4 14:59
 **/
public class TrumpetBean {

    /**
     * title : 昨日境外输入18例，本土1例，港澳台54例
     * content : 4月3日0—24时，31个省（自治区、直辖市）和新疆生产建设兵团报告新增确诊病例19例，其中18例为境外输入病例，1例为本土病例（湖北1例）；新增死亡病例4例（湖北4例）；新增疑似病例11例，均为境外输入病例。
     累计收到港澳台地区通报确诊病例1236例：香港特别行政区845例（出院173例，死亡4例），澳门特别行政区43例（出院10例），台湾地区348例（出院50例，死亡5例）。
     */

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
