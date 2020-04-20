$(function () {
    // 滚动条失效
    window.parent.document.getElementById("mainIframe").scrolling = 'yes';

    let domesticMap = echarts.init(document.getElementById('domesticMap'));
    let newAddCase = echarts.init(document.getElementById('newAddCase'));
    let foreignInCase = echarts.init(document.getElementById('foreignInCase'));
    let mapData = [];
    let currentConfirmedBtn = $('#currentConfirmedBtn');
    let confirmedBtn = $('#confirmedBtn');
    domesticMap.showLoading();
    newAddCase.showLoading();
    foreignInCase.showLoading();
    getMapData(1);

    // 获取国内每日新增数据，只取最近一个月的数据
    getNewAddCase();

    function getNewAddCase() {
        $.get('/crawler/getCaseCount?name=中国疫情汇总&seriesName=新增确诊', function (res) {
            newAddCase.hideLoading();
            let data = res.data;
            let option = {
                title: {
                    text: '每日新增确诊',
                    subtext: '最近一个月内每日新增确诊人数',
                    x: 'center',
                    y: 'top'
                },
                tooltip: {
                    trigger: 'axis'
                },
                toolbox: {
                    show: true,
                    feature: {
                        magicType: {show: true, type: ['line', 'bar']},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                calculable: true,
                xAxis: [
                    {
                        type: 'category',
                        data: data[0].xAxisList
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        type: 'line',
                        data: data[1].seriesList,
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
                    }
                ]
            };
            newAddCase.setOption(option);
        });
    }

    // 获取境外输入每日新增数据，只取最近一个月的数据
    getForeignInCase();

    function getForeignInCase() {
        $.get('/crawler/getCaseCount?name=中国疫情汇总&seriesName=新增境外输入', function (res) {
            foreignInCase.hideLoading();
            let data = res.data;
            let option = {
                title: {
                    text: '每日新增境外输入',
                    subtext: '最近一个月内每日新增境外输入人数',
                    x: 'center',
                    y: 'top'
                },
                tooltip: {
                    trigger: 'axis'
                },
                toolbox: {
                    show: true,
                    feature: {
                        magicType: {show: true, type: ['line', 'bar']},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                calculable: true,
                xAxis: [
                    {
                        type: 'category',
                        data: data[0].xAxisList
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        type: 'line',
                        data: data[1].seriesList,
                        itemStyle: {
                            normal: {
                                color: "#386db3",//折线点的颜色
                                lineStyle: {
                                    color: "#386db3"//折线的颜色
                                }
                            }
                        },
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
                    }
                ]
            };
            foreignInCase.setOption(option);
        });
    }

    currentConfirmedBtn.on('click', function () {
        getMapData(1);
    });

    confirmedBtn.on('click', function () {
        getMapData(2);
    });

    // 切换地图数据源
    function getMapData(flag) {
        $.get('/crawler/getProvinceCovidMapData?flag=' + flag, function (res) {
            mapData = res.data;
            // 更新数据
            setMapOption(mapData, flag);
            if (flag === 1) {
                confirmedBtn.removeClass("active");
                currentConfirmedBtn.addClass("active");
            } else {
                currentConfirmedBtn.removeClass("active");
                confirmedBtn.addClass("active");
            }
        });
    }

    // 显示地图
    function setMapOption(mapData, flag) {
        domesticMap.hideLoading();
        let option = {
            title: {
                text: '国内疫情地图',
                left: 'left'
            },
            tooltip: {
                trigger: 'item',
                showDelay: 0,
                transitionDuration: 0.2,
                formatter: function (params) {
                    let value = (params.value + '').split('.');
                    value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,');
                    return params.seriesName + '<br/>' + params.name + ': ' + value;
                }
            },
            visualMap: {
                show: true,
                // 图例布局 horizontal 横向 vertical 纵向
                orient: 'horizontal',
                left: 'center',
                top: 'bottom',
                inRange: {
                    color: ['#ffffff', '#ffe4d9', '#ffcab3', '#ffaa85', '#ff7b69', '#cc2929', '#8c0d0d']
                },
                splitList: [
                    {start: 10000, label: '>=10000'},
                    {start: 1000, end: 9999},
                    {start: 500, end: 999},
                    {start: 100, end: 499},
                    {start: 10, end: 99},
                    {start: 1, end: 9},
                    {start: 0, end: 0},
                ]
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'top',
                feature: {
                    dataView: {readOnly: true},
                    restore: {},
                    saveAsImage: {}
                }
            },
            series: [
                {
                    // name: '现存确诊',
                    type: 'map',
                    roam: false, // 地图是否能拖动
                    // zoom: 1.2, // 当前地图视角倍数
                    mapType: 'china',
                    itemStyle: {
                        normal: {label: {show: true}}
                    },
                    label: {
                        normal: {
                            textStyle: {
                                fontSize: 8
                            }
                        }
                    },
                    data: mapData
                }
            ]
        };
        // 清除画布
        option.series[0].mapData = mapData;
        if (flag === 1) {
            option.series[0].name = "现存确诊";
            option.title.subtext = '现有确诊人数分布';
        } else {
            option.series[0].name = "累计确诊";
            option.title.subtext = '累计确诊人数分布';
        }
        domesticMap.setOption(option, true);
    }
});

layui.use(['carousel', 'layer'], function () {
    const carousel = layui.carousel
        , layer = layui.layer;
    // 建造实例
    carousel.render({
        elem: '#notices'
        , width: 'auto'
        , height: '30px'
        , arrow: 'none' // 切换箭头默认显示状态  hover（悬停显示）always（始终显示） none（始终不显示）
        , anim: 'updown' // 轮播切换动画方式，可选值为： default（左右切换）updown（上下切换）fade（渐隐渐显切换）
        , interval: '3000'
        , indicator: 'none'
    });

    $('.fakeClass').each(function () {
        let id = $('#' + $(this).attr('id'));
        id.bind('click', function () {
                layer.open({
                    title: '公告消息'
                    , skin: 'layui-layer-lan'
                    , area: ['600px', '150px']
                    , type: 1
                    , closeBtn: 0 //不显示关闭按钮
                    , anim: 2
                    , shadeClose: true // 开启遮罩关闭
                    , content: '<div><p>' + id.attr("value") + '</p></div>'
                });
            }
        );

    });
});