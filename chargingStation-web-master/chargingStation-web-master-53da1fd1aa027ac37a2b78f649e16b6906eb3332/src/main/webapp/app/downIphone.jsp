<%@ taglib prefix="form" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common.jsp" %>
<html>
<head>
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta charset="UTF-8">
    <meta name="fragment" content="!">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="minimal-ui,width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="white">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${path}/css/dl.css">
</head>
<style>
    #android {
        display: none;
    }

    #ios {
        display: none;
    }
</style>
<body>
<span class="pattern left"><img src="${path}/images/download_pattern_left.png"></span>
<span class="pattern right"><img src="${path}/images/download_pattern_right.png"></span>
<div class="out-container">
    <div class="main">
        <header itemscope="" itemtype="http://schema.org/SoftwareApplication">
            <div class="table-container">
                <div class="cell-container">
                    <div class="app-brief">
                        <div class="icon-container wrapper">
                            <i class="icon-icon_path bg-path"></i>
                            <span class="icon">
                                <img src="${path}/images/hyd_256x256.png" itemprop="image">
                            </span>
                        </div>
                        <h1 class="name wrapper">
                            <span class="icon-warp">
                                <i class="icon-ios"></i>
                               好驿达
                            </span>
                        </h1>
                        <div id="ios">
                            <div class="release-info">
                                <p>版本: <span itemprop="softwareVersion">${ios.lastVersion}</span></p>

                                <p>更新于: <span itemprop="datePublished"><fmt:formatDate value='${ios.creationTime}'
                                                                                       pattern='yyyy-MM-dd'/></span></p>
                            </div>
                            <div class="actions type-ios">
                                <a href="${ios.url}" id="iosDownload">
                                    下载安装
                                </a>
                            </div>
                            <div class="release-info" style="padding-top: 10px;">
                                <div style="margin: 0 auto;width:60%;">
                                    <p style="text-align: left;font-size: 16px;color: black">更新说明:</p>
                                    <div style="margin: 0 auto;padding-left:5px;text-align: left;font-size: 10px">
                                        ${ios.description}
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div id="android">
                            <div class="release-info">
                                <p>版本: <span itemprop="softwareVersion">${android.lastVersion}</span></p>

                                <p>更新于: <span itemprop="datePublished"><fmt:formatDate value='${android.creationTime}'
                                                                                       pattern='yyyy-MM-dd'/></span></p>
                            </div>
                            <div class="actions type-ios">
                                <%--<a href="itms-services://?action=download-manifest&url=https://o4spfez37.qnssl.com/ipa_doctor_20160519.plist">--%>
                                <%--下载安装--%>
                                <%--</a>--%>
                                <a href="${android.url}" id="androidDownload">
                                    下载安装
                                </a>
                            </div>
                            <div class="release-info" style="padding-top: 10px;">
                                <div style="margin: 0 auto;width:60%;">
                                    <p style="text-align: left;font-size: 16px;color: black">更新说明:</p>
                                    <div style="margin: 0 auto;padding-left:5px;text-align: left;font-size: 10px">
                                        ${android.description}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    var systemBrowser = {
        Android: function () {
            return navigator.userAgent.match(/Android/i) ? true : false;
        },
        iOS: function () {
            return navigator.userAgent.match(/iPhone|iPad|iPod/i) ? true : false;
        },
        wx: function () {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        }
    };
    var ios = document.getElementById("ios");
    var android = document.getElementById("android");
    if (systemBrowser.Android()) {
        android.style.display = "block";
    }
    if (systemBrowser.iOS()) {
        ios.style.display = "block";
    }
    var androidDownload = document.getElementById("androidDownload");
    var iosDownload = document.getElementById("iosDownload");
    function loadHtml() {
        var div = document.createElement('div');
        div.id = 'weixin-tip';
        div.innerHTML = '<p><img src="${path}/images/live_weixin.png" alt="微信打开" style="width:100%"/></p>';
        document.body.appendChild(div);
    }

    function loadStyleText(cssText) {
        var style = document.createElement('style');
        style.rel = 'stylesheet';
        style.type = 'text/css';
        try {
            style.appendChild(document.createTextNode(cssText));
        } catch (e) {
            style.styleSheet.cssText = cssText; //ie9以下
        }
        var head = document.getElementsByTagName("head")[0]; //head标签之间加上style样式
        head.appendChild(style);
    }
    var cssText = "#weixin-tip{position: fixed; left:0; top:0; background: rgba(0,0,0,0.8); filter:alpha(opacity=90); width: 100%; height:100%; z-index: 100;} #weixin-tip p{text-align: center; margin-top: 10%; padding:0 5%;}";
    androidDownload.onclick = function (event) {
        if (systemBrowser.wx()) {
            loadHtml();
            loadStyleText(cssText);
            event.preventDefault();
        }
    }
    iosDownload.onclick = function (event) {
        if (systemBrowser.wx()) {
            loadHtml();
            loadStyleText(cssText);
            event.preventDefault();
        }
    }
</script>
