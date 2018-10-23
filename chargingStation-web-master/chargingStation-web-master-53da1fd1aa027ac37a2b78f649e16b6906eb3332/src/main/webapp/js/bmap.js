(function (win) {
    function JPMap() {
        var that = this;
        this.map = null;
        this.marks = {};
        this.polylines = {};
        /**
         * 初始化题图
         */
        this.initMap = function (domId) {
            var map = new BMap.Map(domId, {enableMapClick: false});
            var myCity = new BMap.LocalCity();
            myCity.get(function (result) {
                var cityName = result.name;
                map.centerAndZoom(cityName, 15);
            });
            map.centerAndZoom("杭州", 15);
            map.enableScrollWheelZoom();
            map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT}));
            this.map = map;
        }
        /**
         * 根据point移动
         * @param city
         */
        this.panTo = function (point) {
            this.map.panTo(point);
        };
        /**
         * 根据城市名字移动
         * @param city
         */
        this.moveToByCity = function (city) {
            this.map.centerAndZoom(city, 15);
        };
        /**
         * 移除所有覆盖物
         */
        this.removeAllOverlay = function () {
            this.map.clearOverlays();
            this.marks = {};
            this.polylines = {};
        };
        /**
         * 加标注
         */
        this.addMark = function (position, handler) {
            var that = this;
            this.removeAllOverlay();
            var point = new BMap.Point(position.lng, position.lat);
            if (this.marks[position.id]) {
                this.marks[position.id].setPosition(point);
            } else {
                var marker = new BMap.Marker(point);
                //
                var label = new BMap.Label('拖动图标来微调位置', {offset: new BMap.Size(20, 0)});
                marker.setLabel(label);
                marker.enableDragging();
                if (handler && handler.dragendHandler) {
                    marker.addEventListener("dragend", function () {
                        handler.dragendHandler(marker.point);
                    });
                }
                //
                this.map.addOverlay(marker);
                this.marks[position.id] = marker;
            }
            setTimeout(function () {
                that.map.panTo(point);
            }, 200);
        }
        /**
         * 加车辆标注
         */
        this.addCarMark = function (position, handler) {
            var that = this;
            var point = new BMap.Point(position.lng, position.lat);
            var marker = new BMap.Marker(point);
            //
            var label = new BMap.Label(position.number, {offset: new BMap.Size(-20, -20)});
            marker.setLabel(label);
            //
            var icon = new BMap.Icon(position.in ? '../images/car_ok.png' : '../images/car_fail.png', new BMap.Size(34, 24), {
                imageSize: new BMap.Size(34, 24)
            });
            marker.setIcon(icon);
            //
            this.map.addOverlay(marker);
            this.marks[position.id] = marker;
        }
        /**
         * 移除标注
         * @param id
         */
        this.removeMark = function (id) {
            this.map.removeOverlay(this.marks[id]);
            delete this.marks[id];
        }
        /**
         * 画轨迹
         */
        this.addPolyline = function (id, positions) {
            if (this.polylines[id]) {
                return;
            }
            var points = [];
            var marks = [];
            $.each(positions, function (i, position) {
                var point = new BMap.Point(position.lng, position.lat);
                points.push(point);
                var marker = new BMap.Marker(point);
                marker.addEventListener("click", function () {
                    return;
                });
                that.map.addOverlay(marker);
                marks.push(marker);
            });
            var polyline = new BMap.Polyline(points, {
                strokeColor: '#' + ('00000' + (Math.random() * 0x1000000 << 0).toString(16)).slice(-6),
                strokeWeight: 2,
                strokeOpacity: 0.5
            });
            this.map.addOverlay(polyline);
            that.map.setViewport(polyline.getPath());
            this.polylines[id] = {
                polyline: polyline,
                marks: marks
            };
        }
        /**
         * 移除轨迹
         * @param id
         */
        this.removePolyline = function (id) {
            if (!this.polylines[id]) {
                return;
            }
            this.map.removeOverlay(this.polylines[id].polyline);
            $.each(this.polylines[id].marks, function (i, mark) {
                that.map.removeOverlay(mark);
            });
            delete this.polylines[id];
        }
        /**
         * 行政区划
         * @param bound
         */
        this.bound = function (bound) {
            function getBoundary() {
                var bdary = new BMap.Boundary();
                bdary.get(bound, function (rs) {
                    var count = rs.boundaries.length;

                    for (var i = 0; i < count; i++) {
                        var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000"});
                        that.map.addOverlay(ply);
                        that.map.setViewport(ply.getPath());
                    }
                });
            }

            setTimeout(function () {
                getBoundary();
            });
        }
        /**
         * 关键词搜索
         * @param keyword
         * @param callback 参数LocalResultPoi数组
         */
        this.search = function (keyword, callback) {
            var local = new BMap.LocalSearch(this.map, {
                onSearchComplete: function (result) {
                    if (local.getStatus() == BMAP_STATUS_SUCCESS) {
                        var poies = [];
                        for (var i = 0; i < result.getCurrentNumPois(); i++) {
                            poies.push(result.getPoi(i));
                        }
                        if (callback) {
                            callback(poies);
                        }
                    } else {
                        if (callback) {
                            callback([]);
                        }
                    }
                }
            });
            local.enableAutoViewport();
            local.search(keyword);
        }
        /**
         * 绘制多边形
         * @param polygons
         */
        this.addPolygon = function (polygons, canEdit) {
            var views = [];
            var styleOptions = {
                strokeColor: "red",    //边线颜色。
                fillColor: "red",      //填充颜色。当参数为空时，圆形将没有填充效果。
                strokeWeight: 3,       //边线的宽度，以像素为单位。
                strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
                fillOpacity: 0.2,      //填充的透明度，取值范围0 - 1。
                strokeStyle: 'solid' //边线的样式，solid或dashed。
            };
            $.each(polygons, function (i, path) {
                var polygon = new BMap.Polygon(path, styleOptions);  //创建多边形
                that.map.addOverlay(polygon);   //增加多边形
                if (canEdit) {
                    polygon.enableEditing();
                    that.bindRemove(polygon);
                }
                views = views.concat(polygon.getPath());
            })
            setTimeout(function () {
                that.map.setViewport(views);
            }, 1000)
        }
        /**
         * 绘制工具
         * @param callback
         */
        this.initDrawingManager = function (callback) {
            var styleOptions = {
                strokeColor: "red",    //边线颜色。
                fillColor: "red",      //填充颜色。当参数为空时，圆形将没有填充效果。
                strokeWeight: 3,       //边线的宽度，以像素为单位。
                strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
                fillOpacity: 0.2,      //填充的透明度，取值范围0 - 1。
                strokeStyle: 'solid' //边线的样式，solid或dashed。
            }
            //实例化鼠标绘制工具
            var drawingManager = new BMapLib.DrawingManager(this.map, {
                isOpen: false, //是否开启绘制模式
                enableDrawingTool: true, //是否显示工具栏
                drawingToolOptions: {
                    anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
                    offset: new BMap.Size(5, 5), //偏离值
                    scale: 0.7,
                    drawingModes: [BMAP_DRAWING_POLYGON]
                },
                enableCalculate: false,//绘制是否进行测距(画线时候)、测面(画圆、多边形、矩形)
                polygonOptions: styleOptions, //多边形的样式
            });
            drawingManager.addEventListener('polygoncomplete', function (polygon) {
                polygon.enableEditing();
                that.bindRemove(polygon);
                callback && callback(polygon);
            });
        }
        /**
         * 绑定删除
         * @param overlay
         */
        this.bindRemove = function (overlay) {
            var removeMarker = function (e, ee, ol) {
                that.map.removeOverlay(ol);
            }
            var markerMenu = new BMap.ContextMenu();
            markerMenu.addItem(new BMap.MenuItem('删除', removeMarker.bind(overlay)));
            overlay.addContextMenu(markerMenu);
        }
    }

    win.JPMap = JPMap;
})(window)