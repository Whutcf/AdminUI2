package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;


/**
 * @Description 省的汇总数据
 * @ClassName Province
 * @Author 蔡明涛
 * @date 2020.03.01 22:38
 */
@Data
public class ProvinceCovid19Info {
    @TableId(value = "location_id",type = IdType.INPUT)
    private int locationId;
    private String createTime;
    private String modifyTime;
    private String tags;
    private int countryType;
    private int provinceId;
    private String provinceName;
    private String provinceShortName;
    private int currentConfirmedCount;
    private int confirmedCount;
    private int suspectedCount;
    private int curedCount;
    private int deadCount;
    private String comment;
    private int sort;
    @TableField(exist = false)
    private List<CityCovid19Info> cities;

}
