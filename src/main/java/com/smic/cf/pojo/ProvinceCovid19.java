package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @Description 省的汇总数据
 * @ClassName Province
 * @Author 蔡明涛
 * @date 2020.03.01 22:38
 */
@Data
public class ProvinceCovid19 implements Serializable {
    private static final long serialVersionUID = 2104519898102137592L;
    @TableId(value = "location_id",type = IdType.INPUT)
    private int locationId;
    private String provinceName;
    private String provinceShortName;
    private int currentConfirmedCount;
    private int confirmedCount;
    private int suspectedCount;
    private int curedCount;
    private int deadCount;
    private String comment;
    private String statisticsData;
    @TableField(exist = false)
    private List<CityCovid19> cities;
    @TableField(exist = false)
    private List<DomesticStatisticsTrendChartData> domesticStatisticsTrendChartDataList;

}
