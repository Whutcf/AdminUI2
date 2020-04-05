package com.smic.cf.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smic.cf.constants.CrawlerConstants;
import com.smic.cf.constants.SortItem;
import com.smic.cf.dto.SummaryBarChartData;
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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDate.now;

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
                    // 防止异常出现两笔以上的记录，防呆
                    List<ForeignCountryCovid19> foreignCountryCovid19Olds = foreignCountryCovid19Mapper.selectList(queryWrapper);
                    if (foreignCountryCovid19Olds.size() == 0) {
                        foreignCountryCovid19.setDeadRate(foreignCountryCovid19.getDeadRate() + "%");
                        foreignCountryCovid19Mapper.insert(foreignCountryCovid19);
                    } else if (foreignCountryCovid19Olds.size() == 1) {
                        foreignCountryCovid19.setDeadRate(foreignCountryCovid19.getDeadRate() + "%");
                        foreignCountryCovid19Mapper.update(foreignCountryCovid19, queryWrapper);
                    } else {
                        foreignCountryCovid19Mapper.delete(queryWrapper);
                        foreignCountryCovid19.setDeadRate(foreignCountryCovid19.getDeadRate() + "%");
                        foreignCountryCovid19Mapper.insert(foreignCountryCovid19);
                    }
                }
                // 更新每日新增数据,需要对0特殊处理
                IncrVo incrVo = foreignCountryCovid19.getIncrVo();
                if (!StringUtils.isEmpty(incrVo)) {
                    if (incrVo.getId() != 0) {
                        incrVoMapper.deleteById(incrVo.getId());
                    } else {
                        LambdaQueryWrapper<IncrVo> queryWrapper = Wrappers.lambdaQuery();
                        queryWrapper.eq(IncrVo::getId, 0)
                                .eq(IncrVo::getCountryShortCode, incrVo.getCountryShortCode());
                        if (incrVoMapper.selectList(queryWrapper).size() > 0) {
                            incrVoMapper.delete(queryWrapper);
                        }
                    }
                    incrVoMapper.insert(incrVo);
                }
                // 更新历史数据
                List<ForeignStatisticsTrendChartData> statisticsTrendChartDataList = foreignCountryCovid19.getStatisticsTrendChartDataList();
                if (null != statisticsTrendChartDataList && statisticsTrendChartDataList.size() > 0) {
                    LocalDate localDate = now();
                    for (ForeignStatisticsTrendChartData foreignStatisticsTrendChartData : statisticsTrendChartDataList) {
                        String dateId = foreignStatisticsTrendChartData.getDateId();
                        LocalDate date = LocalDate.of(Integer.parseInt(dateId.substring(0, 4))
                                , Integer.parseInt(dateId.substring(4, 6))
                                , Integer.parseInt(dateId.substring(6, 8)));
                        // 历史数据只刷新前一天的数据
                        // java8中主要使用Period、Duration，ChronoUnit
                        // Period类方法getYears（），getMonths（）和getDays（）来计算.
                        // ChronoUnit类可用于在单个时间单位内测量一段时间，例如天数或秒。between（）方法
                        if (ChronoUnit.DAYS.between(date, localDate) <= 1) {
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
                                foreignStatisticTrendChartDataMapper.deleteById(foreignStatisticsTrendChartData.getLocationId());
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
        if (null != field && !StringUtils.isEmpty(field)) {
            if (StringUtils.isEmpty(order) || order.equals(CrawlerConstants.ORDER_BY_ASC)) {
                queryWrapper.orderByAsc(SortItem.getColumn(field));
            } else {
                queryWrapper.orderByDesc(SortItem.getColumn(field));
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

    /**
     * 获取echarts的summaryBarChartData所需要的数据
     *
     * @return com.alibaba.fastjson.JSONObject
     * @author 蔡明涛
     * @date 2020/4/3 0:28
     */
    @Override
    public JSONObject getSummaryBarChartData() {
        // 获取基础数据
        List<SummaryBarChartData> summaryBarChartDataList = foreignCountryCovid19Mapper.getSummaryData();

        JSONObject jsonObject = new JSONObject();

        List<String> legendList = new ArrayList<>();
        legendList.add(CrawlerConstants.CURRENT_CONFIRMED_COUNT_NAME);
        legendList.add(CrawlerConstants.CONFIRMED_COUNT_NAME);
        legendList.add(CrawlerConstants.CURED_COUNT_NAME);
        legendList.add(CrawlerConstants.DEAD_COUNT_NAME);
        jsonObject.put("legendList", legendList);

        List<String> xAxisList = new ArrayList<>();
        List<Map<String, Object>> seriesList = new ArrayList<>();
        Map<String, Object> seriesMap1 = new HashMap<>(16);
        Map<String, Object> seriesMap2 = new HashMap<>(16);
        Map<String, Object> seriesMap3 = new HashMap<>(16);
        Map<String, Object> seriesMap4 = new HashMap<>(16);
        List<Integer> dataList1 = new ArrayList<>();
        List<Integer> dataList2 = new ArrayList<>();
        List<Integer> dataList3 = new ArrayList<>();
        List<Integer> dataList4 = new ArrayList<>();
        seriesMap1.put(CrawlerConstants.ECHARTS_NAME, CrawlerConstants.CURRENT_CONFIRMED_COUNT_NAME);
        seriesMap1.put(CrawlerConstants.ECHARTS_TYPE, CrawlerConstants.ECHARTS_BAR);
        seriesMap2.put(CrawlerConstants.ECHARTS_NAME, CrawlerConstants.CONFIRMED_COUNT_NAME);
        seriesMap2.put(CrawlerConstants.ECHARTS_TYPE, CrawlerConstants.ECHARTS_BAR);
        seriesMap3.put(CrawlerConstants.ECHARTS_NAME, CrawlerConstants.CURED_COUNT_NAME);
        seriesMap3.put(CrawlerConstants.ECHARTS_TYPE, CrawlerConstants.ECHARTS_BAR);
        seriesMap4.put(CrawlerConstants.ECHARTS_NAME, CrawlerConstants.DEAD_COUNT_NAME);
        seriesMap4.put(CrawlerConstants.ECHARTS_TYPE, CrawlerConstants.ECHARTS_BAR);
        // 获取横轴和对应的数据
        for (SummaryBarChartData summaryBarChartData : summaryBarChartDataList) {
            xAxisList.add(summaryBarChartData.getContinents());
            dataList1.add(summaryBarChartData.getCurrentConfirmedTotal());
            dataList2.add(summaryBarChartData.getConfirmedTotal());
            dataList3.add(summaryBarChartData.getCureTotal());
            dataList4.add(summaryBarChartData.getDeadTotal());
        }
        // 在坐标轴上显示数字
        Map<String, Object> textStyle = new HashMap<>(16);
        textStyle.put("color", "#1015E5");
        textStyle.put("fontSize", 8);
        Map<String, Object> label = new HashMap<>(16);
        label.put("show", true);
        label.put("position", "top");
        label.put("textStyle", textStyle);
        Map<String, Object> normal = new HashMap<>(16);
        normal.put("label", label);
        Map<String, Object> itemStyle = new HashMap<>(16);
        itemStyle.put("normal", normal);


        seriesMap1.put(CrawlerConstants.ECHARTS_DATA, dataList1);
        seriesMap1.put(CrawlerConstants.ECHARTS_ITEM_STYLE, itemStyle);
        seriesList.add(seriesMap1);
        seriesMap2.put(CrawlerConstants.ECHARTS_DATA, dataList2);
        seriesMap2.put(CrawlerConstants.ECHARTS_ITEM_STYLE, itemStyle);
        seriesList.add(seriesMap2);
        seriesMap3.put(CrawlerConstants.ECHARTS_DATA, dataList3);
        seriesMap3.put(CrawlerConstants.ECHARTS_ITEM_STYLE, itemStyle);
        seriesList.add(seriesMap3);
        seriesMap4.put(CrawlerConstants.ECHARTS_DATA, dataList4);
        seriesMap4.put(CrawlerConstants.ECHARTS_ITEM_STYLE, itemStyle);
        seriesList.add(seriesMap4);
        jsonObject.put("xAxisList", xAxisList);
        jsonObject.put("seriesList", seriesList);
        return jsonObject;
    }

    /**
     * 获取echarts的世界疫情各大洲当前确诊人数占比
     *
     * @return com.alibaba.fastjson.JSONObject
     * @author 蔡明涛
     * @date 2020/4/3 2:02
     */
    @Override
    public JSONObject getSummaryPieChartData() {
        // 获取基础数据
        List<SummaryBarChartData> summaryBarChartDataList = foreignCountryCovid19Mapper.getSummaryData();
        JSONObject jsonObject = new JSONObject();
        List<String> legendList = new ArrayList<>();
        List<Map<String, Object>> seriesList1 = new ArrayList<>();
        List<Map<String, Object>> seriesList2 = new ArrayList<>();
        List<Map<String, Object>> seriesList3 = new ArrayList<>();
        List<Map<String, Object>> seriesList4 = new ArrayList<>();
        for (SummaryBarChartData summaryBarChartData : summaryBarChartDataList) {
            legendList.add(summaryBarChartData.getContinents());
            Map<String, Object> dataMap1 = new HashMap<>(16);
            Map<String, Object> dataMap2 = new HashMap<>(16);
            Map<String, Object> dataMap3 = new HashMap<>(16);
            Map<String, Object> dataMap4 = new HashMap<>(16);

            dataMap1.put(CrawlerConstants.ECHARTS_NAME, summaryBarChartData.getContinents());
            dataMap1.put(CrawlerConstants.ECHARTS_VALUE, summaryBarChartData.getCurrentConfirmedTotal());

            dataMap2.put(CrawlerConstants.ECHARTS_NAME, summaryBarChartData.getContinents());
            dataMap2.put(CrawlerConstants.ECHARTS_VALUE, summaryBarChartData.getConfirmedTotal());

            dataMap3.put(CrawlerConstants.ECHARTS_NAME, summaryBarChartData.getContinents());
            dataMap3.put(CrawlerConstants.ECHARTS_VALUE, summaryBarChartData.getCureTotal());

            dataMap4.put(CrawlerConstants.ECHARTS_NAME, summaryBarChartData.getContinents());
            dataMap4.put(CrawlerConstants.ECHARTS_VALUE, summaryBarChartData.getDeadTotal());

            seriesList1.add(dataMap1);
            seriesList2.add(dataMap2);
            seriesList3.add(dataMap3);
            seriesList4.add(dataMap4);
        }
        jsonObject.put("legendList", legendList);
        jsonObject.put("seriesList1", seriesList1);
        jsonObject.put("seriesList2", seriesList2);
        jsonObject.put("seriesList3", seriesList3);
        jsonObject.put("seriesList4", seriesList4);
        return jsonObject;
    }

    /**
     * 根据Id找对象
     *
     * @param locationId 地理位置Id
     * @return com.smic.cf.pojo.ForeignCountryCovid19
     * @author 蔡明涛
     * @date 2020/4/5 1:18
     */
    @Override
    public ForeignCountryCovid19 getForeignCountryById(Long locationId) {
        return foreignCountryCovid19Mapper.selectById(locationId);
    }

    /**
     * 根据Id找对象
     *
     * @param locationId 地理位置
     * @return java.util.List<com.smic.cf.pojo.ForeignStatisticsTrendChartData>
     * @author 蔡明涛
     * @date 2020/4/5 1:24
     */
    @Override
    public List<ForeignStatisticsTrendChartData> getTrendChartDataList(Long locationId) {
        LambdaQueryWrapper<ForeignStatisticsTrendChartData> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ForeignStatisticsTrendChartData::getLocationId, locationId)
                .orderByDesc(ForeignStatisticsTrendChartData::getDateId);
        return foreignStatisticTrendChartDataMapper.selectList(queryWrapper);
    }
}
