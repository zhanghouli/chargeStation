<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">资金管理</a></li>
            <li><a href="#">充值记录</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
        <%--<div class="tools">--%>
            <%--<ul class="toolbar">--%>
                <%--<input name="" type="button" class="scbtn _rechargeBtn" data-type="${PASSPORT_CAROWNER.value}"--%>
                       <%--value="给车主充值"/>--%>
            <%--</ul>--%>
        <%--</div>--%>
    <div class="tools">
        <ul class="toolbar">
            <a href="#" class="_rechargeBtn" data-type="${PASSPORT_CAROWNER.value}" >
                <li class="click"><span><img src="${path}/images/t01.png"/></span>给车主充值</li>
            </a>
        </ul>
    </div>
    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <%--<li>--%>
            <%--<label>状态</label>--%>
            <%--<div class="vocation">--%>
            <%--<select name="type">--%>
            <%--<c:forEach var="s" items="${ACCOUNT}">--%>
            <%--<option value="${s.value}"--%>
            <%--<c:if test="${s.value == type}">selected</c:if> >${s.name}</option>--%>
            <%--</c:forEach>--%>
            <%--</select>--%>
            <%--</div>--%>
            <%--</li>--%>
            <li><label>关键字</label>
                <input name="keyword" type="text" class="scinput" value="${keyword}"/>
            </li>
            <li><label>起始时间</label>
                <input name="timeStartStr" type="text" class="scinput Wdate" value="${timeStartStr}"
                       onfocus="WdatePicker()"/>
            </li>
            <li><label>结束时间</label>
                <input name="timeEndStr" type="text" class="scinput Wdate" value="${timeEndStr}"
                       onfocus="WdatePicker()"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="accountRecharge.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <th>充值用户名称</th>
            <th>充值金额</th>
            <th>充值前金额</th>
            <th>充值后金额</th>
            <th>备注</th>
            <th>创建时间</th>
            <%--<th>操作</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                    <%--<td>暂无图片</td>--%>
                <td>${i.realName}</td>
                <td><fmt:formatNumber type="number" value='${i.amount/100}' pattern="￥0.00"/></td>
                <td><fmt:formatNumber type="number" value='${i.amountBefore/100}' pattern="￥0.00"/></td>
                <td><fmt:formatNumber type="number" value='${i.amountAfter/100}' pattern="￥0.00"/></td>
                <td>${i.remark}</td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                    <%--<td>--%>
                    <%--<a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;--%>
                    <%--</td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">${page}</div>
</div>
<%@include file="../footer.jsp" %>
<!--carOwner account-->
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="rechargeBox" style="width:400px">
    <li class="line">
        <label>车主手机号：</label><input name="carOwnerPhone" type="text" class="short-input validate[required]"/>
        <span class="tips _tips" style="width: 100%;text-align: center;"></span>
    </li>
    <li class="line _amount" style="margin-top: 20px;">
        <label>充值金额：</label>
        <input name="amount" type="text" class="short-input validate[required,custom[moneyAny]]"/>元
    </li>
    <li class="line" style="text-align: center;">
        <input name="" type="button" class="btn _doRechargeBtn" value="确定"/>
    </li>
</ul>

<script>
    $(function () {
        //_searchBtn
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })
        var toPassportId = '';
        var toPassportType = -1;
        $('._rechargeBtn').on('click', function () {
            $('#rechargeBox').find('input[type="text"]').val('');
            toPassportId = ''
            toPassportType = $(this).attr("data-type");
            $('._tips').html("");
            $.zxxbox($('#rechargeBox'), {
                title: '给车主充值'
            });
        });
        //金额 充值
        $('._doRechargeBtn').ajaxbtn("${path}/account/addAccountCarOwner.htm", function () {
            return {
                phone: $.trim($('input[name="carOwnerPhone"]').val()),
                amount:$.trim($('input[name="amount"]').val() * 100)
            }
        }, function () {
            return $('#rechargeBox').validationEngine('validate', {validateNonVisibleFields: false}) && !$K.tools.isBlank(toPassportId);
        })
        /**
         * 手机号 判断
         */
        $('input[name="carOwnerPhone"]').on('blur', function () {
            if (!$K.tools.isBlank($(this).val())) {
                $K.http('${path}/account/getByCarOwnerPhone.htm', {
                    phone: $(this).val(),
                    type: toPassportType
                }, function (r) {
                    if (r && r.result.id) {
                        toPassportId = r.result.id;
                        $('._tips').html('用户：' + r.result.userName + '[' + r.result.realName + ']');
                    } else {
                        $('._tips').html('* 车主不存在');
                    }
                }, function () {
                    $('._tips').html('* 车主不存在');
                });
            }
        });


    })
</script>