package com.smic.cf.crawlerbaidu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 用来存储搜集的各区域的历史数据
 * @ClassName Covid19TrendHist
 * @Author 蔡明涛
 * @Date 2020/4/4 17:38
 **/
@Data
public class Covid19TrendHist implements Serializable {

    private static final long serialVersionUID = 8911006298488107978L;
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 区域名称：省、市、区、国家
     */
    private String name;
    /**
     * 日期：比如 2.7
     */
    private String date;
    /**
     * 系列名称：死亡、确诊、新增确诊、治愈等
     */
    private String seriesName;
    /**
     * 具体的数值
     */
    private Integer value;
}
