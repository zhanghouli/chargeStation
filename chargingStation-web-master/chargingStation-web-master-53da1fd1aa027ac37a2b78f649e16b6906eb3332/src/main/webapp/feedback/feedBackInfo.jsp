<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<div class="top">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="${path}/feedback/feedBackList.htm">意见反馈</a></li>
            <li><a href="#">反馈详情</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="formbody">
    <div class="formtitle"><span>反馈详情</span></div>
    <ul class="forminfo short validationEngineContainer">
        <input name="id" type="hidden" value="${feedback.id}"/>
        <%--<li class="line">--%>
            <%--<label>反馈标题:</label>--%>
            <%--<input readonly="readonly" name="title" type="text" value="${feedback.title}"/>--%>
        <%--</li>--%>
        <li class="line">
            <label>反馈内容:</label><textarea cols="" rows="" class="textinput"
                                          readonly="readonly">${feedback.content}</textarea>
        </li>
        <li class="line">
            <label>反馈图片:</label>
            <c:forEach items="${pics}" var="picture">
                <div class="words_down_pic">
                    <c:choose>
                        <c:when test="${ISDEV}">
                            <a rel="${i.id}" href="${path}${files}${picture}" class="fancy-box">
                                <img class="lazy _avatar"
                                     data-original="${path}${files}${picture==null?'../images/default-image.png':picture}"
                                     height="80px" width="70px"/>
                            </a>
                        </c:when>
                        <c:when test="${!ISDEV}">
                            <a rel="${i.id}" href="${path}${picture}" class="fancy-box">
                                <img class="lazy _avatar"
                                     data-original="${path}${picture==null?'../images/default-image.png':picture}"
                                     height="80px" width="70px"/>
                            </a>
                        </c:when>
                    </c:choose>
                </div>
            </c:forEach>
        </li>
        <li class="line">
            <label>&nbsp;</label>
            <div class="upload-banner-img" style="float:left;">
                <div style="height: 30px;">
                </div>
                <div style="height: 30px;width:525px;border-top: 1px dashed #D6D6D6">
                </div>
            </div>
        </li>

        <li class="line">
            <label>处理结果:</label>
            <textarea name="bizDealResult" cols="" rows=""
                      class="textinput validate[required]">${feedback.bizDealResult}</textarea>
        </li>
        <li style="margin-left: 120px;">
            <label>&nbsp;</label>
            <input type="button" class="btn saveBtn" value="提交处理结果"/>
        </li>
    </ul>
</div>
<%@include file="../footer.jsp" %>
<script>
    $(function () {
        $('.saveBtn').ajaxbtn('modifyFeedback.htm', function () {
            return $('.forminfo').serialize();
        }, function () {
            return $('.forminfo').validationEngine('validate');
        })
    })
</script>