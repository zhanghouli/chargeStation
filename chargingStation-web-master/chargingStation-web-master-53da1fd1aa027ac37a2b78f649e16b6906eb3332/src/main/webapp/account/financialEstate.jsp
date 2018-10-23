<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="${path}/account/financialInfo.htm">财务管理</a></li>
            <li><a href="#">物业分配</a></li>
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
                       action="financialEstate.htm"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn financial week"
                       <c:if test="${accountVo.day=='week'}">style="background-color: #3398DB"</c:if> value="本周"
                       action="financialEstate.htm"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn financial yesterday"
                       <c:if test="${accountVo.day=='yesterday'}">style="background-color: #3398DB"</c:if> value="昨日"
                       action="financialEstate.htm"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn financial today"
                       <c:if test="${accountVo.day=='today'}">style="background-color: #3398DB"</c:if> value="今日"
                       action="financialEstate.htm"/>
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
            <li><label>关键字</label>
                <input name="keyword" type="text" class="scinput" value="${accountVo.keyword}"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="financialEstate.htm"/>
            </li>
        </form>
    </ul>
    <table class="tablelist">
        <thead>
        <tr>
            <th>物业姓名</th>
            <th>物业手机号</th>
            <th>物业地址</th>
            <th>状态</th>
            <th>收入金额</th>
            <%--<th style="width: 20%">处理结果</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr>
                <td>${i.passportOrEstateVo.contactName}</td>
                <td>${i.passportOrEstateVo.contactPhone}</td>
                <td>${i.passportOrEstateVo.address}</td>
                <td>
                    <c:if test="${PASSPORT_STATUS_NORMAL.value == i.passportOrEstateVo.status}">
                        <font class="color-green">正常</font>
                    </c:if>
                    <c:if test="${PASSPORT_STATUS_DISABLE.value == i.passportOrEstateVo.status}">
                        <font class="color-red">禁用</font>
                    </c:if>
                </td>
                <td>
                    <c:if test="${i.accountSum == null}">
                        ￥0.00
                    </c:if>
                    <c:if test="${i.accountSum != null}">
                        <fmt:formatNumber type="number" value='${i.accountSum/100}' pattern="￥0.00"/>
                    </c:if>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">${page}</div>

</div>
<%@include file="../footer.jsp" %>
<script type="text/javascript" src="../js/3rd/date/date.js"></script>
<script src="${path}/js/echarts.js"></script>
<script>
    $(function () {
        $('._searchBtn').on('click', function () {
            submitDate();
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })
    })

</script>