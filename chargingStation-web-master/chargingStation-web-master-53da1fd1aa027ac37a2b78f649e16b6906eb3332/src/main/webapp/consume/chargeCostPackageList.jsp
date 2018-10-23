<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">配置管理</a></li>
            <li><a href="#">充值配置</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="javascript:;" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加按量充值配置</li>
            </a>
        </ul>
    </div>
    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <li>
                <label>状态</label>
                <div class="vocation">
                    <select name="isEnabled">
                        <c:forEach var="s" items="${COMMON_STATUS_YES_OR_NO}">
                            <option value="${s.value}"
                                    <c:if test="${s.value == isEnabled}">selected</c:if> >${s.name}</option>
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="chargeCostPackageList.htm"/>
            </li>
        </form>
    </ul>
    <table class="tablelist">
        <thead>
        <tr>
            <%--<th>头像</th>--%>
            <%--<th>运营商Id</th>--%>
            <th>最低功率</th>
            <th>最高功率</th>
            <th>收费</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>修改时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                    <%--<td>暂无图片</td>--%>
                <td>${i.minPow}</td>
                <td>${i.maxPow}</td>
                <td><fmt:formatNumber type="number" value='${i.cost/100}' pattern="￥0.00"/></td>
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
                    <a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">${page}</div>
</div>
<%@include file="../footer.jsp" %>
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="addOrModify" style="width:380px">
    <input id="id" name="id" value="" type="hidden"/>
    <li>
        <label>最低功率:</label>
        <input name="minPow" id="minPow" type="text" class="short-input validate[required,custom[positiveInteger]]"/>
    </li>
    <li>
        <label>最高功率:</label>
        <input name="maxPow" id="maxPow" type="text" class="short-input validate[required,custom[positiveInteger]]"/>
    </li>
    <li>
        <label>收费金额:</label>
        <input name="cost" id="cost" type="text" class="short-input validate[required,custom[moneyAny]]"/>(单位:元)
    </li>
    <li style="text-align: center;">
        <input name="" type="button" class="btn _doAddOrModifyConsumeItem" value="确认"/>
    </li>
</ul>
<script>
    $(function () {
        // search
        $('._searchBtn').on("click", function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })
        // show add
        $('._addBtn').on('click', function () {
            $('#addOrModify').find('input[type="text"]').val("");
            $('#addOrModify').find('input[name="id"]').val("");
            $.zxxbox($('#addOrModify'), {
                title: "新增充值套餐"
            })
        })
        // add
        $('._doAddOrModifyConsumeItem').ajaxbtn("addOrModifyChargeCostPackage.htm", function () {
            return {
                minPow: $.trim($('input[name="minPow"]').val()),
                maxPow: $.trim($('input[name="maxPow"]').val()),
                cost: $.trim($('input[name="cost"]').val() * 100),
                id: $.trim($('input[name="id"]').val())
            }
        }, function () {
            return $('#addOrModify').validationEngine("validate", {validateNonVisibleFields: true})
        })
        //remove
        $('._removeBtn').on("click", function () {
            var id = $(this).parents("tr").attr("data");
            $.zxxbox.ask("确定删除？", function () {
                $K.http("removeChargeCostPackage.htm", {
                    chargeCostPackageId: id
                }, function () {
                    $.zxxbox.remind("操作成功", null, {
                        delay: 3000,
                        onclose: function () {
                            location.reload();
                        }
                    })
                })
            })
        })
        //modify isEnabled
        $('._updateBtn').on("click", function () {
            var id = $(this).parents('tr').attr("data");
            $.zxxbox.ask("确定修改？", function () {
                $K.http("modifyChargeCostPackageIsEnabled.htm", {
                    chargeCostPackageId: id
                }, function () {
                    $.zxxbox.remind("操作成功。", null, {
                        delay: 3000,
                        onclose: function () {
                            location.reload();
                        }
                    })
                })
            })
        })
        //modify show add
        $('._modifyBtn').on('click', function () {
            var id = $(this).parents('tr').attr('data');
            $('input[name="id"]').val(id);
            $K.http("getChargeCostPackage.htm", {
                chargeCostPackageId: id
            }, function (r) {
                var rechargePackage = r.result;
                console.log(rechargePackage);
                $('input[name="maxPow"]').val(rechargePackage.maxPow);
                $('input[name="minPow"]').val(rechargePackage.minPow);
                $('input[name="cost"]').val(rechargePackage.cost / 100);
                $.zxxbox($('#addOrModify'), {
                    title: "修改充值套餐"
                })
            })
        })
    })
</script>