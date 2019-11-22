var datetimeFormatFromDate=function(obj){
	var year=obj.getFullYear(); //获取完整的年份(4位,1970-????)
	var month=Number(obj.getMonth())+1; //获取当前月份(0-11,0代表1月)
	var date=obj.getDate(); //获取当前日(1-31)
	var hours=obj.getHours(); //获取当前小时数(0-23)
	var min=obj.getMinutes(); //获取当前分钟数(0-59)
	var sec=obj.getSeconds(); //获取当前秒数(0-59)
	if(month<10){
		month="0"+month;
	}
	if(date<10){
		date="0"+date;
	}
	if(hours<10){
		hours="0"+hours;
	}
	if(min<10){
		min="0"+min;
	}
	if(sec<10){
		sec="0"+sec;
	}
	var time=year+"-"+month+"-"+date+" "+hours+":"+min+":"+sec;
	return time;
}

//或者星期x hh:mm:ss格式的时间
var datetimeFormat_hms = function (obj) {
    var hours = obj.getHours(); //获取当前小时数(0-23)
    var min = obj.getMinutes(); //获取当前分钟数(0-59)
    var sec = obj.getSeconds(); //获取当前秒数(0-59)
    var week = obj.getDay();
    var arr_weekday = ["星期日 ", "星期一 ", "星期二 ", "星期三 ", "星期四 ", "星期五 ", "星期六 "];
    var weekday = arr_weekday[week];
    if (hours < 10) {
        hours = "0" + hours;
    }
    if (min < 10) {
        min = "0" + min;
    }
    if (sec < 10) {
        sec = "0" + sec;
    }

    return weekday + "   " + hours + ":" + min + ":" + sec;
}


//获取xxxx年xx月xx日格式的数据
var datetimeFormat_ymd=function(obj){
	var year=obj.getFullYear(); //获取完整的年份(4位,1970-????)
	var month=Number(obj.getMonth())+1; //获取当前月份(0-11,0代表1月)
	var date=obj.getDate(); //获取当前日(1-31)
	if(month<10){
		month="0"+month;
	}
	if(date<10){
		date="0"+date;
	}
	var time=year+"年"+month+"月"+date+"日";
	return time;
}