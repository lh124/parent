$(function () {
    $("#startDate").val(formatDateTime(new Date()));
    $("#endDate").val(formatDateTime(new Date()));
    $("#serviceGrid").jqGrid({
        url: '../childService/list',
        datatype: "json",
        colModel: [
            { label: '儿童编码', name: 'childId', width: 100, key: true},
            { label: '儿童姓名', name: 'childName', width: 70},
            { label: '取号时间', name: 'printCodeTime', width: 130},
            { label: '取号号码', name: 'number', width: 80},
            { label: '登记时间', name: 'registerTime', width: 130},
            { label: '登记医生', name: 'registerDoctor', width: 80},
            { label: '登记疫苗', name: 'registerVaccine', width: 80 },
            // { label: '取消登记', name: 'cancelRegister', width: 80  },
            { label: '取消登记原因', name: 'cancelReason', width: 80 },
            { label: '接种时间', name: 'inoculateTime', width: 130 },
            { label: '接种医生', name: 'inoculateDoctor', width: 80  },
            { label: '接种疫苗', name: 'inoculateVaccine', width: 80 },
            { label: '疫苗批号', name: 'inoculateBatchnum', width: 80 },
            // { label: '取消接种', name: 'inoculateCancel', width: 80 },
            { label: '取消接种原因', name: 'inoculateCancelReason', width: 100 },
            { label: '是否完成留观', name: 'isObserve', width: 60 }
        ],
        viewrecords: true,
        height: 'auto',
        //width:1200,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 45,
        autowidth:true,
        multiselect: false,
        pager: "#servicePage",
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
        gridComplete: function () {
            //隐藏grid底部滚动条
            // $("#serviceGrid").closest(".ui-jqgrid-bdiv").css("overflow-x", "hidden");

        },
        onSelectRow:function (rowid,status) {
        }
    });
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
    $('#startDate').datetimepicker(
        {
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            startView: 'month',
            minView:'month',
            language:  'zh-CN'
        }
    );
})
function queryWork() {
    var ret=validateDate();
    if(ret){
        var start=$("#startDate").val();
        var end=$("#endDate").val();
        var childName=$("#childName").val();
        $("#serviceGrid").jqGrid('setGridParam',{
            postData: {"startDate":start,"endDate":end,"childName":childName},
        }).trigger("reloadGrid");
    }

}
function workExcel() {
    var ret=validateDate();
    if(ret){
        var start=$("#startDate").val();
        var end=$("#endDate").val();
        window.location.href="../childService/excel?startDate="+start+"&endDate="+end;
    }
}
function printWork() {
    var ret=validateDate();
    if(ret){
        var start=$("#startDate").val();
        var end=$("#endDate").val();
        window.open("../childService/print?startDate="+start+"&endDate="+end,"_blank");
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