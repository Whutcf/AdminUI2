package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 城市疫情信息实体
 * @ClassName City
 * @Author 蔡明涛
 * @date 2020.03.01 22:48
 */
@Data
public class CityCovid19 implements Serializable {
    private static final long serialVersionUID = 4780783863029009295L;
    @TableId(value = "location_id",type = IdType.INPUT)
    private int locationId;
    private String cityName;
    private int provinceId;
    private String provinceShortName;
    private int currentConfirmedCount;
    private int confirmedCount;
    private int suspectedCount;
    private int curedCount;
    private int deadCount;
}
