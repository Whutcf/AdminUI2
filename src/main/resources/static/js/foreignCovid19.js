// 折叠面板 依赖 element模块
layui.use(['table', 'form', 'layer', 'element', 'carousel'], function () {
    const table = layui.table
        , form = layui.form
        , layer = layui.layer
        , carousel = layui.carousel
        , element = layui.element;
    const selectProvince = $("#provinceName");
    const selectContinents = $("#continents");
    let nameMap = {
        "Canada": "加拿大",
        "Turkmenistan": "土库曼斯坦",
        "Saint Helena": "圣赫勒拿",
        "Lao PDR": "老挝",
        "Lithuania": "立陶宛",
        "Cambodia": "柬埔寨",
        "Ethiopia": "埃塞俄比亚",
        "Faeroe Is.": "法罗群岛",
        "Swaziland": "斯威士兰",
        "Palestine": "巴勒斯坦",
        "Belize": "伯利兹",
        "Argentina": "阿根廷",
        "Bolivia": "玻利维亚",
        "Cameroon": "喀麦隆",
        "Burkina Faso": "布基纳法索",
        "Aland": "奥兰群岛",
        "Bahrain": "巴林",
        "Saudi Arabia": "沙特阿拉伯",
        "Fr. Polynesia": "法属波利尼西亚",
        "Cape Verde": "佛得角",
        "W. Sahara": "西撒哈拉",
        "Slovenia": "斯洛文尼亚",
        "Guatemala": "危地马拉",
        "Guinea": "几内亚",
        "Dem. Rep. Congo": "刚果（金）",
        "Germany": "德国",
        "Spain": "西班牙",
        "Liberia": "利比里亚",
        "Netherlands": "荷兰",
        "Jamaica": "牙买加",
        "Solomon Is.": "所罗门群岛",
        "Oman": "阿曼",
        "Tanzania": "坦桑尼亚",
        "Costa Rica": "哥斯达黎加",
        "Isle of Man": "曼岛",
        "Gabon": "加蓬",
        "Niue": "纽埃",
        "Bahamas": "巴哈马",
        "New Zealand": "新西兰",
        "Yemen": "也门",
        "Jersey": "泽西岛",
        "Pakistan": "巴基斯坦",
        "Albania": "阿尔巴尼亚",
        "Samoa": "萨摩亚",
        "Czech Rep.": "捷克",
        "United Arab Emirates": "阿拉伯联合酋长国",
        "Guam": "关岛",
        "India": "印度",
        "Azerbaijan": "阿塞拜疆",
        "N. Mariana Is.": "北马里亚纳群岛",
        "Lesotho": "莱索托",
        "Kenya": "肯尼亚",
        "Belarus": "白俄罗斯",
        "Tajikistan": "塔吉克斯坦",
        "Turkey": "土耳其",
        "Afghanistan": "阿富汗",
        "Bangladesh": "孟加拉国",
        "Mauritania": "毛里塔尼亚",
        "Dem. Rep. Korea": "朝鲜",
        "Saint Lucia": "圣卢西亚",
        "Br. Indian Ocean Ter.": "英属印度洋领地",
        "Mongolia": "蒙古",
        "France": "法国",
        "Cura?ao": "库拉索岛",
        "S. Sudan": "南苏丹",
        "Rwanda": "卢旺达",
        "Slovakia": "斯洛伐克",
        "Somalia": "索马里",
        "Peru": "秘鲁",
        "Vanuatu": "瓦努阿图",
        "Norway": "挪威",
        "Malawi": "马拉维",
        "Benin": "贝宁",
        "St. Vin. and Gren.": "圣文森特和格林纳丁斯",
        "Korea": "韩国",
        "Singapore": "新加坡",
        "Montenegro": "黑山共和国",
        "Cayman Is.": "开曼群岛",
        "Togo": "多哥",
        "China": "中国",
        "Heard I. and McDonald Is.": "赫德岛和麦克唐纳群岛",
        "Armenia": "亚美尼亚",
        "Falkland Is.": "马尔维纳斯群岛（福克兰）",
        "Ukraine": "乌克兰",
        "Ghana": "加纳",
        "Tonga": "汤加",
        "Finland": "芬兰",
        "Libya": "利比亚",
        "Dominican Rep.": "多米尼加",
        "Indonesia": "印度尼西亚",
        "Mauritius": "毛里求斯",
        "Eq. Guinea": "赤道几内亚",
        "Sweden": "瑞典",
        "Vietnam": "越南",
        "Mali": "马里",
        "Russia": "俄罗斯",
        "Bulgaria": "保加利亚",
        "United States": "美国",
        "Romania": "罗马尼亚",
        "Angola": "安哥拉",
        "Chad": "乍得",
        "South Africa": "南非",
        "Fiji": "斐济",
        "Liechtenstein": "列支敦士登",
        "Malaysia": "马来西亚",
        "Austria": "奥地利",
        "Mozambique": "莫桑比克",
        "Uganda": "乌干达",
        "Japan": "日本",
        "Niger": "尼日尔",
        "Brazil": "巴西",
        "Kuwait": "科威特",
        "Panama": "巴拿马",
        "Guyana": "圭亚那",
        "Madagascar": "马达加斯加",
        "Luxembourg": "卢森堡",
        "American Samoa": "美属萨摩亚",
        "Andorra": "安道尔",
        "Ireland": "爱尔兰",
        "Italy": "意大利",
        "Nigeria": "尼日利亚",
        "Turks and Caicos Is.": "特克斯和凯科斯群岛",
        "Ecuador": "厄瓜多尔",
        "U.S. Virgin Is.": "美属维尔京群岛",
        "Brunei": "文莱",
        "Australia": "澳大利亚",
        "Iran": "伊朗",
        "Algeria": "阿尔及利亚",
        "El Salvador": "萨尔瓦多",
        "C?te d'Ivoire": "科特迪瓦",
        "Chile": "智利",
        "Puerto Rico": "波多黎各",
        "Belgium": "比利时",
        "Thailand": "泰国",
        "Haiti": "海地",
        "Iraq": "伊拉克",
        "S?o Tomé and Principe": "圣多美和普林西比",
        "Sierra Leone": "塞拉利昂",
        "Georgia": "格鲁吉亚",
        "Denmark": "丹麦",
        "Philippines": "菲律宾",
        "S. Geo. and S. Sandw. Is.": "南乔治亚岛和南桑威奇群岛",
        "Moldova": "摩尔多瓦",
        "Morocco": "摩洛哥",
        "Namibia": "纳米比亚",
        "Malta": "马耳他",
        "Guinea-Bissau": "几内亚比绍",
        "Kiribati": "基里巴斯",
        "Switzerland": "瑞士",
        "Grenada": "格林纳达",
        "Seychelles": "塞舌尔",
        "Portugal": "葡萄牙",
        "Estonia": "爱沙尼亚",
        "Uruguay": "乌拉圭",
        "Antigua and Barb.": "安提瓜和巴布达",
        "Lebanon": "黎巴嫩",
        "Uzbekistan": "乌兹别克斯坦",
        "Tunisia": "突尼斯",
        "Djibouti": "吉布提",
        "Greenland": "格陵兰",
        "Timor-Leste": "东帝汶",
        "Dominica": "多米尼克",
        "Colombia": "哥伦比亚",
        "Burundi": "布隆迪",
        "Bosnia and Herz.": "波斯尼亚和黑塞哥维那",
        "Cyprus": "塞浦路斯",
        "Barbados": "巴巴多斯",
        "Qatar": "卡塔尔",
        "Palau": "帕劳",
        "Bhutan": "不丹",
        "Sudan": "苏丹",
        "Nepal": "尼泊尔",
        "Micronesia": "密克罗尼西亚",
        "Bermuda": "百慕大",
        "Suriname": "苏里南",
        "Venezuela": "委内瑞拉",
        "Israel": "以色列",
        "St. Pierre and Miquelon": "圣皮埃尔和密克隆群岛",
        "Central African Rep.": "中非",
        "Iceland": "冰岛",
        "Zambia": "赞比亚",
        "Senegal": "塞内加尔",
        "Papua New Guinea": "巴布亚新几内亚",
        "Trinidad and Tobago": "特立尼达和多巴哥",
        "Zimbabwe": "津巴布韦",
        "Jordan": "约旦",
        "Gambia": "冈比亚",
        "Kazakhstan": "哈萨克斯坦",
        "Poland": "波兰",
        "Eritrea": "厄立特里亚",
        "Kyrgyzstan": "吉尔吉斯斯坦",
        "Montserrat": "蒙特塞拉特",
        "New Caledonia": "新喀里多尼亚",
        "Macedonia": "马其顿",
        "Paraguay": "巴拉圭",
        "Latvia": "拉脱维亚",
        "Hungary": "匈牙利",
        "Syria": "叙利亚",
        "Honduras": "洪都拉斯",
        "Myanmar": "缅甸",
        "Mexico": "墨西哥",
        "Egypt": "埃及",
        "Nicaragua": "尼加拉瓜",
        "Cuba": "古巴",
        "Serbia": "塞尔维亚",
        "Comoros": "科摩罗",
        "United Kingdom": "英国",
        "Fr. S. Antarctic Lands": "南极洲",
        "Congo": "刚果（布）",
        "Greece": "希腊",
        "Sri Lanka": "斯里兰卡",
        "Croatia": "克罗地亚",
        "Botswana": "博茨瓦纳",
        "Siachen Glacier": "锡亚琴冰川地区"
    };

    // 使用默认滚动条
    window.parent.document.getElementById("mainIframe").scrolling = 'yes';

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


    // 世界地图
    let worldMap = echarts.init(document.getElementById('worldMap'));
    worldMap.showLoading();
    let currentConfirmedBtn = $('#currentConfirmedBtn');
    let confirmedBtn = $('#confirmedBtn');
    let mapData = [];

    getWorldMapData(1);
    currentConfirmedBtn.on('click', function () {
        getWorldMapData(1);
    });

    confirmedBtn.on('click', function () {
        getWorldMapData(2);
    });

    // 切换地图数据源
    function getWorldMapData(flag) {
        $.get('/crawler/getWorldCovidMapData?flag=' + flag, function (res) {
            mapData = res.data;
            // 更新数据
            setWorldMapOption(mapData, nameMap, flag);
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
    function setWorldMapOption(mapData, nameMap, flag) {
        worldMap.hideLoading();
        let option = {
            title: {
                text: '世界疫情地图',
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
                    {start: 100000, label: '>=100000'},
                    {start: 10000, end: 99999},
                    {start: 1000, end: 9999},
                    {start: 100, end: 999},
                    {start: 10, end: 99},
                    {start: 1, end: 9},
                    {start: 0, end: 0},
                ]
            },
            nameMap: nameMap, // 地图提供的英文的，需要中文替换
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
                    roam: true, // 地图是否能拖动
                    // zoom: 1.2, // 当前地图视角倍数
                    mapType: 'world',
                    itemStyle: {
                        normal: {label: {show: true}}
                    },
                    label: {
                        normal: {
                            show:false, // 不直接显示地点名称
                            textStyle: {
                                fontSize: 10
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
        worldMap.setOption(option, true);
    }

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

    window.onresize = function () { //用于使echarts自适应高度和宽度
        summaryBarChart.resize();
        summaryPieChart.resize();
        worldMap.resize();
    };

    //监听轮播切换事件
    carousel.on('change(Notice_carousel)', function (obj) { //Notice_carousel 来源于对应HTML容器的 lay-filter="Notice_carousel" 属性值
        obj.index === 0 ? summaryBarChart.resize() : summaryPieChart.resize();
        //console.log(obj.index); //当前条目的索引
        //console.log(obj.prevIndex); //上一个条目的索引
        //console.log(obj.item); //当前条目的元素对象
    });
});