<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">系统设置</a></li>
            <li><a href="#">系统参数</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="#" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加系统参数</li>
            </a>
        </ul>
    </div>

    <ul class="seachform">
        <form method="get" action="" id="searchForm">
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="systemConfigList.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <th>参数名称</th>
            <th>参数值</th>
            <th>备注</th>
            <th>创建时间</th>
            <th>修改时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td>${i.name}</td>
                <td>${i.val}</td>
                <td>${i.remark}</td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td><fmt:formatDate value='${i.modifyTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td>
                    <a href="javascript:;" class="tablelink _modifyBtn">查看修改</a>&nbsp;
                    <a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">${page}</div>
</div>
<%@include file="../footer.jsp" %>
<!--show add or modify -->
<ul class="forminfo zxxbox_contaner short validationEngineContainer formbody" id="addOrModify"
    style="width:450px">
    <input type="hidden" name="id" value=""/>
    <li class="line">
        <label>参数名称:</label>
        <input id="name" name="name" type="text" class="short-input validate[required]"/>
    </li>
    <li class="line">
        <label>参数值:</label>
        <input id="val" name="val" type="text" class="short-input validate[required]"/>
    </li>
    <li class="line">
        <label>备注:</label>
        <textarea name="remark" id="remark" cols="" rows="" style="width: 300px;" class="textinput validate[required]"></textarea>
    </li>
    <li style="margin-left: 120px;">
        <label>&nbsp;</label>
        <input type="button" class="btn saveBtn" value="确认保存"/>
    </li>
</ul>
<script>
    $(function () {
        //search
        $('._searchBtn').on('click',function () {
            $('#searchForm').prop("action",$(this).attr("action")).submit();
        })
        //show add
        $('._addBtn').on('click',function () {
            $('#addOrModify').find('input[type="text"],input[type="hidden"]' ).val("");
            $('#remark').val("");
            $.zxxbox($("#addOrModify"),{
                title:"新增系统参数"
            })
        })

        //add or modify
        $('.saveBtn').ajaxbtn("addOrModifyConfig.htm",function () {
                var config=$('.forminfo').serialize();
                return config;
        },function () {
            return $('#addOrModify').validationEngine("validate",{validateNonVisibleFields: true});
        })

        // show modify
        $('._modifyBtn').on('click',function () {
            var id=$(this).parents('tr').attr("data");
            $('input[name="id"]').val(id);
            $K.http("getConfig.htm",{
                configId:id
            },function (r) {
                var config=r.result;
                $('input[name="name"]').val(config.name);
                $('input[name="val"]').val(config.val);
                $('#remark').val(config.remark);
                $.zxxbox($('#addOrModify'),{
                    title:"修改系统参数"
                })
            })
        })

        //remove
        $('._removeBtn').on('click',function () {
            var id=$(this).parents("tr").attr("data");
            $.zxxbox.ask("确定删除？谨慎操作", function () {
                $K.http("removeConfigId.htm", {
                    configId: id
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