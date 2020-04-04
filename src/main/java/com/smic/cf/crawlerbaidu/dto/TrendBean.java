package com.smic.cf.crawlerbaidu.dto;

import java.util.List;

/**
 * @Description trend数据集合对象
 * @ClassName Trend
 * @Author 蔡明涛
 * @Date 2020/4/4 14:39
 **/
public class TrendBean {

    private List<String> updateDate;
    private List<ListBean> list;

    public List<String> getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(List<String> updateDate) {
        this.updateDate = updateDate;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

}
