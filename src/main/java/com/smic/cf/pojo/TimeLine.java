package com.smic.cf.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 时间线对象
 * @ClassName TimeLine
 * @Author 蔡明涛
 * @date 2020.03.01 19:19
 */
@Data
public class TimeLine implements Serializable {
   @TableId(value = "id", type = IdType.INPUT)
   private int id;
   private String pubDate;
   private String pubDateStr;
   private String title;
   private String summary;
   private String infoSource;
   private String sourceUrl;
   private String provinceId;
   private String provinceName;
   private String createTime;
   private String modifyTime;
   private String dataInfoTime;
}
