<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<link rel="stylesheet" type="text/css" href="${path}/css/city/webuploader.css">
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="${path}/pay/wxPayList.htm">微信配置管理</a></li>
            <li><a href="#">微信配置</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="formbody">

    <div class="formtitle">编辑配置</div>

    <ul class="forminfo short validationEngineContainer">

        <input type="hidden" name="id" value="${wxConfig.id}"/>
        <li class="line">
            <label>所属端:</label>
            <div class="vocation">
                <select name="platform" class="useselect validate[required]">
                    <c:forEach var="s" items="${PLAT_FORM}">
                        <option value="${s.value}" <c:if test="${s.value == wxConfig.platform}">selected</c:if>>
                                ${s.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </li>
        <li class="line">
            <label>pay_key:</label>
            <input id="payKey" name="payKey" type="text" class="short-input validate[required]"
                   value="${wxConfig.payKey}"/>
        </li>
        <li class="line">
            <label>pay_appid:</label>
            <input id="payAppid" name="payAppid" type="text" class="short-input validate[required]"
                   value="${wxConfig.payAppid}"/>
        </li>
        <li class="line">
            <label>pay_secret:</label>
            <input id="paySecret" name="paySecret" type="text" class="short-input validate[required]"
                   value="${wxConfig.paySecret}"/>
        </li>
        <li class="line">
            <label>mch_id:</label>
            <input id="mchId" name="mchId" type="text" class="short-input validate[required]"
                   value="${wxConfig.mchId}"/>
        </li>
        <li class="line">
            <label>cert_local_path:</label>
            <input id="certLocalPath" name="certLocalPath" type="text" class="short-input validate[required]"
                   value="${wxConfig.certLocalPath}"/>
        </li>
        <li class="line">
            <label>cert_password:</label>
            <input id="certPassword" name="certPassword" type="text" class="short-input validate[required]"
                   value="${wxConfig.certPassword}"/>
        </li>
        <li class="line">
            <label>notify_url:</label>
            <input id="notifyUrl" name="notifyUrl" type="text" class="short-input validate[required]"
                   value="${wxConfig.notifyUrl}"/>
        </li>
        <li class="line">
            <label>sub_mch_id:</label>
            <input id="subMchId" name="subMchId" type="text" class="short-input validate[required]"
                   value="${wxConfig.subMchId}"/>
        </li>
        <li class="line">
            <label>ip:</label>
            <input id="ip" name="ip" type="text" class="short-input validate[required]" value="${wxConfig.ip}"/>
        </li>
        <li class="line">
            <%--<label>域名验证文件:</label>--%>
            <input name="veryfy" type="hidden"/>
            <%--<div id="uploader" class="wu-example">--%>
            <%--<!--用来存放文件信息-->--%>
            <%--<div id="thelist" class="uploader-list"></div>--%>
            <%--<div class="btns">--%>
            <%--<div id="picker">选择文件</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <label>域名验证文件:</label>
            <div id="uploader" class="wu-example">
                <!--用来存放文件信息-->
                <div class="this-weiyi">
                    <div class="picker"><h3 style="color: white">选择文件</h3></div>
                </div>
            </div>
            <font id="veryfy"></font>
            <c:if test="${wxConfig.veryfy!=null}"> <font id="veryfy2">已上传文件路径:${wxConfig.veryfy}</font></c:if>
        </li>
        <li class="line">
            <label>支付签名文件:</label>
            <input name="cert" type="hidden"/>
            <div id="uploader2" class="wu-example">
                <!--用来存放文件信息-->
                <div class="this-weiyi">
                    <div class="picker"><h3 style="color: white">选择文件</h3></div>
                </div>
            </div>
            <font id="cert"></font>
            <c:if test="${wxConfig.cert != null}"> <font id="cert2">已上传文件路径:${wxConfig.cert}</font></c:if>
        </li>
        <li style="margin-left: 120px;">
            <label>&nbsp;</label>
            <input type="button" class="btn saveBtn" value="确认保存"/>
        </li>
    </ul>
</div>
<%@include file="../footer.jsp" %>
<script>
    $(function () {

        $('.saveBtn').ajaxbtn('addOrModify.htm', function () {
            var wxConfig = $('.forminfo').serialize();
            return wxConfig;
        }, function () {
            return $('.forminfo').validationEngine('validate', {validateNonVisibleFields: true});
        })


        var upload = WebUploader.create({
            swf: '../js/webuploader/Uploader.swf',
            server: '${path}/common/file/upload.htm',
            pick: '#uploader',
            auto: true,
            formData: {
                bucket: 'veryfy'
            }
        });
        upload.on('uploadSuccess', function (file, data) {
            var avatar = data.result.path;
            var passportId = $('input[name="id"]').val();
            $('input[name="veryfy"]').val(data.result.path);
            $('#veryfy').html("上传成功!");
            $('#veryfy2').hide();
//            $('#upload-img-pic').find('img').prop('src', avatar);
//            $('tr[data="' + passportId + '"]').find('._avatar').prop('src', avatar);
        });
        upload.on('uploadError', function (file) {
            $.zxxbox.remind("上传出错", null, {delay: 2000});
        });


        var upload2 = WebUploader.create({
            swf: '../js/webuploader/Uploader.swf',
            server: '${path}/common/file/upload.htm',
            pick: '#uploader2',
            auto: true,
            formData: {
                bucket: 'cert'
            }
        });
        upload2.on('uploadSuccess', function (file, data) {
            var avatar = data.result.path;
            var passportId = $('input[name="id"]').val();
            $('input[name="cert"]').val(data.result.path);
            $('#cert').html("上传成功!");
            $('#cert2').hide();
//            $('#upload-img-pic').find('img').prop('src', avatar);
//            $('tr[data="' + passportId + '"]').find('._avatar').prop('src', avatar);
        });
        upload2.on('uploadError', function (file) {
            $.zxxbox.remind("上传出错", null, {delay: 2000});
        });
    })
</script>
