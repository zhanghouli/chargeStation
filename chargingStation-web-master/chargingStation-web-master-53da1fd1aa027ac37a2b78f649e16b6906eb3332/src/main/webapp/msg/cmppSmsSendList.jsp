<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">消息中心</a></li>
            <li><a href="#">发送列表</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <a href="#" class="_rechargeBtn" data-type="${PASSPORT_CAROWNER.value}">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>短信发送</li>
            </a>
        </ul>
    </div>
    <ul class="seachform">
        <form method="get" action="" id="searchForm">
            <%--<li>--%>
            <%--<label>发送状态</label>--%>
            <%--<div class="vocation">--%>
            <%--<select name="type">--%>
            <%--<option value="-1"   <c:if test="${ type == '-1'}">selected</c:if>>全部</option>--%>
            <%--<option value="1"  <c:if test="${type == '1'}">selected</c:if>>发送成功</option>--%>
            <%--<option value="0"  <c:if test="${type== '0'}">selected</c:if>>发送失败</option>--%>
            <%--</select>--%>
            <%--</div>--%>
            <%--</li>--%>
            <li><label>关键字</label>
                <input name="keyword" type="text" class="scinput" value="${keyword}"/>
            </li>
            <li>
                <label></label>
                <input name="" type="button" class="scbtn _searchBtn" value="查询" action="cmppSmsSendList.htm"/>
            </li>
        </form>
    </ul>

    <table class="tablelist">
        <thead>
        <tr>
            <th>手机号</th>
            <th>内容</th>
            <th>发送状态</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${list}">
            <tr data="${i.id}">
                <td>${i.phone}</td>
                <td><p class="mlellipsis" style="white-space: normal;text-overflow: ellipsis"
                       mlellipsis="1">${i.content}</p></td>
                <td>
                    <c:if test="${i.result == true}">
                        <font class="color-green">发送成功</font>
                    </c:if>
                    <c:if test="${i.result == false}">
                        <font class="color-red">发送失败</font>
                    </c:if>
                </td>
                <td><fmt:formatDate value='${i.creationTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td>
                    <a href="javascript:;" class="tablelink _removeBtn">删除</a>&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">${page}</div>
</div>
<%@include file="../footer.jsp" %>
<!--cmppSmsSend phone content -->
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="rechargeBox" style="width:400px">
    <li class="line">
        <label>手机号：</label><input name="carOwnerPhone" type="text"
                                  class="short-input validate[required]"/>
        <span class="tips _tips" style="width: 100%;text-align: center;"></span>
    </li>
    <li class="line _amount" style="margin-top: 20px;">
        <label>内容：</label>
        <textarea name="content" type="text" maxlength="2014" style="width:260px;height: 150px;overflow-x:auto"
                  class="short-input validate[required]]"></textarea>
    </li>
    <li class="line" style="text-align: center;">
        <input name="" type="button" class="btn _doRechargeBtn" value="确定"/>
    </li>
</ul>

<script>
    $(function () {
        //_searchBtn
        $('._searchBtn').on('click', function () {
            $('#searchForm').prop("action", $(this).attr("action")).submit();
        })

        $('._rechargeBtn').on('click', function () {
            $('#rechargeBox').find('input[type="text"]').val('');
            $('textarea[name="content"]').val('');
            $.zxxbox($('#rechargeBox'), {
                title: '短信发送'
            });
        });
        //消息 发送
        $('._doRechargeBtn').ajaxbtn("${path}/msg/addCmppSmsSend.htm", function () {
            return {
                phone: $.trim($('input[name="carOwnerPhone"]').val()),
                content: $.trim($('textarea[name="content"]').val())
            }
        }, function () {
            return $('#rechargeBox').validationEngine('validate', {validateNonVisibleFields: false});
            //&& !$K.tools.isBlank(toPassportId);
        }, function (r) {
            $.zxxbox.remind(r.message, null, {
                delay: 3000,
                onclose: function () {
                    location.reload();
                }
            });
        })
        $('._removeBtn').on('click', function () {
            var data = $(this).parents('tr').attr('data');
            $.zxxbox.ask('确定要删除?', function () {
                $K.http('removeCmppSmsSend.htm', {
                    cmppSmsSendId: data
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

    })
</script>