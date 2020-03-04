package com.smic.cf.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 通过正则解析爬虫页面的数据
 * @ClassName CrawlerUtils
 * @Author 蔡明涛
 * @date 2020.03.01 10:17
 */
public class CrawlerUtils {

    public static Document page;

    /**
     * @Description 通过JSoup获取html页面
     * @Param [url]
     * @return void
     * @Author 蔡明涛
     * @Date 2020.03.01 10:30
     **/
    public static void getPage(String url) {
        try {
            page = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description 正则获取爬虫页面
     * @Param [regex, attributeKey, attributeValue]
     * @return java.lang.String
     * @Author 蔡明涛
     * @Date 2020.03.01 10:39
     **/
    public static String getInformation(String regex ,String attributeKey, String attributeValue){
        String information = null;

        //获取表达式对象
        Pattern pattern = Pattern.compile(regex);
        //创建Matcher对象
        Elements pageInformation = page.getElementsByAttributeValue(attributeKey,attributeValue);
        Matcher matcher = pattern.matcher(pageInformation.toString());
        //该方法扫描输入的序列，查找与该模式匹配的子序列
        if (matcher.find()){
            information = matcher.group();
        }
        return information;
    }
}
