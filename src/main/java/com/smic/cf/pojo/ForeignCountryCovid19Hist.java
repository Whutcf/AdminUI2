package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * @Description 外国疫情历史实体类
 * @ClassName ForeignCountry
 * @Author 蔡明涛
 * @date 2020.03.01 22:38
 */
@Data
public class ForeignCountryCovid19Hist {
    @TableId(value = "location_id",type = IdType.INPUT)
    private int locationId;
    private String createTime;
    private String modifyTime;
    private String continents;
    private String countryName;
    private int currentConfirmedCount;
    private int confirmedCount;
    private int suspectedCount;
    private int curedCount;
    private int deadCount;
    private String countryShortCode;
}
