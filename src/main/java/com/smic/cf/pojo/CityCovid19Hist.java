package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Description TODO
 * @ClassName CityCovid19Hist
 * @Author 蔡明涛
 * @date 2020.03.05 00:42
 */
@Data
public class CityCovid19Hist {
    @TableId(value = "location_id",type = IdType.INPUT)
    private int locationId;
    private String cityName;
    private String provinceName;
    private String provinceShortName;
    private int currentConfirmedCount;
    private int confirmedCount;
    private int suspectedCount;
    private int curedCount;
    private int deadCount;
    private String updateTime;
}
