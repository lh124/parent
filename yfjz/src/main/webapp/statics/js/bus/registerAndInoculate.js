//获取过敏史、接种异常反应、接种禁忌、传染病史
function getTrouble(parm) {
    $.ajax({
        url: '../child/listTrouble?page=1&limit=100&sidx=1&order=1&chilCode='+parm,
        dataType: 'json',
        success: function (data) {
            if(data.page!=null) {
                $("#allergyHistory").html("");
                $("#taboo").html("");
                $("#abnormalHistory").html("");
                $("#infectionHistory").html("");
                for (var i = 0; i < data.page.tChildAllergyList.length; i++) {
                    $("#allergyHistory").append(":"+data.page.tChildAllergyList[i].describes + ";");
                }
                for (var i = 0; i < data.page.tChildTabooList.length; i++) {
                    $("#taboo").append(":"+data.page.tChildTabooList[i].istaCode + ";");
                }
                for (var i = 0; i < data.page.tChildAbnormalList.length; i++) {
                    $("#abnormalHistory").append(":"+data.page.tChildAbnormalList[i].aefiCode + ";");
                }
                for (var i = 0; i < data.page.tChildInfectionList.length; i++) {
                    $("#infectionHistory").append(":"+data.page.tChildInfectionList[i].infeCode + ";");
                }
            }
        }
    });
}
/**
 * 合并jqGrid方法
 * */
function MergerStatistics(gridName, CellName) {

    //得到显示到界面的id集合

    var mya = $("#" + gridName + "").getDataIDs();

    //当前显示多少条

    var length = mya.length;

    for (var i = 0; i < length; i++) {

        //从上到下获取一条信息

        var before = $("#" + gridName + "").jqGrid('getRowData', mya[i]);

        //定义合并行数

        var rowSpanTaxCount = 1;

        for (j = i + 1; j <= length; j++) {

            //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏

            var end = $("#" + gridName + "").jqGrid('getRowData', mya[j]);

            var cellNames = CellName.split(",");

            for (var n = 0; n < cellNames.length; n++) {

                if (before[cellNames[0]] == end[cellNames[0]] ) {

                    rowSpanTaxCount++;

                    $("#" + gridName + "").setCell(mya[j], cellNames[0], '', { display: 'none' });

                } else {

                    rowSpanTaxCount = 1;

                    break;

                }

                $("#" + cellNames[0] + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);//最后合并需要合并的行与合并的行数

            }

        }

    }

}


//获取过敏史
function getAllergyHistory(parm) {
    $.ajax({
        url: '../tchildallergy/list?page=1&limit=100&sidx=1&order=1&chilCode='+parm,
        dataType: 'json',
        success: function (data) {
            if(data.page!=null) {
                $("#allergyHistory").html("");
                for (var i = 0; i < data.page.list.length; i++) {
                    $("#allergyHistory").append(data.page.list[i].describes + ";");
                }
            }
        }
    });

}
//获取接种禁忌
function getTaboo(parm) {
    $.ajax({
        url: '../tchildtaboo/list?page=1&limit=100&sidx=1&order=1&chilCode='+parm,
        dataType: 'json',
        success: function (data) {
            if(data.page!=null) {
                $("#taboo").html("");
                for (var i = 0; i < data.page.list.length; i++) {
                    $("#taboo").append(data.page.list[i].istaCode + ";");
                }
            }

        }
    });

}
//获取接种异常反应
function getAbnormalHistory(parm) {
    $.ajax({
        url: '../tchildabnormal/list?page=1&limit=100&sidx=1&order=1&chilCode='+parm,
        dataType: 'json',
        success: function (data) {
            if(data.page!=null){
                $("#abnormalHistory").html("");
                for(var i=0;i<data.page.list.length;i++){
                    $("#abnormalHistory").append(data.page.list[i].aefiCode+";");
                }
            }

        }
    });

}

//获取传染病史
function getInfectionHistory(parm) {
    $.ajax({
        url: '../tchildinfection/list?page=1&limit=100&sidx=1&order=1&chilCode='+parm,
        dataType: 'json',
        success: function (data) {
            if(data.page!=null) {
                $("#infectionHistory").html("");
                for (var i = 0; i < data.page.list.length; i++) {
                    $("#infectionHistory").append(data.page.list[i].infeCode + ";");
                }
            }
        }
    });

}


function getAge(birthTime) {
    var age = getDateTimeDiff(new Date(birthTime));
    var age_str = age.year > 0 ? age.year + "年" : "";
    //age_str += age.month > 0 ? age.month + "月" : "";
    age_str += age.month > 0 ? age.month + "个月" : "";
    if(!isNaN(parseInt(age_str))){
        //age_str += age.day + "日";
        age_str += age.day + "天";
        //$("#monthOld").val(age_str);
        return age_str;
    }else{
        //age_str += age.day > 0 ? age.day + "日" : "";
        age_str += age.day > 0 ? age.day + "天" : "";
        var time1 = new Date(birthTime).Format("yyyy-MM-dd");
        var time2 = new Date().Format("yyyy-MM-dd");
        if(time1 == time2){
            return "0年0月0日";
        }else{
            return age_str;
        }
    }
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
};
// 计算出生时间
function getDateTimeDiff(startTime) {
    var now = new Date();
    var result = {};
    result.year = now.getFullYear() - startTime.getFullYear();
    //result.month = now.getMonth() - startTime.getMonth();
    if (now.getMonth() >= startTime.getMonth()) {
        result.month = now.getMonth() - startTime.getMonth();
    } else {
        result.year--;
        result.month = 12 - startTime.getMonth() + now.getMonth();
    }
    if (now.getDate() >= startTime.getDate()) {//如果大于出生那天
        result.day = now.getDate() - startTime.getDate();
    } else {
        if (result.month > 0) {
            result.month--;
        } else {
            result.year--;
            result.month = 12 - (startTime.getMonth() - now.getMonth()) - 1;
        }
        result.day = getMonthLastDay(startTime.getFullYear(), startTime
                .getMonth() + 1)
            - startTime.getDate() + now.getDate();
    }
    return result;
}

//获取某月份的最后一天
function getMonthLastDay(year, month) {
    return new Date(new Date(year, month + 1, 1).getTime() - 3600000 * 24).getDate();
}

function observe(value, options, row) {

    if(row.createTime==null){
        return "";
    }
    var date1 = new Date(row.createTime);  //开始时间
    var currentDate = new Date();
    var date2 = new Date(row.leaveTime);    //结束时间
    var date3 = date2.getTime() - date1.getTime();  //相差的毫秒数
    var date4 = currentDate.getTime() - date1.getTime();  //相差的毫秒数

    //计算出相差天数
    var days = Math.floor(date3 / (24 * 3600 * 1000));
    //计算出小时数
    var hours = Math.floor(date3 %(24 * 3600 * 1000)/ (3600 * 1000));
    //计算相差分钟数
    var minutes = Math.floor(date3 %(3600 * 1000)/ (60 * 1000));
    //当前时间与接种完成时间差
    var minutes2 = Math.floor(date4 %(3600 * 1000)/ (60 * 1000));
    //alert(minutes);
    if(minutes2<30 && minutes<0){
        return "";
    }
    if(minutes2>=60 && minutes<0){
        return "未留观";
    }
    if(minutes2>=60 && isNaN(minutes)){
        return "未留观";
    }
    /*if (isNaN(minutes)) {
        return "未留观";
    }*/
    if (minutes >= 30||hours>1) {
        return "完成"
    } else if(minutes<30 && minutes>0) {
        return "未完成";
    }else{
        return "";
    }
    //  return "未留观";
}

function observeTime(value, options, row) {
    var date1 = new Date(row.createTime);  //开始时间
    //alert("aa");
    var date2 = new Date(row.leaveTime);    //结束时间
    var date3 = date2.getTime() - date1.getTime();  //香蕉差的毫秒数

    //计算出相差天数
    var days = Math.floor(date3 / (24 * 3600 * 1000));
    //计算出小时数
    var hours = Math.floor(date3 %(24 * 3600 * 1000)/ (3600 * 1000));
    //计算相差分钟数
    var minutes = Math.floor(date3 %(3600 * 1000)/ (60 * 1000));
    if (isNaN(minutes)) {
        return "";
    }
    if(minutes<=0){
        return "";
    }else{
        return minutes;
    }

}