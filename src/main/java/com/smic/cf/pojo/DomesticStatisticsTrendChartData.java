package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 国内省份疫情历史数据
 * @ClassName DomesticStatisticsTrendChartData
 * @Author 蔡明涛
 * @Date 2020/3/28 10:59
 **/
@Data
public class DomesticStatisticsTrendChartData implements Serializable {
    private static final long serialVersionUID = 7325718523565877781L;
    @TableId(value = "location_id",type = IdType.INPUT)
    private int locationId;
    private String provinceName;
    private String dateId;
    private int confirmedCount;
    private int confirmedIncr;
    private int curedCount;
    private int curedIncr;
    private int currentConfirmedCount;
    private int currentConfirmedIncr;
    private int deadCount;
    private int deadIncr;
    private int suspectedCount;
    private int suspectedCountIncr;
}
