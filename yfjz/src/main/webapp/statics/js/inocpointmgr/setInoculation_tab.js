$(function () {
    //是否启用排队叫号
    $("#queue_type").bootstrapSwitch({
        onText:"启用",
        offText:"停用",
        onColor:"success",
        offColor:"primary",
        size:"small",
        onSwitchChange:function(event,state){
            var value = 1;
            if(state==true){
                value = 0;
            }
            $.ajax({
                url:"../inocpointmgr/setQueuesSatus",
                data:{status:value},
                success:function (result) {
                }
            });
        }
    });
    //是否启用排队叫号
    $("#queue_distinction").bootstrapSwitch({
        onText:"启用",
        offText:"停用",
        onColor:"success",
        offColor:"primary",
        size:"small",
        onSwitchChange:function(event,state){
            var value = 1;
            if(state==true){
                value = 0;
            }
            $.ajax({
                url:"../inocpointmgr/setQueuesDistinction",
                data:{status:value},
                success:function (result) {
                    if(result.code ==0){

                    }else {
                        $('#queue_distinction').bootstrapSwitch('state', !state);
                        layer.msg(result.msg);
                    }
                }
            });
        }
    });

    initSysConfigData = function() {
        //排队叫号
        $.ajax({
            url:"../inocpointmgr/getQueuesSatus",
            success:function (data) {
                if(data.code == 0){
                    if(data.typeValue == 0){
                        $('#queue_type').bootstrapSwitch('state', true);
                    }else {
                        $('#queue_type').bootstrapSwitch('state', false);
                    }
                }else {
                    $('#queue_type').bootstrapSwitch('state', true);
                }
            }
        });
        //分队列
        $.ajax({
            url:"../inocpointmgr/getQueuesDistinction",
            success:function (data) {
                if(data.code == 0){
                    if(data.typeValue == 0){
                        $('#queue_distinction').bootstrapSwitch('state', true);
                    }else {
                        $('#queue_distinction').bootstrapSwitch('state', false);
                    }
                }else {
                    $('#queue_distinction').bootstrapSwitch('state', true);
                }
            }
        });
    };
    //初始化
    $("#setSysConfigTables li").on("click",function () {
        var current=$(this).find("a").html();
        if(current=='基础配置'){
            setTimeout(function () {
                initSysConfigData();
            },200);
        }
    });
});
