var syncdataVm = new Vue({
    el:'#syncdatarrapp',
    data:{
        showList: true,
        title: null
    },
    methods: {
        //同步儿童基本信息
        syncChildData:function () {
            layer.msg('同步数据中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false, time:100000}) ;

            $.get("../sync/syncData?type=1", function(r){
                layer.msg(r.msg);
            });
        },
        //同步儿童的历史接种记录
        syncInoculationData:function () {
            layer.msg('同步数据中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false, time:100000}) ;
            $.get("../sync/syncData?type=2", function(r){
                layer.msg(r.msg);
            });
        }
    }
});