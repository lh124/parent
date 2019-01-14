$(function () {
    vm.loadTimePicker();
    vm.reloadData();
})
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
            var res=$("#residence option:selected").val();
            window.location.href='../sixToOne/excel?year='+year+"&residence="+res+"&month="+month;
        },
        print: function () {
            var year= $("#yearDate").val();
            var month=$("#monthDate").val();
            var res=$("#residence option:selected").val();
            //window.location.href='../sixToOne/print?year='+year+"&residence="+res+"&month="+month;
            window.open('../sixToOne/print?year='+year+"&residence="+res+"&month="+month,"_blank");
         
        },
        reloadData: function () {
            layer.msg('加载数据中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false, time:100000}) ;
            var year= $("#yearDate").val();
            var month=$("#monthDate").val();
            var res=$("#residence option:selected").val();
            $.ajax({
                url:"../sixToOne/list",
                type:"get",
                data:{"year":year,"month":month,"residence":res},
                success:function (data) {
                    vm.listData=data.page;
                    layer.msg("加载完成！") ;
                }
            })
        },
        //初始化时间控件
        loadTimePicker: function () {
            //出生日期
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
            //设置本年 当月
           var now= formatDateTime(new Date());
           var date1 = formatDateTime(new Date().setDate(1));
            $("#yearDate").val(date1.substring(0,10));
            $("#monthDate").val(now.substring(0,10));

        },
        reset: function () {
            var year= $("#yearDate").val("");
            var month=$("#monthDate").val("");
        }
    }
});