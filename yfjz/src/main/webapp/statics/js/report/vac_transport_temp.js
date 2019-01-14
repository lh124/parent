$(function () {
    $("#startDate").val(formatDateTime(new Date()));
    $("#endDate").val(formatDateTime(new Date()));
    $('#startDate').datetimepicker(
        {
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            startView: 'month',
            minView:'month',
            language:  'zh-CN',
            initialDate:new Date()
        }
    );
    $('#endDate').datetimepicker(
        {
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            startView: 'month',
            minView:'month',
            language:  'zh-CN'
        }
    );
    $("#vacTransport").jqGrid({
        // url: '../work/list?',
        datatype: "json",
        colModel: [
            { label: '疫苗名称', name: 'username', width: 130, key: true },
            { label: '生产企业', name: 'towerName', width:80 },
            { label: '规格', name: 'finish', width: 80 },
            { label: '有效期', name: 'cancel', width: 80 },
            { label: '数量（支）', name: 'verySatisfied', width: 80 },
            { label: '疫苗类别', name: 'satisfied', width: 80 },
        ],
        viewrecords: true,
        height: '300',
        //width:1200,
        rowNum: -1,
        //rowList : [10,30,50],
        // rownumbers: true,
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
            $("#vacTransport").closest(".ui-jqgrid-bdiv").css("overflow-x","hidden");
        },
        onSelectRow:function (rowid,status) {
        }
    });
    var mydata = [
        { username1: "启运", username2: new Date(), username3: "", username4: ""},
        { username1: "途中", username2: new Date(), username3: "", username4: ""},
        { username1: "到达", username2: new Date(), username3: "", username4: ""},

    ]
    $("#tempTransport").jqGrid({
        // url: '../work/list?',
        datatype: "local",
        data: mydata,
        colNames:['项目','日期/时间','疫苗存储温度','环境温度'],
        colModel: [
            { name: 'username1', index: 'username1', width: 80, align: 'center'},
            { name: 'username2', index: 'username2', width: 80, align: 'center'},
            { name: 'username3', index: 'username3', width: 80, align: 'center'},
            { name: 'username4', index: 'username4', width: 80, align: 'center'},
            // { label: '项目', name: 'username1', width: 130, key: true },
            // { label: '日期/时间', name: 'username2', width:80 },
            // { label: '疫苗存储温度', name: 'username3', width: 80 },
            // { label: '环境温度', name: 'username4', width: 80 },
        ],
        viewrecords: true,
        height: 'auto',
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
            $("#vacTransport").closest(".ui-jqgrid-bdiv").css("overflow-x","hidden");
        },
        onSelectRow:function (rowid,status) {
        }
    });

});
function queryWork() {
    var start=$("#startDate").val();
    var end=$("#endDate").val();
    if (start==undefined||start==null||start==""){
        layer.msg("请选择查询开始时间！")
        return;
    }
    if (end==undefined||end==null||end==""){

        layer.msg("请选择查询结束时间！")
        return;
    }
    $("#satisfiedGrid").jqGrid('setGridParam',{
        postData: {"startDate":start,"endDate":end},
    }).trigger("reloadGrid");
}
function workExcel() {

    window.location.href="../ExcelController/vacTransportTemp";
}
function printWork() {
    var start=$("#startDate").val();
    var end=$("#endDate").val();
    if (start==undefined||start==null||start==""){
        layer.msg("请选择查询开始时间！")
        return;
    }
    if (end==undefined||end==null||end==""){
        layer.msg("请选择查询结束时间！")
        return;
    }
    $("#content").hide();
    $("#content").after("<h1 align='center'>疫苗运输温度记录表</h1>");
    window.focus();
    window.print();
    $("#content").next("h1").remove();
    $("#content").show();
}