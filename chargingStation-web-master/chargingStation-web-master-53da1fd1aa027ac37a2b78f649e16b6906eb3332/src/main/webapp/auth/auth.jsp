<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<link rel="stylesheet" type="text/css" href="${path}/js/3rd/jstree/themes/default/style.min.css"/>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <c:if test="${status == PASSPORT_GOVERNMENT.value}">
                <li><a href="${path}/passport/governmentList.htm">政府</a></li>
            </c:if>
            <c:if test="${status == PASSPORT_ADMIN.value}">
                <li><a href="${path}/passport/passportList.htm">管理员</a></li>
            </c:if>
            <li><a href="#">权限管理</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">

    <div class="formbody" style="float: left;width: 20%;">
        <div class="formtitle">
            <span>权限管理</span>
        </div>
        <div id="jstree"></div>
    </div>
    <div class="formbody _form" style="float: left;width: 70%;">
        <div class="_delBox">
            <div class="formtitle">
                <span>操作</span>
            </div>
            <ul class="forminfo">
                <li>
                    <label>&nbsp;</label>
                    <input name="" type="button" class="btn _doSaveBtn" value="保存"/>
                </li>
            </ul>
        </div>
    </div>
</div>
<%@include file="../footer.jsp" %>
<script type="text/javascript" src="${path}/js/3rd/jstree/jstree.min.js"></script>
<script type="text/javascript">
    $(function () {
        //
        $("#jstree").jstree({
            'core': {
                'data': {
                    "url": "./getAuthPassport.htm?passportId=${passportId}",
                    "dataType": "json"
                },
                'dblclick_toggle':false
            },
            "checkbox": {
                "keep_selected_style": false
            },
            "plugins": ["checkbox"]
        });
        //ok
        $('._doSaveBtn').ajaxbtn('doSavePassportAuth.htm', function () {
            //console.log($("#jstree").jstree("get_checked").join(','));
            return {
                //映射过来的主键iD，跟auth关联
                passportId: '${passportId}',
                auth: $("#jstree").jstree("get_checked").join(',')
            }
        });
    });
</script>
