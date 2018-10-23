<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">系统设置</a></li>
            <li><a href="#">微信参数</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="${path}/pay/wxPayInfo.htm" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加微信配置</li>
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="wxPayList.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <th style="width: 40px">所属端</th>
            <th>pay_key</th>
            <th>pay_appid</th>
            <th>pay_secret</th>
            <th>mch_id</th>
            <th>cert_local_path</th>
            <th>cert_password</th>
            <th>notify_uri</th>
            <%--<th style="width: 5%">域名验证文件</th>--%>
            <%--<th style="width: 5%">支付签名文件</th>--%>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td>${i.platform}</td>
                <td>${i.payKey}</td>
                <td>${i.payAppid}</td>
                <td>${i.paySecret}</td>
                <td>${i.mchId}</td>
                <td>${i.certLocalPath}</td>
                <td>${i.certPassword}</td>
                <td>${i.notifyUrl}</td>
                <%--<td>--%>
                    <%--&lt;%&ndash;<p  class="mlellipsis" style="white-space: normal;text-overflow: ellipsis" mlellipsis="1" >${i.veryfy}</p>&ndash;%&gt;--%>
                    <%--<p class="mlellipsis" style="white-space: normal;text-overflow: ellipsis"--%>
                       <%--mlellipsis="1">${i.veryfy}</p>--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--&lt;%&ndash;<p  class="mlellipsis" style="white-space: normal;text-overflow: ellipsis" mlellipsis="1" >${i.cert}</p>&ndash;%&gt;--%>
                    <%--<p class="mlellipsis" style="white-space: normal;text-overflow: ellipsis"--%>
                       <%--mlellipsis="1">${i.cert}</p>--%>
                <%--</td>--%>
                <td>
                    <a href="${path}/pay/wxPayInfo.htm?payWxId=${i.id}" class="tablelink _modifyBtn">修改</a>&nbsp;
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
    <form action="${path}/pay/addOrModifyWxPay.htm" enctype="multipart/form-data" id="wxPayBtn">
        <input type="hidden" name="id" value=""/>
        <li class="line">
            <label>所属端:</label>
            <div class="vocation">
                <select name="platform" class="useselect validate[required]">
                    <c:forEach var="s" items="${PLAT_FORM}">
                        <option value="${s.value}" <c:if test="${s.value == payWx.platform}">selected</c:if>>
                                ${s.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </li>
        <li class="line">
            <label>pay_key:</label>
            <input id="payKey" name="payKey" type="text" class="short-input validate[required]"/>
        </li>
        <li class="line">
            <label>pay_appid:</label>
            <input id="payAppid" name="payAppid" type="text" class="short-input validate[required]"/>
        </li>
        <li class="line">
            <label>pay_secret:</label>
            <input id="paySecret" name="paySecret" type="text" class="short-input validate[required]"/>
        </li>
        <li class="line">
            <label>mch_id:</label>
            <input id="mchId" name="mchId" type="text" class="short-input validate[required]"/>
        </li>
        <li class="line">
            <label>cert_local_path:</label>
            <input id="certLocalPath" name="certLocalPath" type="text" class="short-input validate[required]"/>
        </li>
        <li class="line">
            <label>cert_password:</label>
            <input id="certPassword" name="certPassword" type="text" class="short-input validate[required]"/>
        </li>
        <li class="line">
            <label>notify_url:</label>
            <input id="notifyUrl" name="notifyUrl" type="text" class="short-input validate[required]"/>
        </li>
        <li class="line">
            <label>sub_mch_id:</label>
            <input id="subMchId" name="subMchId" type="text" class="short-input validate[required]"/>
        </li>
        <li class="line">
            <label>ip:</label>
            <input id="ip" name="ip" type="text" class="short-input validate[required]"/>
        </li>
        <li class="line">
            <label>域名验证文件:</label>
            <input id="veryfy" name="veryfy" type="file" class="short-input validate[required]"/>
        </li>
        <li class="line">
            <label>支付签名文件:</label>
            <input id="cert" name="cert" type="file" class="short-input validate[required]"/>
        </li>
        <li style="margin-left: 120px;">
            <label>&nbsp;</label>
            <input type="button" class="btn saveBtn" value="确认保存"/>
        </li>
    </form>
</ul>

<script>
    $(function () {
        //search
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })

        $('._removeBtn').on('click',function () {
            var id=$(this).parents('tr').attr("data");
            $.zxxbox.ask("确定删除？",function () {
                $K.http('removeWxConfig.htm', {
                    payWxId: id
                }, function (r) {
                    $.zxxbox.remind("操作成功。", null, {
                        delay: 3000,
                        onclose: function () {
                            location.reload();
                        }
                    });
                })
            })
        })

    })
</script>