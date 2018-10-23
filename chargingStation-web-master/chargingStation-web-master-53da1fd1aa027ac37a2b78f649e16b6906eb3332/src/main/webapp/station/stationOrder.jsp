<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <li><a href="#">电口充值记录列表</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <input name="stationPortId" type="hidden" value="${stationPortId}"/>
            <li><label>关键字</label>
                <input name="keyword" type="text" class="scinput" value="${keyword}"/>
            </li>
            <li><label>起始时间</label>
                <input name="timeStartStr" type="text" class="scinput Wdate" value="${timeStartStr}"
                       onfocus="WdatePicker()"/>
            </li>
            <li><label>结束时间</label>
                <input name="timeEndStr" type="text" class="scinput Wdate" value="${timeEndStr}"
                       onfocus="WdatePicker()"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="stationOrder.htm"/>
            </li>
        </form>
    </ul>
    <table class="tablelist">
        <thead>
        <tr>
            <th>运营商名称</th>
            <th>车主姓名</th>
            <th>电站名称</th>
            <th>电口编号</th>
            <th>支付金额(元)</th>
            <th>充值时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td>${i.operatorName}</td>
                <td>${i.carOwnerName}</td>
                <td>${i.stationName}</td>
                <td>${i.portName}</td>
                <td><fmt:formatNumber type="number" value='${i.payment/100}' pattern="￥0.00"/></td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td>
                    <a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">${page}</div>
</div>
<%@include file="../footer.jsp" %>
<script>
    $(function () {
        //search
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })
        // remove
        $('._removeBtn').on('click', function () {
            var data = $(this).parents('tr').attr('data');
            var orderId = data;
            $.zxxbox.ask('确定要删除?', function () {
                $K.http('doRemoveOrder.htm', {
                    orderId: orderId
                }, function (r) {
                    $.zxxbox.remind("操作成功。", null, {
                        delay: 3000,
                        onclose: function () {
                            location.reload();
                        }
                    });
                })
            })
        });
    })
</script>