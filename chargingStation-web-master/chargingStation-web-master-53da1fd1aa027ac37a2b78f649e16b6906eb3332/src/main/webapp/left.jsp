<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>
<body>
<div class="lefttop">
    <a href="#"  style="font-size: 15px">智能充电站后台管理系统</a>
</div>
<dl class="leftmenu">
    <c:forEach items="${auth}" var="entry">
        <c:set var="topAuth" value="${entry.key}"></c:set>
        <c:set var="children" value="${entry.value}"></c:set>
        <c:if test="${topAuth.hasAuth}">
            <dd>
                <div class="title"><span><img src="images/leftico01.png"/></span>${topAuth.name}</div>
                <ul class="menuson">
                    <c:forEach items="${children}" var="child">
                        <c:if test="${child.hasAuth}">
                            <li><a target="rightFrame" href="${path}${child.url}">${child.name}</a><i></i></li>
                        </c:if>
                    </c:forEach>
                </ul>
            </dd>
        </c:if>
    </c:forEach>
    <div class="bq">© 2016-2017 好驿达充电 版权所有</div>
</dl>
</body>
</html>
<script language="JavaScript" src="js/jquery/jquery.1.11.2.min.js"></script>
<script type="text/javascript">
    $(function () {
        //导航切换
        $(".menuson li").click(function () {
            $(".menuson li.active").removeClass("active")
            $(this).addClass("active");
        });

        $('.title').click(function () {
            var $ul = $(this).next('ul');
            $('dd').find('ul').slideUp();
            if ($ul.is(':visible')) {
                $(this).next('ul').slideUp();
            } else {
                $(this).next('ul').slideDown();
            }
        });
    })
</script>
