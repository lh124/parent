$(function () {
    $("#ipForm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            barCodeIp: {
                validators: {
                    notEmpty: {
                        message: 'IP地址不能为空'
                    },
                    ip: {
                        message: 'ip地址不正确，例如：192.168.3.12'
                    }
                }
            }
        }
    });
    $.ajax({
        url:"../configuration/queryConfiguration?type=barCodeIpAddress",
        type: 'get',
        success: function (data) {
           var ret=data.data;
            $("#barCodeIp").val(ret.remark);
            if(ret.id!=undefined||ret.id!=null||ret.id!=""){
                $("#dictId").val(ret.id);
            }
        }
    })
})
function saveIpAdress() {
    var ip=$("#barCodeIp").val().trim();
    var dictId=$("#dictId").val().trim();
    $("#ipForm").bootstrapValidator('validate');//提交验证
    if ($("#ipForm").data('bootstrapValidator').isValid()) {
        $.ajax({
            url:"../inocpointmgr/updateBarCodeIp",
            type: 'get',
            dataType:"json",
            data:{"ipAddress":ip,"dictId":dictId},
            success: function (data) {
                if (data.code==0){
                    layer.msg(data.msg);
                    $("#barCodeIp").val(ip)
                }else{
                    layer.msg(data.msg);
                }
                $('#ipForm').data('bootstrapValidator').resetForm();
            }
        })
    }

}