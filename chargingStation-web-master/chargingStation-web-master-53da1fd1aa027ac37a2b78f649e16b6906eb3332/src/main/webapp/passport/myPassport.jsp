<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">个人中心</a></li>
            <li><a href="#">我的账号</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>

<div class="formbody">
    <div class="formtitle"><span>编辑我的账号</span></div>

    <ul class="forminfo short validationEngineContainer">
        <input name="id" type="hidden" value="${passport.id}"/>
        <input name="picId" type="hidden" value="${passport.picId}">
        <input name="height" type="hidden" value=""/>
        <input name="width" type="hidden" value=""/>
        <input name="size" type="hidden" value=""/>
        <input name="orgName" type="hidden" value=""/>
        <input name="relativeFolder" type="hidden" value=""/>
        <input name="remoteRelativeUrl" type="hidden" value=""/>
        <input name="name" type="hidden" value=""/>
        <li class="line">
            <label>头像图片</label>
            <div class="upload-img" id="upload-img-pic" style="width: 90px;height: 90px">
                <input name="pic" type="hidden" class="short-input" value="${passport.pic}"/>
                <c:choose>
                    <c:when test="${ISDEV}">
                        <img src="${path}${files}${passport.pic==null?'../images/default-image.png':passport.pic}"
                             class="img-radius use-default" style="width: 90px;height: 90px;border-radius:45px"/>
                    </c:when>
                    <c:when test="${!ISDEV}">
                        <img src="${path}${passport.pic==null?'../images/default-image.png':passport.pic}"
                             class="img-radius use-default" style="width: 90px;height: 90px;border-radius:45px"/>
                    </c:when>
                </c:choose>
            </div>
            <img src="${path}/images/edit.png" class="icon-edit" style="margin: 20px;">
        </li>
        <li class="line">
            <label>登录账号</label>
            <input name="userName" type="text" class="short-input validate[required]" value="${passport.userName}"/>
        </li>
        <li class="line">
            <label>姓名</label>
            <input name="realName" type="text" class="short-input" value="${passport.realName}"/></li>
        <li class="line">
            <label>状态</label>
            <input type="text" class="short-input validate[required]"
                   value="<c:if test="${passport.status == PASSPORT_STATUS_DISABLE.value}"> ${PASSPORT_STATUS_DISABLE.name}</c:if><c:if test="${passport.status == PASSPORT_STATUS_NORMAL.value}"> ${PASSPORT_STATUS_NORMAL.name}</c:if>"
                   disabled="disabled"/>
        </li>
        <%--<li class="line">--%>
        <%--<label>地址</label>--%>
        <%--<input name="address" type="text" class="short-input validate[required]" value="${passport.address}" />--%>
        <%--</li>--%>
        <li class="line">
            <label>修改密码</label>
            <input name="password" type="password" class="short-input" value="" placeholder="不填写则为原密码"/>
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
        //
        $('.saveBtn').ajaxbtn('doAddOrModifyPassport.htm', function () {
            return {
                id: $.trim($('input[name="id"]').val()),
                userName: $.trim($('input[name="userName"]').val()),
                realName: $.trim($('input[name="realName"]').val()),
                password: $.trim($('input[name="password"]').val()),
                type: '${PASSPORT_ADMIN.value}',
                phone:'${passport.phone}',
                height: $.trim($('input[name="height"]').val()),
                width: $.trim($('input[name="width"]').val()),
                size: $.trim($('input[name="size"]').val()),
                orgName: $.trim($('input[name="orgName"]').val()),
                relativeFolder: $.trim($('input[name="relativeFolder"]').val()),
                remoteRelativeUrl: $.trim($('input[name="remoteRelativeUrl"]').val()),
                name: $.trim($('input[name="name"]').val()),
                picId: $.trim($('input[name="picId"]').val()),
                pic: $.trim($('input[name="pic"]').val()),
            }
        }, function () {
            return $('.forminfo').validationEngine('validate');
        });
        //头像
        var upload = WebUploader.create({
            swf: '../js/webuploader/Uploader.swf',
            server: '${path}/common/file/uploadImage.htm',
            //paste: document.body,
            pick: '#upload-img-pic',
            auto: true,
            formData: {
                bucket: 'avatar'
            }
        });
        upload.on('uploadSuccess', function (file, data) {
            var passportId = $('input[name="id"]').val();
            if(data.code == 0){
                $.zxxbox.remind("请上传正确的图片类型", null, {delay: 2000});
                return false;
            }
            var image = data.result.imageVo;
            <c:choose>
            <c:when test="${ISDEV}">
            var avatar = ${path}"/files"+image.remoteRelativeUrl;
            </c:when>
            <c:when test="${!ISDEV}">
            var avatar = '${path}'+image.remoteRelativeUrl;
            </c:when>
            </c:choose>
            $('input[name="pic"]').val(image.remoteRelativeUrl);
            $('input[name="height"]').val(image.height);
            $('input[name="width"]').val(image.width);
            $('input[name="size"]').val(image.size);
            $('input[name="orgName"]').val(image.orgName);
            $('input[name="relativeFolder"]').val(image.relativeFolder);
            $('input[name="remoteRelativeUrl"]').val(image.remoteRelativeUrl);
            $('input[name="name"]').val(image.name);
            $('#upload-img-pic').find('img').prop('src', avatar);
//            $('tr[data="' + passportId + '"]').find('._avatar').prop('src', avatar);
        });
        upload.on('uploadError', function (file) {
            $.zxxbox.remind("上传出错", null, {delay: 2000});
        });

    });
</script>
