<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<link rel="stylesheet" type="text/css" href="${path}/js/3rd/jstree/themes/default/style.min.css"/>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css"/>
<div class="top" style="min-width: 1450px;">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">位置监控</a></li>
            <li><a href="#">车辆位置</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="formbody">
    <div id="allmap" style="width:97%;height:85%;z-index: 1;"></div>
</div>
<%@include file="../footer.jsp" %>
<script type="text/javascript" src="${path}/js/3rd/jstree/jstree.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5ylayfeKso2O4RaQep2K8AWC183X2QM6"></script>
<script type="text/javascript"
        src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<script>
    $(function () {
        var polygons = [];
        var map = new JPMap();
        map.initMap("allmap");
        //获取数据
        $K.http('${path}/fence/getFences.htm', {}, function (r) {
            $.each(r.result.list, function (i, polygon) {
                polygons = polygons.concat(JSON.parse(polygon));
            })
            //
            map.addPolygon(polygons);
        });
        $K.http('${path}/fence/getDevices.htm', {}, function (r) {
            $.each(r.result.list, function (i, device) {
                map.addCarMark({
                    id: device.id,
                    number: device.deviceNumber,
                    in: device.fenceStatus == 'in',
                    lng: device.lnge5 / 1e5,
                    lat: device.late5 / 1e5,
                });
            })
        });
    })

</script>