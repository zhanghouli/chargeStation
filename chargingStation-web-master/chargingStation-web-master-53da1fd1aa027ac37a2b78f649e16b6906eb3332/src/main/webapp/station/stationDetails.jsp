<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <c:if test="${estateId != null && operatorId != null  }">
                <li><a href="${path}/passport/operatorList.htm?operatorId=${operatorId}">运营商</a></li>
                <li><a href="${path}/passport/estateList.htm?estateId=${estateId}&operatorId=${operatorId}">物业</a></li>
            </c:if>
            <c:if test="${estateId == null && operatorId != null  }">
                <li><a href="${path}/passport/operatorList.htm?operatorId=${operatorId}">运营商</a></li>
            </c:if>
            <c:if test="${estateId != null && operatorId == null }">
                <li><a href="${path}/passport/estateList.htm?estateId=${estateId}">物业</a></li>
            </c:if>
            <c:if test="${searchStationVo.stationType == 'fault'}">
                <li>
                    <a href="${path}/station/errorStationList.htm?estateId=${estateId}&operatorId=${operatorId}">电站列表</a>
                </li>
            </c:if>
            <c:if test="${searchStationVo.stationType == 'normal' }">
                <li><a href="${path}/station/stationList.htm?estateId=${estateId}&operatorId=${operatorId}">电站列表</a>
                </li>
            </c:if>
            <li><a href="#">电站详情</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="formbody">
    <ul class="forminfo short validationEngineContainer">
        <li class="line">
            <label>电站名称:</label>
            <input readonly="readonly" style="width: 300px;" type="text" value="${station.name}"/>
        </li>
        <li class="line">
            <label>电站编号:</label>
            <input readonly="readonly" style="width: 300px;" type="text" value="${station.number}"/>
        </li>
        <li class="line">
            <label>电站详细地址:</label>
            <input readonly="readonly" style="width: 300px;" type="text" value="${station.address}"/>
        </li>
    </ul>
    <ul class="seachform" style="padding: 10px 10px;">
        <form method="get" action="" id="searchForm">
            <input name="stationId" type="hidden" value="${stationId}">
            <li>
                <label>起始日期</label>
                <input name="timeStartStr" type="text" class="scinput Wdate" id="timeStartStr"
                       value="${timeStartStr}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,maxDate:'#F{$dp.$D(\'timeEndStr\')}',minDate:'#F{$dp.$D(\'timeEndStr\',{d:-30})}'});"/>
            </li>
            <li>
                <label>结束日期</label>
                <input name="timeEndStr" type="text" class="scinput Wdate" id="timeEndStr" value="${timeEndStr}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,minDate:'#F{$dp.$D(\'timeStartStr\')}',maxDate:'#F{$dp.$D(\'timeStartStr\',{d:+30})}' });"/>
            </li>
            <li>
                <label></label>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="stationDetails.htm"/>
            </li>
        </form>
    </ul>
    <div style="width: 100%;float: left;">
        <div style="width:550px;height:400px;padding: 10px 30px;float: left ">
            <div id="temperature" style="width:550px;height:400px;float: left;"></div>
        </div>
        <div style="width:550px;height:400px;padding: 10px 30px;float: left ">
            <div id="voltage" style="width:550px;height:400px;float: left"></div>
        </div>
        <div style="width:550px;height:400px;padding: 10px 30px;float: left ">
            <div id="energy" style="width:550px;height:400px;float: left"></div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp" %>
<script src="${path}/js/echarts.js"></script>
<script>
    var colors = ['#5793f3', '#d14a61', '#675bba'];
    var myChart2Temperature = echarts.init(document.getElementById('temperature'));
    var myChart2Voltage = echarts.init(document.getElementById('voltage'));
    var myChart2Energy = echarts.init(document.getElementById('energy'));
    $(function () {
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        });
        myChart2Temperature.on("dataZoom", function (params) {
            start = params.start;
            end = params.end;
            myChart2Voltage.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            });
            myChart2Energy.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            })
        })
        myChart2Voltage.on("dataZoom", function (params) {
            start = params.start;
            end = params.end;
            myChart2Temperature.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            });
            myChart2Energy.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            })
        })
        myChart2Energy.on("dataZoom", function (params) {
            start = params.start;
            end = params.end;
            myChart2Voltage.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            });
            myChart2Temperature.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            })
        })
        //电站id
        var stationId = '${stationId}';
        // 数据信息
        var stationRealTimeListens = '${stationRealTimeListens}';
        //电站温度 °C
        var temperatureValue = [];
        //电源电压 V
        var voltageValue = [];
        //电能量  kwh
        var energyValue = [];
        //数据时间
        var dataTime = [];
        <c:forEach var="i" items="${stationRealTimeListens}">
        temperatureValue.push(${i.temperature});
        voltageValue.push(${i.voltage});
        energyValue.push(${i.energy});
        dataTime.push('<fmt:formatDate value='${i.dataTime}' pattern='HH:mm:ss'/>')
        </c:forEach>
        temperatureEcharts(dataTime, temperatureValue);
        voltageEcharts(dataTime, voltageValue);
        energyEcharts(dataTime, energyValue);
    })

    /**
     * 电站温度
     * @param data
     * @param temperatureValue
     */
    function temperatureEcharts(data, temperatureValue) {
        option = {
            color: colors,

            tooltip: {
                trigger: 'none',
                axisPointer: {
                    type: 'cross'
                }
            },
            legend: {
                data: ['电站温度']
            },
            grid: {
                top: 70,
                bottom: 50
            },
            xAxis: [
                {
                    type: 'category',
                    axisTick: {
                        alignWithLabel: false
                    },
                    axisLine: {
                        onZero: false
                    },
                    axisPointer: {
                        label: {
                            formatter: function (params) {
                                return '温度'
                                    + (params.seriesData.length ? '：' + params.seriesData[0].data + '°C' : '');
                            }
                        }
                    },
                    data: data,
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 10
            }, {
                start: 0,
                end: 10,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            series: [
                {
                    name: '电站温度',
                    type: 'line',
                    smooth: true,
                    data: temperatureValue,

                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart2Temperature.setOption(option);
    }

    /**
     * 电站电压
     * @param data
     * @param voltageValue
     */
    function voltageEcharts(data, voltageValue) {
        option = {
            color: colors,

            tooltip: {
                trigger: 'none',
                axisPointer: {
                    type: 'cross'
                }
            },
            legend: {
                data: ['电站电压']
            },
            grid: {
                top: 70,
                bottom: 50
            },
            xAxis: [
                {
                    type: 'category',
                    axisTick: {
                        alignWithLabel: false
                    },
                    axisLine: {
                        onZero: false
                    },
                    axisPointer: {
                        label: {
                            formatter: function (params) {
                                return '电压'
                                    + (params.seriesData.length ? '：' + params.seriesData[0].data + 'V' : '');
                            }
                        }
                    },
                    data: data,
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 10
            }, {
                start: 0,
                end: 10,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            series: [
                {
                    name: '电站电压',
                    type: 'line',
                    smooth: true,
                    data: voltageValue,

                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart2Voltage.setOption(option);
    }

    /**
     * 电能量
     * @param data
     * @param energyValue
     */
    function energyEcharts(data, energyValue) {
        option = {
            color: colors,

            tooltip: {
                trigger: 'none',
                axisPointer: {
                    type: 'cross'
                }
            },
            legend: {
                data: ['电站电能量']
            },
            grid: {
                top: 70,
                bottom: 50
            },
            xAxis: [
                {
                    type: 'category',
                    axisTick: {
                        alignWithLabel: false
                    },
                    axisLine: {
                        onZero: false
                    },
                    axisPointer: {
                        label: {
                            formatter: function (params) {
                                return '电能量'
                                    + (params.seriesData.length ? '：' + params.seriesData[0].data + 'KWH' : '');
                            }
                        }
                    },
                    data: data,
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 10
            }, {
                start: 0,
                end: 10,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            series: [
                {
                    name: '电站电能量',
                    type: 'line',
                    smooth: true,
                    data: energyValue,

                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart2Energy.setOption(option);
    }
    /**
     * 居中
     */
    function postion() {
        var w = $(window).width();
        var tw = $('#temperature').width();
        var leftValue = (w - tw) / 2;
        if (w < 1600) {
            $('#temperature').css({
                left: leftValue + 'px',
            })
            $('#voltage').css({
                left: leftValue + 'px',
            })
            $('#energy').css({
                left: leftValue + 'px',
            })
        } else {
            $('#temperature').css({
                left: 0 + 'px',
            })
            $('#voltage').css({
                left: 0 + 'px',
            })
            $('#energy').css({
                left: 0 + 'px',
            })
        }
    }
    /**
     * 返回 HH：mm:ss
     */
    function dateHous(date) {
        var myDate = new Date(date)
        var hh = myDate.getHours();
        var mm = myDate.getMinutes();
        var ss = myDate.getSeconds();
        return hh + ":" + mm + ":" + ss;
    }
</script>