$(function () {

    $("#batchGrid").jqGrid({
       // url: '../batch/list?selectTimes=20180904154912',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 130, key: true,hidden:true },
            { label: '儿童编码', name: 'chilCode', width: 130 },
            { label: '行政村', name: 'committee', hidden: true },
            { label: '姓名', name: 'chilName', width: 60 ,align : "center"},
            { label: '性别', name: 'chilSex', width: 50 ,align : "center"},
            { label: '出生日期', name: 'chilBirthday', width: 80 },
            { label: '父亲姓名', name: 'fatherName', width: 60 },
            { label: '母亲姓名', name: 'matherName', width: 60 },
            { label: '家庭电话', name: 'chilTel', width: 80 },
            { label: '手机', name: 'chilMobile', width: 80 },
            { label: '联系地址', name: 'address', width: 150 },
            { label: '疫苗', name: 'planName', width: 80 },
            { label: '批号', name: 'batchnum', width: 80 },
            { label: '疫苗ID', name: 'planId', width: 80 ,hidden:true},
            { label: '剂次', name: 'times', width: 40 ,align : "center"},
            { label: '接种部位', name: 'inoculateSiteCode', width: 120 ,
                formatter: function(cellValue, options, rowdata) {
                    if (cellValue == 1 || cellValue == '左上臂') {
                        return "左上臂";
                    } else if (cellValue == 2 || cellValue == '右上臂') {
                        return "右上臂";
                    } else if (cellValue == 4 || cellValue == '左大腿') {
                        return "左大腿";
                    } else if (cellValue == 5 || cellValue == '右大腿') {
                        return "右大腿";
                    } else if (cellValue == 7 || cellValue == '左臀部') {
                        return "左臀部";
                    } else if (cellValue == 8 || cellValue == '右臀部') {
                        return "右臀部";
                    } else if (cellValue == 9 || cellValue == '口服') {
                        return "口服";
                    }
                    else {
                        return "";
                    }
                },
                unformat:Unformat_Select
            },
            { label: '日期', name: 'inoDate', width: 80 ,formatter: function (value, grid, rows, state) {
                    if(value != null && value != "" && value.length >=10){
                        return value.substring(0,10);
                    }
                    return "";
                }
            }
            , { label: '在册情况', name: 'here', width: 80},
            { label: '备注', name: 'remark', width: 100},
            { label: '是否接种', name: 'isInoc', width: 60},
        ],
        viewrecords: true,
        height: 'auto',
        sortable:true,
        sortname : 'chilName',
        sortorder : "desc",
        //width:1200,
        rowNum: 1000,
        //rowList : [10,30,50],
        rownumbers: true,
       // rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        // pager: "#batchGridPage",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            //$("#batchGrid").closest(".ui-jqgrid-bdiv").css("overflow-x","hidden");
            $("#batchGrid").setGridParam().hideCol("committee");
        },
        grouping:true,
        groupingView : {
            groupField : ['committee']
        },
        onSelectRow:function (rowid,status) {
            if (!status) return;//如果是取消选中，则不开启编辑模式
            var str = "";
            $.ajax({
                type:"post",
                url:"../sys/dict/maplist",
                dataType:'json',
                async: false,
                contentType:'application/json;charset=UTF-8',
                success: function (data) {
                    var inoculateSiteArr = data.list[0];
                    $.each(inoculateSiteArr, function (index, item) {
                        str = str + item.value + ":" + item.text + ";";
                    });
                    str = str.substring(0, str.length - 1);
                    $('#batchGrid').setColProp('inoculateSiteCode', {
                        editable: true, edittype: "select",editoptions: {buildSelect: null, dataUrl: null, value: str}
                    });
                }
            });

            //开启编辑
            $("#batchGrid").editRow(rowid);
        }
    });
    $('#times').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择查询记录...',
        actionsBox:true
    });
    reloadTimes();
    $("#batchGrid").closest(".ui-jqgrid-bdiv").css("overflow-x","hidden");
    $("html").css("overflow-x","auto")
    loadBios();
    loadCommiteeData();
    loadInfoStatusData();
    pinyincommsearch();//拼音查询
    $("#chilBirthdayEnd").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    $("#chilBirthdayStart").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
   

})
var orgId=$("#orgId").val();
var chilCommittee='';

function pinyincommsearch() {
    //初始化下拉框
    $('#chilCommittees').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择行政村/居委会',
        //     actionsBox:true,
        search:false,
    });

    // 行政村/居委会拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#chilCommitteeIdParent").find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $('#chilCommitteeIdParent .open input').val();
        var hunt = $("#chilCommittees");
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {
            hunt.empty();
            var value = null;
            if("undefined" != typeof chilCommittee){
                value = chilCommittee;
            }
            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if(reg.test(inputManagerName)){
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
                    }
                }
            }else{
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].name.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
                    }
                }
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            event.stopPropagation();
            return false;
        } else{
            //如果输入的字符为空,清除之前option标签
            hunt.empty();
            var value = null;
            if("undefined" != typeof chilCommittee){
                value = chilCommittee;
            }
            var con = '<option value=""></option>';
            for (var i = 0; i < value.length; i++) {
                con += '<option  value="' + value[i].code + '">' + value[i].name + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });
}
function reloadTimes() {
    $('#times').selectpicker('val', "");
    $.ajax({
        url: '../batch/queryAllTimes',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            $("#times").find("option").remove();
            $("#times").append(" <option value=''></option>");
            $.each(results, function (i, n) {
                $("#times").append(" <option value=\"" + n.times + "\">" + n.times + "</option>");
            })
            $("#times").selectpicker('refresh');
        }
    });
}
function queryData() {
    var times = $("#times").selectpicker('val');
    if (times == undefined || times == null || times == "") {
        layer.msg("请选择一条查询记录");
        $("#batchGrid").jqGrid("clearGridData")
        return;
    }
    reset();
    var url = '../batch/list?selectTimes=' + times;

    $("#batchGrid").jqGrid('setGridParam', {
        postData:"",
        url: url

    }).trigger("reloadGrid");
}
function deleteRecord() {
    var times = $("#times").selectpicker('val');
    if (times == undefined || times == null || times == "") {
        layer.msg("请选择一条查询记录");

        return;
    }
    layer.confirm("请确定删除该条查询记录吗？",function (index) {
        $.ajax({
            url: '../batch/deleteTimes?selectTimes='+times,
            dataType: "json",
            type: 'POST',
            success: function (results) {
                layer.msg(results.msg);
                reloadTimes();
                layer.close(index);
            }
        });
    })

}
//个案状态
function loadInfoStatusData(){

    var param = new Array();
    $.ajax({
        url: '../child/listdiccode?ttype=child_info_status',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            var seldata = results.data;
            if("undefined" != typeof vm){
                vm.chilCommittee = seldata;
            }
            $.each(seldata, function (i, n) {
                $("#infostatus").append(" <option value=\"" + n.value + "\">" + n.text + "</option>");
            })
            $("#infostatus").selectpicker('refresh');
        }
    });
}
//行政村
function loadCommiteeData(){
    var param = new Array();
    $.ajax({
        url: '../tbasecommittee/list?org_id='+orgId+'&page=1&limit=1000',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            var seldata = results.page.list;
            if("undefined" != typeof chilCommittee){
                chilCommittee = seldata;
            }
            $.each(seldata, function (i, n) {
                $("#chilCommittees").append(" <option value=\"" + n.name + "\">" + n.name + "</option>");
            })
            $("#chilCommittees").selectpicker('refresh');
        }
    });
}
//疫苗类别
function loadBios(){
    var param = new Array();
    $.ajax({
        url: '../truledic/list?page=1&limit=1000',
        dataType: "json",
        type: 'POST',
        success: function (results) {
            var seldata = results.page.list;
            $.each(seldata, function (i, n) {
                $("#biotypes").append(" <option value=\"" + n.name + "\">" + n.name + "</option>");
            })
            $("#biotypes").selectpicker('refresh');

        }
    });
}
function reset() {
        $("#batchForm")[0].reset();
    $('#biotypes').selectpicker('val', "");
    $("#biotypes").selectpicker('refresh');
    $('#chilCommittees').selectpicker('val', "");
    $("#chilCommittees").selectpicker('refresh');
    $('#infostatus').selectpicker('val', "");
    $("#infostatus").selectpicker('refresh');
}
function query() {
    var times = $("#times").selectpicker('val');
    if (times == undefined || times == null || times == "") {
        layer.msg("请选择一条查询记录");
        return;
    }
    var url = '../batch/list?selectTimes=' + times;
    var datas=$("#batchForm").serialize();
    $("#batchGrid").jqGrid('setGridParam', {
        postData:datas,
        url: url
    }).trigger("reloadGrid");
}
function batchInoculate() {
    var ele=$("#batchGrid");
    endEdit(ele)
    var rows=getSelectedRows("batchGrid");
    var param=new Array();
    for (var i = 0; i < rows.length; i++) {
        var row = ele.jqGrid("getRowData", rows[i]);
        if (row.isInoc == null || row.isInoc == undefined || row.isInoc == "") {
            if (row.inoculateSiteCode == '不详') {
                row.inoculateSiteCode = '10';
            }
            param.push(row);
        }else if (row.isInoc == "是") {

            param.push(row);
        }
    }
    layer.confirm("确认进行补录操作吗？",function () {
        $.ajax({
            url: '../batch/saveInoculateInfo',
            dataType: "json",
            contentType:'application/json;charset=UTF-8',
            data:JSON.stringify({"rows":param}),
            type: 'POST',
            success: function (results) {
                if(results.code==0){
                    if(results.type=="web"){
                        var times = $("#times").selectpicker('val');
                        var url = '../batch/list?selectTimes=' + times;
                        $("#batchGrid").jqGrid('setGridParam', {
                            url: url
                        }).trigger("reloadGrid");
                    }else{
                        $("#batchGrid").jqGrid("clearGridData");
                    }

                }
                layer.msg(results.msg)

            }
        });
    })

}
function printTable() {
    var times = $("#times").selectpicker('val');
    if (times == undefined || times == null || times == "") {
        layer.msg("请选择一条查询记录");
        return;
    }
    $("#selectCondition").hide();
    $("#selectCondition").after("<h1 align='center'>批量补录统计</h1>");
    window.focus();
    window.print();
    $("#selectCondition").next("h1").remove();
    $("#selectCondition").show();

}
function outputExl() {
    var times = $("#times").selectpicker('val');
    if (times == undefined || times == null || times == "") {
        layer.msg("请选择一条查询记录");
        return;
    }
    window.location.href='../batch/batchOutputExcel?selectTimes='+times+"&"+$("#batchForm").serialize();
}
function input() {
    var times = $("#times").selectpicker('val');
    if (times == undefined || times == null || times == "") {
        layer.msg("请选择一条查询记录");
        return;
    }
    window.location.href='../batch/batchOutputExcel?selectTimes='+times+"&"+$("#batchForm").serialize();
}

/**
 * 上传文件
 *
 */
function inputExl() {

        var fileName=$("#uploadFile").val();
        if (fileName==null||fileName==""||fileName==undefined){
            layer.msg("请选择文件！");
            return;
        }
        var ret= checkform(fileName);
        if (!ret){
            return;
        }
        $.ajaxFileUpload({
            url :'../batch/uploadFile',//后台请求地址
            type: 'post',//请求方式  当要提交自定义参数时，这个参数要设置成post
            secureuri : false,//是否启用安全提交，默认为false。
            fileElementId : 'uploadFile',// 需要上传的文件域的ID，即<input type="file">的ID。
            dataType : 'json',//服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。如果json返回的带pre,这里修改为json即可解决。
            success : function (result,status) {//提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
               console.log(result);
               layer.msg(result.message);
                $("#batchGrid").jqGrid("clearGridData");
                $("#batchGrid").setGridParam({datatype : 'local',data:result.data}).trigger('reloadGrid');
               $("#uploadFile").val("");
            },
            error : function (json, status, e) {//提交失败自动执行的处理函数。
            }
        });
}

/**
 * 判断上传的文件格式是否为excel
 * @param o
 * @returns {boolean}
 */
function checkform(fileName){
    var fileext=fileName.substring(fileName.lastIndexOf("."),fileName.length)
    fileext=fileext.toLowerCase()
    if (fileext!='.xls'&&fileext!='.xlsx'){
        layer.msg("对不起，导入数据格式必须是xls或xlsx格式文件哦，请您调整格式后重新上传，谢谢 ！");
        return false;
    }
    return true;
}

function changeChildInfo() {
   var ele= $("#batchGrid");
   // var selectRow= ele.getGridParam("selarrrow");
    var rowKey = ele.getGridParam("selrow");
    if(!rowKey){
        layer.msg("请选择全部数据，进行批量变更！");
        return ;
    }
   var ids=getRows(ele);
   var rows=getSelectedRows("batchGrid");
   if(rows.length!=ids.length){
        layer.msg("请选择全部数据，进行批量变更！");
        return;
   }
   layer.confirm("确认变更儿童信息吗?",function () {
       $.ajax({
           url:"../batch/changeChildInfo",
           type:'post',
           dataType: "json",
           contentType:'application/json;charset=UTF-8',
           data:JSON.stringify({"rows":ids}),
           success:function (data) {
               layer.msg(data.msg);
           }

       })
   })



}