package com.smic.cf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smic.cf.constants.CrawlerConstants;
import com.smic.cf.mapper.ForeignCountryCovid19Mapper;
import com.smic.cf.mapper.ForeignStatisticTrendChartDataMapper;
import com.smic.cf.mapper.IncrVoMapper;
import com.smic.cf.pojo.ForeignCountryCovid19;
import com.smic.cf.pojo.ForeignStatisticsTrendChartData;
import com.smic.cf.pojo.IncrVo;
import com.smic.cf.service.ForeignCountryService;
import com.smic.cf.util.DateUtils;
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
                    // 防止异常出现两笔以上的记录，防呆
                    List<ForeignCountryCovid19> foreignCountryCovid19Olds = foreignCountryCovid19Mapper.selectList(queryWrapper);
                    if (foreignCountryCovid19Olds.size() == 0) {
                        foreignCountryCovid19Mapper.insert(foreignCountryCovid19);
                    } else if (foreignCountryCovid19Olds.size() == 1) {
                        foreignCountryCovid19Mapper.update(foreignCountryCovid19, queryWrapper);
                    } else {
                        foreignCountryCovid19Mapper.delete(queryWrapper);
                        foreignCountryCovid19Mapper.insert(foreignCountryCovid19);
                    }
                }
                // 更新每日新增数据,需要对0特殊处理
                IncrVo incrVo = foreignCountryCovid19.getIncrVo();
                if (!StringUtils.isEmpty(incrVo)){
                    if (incrVo.getId() != 0) {
                        incrVoMapper.deleteById(incrVo.getId());
                    } else {
                        LambdaQueryWrapper<IncrVo> queryWrapper = Wrappers.lambdaQuery();
                        queryWrapper.eq(IncrVo::getId, 0)
                                .eq(IncrVo::getCountryShortCode, incrVo.getCountryShortCode());
                        if (incrVoMapper.selectList(queryWrapper).size()>0) {
                            incrVoMapper.delete(queryWrapper);
                        }
                    }
                    incrVoMapper.insert(incrVo);
                }
                // 更新历史数据
                List<ForeignStatisticsTrendChartData> statisticsTrendChartDataList = foreignCountryCovid19.getStatisticsTrendChartDataList();
                if (null != statisticsTrendChartDataList && statisticsTrendChartDataList.size() > 0) {
                    for (ForeignStatisticsTrendChartData foreignStatisticsTrendChartData : statisticsTrendChartDataList) {
                        // 历史数据只刷新最近两天的数据
                        int dataId = Integer.parseInt(foreignStatisticsTrendChartData.getDateId());
                        int planDateId = (Integer.parseInt(DateUtils.getCurrentDateWithNumber())-1);
                        if (dataId >= planDateId){
                            LambdaQueryWrapper<ForeignStatisticsTrendChartData> queryWrapper = new LambdaQueryWrapper<>();
                            queryWrapper.eq(ForeignStatisticsTrendChartData::getLocationId, foreignStatisticsTrendChartData.getLocationId())
                                    .eq(ForeignStatisticsTrendChartData::getCountryShortCode, foreignStatisticsTrendChartData.getCountryShortCode())
                                    .eq(ForeignStatisticsTrendChartData::getDateId, foreignStatisticsTrendChartData.getDateId());
                            List<ForeignStatisticsTrendChartData> trendChartData = foreignStatisticTrendChartDataMapper.selectList(queryWrapper);
                            // 总是会莫名出现重复选项，所以选择使用selectList
                            if (trendChartData.size() == 0) {
                                foreignStatisticTrendChartDataMapper.insert(foreignStatisticsTrendChartData);
                            } else if (trendChartData.size() == 1) {
                                foreignStatisticTrendChartDataMapper.update(foreignStatisticsTrendChartData, queryWrapper);
                            } else {
                                foreignStatisticTrendChartDataMapper.selectById(foreignStatisticsTrendChartData.getLocationId());
                                foreignStatisticTrendChartDataMapper.insert(foreignStatisticsTrendChartData);
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
     * @param field        排序的栏位
     * @param order        排序的顺序 ASE，null，DESC
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.smic.cf.pojo.ForeignCountryCovid19>
     * @author 蔡明涛
     * @date 2020/3/29 16:34
     */
    @Override
    public IPage<ForeignCountryCovid19> selectPage(Integer page, Integer limit, String continents, String provinceName, String field, String order) {
        QueryWrapper<ForeignCountryCovid19> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(continents), "continents", continents)
                .eq(!StringUtils.isEmpty(provinceName), "province_name", provinceName);
        if (null != field) {
            if (CrawlerConstants.FIELD_DEAD_RATE.equals(field)){
                if (order == null || CrawlerConstants.ORDER_BY_ASC.equals(order)) {
                    queryWrapper.orderByAsc("dead_rate+0");
                } else {
                    queryWrapper.orderByDesc("dead_rate+0");
                }
            } else {
                if (order == null || CrawlerConstants.ORDER_BY_ASC.equals(order)) {
                    if (CrawlerConstants.FIELD_CURRENT_CONFIRMED_COUNT.equals(field)){
                        queryWrapper.orderByAsc("current_confirmed_count");
                    } else {
                        queryWrapper.orderByAsc("confirmed_count");
                    }
                } else {
                    if (CrawlerConstants.FIELD_CURRENT_CONFIRMED_COUNT.equals(field)){
                        queryWrapper.orderByDesc("current_confirmed_count");
                    } else {
                        queryWrapper.orderByDesc("confirmed_count");
                    }
                }
            }
        }
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
