<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">故障报修</a></li>
            <li><a href="#">故障电站</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <li>
                <label>查阅状态</label>
                <div class="vocation">
                    <select name="isViewed">
                        <c:forEach var="s" items="${FEED_BACK_ISVIEWED}">
                            <option value="${s.value}"
                                    <c:if test="${s.value == isViewed}">selected</c:if> >${s.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li><label>关键字</label>
                <input name="keyword" type="text" class="scinput" value="${keyword}"/>
            </li>
            <%--<li><label>起始时间</label>--%>
            <%--<input name="timeStartStr" type="text" class="scinput Wdate"--%>
            <%--onfocus="WdatePicker()"/>--%>
            <%--</li>--%>
            <%--<li><label>结束时间</label>--%>
            <%--<input name="timeEndStr" type="text" class="scinput Wdate"--%>
            <%--onfocus="WdatePicker()"/>--%>
            <%--</li>--%>
            <li>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="stationFaultList.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <th>故障电站</th>
            <th>电站地址</th>
            <th style="width: 30%">意见内容</th>
            <th>查阅状态</th>
            <%--<th style="width: 20%">处理结果</th>--%>
            <th>创建时间</th>
            <th>修改时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                    <%--<td>暂无图片</td>--%>
                <td>${i.stationName}</td>
                        <td>${i.address}</td>
                <td>
                    <p class="mlellipsis" style="white-space: normal;text-overflow: ellipsis"
                       mlellipsis="1">${i.remark}</p>
                    <c:forEach var="picture" items="${i.picList}" varStatus="status">
                    <div class="words_down_pic">
                        <c:choose>
                            <c:when test="${ISDEV}">
                                <a rel="${i.id}" href="${path}${files}${picture}" class="fancy-box">
                                    <img class="lazy _avatar"
                                         data-original="${path}${files}${picture==null?'../images/default-image.png':picture}"
                                         height="80px" width="70px"/>
                                </a>
                            </c:when>
                            <c:when test="${!ISDEV}">
                                <a rel="${i.id}" href="${path}${picture}" class="fancy-box">
                                    <img class="lazy _avatar"
                                         data-original="${path}${picture==null?'../images/default-image.png':picture}"
                                         height="80px" width="70px"/>
                                </a>
                            </c:when>
                        </c:choose>
                    </div>
                    </c:forEach>
                </td>
                <td>
                    <c:if test="${'true'== i.isViewed}">
                        <font class="color-green">已查阅</font>
                    </c:if>
                    <c:if test="${'false' == i.isViewed}">
                        <font class="color-red">未查阅</font>
                    </c:if>
                </td>
                    <%--<td>--%>
                    <%--<p mlellipsis="1" class="mlellipsis"--%>
                    <%--style="white-space: normal;text-overflow: ellipsis">${i.bizDealResult}</p>--%>
                    <%--</td>--%>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td><fmt:formatDate value='${i.modifyTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td>
                    <a href="${path}/station/stationFaultInfo.htm?stationFaultId=${i.id}"
                       class="tablelink _modifyBtn">查阅故障信息</a>&nbsp;
                    <a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">${page}</div>
</div>
</div>
<%@include file="../footer.jsp" %>
<script>
    $(function () {
        //search
        $('._searchBtn').on("click", function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })
        //remove
        $('._removeBtn').on("click", function () {
            var id = $(this).parents('tr').attr("data");
            $.zxxbox.ask("确定删除？", function () {
                $K.http("removeStationFault.htm", {
                    stationFaultId: id
                }, function (r) {
                    $.zxxbox.remind("操作成功。", null, {
                        delay: 3000,
                        onclose: function () {
                            location.reload();
                        }
                    })
                })
            })
        })
    })
</script>