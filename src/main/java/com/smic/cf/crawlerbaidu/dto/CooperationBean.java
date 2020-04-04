package com.smic.cf.crawlerbaidu.dto;

import java.util.List;

/**
 * @Description 其他应用跳转的地址
 * @ClassName CooperationBean
 * @Author 蔡明涛
 * @Date 2020/4/4 14:55
 **/
public class CooperationBean {

    /**
     * data :
     * [{"iconSrc":"https://mms-res.cdn.bcebos.com/mms-res/voicefe/captain/images/ea36e628ea60507f5d928abc1ed0da87.png?size=90*90",
     * "title":"同乘查询",
     * "url":"https://u1qa5f.smartapps.cn/pages/epidemic/index"}]
     * name : oppo_2
     */

    private String name;
    private List<DataBean> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
