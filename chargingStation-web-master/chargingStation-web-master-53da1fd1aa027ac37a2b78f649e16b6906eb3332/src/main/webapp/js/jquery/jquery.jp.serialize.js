/**
 * jQuery serialize
 * depends jquery kiss
 *
 *  操作菜单
 */(function ($) {
    $.fn.extend({
        serialize: function () {
            var _this = $(this);
            var data = {};
            var texts = _this.find('input[type="text"],input[type="number"],input[type="radio"],input[type="hidden"],textarea');
            var selects = _this.find('select');
            var checkboxs = _this.find('input[type="checkbox"]:checked');
            var redioes = _this.find('input[type="radio"]');
            texts.each(function (i, t) {
                var text = $(t);
                if (!text.prop('name')) {
                    return true;
                }
                data[text.prop('name')] = text.val();
            });
            selects.each(function (i, s) {
                var select = $(s);
                if (!select.prop('name')) {
                    return true;
                }
                data[select.prop('name')] = select.val();
            });
            checkboxs.each(function (i, r) {
                var checkbox = $(r);
                if (!checkbox.prop('name')) {
                    return true;
                }
                var h = data[checkbox.prop('name')];
                var arr = [];
                if (h) {
                    arr = h.split(',');
                }
                arr.push(checkbox.val());
                data[checkbox.prop('name')] = arr.join(',');
            });
            redioes.each(function (i, r) {
                var redio = $(r);
                if (!redio.prop('name')) {
                    return true;
                }
                if(redio.is(':checked')){
                    data[redio.prop('name')] = redio.val();
                }
            });
            for (key in data) {
                if (data[key] instanceof Array) {
                    data[key] = data[key].join(',');
                }
            }
            return data;
        }
    });
})(jQuery);