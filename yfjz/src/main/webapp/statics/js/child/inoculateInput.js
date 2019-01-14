var birthday= getUrlVars()["birthday"];
var birthdayss="";
if (birthday!=null&&birthday!="undefinde"){
    birthdayss=birthday.replace("%20"," ");
}
$(function () {
    vm.childCode= getUrlVars()["childCode"];
    $("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + vm.childCode+"&birthdays="+birthdayss);
    $("#historyRecord").jqGrid({
        url: '../tchildinoculate/list?chilCode='+vm.childCode,
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', key: true, hidden: true, width: 10,align:'center'},
            {label: '疫苗类别', name: 'inocVcnKind', width: 180, editable: true,align:'center',
                cellattr: function (rowId, tv, rawObject, cm, rdata) {
                    return 'id=\'inocVcnKind' + rowId + "\'";
                }
            },
            {label: '疫苗名称', name: 'inocBactCode', width: 180, editable: true,align:'center'
            },
            {label: '剂次', name: 'inocTime', width: 50,align:'center'},
            {label: '接种日期', name: 'inocDate', width: 140,align:'center',
                formatter:function (value) {
                    return value.substring(0,10);
                }
            },
            {label: '接种部位', name: 'inocInplId', width: 70,align:'center'},
            {label: '疫苗批号', name: 'inocBatchno', width: 110,align:'center'},

            {label: '接种状态', name: 'inocOpinion', hidden: true, width: 120},
            {label: '接种单位', name: 'inocDepaCode', width: 170,align:'center'},
            {label: '生产企业', name: 'inocCorpCode', width: 90,align:'center'},
            {label: '接种护士', name: 'inocNurse', width: 80,align:'center'},
            {label: '接种属性', name: 'inocProperty', width: 60,align:'center'}

            /* { label: '疫苗失效期', name: 'inocValiddate', width: 85 },*/

            /* {
                 label: '是否免费', name: 'inocFree', width: 70,
                 formatter: function (value) {
                     if (value == 1) {
                         return "免费";
                     } else if (value == 0) {
                         return "收费";
                     } else {
                         return "";
                     }
                 },
                 unformatter: function (cellvalue, options, cellobject) {
                     return cellvalue;
                 }
             }*/



            /*{label: '留观是否完成', name: 'leaveTime', width: 80, formatter: observe},
            {label: '留观时间', name: 'observeTime', width: 80, formatter: observeTime},
            {label: '添加记录时间', name: 'createTime', width: 80, hidden: true}*/

            /*  { label: '备注', name: 'remark', width: 80 }*/
        ],
        viewrecords: true,
        // height: 165,
        width:'817px',
        height: '800px',
        rowNum: 1000,
        rowList: [40, 50, 80],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        // multiselect: true,
        pager: "#inoculationGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            MergerStatistics("historyRecord", 'inocVcnKind');
            //隐藏grid底部滚动条
            // $("#inoculationGridGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    // vm.loadHistory();
    $("input[value='remove']").attr("checked","checked");
    $(":radio").click(function () {
       var val= $(this).val();
        if(val=='normal'){
            $("input[value='remove']").removeAttr("checked");
            $("input[value='normal']").attr("checked","checked");
            $(".tbHide").show();
            $("#batchnumList").selectpicker("val","");
            $("#batchnumList").selectpicker('refresh');
            $("#factory").selectpicker("val","");
            $("#factory").selectpicker('refresh');
            $("#bioName").selectpicker('refresh');
            $("#batchnumList").empty();
            $("#expiryDate").val("");
            $("#batchnumList").selectpicker('refresh');
            vm.loadVac(val);
        }else if(val=='remove'){
            $("input[value='normal']").removeAttr("checked");
            $("input[value='remove']").attr("checked","checked");
            $(".tbHide").hide();
            $("#expiryDate").val("");
            // $("#removeBatchnum").show();
            $("#batchnumList").selectpicker("val","");
            $("#batchnumList").selectpicker('refresh');
            $("#bioName").selectpicker('refresh');
            vm.loadVac(val);
        }
    })
    loadInfoStatusDatainput();//个案状态
    sexCode();//性别
    move1();//居住属性
    updateloadCommiteeData();//行政村
    $("#inosex").css({"display": "none"});
    $("#inochilHere").css({"display": "none"});
    $("#inochilAccount").css({"display": "none"});
    $("#updatachilcommittees").css({"display": "none"});


})
var vm;
vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        childCode: null,
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
        vaccineInfo: [], //疫苗信息
    },
    methods: {
        query: function () {

        },
        loadHistory: function () {

            $("#historyRecord").jqGrid('clearGridData');
            $("#historyRecord").jqGrid('setGridParam', {
                postData: {"chilCode": vm.childCode},
                url: '../tchildinoculate/list',
                page: 1,
            }).trigger("reloadGrid");
        },
        saveInoc: function () {
            debugger
            var inoDate = $("#inoDate").val();
            if (inoDate == null || inoDate == "" || inoDate == undefined) {
                layer.msg("接种时间不能为空");
                return;
            }
            if (!checkInputDate(inoDate)) {
                return;
            }
            var bioCode = $("#bioName").selectpicker('val');
            if (bioCode == null || bioCode == "" || bioCode == undefined) {
                layer.msg("疫苗不能为空");
                return;
            }
            $("#enterSave").attr('disabled', 'disabled');
            var inoculateSiteCode = $("#inoculateSiteCode").selectpicker('val');
            var channel = $("#channel").selectpicker('val');
            var inocRegulatoryCode = $("#inocRegulatoryCode").val();
            var data = {
                "inoDate": inoDate,
                "vaccineCode": bioCode,
                "inoculateSiteCode": inoculateSiteCode,
                "channel": channel,
                "inocRegulatoryCode": inocRegulatoryCode,
                "chilCode": vm.childCode,
                "type": "single"
            };
            var type = $("#inocType").find("input[checked]").val();
                data.batchnum=$("#batchnumList").selectpicker("val");
            if (type == 'remove') {
                data.type="remove";
            } else {
                data.factoryId=$("#factory").selectpicker("val")==-1?"":$("#factory").selectpicker("val");
               var departInput=$("#departInput").find("input").val();
               if (departInput==""||departInput==null||departInput==undefined){
                   data.orgId=$("#inocDepart").selectpicker("val")
               }else{
                   data.otherDepa=departInput;
               }
                data.expiryDate=$("#expiryDate").val();
                data.type="normal";

            }
            $.ajax({
                url: '../batch/saveSingle',
                dataType: "json",
                contentType: 'application/json;charset=UTF-8',
                data: JSON.stringify(data),
                type: 'POST',
                success: function (results) {

                    vm.cancel();
                    $("#batchnumList").empty();
                    $("#batchnumList").selectpicker('refresh');
                    $("#enterSave").removeAttr("disabled");
                    var type = $("#inocType").find("input[checked]").val();
                    if (type=="normal"){
                       $("#inoDate").val("");
                       $("#batchnum").val("");
                        $("#factory").selectpicker("val","");
                        $("#factory").selectpicker('refresh');
                        $("#expiryDate").val("");
                    }
                    if (results.code == 0) {

                        /*$("#historyRecord").trigger("reloadGrid");*/
                        $("#inoculation_iframe").attr("src", "../child/tchildinoculate.html?chilCode=" + vm.childCode+"&birthdays="+birthdayss);
                    }
                    layer.msg(results.msg)

                }
            });

        },
        cancel: function () {
            $("#enterSave").removeAttr("disabled");
            // $("#inoDate").val("");
            $('#bioName').selectpicker('val', "");
            $("#bioName").selectpicker('refresh');
            $('#inoculateSiteCode').selectpicker('val', "");
            $("#inoculateSiteCode").selectpicker('refresh');
            $('#channel').selectpicker('val', "");
            $("#channel").selectpicker('refresh');
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
            //加载接种途径
            $.ajax({
                type: "post",
                url: "../child/listdiccode?ttype=code_inoculation_route",
                dataType: 'json',
                async: false,
                contentType: 'application/json;charset=UTF-8',
                success: function (data) {
                    var result = data.data;
                    $.each(result, function (i, n) {

                        $("#channel").append(" <option value=\"" + n.value + "\">" + n.text + "</option>");
                    })
                    $("#channel").selectpicker('refresh');
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
    //初始化日期
    $('#inoDate,#expiryDate').datetimepicker(
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
            var ages = getAge(vm.childInfo.chilBirthday);
            vm.childInfo.age=ages;
       }
    });
    vm.loadData();
    $("#inoculateSiteCode").selectpicker('val',1);
    $("#channel").selectpicker('val',1);
    // $("#subFrom").find("td").addClass("tdClass");
    $(".tbHide").hide();
    $("#findInput").find("input").on("change",function () {
        var type = $("#inocType").find("input[checked]").val();
        var val=$(this).val();
        var lis= $(this).parent().next().find("li");
        var isExsite= $(lis[lis.length-1]).hasClass("no-results");
        if(isEmpty(val)){
            return;
        }
        if(type=="normal"&&isExsite){
            $("#batchnumList").append(" <option value=\"" + val + "\">" +val + "</option>");
            $("#batchnumList").selectpicker('val',val);
            $("#batchnumList").selectpicker('refresh');
        }
    });
})

/**
 * 改变批号
 */
function changeVac () {
    var batchum= $("#batchnumList").selectpicker('val');
    var type = $("#inocType").find("input[checked]").val();
    var data=vm.vaccineInfo;
    for(var i=0;i<data.length;i++){
        var row=data[i];
        if(row.batchnum==batchum){
            if(type=="remove"){
                $("#inoculateSiteCode").selectpicker('val',row.site);
                $("#channel").selectpicker('val',row.channel);
            }else{
                $("#inoculateSiteCode").selectpicker('val',row.site);
                $("#channel").selectpicker('val',row.channel);
                $("#expiryDate").val(row.expiryDate.replace(/\-/g, ""));
                $("#factory").selectpicker('val',row.factoryCode);
            }
        }
    }
}

/**
 * 获取领取的疫苗批号
 */
function getBatchnum() {
    var type = $("#inocType").find("input[checked]").val();
    // if(type=='remove'){
        var val=  $("#bioName").selectpicker('val');
        $.ajax({
            type:"get",
            url:"../receive/queryBatchNum",
            data:{"code":val,"type":type},
            async:false,
            success:function (ret) {
                var result=ret.data;
                vm.vaccineInfo=result;
                $("#batchnumList").empty();
                $.each(result, function (i, n) {

                    $("#batchnumList").append(" <option value=\"" + n.batchnum + "\">" + n.batchnum + "</option>");
                })
                $("#batchnumList").selectpicker('refresh');
                var row=result[0];
                if(isEmpty(row)){
                    return;
                }
                if(type=="remove"){
                    $("#inoculateSiteCode").selectpicker('val',row.site);
                    $("#channel").selectpicker('val',row.channel);
                }else{
                    $("#inoculateSiteCode").selectpicker('val',row.site);
                    $("#channel").selectpicker('val',row.channel);
                    $("#expiryDate").val(row.expiryDate.replace(/\-/g, ""));
                    $("#factory").selectpicker('val',row.factoryCode);
                }

            }
        })
    // }
}
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
    var str = '';
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
    layer.msg("接种月龄:"+dateDiff(birthdayss, emergencySencondMgrId) + ",接种评价:" + str);
})

