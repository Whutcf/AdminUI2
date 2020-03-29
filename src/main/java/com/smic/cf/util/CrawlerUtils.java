package com.smic.cf.util;

import org.jsoup.Connection;
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

    /**
     * 获取url中所需要的数据
     * @param regex 正则表达式
     * @param jsonDataString 解析的json文件数据
     * @return java.lang.String
     * @author 蔡明涛
     * @date 2020/3/28 18:05
     */
    public static String getJsonInformation(String regex,String jsonDataString){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jsonDataString);
        if (matcher.find()){
            return matcher.group();
        }else {
            return null;
        }
    }

    /**
     * 获取json文件并转化成字符串
     * @param url 网页地址
     * @return java.lang.String
     * @author 蔡明涛
     * @date 2020/3/28 10:55
     */
    public static String getJsonStringData(String url) throws IOException {
        Connection.Response response = Jsoup.connect(url).header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                .timeout(10000).ignoreContentType(true).execute();

        return response.body();
    }
}
