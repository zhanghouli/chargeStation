<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">系统设置</a></li>
            <li><a href="#">开通城市</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="#" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加开通城市</li>
            </a>
        </ul>
    </div>

    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <li><label>关键字</label>
                <input name="keyword" type="text" class="scinput" value="${keyword}"/>
            </li>
            <li>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="systemConfigList.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <th>城市名称</th>
            <th>创建时间</th>
            <th>修改时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td>${i.name}</td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td><fmt:formatDate value='${i.modifyTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td>
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
<!--show add or modify -->
<ul class="forminfo zxxbox_contaner short validationEngineContainer formbody" id="addOrModify"
    style="width:450px">
    <input type="hidden" name="id" value=""/>
    <li class="line _address">
        <label>开通城市选择：</label>
        <div class="vocation" style="margin-right: 3px">
            <select width="120" class="loc_province validate[required]" id="province" name="province"></select>
        </div>
        <div class="vocation" style="margin-right: 3px">
            <select width="120" class="loc_city validate[required]" id="city" name="city"></select>
        </div>
    </li>
    <li style="margin-left: 120px;">
        <label>&nbsp;</label>
        <input type="button" class="btn saveBtn" value="确认保存"/>
    </li>
</ul>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/area.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/location.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/select2.js"></script>
<script type="text/javascript" src="${path}/js/3rd/cityselect/js/select2_locale_zh-CN.js"></script>
<script>
    $(function () {
        //search
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })
        //show add
        $('._addBtn').on('click', function () {
            $('#addOrModify').find('input[type="text"],input[type="hidden"]').val("");
            $K.http('${path}/area/getAreaJson.htm', {}, function (r) {
                showLocation({
                    item: r.result
                });
                $('#province').val("").uedSelect('setOption').trigger('change');
                $('#city').val("").uedSelect('setOption').trigger('change');
            });
            $.zxxbox($("#addOrModify"), {
                title: "新增开通城市"
            })
        })

        //add or modify
        $('.saveBtn').ajaxbtn("addOrModifyOpenArea.htm", function () {
            return {
                province:$("#province").val(),
                city:$("#city").val(),
                id:$.trim($('input[name="id"]').val())
            }
        }, function () {
            return $('#addOrModify').validationEngine("validate", {validateNonVisibleFields: true});
        })

        // show modify
        $('._modifyBtn').on('click', function () {
            var id = $(this).parents('tr').attr("data");

            $K.http("getOpenArea.htm", {
                openAreaId: id
            }, function (r) {
                var openArea = r.result;
                $K.http('${path}/area/getAreaJson.htm', {}, function (r) {
                    showLocation({
                        item: r.result
                    });
                    $('#province').val(openArea.provinceCode).uedSelect('setOption').trigger('change');
                    $('#city').val(openArea.cityCode).uedSelect('setOption').trigger('change');
                });
                $('input[name="id"]').val(id);
                $.zxxbox($('#addOrModify'), {
                    title: "修改开通城市"
                })
            })
        })

        //remove
        $('._removeBtn').on('click', function () {
            var id = $(this).parents("tr").attr("data");
            $.zxxbox.ask("确定删除？", function () {
                $K.http("removeOpenArea.htm", {
                    openAreaId: id
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
    })
</script>