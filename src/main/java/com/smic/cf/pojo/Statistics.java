package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * @Description 基础数据信息
 * @ClassName Statics
 * @Author 蔡明涛
 * @date 2020.03.01 11:41
 */
@Data
public class Statistics {
    /**
     *  "id": 1,
     * 	"createTime": 1579537899000,
     * 	"imgUrl": "https://img1.dxycdn.com/2020/0201/450/3394153392393266839-135.png",
     * 	"dailyPics": ["https://img1.dxycdn.com/2020/0211/763/3395998884005602079-135.png", "https://img1.dxycdn.com/2020/0211/362/3395998896890788910-135.png", "https://img1.dxycdn.com/2020/0211/365/3395998905480724211-135.png", "https://img1.dxycdn.com/2020/0211/364/3395998916217859778-135.png", "https://img1.dxycdn.com/2020/0211/922/3395998929103046444-135.png", "https://img1.dxycdn.com/2020/0211/089/3395998939840182072-135.png"],
     * 	"summary": "",
     * 	"deleted": false,
     * 	"countRemark": "",
     * 	"currentConfirmedCount": 37347,
     * 	"confirmedCount": 79394,
     * 	"suspectedCount": 1418,
     * 	"curedCount": 39209,
     * 	"deadCount": 2838,
     * 	"seriousCount": 7664,
     * 	"suspectedIncr": 248,
     * 	"currentConfirmedIncr": -2664,
     * 	"confirmedIncr": 435,
     * 	"curedIncr": 3052,
     * 	"deadIncr": 47,
     * 	"seriousIncr": -288,
     * 	"remark1": "易感人群：人群普遍易感。老年人及有基础疾病者感染后病情较重，儿童及婴幼儿也有发病",
     * 	"remark2": "潜伏期：一般为 3～7 天，最长不超过 14 天，潜伏期内可能存在传染性，其中无症状病例传染性非常罕见",
     * 	"remark3": "宿主：野生动物，可能为中华菊头蝠",
     * 	"note1": "病毒：SARS-CoV-2，其导致疾病命名 COVID-19",
     * 	"note2": "传染源：新冠肺炎的患者。无症状感染者也可能成为传染源。",
     * 	"note3": "传播途径：经呼吸道飞沫、接触传播是主要的传播途径。气溶胶传播和消化道等传播途径尚待明确。",
     * 	"generalRemark": "疑似病例数来自国家卫健委数据，目前为全国数据，未分省市自治区等",
     * 	"abroadRemark": "",
     * 	"foreignStatistics": {
     * 		"currentConfirmedCount": 5739,
     * 		"confirmedCount": 6257,
     * 		"suspectedCount": 0,
     * 		"curedCount": 424,
     * 		"deadCount": 94,
     * 		"suspectedIncr": 0,
     * 		"currentConfirmedIncr": 1314,
     * 		"confirmedIncr": 1419,
     * 		"curedIncr": 87,
     * 		"deadIncr": 18
     *        }
     */
    @TableId(value = "id",type = IdType.INPUT)
    private int id;
    private String createTime;
    private String modifyTime;
    private String imgUrl;
    private String dailyPics;
    private String summary;
    private boolean deleted;
    private String countRemark;
    private int currentConfirmedCount;
    private int confirmedCount;
    private int suspectedCount;
    private int curedCount;
    private int deadCount;
    private int suspectedIncr;
    private int currentConfirmedIncr;
    private int confirmedIncr;
    private int curedIncr;
    private int deadIncr;
    private String remark1;
    private String remark2;
    private String remark3;
    private String note1;
    private String note2;
    private String note3;
    private String generalRemark;
    private String abroadRemark;
    @TableField(exist = false)
    private List<Marquee> marquee;
    @TableField(exist = false)
    private ForeignStatistics foreignStatistics;
    @TableField(exist = false)
    private List<TrendChart> quanguoTrendChart;
    @TableField(exist = false)
    private List<TrendChart> hbFeiHbTrendChart;
    @TableField(exist = false)
    private List<TrendChart> foreignTrendChart;
    @TableField(exist = false)
    private List<TrendChart> importantForeignTrendChart;
}
