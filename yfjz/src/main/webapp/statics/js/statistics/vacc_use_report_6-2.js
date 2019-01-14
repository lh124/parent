$(function () {
    var myDate = new Date();
    var day = myDate.getMonth() + 1; //获取当前月份(0-11,0代表1月)
    if(day.length == 1){
        day = "0" + day;
    }
    $('#monthDate').val(day);

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

    vm.loadData(new Date().getFullYear(),day);




});

var vm = new Vue({
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
            window.location.href='../sixToTwo/excel?year='+year+"&month="+month;
        },
        print: function () {
            var year= $("#yearDate").val();
            var month=$("#monthDate").val();
            window.open('../sixToTwo/print?year='+year+"&month="+month,"_blank");

        },
        reloadData: function () {
            var year= $("#yearDate").val();
            var month=$("#monthDate").val();
            vm.loadData(year,month);

        },
        reset: function () {
            var year= $("#yearDate").val("");
            var month=$("#monthDate").val("");
        },
        loadData:function (year,month) {
            layer.msg('加载数据中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false, time:100000}) ;
            $.ajax({
                url:'../sixToTwo/list',
                dataType:'json',
                type:'get',
                data:{"year":year,"month":month},
                success:function (data) {
                    vm.listData=data.page.list;
                    layer.msg("加载完成！") ;
                }
            })
        },
        upload:function () {
            layer.confirm("你确定要上传该报表吗？",function (index) {
                layer.msg('上传数据中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false, time:100000}) ;
                var year= $("#yearDate").val();
                var month=$("#monthDate").val();
                $.ajax({
                    url:'../sixToTwo/upload',
                    dataType:'json',
                    type:'get',
                    data:{"year":year,"month":month},
                    success:function (data) {
                        layer.msg(data.msg) ;
                        layer.close(index);
                    }
                });
            })

        }
    }
});