<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">电站管理</a></li>
            <li><a href="#">电站列表</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <li>
                <label style="width: 70px;">报警类型</label>
                <div class="vocation">
                    <select name="type">
                        <option value="">全部</option>
                        <c:forEach var="s" items="${WARNING_ENUM}">
                            <option value="${s.value}"
                                    <c:if test="${s.value == type}">selected</c:if> >${s.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
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
            <li><label style="width: 70px;">关键字</label>
                <input name="keyword" type="text" class="scinput" value="${keyword}"/>
            </li>
            <li><label style="width: 70px;">起始时间</label>
                <input name="timeStartStr" type="text" class="scinput Wdate" value="${timeStartStr}"
                       onfocus="WdatePicker()"/>
            </li>
            <li><label style="width: 70px;">结束时间</label>
                <input name="timeEndStr" type="text" class="scinput Wdate" value="${timeEndStr}"
                       onfocus="WdatePicker()"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="warningList.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <th>电站名字</th>
            <th>电站编号</th>
            <th>电站地址</th>
            <th>关联运营商名字</th>
            <th>关联物业</th>
            <th>充电站温度</th>
            <th>报警类型</th>
            <th>上报时间</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td>${i.stationName}</td>
                <td>${i.stationNumber}</td>
                <td>${i.address}</td>
                <td>
                    <c:if test="${i.operatorName != null}"> ${i.operatorName}</c:if>
                    <c:if test="${i.operatorName == null}"><font class="color-red">未关联</font></c:if>
                </td>
                <td>
                    <c:if test="${i.estateName != null}">${i.estateName}</c:if>
                    <c:if test="${i.estateName == null}"><font class="color-red">未关联</font></c:if>
                </td>
                <td>${i.temp}</td>
                <td>
                    <a href="javascript:;" class="tablelink _updateBtn">
                        <c:if test="${WARNING_ENUM_TEMPERATURE_OVERRUN.value == i.warningType}">
                            <font class="color-red">${WARNING_ENUM_TEMPERATURE_OVERRUN.name}</font>
                        </c:if>
                        <c:if test="${WARNING_ENUM_CHASSIS_OPENED.value == i.warningType}">
                            <font class="color-red">${WARNING_ENUM_CHASSIS_OPENED.name}</font>
                        </c:if>
                        <c:if test="${WARNING_ENUM_POWER_FAILURE.value == i.warningType}">
                            <font class="color-red">${WARNING_ENUM_POWER_FAILURE.name}</font>
                        </c:if>
                        <c:if test="${WARNING_ENUM_SMOKE_WARNING.value == i.warningType}">
                            <font class="color-red">${WARNING_ENUM_SMOKE_WARNING.name}</font>
                        </c:if>
                    </a>
                </td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
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
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=9zuWfXv8wmLzRIENsm5qFy88"></script>
<script type="text/javascript" src="${path}/js/bmap.js"></script>
<script>
    $(function () {
        $K.http('${path}/area/getAreaJson.htm', {}, function (r) {
            showLocation({
                item: r.result
            });
            var searchProvince = '${searchProvince}';
            var searchCity = '${searchCity}';
            var searchTown = '${searchTown}';
            $('#searchProvince').val(searchProvince).uedSelect('setOption').trigger('change');
            $('#searchCity').val(searchCity).uedSelect('setOption').trigger('change');
            $('#searchTown').val(searchTown).uedSelect('setOption').trigger('change');
        });
        //_searchBtn
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })
    })
</script>