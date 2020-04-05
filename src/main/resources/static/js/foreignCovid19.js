// 折叠面板 依赖 element模块
layui.use(['table', 'form', 'layer', 'element', 'carousel'], function () {
    const table = layui.table
        , form = layui.form
        , layer = layui.layer
        , carousel = layui.carousel
        , element = layui.element;
    const selectProvince = $("#provinceName");
    const selectContinents = $("#continents");

    // 加载表格数据
    function initialTable() {
        table.render({
            elem: '#foreignStatistics '
            /* 高度最大化 */
            , height: 500
            /* 全局设置单元格的最新宽度 */
            , cellMinWidth: 60
            /* 隔行换色 */
            // , even: true
            , title: '国外疫情表'
            , url: '/crawler/getForeignStatistics'
            , cols: [
                [{field: 'locationId', hide: true}
                    , {field: 'continents', title: '大洲'}
                    , {field: 'provinceName', title: '国家名称'}
                    , {field: 'countryShortCode', title: '英文简称'}
                    , {field: 'countryFullName', title: '英文全称'}
                    , {field: 'currentConfirmedCount', title: '当前确诊', sort: true, style: "color: #a3313b;"}
                    , {field: 'confirmedCount', title: '累计确诊', sort: true}
                    , {field: 'curedCount', title: '治愈病例', sort: true}
                    , {field: 'deadCount', title: '死亡病例', sort: true}
                    , {field: 'deadRate', title: '死亡率%', sort: true}
                    , {title: '操作', fixed: 'right', align: 'center', toolbar: '#toolbar'}
                ]
            ]
            , done: function (res, curr, count) {
                // 设置某一列头 data-field 指向你想要改变的列头的field 值
                $(".layui-table-header .layui-table thead tr th[data-field='currentConfirmedCount'] ").css({
                    'background-color': '#c23531',
                    'color': '#fff'
                });
                $(".layui-table-header .layui-table thead tr th[data-field='confirmedCount'] ").css({
                    'background-color': '#2f4554',
                    'color': '#fff'
                });
                $(".layui-table-header .layui-table thead tr th[data-field='curedCount'] ").css({
                    'background-color': '#61a0a8',
                    'color': '#fff'
                });
                $(".layui-table-header .layui-table thead tr th[data-field='deadCount'] ").css({
                    'background-color': '#d48265',
                    'color': '#fff'
                });
            }, page: {
                layout: ['prev', 'page', 'next', 'skip', 'limit', 'refresh', 'count'] //自定义分页布局
                , groups: 5 //显示 5 个连续页码
                , first: '首页' // 自定义“首页”的内容
                , last: '尾页' // 自定义“尾页”的内容
                , limit: 20 // 每页显示条数
                , limits: [20, 50, 100, 200] //每页条数的选择项
            }
            , where: {
                'continents': ''
                , 'provinceName': ''
                // 排序相关字段
                , field: ''
                , order: ''
            }
            , autoSort: false // 自定义排序，必须添加的内容
            , method: 'post'
            , response: {
                statusCode: 200 // 重新规定成功的状态码为 200， table 默认为0
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.rows //解析数据列表
                };
            }
        });
    }

    initialTable();

    // 获取大洲下拉框
    function getContinents() {
        selectContinents.empty('');
        $.ajax({
            url: '/crawler/getContinents'
            , dataType: 'json'
            , type: 'get'
            , success: function (data) {
                selectContinents.append(new Option('请选择或搜索大洲', ''));
                $.each(data.data, function (index, item) {
                    selectContinents.append(new Option(item, item));
                });
                // append 结束后重新渲染
                form.render("select");
            }
        });
    }

    getContinents();

    // 获取国家下拉框
    form.on('select(continentsFilter)', function (data) {
        selectProvince.empty('');
        if (data.value !== '' && data.value != null) {
            $.ajax({
                url: '/crawler/getCountries'
                , dataType: 'json'
                , data: {
                    continents: $('#continents').val()
                }
                , type: 'get'
                , success: function (data) {
                    selectProvince.append(new Option('请选择或搜索国家', ''));
                    $.each(data.data, function (index, item) {
                        // new Option(text,value)
                        selectProvince.append(new Option(item, item));
                    });
                    selectProvince.find("option[value='']").prop("selected", true);
                    // append 结束后重新渲染
                    form.render("select");
                }
            })
        }
    });

    // 搜索功能
    const active = {
        // 重新加载
        reload: function () {
            table.reload('foreignStatistics', {
                where: {
                    'provinceName': selectProvince.val().trim()
                    , 'continents': selectContinents.val().trim()
                    // 排序相关的字段
                    , field: ''
                    , order: ''
                }
                , page: {
                    cur: 1
                }
            }, 'data');
        }
    };
    $('#search_submit').on('click', function () {
        let type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //重置按钮
    $("#searchResetBtn").unbind().bind({
        click: function () {
            getContinents();
            initialTable();
            selectProvince.empty('');
            selectProvince.append(new Option('请选择或搜索国家', ''));
            //加载完以后重新初始化
            $("#search_submit").click();
        }
    });

    // 手动刷新数据
    $('#refresh').unbind().bind({
        click: function () {
            const loading = layer.load(0, {shade: [0.5, 'grey'], time: 150 * 1000});
            $.ajax({
                url: "/crawler/refresh"
                , success: function (data) {
                    if (data.code === 200) {
                        layer.close(loading);
                        layer.msg("数据刷新成功！", {icon: 6});
                        getContinents();
                        initialTable();
                        selectProvince.empty('');
                        selectProvince.append(new Option('请选择或搜索国家', ''));
                    }
                }
            });
        }
    });

    // 自定义排序
    table.on('sort(foreignStatic)', function (obj) { // foreignStatic 对应的是table的lay-filter
        table.reload('foreignStatistics', {
            initSort: obj
            , where: {
                'provinceName': selectProvince.val().trim()
                , 'continents': selectContinents.val().trim()
                // 排序相关字段
                , field: obj.field // 排序的字段
                , order: obj.type // 排序的方式,当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
            }
        }); // foreignStatistics 对应的是table的id
    });

    // 查看历史数据
    // 监听工具条
    table.on('tool(foreignStatic)', function (obj) {
        let data = obj.data;
        if (obj.event === 'detail') {
            let locationId = data.locationId;
            let provinceName = data.provinceName;
            let currentConfirmedCount = data.currentConfirmedCount;
            let confirmedCount = data.confirmedCount;
            let curedCount = data.curedCount;
            let deadCount = data.deadCount;
            layer.open({
                type: 2 //Page层类型 1: 纯文本 2: 跳转页面
                , area: ['1300px', '620px']
                , title: provinceName + '疫情发展'
                , shade: 0.6 //遮罩透明度
                , shadeClose: true//弹出框之外的地方是否可以点击
                , maxmin: true //允许全屏最小化
                , anim: 1 //0-6的动画形式，-1不开启
                , content: 'foreignCovid19HistDetail?locationId=' + locationId
                    + '&provinceName=' + encodeURIComponent(provinceName) + '&currentConfirmedCount=' + currentConfirmedCount
                    + '&confirmedCount=' + confirmedCount + '&curedCount=' + curedCount + '&deadCount=' + deadCount
            });
        }
    });

    //建造轮播实例
    carousel.render({
        elem: '#Notice_carousel'
        , width: '100%' //设置容器宽度
        , height: '300px'
        , arrow: 'none' //不显示箭头
        , indicator: 'inside'//指示器位置，可选outside,none
        , autoplay: true//自动切换
        , interval: 20000 // 单位ms
        , trigger: 'hover'//悬浮切换
        //,anim: 'updown' //切换动画方式默认左右
    });

    // 基于准备好的dom，初始化echarts的实例
    let summaryBarChart = echarts.init(document.getElementById('summaryBarChart'));
    let summaryPieChart = echarts.init(document.getElementById('summaryPieChart'));

    function generateSummaryBarChart() {
        $.ajax({
            url: '/crawler/getSummaryBarChartData'
            , dataType: 'json'
            , success: function (data) {
                generateBarChar(data);
            }
        });
    }

    generateSummaryBarChart();

    function generateSummaryPieChart() {
        $.ajax({
            url: '/crawler/getSummaryPieChartData'
            , dataType: 'json'
            , success: function (data) {
                generatePieChar(data);
            }
        });
    }

    generateSummaryPieChart();

    // 生成图标的方法
    function generateBarChar(data) {
        let optionBarChart = {
            title: {
                text: '疫情汇总'
            },
            tooltip: {
                trigger: 'axis'//悬浮显示对比
            },
            legend: {//顶部显示 与series中的数据类型的name一致
                data: data.legendList
            },
            toolbox: {
                feature: {
                    dataView: {readOnly: true},// 数据视图，不可修改
                    saveAsImage: {}//保存图片下载
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: true,//从起点开始
                data: data.xAxisList
            },
            yAxis: {
                type: 'value'
            },
            series: data.seriesList
        };
        summaryBarChart.setOption(optionBarChart);
    }

    function generatePieChar(data) {
        let optionPieChart = {
            title: [{
                text: '疫情汇总'
                , x: 'center'//标题居中
            }, {
                subtext: '当期确诊占比'//副标题
                , left: '28%'
                , top: '80%'
                , textAlign: 'center'
            }, {
                subtext: '累计确诊占比'//副标题
                , left: '46%'
                , top: '80%'
                , textAlign: 'center'
            }, {
                subtext: '累计治愈占比'//副标题
                , left: '64%'
                , top: '80%'
                , textAlign: 'center'
            }, {
                subtext: '累计死亡占比'//副标题
                , left: '82%'
                , top: '80%'
                , textAlign: 'center'
            }], tooltip: {
                trigger: 'item'//悬浮显示对比
                , formatter: "{a}<br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',//类型垂直,默认水平
                left: 'left',//类型区分在左 默认居中
                data: data.legendList
            },
            series: [
                {
                    name: '确诊人数占比',
                    type: 'pie',//饼状
                    radius: '60%',//圆的大小
                    center: ['28%', '50%'],
                    data: data.seriesList1
                }, {
                    name: '累计人数占比',
                    type: 'pie',//饼状
                    radius: '60%',//圆的大小
                    center: ['46%', '50%'],
                    data: data.seriesList2
                }, {
                    name: '治愈人数占比',
                    type: 'pie',//饼状
                    radius: '60%',//圆的大小
                    center: ['64%', '50%'],
                    data: data.seriesList3
                }, {
                    name: '死亡人数占比',
                    type: 'pie',//饼状
                    radius: '60%',//圆的大小
                    center: ['82%', '50%'],
                    data: data.seriesList4
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        summaryPieChart.setOption(optionPieChart);
    }

    // 指定图标的配置项和数据
    // var optionPieChart = {
    //     title: {
    //         text: '发布类型汇总',
    //         subtext: '纯属虚构',//副标题
    //         x: 'center'//标题居中
    //     }, tooltip: {
    //         trigger: 'item'//悬浮显示对比
    //     },
    //     legend: {
    //         orient: 'vertical',//类型垂直,默认水平
    //         left: 'left',//类型区分在左 默认居中
    //         data: ['公告', '作业', '光荣榜', '成绩报告']
    //     },
    //     series: [
    //         {
    //             type: 'pie',//饼状
    //             radius: '60%',//圆的大小
    //             center: ['50%', '50%'],//居中
    //             data: [
    //                 {value: 335, name: '公告'},
    //                 {value: 310, name: '作业'},
    //                 {value: 234, name: '光荣榜'},
    //                 {value: 135, name: '成绩报告'}
    //             ]
    //         }
    //     ]
    // };
    //     optionBarChart = {
    //     title: {
    //         text: '疫情汇总'
    //     },
    //     tooltip: {
    //         trigger: 'axis'//悬浮显示对比
    //     },
    //     legend: {//顶部显示 与series中的数据类型的name一致
    //         data: ['公告', '作业', '光荣榜', '成绩报告']
    //     },
    //     toolbox: {
    //         feature: {
    //             saveAsImage: {}//保存图片下载
    //         }
    //     },
    //     xAxis: {
    //         type: 'category',
    //         boundaryGap: false,//从起点开始
    //         data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    //     },
    //     yAxis: {
    //         type: 'value'
    //     },
    //     series: [{
    //         name: '公告',
    //         type: 'line',//线性
    //         data: [620, 732, 701, 734, 1090, 1130, 1120],
    //     }, {
    //         name: '作业',
    //         type: 'line',//线性
    //         data: [720, 832, 801, 834, 1190, 1230, 1220],
    //     }, {
    //         smooth: true,//曲线 默认折线
    //         name: '光荣榜',
    //         type: 'line',//线性
    //         data: [820, 932, 901, 934, 1290, 1330, 1320],
    //     }, {
    //         smooth: true,//曲线
    //         name: '成绩报告',
    //         type: 'line',//线性
    //         data: [220, 332, 401, 534, 690, 730, 820],
    //     }]
    // },

    window.onresize = function () { //用于使echarts自适应高度和宽度
        summaryBarChart.resize();
        summaryPieChart.resize();
    };

    //监听轮播切换事件
    carousel.on('change(Notice_carousel)', function (obj) { //Notice_carousel 来源于对应HTML容器的 lay-filter="Notice_carousel" 属性值
        obj.index === 0 ? summaryBarChart.resize() : summaryPieChart.resize();
        //console.log(obj.index); //当前条目的索引
        //console.log(obj.prevIndex); //上一个条目的索引
        //console.log(obj.item); //当前条目的元素对象
    });
});