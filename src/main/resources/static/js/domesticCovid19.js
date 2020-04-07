$(function () {


    let domesticMap = echarts.init(document.getElementById('domesticMap'));

    domesticMap.showLoading();

    $.get('/crawler/getProvinceCovidNewAddData', function (res) {
        let data = res.data;
        domesticMap.hideLoading();
        let option = {
            title: {
                text: '国内疫情地图',
                subtext: '国内现有确诊人数分布',
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
                    color: ['#ffffff', '#faebd2', '#e9a188', '#bb3937', '#772526', '#480f10']
                },
                splitList: [
                    {start: 1000, end: 4999},
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
                    name: '现存确诊',
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
                    data: data
                }
            ]
        };
        domesticMap.setOption(option);
    });
});