package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 跑马灯信息
 * @ClassName Marquee
 * @Author 蔡明涛
 * @date 2020.03.01 10:42
 */
@Data
public class Marquee implements Serializable {
    @TableId(value = "id",type = IdType.INPUT)
    private int id;
    private String marqueeLabel;
    private String marqueeContent;
    private String marqueeLink;
    private String createTime;
}
