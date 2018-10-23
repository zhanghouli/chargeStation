<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../header.jsp" %>
<link rel="stylesheet" type="text/css" href="${path}/js/3rd/jstree/themes/default/style.min.css"/>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css"/>
<div class="top" style="min-width: 1450px;">
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">位置监控</a></li>
            <li><a href="#">电子围栏</a></li>
        </ul>
    </div>
    <%@include file="../top.jsp" %>
</div>
<div class="tools" style="height:1px"></div>
<ul class="seachform">
    <form method="get" id="searchForm" action="fenceList.htm">
        <input type="hidden" name="governmentId" value="${fence.governmentId}">
        <li><label>电子围栏名称:</label>
            <input name="name" type="text" class="scinput" value="${fence.name}"/>
        </li>
        <li>
            <input name="" type="button" class="scbtn _searchBtn" value="查询"/>
        </li>
    </form>
</ul>
<!--电子围栏-->
<div class="rightinfo">

    <div class="formbody" style="float: left;width: 20%;">
        <div class="formtitle"><span>电子围栏</span></div>
        <ul class="toolbar">
            <a href="javascript:;" class="_addBtn">
                <li class="click"><span><img src="${path}/images/t01.png"/></span>添加</li>
            </a>
        </ul>
        <br/>

        <div class="clr"></div>
        <br/>

        <div id="jstree"></div>
    </div>
    <div class="formbody _form" style="float: left;width: 70%;display: none">
        <ul class="forminfo validationEngineContainer _editBox">
            <li class="line">
                <label>围栏名称<b>*</b></label>
                <input name="nameEdit" type="text" class="dfinput validate[required]"/>
                <input name="" type="button" class="btn _doEditBtn" value="确认修改"/>
                <input name="" type="button" style="margin-left: 50px;" class="btn _doDelBtn" value="删除"/>
            </li>
            <li class="line">
                <label>地址搜索</label>
                <input name="address" id="address" type="text" style="width: 337px;" class="dfinput _address"
                       placeholder="输入您想要找的地址"/>
                <input name="" type="button" class="btn _search" value="搜索"/>
            </li>
        </ul>
        <div class="pull-down _addressDiv" style="display: none">
            <ul>
            </ul>
        </div>
        <div class="formtitle"><span>地图</span></div>
        <div id="allmap" style="width:70%;height:65%;z-index: 1;"></div>
    </div>
</div>
<%--add/modify--%>
<ul class="forminfo zxxbox_contaner validationEngineContainer" id="addOrModifyBox" style="width:400px">
    <li><label>电子围栏名称</label><input name="fenceName" type="text" class="short-input validate[required]"/></li>
    <li style="text-align: center;">
        <input name="" type="button" class="btn _doAddOrModifyBtn" value="确定"/>
    </li>
</ul>
<%@include file="../footer.jsp" %>
<script type="text/javascript" src="${path}/js/3rd/jstree/jstree.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5ylayfeKso2O4RaQep2K8AWC183X2QM6"></script>
<script type="text/javascript"
        src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<script>
    $(function () {
        var polygons = [];
        var fence = null;
        var map = new JPMap();
        var _addressDiv = $('._addressDiv');
        map.initMap("allmap");
        map.initDrawingManager();
        //tree
        $('#jstree').on("changed.jstree", function (e, data) {
            var f = data.instance.get_node(data.selected[0]);
//            if (fence && fence.id == f.id) {
//                return;
//            }
            fence = f;
            var governmentId = fence.original.governmentId;
            $('input[name="nameEdit"]').val(fence.text);
            $('input[name="address"]').val("");
            if (!$('._form').is(':visible')) {
                $('._form').show();
            }
            try {
                polygons = JSON.parse(fence.original.fencePoints);
            } catch (e) {
                polygons == []
            }
            map.removeAllOverlay();
            map.addPolygon(polygons, true);
        }).on("loaded.jstree", function (event, data) {
            data.instance.open_all();
        }).jstree({
            'plugins': ["dnd", "types", "wholerow"],
            'core': {
                "check_callback": true,//在对树进行改变时，check_callback是必须设置为true；
                <c:if test="${fence.name ==null || fence.name ==''}">
                'data': {
                    "url": "./getCategoryTree.htm?governmentId=${fence.governmentId}",
                    "dataType": "json"
                }
                </c:if>
                <c:if test="${fence.name !=null && fence.name !=''}">
                'data': {
                    "url": "./getCategoryTree.htm?governmentId=${fence.governmentId}&name=${fence.name}",
                    "dataType": "json"
                }
                </c:if>
            },
            //types-对树的节点进行设置，包括子节点type、个数
            'types': {
                "#": {
                    "max_children": 0
                }
            }
        });
        //add or modify
        $('._addBtn').on('click', function () {
            $('#addOrModifyBox').find('input[type="text"]').val('');
            $('.scinput').val('');
            $.zxxbox($('#addOrModifyBox'), {
                title: '新增电子围栏'
            });
        });
        <!--新增-->
        $('._doAddOrModifyBtn').ajaxbtn('${path}/fence/addOrModifyFence.htm', function () {
            initPoints();
            return {
                governmentId: $.trim($('input[name="governmentId"]').val()),
                name: $.trim($('input[name="fenceName"]').val()),
                fencePoints: JSON.stringify(polygons)
            }

        }, function () {
            return $('#addOrModifyBox').validationEngine('validate', {validateNonVisibleFields: true});
        });
        <!--修改-->
        $('._doEditBtn').ajaxbtn('${path}/fence/addOrModifyFence.htm', function () {
            initPoints();
            return {
                id: fence.id,
                name: $.trim($('input[name="nameEdit"]').val()),
                fencePoints: JSON.stringify(polygons)
            }
        }, function () {
            return $('._editBox').validationEngine('validate', {validateNonVisibleFields: true});
        })
        //删除
        $('._doDelBtn').on('click', function () {
            $.zxxbox.ask('确定要删除?', function () {
                $K.http('${path}/fence/doRemoveId.htm', {
                    id: fence.id
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
        <!--搜索-->
        $('._searchBtn').on('click', function () {
            $('#searchForm').submit();
        })
        <!--地址搜索-->
        $('._search').on('click', function () {
            map.moveToByCity($.trim($('input[name="address"]').val()))
        });
        <!--下拉框-->
        $('._address').bind('input propertychange', function () {
            var _this = this;
            map.search(_this.value, searchHandler);
            _addressDiv.css({
                "width": this.offsetWidth,
                "top": this.offsetTop + this.offsetHeight,
                "left": this.offsetLeft,
                "display": "block"
            })
            str = _this.value;
        });
        //搜索框 上下选择事件
        var index, inAll, str, keyCode;
        $(document).keyup(function (event) {
            if (document.getElementById("address") == document.activeElement) {
                keyCode = event.keyCode;
                if (keyCode == 40 || keyCode == 38) {
                    selectLi(keyCode);
                }
            }
            return;
        });
        // li 内容筛选
        function selectLi(keyCode) {
            inAll = _addressDiv.find("ul li").length;
            if (inAll <= 0) {
                return;
            }
            for (var i = 0; i < inAll; i++) {
                var li = _addressDiv.find("ul li")[i];
                if (li.className.indexOf("sug-s") != -1) {
                    if (keyCode == 40) {
                        index = i + 1;
                    } else if (keyCode == 38) {
                        index = i - 1;
                    }
                    break;
                }
                if (keyCode == 40) {
                    index = 0;
                } else if (keyCode == 38) {
                    index = inAll - 1;
                }
            }
            //向下走
            if (keyCode == 40) {
                //如果 变量  大于
                if (index >= inAll) {
                    _addressDiv.find("ul li")[index - 1].classList.remove("sug-s");
                    index = 0;
                    $('input[name="address"]').val(str);
                    return;
                }
                var li = _addressDiv.find("ul li")[index];
                li.classList.add("sug-s");
                //其他 同类 改变状态
                for (var i = 0; i < inAll; i++) {
                    if (index != i) {
                        _addressDiv.find("ul li")[i].classList.remove("sug-s");
                    }
                    map.moveToByCity(li.innerHTML);
                    $('input[name="address"]').val(li.innerHTML);
                }
                index++;
            } else if (keyCode == 38) {
                if (index < 0) {
                    _addressDiv.find("ul li")[0].classList.remove("sug-s");
                    index = 0;
                    $('input[name="address"]').val(str);
                    return;
                }
                var li = _addressDiv.find("ul li")[index];
                li.classList.add("sug-s");
                for (var i = inAll - 1; i >= 0; i--) {
                    if (index != i) {
                        _addressDiv.find("ul li")[i].classList.remove("sug-s");
                    }
                    map.moveToByCity(li.innerHTML);
                    $('input[name="address"]').val(li.innerHTML);
                }
                index--;
            }
        }

        //失去焦点触发 事件
        var canBlur = true;
        $('input[name="address"]').blur(function () {
            if (canBlur) {
                _addressDiv.hide();
            }
        })
        //窗口改变 触发事件
        window.onresize = function () {
            _addressDiv.hide();
        }
        //
        function initPoints() {
            polygons = [];
            var overlays = map.map.getOverlays();
            $.each(overlays, function (i, overlay) {
                if ('[object Polygon]' == overlay.toString()) {
                    var points = [];
                    var path = overlay.getPath();
                    for (var i = 0; i < path.length; i++) {
                        points.push({
                            lng: path[i].lng,
                            lat: path[i].lat,
                        });
                    }
                    polygons.push(points);
                }
            })
        }

        function searchHandler(poies) {
            _addressDiv.find("ul").empty();
            if (poies.length <= 0) {
                _addressDiv.hide();
                return;
            }
            for (var index = 0; index < poies.length; index++) {
                var poi = poies[index];
                if (BMAP_POI_TYPE_NORMAL != poi.type) {
                    continue;
                }
                _addressDiv.find("ul").append('<li class="bar-li" data="' + index + '">[' + poi.title + ']' + poi.address + '</li>');
            }
            $(".bar-li").on("click", function (event) {
                $('input[name="address"]').val(event.target.innerText);
                map.moveToByCity(event.target.innerText);
                _addressDiv.hide();
            })
            for (var i = 0; i < _addressDiv.find("ul li").length; i++) {
                (function (i) {
                    var li = _addressDiv.find("ul li")[i];
                    li.onclick = function () {
                        $('input[name="address"]').val(li.innerHTML);
                        map.moveToByCity($.trim($('input[name="address"]').val()))
                        canBlur = true;
                        _addressDiv.hide();
                    };
                    li.onmouseover = function () {
                        canBlur = false;
                        li.classList.add("sug-s");
                        for (var n = 0; n < _addressDiv.find("ul li").length; n++) {
                            if (i != n) {
                                _addressDiv.find("ul li")[n].classList.remove("sug-s");
                            }
                        }
                    };
                    li.onmouseout = function () {
                        canBlur = true;
                        li.classList.remove("sug-s");
                    };
                })(i);
            }
        }
    })

</script>