<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<link href="${path}/css/city/common.css" rel="stylesheet"/>
<link href="${path}/css/city/select2.css" rel="stylesheet"/>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <c:if test="${operatorId!=null}">
                <li><a href="${path}/passport/operatorList.htm?operatorId=${operatorId}">运营商</a></li>
            </c:if>
            <c:if test="${operatorId==null}">
                <li><a href="#">用户管理</a></li>
            </c:if>
            <li><a href="#">${PASSPORT_PROPERTY.name}</a></li>

        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <c:if test="${operator !=null }">
        <div class="tools">
            <ul class="toolbar">
                <a href="#" class="_addBtn">
                    <li class="click"><span><img src="${path}/images/t01.png"/></span>添加物业</li>
                </a>
            </ul>
        </div>
    </c:if>

    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <input name="operatorId" type="hidden" value="${operatorId}">
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
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="estateList.htm"/>
            </li>
        </form>
    </ul>
    <table class="imgtable">
        <thead>
        <tr>
            <th>头像</th>
            <th style="width: 84px;">关联运营商名字</th>
            <th style="width: 64px;">账号登录名</th>
            <th>物业姓名/手机号</th>
            <th>物业地址</th>
            <th style="width: 124px;">物业电费(元/KWH)</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>修改时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}" data-estateId="${i.estateId}" data-operatorId="${i.operatorId}"
                data-bill="${i.electricBill}">
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
                <td>
                    <c:if test="${i.operatorName==null}"><font class="color-red">未关联</font></c:if>
                    <c:if test="${i.operatorName!=null}">${i.operatorName}</c:if>
                </td>
                <td>${i.userName}</td>
                <td>${i.realName}/${i.phone}</td>
                <td>${i.address}</td>
                <td><fmt:formatNumber value="${i.electricBill/100}" pattern="￥0.00"/></td>
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
                    <a href="javascript:;" class="tablelink  _addStation">充电站添加</a>&nbsp;
                    <a href="${path}/station/stationList.htm?estateId=${i.estateId}&operatorId=${operatorId}"
                       class="tablelink ">查看电站</a>&nbsp;
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


<script type="text/javascript" src="${path}/js/3rd/city/area.js"></script>
<script type="text/javascript" src="${path}/js/3rd/city/location.js"></script>
<script type="text/javascript" src="${path}/js/3rd/city/select2.js"></script>
<script type="text/javascript" src="${path}/js/3rd/city/select2_locale_zh-CN.js"></script>
<%--add or modify--%>
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="addOrModifyEstate" style="width:420px">
    <input name="passportId" type="hidden" value=""/>
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
        <input name="passwordEstate" id="passwordEstate" type="password" class="short-input"/>
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
        <input name="contactPhone" type="text" class="short-input validate[required]"/>
    </li>
    <%--<li class="line">--%>
    <%--<label>区域地址</label>--%>
    <%--<input name="cityName" type="text" readonly="readonly" class="short-input"/>--%>
    <%--</li>--%>
    <li>
        <label>物业详细地址:</label>
        <input name="addressEstate" type="text" class="short-input validate[required]"/>
    </li>
    <li>
        <label>物业电费:</label>
        <input name="electricBill" type="text" class="short-input validate[required,custom[moneyAny]]"/>单位(元)/KWH
    </li>
    <li style="text-align: center;">
        <input name="" type="button" class="btn _doAddOrModifyEstate" value="确认"/>
    </li>
</ul>
<ul class="forminfo seachform zxxbox_contaner short validationEngineContainer formbody" id="addOrModifyStation"
    style="width:450px">
    <input type="hidden" name="id"/>
    <input type="hidden" name="operatorId"/>
    <input type="hidden" name="estateId"/>
    <!--id！=null就进来了-->
    <input type="hidden" name="id" value=""/>
    <li class="line">
        <label>电站编号:</label>
        <div class="vocation">
            <input id="number" name="number" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" type="text"
                   class="short-input validate[required]"/>
        </div>
    </li>
    <p style="text-align: center">(编号末尾为0-9或A-F 会触发批量电站条件)</p>
    <li class="line">
        <label>批量电站编号至:</label>
        <div class="vocation">
            <select width="130" id="batchNumber" name="batchNumber">
                <option value="">请选择</option>
            </select>(选择之后会进行批量添加)
        </div>
        <%--()--%>
    </li>
    <li class="line">
        <label>电站名称:</label>
        <input id="name" name="name" type="text" class="short-input validate[required]"/>
    </li>
    <li class="line">
        <label>充电口数量:</label>
        <input id="portCount" name="portCount" type="text"
               class="short-input validate[required,custom[numberInteger]]"/>
    </li>
    <li class="line">
        <label>支付套餐Id:</label>
        <div class="vocation">
            <select width="130" id="consumePackageId" name="consumePackageId" class=" validate[required]">
                <option value="">请选择</option>
                <%--<option>the</option>--%>
                <%--<option>hell</option>--%>
            </select>(没有套餐请与此相关联运营商增加套餐)
        </div>
        <%--<div class="parent">--%>
        <%--<select name="consumePackageId" id="consumePackageId" class="validate[required]">--%>

        <%--</select>--%>
        <%--</div>--%>
        <%--<input id="consumePackageId" name="consumePackageId" type="text" class="short-input validate[required]"/>--%>
    </li>
    <li class="line">
        <label>充电站电费:</label>
        <input name="electricBillStation" type="text" class="short-input validate[required,custom[moneyAny]]"/>元/KWH
    </li>
    <li class="line">
        <label>运营商比例分成:</label>
        <input id="operatorSharingRatio" name="operatorSharingRatio" type="number"
               class="short-input validate[required,custom[positiveInteger]]"/>
    </li>
    <li class="line">
        <label>物业比例分成:</label>
        <input id="estateSharingRatio" name="estateSharingRatio" type="number"
               class="short-input validate[required,custom[positiveInteger]]"/>
        <span class="span-text tips _tips"></span>
    </li>
    <li class="line _address">
        <label>运营商地址：</label>
        <div class="vocation" style="margin-right: 3px">
            <select width="100" class="loc_province" id="province" name="province"></select>
        </div>
        <div class="vocation" style="margin-right: 3px">
            <select width="100" class="loc_city" id="city" name="city"></select>
        </div>
        <div class="vocation" style="margin-right: 3px">
            <select width="100" class="loc_town" id="town" name="town"></select>
        </div>
    </li>
    <li class="line">
        <label>详细地址</label>
        <input name="address" type="text" class="short-input validate[required]" id="address"/>
        <a href="javascript:;" id="mapBtn" style="line-height: 34px;" class="tablelink">地图选择</a>
    </li>

    <li style="margin-left: 120px;">
        <label>&nbsp;</label>
        <input type="button" class="btn saveBtn" value="确认保存"/>
        <%--<input type="button" class="btn saveBtn" style="float: left" value="批量添加">--%>
    </li>
</ul>
<!-- map-->
<div class="mapContainer">
    <div class="wrap_bar">
        <span>当前地址:<b id="mapTitle"></b><button class="scbtn" id="mapOkBtn">确定</button><a id="closeMapBtn"></a></span>
    </div>
    <div id="mapLeft" class="mapLeft">
        <div class="searchBtn" style="float:none">
            <input type="text" class="" style="width:55%;" id="mapSearchKeyword">
            <input type="button" value="搜索" class="scbtn" style="width:30%;" id="mapSearch">
        </div>
        <ul id="poies">
            <li>没有数据</li>
        </ul>
    </div>
    <div id="allmap"></div>
</div>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/area.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/location.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/select2.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=9zuWfXv8wmLzRIENsm5qFy88"></script>
<script type="text/javascript" src="${path}/js/bmap.js"></script>
<script>
    $(function () {
        $('.saveBtn').ajaxbtn('${path}/station/doAddOrModifyStation.htm', function () {
            var station = $('.formbody').serialize();
            var area = [$("#province").val(), $("#city").val(), $("#town").val()];
            var areaDes = [$("#province option:selected").text(), $("#city option:selected").text(), $("#town option:selected").text()];
            var bill = $.trim($('input[name="electricBillStation"]').val() * 100);
            station.area = area.join(',');
            station.areaDes = areaDes.join(',');

            var lngE5;
            var latE5;
            if (stationPoint) {
                lngE5 = $.trim(stationPoint.lng.toFixed(5)) * 1E5;
                latE5 = $.trim(stationPoint.lat.toFixed(5)) * 1E5;
            }

            station.lngE5 = lngE5;
            station.latE5 = latE5;
            station.electricBillStation = bill;
            return station;
        }, function () {
            var lngE5;
            var latE5;
            if (stationPoint) {
                lngE5 = $.trim(stationPoint.lng.toFixed(5)) * 1E5;
                latE5 = $.trim(stationPoint.lat.toFixed(5)) * 1E5;
            }
            if (lngE5 == 0 && latE5 == 0) {
                alert("请地图选址");
                return false;
            }
            var operatorSharingRatio = parseInt($('input[name="operatorSharingRatio"]').val());
            var estateSharingRatio = parseInt($('input[name="estateSharingRatio"]').val());
            if ((operatorSharingRatio + estateSharingRatio) > 100) {
                $('._tips').css("color", "red");
                $('._tips').html("运营商物业分成比例的总和不超过100");
                return false;
            }
            return $('#addOrModifyStation').validationEngine('validate', {validateNonVisibleFields: true});
        });
        // toggle
        $('._updateBtn').on('click', function () {
            //
            var estateId = $(this).parents('tr').attr('data');
            $.zxxbox.ask('确定更改状态?', function () {
                $K.http('toggleEstateIsEnabled.htm', {
                    estateId: estateId
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
            $.zxxbox($('#addOrModifyBox'), {
                title: '新增物业'
            });
        });
        //show modify
        $('._addStation').on('click', function () {
            $('#addOrModifyStation').find('input[type="text"],input[type="number"],input[type="hidden"],input[type="number"]').val('');
            var obj = $('#batchNumber').parents('.vocation');
            obj.find('.uew-select-text').html("请选择");
            $("#batchNumber").empty();
            var select = "请选择";
            $("#batchNumber").append("<option value=''>" + select + "</option>");
            var operatorId = $(this).parents('tr').attr('data-operatorId');
            var estateId = $(this).parents('tr').attr('data-estateId');
            var bill = $(this).parents("tr").attr('data-bill');
            $("#consumePackageId").empty();
            $('input[name="estateId"]').val(estateId);
            $K.http('${path}/passport/getOperatorId.htm',
                {
                    operatorId: operatorId
                }, function (r) {
                    var operator = r.result;
                    var cityAddres = operator.area.split(',');
                    $('input[name="operatorId"]').val(operator.operatorId);
                    var map = operator.maps;
                    $("#consumePackageId").append("<option value=''>请选择</option>");
                    $('input[name="operatorSharingRatio"]').val(operator.operatorDivided);
                    $('input[name="estateSharingRatio"]').val(operator.estateDivided);
                    if (map) {
                        $.each(map, function (index, element) {
                            $("#consumePackageId").append("<option value=" + index + ">" + element + "</option>");
                        })
                    }
                    //@@@ area
                    $K.http('${path}/area/getAreaJson.htm', {}, function (r) {
                        showLocation({
                            item: r.result
                        });
                        $('#province').val(cityAddres[0]).uedSelect('setOption').trigger('change');
                        $('#city').val(cityAddres[1]).uedSelect('setOption').trigger('change');
                        $('#town').val(cityAddres[2]).uedSelect('setOption').trigger('change');
                    });
                    $.zxxbox($('#addOrModifyStation'), {
                        title: '新建电站'
                    });
                });
        });
        /*物业新增*/
        $('._addBtn').on('click', function () {
            $('#addOrModifyEstate').find('input[type="text"],input[type="password"],input[type="hidden"]').val("");
            var operatorId = '${operator.id}';
            $('input[name="operatorId"]').val(operatorId);
            $('#upload-img-picEstate').find("img").attr("src", "../images/default-image.png");
            $.zxxbox($('#addOrModifyEstate'), {
                title: "新增物业"
            })
        })
        /* 物业修改 触发事件*/
        $('._modifyBtn').on('click', function () {
            $('#addOrModifyBox').find('input[type="text"],input[type="number"],input[type="hidden"],input[type="password"]').val('');
            var passportId = $(this).parents('tr').attr('data');
            var estateId = $(this).parents('tr').attr('data-estateId');
            $('input[name="id"]').val(estateId);
            $K.http('${path}/passport/getEstateInfo.htm',
                {
                    estateId: estateId,
                    passportId: passportId
                }, function (r) {
                    var passportOrEstateVo = r.result
                    $('input[name="passportId"]').val(passportOrEstateVo.id);
                    $('input[name="estateId"]').val(passportOrEstateVo.estateId);
                    $('input[name="userNameEstate"]').val(passportOrEstateVo.userName);
                    $('input[name="realNameEstate"]').val(passportOrEstateVo.realName);
                    $('input[name="phoneEstate"]').val(passportOrEstateVo.phone);
                    $('input[name="contactName"]').val(passportOrEstateVo.contactName);
                    $('input[name="contactPhone"]').val(passportOrEstateVo.contactPhone);
                    $('input[name="addressEstate"]').val(passportOrEstateVo.address);
                    $('input[name="electricBill"]').val(passportOrEstateVo.electricBill / 100);
                    if (passportOrEstateVo.pic) {
                        $('input[name="estatePic"]').val(passportOrEstateVo.pic);
                        $('input[name="estatePicId"]').val(passportOrEstateVo.picId);

                        <c:choose>
                        <c:when test="${ISDEV}">
                        $('#upload-img-picEstate').find("img").attr("src", ${path}"/files" + passportOrEstateVo.pic);
                        </c:when>
                        <c:when test="${!ISDEV}">
                        $('#upload-img-picEstate').find("img").attr("src", '${path}' + passportOrEstateVo.pic);
                        </c:when>
                        </c:choose>
                    }
                    $.zxxbox($('#addOrModifyEstate'), {
                        title: '查看/修改物业信息'
                    });
                });
        });
        //do add or modify
        $('._doAddOrModifyEstate').ajaxbtn('doAddOrModifyEstate.htm', function () {
            return {
                id: $.trim($('input[name="passportId"]').val()),
                userName: $.trim($('input[name="userNameEstate"]').val()),
                realName: $.trim($('input[name="realNameEstate"]').val()),
                phone: $.trim($('input[name="phoneEstate"]').val()),
                password: $.trim($('input[name="passwordEstate"]').val()),
                operatorId: $.trim($('input[name="operatorId"]').val()),
                address: $('input[name="addressEstate"]').val(),
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
            return $('#addOrModifyEstate').validationEngine('validate', {validateNonVisibleFields: true});
        });
        // remove
        $('._removeBtn').on('click', function () {
            var passportId = $(this).parents('tr').attr('data');
            $.zxxbox.ask('本次操作会删除相关电站', function () {
                $K.http('doRemoveEstate.htm', {
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
        var sixTeen = new Array("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F");
        //批量号码计算
        $('#number').blur(function () {
            var obj = $('#batchNumber').parents('.vocation');
            obj.find('.uew-select-text').html("请选择");
            $("#batchNumber").empty();
            var select = "请选择";
            $("#batchNumber").append("<option value=''>" + select + "</option>");
            //追加变量
            var isAdd = false;
            //当前输入的值
            var value = $(this).val();
            //长度 不小于2
            var length = value.length;
            //拼字符串
            var subValue = value.substring(0, value.length - 1);
            var arr = [];
            if (value.length > 0) {
                var v2 = value[length - 1];
                for (var i = 0; i < sixTeen.length; i++) {
                    if (isAdd) {
                        var addValue = subValue + sixTeen[i];
                        arr.push(addValue);
                    }
                    if (sixTeen[i] === v2) {
                        isAdd = true;
                    }
                }
            }
            if (arr) {
                $.each(arr, function (index, element) {
                    $("#batchNumber").append("<option value=" + element + ">" + element + "</option>");
                })
            }
        })
        //头像
        var upload = WebUploader.create({
            swf: '../js/webuploader/Uploader.swf',
            server: '${path}/common/file/uploadImage.htm',
            pick: '#upload-img-picEstate',
            auto: true,
            formData: {
                bucket: 'avatar'
            }
        });
        //图片返回成功
        upload.on('uploadSuccess', function (file, data) {
            var image = data.result.imageVo;
            if (data.code == 0) {
                $.zxxbox.remind("请上传正确的图片类型", null, {delay: 2000});
                return false;
            }
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
            var passportId = $('input[name="id"]').val();
            $('#upload-img-picEstate').find('img').prop('src', avatar);
            $('tr[data-estateId="' + passportId + '"]').find('._avatar').prop('src', avatar);
        });
        //图片返回失败
        upload.on('uploadError', function (file) {
            $.zxxbox.remind("上传出错", null, {delay: 2000});
        });


        //map
        //@@@@ map
        var stationPointTmp = null;
        var stationPoint = {
            lng: ${shop.lngE5/1E5},
            lat: ${shop.latE5/1E5}
        };
        var map = new JPMap();
        map.initMap("allmap");
        //--加mark
        function addMark(point) {
            stationPoint = point;
            map.addMark({
                id: 0,
                lng: point.lng,
                lat: point.lat
            }, {
                dragendHandler: function (newPoint) {
                    stationPointTmp = newPoint;
                }
            }, {
                draggable: true
            });
        }


        //--地图搜索回调
        function searchHandler(poies) {
            $('#poies').empty();
            if (poies.lenght <= 0) {
                $('#poies').html('<li>没有数据</li>');
                return;
            }
            for (var index = 0; index < poies.length; index++) {
                var poi = poies[index];
                if (0 == index && !stationPoint) {
                    addMark(poi.point);
                }
                if (BMAP_POI_TYPE_NORMAL != poi.type) {
                    continue;
                }
                $('#poies').append('<li data="' + index + '">[' + poi.title + ']' + poi.address + '</li>');
            }
            $('#poies').find('li').on('click', function () {
                $(this).addClass('cur').siblings().removeClass('cur')
                var index = $(this).attr('data');
                var poi = poies[index];
                addMark(poi.point);
                $('#mapSearchKeyword').val('[' + poi.title + ']' + poi.address);
            });
        }

        //--显示地图
        $('#mapBtn').on('click', function () {
            if ($K.tools.isBlank($('#province').val()) && $K.tools.isBlank($('#city').val()) && $K.tools.isBlank($('#town').val()) && $K.tools.isBlank($('#address').val())) {
                alert('请先输入地址');
                return;
            }
            var province = $K.tools.isBlank($('#province').val()) ? '' : $('#province option:selected').text();
            var city = $K.tools.isBlank($('#city').val()) ? '' : $('#city option:selected').text();
            var town = $K.tools.isBlank($('#town').val()) ? '' : $('#town option:selected').text();
            var address = $.trim($('#address').val());
            var bound = province + city + town;
            var keyword = address;
            $('#mapTitle').text(bound + keyword);
            if ($K.tools.isBlank(keyword)) {
                $('#mapSearchKeyword').val(bound);
            } else {
                $('#mapSearchKeyword').val(address);
            }

            $('#poies').empty();
            $('.mapContainer').show();
            if (stationPoint) {
                addMark(stationPoint);
            }
            if (!$K.tools.isBlank(keyword)) {
                map.moveToByCity(bound)
                map.search(keyword, searchHandler);
            } else {
                map.moveToByCity(bound);
                map.search(bound, searchHandler);
            }
        });
        //--关闭地图
        $('#closeMapBtn').on('click', function () {
            stationPointTmp = null;
            stationPoint = {
                lng: ${shop.lngE5/1E5},
                lat: ${shop.latE5/1E5}
            };
            console.log(stationPoint)
            $('.mapContainer').hide();
        });
        //--地图搜索
        $('#mapSearch').on('click', function () {
            var keyword = $.trim($('#mapSearchKeyword').val());
            map.search(keyword, searchHandler);
        });
        //--地图确定位置
        $('#mapOkBtn').on('click', function () {
            if (stationPointTmp) {
                stationPoint = stationPointTmp;
            }
            $('input[name="address"]').val($('#mapSearchKeyword').val());
            $('.mapContainer').hide();
        });
    });
</script>

