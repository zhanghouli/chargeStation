<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="${path}/pay/aliPayList.htm">支付配置管理</a></li>
            <li><a href="#">支付宝配置</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>

<div class="formbody">
    <div class="formtitle">编辑配置</div>

    <ul class="forminfo short validationEngineContainer">
        <input name="id" type="hidden" value="${payAli.id}"/>
        <li class="line">
            <label>appId</label>
            <input name="appId" type="text" class="short-input validate[required]" value="${payAli.appId}"/>
        </li>
        <li class="line">
            <label>notify_url</label>
            <input name="notifyUrl" type="text" class="short-input validate[required]" value="${payAli.notifyUrl}"/>
        </li>
        <li class="line">
            <label>return_url</label>
            <input name="returnUrl" type="text" class="short-input validate[required]" value="${payAli.returnUrl}"/>
        </li>
        <li class="line">
            <label>private_key</label>
            <input name="privateKey" type="text" class="short-input validate[required]" value="${payAli.privateKey}"/>
        </li>
        <li class="line">
            <label>public_key</label>
            <input name="publicKey" type="text" class="short-input validate[required]" value="${payAli.publicKey}"/>
        </li>
        <li>
            <label>&nbsp;</label>
            <input type="button" class="btn saveBtn" value="确认保存"/>
        </li>
    </ul>
</div>
<%@include file="../footer.jsp" %>

<script>
    $(function () {
        $('.saveBtn').ajaxbtn('doAddOrModifyPayAli.htm', function () {
            return {
                id: $.trim($('input[name="id"]').val()),
                platform: $.trim($('select[name="platform"]').val()),
                appId: $.trim($('input[name="appId"]').val()),
                partner: $.trim($('input[name="partner"]').val()),
                sellerId: $.trim($('input[name="sellerId"]').val()),
                notifyUrl: $.trim($('input[name="notifyUrl"]').val()),
                returnUrl: $.trim($('input[name="returnUrl"]').val()),
                privateKey: $.trim($('input[name="privateKey"]').val()),
                publicKey: $.trim($('input[name="publicKey"]').val())
            }
        }, function () {
            return $('.forminfo').validationEngine('validate');
        });
    });
</script>
