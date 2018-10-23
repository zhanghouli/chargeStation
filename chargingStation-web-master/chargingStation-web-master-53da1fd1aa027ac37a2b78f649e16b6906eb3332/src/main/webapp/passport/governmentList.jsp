<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">用户管理</a></li>
            <li><a href="#">${PASSPORT_GOVERNMENT.name}</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="#" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加政府</li>
            </a>
        </ul>
    </div>

    <ul class="seachform">
        <form method="get" action="" id="searchForm">
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="governmentList.htm"/>
            </li>
        </form>
    </ul>
    <table class="imgtable">
        <thead>
        <tr>
            <th>头像</th>
            <th>登录账号</th>
            <th>政府名字</th>
            <th>手机号</th>
            <th>政府地址</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>修改时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}" data-governmentId="${i.governmentId}">
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
                <td>${i.userName}</td>
                <td>${i.realName}</td>
                <td>${i.phone}</td>
                <td>${i.areaDes}</td>
                <td>
                    <a href="javascript:;" class="tablelink _updateBtn">
                        <c:if test="${PASSPORT_STATUS_NORMAL.value == i.status}">
                            <font class="color-green">正常</font>
                        </c:if>
                        <c:if test="${PASSPORT_STATUS_DISABLE.value == i.status}">
                            <font class="color-red">禁用</font>
                        </c:if>
                        <img src="${path}/images/edit.png" class="icon-edit">
                    </a>
                </td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td><fmt:formatDate value='${i.modifyTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td>
                    <a href="${path}/auth/auth.htm?passportId=${i.id}&status=${PASSPORT_GOVERNMENT.value}" class="tablelink">权限管理</a>
                    <a href="${path}/fence/fenceList.htm?governmentId=${i.governmentId}" class="tablelink">电子围栏</a>
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
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/area.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/location.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/select2.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/select2_locale_zh-CN.js"></script>
<!--add or modify operator -->
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="addOrModifyBox" style="width:450px">
    <input name="id" type="hidden" value=""/>
    <input name="governmentPicId" type="hidden" value=""/>
    <input name="governmentName" type="hidden" value=""/>
    <input name="governmentOrgName" type="hidden" value=""/>
    <input name="governmentHeight" type="hidden" value=""/>
    <input name="governmentWidth" type="hidden" value=""/>
    <input name="governmentSize" type="hidden" value=""/>
    <input name="governmentRelativeFolder" type="hidden" value=""/>
    <input name="governmentRemoteRelativeUrl" type="hidden" value=""/>

    <li class="line">
        <label>头像:</label>
        <div class="upload-img" id="upload-img-pic" style="width: 90px;height: 90px">
            <input name="governmentPic" type="hidden" class="short-input" value=""/>
            <img src="../images/default-image.png" class="img-radius use-default"
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
        <label>政府姓名:</label>
        <input name="realName" type="text" class="short-input validate[required]"/>
    </li>
    <li>
        <label>政府手机号:</label>
        <input name="phone" type="text" class="short-input validate[required,custom[mobile]]"/>
    </li>
    <li>
        <label>密码:</label>
        <input name="password" id="password" type="password" class="short-input "/>
    </li>
    <li>
        <label>确认密码:</label>
        <input name="confirmPassword" type="password" class="short-input"/>
    </li>
    <li class="line _address">
        <label>政府区域地址：</label>
        <div class="vocation" style="margin-right: 3px">
            <select width="100" class="loc_province validate[required]" id="province" name="province"></select>
        </div>
        <div class="vocation" style="margin-right: 3px">
            <select width="100" class="loc_city validate[required]" id="city" name="city"></select>
        </div>
        <div class="vocation" style="margin-right: 3px">
            <select width="100" class="loc_town validate[required]" id="town" name="town"></select>
        </div>
    </li>
    <li style="text-align: center;">
        <input name="" type="button" class="btn _doAddOrModifyBtn" value="确认"/>
    </li>
</ul>
<script>
    $(function () {
        // search
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop('action', $(this).attr('action')).submit();
        });
        // toggle
        $('._updateBtn').on('click', function () {
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
        // toggle
        $('._customServiceBtn').on('click', function () {
            //
            var passportId = $(this).parents('tr').attr('data');
            $.zxxbox.ask('确定更改状态?', function () {
                $K.http('toggleOperatorIsEnabled.htm', {
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

        //show add
        $('._addBtn').on('click', function () {
            $('#addOrModifyBox').find('input[type="text"],input[type="hidden"],input[type="password"]').val("");
            $('#upload-img-pic').find("img").attr("src", "../images/default-image.png");
            $K.http('${path}/area/getAreaJson.htm', {}, function (r) {
                showLocation({
                    item: r.result
                });
                $('#province').val("").uedSelect('setOption').trigger('change');
                $('#city').val("").uedSelect('setOption').trigger('change');
                $('#town').val("").uedSelect('setOption').trigger('change');
            });
            $('input[name="password"]').addClass('validate[required]');
            $('input[name="confirmPassword"]').addClass('validate[required,equals[password]]');
            $.zxxbox($('#addOrModifyBox'), {
                title: '新增政府'
            });
        });
        //show modify
        $('._modifyBtn').on('click', function () {
            $('#addOrModifyBox').find('input[type="text"],input[type="hidden"],input[type="password"]').val('');
            var id = $(this).parents('tr').attr('data');
            var governmentId = $(this).parents('tr').attr('data-governmentId');
            $('input[name="id"]').val(id);
            $K.http('${path}/passport/getPassportGovernment.htm',
                {
                    passportId: id,
                    governmentId: governmentId
                }, function (r) {
                    var government = r.result;
                    if (government.picId) {
                        $('input[name="governmentPic"]').val(government.pic);
                        $('input[name="governmentPicId"]').val(government.picId);
                        <c:choose>
                        <c:when test="${ISDEV}">
                        $('#upload-img-pic').find("img").attr("src", ${path}"/files" + government.pic);
                        </c:when>
                        <c:when test="${!ISDEV}">
                        $('#upload-img-pic').find("img").attr("src", '${path}'+government.pic);
                        </c:when>
                        </c:choose>
                    }
                    $('input[name="realName"]').val(government.realName);
                    $('input[name="userName"]').val(government.userName);
                    $('input[name="phone"]').val(government.phone);
                    var cityArrdes = Array(3);
                    cityArrdes = government.area.split(",");
                    $K.http('${path}/area/getAreaJson.htm', {}, function (e) {
                        showLocation({
                            item: e.result
                        });
                        $('#province').val(cityArrdes[0]).uedSelect('setOption').trigger('change');
                        $('#city').val(cityArrdes[1]).uedSelect('setOption').trigger('change');
                        $('#town').val(cityArrdes[2]).uedSelect('setOption').trigger('change');
                    });
                    $.zxxbox($('#addOrModifyBox'), {
                        title: '查看/修改政府信息'
                    });
                });
        });

        //do add or modify
        $('._doAddOrModifyBtn').ajaxbtn('doAddOrModifyGovernment.htm', function () {
            var area = [$("#province").val(), $("#city").val(), $("#town").val()];
            var areaDes = [$("#province option:selected").text(), $("#city option:selected").text(), $("#town option:selected").text()];

            return {
                id: $.trim($('input[name="id"]').val()),
                phone: $.trim($('input[name="phone"]').val()),
                realName: $.trim($('input[name="realName"]').val()),
                userName: $.trim($('input[name="userName"]').val()),
                password: $.trim($('input[name="password"]').val()),
                type: $.trim($('input[name="type"]').val()),
                area: area.join(','),
                areaDes: areaDes.join(','),
                pic: $.trim($('input[name="governmentPic"]').val()),
                picId: $.trim($('input[name="governmentPicId"]').val()),
                name: $.trim($('input[name="governmentName"]').val()),
                orgName: $.trim($('input[name="governmentOrgName"]').val()),
                height: $.trim($('input[name="governmentHeight"]').val()),
                width: $.trim($('input[name="governmentWidth"]').val()),
                size: $.trim($('input[name="governmentSize"]').val()),
                relativeFolder: $.trim($('input[name="governmentRelativeFolder"]').val()),
                remoteRelativeUrl: $.trim($('input[name="governmentRemoteRelativeUrl"]').val()),
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
            var governmentId = $(this).parents('tr').attr('data-governmentId');
            var passportId = data;
            $.zxxbox.ask('确定要删除?谨慎操作!', function () {
                $K.http('doRemoveGovernmentId.htm', {
                    passportId: passportId,
                    governmentId: governmentId
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
        //do add or modify consume
        $('._doAddOrModifyConsume').ajaxbtn('doAddOrModifyConsume.htm', function () {
            return {
                operatorId: $.trim($('input[name="ConsumeOperatorId"]').val()),
                name: $.trim($('input[name="name"]').val()),
            }
        }, function () {
            return $('#addOrModifyConsume').validationEngine('validate', {validateNonVisibleFields: true});
        })
        //政府头像
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
            var avatar ='${path}'+image.remoteRelativeUrl;
            </c:when>
            </c:choose>
            $('input[name="governmentPic"]').val(image.remoteRelativeUrl);
            $('input[name="governmentHeight"]').val(image.height);
            $('input[name="governmentWidth"]').val(image.width);
            $('input[name="governmentSize"]').val(image.size);
            $('input[name="governmentOrgName"]').val(image.orgName);
            $('input[name="governmentRelativeFolder"]').val(image.relativeFolder);
            $('input[name="governmentRemoteRelativeUrl"]').val(image.remoteRelativeUrl);
            $('input[name="governmentName"]').val(image.name);
            $('#upload-img-pic').find('img').prop('src', avatar);
            $('tr[data="' + passportId + '"]').find('._avatar').prop('src', avatar);
        });
        upload.on('uploadError', function (file) {
            $.zxxbox.remind("上传出错", null, {delay: 2000});
        });


    });
</script>

