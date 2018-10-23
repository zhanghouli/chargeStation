<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">资金管理</a></li>
            <li><a href="#">财务管理</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">

    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <%--#25486a--%>
            <input name="day" id="day" type="hidden" value="${accountVo.day}">
            <li>
                <input name="" type="button" class="scbtn financial month"
                       <c:if test="${accountVo.day=='month'}">style="background-color: #3398DB"</c:if> value="本月"
                       action="financialInfo.htm"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn financial week"
                       <c:if test="${accountVo.day=='week'}">style="background-color: #3398DB"</c:if> value="本周"
                       action="financialInfo.htm"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn financial yesterday"
                       <c:if test="${accountVo.day=='yesterday'}">style="background-color: #3398DB"</c:if> value="昨日"
                       action="financialInfo.htm"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn financial today"
                       <c:if test="${accountVo.day=='today'}">style="background-color: #3398DB"</c:if> value="今日"
                       action="financialInfo.htm"/>
            </li>
            <li><label>起始时间</label>
                <input name="timeStartStr" id="timeStartStr" value="${accountVo.timeStartStr}" type="text"
                       class="scinput Wdate"
                       onfocus="WdatePicker()"/>
            </li>
            <li><label>结束时间</label>
                <input name="timeEndStr" id="timeEndStr" value="${accountVo.timeEndStr}" type="text"
                       class="scinput Wdate"
                       onfocus="WdatePicker()"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="financialInfo.htm"/>
            </li>
            <li style="float: right">
                <input name="" type="button" class="scbtn financial operator" value="运营商"
                       action="financialOperator.htm"/>
            </li>
            <li style="float: right">
                <input name="" type="button" class="scbtn financial estate" value="物业" action="financialEstate.htm"/>
            </li>
        </form>
    </ul>
    <div id="myChart2" style="width:90%;height:500px;margin-top: 40px;"></div>

</div>
<%@include file="../footer.jsp" %>
<script type="text/javascript" src="../js/3rd/date/date.js"></script>
<script src="${path}/js/echarts.js"></script>
<script>
    $(function () {
        //查询
        $('._searchBtn').on('click', function () {
            submitDate();
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })
        //跳转运营商
        $('.operator').on('click', function () {
            location.href = "${path}/account/" + $(this).attr("action")
        })
        //跳转物业
        $('.estate').on('click', function () {
            location.href = "${path}/account/" + $(this).attr("action")
        })

    })
    var myChart2 = echarts.init(document.getElementById('myChart2'));
    option = {
        title: {
            text: '系统收入分配图',
            left:'50'
        },
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: ['电费', '平台管理费', '投资方管理费', '运营商管理费', '物业管理费'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '收入金额',
                type: 'bar',
                barWidth: '60%',
                data: ['${financial.electricityFees/100}', '${financial.platformFees/100}', '${financial.investorFees/100}', '${financial.operatorFees/100}', '${financial.estateFees/100}']
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart2.setOption(option);
</script>