<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<link rel="stylesheet" type="text/css" href="${path}/css/timepicker/timePicker.css">
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="${path}/passport/operatorList.htm?operatorId=${operatorId}">运营商</a></li>
            <li><a href="${path}/consume/consumePackageOperatorIdList.htm?operatorId=${operatorId}">套餐管理</a></li>
            <li><a href="#">套餐详情</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="#" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加套餐详情</li>
            </a>
        </ul>
    </div>

    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <input name="consumePackageId" value="${consumePackageId}" type="hidden"/>
            <li>
                <label>状态</label>
                <div class="vocation">
                    <select name="status">
                        <c:forEach var="s" items="${PASSPORT_STATUS}">
                            <option value="${s.value}"
                                    <c:if test="${s.value == status}">selected</c:if> >${s.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <%--<li><label>关键字</label>--%>
                <%--<input name="keyword" type="text" class="scinput" value="${keyword}"/>--%>
            <%--</li>--%>
            <%--<li><label>起始时间</label>--%>
            <%--<input name="timeStartStr" type="text" class="scinput Wdate"--%>
            <%--onfocus="WdatePicker()"/>--%>
            <%--</li>--%>
            <%--<li><label>结束时间</label>--%>
            <%--<input name="timeEndStr" type="text" class="scinput Wdate"--%>
            <%--onfocus="WdatePicker()"/>--%>
            <%--</li>--%>
            <li>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="consumeItemList.htm"/>
            </li>
        </form>
    </ul>
    <table class="tablelist">
        <thead>
        <tr>
            <%--<th>头像</th>--%>
            <%--<th>运营商Id</th>--%>
            <th>套餐名称</th>
            <th>状态</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>支付金额(单位:元)</th>
            <th>充电时间(单位:小时)</th>
            <th>创建时间</th>
            <th>修改时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                    <%--<td>暂无图片</td>--%>
                <td>${i.consumeName}</td>
                <td>
                    <a href="javascript:;" class="tablelink _updateBtn">
                        <c:if test="${'true'== i.isEnabled}">
                            <font class="color-green">正常</font>
                        </c:if>
                        <c:if test="${'false'== i.isEnabled}">
                            <font class="color-red">禁用</font>
                        </c:if>
                        <img src="${path}/images/edit.png" class="icon-edit">
                    </a>
                </td>
                <td>${i.startTime}</td>
                <td>${i.endTime}</td>
                <td><fmt:formatNumber  type="number" value='${i.payment/100}' pattern="￥0.00"/></td>
                <td><fmt:formatNumber  type="number" value='${i.time/60}' pattern="0.00"/>小时</td>
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
<!--add consumeItem -->
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="addOrModifyConsumeItem" style="width:390px">
    <input id="id" name="id" value="" type="hidden"/>
    <input id="consumePackageId" name="consumePackageId" value="${consumePackageId}" type="hidden"/>
    <li>
        <label>开始时间:</label>
        <input name="startTime" id="startTime" type="text" class="short-input validate[required,custom[timeHhMm]]"/>
    </li>
    <li>
        <label>结束时间:</label>
        <input name="endTime" id="endTime" type="text" class="short-input validate[required,custom[timeHhMm]]"/>
    </li>
    <li>
        <label>支付金额:</label>
        <input name="payment" type="text" class="short-input validate[required,custom[moneyAny]]"/>(单位:元)
    </li>
    <li>
        <label>充电时间:</label>
        <input name="time" type="text" class="short-input validate[required,custom[keepOne]]"/>(单位:小时)
    </li>
    <li style="text-align: center;">
        <input name="" type="button" class="btn _doAddOrModifyConsumeItem" value="确认"/>
    </li>
</ul>
<script type="text/javascript" src="${path}/js/3rd/timepicker/jquery-timepicker.js"></script>
<script>
    $(function () {
        //search
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop('action', $(this).attr('action')).submit();
        })
        //show consumeItem
        $('._addBtn').on('click', function () {
            $('#addOrModifyConsumeItem').find('input[type="text"],input[type="number"]').val('');
            $('input[name="id"]').val("");
            $.zxxbox($('#addOrModifyConsumeItem'), {
                title: "套餐详情添加"
            })
        })
        //show modify
        $('._modifyBtn').on("click", function () {
            var consumeItemId = $(this).parents('tr').attr('data');
            $('input[name="id"]').val(consumeItemId);
            $K.http("${path}/consume/getConsumeItemInfo.htm",
                {
                    consumeItemId: consumeItemId
                }, function (r) {
                    var consumeItem = r.result;
                    $('input[name="startTime"]').val(consumeItem.startTime);
                    $('input[name="endTime"]').val(consumeItem.endTime);
                    $('input[name="payment"]').val(consumeItem.payment/100);
                    $('input[name="time"]').val(consumeItem.time/60);
                    $.zxxbox($('#addOrModifyConsumeItem'), {
                        title: "修改套餐详情"
                    })
                })
        })
        //show add or modify
        $('._doAddOrModifyConsumeItem').ajaxbtn("doAddOrModifyConsumeItem.htm", function () {
            return {
                id: $.trim($('input[name="id"]').val()),
                consumePackageId:$.trim($('input[name="consumePackageId"]').val()),
                startTime: $.trim($('input[name="startTime"]').val()),
                endTime: $.trim($('input[name="endTime"]').val()),
                payment: $.trim($('input[name="payment"]').val()*100),
                time: $.trim(($('input[name="time"]').val()*60))
            }
        }, function () {
            return $('#addOrModifyConsumeItem').validationEngine('validate', {validateNonVisibleFields: true});
        })
        //remove
        $('._removeBtn').on('click', function () {
            var consumeItemId = $(this).parents('tr').attr("data");
            $.zxxbox.ask("确定要删除？", function () {
                $K.http("doRemoveConsumeItemId.htm",
                    {
                        consumeItemId: consumeItemId,
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
        //toggle
        $('._updateBtn').on('click', function () {
            var consumeItemId = $(this).parents('tr').attr("data");
            $.zxxbox.ask("确定修改？", function () {
                $K.http("${path}/consume/modifyConsumeItemStatus.htm",
                    {
                        consumeItemId: consumeItemId,
                    }, function (r) {
                        $.zxxbox.remind("修改成功。", null, {
                            delay: 3000,
                            onclose: function () {
                                location.reload();
                            }
                        })
                    })
            })
        })

        $("#startTime").hunterTimePicker();
        $("#endTime").hunterTimePicker();

    })
</script>