var birthday= getUrlVars()["birthday"];
var birthdayss="";
if (birthday!=null&&birthday!="undefinde"){
    birthdayss=birthday.replace("%20"," ");
}
console.log(birthdayss)
$(function () {
    vm.childCode= getUrlVars()["childCode"];
    var chilHere= getUrlVars()["chilHere"];
    var chilName= getUrlVars()["chilName"];
    var birthdays= getUrlVars()["birthdays"];
    vm.birthdays= birthdays;
    vm.chilName= decodeURI(chilName);

    vm.chilHere= decodeURI(chilHere);
    vm.loadHistory();
    $("input[value='remove']").attr("checked","checked");
})
var vm;
vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        childCode: null,
        chilName: null,
        chilHere: null,
        birthdays: null,
        childInfo: {
            chilName: null,
            chilCode: null,
            chilBirthday: null,
            age: null,
            chilSex: null,
            chilCommittee: null,
            chilMother: null,
            chilMobile: null,
            chilAccount: null,
            chilHere: null,
            remark: null,
            chilAddress: null
        },
        inoculateSiteArr: [], //接种部位数组
    },
    methods: {
        query: function () {

        },
        loadHistory: function () {
            $("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + vm.childCode+"&birthdays="+vm.birthdays);

            $("#move_iframe").attr("src", "../child/tchildmove.html?chilCode=" + vm.childCode + "&chilHere=" + vm.chilHere + "&chilName=" + vm.chilName);
            $("#abnormal_iframe").attr("src", "../child/tchildabnormal.html?chilCode=" + vm.childCode + "&chilName=" + vm.chilName);
            $("#taboo_iframe").attr("src", "../child/tchildtaboo.html?chilCode=" + vm.childCode + "&chilName=" +  vm.chilName);
            $("#allergy_iframe").attr("src", "../child/tchildallergy.html?chilCode=" + vm.childCode + "&chilName=" +  vm.chilName);
            $("#infection_iframe").attr("src", "../child/tchildinfection.html?chilCode=" + vm.childCode + "&chilName=" +  vm.chilName);
        },
        cancel: function () {
            $("#enterSave").removeAttr("disabled");
            // $("#inoDate").val("");
            $('#bioName').selectpicker('val', "");
            $("#bioName").selectpicker('refresh');
            $('#inoculateSiteCode').selectpicker('val', "");
            $("#inoculateSiteCode").selectpicker('refresh');
            $("#inocRegulatoryCode").val("");
            $("#inocRegulatoryCode").val("");
        },
        loadData: function () {
            //加载疫苗的名称信息,查询已经领取的疫苗
            ///receive/receiveList?type=0&_search=false&nd=1540911582735&limit=10&page=1&sidx=&order=asc

            //加载接种部位
            $.ajax({
                type: "post",
                url: "../child/listdiccode?ttype=code_inoculation_site",
                dataType: 'json',
                async: false,
                contentType: 'application/json;charset=UTF-8',
                success: function (data) {
                    var result = data.data;
                    $.each(result, function (i, n) {

                        $("#inoculateSiteCode").append(" <option value=\"" + n.value + "\">" + n.text + "</option>");
                    })
                    $("#inoculateSiteCode").selectpicker('refresh');
                }
            });
            //生产企业
            $.ajax({
                // get请求地址
                url: '../tvacfactory/getAllData',
                dataType: "json",
                type: 'POST',
                success: function (data) {
                    var result = data.page;
                    $("#factory").append(" <option value='-1'></option>")
                    $.each(result, function (i, n) {

                        $("#factory").append(" <option value=\"" + n.factoryCode + "\">" + n.factoryCnName + "</option>");
                    })
                    $("#factory").selectpicker('refresh');
                }
            });


        },
        close: function () {
            window.parent.layer.closeAll();
        },
        loadVac: function (type) {
            var url;
            if (type == 'remove') {
                url = '../receive/receiveList?type=0&_search=false&nd=1540911582735&limit=1000&page=1&sidx=&order=asc';
            } else {
                url = '../tvacinfo/getAllData'
            }
            $.ajax({
                // get请求地址
                url: url,
                dataType: "json",
                type: 'POST',
                success: function (data) {
                    var result = data.page.list;
                    if (result == undefined) {
                        result = data.page;
                    }
                    var name = "";
                    $("#bioName").empty();
                    $("#bioName").append(" <option value='-1'></option>");
                    var newArr=new Array();
                    $.each(result, function (i, n) {
                        if (type == 'remove') {
                            name = n.productName
                        } else {
                            name = n.bioCnSortname
                        }
                        // if($("#bioName option").HTML())

                        var datas=n.bioCode+':'+name;
                         newArr.push(datas);
                       // $("#bioName").append(" <option value=\"" + n.bioCode + "\">" + name + "</option>");
                    })
                    var par=uniqueArr(newArr);
                    console.log(par);
                    $.each(par, function (i, n) {
                         var n=n.split(":");
                         $("#bioName").append(" <option value=\"" + n[0] + "\">" + n[1] + "</option>");
                    })
                    $("#bioName").selectpicker('refresh');

                }
            });
        },
        changeStatus: function () {
           var other= $("#other").prop("checked");
           if(other){
              $("#departInput").show();
               $("#departInput").find("input").val("省外");
              $("#departSelect").hide();
           }else{
               $("#departInput").find("input").val("");
               $("#departInput").hide();
               $("#departSelect").show();
           }
        }

    }
});

$(function () {

    //可选疫苗
    //初始化下拉框
    $('.selectpicker').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'',
        noneResultsText:'没有匹配的结果',
        actionsBox:true
    });
    vm.loadVac('remove');
    $('#inocDepart').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'',
        noneResultsText:'没有匹配的结果',
        actionsBox:true
    });
    $("#inoculateSiteCode").selectpicker('val',1);
    //初始化日期
    $('#inoDate').datetimepicker(
        {
            format: 'yyyymmdd',
            autoclose: true,
            todayBtn: true,
            startView: 'month',
            minView:'month',
            language:  'zh-CN'
        }
    );

    //查询儿童信息
    $.ajax({
        type: "get",
        url:'../child/list?page=1&limit=10',
        data:{'chilCode':vm.childCode},
        dataType: 'json',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        success: function (data) {
            vm.childInfo=data.page.list[0];
            vm.childInfo.chilCreatedate = vm.childInfo.chilCreatedate.substring(0,10);
            vm.childInfo.chilLeavedate = vm.childInfo.chilLeavedate.substring(0,10);
            if(vm.childInfo.syncstatus==1){
                vm.childInfo.syncstatus = '已同步';
            }else{
                vm.childInfo.syncstatus = '未同步';
            }
            var ages = getAge(vm.childInfo.chilBirthday);
            vm.childInfo.age=ages;
            console.log(vm.childInfo)
        }
    });
    vm.loadData();

})


function getUrlVars() {
    var vars = [],
        hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for (var i = 0; i < hashes.length; i++) {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}
function getAge(birthTime) {
    var age = getDateTimeDiff(new Date(birthTime));
    var age_str = age.year > 0 ? age.year + "年" : "";
    age_str += age.month > 0 ? age.month + "月" : "";
    if(!isNaN(parseInt(age_str))){
        age_str += age.day + "日";
        //$("#monthOld").val(age_str);
        return age_str;
    }else{
        age_str += age.day > 0 ? age.day + "日" : "";
        var time1 = new Date(birthTime).Format("yyyy-MM-dd");
        var time2 = new Date().Format("yyyy-MM-dd");
        if(time1 == time2){
            return "0年0月0日";
        }else{
            return age_str;
        }
    }
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


$("#historyRecord").on("mouseover", 'tr[role="row"]', function () {
    var trid = $(this).attr("id");
    //里面写相关的操作。
    var rowData = $("#historyRecord").jqGrid('getRowData', trid);
    var emergencySencondMgrId = rowData["inocDate"];//列名和jGrid定义时候的值一样
    var statas = rowData["inocOpinion"];//列名和jGrid定义时候的值一样
    var channel = parseInt(rowData["channel"]);//列名和jGrid定义时候的值一样
    var str = '';
    var ch="";
    switch (channel){
        case 1:ch="肌内";break;
        case 2:ch="皮下";break;
        case 3:ch="皮内";break;
        case 4:ch="口服";break;
        default :ch="其他";
    }
    if (statas == 1) {
        str = '及时'
    } else if (statas == 2) {
        str = '合格'
    } else if (statas == 3) {
        str = '超期'
    } else if (statas == 4) {
        str = '首针提前'
    } else if (statas == 5) {
        str = '间短'
    }
    layer.msg("<p align='left'>接种月龄:"+dateDiff(birthdayinocs, emergencySencondMgrId) + "</p><p align='left'>接种评价：" + str+"</p>"+"<p align='left'>接种途径：" + ch+"</p>");
})

