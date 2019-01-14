var syncdataVm = new Vue({
    el:'#mongorrapp',
    data:{
        showList: true,
        title: null,
        r:null
    },
    methods: {
        //mogodb测试
        mongo:function () {
            $.get("../mongo/mongolist", function(r){
                syncdataVm.r = r;
            });
        }
    }
});