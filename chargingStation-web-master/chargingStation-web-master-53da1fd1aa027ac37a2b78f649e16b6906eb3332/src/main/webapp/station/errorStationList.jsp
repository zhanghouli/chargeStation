<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <c:if test="${estateId != null && operatorId != null  }">
                <li><a href="${path}/passport/operatorList.htm?operatorId=${operatorId}">运营商</a></li>
                <li><a href="${path}/passport/estateList.htm?estateId=${estateId}&operatorId=${operatorId}">物业</a></li>
            </c:if>
            <c:if test="${estateId == null && operatorId != null  }">
                <li><a href="${path}/passport/operatorList.htm?operatorId=${operatorId}">运营商</a></li>
            </c:if>
            <c:if test="${estateId != null && operatorId == null }">
                <li><a href="${path}/passport/estateList.htm?estateId=${estateId}">物业</a></li>
            </c:if>
            <c:if test="${estateId == null && operatorId == null}">
                <li><a href="#">电站管理</a></li>
            </c:if>

            <li><a href="#">电站列表</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <c:if test="${estate != null}">
        <div class="tools">
            <ul class="toolbar">
                <a href="#" class="_addBtn">
                    <li class="click"><span><img src="${path}/images/t01.png"/></span>添加电站</li>
                </a>
            </ul>
        </div>
    </c:if>
    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <input name="estateId" type="hidden" value="${searchStationVo.estateId}">
            <input name="operatorId" type="hidden" value="${searchStationVo.operatorId}">
            <c:if test="${passport.type != PASSPORT_GOVERNMENT.value}">
                <li>
                    <label>区域选择</label>
                    <div class="vocation" style="margin-right: 10px;">
                        <select class="loc_province" name="searchProvince" id="searchProvince">
                        </select>
                    </div>
                    <div class="vocation" style="margin-right: 10px;">
                        <select class="loc_city" name="searchCity" id="searchCity">
                        </select>
                    </div>
                    <div class="vocation">
                        <select class="loc_town" name="searchTown" id="searchTown">
                        </select>
                    </div>
                </li>
            </c:if>
            <li><label>关键字</label>
                <input name="keyword" type="text" class="scinput" value="${keyword}"/>
            </li>

            <li>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="errorStationList.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <%--<th>头像</th>--%>
            <th>电站名字</th>
            <th>电站编号</th>
            <th>电站地址</th>
            <th style="width: 84px;">关联运营商名字</th>
            <th style="width: 55px">关联物业</th>
            <th style="width: 72px;">充电套餐名称</th>
            <th style="width: 60px">充电口数量</th>
            <th style="width: 30px">状态</th>
            <th>创建时间</th>
            <th>修改时间</th>
            <th style="width: 208px">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                    <%--<td>暂无图片</td>--%>
                <td>${i.name}</td>
                <td>${i.number}</td>
                <td>${i.address}</td>
                <td>
                    <c:if test="${i.operatorName != null}"> ${i.operatorName}</c:if>
                    <c:if test="${i.operatorName == null}"><font class="color-red">未关联</font></c:if>
                </td>
                <td>
                    <c:if test="${i.estateName != null}">${i.estateName}</c:if>
                    <c:if test="${i.estateName == null}"><font class="color-red">未关联</font></c:if>
                </td>
                <td>${i.consumePackageName}</td>
                <td>${i.portCount}</td>
                <td>

                    <c:if test="${STATION_STATUS_NORMAL.value == i.status}">
                        <font class="color-green">正常</font>
                    </c:if>
                    <c:if test="${STATION_STATUS_DISABLE.value == i.status}">
                        <font class="color-red">禁用</font>
                    </c:if>
                    <c:if test="${STATION_STATUS_FAULT.value == i.status}">
                        <font class="color-orange">故障</font>
                    </c:if>
                        <%--<a href="javascript:;" class="tablelink _updateBtn">--%>
                        <%--<img src="${path}/images/edit.png" class="icon-edit">--%>
                        <%--</a>--%>
                </td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td><fmt:formatDate value='${i.modifyTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td>
                    <a href="${path}/station/stationDetails.htm?stationId=${i.id}&estateId=${estateId}&operatorId=${operatorId}&stationType=fault"
                       class="tablelink ">电站详情</a>&nbsp;
                    <a href="javascript:;" class="tablelink _modifyBtn">电站修改</a>&nbsp;
                    <a href="javascript:;" class="tablelink _addStationPor">充电口添加</a>&nbsp;
                    <br>
                    <a href="${path}/station/stationPortList.htm?stationId=${i.id}&estateId=${estateId}&operatorId=${operatorId}&stationType=fault"
                       class="tablelink _selectStationPor">充电口管理</a>&nbsp;
                    <a href="${path}/station/stationMaintainLogList.htm?stationId=${i.id}&estateId=${estateId}&operatorId=${operatorId}&stationType=fault"
                       class="tablelink ">电站维护</a>&nbsp;
                    <a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">${page}</div>
</div>
<%@include file="../footer.jsp" %>
<!--modify station -->
<ul class="forminfo seachform zxxbox_contaner short validationEngineContainer formbody" id="addOrModifyStation"
    style="width:450px">
    <!--id！=null就进来了-->
    <input type="hidden" name="id" value=""/>
    <input type="hidden" name="lngE5" value=""/>
    <input type="hidden" name="latE5" value=""/>
    <input type="hidden" name="operatorId"/>
    <input type="hidden" name="estateId"/>
    <li class="line">
        <label>电站编号:</label>
        <input id="number" name="number" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" maxlength="45" type="text"
               class="short-input validate[required]"/>
    </li>
    <li class="line">
        <label>电站名称:</label>
        <input id="name" name="name" type="text" placeholder="电站名称" class="short-input validate[required]"/>
    </li>
    <li class="line">
        <label>充电口数量:</label>
        <input id="portCount" name="portCount" type="number" class="short-input validate[required]"/>
    </li>
    <li class="line">
        <label>支付套餐Id:</label>
        <%--<input id="consumePackageId" name="consumePackageId" type="text" class="short-input validate[required]"/>--%>
        <div class="vocation">
            <select width="130" id="consumePackageId" name="consumePackageId" class=" validate[required]">
                <%--<option>the</option>--%>
                <%--<option>hell</option>--%>
            </select>(没有套餐请与此相关联运营商增加套餐)
        </div>
    </li>
    <li class="line">
        <label>充电站电费:</label>
        <input name="electricBillStation" type="text" class="short-input validate[required,custom[moneyAny]]"/>(单位:元/KWH)
    </li>
    <li class="line">
        <label>运营商比例分成:</label>
        <input id="operatorSharingRatio" name="operatorSharingRatio" type="number"
               class="short-input validate[required]"/>
    </li>
    <li class="line">
        <label>物业比例分成:</label>
        <input id="estateSharingRatio" name="estateSharingRatio" type="number" class="short-input validate[required]"/>
    </li>
    <li class="line _address">
        <label>区域地址：</label>
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
    <li class="line">
        <label>详细地址</label>
        <input name="address" type="text" class="short-input validate[required]" id="address"/>
        <a href="javascript:;" id="mapBtn" style="line-height: 34px;" class="tablelink">地图选择</a>
    </li>

    <li style="margin-left: 120px;">
        <label>&nbsp;</label>
        <input type="button" class="btn saveBtn" value="确认保存"/>
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
<!--add station port -->
<ul class="forminfo zxxbox_contaner short validationEngineContainer formbody" id="addOrModifyStationPort"
    style="width:350px">
    <input type="hidden" name="stationId" value=""/>
    <!--id！=null就进来了-->
    <li class="line">
        <label>充电口编号:</label>
        <input id="portNumber" name="portNumber" type="text" class="short-input validate[required]"/>
    </li>
    <li class="line">
        <label>充电口二维码:</label>
        <input id="qrCode" name="qrCode" type="text" class="short-input validate[required]"/>
    </li>
    <li class="line">
        <label>最大功率:</label>
        <input id="maxPower" name="maxPower" type="number"
               class="short-input validate[required,custom[positiveInteger]]"/>

    </li>
    <li class="line">
        <label>最小功率:</label>
        <input id="minPower" name="minPower" type="number"
               class="short-input validate[required,custom[positiveInteger]]"/>
    </li>
    <li class="line">
        <label>涓流时间:</label>
        <input id="trickleTime" name="trickleTime" type="number"
               class="short-input validate[required,custom[positiveInteger]]"/>
    </li>
    <li class="line">
        <label>开启充满自停:</label>
        开启:<input id="isAutoStop" name="isAutoStop" type="radio" value="1" checked="checked"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        关闭:<input id="isAutoStop" name="isAutoStop" type="radio" value="0"/>
        <%--<div class="vocation">--%>
        <%--<select name="isAutoStop">--%>
        <%--<c:forEach var="s" items="${COMMON_OPEN_OR_CLOSE}">--%>
        <%--<option value="${s.value}"--%>
        <%--<c:if test="${s.value == status}">selected</c:if> >${s.name}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
        <%--</div>--%>
    </li>
    <li class="line">
        <label>开启大功率:</label>
        开启:<input name="isLargePower" type="radio" id="isLargePower" value="1" checked="checked"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        关闭:<input name="isLargePower" type="radio" id="isLargePower" value="0"/>
        <%--<select name="isLargePower">--%>
        <%--<c:forEach var="s" items="${COMMON_OPEN_OR_CLOSE}">--%>
        <%--<option value="${s.value}"--%>
        <%--<c:if test="${s.value == status}">selected</c:if> >${s.name}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
    </li>

    <li style="margin-left: 120px;">
        <label>&nbsp;</label>
        <input type="button" class="btn saveBtnStationPort" value="确认保存"/>
    </li>
</ul>

<script type="text/javascript" src="${path}/js/3rd/cityselect/js/area.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/location.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/select2.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=9zuWfXv8wmLzRIENsm5qFy88"></script>
<script type="text/javascript" src="${path}/js/bmap.js"></script>
<script>
    $(function () {
        var searchProvince = '${searchProvince}';
        var searchCity = '${searchCity}';
        var searchTown = '${searchTown}';
        $K.http('${path}/area/getAreaJson.htm', {}, function (r) {
            showLocation({
                item: r.result
            });
            $('#searchProvince').val(searchProvince).uedSelect('setOption').trigger('change');
            $('#searchCity').val(searchCity).uedSelect('setOption').trigger('change');
            $('#searchTown').val(searchTown).uedSelect('setOption').trigger('change');
        });


        //_searchBtn
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })

        //show modify
        $('._addBtn').on('click', function () {
            $('#addOrModifyStation').find('input[type="text"],input[type="number"],input[type="hidden"],input[type="number"]').val('');
            var operatorId = '${estate.operatorId}';
            var estateId = '${estate.id}';
            var bill = '${estate.electricBill}';
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
                    $('#province').val(cityAddres[0]).uedSelect('setOption').trigger('change');
                    $('#city').val(cityAddres[1]).uedSelect('setOption').trigger('change');
                    $('#town').val(cityAddres[2]).uedSelect('setOption').trigger('change');

                    $.zxxbox($('#addOrModifyStation'), {
                        title: '新建电站'
                    });
                });
        });


        // show modify
        $('._modifyBtn').on('click', function () {
            //@@@ area
            $('#consumePackageId').empty();
            $('#addOrModifyStation').find('input[type="text"],input[type="number"],input[type="hidden"]').val('');
            var stationId = $(this).parents('tr').attr('data');
            $('input[name="id"]').val(stationId);
            $K.http("toggleStationId.htm", {
                stationId: stationId
            }, function (r) {
                var stationPackageVo = r.result;
                var packageMap = stationPackageVo.maps;
                var station = stationPackageVo.station;
                var cityArrdes = new Array();
                cityArrdes = station.area.split(',');
                $('input[name="number"]').val(station.number);
                $('input[name="name"]').val(station.name);
                $('input[name="address"]').val(station.address);
                $('input[name="portCount"]').val(station.portCount);
                $('input[name="electricBillStation"]').val(station.electricBill / 100);
                $('input[name="latE5"]').val(station.latE5);
                $('input[name="lngE5"]').val(station.lngE5);
                $('#consumePackageId').append("<option value=''>请选择</option>");
                $.each(packageMap, function (index, element) {
                    $('#consumePackageId').append("<option value=" + index + ">" + element + "</option>");
                })
                var obj = document.getElementById("consumePackageId");
                var objText = $('#consumePackageId').parents('.vocation');
                var text = "";
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].value == station.consumePackageId) {
                        obj[i].selected = true;
                        obj.parentNode.childNodes[0].childNodes[0].innerHTML = obj[i].text;
                        break;
                    } else {
                        obj[0].selected = true;
                        obj.parentNode.childNodes[0].childNodes[0].innerHTML = obj[0].text;
                    }
                }
                $('input[name="operatorSharingRatio"]').val(station.operatorSharingRatio);
                $('input[name="estateSharingRatio"]').val(station.estateSharingRatio);
                $('#province').val(cityArrdes[0]).uedSelect('setOption').trigger('change');
                $('#city').val(cityArrdes[1]).uedSelect('setOption').trigger('change');
                $('#town').val(cityArrdes[2]).uedSelect('setOption').trigger('change');

                $.zxxbox($('#addOrModifyStation'), {
                    title: "修改充电站"
                })
            })
        })

        //remove
        $('._removeBtn').on('click', function () {
            var stationId = $(this).parents('tr').attr('data');
            $.zxxbox.ask("确定删除？谨慎操作", function () {
                $K.http("removeStationId.htm", {
                    stationId: stationId
                }, function (r) {
                    $.zxxbox.remind("操作成功。", null, {
                        delay: 3000,
                        onclose: function () {
                            location.reload();
                        }
                    })
                })
            })
        })

        // toggle
        $('._updateBtn').on('click', function () {
            //
            var stationId = $(this).parents('tr').attr('data');
            $.zxxbox.ask('确定更改状态?', function () {
                $K.http('toggleStationId.htm', {
                    stationId: stationId
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

        // show add stationPort
        $('._addStationPor').on('click', function () {
            $('#addOrModifyStationPort').find("input[type=text],input[type=number]").val('');
            var stationId = $(this).parents("tr").attr("data");
            $('input[name="stationId"]').val(stationId);
            $.zxxbox($('#addOrModifyStationPort'), {
                title: "新建充电口"
            })
        })
        //add stationPort
        $('.saveBtnStationPort').ajaxbtn("addStationPort.htm", function () {
            var stationPort = $('#addOrModifyStationPort').serialize();
            var number = $('input[name="portNumber"]').val();
            stationPort.number = number;
            return stationPort;
        }, function () {
            return $('#addOrModifyStationPort').validationEngine('validate', {validateNonVisibleFields: true});
        });

        $('.saveBtn').ajaxbtn("doAddOrModifyStation.htm", function () {
            var station = $('#addOrModifyStation').serialize();
            var area = [$("#province").val(), $("#city").val(), $("#town").val()];
            var areaDes = [$("#province option:selected").text(), $("#city option:selected").text(), $("#town option:selected").text()];
            station.area = area.join(',');
            station.areaDes = areaDes.join(',');
            var lngE5;
            var latE5;
            if (stationPoint) {
                lngE5 = $.trim(stationPoint.lng.toFixed(5)) * 1E5;
                latE5 = $.trim(stationPoint.lat.toFixed(5)) * 1E5;
            }
            station.lngE5 = lngE5 == 0 ? station.lngE5 : lngE5;
            station.latE5 = latE5 == 0 ? station.latE5 : latE5;
            var bill = $.trim($('input[name="electricBillStation"]').val() * 100);
            station.electricBill = bill;
            return station;
        }, function () {
            return $('#addOrModifyStation').validationEngine('validate', {validateNonVisibleFields: true});
        })


        //map
        //@@@@ map
        var stationPointTmp = null;
        var stationPoint = {
            lng: $('input[name="lngE5"]').val() / 1E5,
            lat: $('input[name="latE5"]').val() / 1E5,
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
            $('#mapSearchKeyword').val(keyword);
            $('#poies').empty();
            $('.mapContainer').show();
            stationPoint = {
                lng: $('input[name="lngE5"]').val() / 1E5,
                lat: $('input[name="latE5"]').val() / 1E5,
            };
            if (stationPoint) {
                addMark(stationPoint);
            }
            if (!$K.tools.isBlank(keyword)) {
                map.moveToByCity(bound)
                map.search(keyword, searchHandler);
            }
        });
        //--关闭地图
        $('#closeMapBtn').on('click', function () {
            stationPointTmp = null;
            stationPoint = {
                lng: $('input[name="lngE5"]').val() / 1E5,
                lat: $('input[name="latE5"]').val() / 1E5,
            };
            $('.mapContainer').hide();
        });
        //--地图搜索
        $('#mapSearch').on('click', function () {
            var keyword = $.trim($('#mapSearchKeyword').val());
            console.log(stationPoint)
            map.search(keyword, searchHandler);
        });

        //--地图确定位置
        $('#mapOkBtn').on('click', function () {
            if (stationPointTmp) {
                console.log(stationPoint)
                stationPoint = stationPointTmp;
            }
            console.log(stationPoint)
            console.log(stationPointTmp);
            $('input[name="address"]').val($('#mapSearchKeyword').val());
            $('.mapContainer').hide();
        });
    })
</script>