$(function () {

    $("#startDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy',//显示格式
        minView: 4,//设置只显示到月份
        maxView: 4,//设置只显示到月份
        startView: 4,
        autoclose: true, //选择完时间后自动关闭，默认false（不关闭）
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    $("#endDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'mm',//显示格式
        minView: 3,//设置只显示到月份
        maxView: 3,//设置只显示到月份
        startView: 3,
        autoclose: true, //选择完时间后自动关闭，默认false（不关闭）
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    //设置本年 当月
    var now= formatDateTime(new Date());
    $("#startDate").val(now.substring(0,4));
    $("#endDate").val(now.substring(5,7));
    var end=$('#endDate').val();
    var start= $("#startDate").val();
    $("#vaccineChangeGrid").jqGrid({
        url: '../vaccineChange/list?year='+start+"&month="+end,
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 130, key: true,hidden:true },
            { label: '疫苗/产品名称', name: 'productName', width: 130},
            { label: '生产厂家', name: 'factoryName', width:80 },
            { label: '批号', name: 'batchnum', width: 80},
            { label: '失效期', name: 'expiryDate', width: 80},
            { label: '人份转换', name: 'conversion', width: 80},
            { label: '上月结余', name: 'initAmount', width: 80},
            { label: '入库数量', name: 'inputAmount', width: 80 },
            { label: '报损数量', name: 'damageAmount', width: 80  },
            { label: '退货数量', name: 'returnAmount', width: 80 },
            { label: '使用数量（含损耗数量）', name: 'useAmount', width: 80 },
            { label: '使用人份', name: 'usePersonAmount', width: 80  },
            { label: '损耗人份', name: 'damagePersonAmount', width: 80 },
            { label: '本月结余', name: 'endAmount', width: 80 }
           /* { label: '一般数量', name: 'commonly', width: 80,cellattr: function (rowId, tv, rawObject, cm, rdata) {
                    //合并单元格
                    return 'id=\'commonly' + rowId + "\'";

                } },*/
        ],
        viewrecords: true,
        height: 'auto',
        //width:1200,
        rowNum: 1000,
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
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#vaccineChangeGrid").closest(".ui-jqgrid-bdiv").css("overflow-x", "hidden");

        },
        onSelectRow:function (rowid,status) {
        }
    });
    $("#vaccineChangeGrid").jqGrid('setGroupHeaders', {
        useColSpanStyle: true,
        groupHeaders:[
            {startColumnName:'inputAmount', numberOfColumns:6, titleText: '本月发生'},
        ]
    })
})
function queryVaccine() {
    var end=$('#endDate').val();
    var start= $("#startDate").val();
    if (start==undefined||start==null||start==""){
        layer.msg("请选择年份！")
        return;
    }
    if (end==undefined||end==null||end==""){

        layer.msg("请选择月份！")
        return;
    }
    $("#vaccineChangeGrid").jqGrid('setGridParam',{
        url:'../vaccineChange/list',
        postData: {"year":start,"month":end},
    }).trigger("reloadGrid");
}
function vaccineExcel() {
    var end=$('#endDate').val();
    var start= $("#startDate").val();
    if (start==undefined||start==null||start==""){
        layer.msg("请选择年份！")
        return;
    }
    if (end==undefined||end==null||end==""){

        layer.msg("请选择月份！")
        return;
    }

    window.location.href="../vaccineChange/excel?year="+start+"&month="+end+"&page=1&limit=10000";
}
function printVaccine() {
    var end=$('#endDate').val();
    var start= $("#startDate").val();
    if (start==undefined||start==null||start==""){
        layer.msg("请选择年份！")
        return;
    }
    if (end==undefined||end==null||end==""){

        layer.msg("请选择月份！")
        return;
    }

    window.open("../vaccineChange/print?year="+start+"&month="+end,"_blank");
    
}

