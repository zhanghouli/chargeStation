<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">充值记录管理</a></li>
            <li><a href="#">详情信息</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>

<div class="formbody">
    <div class="formtitle"><span>订单信息</span></div>

    <ul class="forminfo short validationEngineContainer">
        <li>
            <label>车主名称:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${passport.userName}" type="text" disabled/>
        </li>
        <li>
            <label>车主手机号:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${passport.phone}" type="text" disabled/>
        </li>
        <li>
            <label>电站名称:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${station.name}" type="text" disabled/>
        </li>
        <li>
            <label>电站编号:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${station.number}" type="text" disabled/>
        </li>
        <li>
            <label>电站详细地址:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${station.address}" type="text" disabled/>
        </li>
        <li>
            <label>充电口编号:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${stationPort.number}" type="text" disabled/>
        </li>
        <li>
            <label>订单金额:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${order.payment/100}" type="text" disabled/>
        </li>
        <li>
            <label>实际充电时间:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${order.actualTime}" type="text" disabled/>
        </li>
        <li>
            <label>订单状态:</label>
            <input readonly="readonly" style="width: 250px;display: inline"
                   value="${statusName}" type="text" disabled/>
        </li>
    </ul>
    <div style="width: 100%;float: left;margin-top: 50px;">
        <div id="current" style="width:550px;height:400px;float: left;margin-top: 16px;"></div>
        <br>
        <div id="power" style="width:550px;height:400px;float: left"></div>
        <br>
        <div id="energy" style="width:550px;height:400px;;float: left;margin-top: -16px;"></div>
    </div>

</div>
<%@include file="../footer.jsp" %>
<script src="${path}/js/echarts.js"></script>
<script>
    var colors = ['#5793f3', '#d14a61', '#675bba'];

    $(function () {
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })
        //居中
//        myChart2();
//        $(window).resize(function () {
//            myChart2();
//        });

        //插座电流 A
        var currentValue = [];
        //功率 W
        var powerValue = [];
        //电能量  kwh
        var energyValue = [];
        //数据时间
        var dataTime = [];
        <c:forEach var="i" items="${stationPortRealTimeListenList}">
        currentValue.push(${i.current});
        powerValue.push(${i.power});
        energyValue.push(${i.energy});
        dataTime.push('<fmt:formatDate value='${i.creationTime}' pattern='HH:mm:ss'/>')
        </c:forEach>
        currentEcharts(dataTime, currentValue);
        powerEcharts(dataTime, powerValue);
        energyEcharts(dataTime, energyValue);
    })

    setInterval(function () {
        $.ajax({
            url: "${path}/station/orderStationPortDetails.htm?portId=${stationPort.id}",
            dataType: "json",
            success: function (r) {
                var list = r.result.list;
                currentValue = [];
                powerValue = [];
                energyValue = [];
                dataTime = [];
                for (var i = 0; i < list.length; i++) {
                    currentValue.push(list[i].current);
                    powerValue.push(list[i].power);
                    energyValue.push(list[i].energy);
                    dataTime.push(dateHous(list[i].creationTime))
                }
                currentEcharts(dataTime, currentValue);
                powerEcharts(dataTime, powerValue);
                energyEcharts(dataTime, energyValue);
            }
        })
    }, 30000);

    /**
     * 电口电流
     * @param data
     * @param temperatureValue
     */
    function currentEcharts(data, currentValue) {
        var myChart2 = echarts.init(document.getElementById('current'));
        option = {
            color: colors,

            tooltip: {
                trigger: 'none',
                axisPointer: {
                    type: 'cross'
                }
            },
            legend: {
                data: ['电口电流']
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
                                return '电流'
                                    + (params.seriesData.length ? '：' + params.seriesData[0].data + 'A' : '');
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
            series: [
                {
                    name: '电口电流',
                    type: 'line',
                    smooth: true,
                    data: currentValue,

                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart2.setOption(option);
    }
    /**
     * 电口功率
     * @param data
     * @param voltageValue
     */
    function powerEcharts(data, powerValue) {
        var myChart2 = echarts.init(document.getElementById('power'));
        option = {
            color: colors,

            tooltip: {
                trigger: 'none',
                axisPointer: {
                    type: 'cross'
                }
            },
            legend: {
                data: ['电口功率']
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
                                return '功率'
                                    + (params.seriesData.length ? '：' + params.seriesData[0].data + 'W' : '');
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
            series: [
                {
                    name: '电口功率',
                    type: 'line',
                    smooth: true,
                    data: powerValue,

                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart2.setOption(option);
    }
    /**
     * 电能量
     * @param data
     * @param energyValue
     */
    function energyEcharts(data, energyValue) {
        var myChart2 = echarts.init(document.getElementById('energy'));
        option = {
            color: colors,

            tooltip: {
                trigger: 'none',
                axisPointer: {
                    type: 'cross'
                }
            },
            legend: {
                data: ['电口电能量']
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
            series: [
                {
                    name: '电口电能量',
                    type: 'line',
                    smooth: true,
                    data: energyValue,

                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart2.setOption(option);
    }
    /**
     * 居中
     */
    function postion() {
        var w = $(window).width();
        var tw = $('#current').width();
        var leftValue = (w - tw) / 2;
        if (w < 1600) {
            $('#current').css({
                left: leftValue + 'px',
            })
            $('#power').css({
                left: leftValue + 'px',
            })
            $('#energy').css({
                left: leftValue + 'px',
            })
        } else {
            $('#current').css({
                left: 0 + 'px',
            })
            $('#power').css({
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