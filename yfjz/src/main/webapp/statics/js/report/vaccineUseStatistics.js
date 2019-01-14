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
         url: '../vaccineUse/list?',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 130,hidden:true, key: true },
            { label: '工作台', name: 'towerName', width:80 },
            { label: '医生', name: 'username', width:80 },
            { label: '疫苗名称', name: 'productName', width: 80 },
            { label: '疫苗归属', name: 'belong', width: 80 },
            { label: '批号', name: 'batchnum', width: 80 },
            { label: '失效期', name: 'expiryDate', width: 80 },
            { label: '人份转换', name: 'conversion', width: 80 },
            { label: '领用人份数', name: 'receivePersonAmount', width: 80 },
            { label: '使用人份数', name: 'usePersonAmount', width: 80 },
            { label: '报损人份数', name: 'damagePersonAmount', width: 80 },
            // { label: '剩余人份数', name: 'remainAmount', width: 80 },
            { label: '归还人份数', name: 'returnAmount', width: 80 }
        ],
        viewrecords: true,
        height: 'auto',
        //width:1200,
        rowNum: -1,
        //rowList : [10,30,50],
        // rownumbers: true,
        // rownumWidth: 25,
        autowidth:true,
        multiselect: false,
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
    var ret=validateDate();
    if(ret){
        var start=$("#startDate").val();
        var end=$("#endDate").val();
        $("#satisfiedGrid").jqGrid('setGridParam',{
            postData: {"startDate":start,"endDate":end},
        }).trigger("reloadGrid");
    }

}
function workExcel() {
   var ret=validateDate();
   if(ret){
       var start=$("#startDate").val();
       var end=$("#endDate").val();
        window.location.href="../vaccineUse/excel?startDate="+start+"&endDate="+end;
   }
}
function printWork() {
    var ret=validateDate();
    if(ret){
        var start=$("#startDate").val();
        var end=$("#endDate").val();
        window.open("../vaccineUse/print?startDate="+start+"&endDate="+end,"_blank");
       // window.focus();
        //window.print();
    }

}
function compareDate(date1,date2) {
    var d1 = new Date(date1.replace(/\-/g, "\/"));
    var d2 = new Date(date2.replace(/\-/g, "\/"));
    if(d1 >d2)
    {
        return  true;
    }
    return false;
}

function validateDate(){
    var start=$("#startDate").val();
    var end=$("#endDate").val();
    if (start==undefined||start==null||start==""){
        layer.msg("请选择查询开始时间！")
        return false;
    }
    if (end==undefined||end==null||end==""){

        layer.msg("请选择查询结束时间！")
        return false;
    }
    var ret=compareDate(start,formatDateTime(new Date()));
    if(ret){
        layer.msg("开始时间不能大于当前时间！")
        return false;
    }
    var ret=compareDate(end,formatDateTime(new Date()));
    if(ret){
        layer.msg("结束时间不能大于当前时间！")
        return false;
    }
    var ret=compareDate(start,end);
    if(ret)
    {
        alert("开始时间不能大于结束时间！");
        return false;
    }
    return true;
}