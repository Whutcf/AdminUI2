package com.smic.cf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smic.cf.pojo.ForeignCountryCovid19;
import com.smic.cf.service.ForeignCountryService;
import com.smic.cf.util.ResultBean;
import com.smic.cf.util.ResultBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 手动刷新获取爬虫信息
 * @ClassName CrawlerController
 * @Author 蔡明涛
 * @date 2020.03.01 12:15
 */
@RestController
@Slf4j
@RequestMapping("/crawler")
public class CrawlerController {

    @Resource
    private ForeignCountryService foreignCountryService;

    // todo: 2020/3/1 21:06 蔡明涛  考虑做个手动触发爬虫的页面

    /**
     * 获取所有全球疫情数据
     * @param page 起始页
     * @param limit 页面显示条数
     * @return com.smic.cf.util.ResultBean<com.alibaba.fastjson.JSONObject>
     * @author 蔡明涛
     * @date 2020/3/29 19:51
     */
    @PostMapping("/getForeignStatistics")
    public ResultBean<JSONObject> getForeignStatistics(@RequestParam("page") Integer page,
                                                       @RequestParam("limit") Integer limit,
                                                       @RequestParam("continents") String continents,
                                                       @RequestParam("provinceName") String provinceName){
        JSONObject jsonObject = new JSONObject();
        IPage<ForeignCountryCovid19> iPage =  foreignCountryService.selectPage(page,limit,continents,provinceName);
        jsonObject.put("total",iPage.getTotal());
        jsonObject.put("rows",iPage.getRecords());
        return ResultBeanUtil.success(jsonObject);
    }

    /**
     * 获取大洲下拉框的值
     * @return com.smic.cf.util.ResultBean<java.util.List<java.lang.String>>
     * @author 蔡明涛
     * @date 2020/3/29 19:55
     */
    @GetMapping("/getContinents")
    public ResultBean<JSONArray> getContinents(){
        List<String> continents = foreignCountryService.getContinents();
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(continents));
        return ResultBeanUtil.success(jsonArray);
    }

    /**
     * 获取大洲下拉框的值
     * @return com.smic.cf.util.ResultBean<java.util.List<java.lang.String>>
     * @author 蔡明涛
     * @date 2020/3/29 19:55
     */
    @GetMapping("/getCountries")
    public ResultBean<JSONArray> getCountries(@RequestParam("continents") String continents){
        List<String> counties = foreignCountryService.getCountries(continents);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(counties));
        return ResultBeanUtil.success(jsonArray);
    }

}
