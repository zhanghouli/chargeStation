<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">模版配置</a></li>
            <li><a href="#">配置列表</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <%--<div class="tools">--%>
    <%--<ul class="toolbar">--%>
    <%--<input name="" type="button" class="scbtn _rechargeBtn" data-type="${PASSPORT_CAROWNER.value}"--%>
    <%--value="给车主充值"/>--%>
    <%--</ul>--%>
    <%--</div>--%>
    <div class="tools">
        <ul class="toolbar">
            <li class="click _rechargeBtn"><span><img src="${path}/images/t01.png"/></span>模版配置</li>
        </ul>
    </div>
    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <li>
                <label>模版类型</label>
                <div class="vocation" style="width:200px;">
                    <select name="type">
                        <c:forEach var="s" items="${MESSAGE}">
                            <option value="${s.value}"
                                    <c:if test="${s.value == type}">selected</c:if> >${s.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <%--<li><label>关键字</label>--%>
            <%--<input name="keyword" type="text" class="scinput" value="${keyword}"/>--%>
            <%--</li>--%>
            <%--<li><label>起始时间</label>--%>
            <%--<input name="timeStartStr" type="text" class="scinput Wdate" value="${timeStartStr}"--%>
            <%--onfocus="WdatePicker()"/>--%>
            <%--</li>--%>
            <%--<li><label>结束时间</label>--%>
            <%--<input name="timeEndStr" type="text" class="scinput Wdate" value="${timeEndStr}"--%>
            <%--onfocus="WdatePicker()"/>--%>
            <%--</li>--%>
            <li>
            <input name="" type="button" class="scbtn _searchBtn" style="margin-left: 50px;" value="查询" action="wxMessageList.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <th>模版名称</th>
            <th>模版ID</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data-id="${i.id}">
                    <%--<td>暂无图片</td>--%>
                <td>${i.type}</td>
                <td>${i.templateId}</td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td><a href="javascript:;" class="tablelink _modifyBtn">修改</a>&nbsp;
                    <a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">${page}</div>
</div>
<%@include file="../footer.jsp" %>
<!--carOwner account-->
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="rechargeBox" style="width:400px">
    <input type="hidden" name="messageId" value="">
    <li class="line">
        <label>模版类型</label>
        <select name="messageType" style="opacity:1" class="validate[required]">
            <c:forEach var="s" items="${MESSAGE}">
                <option value="${s.value}">${s.name}</option>
            </c:forEach>
        </select>
    </li>
    <li class="line _amount" style="margin-top: 20px;">
        <label>模版id：</label>
        <input name="templateId" type="text" class="short-input validate[required]"/>
    </li>
    <li class="line" style="text-align: center;">
        <input name="" type="button" class="btn _doRechargeBtn" value="确定"/>
    </li>
</ul>

<script>
    $(function () {
        $(".uew-select-value").css({
            width: "200px"
        })
        //_searchBtn
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })
        $('._rechargeBtn').on('click', function () {
            $('#rechargeBox').find('input[type="text"]').val('');
            $('input[name="messageId"]').val("");
            $('select[name="messageType"]').val("");
            $.zxxbox($('#rechargeBox'), {
                title: '模版新增'
            });
        });
        //模版 id 添加
        $('._doRechargeBtn').ajaxbtn("${path}/message/addOrModifyMessage.htm", function () {
            return {
                id: $.trim($('input[name="messageId"]').val()),
                type: $.trim($('select[name="messageType"]').val()),
                templateId: $.trim($('input[name="templateId"]').val())
            }
        }, function () {
            return $('#rechargeBox').validationEngine('validate', {validateNonVisibleFields: false});
        })

        //修改 
        $('._modifyBtn').on('click', function () {
            var messageId = $(this).parents('tr').attr('data-id');
            $K.http("${path}/message/getMessageId.htm",{
                messageId:messageId
            },function (r) {
               var message=r.result;
                $('input[name="messageId"]').val(message.id);
                $('input[name="templateId"]').val(message.templateId);
                $('select[name="messageType"]').val(message.type);

            })
            $.zxxbox($('#rechargeBox'), {
                title: '模版修改'
            });
        });

        //remove
        $('._removeBtn').on('click', function () {
            var messageId = $(this).parents('tr').attr('data-id');
            $.zxxbox.ask("确定删除？谨慎操作", function () {
                $K.http("${path}/message/removeMessageId.htm", {
                    messageId: messageId
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