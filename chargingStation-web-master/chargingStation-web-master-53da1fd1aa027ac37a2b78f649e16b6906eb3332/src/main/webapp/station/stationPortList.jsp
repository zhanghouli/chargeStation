<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
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
            <li><a href="#">电站充电口</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="formbody">
    <%--<div class="centerdiv">--%>
    <%--<span>电站名称：${station.name}</span>--%>
    <%--<span>电站编号：${station.number}</span>--%>
    <%--<span>电站详细地址：${station.address}</span>--%>
    <%--</div>--%>
    <ul class="forminfo short validationEngineContainer">
        <li class="line">
            <label>电站名称:</label>
            <input readonly="readonly" style="width: 300px;" type="text" value="${station.name}"/>
        </li>
        <li class="line">
            <label>电站编号:</label>
            <input readonly="readonly" style="width: 300px;" type="text" value="${station.number}"/>
        </li>
        <li class="line">
            <label>电站详细地址:</label>
            <input readonly="readonly" style="width: 300px;" type="text" value="${station.address}"/>
        </li>
    </ul>
    <c:forEach items="${stationPorts}" var="i">
        <div class="block-div">
            <div><span>充电口序号:${i.seq}</span></div>
            <div><span>充电口编号:${i.number}</span></div>
            <div><span>充电二维码:${i.qrCode}</span></div>
            <div><span>最大功率:${i.maxPower}</span></div>
            <div><span>最小功率:${i.minPower}</span></div>
            <div><span>涓流时间:${i.trickleTime}</span></div>
            <div>
                <span>充满自停:
                 <c:if test="${i.isAutoStop == 'true'}">已启用</c:if>
                 <c:if test="${i.isAutoStop == 'false'}">未启用</c:if>
                 </span>
            </div>
            <div>
                <span>最大功率状态:
                <c:if test="${i.isLargePower == 'true'}">已启用</c:if>
                <c:if test="${i.isLargePower == 'false'}">未启用</c:if>
                </span>
            </div>
            <div>
                <span>工作状态:
                <c:if test="${i.status == STATION_PORT_STATUS_FREE.value}"><font class="color-darkGrey">空闲</font></c:if>
                <c:if test="${i.status == STATION_PORT_STATUS_WORKING.value}"><font class="color-green">使用</font></c:if>
                <c:if test="${i.status == STATION_PORT_STATUS_DISABLE.value}"><font class="color-red">禁用</font></c:if>
                <c:if test="${i.status == STATION_PORT_STATUS_FAULT.value}"><font class="color-orange">故障</font></c:if>
                      <c:if test="${i.status == STATION_PORT_STATUS_SUSPEND.value}"><font class="color-orange">暂停</font></c:if>
                </span>
            </div>
            <div>
            <a href="javascript:;" class="tablelink _modifyBtn" data-id="${i.id}">查看修改</a> &nbsp;
            <a href="javascript:;" class="tablelink _removeBtn" data-id="${i.id}">删除</a> &nbsp;
            <a href="${path}/station/stationPort.htm?stationPortId=${i.id}&estateId=${estateId}&operatorId=${operatorId}&stationId=${station.id}" class="tablelink">电口详情</a>&nbsp;
            <a href="${path}/station/stationOrder.htm?stationPortId=${i.id}&estateId=${estateId}&operatorId=${operatorId}&stationId=${station.id}" class="tablelink">充电记录</a>
            </div>
        </div>
    </c:forEach>
</div>
<%@include file="../footer.jsp" %>

<!--add station port -->
<ul class="forminfo zxxbox_contaner short validationEngineContainer formbody" id="addOrModifyStationPort"
    style="width:350px">
    <input type="hidden" name="id" value=""/>
    <!--id！=null就进来了-->
    <%--<li class="line">--%>
        <%--<label>充电口序号:</label>--%>
        <%--<input id="seq" name="seq" type="number" class="short-input validate[required]"/>--%>
    <%--</li>--%>
    <li class="line">
        <label>充电口编号:</label>
        <input id="number" name="number" type="text" class="short-input validate[required]" maxlength="45"/>
    </li>
    <li class="line">
        <label>充电口二维码:</label>
        <input id="qrCode" name="qrCode"  type="text" class="short-input validate[required]"  maxlength="45"/>
    </li>
    <li class="line">
        <label>最大功率:</label>
        <input id="maxPower" name="maxPower" type="number" class="short-input validate[required,custom[positiveInteger]]"  maxlength="45"/>

    </li>
    <li class="line">
        <label>最小功率:</label>
        <input id="minPower" name="minPower" type="number" class="short-input validate[required,custom[positiveInteger]]"  maxlength="45"/>
    </li>
    <li class="line">
        <label>涓流时间:</label>
        <input id="trickleTime" name="trickleTime" type="number" class="short-input validate[required,custom[positiveInteger]]"  maxlength="45"/>
    </li>
    <li class="line">
        <label>是否开启充满自停:</label>
        开启:<input id="isAutoStop1" name="isAutoStop" type="radio" value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        关闭:<input id="isAutoStop2" name="isAutoStop" type="radio" value="0"/>
        <%--<div class="vocation">--%>
        <%--<select name="isAutoStop">--%>
        <%--<c:forEach var="s" items="${COMMON_OPEN_OR_CLOSE}">--%>
        <%--<option value="${s.value}"--%>
        <%--<c:if test="${s.value == status}">selected</c:if> >${s.name}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
        <%--</div>--%>
    </li>
    <li class="line">
        <label>是否开启大功率:</label>
        开启:<input name="isLargePower" type="radio" id="isLargePower1" value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        关闭:<input name="isLargePower" type="radio" id="isLargePower2" value="0"/>
        <%--<select name="isLargePower">--%>
        <%--<c:forEach var="s" items="${COMMON_OPEN_OR_CLOSE}">--%>
        <%--<option value="${s.value}"--%>
        <%--<c:if test="${s.value == status}">selected</c:if> >${s.name}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
    </li>

    <li style="margin-left: 120px;">
        <label>&nbsp;</label>
        <input type="button" class="btn saveBtnStationPort" value="确认保存"/>
    </li>
</ul>


<script>
    $(function () {
        //show modify
        $('._modifyBtn').on('click', function () {
            var id = $(this).attr('data-id');

            $('#addOrModifyStationPort').find('input[type="text"],input[type="number"],input[type="hidden"]').val('');
            $('input[name="id"]').val(id);
            $K.http("getStationPort.htm", {
                stationPortId: id
            }, function (r) {
                var stationPort = r.result;
//                $('input[name="seq"]').val(stationPort.seq);
                $('input[name="number"]').val(stationPort.number);
                $('input[name="qrCode"]').val(stationPort.qrCode);
                $('input[name="maxPower"]').val(stationPort.maxPower);
                $('input[name="minPower"]').val(stationPort.minPower);
                $('input[name="trickleTime"]').val(stationPort.trickleTime);
                if (stationPort.isAutoStop) {
                    $('#isAutoStop1').prop("checked", "checked");
                } else {
                    $('#isAutoStop2').prop("checked", "checked");
                }
                if (stationPort.isLargePower) {
                    $('#isLargePower1').prop("checked", "checked");
                } else {
                    $('#isLargePower2').prop("checked", "checked");
                }

                $.zxxbox($('#addOrModifyStationPort'), {
                    title: "修改充电口"
                })

            })
        })


        // modify stationPort
        $('.saveBtnStationPort').ajaxbtn("addStationPort.htm", function () {
            var stationPort = $('.forminfo').serialize();
            return stationPort;
        }, function () {
            return $('.forminfo').validationEngine('validate', {validateNonVisibleFields: true});
        })

        $('._removeBtn').on('click', function () {
            var id = $(this).attr('data-id');
            var stationPortId = id;
            $.zxxbox.ask('确定要删除?', function () {
                $K.http('doRemoveStationPortId.htm', {
                    stationPortId: stationPortId
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
