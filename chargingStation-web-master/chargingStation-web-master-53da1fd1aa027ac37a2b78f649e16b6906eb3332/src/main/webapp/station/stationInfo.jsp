<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">物业</a></li>
            <li><a href="#">电站添加</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="formbody">
    <div class="formtitle">
        <c:if test="${station.id == null}">
        <span>
            新增电站
        </span>
        </c:if>
        <c:if test="${station.id != null}">
        <span>
            修改电站
        </span>
        </c:if>
    </div>

    <ul class="forminfo short validationEngineContainer">
        <input type="hidden" name="id" value="${station.id}"/>
        <input type="hidden" name="operatorId" value="${operatorId}"/>
        <input type="hidden" name="estateId" value="${estateId}"/>
        <!--id！=null就进来了-->
        <input type="hidden" name="id" value=""/>
        <li class="line">
            <label>电站编号:</label>
            <input id="number" name="number" type="text" class="short-input validate[required]"
                   value="${station.number}"/>
        </li>
        <li class="line">
            <label>电站名称:</label>
            <input id="name" name="name" type="text" class="short-input validate[required]" value="${station.name}"/>
        </li>
        <li class="line">
            <label>充电口数量:</label>
            <input id="portCount" name="portCount" type="number" class="short-input validate[required]"
                   value="${station.portCount}"/>
        </li>
        <li class="line">
            <label>支付套餐Id:</label>
            <input id="consumePackageId" name="consumePackageId" type="number" class="short-input validate[required]"
                   value="${station.consumePackageId}"/>
        </li>
        <li class="line">
            <label>运营商比例分成:</label>
            <input id="operatorSharingRatio" name="operatorSharingRatio" type="number"
                   class="short-input validate[required]" value="${station.operatorSharingRatio}"/>
        </li>
        <li class="line">
            <label>物业比例分成:</label>
            <input id="estateSharingRatio" name="estateSharingRatio" type="number"
                   class="short-input validate[required]" value="${station.estateSharingRatio}"/>
        </li>
        <li class="line _address">
            <label>区域地址：</label>
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
            <input name="address" type="text" class="short-input" value="${station.address}" id="address"/>
            <a href="javascript:;" id="mapBtn" style="line-height: 34px;" class="tablelink">地图选择</a>
        </li>

        <li>
            <label>&nbsp;</label>
            <input type="button" class="btn saveBtn" value="确认保存"/>
        </li>
    </ul>
</div>

<%--map--%>
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
<%@include file="../footer.jsp" %>
<script>
    $(function () {


        $('.saveBtn').ajaxbtn('${path}/station/doAddOrModifyStation.htm', function () {
            var station = $('.formbody').serialize();
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
            station.lngE5 = lngE5;
            station.latE5 = latE5;
            return station;
        }, function () {
            return $('.formbody').validationEngine('validate');
        }, function () {
            $.zxxbox.ask("操作成功。", null, {
                delay: 3000,
                onclose: function () {
                    location.reload();
                }
            })
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

        //@@@ area
        $K.http('http://jbrain.jopool.com/api/region/getForWebCityPicker.htm?pk=ImXxJdmngANqOaVqmUmpxg9I', {}, function (r) {
            showLocation({
                item: r.result
            });
            <%--$('#province').val(cityNames[0]).uedSelect('setOption').trigger('change');--%>
            <%--$('#city').val(cityNames[1]).uedSelect('setOption').trigger('change');--%>
            <%--$('#town').val(cityNames[2]).uedSelect('setOption').trigger('change');--%>
        });

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
            var keyword = bound + address;
            $('#mapTitle').text(keyword);
            $('#mapSearchKeyword').val(keyword);
            $('#poies').empty();
            $('.mapContainer').show();
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
                lng: ${shop.lngE5/1E5},
                lat: ${shop.latE5/1E5}
            };
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