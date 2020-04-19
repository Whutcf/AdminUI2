$(function () {
    let domesticMap = echarts.init(document.getElementById('domesticMap'));
    let mapData = [];
    let currentConfirmedBtn = $('#currentConfirmedBtn');
    let confirmedBtn = $('#confirmedBtn');
    domesticMap.showLoading();

    getMapData(1);

    currentConfirmedBtn.on('click', function () {
        getMapData(1);
    });

    confirmedBtn.on('click', function () {
        getMapData(2);
    });

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
                    color: ['#ffffff', '#ffe4d9', '#ffcab3', '#ffaa85', '#ff7b69', '#cc2929','#8c0d0d']
                },
                splitList: [
                    {start: 10000,label: '>=10000'},
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
            option.title.subtext= '现有确诊人数分布';
        } else {
            option.series[0].name = "累计确诊";
            option.title.subtext= '累计确诊人数分布';
        }
        domesticMap.setOption(option,true);
    }

});