package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
                        foreignCountryCovid19.setDeadRate(foreignCountryCovid19.getDeadRate() + "%");
                        foreignCountryCovid19Mapper.updateById(foreignCountryCovid19);
                    } else {
                        foreignCountryCovid19.setDeadRate(foreignCountryCovid19.getDeadRate() + "%");
                        foreignCountryCovid19Mapper.insert(foreignCountryCovid19);
                    }
                } else {
                    // 需要对0号区域特殊处理
                    LambdaQueryWrapper<ForeignCountryCovid19> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(ForeignCountryCovid19::getLocationId, 0)
                            .eq(!StringUtils.isEmpty(foreignCountryCovid19.getCountryShortCode()), ForeignCountryCovid19::getCountryShortCode, foreignCountryCovid19.getCountryShortCode())
                            .eq(!StringUtils.isEmpty(foreignCountryCovid19.getContinents()), ForeignCountryCovid19::getContinents, foreignCountryCovid19.getContinents())
                            .eq(!StringUtils.isEmpty(foreignCountryCovid19.getCountryFullName()), ForeignCountryCovid19::getCountryFullName, foreignCountryCovid19.getCountryFullName());
                    ForeignCountryCovid19 foreignCountryCovid19Old = foreignCountryCovid19Mapper.selectOne(queryWrapper);
                    if (StringUtils.isEmpty(foreignCountryCovid19Old)) {
                        foreignCountryCovid19.setDeadRate(foreignCountryCovid19.getDeadRate() + "%");
                        foreignCountryCovid19Mapper.insert(foreignCountryCovid19);
                    } else {
                        if (!foreignCountryCovid19Old.equals(foreignCountryCovid19)) {
                            foreignCountryCovid19.setDeadRate(foreignCountryCovid19.getDeadRate() + "%");
                            foreignCountryCovid19Mapper.update(foreignCountryCovid19, queryWrapper);
                        }
                    }
                }
                // 更新每日新增数据,需要对0特殊处理
                IncrVo incrVo = foreignCountryCovid19.getIncrVo();
                if (incrVo.getId() != 0) {
                    incrVoMapper.deleteById(incrVo.getId());
                } else {
                    LambdaQueryWrapper<IncrVo> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(IncrVo::getId, 0)
                            .eq(IncrVo::getCountryShortCode, incrVo.getCountryShortCode());
                    if (!incrVo.getCountryShortCode().equals("CNMI") && !StringUtils.isEmpty(incrVoMapper.selectOne(queryWrapper))) {
                        incrVoMapper.delete(queryWrapper);
                    }
                }
                incrVoMapper.insert(incrVo);
                // 更新历史数据
                List<ForeignStatisticsTrendChartData> statisticsTrendChartDataList = foreignCountryCovid19.getStatisticsTrendChartDataList();
                if (null != statisticsTrendChartDataList && statisticsTrendChartDataList.size() > 0) {
                    for (ForeignStatisticsTrendChartData foreignStatisticsTrendChartData : statisticsTrendChartDataList) {
                        LambdaQueryWrapper<ForeignStatisticsTrendChartData> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(ForeignStatisticsTrendChartData::getLocationId, foreignStatisticsTrendChartData.getLocationId())
                                .eq(ForeignStatisticsTrendChartData::getCountryShortCode, foreignStatisticsTrendChartData.getCountryShortCode())
                                .eq(ForeignStatisticsTrendChartData::getDateId, foreignStatisticsTrendChartData.getDateId());
                        ForeignStatisticsTrendChartData trendChartDataNew = foreignStatisticTrendChartDataMapper.selectOne(queryWrapper);
                        if (StringUtils.isEmpty(trendChartDataNew)) {
                            foreignStatisticTrendChartDataMapper.insert(foreignStatisticsTrendChartData);
                        } else {
                            if (!foreignStatisticsTrendChartData.equals(trendChartDataNew)) {
                                foreignStatisticTrendChartDataMapper.update(foreignStatisticsTrendChartData, queryWrapper);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取国外疫情数据
     *
     * @param page         当前页
     * @param limit        页面记录数
     * @param continents   大洲名
     * @param provinceName 国家名
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.smic.cf.pojo.ForeignCountryCovid19>
     * @author 蔡明涛
     * @date 2020/3/29 16:34
     */
    @Override
    public IPage<ForeignCountryCovid19> selectPage(Integer page, Integer limit, String continents, String provinceName) {
        LambdaQueryWrapper<ForeignCountryCovid19> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(!StringUtils.isEmpty(continents), ForeignCountryCovid19::getContinents, continents)
                .eq(!StringUtils.isEmpty(provinceName), ForeignCountryCovid19::getProvinceName, provinceName);
        Page<ForeignCountryCovid19> page1 = new Page<>(page, limit);
        return foreignCountryCovid19Mapper.selectPage(page1, queryWrapper);
    }

    /**
     * 获取大洲数
     *
     * @return java.util.List<java.lang.String>
     * @author 蔡明涛
     * @date 2020/3/29 19:55
     */
    @Override
    public List<String> getContinents() {
        return foreignCountryCovid19Mapper.getContinents();
    }

    /**
     * 获取大洲对应的国家
     *
     * @param continents 大洲
     * @return java.util.List<java.lang.String>
     * @author 蔡明涛
     * @date 2020/3/29 20:36
     */
    @Override
    public List<String> getCountries(String continents) {
        return foreignCountryCovid19Mapper.getCountries(continents);
    }
}
