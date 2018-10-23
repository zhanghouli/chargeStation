$(function () {
    window.$K = {
        //ajax简单封装
        http: function (url, data, success, fail) {
            //$.zxxbox.loading();
            $.post(url, data, function (r) {
                if (r.code != 1) {
                    if (r.message) {
                        $.zxxbox.remind(r.message);
                    } else {
                        $.zxxbox.remind('登录超时，请重新登录', function () {
                            top.location.href = location.href;
                        });
                    }
                    if (fail) {
                        fail(r);
                    }
                    return;
                }
                if (success) {
                    success(r);
                }
            }).error(function (r) {
                $.zxxbox.remind("操作失败。", null, {delay: 1000});
                if (fail) {
                    fail(r);
                }
            });
        },
        tools: {
            // 是否为空
            isBlank: function (val) {
                if (!val) {
                    return true;
                }
                var re = /^\s*$/g;
                return re.test(val);
            },
            timeFormat: function (time, format) {
                Date.prototype.format = function (format) {
                    var o = {
                        "M+": this.getMonth() + 1, // month
                        "d+": this.getDate(), // day
                        "h+": this.getHours(), // hour
                        "m+": this.getMinutes(), // minute
                        "s+": this.getSeconds(), // second
                        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
                        "S": this.getMilliseconds()
                    };
                    if (/(y+)/.test(format)) {
                        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
                    }
                    for (var k in o)
                        if (new RegExp("(" + k + ")").test(format)) {
                            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                        }
                    return format;
                };
                if (typeof time == 'number') {
                    time = new Date(time);
                }
                var _time = time || new Date();
                var datatime = _time.format(format || 'yyyy-MM-dd hh:mm:ss');
                return datatime;
            }
        },
        createId: function () {
            // UUID
            function UUID() {
                this.id = this.createUUID()
            }

            UUID.prototype.valueOf = function () {
                return this.id
            };
            UUID.prototype.toString = function () {
                return this.id
            };
            UUID.prototype.createUUID = function () {
                var dg = new Date(1582, 10, 15, 0, 0, 0, 0);
                var dc = new Date();
                var t = dc.getTime() - dg.getTime();
                var tl = UUID.getIntegerBits(t, 0, 31);
                var tm = UUID.getIntegerBits(t, 32, 47);
                var thv = UUID.getIntegerBits(t, 48, 59) + '1';
                var csar = UUID.getIntegerBits(UUID.rand(4095), 0, 7);
                var csl = UUID.getIntegerBits(UUID.rand(4095), 0, 7);
                var n = UUID.getIntegerBits(UUID.rand(8191), 0, 7) + UUID.getIntegerBits(UUID.rand(8191), 8, 15)
                    + UUID.getIntegerBits(UUID.rand(8191), 0, 7) + UUID.getIntegerBits(UUID.rand(8191), 8, 15)
                    + UUID.getIntegerBits(UUID.rand(8191), 0, 15);
                return tl + tm + thv + csar + csl + n
            };
            UUID.getIntegerBits = function (val, start, end) {
                var base16 = UUID.returnBase(val, 16);
                var quadArray = new Array();
                var quadString = '';
                var i = 0;
                for (i = 0; i < base16.length; i++) {
                    quadArray.push(base16.substring(i, i + 1))
                }
                for (i = Math.floor(start / 4); i <= Math.floor(end / 4); i++) {
                    if (!quadArray[i] || quadArray[i] == '') {
                        quadString += '0';
                    } else {
                        quadString += quadArray[i]
                    }
                }
                return quadString
            };
            UUID.returnBase = function (number, base) {
                return (number).toString(base).toUpperCase()
            };
            UUID.rand = function (max) {
                return Math.floor(Math.random() * (max + 1))
            };
            return UUID.prototype.createUUID().toLowerCase();
        }
    }
});