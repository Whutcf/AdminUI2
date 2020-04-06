package com.smic.cf.entities.pojo;

/**
 * @Description 数据源URL 和 获取到的JSON数据
 * @ClassName Crawler
 * @Author 蔡明涛
 * @date 2020.03.01 09:47
 */
public class Crawler {
    /**
     * 爬虫的网址 丁香医生
     */
    public static final String URL1 = "https://3g.dxy.cn/newh5/view/pneumonia";
    /**
     * 爬虫的网址 百度
     */
    public static final String URL2 = "https://voice.baidu.com/act/newpneumonia/newpneumonia?city=江苏-江苏";

    /**
     * 页面的ID选择器数据的id
     */
    public static final String ID = "id";

    /**
     * 获取国外信息汇总
     */
    public static final String FOREIGN_STATIC_INFORMATION_REGEX_TEMPLATE="\\[\\{.*\\}\\]";
    public static final String FOREIGN_STATIC_INFORMATION_ATTRIBUTE="getListByCountryTypeService2true";

    /**
     * 解析json文件转成的string
     */
    public static final String JSON_REGEX_TEMPLATE = "\\[\\{.*\\}\\]";

    /**
     * 获取国内信息
     */
    public static final String DOMESTIC_STATIC_INFORMATION_REGEX_TEMPLATE = "\\[\\{.*\\}\\]\\}\\]";
    public static final String DOMESTIC_STATIC_INFORMATION_ATTRIBUTE="getAreaStat";


    /**
     * 时间线新闻
     */
    public static final String TIME_LINE_REGEX_TEMPLATE = "\\[\\{.*\\}\\]";
    public static final String DOMESTIC_TIME_LINE_ATTRIBUTE="getTimelineService1";

    public static final String BAIDU_DATA_REGEX_TEMPLATE = "\\\"component\\\".*\\}\\]\\}\\]";
    public static final String BAIDU_DATA_ATTRIBUTE = "captain-config";
}
