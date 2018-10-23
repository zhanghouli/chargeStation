function showLocation(option) {

    var loc = new Location(option.item);
    var title = ['省份', '地级市', '市、县、区'];
    $.each(title , function(k , v) {
        title[k]	= '<option value="">'+v+'</option>';
    })

    $('#loc_province').append(title[0]);
    $('#loc_city').append(title[1]);
    $('#loc_town').append(title[2]);

    $("#loc_province,#loc_city,#loc_town").select2()
    $('#loc_province').change(function() {
        $('#loc_city').empty();
        $('#loc_city').append(title[1]);
        loc.fillOption('loc_city' , '0,'+$('#loc_province').val());
        $('#loc_city').change()
    })

    $('#loc_city').change(function() {
        $('#loc_town').empty();
        $('#loc_town').append(title[2]);
        loc.fillOption('loc_town' , '0,' + $('#loc_province').val() + ',' + $('#loc_city').val());
    })

    // $('#loc_town').change(function() {
    //     $('input[@name=location_id]').val($(this).val());
    // })

    if (option.province) {
        loc.fillOption('loc_province' , '0' , option.province);

        if (option.city) {
            loc.fillOption('loc_city' , '0,'+option.province , option.city);

            if (option.town) {
                loc.fillOption('loc_town' , '0,'+option.province+','+option.city , option.town);
            }
        }

    } else {
        loc.fillOption('loc_province' , '0');
    }


}
//
// $(function () {
//     $K.http('https://jbrain.zhiyoupei.com/api/region/getForWebCityPicker.htm?pk=nm0DvZnoQemEqwh9CbLTAjFI', {}, function (r) {
//         $.zxxbox.hide();
//         showLocation({
//             item: r.result
//         });
//     });
// })

$(function(){
    $('#btnval').click(function(){
        alert($('#loc_province').val() + ' - ' + $('#loc_city').val() + ' - ' +  $('#loc_town').val())
    })
    $('#btntext').click(function(){
        alert($('#loc_province').select2('data').text + ' - ' + $('#loc_city').select2('data').text + ' - ' +  $('#loc_town').select2('data').text)
    })
})