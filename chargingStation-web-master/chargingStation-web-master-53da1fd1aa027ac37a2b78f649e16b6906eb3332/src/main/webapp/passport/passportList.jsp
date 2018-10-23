<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">用户管理</a></li>
            <li><a href="#">${PASSPORT_ADMIN.name}</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="javascript:;" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加</li>
            </a>
        </ul>
    </div>

    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <input name="type" type="hidden" value="${type}"/>
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="passportList.htm"/>
            </li>
        </form>
    </ul>
    <table class="imgtable">
        <thead>
        <tr>
            <th>头像</th>
            <th>账号</th>
            <th>姓名/手机号</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>修改时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td class="imgtd">
                    <c:choose>
                        <c:when test="${ISDEV}">
                            <img class="lazy img-radius _avatar" style="height: 60px;"
                                 data-original="${path}${files}${i.pic== null?'../images/default-image.png':i.pic}"/>
                        </c:when>
                        <c:when test="${!ISDEV}">
                            <img class="lazy img-radius _avatar" style="height: 60px;"
                                 data-original="${path}${i.pic== null?'../images/default-image.png':i.pic}"/>
                        </c:when>
                    </c:choose>
                </td>
                <td>${i.userName}</td>
                <td>${i.realName}/${i.phone}</td>
                <td>
                    <a href="javascript:;" class="tablelink _updateBtn">
                        <c:if test="${PASSPORT_STATUS_NORMAL.value == i.status}">
                            <font class="color-green">${PASSPORT_STATUS_NORMAL.name}</font>
                        </c:if>
                        <c:if test="${PASSPORT_STATUS_DISABLE.value == i.status}">
                            <font class="color-red">${PASSPORT_STATUS_DISABLE.name}</font>
                        </c:if>
                        <img src="${path}/images/edit.png" class="icon-edit">
                    </a>
                </td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td><fmt:formatDate value='${i.modifyTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td>

                    <a href="${path}/auth/auth.htm?passportId=${i.id}&status=${PASSPORT_ADMIN.value}" class="tablelink">权限管理</a>
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
<%--add or modify--%>
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="addOrModifyBox" style="width:350px">
    <input name="id" type="hidden" value=""/>
    <input name="picId" type="hidden" value=""/>
    <input name="height" type="hidden" value=""/>
    <input name="width" type="hidden" value=""/>
    <input name="size" type="hidden" value=""/>
    <input name="orgName" type="hidden" value=""/>
    <input name="relativeFolder" type="hidden" value=""/>
    <input name="remoteRelativeUrl" type="hidden" value=""/>
    <input name="name" type="hidden" value=""/>

    <li class="line">
        <label>头像:</label>
        <div class="upload-img" id="upload-img-pic" style="width: 90px;height: 90px">
            <input name="pic" type="hidden" class="short-input" value=""/>
            <img src="../images/upload-image.png" class="img-radius use-default"
                 style="width: 90px;height: 90px;border-radius:45px"/>
        </div>
        <img src="${path}/images/edit.png" class="icon-edit" style="margin: 20px;">
        <div class="clear"></div>
    </li>
    <li>
        <label>登录账号:</label>
        <input name="userName" type="text" class="short-input validate[required]"/>
    </li>
    <li>
        <label>姓名:</label>
        <input name="realName" type="text" class="short-input validate[required]"/>
    </li>
    <li>
        <label>手机号:</label>
        <input name="phone" type="text" class="short-input validate[required]"/>
    </li>
    <li>
        <label>密码:</label>
        <input name="password" id="password" type="password" class="short-input"/>
    </li>
    <li>
        <label>确认密码:</label>
        <input name="confirmPassword" type="password" class="short-input"/>
    </li>
    <li style="text-align: center;">
        <input name="" type="button" class="btn _doAddOrModifyBtn" value="确认"/>
    </li>
</ul>
<script>
    $(function () {
        // toggle
        $('._updateBtn').on('click', function () {
            var admin = $(this).parents('tr').attr('data-admin');
            if (admin == 'true') {
                $.zxxbox.remind('超级管理员不允许修改');
                return;
            }
            //
            var passportId = $(this).parents('tr').attr('data');
            $.zxxbox.ask('确定更改状态?', function () {
                $K.http('toggleCustomService.htm', {
                    passportId: passportId
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

        // search
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop('action', $(this).attr('action')).submit();
        });
        //show add
        $('._addBtn').on('click', function () {
            $('#addOrModifyBox').find('input[type="text"],input[type="hidden"],input[type="password"]').val('');
            $('#upload-img-pic').find("img").attr("src", "../images/upload-image.png");
            $('input[name="password"]').addClass('validate[required]');
            $('input[name="confirmPassword"]').addClass('validate[required,equals[password]]');
            $.zxxbox($('#addOrModifyBox'), {
                title: '新增管理员'
            });
        });
        //show modify
        $('._modifyBtn').on('click', function () {
            $('#addOrModifyBox').find('input[type="text"],input[type="hidden"],input[type="password"]').val('');
            var id = $(this).parents('tr').attr('data');
            $('input[name="id"]').val(id);
            $K.http('${path}/passport/getPassportInfo.htm',
                {
                    passportId: id
                }, function (r) {
                    var passport = r.result;
                    if (passport.picId) {
                        <c:choose>
                        <c:when test="${ISDEV}">
                        $('#upload-img-pic').find("img").attr("src", ${path}"/files" + passport.pic);
                        </c:when>
                        <c:when test="${!ISDEV}">
                        $('#upload-img-pic').find("img").attr("src", '${path}'+passport.pic);
                        </c:when>
                        </c:choose>
                        $('input[name="pic"]').val(passport.pic);
                        $('input[name=picId]').val(passport.picId);
                    }
                    $('input[name="phone"]').val(passport.phone);
                    $('input[name="realName"]').val(passport.realName);
                    $('input[name="userName"]').val(passport.userName);
                    $.zxxbox($('#addOrModifyBox'), {
                        title: '查看/修改管理员信息'
                    });
                });
        });
        //do add or modify
        $('._doAddOrModifyBtn').ajaxbtn('doAddOrModifyPassport.htm', function () {
            return {
                id: $.trim($('input[name="id"]').val()),
                phone: $.trim($('input[name="phone"]').val()),
                realName: $.trim($('input[name="realName"]').val()),
                userName: $.trim($('input[name="userName"]').val()),
                pic: $.trim($('input[name="pic"]').val()),
                password: $.trim($('input[name="password"]').val()),
                type: $.trim($('input[name="type"]').val()),
                height: $.trim($('input[name="height"]').val()),
                width: $.trim($('input[name="width"]').val()),
                size: $.trim($('input[name="size"]').val()),
                orgName: $.trim($('input[name="orgName"]').val()),
                relativeFolder: $.trim($('input[name="relativeFolder"]').val()),
                remoteRelativeUrl: $.trim($('input[name="remoteRelativeUrl"]').val()),
                name: $.trim($('input[name="name"]').val()),
                picId: $.trim($('input[name="picId"]').val()),
            }
        }, function () {
            var password = $.trim($('input[name="password"]').val());
            if (!$K.tools.isBlank(password)) {
                $('input[name="confirmPassword"]').addClass('validate[required,equals[password]]');
            }
            return $('#addOrModifyBox').validationEngine('validate', {validateNonVisibleFields: true});
        });
        // remove
        $('._removeBtn').on('click', function () {
            var data = $(this).parents('tr').attr('data');
            var passportId = data;
            $.zxxbox.ask('确定要删除?', function () {
                $K.http('doRemovePassport.htm', {
                    passportId: passportId
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

        // toggle
        $('._customServiceBtn').on('click', function () {
            var data = $(this).parents('tr').attr('data');
            var passportId = data;
            $.zxxbox.ask('确定改变状态?', function () {
                $K.http('toggleCustomService.htm', {
                    passportId: passportId
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

        //头像
        var upload = WebUploader.create({
            swf: '../js/webuploader/Uploader.swf',
            server: '${path}/common/file/uploadImage.htm',
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
            var avatar = ${path}"/files" + image.remoteRelativeUrl;
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
            $('tr[data="' + passportId + '"]').find('._avatar').prop('src', avatar);
        });
        upload.on('uploadError', function (file,data) {
            $.zxxbox.remind("上传出错", null, {delay: 2000});
        });
    });
</script>

