<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<link rel="stylesheet" type="text/css" href="${path}/css/timepicker/timePicker.css">
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="${path}/passport/operatorList.htm?operatorId=${operatorId}">运营商</a></li>
            <li><a href="#">套餐管理</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="#" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加套餐</li>
            </a>
        </ul>
    </div>

    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <input name="operatorId" type="hidden" value="${operatorId}">
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询"
                       action="consumePackageOperatorIdList.htm"/>
            </li>
        </form>
    </ul>
    <table class="tablelist">
        <thead>
        <tr>
            <th>运营商名字</th>
            <th>套餐名称</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>修改时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td>${passport.realName}</td>
                <td>${i.name}</td>
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
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td><fmt:formatDate value='${i.modifyTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td>
                    <a href="javascript:;" class="tablelink _modifyBtn">查看修改</a>&nbsp;
                        <%--<a href="javascript:;" class="tablelink _addConsumeItem">套餐详情添加</a>&nbsp;--%>
                    <a href="${path}/consume/consumeItemList.htm?consumePackageId=${i.id}&operatorId=${operatorId}"
                       class="tablelink ">查看套餐详情</a>&nbsp;
                    <a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">${page}</div>
</div>
<%@include file="../footer.jsp" %>
<!-- add or modify consumePackage -->
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="addOrModifyConsume" style="width:390px">
    <input name="id" type="hidden" value="">
    <input name="operatorId" type="hidden" value="${operatorId}">
    <li>
        <label>套餐名称:</label>
        <input name="name" type="text" class="short-input validate[required]"/>
    </li>
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
        <input name="" type="button" class="btn _doAddOrModifyConsume" value="确认"/>
    </li>
</ul>
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="modifyConsume" style="width:390px">
    <li>
        <label>套餐名称:</label>
        <input name="name2" type="text" class="short-input validate[required]"/>
    </li>
    <li style="text-align: center;">
        <input name="" type="button" class="btn s_modifyConsume" value="确认"/>
    </li>
</ul>
<script type="text/javascript" src="${path}/js/3rd/timepicker/jquery-timepicker.js"></script>
<script>
    $(function () {
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop('action', $(this).attr('action')).submit();
        })
        //show add
        $('._addBtn').on('click', function () {
            $('#addOrModifyConsume').find('input[type="text"]', 'input[type="number"]').val('');
            $('#addOrModifyConsume li').show();
            $('#addOrModifyConsume li input').addClass("validate[required]");
            $.zxxbox($('#addOrModifyConsume'), {
                title: "新增电口套餐"
            })
        })
        //show add
        $('._modifyBtn').on('click', function () {
            var consumeId = $(this).parents('tr').attr("data");
            $('input[name="id"]').val(consumeId);
            $K.http('${path}/consume/getConsumePackageInfo.htm',
                {
                    consumeId: consumeId,
                }, function (r) {
                    var consume = r.result;
                    $('input[name="name2"]').val(consume.name);
                    $.zxxbox($('#modifyConsume'), {
                        title: "修改套餐名称"
                    })
                }
            )
        })
        //toggle
        $('._updateBtn').on('click', function () {
            var consumeId = $(this).parents('tr').attr("data");
            $.zxxbox.ask("确定修改？", function () {
                $K.http("${path}/consume/modifyConsumePackageStatus.htm",
                    {
                        consumeId: consumeId,
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
        $(".s_modifyConsume ").ajaxbtn('doModifyConsumePackage.htm', function () {
            return {
                id: $.trim($('input[name="id"]').val()),
                name: $.trim($('input[name="name2"]').val()),
                operatorId: $.trim($('input[name="operatorId"]').val()),
            }
        }, function () {
            return $('#modifyConsume').validationEngine('validate', {validateNonVisibleFields: true});
        })
        //do add or modify consume
        $('._doAddOrModifyConsume').ajaxbtn('doModifyConsumePackage.htm', function () {
            return {
                id: $.trim($('input[name="id"]').val()),
                name: $.trim($('input[name="name"]').val()),
                operatorId: $.trim($('input[name="operatorId"]').val()),
                startTime: $.trim($('input[name="startTime"]').val()),
                endTime: $.trim($('input[name="endTime"]').val()),
                payment: $.trim($('input[name="payment"]').val() * 100),
                time: $.trim($('input[name="time"]').val() * 60),
            }
        }, function () {
            return $('#addOrModifyConsume').validationEngine('validate', {validateNonVisibleFields: true});
        })
        //remove
        $('._removeBtn').on('click', function () {
            var consumeId = $(this).parents('tr').attr("data");
            var count = 0;
            var title = "确定要删除？"
            $K.http("getStationUserChargeCostPackageCounts.htm",
                {
                    chargeCostPackageId: consumeId,
                }, function (r) {
                    count = r.result;
                    if (parseInt(count) > 0) {
                        title = "有电站在使用该套餐，确定要删除？"
                    }
                    $.zxxbox.ask(title, function () {
                        $K.http("doRemoveConsumeId.htm",
                            {
                                consumeId: consumeId,
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
        //show consumeItem
        $('._addConsumeItem').on('click', function () {
            $('#addOrModifyConsumeItem').find('input[type="text"],input[type="hidden"],input[type="number"]').val('');
            var consumePackageId = $(this).parents('tr').attr('data');
            $('input[name="consumePackageId"]').val(consumePackageId);
            $.zxxbox($('#addOrModifyConsumeItem'), {
                title: "套餐详情添加"
            })
        })
        //add consumeItem
        $('._doAddOrModifyConsumeItem').ajaxbtn('doAddOrModifyConsumeItem.htm', function () {

            return {
                consumePackageId: $.trim($('input[name="consumePackageId"]').val()),
                startTime: $.trim($('input[name="startTime"]').val()),
                endTime: $.trim($('input[name="endTime"]').val()),
                payment: $.trim($('input[name="payment"]').val() / 100),
                time: $.trim($('input[name="time"]').val() / 60)
            }
        }, function () {
            return $('#addOrModifyConsumeItem').validationEngine('validate', {validateNonVisibleFields: true});
        });
        //
        $("#startTime").hunterTimePicker();
        $("#endTime").hunterTimePicker();

    })
</script>