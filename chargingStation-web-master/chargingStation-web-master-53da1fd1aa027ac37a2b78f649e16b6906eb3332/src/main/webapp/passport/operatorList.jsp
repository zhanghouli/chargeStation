<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">用户管理</a></li>
            <li><a href="#">${PASSPORT_OPERATOR.name}</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="#" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加运营商</li>
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="operatorList.htm"/>
            </li>
        </form>
    </ul>
    <table class="imgtable">
        <thead>
        <tr>
            <th>头像</th>
            <th style="width: 54px;">登录账号</th>
            <th>运营商名字</th>
            <th>手机号</th>
            <th>运营商地址</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>修改时间</th>
            <th style="width: 190px">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}" data-operatorId="${i.operatorId}">
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
                    <a href="javascript:;" class="tablelink _addEstate">物业添加</a>&nbsp;
                    <a href="${path}/consume/consumePackageOperatorIdList.htm?operatorId=${i.operatorId}"
                       class="tablelink ">电口套餐</a>&nbsp;
                    <a href="${path}/passport/estateList.htm?operatorId=${i.operatorId}" class="tablelink ">查看物业</a>&nbsp;
                    <a href="${path}/station/stationList.htm?operatorId=${i.operatorId}" class="tablelink ">查看电站</a>&nbsp;
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
<!--add or modify estate -->
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="addOrModifyEstate" style="width:370px">
    <input name="operatorId" type="hidden" value=""/>
    <input name="estateId" type="hidden" value=""/>
    <input name="estatePicId" type="hidden" value=""/>
    <input name="estateName" type="hidden" value=""/>
    <input name="estateOrgName" type="hidden" value=""/>
    <input name="estateHeight" type="hidden" value=""/>
    <input name="estateWidth" type="hidden" value=""/>
    <input name="estateSize" type="hidden" value=""/>
    <input name="estateRelativeFolder" type="hidden" value=""/>
    <input name="estateRemoteRelativeUrl" type="hidden" value=""/>
    <li class="line">
        <label>头像:</label>
        <div class="upload-img" id="upload-img-picEstate" style="width: 90px;height: 90px">
            <input name="estatePic" type="hidden" class="short-input" value=""/>
            <img src="../images/default-image.png" class="img-radius use-default"
                 style="width: 90px;height: 90px;border-radius:45px"/>
        </div>
        <img src="${path}/images/edit.png" class="icon-edit" style="margin: 20px;">
        <div class="clear"></div>
    </li>
    <li>
        <label>登录账号:</label>
        <input name="userNameEstate" type="text" class="short-input validate[required]"/>
    </li>
    <li>
        <label>登录姓名:</label>
        <input name="realNameEstate" type="text" class="short-input validate[required]"/>
    </li>
    <li>
        <label>登录手机号:</label>
        <input name="phoneEstate" type="text" class="short-input validate[required,custom[mobile]]"/>
    </li>
    <li>
        <label>密码:</label>
        <input name="passwordEstate" id="passwordEstate" type="password" class="short-input validate[required]"/>
    </li>
    <li>
        <label>确认密码:</label>
        <input name="confirmPasswordEstate" type="password" class="short-input"/>
    </li>
    <li>
        <label>联系人姓名:</label>
        <input name="contactName" type="text" class="short-input validate[required]"/>
    </li>
    <li>
        <label>联系人手机号:</label>
        <input name="contactPhone" type="text" class="short-input validate[required,custom[mobile]]"/>
    </li>
    <li>
        <label>物业详细地址:</label>
        <input name="address" type="text" class="short-input validate[required]"/>
    </li>
    <li>
        <label>物业电费:</label>
        <input name="electricBill" type="text" class="short-input validate[required,custom[moneyAny]]"/>元/KWH
    </li>
    <li style="text-align: center;">
        <input name="" type="button" class="btn _doAddOrModifyEstate" value="确认"/>
    </li>
</ul>
<!--add or modify consume package -->
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="addOrModifyConsume" style="width:370px">
    <input name="ConsumeOperatorId" type="hidden" value="">
    <li>
        <label>套餐名称:</label>
        <input name="name" type="text" class="short-input validate[required]"/>
    </li>
    <li style="text-align: center;">
        <input name="" type="button" class="btn _doAddOrModifyConsume" value="确认"/>
    </li>
</ul>

<!--add or modify operator -->
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="addOrModifyBox" style="width:450px">
    <input name="id" type="hidden" value=""/>
    <input name="operatorPicId" type="hidden" value=""/>
    <input name="operatorName" type="hidden" value=""/>
    <input name="operatorOrgName" type="hidden" value=""/>
    <input name="operatorHeight" type="hidden" value=""/>
    <input name="operatorWidth" type="hidden" value=""/>
    <input name="operatorSize" type="hidden" value=""/>
    <input name="operatorRelativeFolder" type="hidden" value=""/>
    <input name="operatorRemoteRelativeUrl" type="hidden" value=""/>

    <li class="line">
        <label>头像:</label>
        <div class="upload-img" id="upload-img-pic" style="width: 90px;height: 90px">
            <input name="operatorPic" type="hidden" class="short-input" value=""/>
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
        <label>运营商姓名:</label>
        <input name="realName" type="text" class="short-input validate[required]"/>
    </li>
    <li>
        <label>运营商手机号:</label>
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
        <label>运营区域地址：</label>
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
                title: '新增运营商'
            });
        });
        //show modify
        $('._modifyBtn').on('click', function () {
            $('#addOrModifyBox').find('input[type="text"],input[type="hidden"],input[type="password"]').val('');
            var id = $(this).parents('tr').attr('data');
            upload.options.formData.passportId = id
            $('input[name="id"]').val(id);
            $K.http('${path}/passport/getPassportOrOperator.htm',
                {
                    passportId: id
                }, function (r) {
                    var operator = r.result;

                    if (operator.picId) {
                        <c:choose>
                        <c:when test="${ISDEV}">
                        $('#upload-img-pic').find("img").attr("src", ${path}"/files" + operator.pic);
                        </c:when>
                        <c:when test="${!ISDEV}">
                        $('#upload-img-pic').find("img").attr("src", '${path}' + operator.pic);
                        </c:when>
                        </c:choose>
                        $('input[name="pic"]').val(operator.pic);
                        $('input[name=picId]').val(operator.picId);
                    }
                    $('input[name="realName"]').val(operator.realName);
                    $('input[name="userName"]').val(operator.userName);
                    $('input[name="phone"]').val(operator.phone);
                    var cityArrdes = Array(3);
                    cityArrdes = operator.area.split(",");
                    $K.http('${path}/area/getAreaJson.htm', {}, function (e) {
                        showLocation({
                            item: e.result
                        });
                        $('#province').val(cityArrdes[0]).uedSelect('setOption').trigger('change');
                        $('#city').val(cityArrdes[1]).uedSelect('setOption').trigger('change');
                        $('#town').val(cityArrdes[2]).uedSelect('setOption').trigger('change');
                    });
                    $.zxxbox($('#addOrModifyBox'), {
                        title: '查看/修改运营商信息'
                    });
                });
        });
        //add estate
        $('._addEstate').on('click', function () {
            $('#addOrModifyEstate').find('input[type="text"],input[type="password"],input[type="hidden"]').val("");
            var operatorId = $(this).parents("tr").attr("data-operatorId");
            $('input[name="operatorId"]').val(operatorId);
            $('#upload-img-picEstate').find("img").attr("src", "../images/default-image.png");
            $.zxxbox($('#addOrModifyEstate'), {
                title: "新增物业"
            })
        })
        //do add or modify
        $('._doAddOrModifyBtn').ajaxbtn('doAddOrModifyOperator.htm', function () {
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
                pic: $.trim($('input[name="operatorPic"]').val()),
                picId: $.trim($('input[name="operatorPicId"]').val()),
                name: $.trim($('input[name="operatorName"]').val()),
                orgName: $.trim($('input[name="operatorOrgName"]').val()),
                height: $.trim($('input[name="operatorHeight"]').val()),
                width: $.trim($('input[name="operatorWidth"]').val()),
                size: $.trim($('input[name="operatorSize"]').val()),
                relativeFolder: $.trim($('input[name="operatorRelativeFolder"]').val()),
                remoteRelativeUrl: $.trim($('input[name="operatorRemoteRelativeUrl"]').val()),
            }
        }, function () {
            var password = $.trim($('input[name="password"]').val());
            if (!$K.tools.isBlank(password)) {
                $('input[name="confirmPassword"]').addClass('validate[required,equals[password]]');
            }
            return $('#addOrModifyBox').validationEngine('validate', {validateNonVisibleFields: true});
        });
        //do add or modify Estate
        $('._doAddOrModifyEstate').ajaxbtn('doAddOrModifyEstate.htm', function () {
//            var address = $('input[name="cityName"]').val() + "-" + $('input[name="address"]').val();
            return {
                id: $.trim($('input[name="estateId"]').val()),
                userName: $.trim($('input[name="userNameEstate"]').val()),
                realName: $.trim($('input[name="realNameEstate"]').val()),
                phone: $.trim($('input[name="phoneEstate"]').val()),
                password: $.trim($('input[name="passwordEstate"]').val()),
                operatorId: $.trim($('input[name="operatorId"]').val()),
                address: $('input[name="address"]').val(),
                electricBill: $.trim($('input[name="electricBill"]').val() * 100),
                contactPhone: $.trim($('input[name="contactPhone"]').val()),
                contactName: $.trim($('input[name="contactName"]').val()),
                pic: $.trim($('input[name="estatePic"]').val()),
                picId: $.trim($('input[name="estatePicId"]').val()),
                name: $.trim($('input[name="estateName"]').val()),
                orgName: $.trim($('input[name="estateOrgName"]').val()),
                height: $.trim($('input[name="estateHeight"]').val()),
                width: $.trim($('input[name="estateWidth"]').val()),
                size: $.trim($('input[name="estateSize"]').val()),
                relativeFolder: $.trim($('input[name="estateRelativeFolder"]').val()),
                remoteRelativeUrl: $.trim($('input[name="estateRemoteRelativeUrl"]').val()),
            }
        }, function () {
            var passwordEstate = $.trim($('input[name="passwordEstate"]').val());
            if (!$K.tools.isBlank(passwordEstate)) {
                $('input[name="confirmPasswordEstate"]').addClass('validate[required,equals[passwordEstate]]');
            }
            return $('#addOrModifyEstate').validationEngine('validate', {validateNonVisibleFields: true});
        })
        // remove
        $('._removeBtn').on('click', function () {
            var passportId = $(this).parents('tr').attr('data');
            var operatorId = $(this).parents('tr').attr('data-operatorId');
            $K.http('getStationCountsOrEstateCounts.htm', {
                operatorId: operatorId
            }, function (r) {
                if (parseInt(r.result) > 0) {
                    $.zxxbox.remind("本帐户有关联电站，请先删除关联电站", null, {
                        delay: 3000,
                        onclose: function () {
                            location.reload();
                        }
                    });
                } else {
                    $.zxxbox.ask('确定要删除?谨慎操作!', function () {
                        $K.http('doRemoveOperatorId.htm', {
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
                }
            })

        });
        //show add
        $('._addConsume').on('click', function () {
            $('#addOrModifyConsume').find($('input[name="name"]').val(''))
            var consumeId = $(this).parents('tr').attr('data-operatorId');
            $('input[name="ConsumeOperatorId"]').val(consumeId);
            $.zxxbox($('#addOrModifyConsume'), {
                title: "新增电口套餐"
            })
        })
        //do add or modify consume
        $('._doAddOrModifyConsume').ajaxbtn('doAddOrModifyConsume.htm', function () {
            return {
                operatorId: $.trim($('input[name="ConsumeOperatorId"]').val()),
                name: $.trim($('input[name="name"]').val()),
            }
        }, function () {
            return $('#addOrModifyConsume').validationEngine('validate', {validateNonVisibleFields: true});
        })
        //运营商头像
        var upload = WebUploader.create({
            swf: '../js/webuploader/Uploader.swf',
            server: '${path}/common/file/uploadImage.htm',
            pick: '#upload-img-pic',
            auto: true,
            formData: {
                bucket: 'avatar',
                passportId: ""
            }
        });
        upload.on('uploadSuccess', function (file, data) {
            console.log(data);
            if (data.code == 0) {
                $.zxxbox.remind("请上传正确的图片类型", null, {delay: 2000});
                return false;
            }
            var passportId = $('input[name="id"]').val();
            var image = data.result.imageVo;
            <c:choose>
            <c:when test="${ISDEV}">
            var avatar = ${path}"/files" + image.remoteRelativeUrl;
            </c:when>
            <c:when test="${!ISDEV}">
            var avatar = '${path}' + image.remoteRelativeUrl;
            </c:when>
            </c:choose>
            $('input[name="operatorPic"]').val(image.remoteRelativeUrl);
            $('input[name="operatorHeight"]').val(image.height);
            $('input[name="operatorWidth"]').val(image.width);
            $('input[name="operatorSize"]').val(image.size);
            $('input[name="operatorOrgName"]').val(image.orgName);
            $('input[name="operatorRelativeFolder"]').val(image.relativeFolder);
            $('input[name="operatorRemoteRelativeUrl"]').val(image.remoteRelativeUrl);
            $('input[name="operatorName"]').val(image.name);
            $('#upload-img-pic').find('img').prop('src', avatar);
            $('tr[data="' + passportId + '"]').find('._avatar').prop('src', avatar);
        });
        upload.on('uploadError', function (file) {
            $.zxxbox.remind("上传出错", null, {delay: 2000});
        });


        //物业头像
        var uploadEstate = WebUploader.create({
            swf: '../js/webuploader/Uploader.swf',
            server: '${path}/common/file/uploadImage.htm',
            pick: '#upload-img-picEstate',
            auto: true,
            formData: {
                bucket: 'avatar',
                passportId: ""
            }
        });
        uploadEstate.on('uploadSuccess', function (file, data) {

            if (data.code == 0) {
                $.zxxbox.remind("请上传正确的图片类型", null, {delay: 2000});
                return false;
            }
            var image = data.result.imageVo;
            <c:choose>
            <c:when test="${ISDEV}">
            var avatar = ${path}"/files" + image.remoteRelativeUrl;
            </c:when>
            <c:when test="${!ISDEV}">
            var avatar = '${path}' + image.remoteRelativeUrl;
            </c:when>
            </c:choose>
            $('input[name="estatePic"]').val(image.remoteRelativeUrl);
            $('input[name="estateHeight"]').val(image.height);
            $('input[name="estateWidth"]').val(image.width);
            $('input[name="estateSize"]').val(image.size);
            $('input[name="estateOrgName"]').val(image.orgName);
            $('input[name="estateRelativeFolder"]').val(image.relativeFolder);
            $('input[name="estateRemoteRelativeUrl"]').val(image.remoteRelativeUrl);
            $('input[name="estateName"]').val(image.name);
            $('input[name="estatePic"]').val(image.remoteRelativeUrl);
            $('#upload-img-picEstate').find('img').prop('src', avatar);
        });
        uploadEstate.on('uploadError', function (file, data) {
            $.zxxbox.remind("上传出错", null, {delay: 2000});
        });

    });
</script>

