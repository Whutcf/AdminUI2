package com.smic.cf.entities.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Description 每日新增实体类，每日新增和区域绑定，方便存贮使用
 * @ClassName IncrVo
 * @Author 蔡明涛
 * @Date 2020/3/28 10:09
 **/
@Data
public class IncrVo {
    @TableId(value = "id",type = IdType.INPUT)
    private int id;
    private String countryShortCode;
    private int currentConfirmedIncr;
    private int confirmedIncr;
    private int curedIncr;
    private int deadIncr;
}
