package com.smic.cf.crawlerbaidu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 链接到百度查询谣言，常识和热词
 * @ClassName BaiDuQueryInfo
 * @Author 蔡明涛
 * @Date 2020/4/19 11:18
 **/
@Data
public class Covid19BaiduQueryInfo implements Serializable {
    private static final long serialVersionUID = 5383077735637049297L;
    @TableId(value = "degree",type = IdType.INPUT)
    private String degree;
    private String query;
    /**
     * 0:常识 1&2：热词 7：谣言 4:废弃
     */
    private String type;
    private String url;
}
