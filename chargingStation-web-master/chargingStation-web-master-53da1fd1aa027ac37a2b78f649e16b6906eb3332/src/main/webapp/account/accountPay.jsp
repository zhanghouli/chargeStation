<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">资金管理</a></li>
            <li><a href="#">消费记录</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">

    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <%--<li>--%>
            <%--<label>状态</label>--%>
            <%--<div class="vocation">--%>
            <%--<select name="type">--%>
            <%--<c:forEach var="s" items="${ACCOUNT}">--%>
            <%--<option value="${s.value}"--%>
            <%--<c:if test="${s.value == type}">selected</c:if> >${s.name}</option>--%>
            <%--</c:forEach>--%>
            <%--</select>--%>
            <%--</div>--%>
            <%--</li>--%>
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="accountPay.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <th>消费用户名称</th>
            <th>消费金额</th>
            <th>消费前金额</th>
            <th>消费后金额</th>
            <th>备注</th>
            <th>创建时间</th>
            <%--<th>操作</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                    <%--<td>暂无图片</td>--%>
                <td>${i.realName}</td>
                <td><fmt:formatNumber  type="number" value='${i.amount/100}' pattern="￥0.00"/></td>
                <td><fmt:formatNumber  type="number" value='${i.amountBefore/100}' pattern="￥0.00"/></td>
                <td><fmt:formatNumber  type="number" value='${i.amountAfter/100}' pattern="￥0.00"/></td>
                <td>${i.remark}</td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <%--<td>--%>
                    <%--<a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;--%>
                <%--</td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">${page}</div>
</div>
<%@include file="../footer.jsp" %>

<script>
    $(function () {
        //_searchBtn
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })


    })
</script>