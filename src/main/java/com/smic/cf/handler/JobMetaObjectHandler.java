package com.smic.cf.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.smic.cf.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

/**
 * @Description mybatis-plus 的自动填充组件，结合TableFiled(fill = FieldFill.INSERT)一起使用，同时配置类也要提供相关配置
 * @ClassName jobMetaObjectHandler
 * @Author 蔡明涛
 * @Date 2020/3/11 20:24
 **/
@Slf4j
public class JobMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("新增Job自动填充！");
        this.setFieldValByName("status",0,metaObject);
        this.setFieldValByName("deleteFlag",0,metaObject);
        this.setFieldValByName("createTime", DateUtils.getCurrentDateTime(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("更新Job自动填充！");
        this.setFieldValByName("updateTime",DateUtils.getCurrentDateTime(),metaObject);
    }
}
