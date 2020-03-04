package com.smic.cf.pojo;

/**
 * @Description 数据源URL 和 获取到的JSON数据
 * @ClassName Crawler
 * @Author 蔡明涛
 * @date 2020.03.01 09:47
 */
public class Crawler {
    /**
     * 爬虫的网址
     */
    public static final String URL = "https://3g.dxy.cn/newh5/view/pneumonia";

    /**
     * 时间线新闻
     */
    public static final String TIME_LINE_REGEX_TEMPLATE = "\\[(.*?)\\]";
    public static final String TIME_LINE_ATTRIBUTE="getTimelineService";

    /**
     * 获取各省市信息
     */
    public static final String AREA_INFORMATION_REGEX_TEMPLATE = "\\[(.*)\\]";
    public static final String AREA_INFORMATION_ATTRIBUTE="getAreaStat";

    /**
     * 获取国内基本总数据
     */
    public static final String STATIC_INFORMATION_REGEX_TEMPLATE="\\{(\"id\".*?)\\}\\}";
    public static final String STATIC_INFORMATION_ATTRIBUTE="getStatisticsService";

    /**
     * 获取各省详细信息
     */
    public static final String PROVINCE_INFORMATION_REGEX_TEMPLATE = "\\[(.*)\\]";
    public static final String PROVINCE_INFORMATION_ATTRIBUTE = "getListByCountryTypeService1";

    /**
     * 获取总国外基本数据
     */
    public static final String FOREIGN_STATIC_INFORMATION_REGEX_TEMPLATE="\\[(.*)\\]";
    public static final String FOREIGN_STATIC_INFORMATION_ATTRIBUTE="getListByCountryTypeService2";

}
