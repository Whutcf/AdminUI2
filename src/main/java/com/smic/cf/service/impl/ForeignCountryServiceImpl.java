package com.smic.cf.service.impl;

import com.smic.cf.mapper.master.ForeignCountryCovidHistMapper;
import com.smic.cf.mapper.master.ForeignCountryCovidInfoMapper;
import com.smic.cf.pojo.ForeignCountryCovid19Hist;
import com.smic.cf.pojo.ForeignCountryCovid19Info;
import com.smic.cf.service.ForeignCountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @ClassName ForeignCountryServiceImpl
 * @Author 蔡明涛
 * @date 2020.03.02 20:01
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ForeignCountryServiceImpl implements ForeignCountryService {

    @Resource
    private ForeignCountryCovidInfoMapper foreignCountryCovidInfoMapper;
    @Resource
    private ForeignCountryCovidHistMapper foreignCountryCovidHistMapper;

    @Override
    public String insertForeignCountryData(List<ForeignCountryCovid19Info> foreignCountryCovid19InfoList) {
        StringBuilder foreignCountryNews = new StringBuilder();
        ForeignCountryCovid19Hist foreignCountryCovid19Hist = new ForeignCountryCovid19Hist();
        for (ForeignCountryCovid19Info foreignCountryCovid19Info : foreignCountryCovid19InfoList) {
            ForeignCountryCovid19Info oldForeignCountryCovid19Info = foreignCountryCovidInfoMapper.selectById(foreignCountryCovid19Info.getLocationId());
            if (StringUtils.isEmpty(oldForeignCountryCovid19Info)){
                foreignCountryCovidInfoMapper.insert(foreignCountryCovid19Info);
                log.debug("新增{}的汇总信息!", foreignCountryCovid19Info.getProvinceName());
                //复制信息到历史表
                setHistData(foreignCountryCovid19Hist,foreignCountryCovid19Info);
                foreignCountryCovidHistMapper.insert(foreignCountryCovid19Hist);
                foreignCountryNews.append(foreignCountryCovid19Info.getProvinceName()).append("累计确诊：").append(foreignCountryCovid19Info.getConfirmedCount()).append("例，死亡：")
                        .append(foreignCountryCovid19Info.getDeadCount()).append("例，治愈：").append(foreignCountryCovid19Info.getCuredCount()).append("例！<br>");
            }else {
                if (oldForeignCountryCovid19Info.equals(foreignCountryCovid19Info)){
                    log.debug("{}已经是最新数据!", foreignCountryCovid19Info.getProvinceName());
                }else {
                    foreignCountryCovidInfoMapper.updateById(foreignCountryCovid19Info);
                    log.debug("更新{}的汇总信息!", foreignCountryCovid19Info.getProvinceName());
                    //复制信息到历史表
                    setHistData(foreignCountryCovid19Hist,foreignCountryCovid19Info);
                    foreignCountryCovidHistMapper.insert(foreignCountryCovid19Hist);
                    foreignCountryNews.append(foreignCountryCovid19Info.getProvinceName()).append("累计确诊：").append(foreignCountryCovid19Info.getConfirmedCount()).append("例，死亡：")
                            .append(foreignCountryCovid19Info.getDeadCount()).append("例，治愈：").append(foreignCountryCovid19Info.getCuredCount()).append("例！<br>");
                }
            }
        }
        return foreignCountryNews.toString();
    }

    /**
     * 将INFO的属性赋值到HIST中
     * @param foreignCountryCovid19Hist, foreignCountryCovid19Info
     * @return void
     * @author 蔡明涛
     * @date 2020.03.04 00:25
     **/
    private void setHistData(ForeignCountryCovid19Hist foreignCountryCovid19Hist, ForeignCountryCovid19Info foreignCountryCovid19Info) {
        BeanUtils.copyProperties(foreignCountryCovid19Info,foreignCountryCovid19Hist);
        foreignCountryCovid19Hist.setCountryName(foreignCountryCovid19Info.getProvinceName());
    }
}
