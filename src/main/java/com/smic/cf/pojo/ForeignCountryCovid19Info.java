package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * @Description 外国疫情实体类
 * @ClassName ForeignCountry
 * @Author 蔡明涛
 * @date 2020.03.01 22:38
 */
@Data
public class ForeignCountryCovid19Info {
    @TableId(value = "location_id",type = IdType.INPUT)
    private int locationId;
    private String createTime;
    private String modifyTime;
    private String tags;
    private int countryType;
    private String continents;
    private int provinceId;
    private String provinceName;
    private int currentConfirmedCount;
    private int confirmedCount;
    private int suspectedCount;
    private int curedCount;
    private int deadCount;
    private String comment;
    private int sort;
    private String countryShortCode;
}
