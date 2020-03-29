package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @Description 外国疫情实体类
 * @ClassName ForeignCountry
 * @Author 蔡明涛
 * @date 2020.03.01 22:38
 */
@Data
public class ForeignCountryCovid19 implements Serializable {
    private static final long serialVersionUID = 6444643212586251031L;
    @TableId(value = "location_id",type = IdType.INPUT)
    private int locationId;
    private String createTime;
    private String modifyTime;
    private String tags;
    private String continents;
    private String provinceName;
    private int currentConfirmedCount;
    private int confirmedCount;
    private int confirmedCountRank;
    private int suspectedCount;
    private int curedCount;
    private int deadCount;
    private int deadCountRank;
    private String deadRate;
    private int deadRateRank;
    private String comment;
    private int sort;
    private String countryShortCode;
    private String countryFullName;
    private String statisticsData;
    private boolean showRank;
    @TableField(exist = false)
    private IncrVo incrVo;
    @TableField(exist = false)
    private List<ForeignStatisticsTrendChartData> statisticsTrendChartDataList;
}
