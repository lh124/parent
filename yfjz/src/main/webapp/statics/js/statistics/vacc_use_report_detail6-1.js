$(function () {

    $("#vacc_use_report_detail6-1").jqGrid({
        //url: '../inoStatistics/inoculationRate',
        datatype: "json",
        colModel: [
            {label: '疫苗', name: 'ym', width: 120, align: "center"},
            {label: '剂次', name: 'jc', width: 50, align: "center"},
            {label: '应种剂次数', name: 'ydjcs', width: 80, align: "center"},
            {label: '实种剂次数', name: 'sdjcs', width: 80, align: "center"},
            {label: '应种剂次数', name: 'ydjcs2', width: 80, align: "center"},
            {label: '实种剂次数', name: 'sdjcs2', width: 80, align: "center"},
        ],
        viewrecords: true,
        height: 'auto',
        rowNum: -1,
        //rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 50,
        autowidth: true,
        //multiselect: true,
        // pager: "#jqGridPager",
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
            //隐藏grid底部滚动条
            $("#vacc_use_report_detail6-1").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $("#vacc_use_report_detail6-1").jqGrid('setGroupHeaders', {
        useColSpanStyle: true,
        groupHeaders: [
            {startColumnName: 'ydjcs', numberOfColumns: 2, titleText: '<em>本地</em>'},
            {startColumnName: 'ydjcs2', numberOfColumns: 2, titleText: '<em>流动</em>'}
        ]
    });
    $("#vacc_use_report_detail6-1").jqGrid('setLabel', 'rn', '序号', {'text-align': 'left','vertical-align': 'middle'}, '');

    var myDate = new Date();
    var day = myDate.getMonth() + 1; //获取当前月份(0-11,0代表1月)
    if(day.length == 1){
        day = "0" + day;
    }
    $('#month').val(day);

    /*//layui加载时间组件
    layui.use('laydate', function() {
        var laydate = layui.laydate;

        //年选择器
        laydate.render({
            elem: '#yearDate',
            type: 'year',
            value: new Date(),
            format: 'yyyy',
            isInitValue: true
        });

    });*/

    //开始时间
    $("#yearDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: 2,//设置只显示到月份
        maxView: 4,//设置只显示到月份
        startView: 2,
        autoclose: true, //选择完时间后自动关闭，默认false（不关闭）
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    //结束时间
    $("#monthDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd',//显示格式
        minView: 2,//设置只显示到月份
        maxView: 4,//设置只显示到月份
        startView: 2,
        autoclose: true, //选择完时间后自动关闭，默认false（不关闭）
        forceParse: true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    });
    //设置本年 当月 当天
    var now= formatDateTime(new Date());
    var date1 = formatDateTime(new Date().setDate(1));
    $("#yearDate").val(date1.substring(0,10));
    $("#monthDate").val(now.substring(0,10));
});
var vm;
vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        listData:null
    },
    methods: {

        query: function () {
            vm.reloadData();
        },
        excel: function () {
            var year= $("#yearDate").val();
            var month=$("#monthDate").val();
            window.location.href='../sixToOne/totalExcel?year='+year+"&month="+month;
        },
        print: function () {
            var year= $("#yearDate").val();
            var month=$("#monthDate").val();
            //window.location.href='../sixToOne/totalPrint?year='+year+"&month="+month;
            window.open('../sixToOne/totalPrint?year='+year+"&month="+month,"_blank");

        },
        reloadData: function () {
            layer.msg('加载数据中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false, time:100000}) ;
            var year= $("#yearDate").val();
            var month=$("#monthDate").val();
            var res=$("#residence option:selected").val();
            $.ajax({
                url:"../sixToOne/totalList",
                type:"get",
                data:{"year":year,"month":month,"residence":res},
                success:function (data) {
                    vm.listData=data.page;
                    layer.msg("加载完成！") ;
                }
            })
        },
        reset: function () {
            var year= $("#yearDate").val("");
            var month=$("#monthDate").val("");
        }
    }
});
