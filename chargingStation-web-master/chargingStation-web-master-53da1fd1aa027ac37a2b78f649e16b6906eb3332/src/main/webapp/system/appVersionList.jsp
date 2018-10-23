<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<link rel="stylesheet" type="text/css" href="${path}/css/simditor.css"/>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">系统设置</a></li>
            <li><a href="#">APP更新配置</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="#" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加APP更新配置</li>
            </a>
        </ul>
    </div>
    <table class="tablelist">
        <thead>
        <tr>
            <th>包名</th>
            <th>系统类型</th>
            <th>当前版本</th>
            <th>更新链接</th>
            <th>是否强制升级</th>
            <th>升级说明</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td>${i.appId}</td>
                <td>${i.os}</td>
                <td>${i.lastVersion}</td>
                <td>${i.url}</td>
                <td>
                    <c:if test="${i.isForceUpdate == false}">
                        否
                    </c:if>
                    <c:if test="${i.isForceUpdate == true}">
                        是
                    </c:if>
                </td>
                <td>
                    <div class="mlellipsis" style="white-space: normal;text-overflow: ellipsis"
                         mlellipsis="1">${i.description}</div>
                </td>
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
<ul class="forminfo-plug zxxbox_contaner short validationEngineContainer formbody" id="addOrModify"
    style="width:450px">
    <input type="hidden" name="id" value=""/>
    <li class="liplug line">
        <label>包名:</label>
        <input id="appId" name="appId" type="text" class="short-input validate[required]"/>
    </li>
    <li class="liplug line">
        <label>系统类型:</label>
        <div class="vocation">
            <select name="os" style="opacity:1">
                <option value="ANDROID">ANDROID</option>
                <option value="IOS">IOS</option>
            </select>
        </div>
    </li>
    <li class="liplug line">
        <label>当前版本:</label>
        <input id="lastVersion" name="lastVersion" type="text" class="short-input validate[required]"/>
    </li>
    <li class="liplug line">
        <label>更新链接:</label>
        <input id="url" name="url" type="text" class="short-input validate[required]"/>
    </li>
    <li class="liplug line">
        <label>是否强制升级:</label>
        <div class="vocation">
            <select name="isForceUpdate" style="opacity:1">
                <option value="false">否</option>
                <option value="true">是</option>
            </select>
        </div>
    </li>
    <li class="liplug line">
        <label>升级说明:</label>
        <textarea name="description" id="description" cols="" rows="" style="width: 300px;overflow-y:auto"
                  class="textinput "></textarea>
        <%--<textarea id="description" name="description" autofocus></textarea>--%>
    </li>
    <li style="margin-left: 120px;" class="liplug">
        <label>&nbsp;</label>
        <input type="button" class="btn saveBtn" value="确认保存"/>
    </li>
</ul>
<script type="text/javascript" src="${path}/js/simditor/module.min.js"></script>
<script type="text/javascript" src="${path}/js/simditor/hotkeys.min.js"></script>
<script type="text/javascript" src="${path}/js/simditor/uploader.min.js"></script>
<script type="text/javascript" src="${path}/js/simditor/simditor.min.js"></script>
<script>
    $(function () {
        //show add
        $('._addBtn').on('click', function () {
            $('#addOrModify').find('input[type="text"],input[type="hidden"]').val("");
            $('#remark').val("");
            $.zxxbox($("#addOrModify"), {
                title: "新增APP更新参数"
            })
        })

        // 提交 表单
        $('.saveBtn').ajaxbtn("addOrModifyAppVersion.htm", function () {
            return {
                id: $('input[name="id"]').val(),
                appId: $('input[name="appId"]').val(),
                os: $('select[name="os"]').val(),
                lastVersion: $('input[name="lastVersion"]').val(),
                url: $('input[name="url"]').val(),
                isForceUpdate: $('select[name="isForceUpdate"]').val(),
                description: $('textarea[name="description"]').val(),
            }
        }, function () {
            return $('#addOrModify').validationEngine("validate", {validateNonVisibleFields: true});
        })
        // show modify
        $('._modifyBtn').on('click', function () {
            var id = $(this).parents('tr').attr("data");
            $('input[name="id"]').val(id);
            $K.http("changeAppVersion.htm", {
                appVersionId: id
            }, function (r) {
                var appVersion = r.result;
                $('input[name="appId"]').val(appVersion.appId);
                $('input[name="lastVersion"]').val(appVersion.lastVersion);
                $('input[name="url"]').val(appVersion.url);
                //$("#description").val(appVersion.description)
//                var sim = $(".simditor-body");
//                $(".simditor-body")[0].innerHTML = appVersion.description
                $('textarea[name="description"]').val(appVersion.description);
                $('select[name="os"]').val(appVersion.os);
                $('select[name="isForceUpdate"]').val(appVersion.isForceUpdate.toString())
                $.zxxbox($('#addOrModify'), {
                    title: "修改APP更新参数"
                })
            })
        })
        //remove
        $('._removeBtn').on('click', function () {
            var id = $(this).parents("tr").attr("data");
            $.zxxbox.ask("确定删除？", function () {
                $K.http("removeAppVersion.htm", {
                    appVersionId: id
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
//        var editor = new Simditor({
//            textarea: $('#description')
//            //optional options
//        });
//        $('.simditor-body').addClass("simditor-body2");
//        $('.simditor').addClass("simditor2");
//        $('.simditor').addClass("simditor2-left");
//        $('.simditor-wrapper').addClass("simditor2");
//        $('.simditor-toolbar').addClass("simditor-toolbar2");
//        var simditorul = $(".simditor-toolbar ul");
//        var simditorli = $(".simditor-toolbar ul li");
//        simditorul[0].removeChild(simditorli[8])
//        simditorul[0].removeChild(simditorli[9])
//        simditorul[0].removeChild(simditorli[10])
//        simditorul[0].removeChild(simditorli[11])

    })
</script>