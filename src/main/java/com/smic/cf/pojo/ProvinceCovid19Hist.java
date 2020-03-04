package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description TODO
 * @ClassName ProvinceCovid19Hist
 * @Author 蔡明涛
 * @date 2020.03.04 23:41
 */
@Data
public class ProvinceCovid19Hist implements Serializable {
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
}
