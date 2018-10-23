<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<link rel="stylesheet" type="text/css" href="${path}/css/simditor.css"/>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <c:if test="${estateId != null && operatorId != null  }">
                <li><a href="${path}/passport/operatorList.htm?operatorId=${operatorId}">运营商</a></li>
                <li><a href="${path}/passport/estateList.htm?estateId=${estateId}&operatorId=${operatorId}">物业</a></li>
            </c:if>
            <c:if test="${estateId == null && operatorId != null  }">
                <li><a href="${path}/passport/operatorList.htm?operatorId=${operatorId}">运营商</a></li>
            </c:if>
            <c:if test="${estateId != null && operatorId == null }">
                <li><a href="${path}/passport/estateList.htm?estateId=${estateId}">物业</a></li>
            </c:if>
            <c:if test="${searchStationVo.stationType == 'fault'}">
                <li><a href="${path}/station/errorStationList.htm?estateId=${estateId}&operatorId=${operatorId}">电站列表</a></li>
            </c:if>
            <c:if test="${searchStationVo.stationType == 'normal' }">
                <li><a href="${path}/station/stationList.htm?estateId=${estateId}&operatorId=${operatorId}">电站列表</a></li>
            </c:if>

            <li><a href="#">维护列表</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="#" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加电站维护信息</li>
            </a>
        </ul>
    </div>
    <input type="hidden" name="stationId" value="${stationId}"/>
    <table class="tablelist">
        <thead>
        <tr>
            <th>维修内容</th>
            <th>填写日期</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td><div class="mlellipsis" style="white-space: normal;text-overflow: ellipsis"
                                     mlellipsis="2">${i.content}</div></td>
                <td><fmt:formatDate value="${i.creationTime}" pattern="yyyy-MM-dd HH:mm"/></td>
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
<ul class="forminfo zxxbox_contaner short validationEngineContainer formbody" id="addOrModify"
    style="width:450px">
    <input type="hidden" name="id" value=""/>
    <li class="line">
        <label>维护说明:</label>
        <textarea name="content" id="content" cols="" rows="" style="width: 300px;overflow-y:auto"
                  class="textinput "></textarea>
    </li>
    <li style="margin-left: 120px;">
        <label>&nbsp;</label>
        <input type="button" class="btn saveBtn" value="确认保存"/>
    </li>
</ul>
<script type="text/javascript" src="${path}/js/simditor/module.min.js"></script>
<script type="text/javascript" src="${path}/js/simditor/hotkeys.min.js"></script>
<script type="text/javascript" src="${path}/js/simditor/uploader.min.js"></script>
<script type="text/javascript" src="${path}/js/simditor/simditor.min.js"></script>
<script>
    $(function () {
        //show add
        $('._addBtn').on('click', function () {
            $('#addOrModify').find('input[type="text"],input[type="hidden"]').val("");
            $('#remark').val("");
            $.zxxbox($("#addOrModify"), {
                title: "新增电站维护信息"
            })
        })

        // 提交 表单
        $('.saveBtn').ajaxbtn("addOrModifyStationMaintainLog.htm", function () {
            return {
                id: $.trim($('input[name="id"]').val()),
                stationId: $.trim($('input[name="stationId"]').val()),
                content: $.trim($('textarea[name="content"]').val()),
            }
        }, function () {
            return $('#addOrModify').validationEngine("validate", {validateNonVisibleFields: true});
        })
        // show modify
        $('._modifyBtn').on('click', function () {
            var id = $(this).parents('tr').attr("data");
            $('input[name="id"]').val(id);
            $K.http("changeStationMaintainLog.htm", {
                stationMaintainLogId: id
            }, function (r) {
                var stationLog = r.result;
                $('textarea[name="content"]').val(stationLog.content);
                $.zxxbox($('#addOrModify'), {
                    title: "修改电站维护信息"
                })
            })
        })
        //remove
        $('._removeBtn').on('click', function () {
            var id = $(this).parents("tr").attr("data");
            $.zxxbox.ask("确定删除？", function () {
                $K.http("removeStationMaintainLog.htm", {
                    stationMaintainLogId: id
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