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
            <li>
                <a href="${path}/station/stationPortList.htm?stationId=${stationId}&estateId=${estateId}&operatorId=${operatorId}">电口列表</a>
            </li>
            <li><a href="#">单个电口信息</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>

<div class="formbody">

    <div class="formtitle"><span>电口信息</span></div>

    <ul class="forminfo short validationEngineContainer">
        <li>
            <label>电站名称:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${stationPortInfoVo.stationName}" type="text" disabled/>
        </li>
        <li>
            <label>电站编号:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${stationPortInfoVo.numberStation}" type="text" disabled/>
        </li>
        <li>
            <label>电站详细地址:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${stationPortInfoVo.stationAddress}" type="text" disabled/>
        </li>
        <li>
            <label>运营商:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${stationPortInfoVo.operatorRealName}" type="text" disabled/>
        </li>
        <li>
            <label>物业:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${stationPortInfoVo.estateName}" type="text" disabled/>
        </li>
        <li>
            <label>物业联系电话:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${stationPortInfoVo.estatePhone}" type="text" disabled/>
        </li>
        <li>
            <label>充电口序号:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${stationPortInfoVo.seq}" type="text" disabled/>
        </li>
        <li>
            <label>充电口编号:</label>
            <input readonly="readonly" style="width: 250px"
                   value="${stationPortInfoVo.numberPort}" type="text" disabled/>
        </li>
    </ul>
</div>
<!-- 每日电口电量输出-->
<div class="formbody" style="float: left;width: 100%">
    <ul class="seachform" style="padding: 10px 10px;">
        <form method="get" action="" id="searchForm">
            <input name="stationPortId" type="hidden" value="${stationPortId}">
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询" />
            </li>
        </form>
    </ul>
    <div id="myChart2" style="width:90%;height:500px;"></div>
</div>
<!--电流 功率 电能 -->
<div class="formbody" style="float: left">
    <ul class="seachform" style="padding: 10px 10px;">
        <form method="get" action="">
            <li>
                <label>起始日期</label>
                <input name="timeStartStrPort" type="text" class="scinput Wdate" id="timeStartStrPort"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,maxDate:'#F{$dp.$D(\'timeEndStrPort\')}',minDate:'#F{$dp.$D(\'timeEndStrPort\',{d:-30})}'});"/>
            </li>
            <li>
                <label>结束日期</label>
                <input name="timeEndStrPort" type="text" class="scinput Wdate" id="timeEndStrPort"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd' ,minDate:'#F{$dp.$D(\'timeStartStrPort\')}',maxDate:'#F{$dp.$D(\'timeStartStrPort\',{d:+30})}' });"/>
            </li>
            <li>
                <label></label>
                <input name="" type="button" class="scbtn _searchBtnPort" value="查询"/>
            </li>
        </form>
    </ul>
    <div style="width:550px;height:400px;padding: 10px 30px;float: left ">
        <div id="current" style="width:550px;height:400px;float: left;"></div>
    </div>
    <div style="width:550px;height:400px;padding: 10px 30px;float: left ">
        <div id="power" style="width:550px;height:400px;float: left"></div>
    </div>
    <div style="width:550px;height:400px;padding: 10px 30px;float: left ">
        <div id="energy" style="width:550px;height:400px;;float: left;"></div>
    </div>
</div>
<%@include file="../footer.jsp" %>
<script src="${path}/js/echarts.js"></script>
<script>
    var colors = ['#5793f3', '#d14a61', '#675bba'];
    var myChartCurrent = echarts.init(document.getElementById('current'));
    var myChartPower = echarts.init(document.getElementById('power'));
    var myChartEnergy = echarts.init(document.getElementById('energy'));
    //插座电流 A
    var currentValue = [];
    //功率 W
    var powerValue = [];
    //电能量  kwh
    var energyValue = [];
    //数据时间
    var dataTime = [];
    //dataZoom 起始 与结束
    var start = 0;
    var end = 0;
    var dateFormat = function (timestamp, formats) {
        // formats格式包括
        // 1. Y-m-d
        // 2. Y-m-d H:i:s
        // 3. Y年m月d日
        // 4. Y年m月d日 H时i分
        formats = formats || 'Y-m-d';

        var zero = function (value) {
            if (value < 10) {
                return '0' + value;
            }
            return value;
        };

        var myDate = timestamp ? new Date(timestamp) : new Date();

        var year = myDate.getFullYear();
        var month = zero(myDate.getMonth() + 1);
        var day = zero(myDate.getDate());

        var hour = zero(myDate.getHours());
        var minite = zero(myDate.getMinutes());
        var second = zero(myDate.getSeconds());

        return formats.replace(/Y|m|d|H|i|s/ig, function (matches) {
            return ({
                Y: year,
                m: month,
                d: day,
                H: hour,
                i: minite,
                s: second
            })[matches];
        });
    };
    $(function () {
        //居中
        myChart2();
        // 电流  功率  电能 图
        getStationPortRealTimeListen(dateFormat(), dateFormat());
        $('input[name="timeStartStrPort"]').val(dateFormat());
        $('input[name="timeEndStrPort"]').val(dateFormat());
        //电流  功率  电能图  日期查询
        $('._searchBtnPort').on('click', function () {
            getStationPortRealTimeListen($('input[name="timeStartStrPort"]').val(), $('input[name="timeEndStrPort"]').val());
        })
        //每日总电流
        getStationPortDateSum();
        //每日总电流  查询
        $('._searchBtn').on('click', function () {
            getStationPortDateSum($('input[name="timeStartStr"]').val(), $('input[name="timeEndStr"]').val());
        })
        //电流滑动
        myChartCurrent.on('dataZoom', function (params) {
            start = params.start;
            end = params.end;
            myChartPower.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            });
            myChartEnergy.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            })
        })
        //功率滑动
        myChartPower.on('dataZoom', function (params) {
            start = params.start;
            end = params.end;
            myChartCurrent.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            });
            myChartEnergy.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            })
        })
        //电能滑动
        myChartEnergy.on('dataZoom', function (params) {
            start = params.start;
            end = params.end;
            myChartCurrent.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            });
            myChartPower.setOption({
                dataZoom: [{
                    type: 'inside',
                    start: start,
                    end: end
                }]
            })
        })
    })
    /**
     * 电口电流
     * @param data
     * @param temperatureValue
     */
    function currentEcharts(data, currentValue) {
        option = {
            tooltip: {
                trigger: 'axis',
                position: function (pt) {
                    return [pt[0], '10%'];
                }
            },
            title: {
                left: 'center',
                text: '电流图',
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: data,
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '100%']
            },
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
                    name: '电流',
                    type: 'line',
                    data: currentValue
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChartCurrent.setOption(option);
    }
    /**
     * 电口功率
     * @param data
     * @param voltageValue
     */
    function powerEcharts(data, powerValue) {
        option = {
            tooltip: {
                trigger: 'axis',
                position: function (pt) {
                    return [pt[0], '10%'];
                }
            },
            title: {
                left: 'center',
                text: '电功率',
            },
            toolbox: {
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    },
                    restore: {},
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: data,
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '100%']
            },
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
                    name: '电功率',
                    type: 'line',
                    data: powerValue
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChartPower.setOption(option);
    }
    /**
     * 电能量
     * @param data
     * @param energyValue
     */
    function energyEcharts(data, energyValue) {

        option = {
            tooltip: {
                trigger: 'axis',
                position: function (pt) {
                    return [pt[0], '10%'];
                }
            },
            title: {
                left: 'center',
                text: '电能量',
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: data,
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '100%']
            },
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
                    name: '电能量',
                    type: 'line',
                    data: energyValue
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChartEnergy.setOption(option);
    }
    /**
     * 总电流图
     */
    function myChart2(date, power) {
        var myChart2 = echarts.init(document.getElementById('myChart2'));
        option = {
            title: {
                text: '每日电口电量输出统计'
            },
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: date
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: "(kwh)",
                    type: 'line',
                    stack: '总量',
                    data: power
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart2.setOption(option);
    }

    function getStationPortRealTimeListen(startTime, endTime) {
        startTime = startTime || null;
        endTime = endTime || null;
        $.ajax({
            url: "${path}/station/getStationPortListenByPortId.htm?portId=${stationPortId}",
            dataType: "json",
            data: {
                timeStartStr: startTime,
                timeEndStr: endTime
            },
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
                    dataTime.push(dateFormat(list[i].creationTime, "Y-m-d H:i:s"))
                }
                currentEcharts(dataTime, currentValue);
                powerEcharts(dataTime, powerValue);
                energyEcharts(dataTime, energyValue);
            }
        })
    }

    function getStationPortDateSum(startTime, endTime) {
        startTime = startTime || null;
        endTime = endTime || null;
        $.ajax({
            url: "${path}/station/getStationPortDateSum.htm?portId=${stationPortId}",
            dataType: "json",
            data: {
                timeStartStr: startTime,
                timeEndStr: endTime
            },
            success: function (r) {
                var list = r.result.list;
                var power = [];
                dataTime = [];
                if (list.length > 0) {
                    $('input[name="timeStartStr"]').val(dateFormat(list[0].date));
                    $('input[name="timeEndStr"]').val(dateFormat(list[list.length - 1].date));
                    for (var i = 0; i < list.length; i++) {
                        power.push(list[i].power);
                        dataTime.push(dateFormat(list[i].date, "Y/m/d"));
                    }
                }
                myChart2(dataTime, power);
            }
        })
    }

</script>