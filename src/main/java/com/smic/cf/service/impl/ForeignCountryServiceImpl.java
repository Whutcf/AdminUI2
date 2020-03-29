package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.smic.cf.mapper.ForeignCountryCovid19Mapper;
import com.smic.cf.mapper.ForeignStatisticTrendChartDataMapper;
import com.smic.cf.mapper.IncrVoMapper;
import com.smic.cf.pojo.ForeignCountryCovid19;
import com.smic.cf.pojo.ForeignStatisticsTrendChartData;
import com.smic.cf.pojo.IncrVo;
import com.smic.cf.service.ForeignCountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @ClassName ForeignCountryServiceImpl
 * @Author 蔡明涛
 * @date 2020.03.02 20:01
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ForeignCountryServiceImpl implements ForeignCountryService {

    @Resource
    private ForeignCountryCovid19Mapper foreignCountryCovid19Mapper;
    @Resource
    private ForeignStatisticTrendChartDataMapper foreignStatisticTrendChartDataMapper;
    @Resource
    private IncrVoMapper incrVoMapper;


    /**
     * 将国外数据存入数据库
     *
     * @param foreignCountryCovid19List 国外实体数据集合
     * @author 蔡明涛
     * @date 2020.03.28 19:50
     **/
    @Override
    public void insertForeignCountryData(List<ForeignCountryCovid19> foreignCountryCovid19List) {
        if (null != foreignCountryCovid19List && foreignCountryCovid19List.size() > 0) {
            for (ForeignCountryCovid19 foreignCountryCovid19 : foreignCountryCovid19List) {
                // 保存或更新当前区域数据
                if (foreignCountryCovid19.getLocationId() != 0) {
                    if (!StringUtils.isEmpty(foreignCountryCovid19Mapper.selectById(foreignCountryCovid19.getLocationId()))) {
                        foreignCountryCovid19Mapper.updateById(foreignCountryCovid19);
                    } else {
                        foreignCountryCovid19Mapper.insert(foreignCountryCovid19);
                    }
                } else {
                    // 需要对0号区域特殊处理
                    LambdaQueryWrapper<ForeignCountryCovid19> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(ForeignCountryCovid19::getLocationId, 0)
                            .eq(!StringUtils.isEmpty(foreignCountryCovid19.getCountryShortCode()), ForeignCountryCovid19::getCountryShortCode, foreignCountryCovid19.getCountryShortCode())
                            .eq(!StringUtils.isEmpty(foreignCountryCovid19.getContinents()), ForeignCountryCovid19::getContinents, foreignCountryCovid19.getContinents())
                            .eq(!StringUtils.isEmpty(foreignCountryCovid19.getCountryFullName()), ForeignCountryCovid19::getCountryFullName, foreignCountryCovid19.getCountryFullName());
                    if (!StringUtils.isEmpty(foreignCountryCovid19Mapper.selectOne(queryWrapper))) {
                        foreignCountryCovid19Mapper.delete(queryWrapper);
                    }
                    foreignCountryCovid19Mapper.insert(foreignCountryCovid19);
                }
                // 更新每日新增数据,需要对0特殊处理
                IncrVo incrVo = foreignCountryCovid19.getIncrVo();
                if (incrVo.getId() != 0) {
                    incrVoMapper.deleteById(incrVo.getId());
                } else {
                    LambdaQueryWrapper<IncrVo> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(IncrVo::getId, 0)
                            .eq(IncrVo::getCountryShortCode, incrVo.getCountryShortCode());
                    if (!StringUtils.isEmpty(incrVoMapper.selectOne(queryWrapper))) {
                        incrVoMapper.delete(queryWrapper);
                    }
                }
                incrVoMapper.insert(incrVo);
                // 更新历史数据
                List<ForeignStatisticsTrendChartData> statisticsTrendChartDataList = foreignCountryCovid19.getStatisticsTrendChartDataList();
                if (null != statisticsTrendChartDataList && statisticsTrendChartDataList.size() > 0) {
                    for (ForeignStatisticsTrendChartData foreignStatisticsTrendChartData : statisticsTrendChartDataList) {
                        ForeignStatisticsTrendChartData trendChartData = new LambdaQueryChainWrapper<>(foreignStatisticTrendChartDataMapper)
                                .eq(ForeignStatisticsTrendChartData::getLocationId, foreignStatisticsTrendChartData.getLocationId())
                                .eq(ForeignStatisticsTrendChartData::getCountryShortCode, foreignStatisticsTrendChartData.getCountryShortCode())
                                .eq(ForeignStatisticsTrendChartData::getDateId, foreignStatisticsTrendChartData.getDateId()).one();
                        if (StringUtils.isEmpty(trendChartData)) {
                            foreignStatisticTrendChartDataMapper.insert(foreignStatisticsTrendChartData);
                        }
                    }
                }
            }
        }
    }
}
