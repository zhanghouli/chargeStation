<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">意见反馈</a></li>
            <li><a href="#">反馈列表</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <li>
                <label>反馈类型</label>
                <div class="vocation">
                    <select name="type">
                        <c:forEach var="s" items="${FEED_BACK}">
                            <option value="${s.value}"
                                    <c:if test="${s.value == type}">selected</c:if> >${s.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="feedBackList.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <%--<th>意见标题</th>--%>
            <th style="width: 30%">意见内容</th>
            <th>反馈人姓名</th>
            <th>反馈类型</th>
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
                    <%--<td>${i.title}</td>--%>
                <td>
                    <p class="mlellipsis" style="white-space: normal;text-overflow: ellipsis"
                       mlellipsis="1">${i.content}</p>
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
                <td>${i.passportName}</td>
                <td>
                    <c:if test="${FEED_BACK_OPERATOR.value == i.type}">
                        <font>${FEED_BACK_OPERATOR.name}</font>
                    </c:if>
                    <c:if test="${FEED_BACK_CAROWNER.value == i.type}">
                        <font>${FEED_BACK_CAROWNER.name}</font>
                    </c:if>
                    <c:if test="${FEED_BACK_PROPERTY.value == i.type}">
                        <font>${FEED_BACK_PROPERTY.name}</font>
                    </c:if>
                    <c:if test="${FEED_BACK_GOVERNMENT.value == i.type}">
                        <font>${FEED_BACK_GOVERNMENT.name}</font>
                    </c:if>
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
                    <a href="${path}/feedback/feedBackInfo.htm?feedbackId=${i.id}"
                       class="tablelink _modifyBtn">查阅反馈意见</a>&nbsp;
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
                $K.http("removeFeedback.htm", {
                    feedbackId: id
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