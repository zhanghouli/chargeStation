</body>
</html>
<c:choose>
    <c:when test="${ISDEV}">
        <script type="text/javascript" src="${path}/js/jquery/jquery.1.11.2.min.js"></script>
        <script type="text/javascript" src="${path}/js/jquery/jquery.zxxbox.3.0.js"></script>
        <script type="text/javascript" src="${path}/js/jquery/jquery.jp.ajaxbtn.js"></script>
        <script type="text/javascript" src="${path}/js/jquery/jquery.jp.autocomplete.js"></script>
        <script type="text/javascript" src="${path}/js/jquery/jquery-ui.min.js"></script>
        <script type="text/javascript" src="${path}/js/jquery/jquery.jp.serialize.js"></script>
        <script type="text/javascript" src="${path}/js/jquery/jquery.lazyload.js"></script>
        <script type="text/javascript" src="${path}/js/jquery/jquery.idTabs.min.js"></script>
        <script type="text/javascript" src="${path}/js/jquery/jquery.circliful.min.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/validate/jquery.validationEngine.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/validate/jquery.validationEngine-zh_CN.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/webuploader/webuploader.min.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/jstree/jstree.min.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/select/select-ui.min.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/Jcrop/js/jquery.Jcrop.min.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/select2/js/select2.min.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/select2/js/i18n/zh-CN.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/mlellipsis/mlellipsis.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="${path}/js/json2.js"></script>
        <script type="text/javascript" src="${path}/js/kiss-web.1.0.js"></script>
        <script type="text/javascript" src="${path}/js/bmap.js"></script>
    </c:when>
    <c:when test="${!ISDEV}">
        <script type="text/javascript" src="${path}/wro/basic.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/mlellipsis/mlellipsis.js"></script>
        <script type="text/javascript" src="${path}/js/3rd/My97DatePicker/WdatePicker.js"></script>
    </c:when>
</c:choose>
<script>
    $(function () {
        //
        <c:if test="${result!=null && result.code!=SUCCESS}">
        $.zxxbox.remind('${result.message}');
        </c:if>
        //select
        $(".seachform select").uedSelect({
            width: 150
        });
        $(".short select.useselect").uedSelect({
            width: 200
        });
        //
        $(".itab ul").idTabs();
        //back
        $('._back').on('click', function () {
            history.go(-1);
        });
        //odd
        $('.tablelist tbody tr:odd').addClass('odd');
        $('.imgtable tbody tr:odd').addClass('odd');
        $('.imgtable tbody tr:odd').addClass('odd');
        //fancybox
        $(".fancy-box").fancybox();
        //lazyload
        $("img.lazy").lazyload({effect: "fadeIn", placeholder: "${path}/images/default-image.png", threshold: 200});
        //default img
        $('img.use-default').each(function () {
            var that = this;
            this.error = function () {
                that.src = '${path}/images/default-image.png';
            }
        });
        //
        $('._close').on('click', function () {
            $.zxxbox.hide();
        })
        //
        $('.mlellipsis').each(function (i, dom) {
            var div = $(dom);
            var line = div.attr('mlellipsis') || 3;
            div[0].mlellipsis(line);
        });
    })
</script>