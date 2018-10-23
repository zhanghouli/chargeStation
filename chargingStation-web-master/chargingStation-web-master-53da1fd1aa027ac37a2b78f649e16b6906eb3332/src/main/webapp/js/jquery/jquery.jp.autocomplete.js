(function ($) {
    $.fn.extend({
        autoComplete: function (options) {
            var defaults = {
                ajax_url: true,
                onFocus: null,
                onSelected: null,
                param: ['number', 'name']
            }
            var options = $.extend(defaults, options);

            return this.each(function () {
                $(".h2").remove();
                var mythis = $(this);
                var timer = null;
                mythis.parent().on("click", ".select4_box li", function () {
                    $(this).parents('.select4_box').prev().val($(this).text()).attr('data-value', $(this).attr('data-value'));
                    $(".select4_box").remove();
                    if (options.onSelected) {
                        options.onSelected(JSON.parse($(this).attr('data-value')));
                    }
                });

                $(document).click(function (event) {
                    $(".select4_box").remove();
                });

                $(".select4_box").click(function (event) {
                    event.stopPropagation();
                });

                mythis.click(function (event) {
                    if (timer) {
                        clearTimeout(timer);
                        timer = null;
                    }
                    if (options.onFocus) {
                        options.onFocus();
                    }
                    var val = $(this).val();
                    if ($.trim(val) == '') {
                        return;
                    }
                    var mythis = $(this);
                    var width = $(this).width() + "px";
                    var top = $(this).position().top + 33;
                    var left = $(this).position().left;
                    timer = setTimeout(function () {
                        $.ajax({
                            url: options.ajax_url,
                            dataType: "json",
                            data: {keyword: val},
                            success: function (json) {
                                var list = json.result.list;
                                if (list && list.length > 0) {
                                    var html = '<div class="select4_box"><ul>';
                                    $.each(list, function (k, v) {
                                        html += '<li data-value=\'' + JSON.stringify(v) + '\'>' + v[options.param[0]] + '[' + v[options.param[1]] + ']</li>';
                                    });
                                    html += '</ul></div>';
                                    $(".select4_box").remove();
                                    mythis.after(html);
                                    //$(".select4_box").css({top: top, left: left, width: width});
                                    $(".select4_box").css({top: top, left: left});
                                } else {
                                    $(".select4_box").remove();
                                }
                            }
                        });
                    }, 500);
                });

                mythis.keyup(function (event) {
                    if (event.keyCode == 40) {
                        var index = $(".select4_box li.active").index() + 1;
                        $(".select4_box li").eq(index).addClass('active').siblings().removeClass('active');
                        mythis.val($(".select4_box li.active").text());
                    } else if (event.keyCode == 38) {
                        var index = $(".select4_box li.active").index() - 1;
                        if (index < 0) {
                            index = $(".select4_box li").length - 1;
                        }
                        $(".select4_box li").eq(index).addClass('active').siblings().removeClass('active');
                        mythis.val($(".select4_box li.active").text());
                    } else if (event.keyCode == 13) {
                        event.stopPropagation();
                        mythis.val($(".select4_box li.active").text());
                        return false;
                    } else {
                        mythis.trigger("click");
                    }
                });

            });


        }
    });
})(jQuery);