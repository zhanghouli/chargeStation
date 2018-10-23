<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">支付配置管理</a></li>
            <li><a href="#">支付宝配置列表</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <%--<div class="tools">--%>
        <%--<ul class="toolbar">--%>
            <%--<a href="aliPayInfo.htm">--%>
                <%--<li class="click"><span><img src="${path}/images/t01.png"/></span>添加</li>--%>
            <%--</a>--%>
        <%--</ul>--%>
    <%--</div>--%>

    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <li>
                <label>关键字</label>
                <input name="keyword" type="text" class="scinput" value="${keyword}"/>
            </li>
            <li>
                <label>&nbsp;</label>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="aliPayList.htm"/>
            </li>
        </form>
    </ul>
    <table class="imgtable">
        <thead>
        <tr>
            <th width="10%">app_id</th>
            <th width="10%">notify_url</th>
            <th width="10%">return_url</th>
            <th width="10%">private_key</th>
            <th width="10%">public_key</th>
            <th width="15%">创建时间</th>
            <th width="15%">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}" height="50">
                <td>${i.appId}</td>
                <td><p class="mlellipsis" style="white-space: normal;text-overflow: ellipsis"
                    mlellipsis="1">${i.notifyUrl}</p></td>
                <td>${i.returnUrl}</td>
                <td>
                    <p class="mlellipsis" style="white-space: normal;text-overflow: ellipsis"
                       mlellipsis="3">${i.privateKey}</p></td>
                <td><p class="mlellipsis" style="white-space: normal;text-overflow: ellipsis"
                                     mlellipsis="3">${i.publicKey}</p></td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td>
                    <a href="aliPayInfo.htm?payAliId=${i.id}" class="tablelink _modifyBtn">修改</a>&nbsp;
                    <%--<a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;--%>
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
        $('._removeBtn').on('click', function () {
            var data = $(this).parents('tr').attr('data');
            var payAliId = data;
            $.zxxbox.ask('确定要删除?', function () {
                $K.http('doRemovePayAli.htm', {
                    'payAliId' : payAliId
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
        //
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop('action', $(this).attr('action')).submit();
        });
        //
        $('.mlellipsis').each(function (i, dom) {
            var div = $(dom);
            var line = div.attr('mlellipsis') || 3;
            div[0].mlellipsis(line);
        });
    });
</script>
