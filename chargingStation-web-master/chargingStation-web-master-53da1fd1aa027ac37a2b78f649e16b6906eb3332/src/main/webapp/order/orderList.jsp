<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">电站管理</a></li>
            <li><a href="#">电口充值记录列表</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <li>
                <label>订单状态</label>
                <div class="vocation">
                    <select name="status">
                        <option value="">全部</option>
                        <c:forEach var="s" items="${ORDER_STATUS}">
                            <option value="${s.value}"
                                    <c:if test="${s.value == status}">selected</c:if> >${s.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li><label>关键字查询</label>
                <input name="keyword" type="text" class="" value="${keyword}"/>
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="orderList.htm"/>
            </li>
        </form>
    </ul>
    <table class="tablelist">
        <thead>
        <tr>
            <th>车主/手机</th>
            <th>电站名称</th>
            <th>电站编号</th>
            <th>电口编号</th>
            <th style="width: 54px">订单金额</th>
            <th style="width: 94px">实际充电时间(分)</th>
            <th>订单状态</th>
            <%--<th>结束原因</th>--%>
            <th>开单时间</th>
            <th>结束时间</th>
            <th style="width: 35px">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td>${i.carownerName}/${i.carownerPhone}</td>
                <td>${i.stationName}</td>
                <td>${i.stationNumber}</td>
                <td>${i.stationPort + 1}</td>
                <td>${i.payment/100}</td>
                <td>${i.actualTime}</td>
                <td>
                    <c:if test="${i.orderStatus == ORDER_STATUS_WAITING_FOR_PAY.value}">${ORDER_STATUS_WAITING_FOR_PAY.name}</c:if>
                    <c:if test="${i.orderStatus == ORDER_STATUS_WAITING_FOR_CONNECT.value}">${ORDER_STATUS_WAITING_FOR_CONNECT.name}</c:if>
                    <c:if test="${i.orderStatus == ORDER_STATUS_RECHARGING.value}">${ORDER_STATUS_RECHARGING.name}</c:if>
                    <c:if test="${i.orderStatus == ORDER_STATUS_FINISHED.value}">${ORDER_STATUS_FINISHED.name}</c:if>
                    <c:if test="${i.orderStatus == ORDER_STATUS_CLOSE.value}">${ORDER_STATUS_CLOSE.name}</c:if>
                    <c:if test="${i.remark != null}">[${i.remark}]</c:if>
                </td>
                    <%--<td></td>--%>
                <td><fmt:formatDate value='${i.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td>
                    <c:if test="${i.orderStatus == ORDER_STATUS_FINISHED.value || i.orderStatus == ORDER_STATUS_CLOSE.value}">
                        <fmt:formatDate value='${i.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
                    </c:if>
                    <c:if test="${i.orderStatus != ORDER_STATUS_FINISHED.value && i.orderStatus != ORDER_STATUS_CLOSE.value}">
                            /
                    </c:if>
                </td>
                <td>
                    <c:if test="${i.orderStatus == ORDER_STATUS_RECHARGING.value}">
                        <a href="${path}/order/orderListDetails.htm?carOwnerId=${i.carownerId}&&stationPortId=${i.stationPortId}&&orderId=${i.id}"
                           class="tablelink ">详情</a>&nbsp;
                    </c:if>
                    <c:if test="${i.orderStatus == ORDER_STATUS_FINISHED.value || i.orderStatus == ORDER_STATUS_FINISHED.value || i.orderStatus == ORDER_STATUS_CLOSE.value}">
                        <a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;
                    </c:if>
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