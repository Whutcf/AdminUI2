// 参照admin_add_role的方法使用 th:value 传参失效。直接通过js获取url中的参数
let locationId = getUrlParam("locationId");
let countryName = getUrlParam("provinceName");
let currentConfirmedCount = getUrlParam("currentConfirmedCount");
let confirmedCount = getUrlParam("confirmedCount");
let curedCount = getUrlParam("curedCount");
let deadCount = getUrlParam("deadCount");

function getUrlParam(parameter) {
    // 定义正则表达
    const reg = new RegExp("(^|&)" + parameter + "=([^&]*)(&|$)", "i");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) {
        if (parameter === "provinceName") {
            // 中文被加码了需要解码
            return unescape(decodeURIComponent(r[2]));
        } else {
            return unescape(r[2]);
        }
    }
    return null;
}

//页面初始化 将父页面的值赋值给子页面
$(function () {
    $('#currentConfirmedCount').text(currentConfirmedCount);
    $('#confirmedCount').text(confirmedCount);
    $('#curedCount').text(curedCount);
    $('#deadCount').text(deadCount);
});

layui.use(['table', 'form', 'layer', 'element', 'carousel', 'laydate'], function () {
    const table = layui.table
        , form = layui.form
        , layer = layui.layer
        , carousel = layui.carousel
        , element = layui.element
        // , laydate = layui.laydate
    ;
    // 取消对时间的过滤
    // let beginDate = '';
    // let endDate = '';
    /*    laydate.render({
            elem: '#beginDate'
            // 限定时间 min max
            , min: '2020-01-19'
            // 初始赋值
            , value: '20200119'
            , isInitValue: true
            // 主题颜色
            , theme: '#61a0a8'
            // 开启公例节日
            , calendar: true
            // 自定义格式
            , format: 'yyyyMMdd'
            , done: function (value) {
                beginDate = value;
            }
        });
        laydate.render({
            elem: '#endDate'
            , min: '2020-01-19'
            , theme: '#61a0a8'
            , calendar: true
            , format: 'yyyyMMdd'
            , done: function (value) {
                endDate = value;
            }
        });*/

    // 加载表格数据
    function initialTable() {
        table.render({
            elem: '#foreignStatistics '
            /* 高度最大化 */
            , height: 500
            , skin: 'row'
            /* 全局设置单元格的最新宽度 */
            , cellMinWidth: 60
            /* 隔行换色 */
            // , even: true
            // , title: '国外疫情表'
            , url: '/crawler/getForeignStatisticsHist?locationId=' + locationId
            , cols: [
                [{field: 'locationId', hide: true}
                    , {field: 'dateId', title: '日期'}
                    , {field: 'currentConfirmedCount', title: '现有确诊', style: "color: #a3313b;"}
                    , {field: 'confirmedCount', title: '累计确诊'}
                    , {field: 'curedCount', title: '累计治愈'}
                    , {field: 'deadCount', title: '累计死亡'}
                ]
            ]
            , page: false
            , done: function (res, curr, count) {
                // 设置某一列头 data-field 指向你想要改变的列头的field 值
                $(".layui-table-header .layui-table thead tr th[data-field='currentConfirmedCount'] ").css({
                    'background-color': '#ff6a57',
                    'color': '#fff'
                });
                $(".layui-table-header .layui-table thead tr th[data-field='confirmedCount'] ").css({
                    'background-color': '#e83132',
                    'color': '#fff'
                });
                $(".layui-table-header .layui-table thead tr th[data-field='curedCount'] ").css({
                    'background-color': '#10aeb5',
                    'color': '#fff'
                });
                $(".layui-table-header .layui-table thead tr th[data-field='deadCount'] ").css({
                    'background-color': '#4d5054',
                    'color': '#fff'
                });
            }
            // , where: {
            //     'beginDate': beginDate
            //     , 'endDate': endDate
            // }
            , method: 'post'
            , response: {
                statusCode: 200 // 重新规定成功的状态码为 200， table 默认为0
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "data": res.data //解析数据列表
                };
            }
        });
    }

    initialTable();

    // 准备好的dom,初始化echarts的实例
    let covid19TrendChart = echarts.init(document.getElementById('covid19TrendChart'));

    // 显示加载中
    covid19TrendChart.showLoading();

    setTimeout(function (){
        $.get('/crawler/getForeignCountryDataSet?locationId='+locationId, function (res) {
            let data = res.data;
            let max = data[0].length;
            let itemName= data[0][0];
            let value = data[0][max-1];
            let tooltips = data[0][max-1];
            covid19TrendChart.hideLoading();// 数据到手去除加载效果
            let option = {
                title: {
                    text: countryName + '疫情趋势',
                    subtext: '当日存在确诊、当日累计治愈、当日累计死亡',
                    left: 'left'
                },
                tooltip: {
                    trigger: 'axis'
                    , showContent: false // 是否显示图标文字
                },
                dataset:{
                    source: data // 标题行1行，3个系列3行
                },
                xAxis: {type: 'category'},
                yAxis: {gridIndex:0},
                grid: {top: '50%'},
                series: [
                    {type: 'line', smooth: true, seriesLayoutBy: 'row'},// smooth指折线的平滑度，seriesLayoutBy:'row'显示在一列
                    {type: 'line', smooth: true, seriesLayoutBy: 'row'},
                    {type: 'line', smooth: true, seriesLayoutBy: 'row'},
                    {
                        type: 'pie',
                        id: 'pie',
                        radius: '30%', // 饼图大小
                        center: ['50%','25%'] ,// 距离左侧和顶部的百分比
                        label:{
                            formatter: '{b}: {@0404} ({d}%)' // b表示横坐标，@1表示从第一个值对应的x轴开始，d表示饼图的占比
                        },
                        encode: {
                            itemName:itemName, // 传入的是个二维数组 取第一行和第一列
                            value:value,
                            tooltip:tooltips
                        }
                    }
                ]
            };
            // 是的饼图能随着鼠标的移动而变化
            covid19TrendChart.on('updateAxisPointer',function (event) {
                let xAxisInfo = event.axesInfo[0];// source的第一行数据
                if (xAxisInfo) {
                    let dimension = xAxisInfo.value + 1;
                    covid19TrendChart.setOption({
                        series: {
                            id: 'pie',
                            label: {
                                formatter: '{b}: {@[' + dimension + ']} ({d}%)'
                            },
                            encode: {
                                value: dimension,
                                tooltip: dimension
                            }
                        }
                    });
                }
            });
            covid19TrendChart.setOption(option);
        })
    });
});
