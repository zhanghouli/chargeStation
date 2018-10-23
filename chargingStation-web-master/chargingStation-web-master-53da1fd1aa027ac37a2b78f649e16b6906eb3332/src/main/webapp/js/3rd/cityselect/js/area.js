function showLocation(option) {

    var loc = new Location(option.item);
    var title = ['省份', '地级市', '市、县、区'];
    $.each(title, function (k, v) {
        title[k] = '<option value="">' + v + '</option>';
    })
    $('select.loc_province').append(title[0]);
    $('select.loc_city').append(title[1]);
    $('select.loc_town').append(title[2]);
    $(".loc_province,.loc_city,.loc_town").uedSelect({
        width: 90
    });
    $('select.loc_province').change(function () {
        var parent = $(this).parents('li');
        var locCity = parent.find('select.loc_city');
        locCity.empty();
        locCity.append(title[1]);
        loc.fillOption(locCity, '0,' + $(this).val());
        locCity.change()
    })

    $('select.loc_city').change(function () {
        var parent = $(this).parents('li');
        var locTown = parent.find('select.loc_town');
        locTown.empty();
        locTown.append(title[2]);
        loc.fillOption(locTown, '0,' + parent.find('select.loc_province').val() + ',' + parent.find('select.loc_city').val());
        locTown.change();
    })

    //$('#loc_town').change(function () {
    //    $('input[@name=location_id]').val($(this).val());
    //})

    if (option.province) {
        loc.fillOption($('select.loc_province'), '0', option.province);
        if (option.city) {
            loc.fillOption($('select.loc_city'), '0,' + option.province, option.city);
            if (option.town) {
                loc.fillOption($('select.loc_town'), '0,' + option.province + ',' + option.city, option.town);
            }
        }
    } else {
        loc.fillOption($('select.loc_province'), '0');
    }

}
//
// $(function () {
//     $K.http('https://jbrain.zhiyoupei.com/api/region/getForWebCityPicker.htm?pk=nm0DvZnoQemEqwh9CbLTAjFI', {}, function (r) {
//         // $.zxxbox.hide();
//         showLocation({
//             item: r.result
//         });
//     });
// })