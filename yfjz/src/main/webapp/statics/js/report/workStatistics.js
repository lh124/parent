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
    $("#satisfiedGrid").jqGrid({
         url: '../work/list?',
        datatype: "json",
        colModel: [
            { label: '工作人员', name: 'realName', width: 130, key: true },
            { label: '工作台', name: 'towerName', width:80 },
            { label: '完成数', name: 'finish', width: 80 },
            { label: '取消数', name: 'cancel', width: 80 },
            { label: '非常满意数量', name: 'verySatisfied', width: 80 },
            { label: '满意数量', name: 'satisfied', width: 80 },
            { label: '一般数量', name: 'commonly', width: 80 },
            { label: '不满意数量', name: 'dissatisfied', width: 80 },
            { label: '满意率', name: 'satisfiedRate', width: 60}
        ],
        viewrecords: true,
        height: 'auto',
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
            $("#satisfiedGrid").closest(".ui-jqgrid-bdiv").css("overflow-x","hidden");
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
    window.location.href="../work/excel?startDate="+start+"&endDate="+end;
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
    $("#content").after("<h1 align='center'>工作量统计</h1>");
    window.focus();
    window.print();
    $("#content").next("h1").remove();
    $("#content").show();
}