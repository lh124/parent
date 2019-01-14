
$(function () {
    $("#notification1").bootstrapSwitch({
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
                url:"../inocpointmgr/offernumberStatus",
                data:{status:value},
                success:function (result) {
                    if(result.code == 0){
                        if(!state){
                            unDisabledSetting();
                        }else {
                            disabledSetting();
                        }
                    }else{
                        alert(r.msg);
                    }
                }
            });
        }
    });

    //禁用编辑
    disabledSetting = function () {
        $("#mAmount").attr("disabled","disabled");
        $("#mStime").attr("disabled","disabled");
        $("#mEtime").attr("disabled","disabled");
        $("#aAmount").attr("disabled","disabled");
        $("#aStime").attr("disabled","disabled");
        $("#aEtime").attr("disabled","disabled");
        $("#submitNumber").attr('disabled',true);
    };
    //启用编辑
    unDisabledSetting = function () {
        $("#mAmount").removeAttr("disabled");
        $("#mStime").removeAttr("disabled");
        $("#mEtime").removeAttr("disabled");
        $("#aAmount").removeAttr("disabled");
        $("#aStime").removeAttr("disabled");
        $("#aEtime").removeAttr("disabled");
        $("#submitNumber").removeAttr("disabled");
    };

    //提交设置到后端保存
    submitNumber = function () {
        if (parseInt($("#mAmount").val())<0){
            layer.msg("上午取号数不能为负数");
            return false;
        }
        if (parseInt($("#aAmount").val())<0){
            layer.msg("下午取号数不能为负数");
            return false;
        }
        if (($("#mAmount").val()==null  || $("#mAmount").val() == "")){
            layer.msg("请设置上午取号数");
            return false;
        }
        if ($("#mStime").val()==null || $("#mStime").val()==""){
            layer.msg("请设置上午开始时间");
            return false;
        }
        if ($("#mEtime").val()==null || $("#mEtime").val()==""){
            layer.msg("请设置上午结束时间");
            return false;
        }
        if (($("#aAmount").val()==null || $("#aAmount").val()=="")){
            layer.msg("请设置下午午取号数");
            return false;
        }
        if ($("#aStime").val()==null || $("#aStime").val()==""){
            layer.msg("请设置下午开始时间");
            return false;
        }
        if ($("#aEtime").val()==null || $("#aEtime").val()==""){
            layer.msg("请设置下午结束时间");
            return false;
        }
        var data = $("#offernumberForm").serialize();
        $.ajax({
            url:"../inocpointmgr/offernumberSave?"+data,
            success:function (result) {
                if(result.code == 0){
                    alert('保存成功');
                }else{
                    alert(r.msg);
                }
            }
        });
    };

    $("#submitNumber").click(function(){
        submitNumber();
    });
    initNumberData = function () {
        $.ajax({
            url:"../inocpointmgr/offernumberInfo",
            success:function (data) {
                if(data.code == 0){
                    $("#mAmount").val(data.tBaseGetnums.m_amount);
                    $("#mStime").val(data.tBaseGetnums.m_stime);
                    $("#mEtime").val(data.tBaseGetnums.m_etime);
                    $("#aAmount").val(data.tBaseGetnums.a_amount);
                    $("#aStime").val(data.tBaseGetnums.a_stime);
                    $("#aEtime").val(data.tBaseGetnums.a_etime);
                    if(data.tBaseGetnums.status ==0){
                        $('#notification1').bootstrapSwitch('state', true);
                        disabledSetting();
                    }else {
                        $('#notification1').bootstrapSwitch('state', false);
                        unDisabledSetting();
                    }
                }else {
                    $('#notification1').bootstrapSwitch('state', true);
                    unDisabledSetting();
                }
            }
        });
    };
    //初始化
    initNumberData();
});
