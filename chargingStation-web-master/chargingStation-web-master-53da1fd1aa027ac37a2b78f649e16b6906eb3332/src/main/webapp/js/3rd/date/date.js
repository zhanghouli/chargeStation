var now = new Date();
var nowTime = now.getTime();
var day = now.getDay();
var oneDayTime = 24 * 60 * 60 * 1000;
//显示周一
var MondayTime = nowTime - (day - 1) * oneDayTime;
//显示周日
var SundayTime = nowTime + (7 - day) * oneDayTime;
//初始化日期时间
var monday = new Date(MondayTime);
var sunday = new Date(SundayTime);
function getCurrentMonthFirst() {
    var date = new Date();
    date.setDate(1);
    return date;
}
function getCurrentMonthLast() {
    var date = new Date();
    var currentMonth = date.getMonth();
    var nextMonth = ++currentMonth;
    var nextMonthFirstDay = new Date(date.getFullYear(), nextMonth, 1);
    var oneDay = 1000 * 60 * 60 * 24;
    return new Date(nextMonthFirstDay - oneDay);
}
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function dateFormat_1(longTypeDate) {
    var dateType = "";
    var date = new Date();
    date.setTime(longTypeDate);
    dateType += date.getFullYear();   //年
    dateType += "-" + getMonth(date); //月
    dateType += "-" + getDay(date);   //日
    return dateType;
}
//返回 01-12 的月份值
function getMonth(date) {
    var month = "";
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11
    if (month < 10) {
        month = "0" + month;
    }
    return month;
}

function getDay(date) {
    var day = "";
    day = date.getDate();
    if (day < 10) {
        day = "0" + day;
    }
    return day;
}


function submitDate() {
    var today = new Date();
    var start = $('#timeStartStr').val()
    var end = ($('#timeEndStr').val())
    var date = today.Format("yyyy-MM-dd");
    var yesterday = dateFormat_1(today.setTime(today.getTime() - 24 * 60 * 60 * 1000));
    var type = 0;
    if (start == date && end == date) {
        $('#day').val("today")
        type = 1;
    }
    if (type != 1) {
        if (start == yesterday && end == yesterday) {
            $('#day').val("yesterday")
            type = 1;
        }
    }
    if (type != 1) {
        if (start >= (monday.Format("yyyy-MM-dd")) && end <= (sunday.Format("yyyy-MM-dd"))) {
            $('#day').val("week")
            type = 1;
        }
    }
    if (type != 1) {
        if (start >= (getCurrentMonthFirst().Format("yyyy-MM-dd")) && end <= (getCurrentMonthLast().Format("yyyy-MM-dd"))) {
            $('#day').val("month")
            type = 1;
        }
    }
    if(type != 1){
        if (start < (getCurrentMonthFirst().Format("yyyy-MM-dd")) || end >= (getCurrentMonthLast().Format("yyyy-MM-dd"))) {
            $('#day').val("")
            type = 1;
        }
    }
}
$('.today').on('click', function () {
    $('#day').val("today");
    $('input[name="timeEndStr"]').val("");
    $('input[name="timeStartStr"]').val("");
    $('#searchForm').prop("action", $(this).attr("action")).submit();
})
$('.yesterday').on('click', function () {
    $('#day').val("yesterday");
    $('input[name="timeEndStr"]').val("");
    $('input[name="timeStartStr"]').val("");
    $('#searchForm').prop("action", $(this).attr("action")).submit();
})
$('.week').on('click', function () {
    $('#day').val("week");
    $('input[name="timeEndStr"]').val("");
    $('input[name="timeStartStr"]').val("");
    $('#searchForm').prop("action", $(this).attr("action")).submit();
})
$('.month').on('click', function () {
    $('#day').val("month");
    $('input[name="timeEndStr"]').val("");
    $('input[name="timeStartStr"]').val("");
    $('#searchForm').prop("action", $(this).attr("action")).submit();
})