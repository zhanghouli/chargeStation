<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">消息中心</a></li>
            <li><a href="#">接收列表</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <li><label>关键字</label>
                <input name="keyword" type="text" class="scinput" value="${keyword}"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="cmppSmsReceiveList.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <th>手机号</th>
            <th>内容</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td>${i.phone}</td>
                <td><p class="mlellipsis" style="white-space: normal;text-overflow: ellipsis"
                       mlellipsis="1">${i.content}</p></td>
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
        //_searchBtn
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })
        $('._removeBtn').on('click', function () {
            var data = $(this).parents('tr').attr('data');
            $.zxxbox.ask('确定要删除?', function () {
                $K.http('removeCmppSmsReceiveList.htm', {
                    cmppSmsReceiveId: data
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