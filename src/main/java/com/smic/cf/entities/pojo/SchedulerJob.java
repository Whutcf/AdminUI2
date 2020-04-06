package com.smic.cf.entities.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Description job实体类
 * @ClassName SchedulerJob
 * @Author 蔡明涛
 * @Date 2020/3/11 20:09
 **/
@Data
public class SchedulerJob {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String jobName;

    private String cronExpression;

    private String beanName;

    private String methodName;

    /**
     * 状态 1.启动 2.暂停
     */
    @TableField(fill = FieldFill.INSERT)
    private int status;

    /**
     * 是否删除 0.否 1.是
     */
    @TableField(fill = FieldFill.INSERT)
    private int deleteFlag;

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 修改人
     */
    private Integer updaterName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createdTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updatedTime;

}
