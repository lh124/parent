function tt2() {
    //未登记人数
    $("#dj_content1").jqGrid({
        datatype: "local",
        // datatype: "json",
        colModel: [
            {label: '登记台', name: 'noText', key: true,  width: 220},
            {label: '待登记人数', name: 'childCode',   width: 220},
        ],
        // height: 200,
        viewrecords: true,
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
    });

    //已登记人数
    $("#dj_content2").jqGrid({
        datatype: "local",
        colModel: [
            {label: '登记台', name: 'noText', key: true,  width: 220},
            {label: '完成登记人数', name: 'childCode',   width: 220},

        ],
        viewrecords: true,
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
    });

    //未接种人数
    $("#jz_content3").jqGrid({
        datatype: "local",
        colModel: [
            {label: '接种台', name: 'childCode',   width: 220},
            {label: '等待人数', name: 'position',   width: 220},
        ],
        viewrecords: true,
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,
    });
    //已接种人数
    $("#jz_content4").jqGrid({
        datatype: "local",
        // datatype: "json",
        colModel: [
            {label: '接种台', name: 'chilCode',   width: 220},
            {label: '完成人数', name: 'chilName',   width: 220},
        ],
        viewrecords: true,
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,
    });
    //已留观人数
    $("#lg_content5").jqGrid({
        datatype: "local",
        // datatype: "json",
        colModel: [
            {label: '姓名', name: 'chilCode',   width: 110},
            {label: '疫苗名称', name: 'inocBactCode',   width: 110},
            {label: '疫苗批号', name: 'inocBatchno',   width: 110},
            {label: '接种时间', name: 'inocDate',   width: 110},
        ],
        viewrecords: true,
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,
    });
    //未留观人数
    $("#lg_content6").jqGrid({
        datatype: "local",
        // datatype: "json",
        colModel: [
            {label: '姓名', name: 'chilCode',   width: 110},
            {label: '疫苗名称', name: 'inocBactCode',   width: 110},
            {label: '疫苗批号', name: 'inocBatchno',   width: 110},
            {label: '接种时间', name: 'inocDate',   width: 110},
        ],
        viewrecords: true,
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,
    });
    //待儿保人数
    $("#eb_content7").jqGrid({
        datatype: "local",
        // datatype: "json",
        colModel: [
            {label: '儿保台', name: 'noText',   width: 220},
            {label: '待儿保人数', name: 'childCode',   width: 220},
        ],
        viewrecords: true,
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,
    });

    //未预检人数
    $("#yj_content8").jqGrid({
        datatype: "local",
        // datatype: "json",
        colModel: [
            {label: '预检台', name: 'noText',   width: 220},
            {label: '待预检人数', name: 'childCode',   width: 220},
        ],
        viewrecords: true,
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,
    });
    // //预检
    $("#yj_content10").jqGrid({
        datatype: "local",
        // datatype: "json",
        colModel: [
            {label: '预检台', name: 'remark',   width: 220},
            {label: '预检完成数', name: 'chilCode',   width: 220},
        ],
        viewrecords: true,
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,

    });
    //儿保
    $("#eb_content9").jqGrid({
        datatype: "local",
        // datatype: "json",
        colModel: [
            {label: '儿保台', name: 'remark',   width: 220},

            {label: '完成儿保数', name: 'chilCode',   width: 220},
        ],
        viewrecords: true,
        rownumbers: true,
        rownumWidth: 40,
        autowidth: true,
    });

    $.ajax({
        type:"post",
        url: '../tbusregister/listregister',
        datatype: "json",
        success:function (data) {
            console.log(data);
            $("#dj_content1").jqGrid("clearGridData");
            $("#dj_content2").jqGrid("clearGridData");
            $("#jz_content3").jqGrid("clearGridData");
            $("#jz_content4").jqGrid("clearGridData");
            $("#lg_content5").jqGrid("clearGridData");
            $("#lg_content6").jqGrid("clearGridData");
            $("#eb_content7").jqGrid("clearGridData");
            $("#yj_content8").jqGrid("clearGridData");
            $("#eb_content9").jqGrid("clearGridData");
            $("#yj_content10").jqGrid("clearGridData");
            $("#dj_content1").setGridParam({datatype : 'local',data: data.data.list1}).trigger('reloadGrid');
            $("#dj_content2").setGridParam({datatype : 'local',data: data.data.list2}).trigger('reloadGrid');
            $("#jz_content3").setGridParam({datatype : 'local',data: data.data.list4}).trigger('reloadGrid');
            $("#jz_content4").setGridParam({datatype : 'local',data: data.data.list3}).trigger('reloadGrid');
            $("#lg_content5").setGridParam({datatype : 'local',data: data.data.list5}).trigger('reloadGrid');
            $("#lg_content6").setGridParam({datatype : 'local',data: data.data.list6}).trigger('reloadGrid');
            $("#eb_content7").setGridParam({datatype : 'local',data: data.data.list8}).trigger('reloadGrid');
            $("#yj_content8").setGridParam({datatype : 'local',data: data.data.list7}).trigger('reloadGrid');
            $("#eb_content9").setGridParam({datatype : 'local',data: data.data.list10}).trigger('reloadGrid');
            $("#yj_content10").setGridParam({datatype : 'local',data: data.data.list9}).trigger('reloadGrid');


        }
    });
}
function towerregister(){
    layer.open({
        title: '登记',
        skin: 'tmpl',
        type: 1,
        shadeClose: true,
        closeBtn: 1,//取消右上角的x关闭按钮
        area: ['500px', '400px'],
        content: $("#dj_myModal1"),
        success: function () {
            //表格只能在弹框之后加载，否则无效
            tt2();
            $(".layui-layer-page").css("z-index","198910151");
        },
        end:function () {
            $(".layui-layer-shade").css("display","none");
        }
    });
}
// function inoculatebtn(){
//     layer.open({
//         title: '接种',
//         skin: 'tmpl',
//         type: 1,
//         // closeBtn: 1,//取消右上角的x关闭按钮
//         area: ['500px', '400px'],
//         content: $("#jz_myModal2"),
//         success: function () {
//             //表格只能在弹框之后加载，否则无效
//             tt2();
//             $(".layui-layer-page").css("z-index","198910151");
//
//         },
//         end:function () {
//             $(".layui-layer-shade").css("display","none");
//         }
//     });
// }
function healthcarebtn(){
    layer.open({
        title: '儿保',
        skin: 'tmpl',
        type: 1,
        shadeClose: true,
        closeBtn: 1,//取消右上角的x关闭按钮
        area: ['500px', '400px'],
        content: $("#eb_myModal3"),
        success: function () {
            //表格只能在弹框之后加载，否则无效
            tt2();
            $(".layui-layer-page").css("z-index","198910151");

        },
        end:function () {
            $(".layui-layer-shade").css("display","none");
        }
    });
}
function precheckbtn(){
    layer.open({
        title: '预检',
        skin: 'tmpl',
        type: 1,
        shadeClose: true,
        closeBtn: 1,//取消右上角的x关闭按钮
        area: ['500px', '400px'],
        content: $("#yj_myModal4"),
        success: function () {
            //表格只能在弹框之后加载，否则无效
            tt2();
            $(".layui-layer-page").css("z-index","198910151");

        },
        end:function () {
            $(".layui-layer-shade").css("display","none");

        }
    });
}
function observebtn(){
    layer.open({
        title: '留观',
        skin: 'tmpl',
        type: 1,
        shadeClose: true,
        closeBtn: 1,//取消右上角的x关闭按钮
        area: ['500px', '400px'],
        content: $("#lg_myModal5"),
        success: function () {
            //表格只能在弹框之后加载，否则无效
            tt2();
            $(".layui-layer-page").css("z-index","198910151");

        },
        end:function () {
            $(".layui-layer-shade").css("display","none");
        }
    });
}


function formatStr(str) {
    str = str + '';
    var len = str.length;
    if(len < 3) {
        for (var i = len ; i < 3 ; i++) {
            str = '0' + str;
        }
    }
    return str;
}
function Registrationmonitoring() {
    var self = this;
    //定时任务 5秒刷新一下
    // window.setTimeout(this.Registrationmonitoring, 5000);
    $.ajax({
        type: "post",
        url: "../tbusregister/RegistrationMonitoring",
        dataType: 'json',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        success: function (data) {
            console.log(data);
            if ($("#yj01").val() == 'precheck') {
                console.log("precheck");
                var str = '<i class="play-top"></i>';
                var next = '<i class="play-next"></i>';
                var html = str+'<div class="row-box-items box-items-qh box-items-width"><span class="cs-begin">' + self.formatStr(data.data.number) + '</span></div>';
                for (var i = 0; i < data.data.tower.length; i++) {
                    if (data.data.tower[i].towerNatureId == 1) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-dj " id="towerregister" data-target="#dj_myModal1" onclick="towerregister()"><span class="s1" id="dj_s1">' + self.formatStr(data.data.todaysumregister) + '</span><span id="dj_s2" class="s2">' + self.formatStr(data.data.sumregister) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 2) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-jz" id="inoculatebtn" data-target="#jz_myModal2" ><span class="s1">' + self.formatStr(data.data.noinoculate) + '</span><span class="s2">' + self.formatStr(data.data.suminoculate) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 3) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-eb" id="healthcarebtn" data-target="#eb_myModal3" onclick="healthcarebtn()"><span class="s1">' + self.formatStr(data.data.sumnohealthcare) + '</span><span class="s2">' + self.formatStr(data.data.sumhealthcare) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 4) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-yj box-items-yj-now" id="precheckbtn" data-target="#yj_myModal4" onclick="precheckbtn()"><span class="s1">' + self.formatStr(data.data.sumnoprecheck) + '</span><span class="s2">' + self.formatStr(data.data.sumprecheck) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 5) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-lg" id="observebtn" data-target="#lg_myModal5" ><span class="s1">' + self.formatStr(data.data.sumobserve) + '</span><span class="s2">' + self.formatStr(data.data.observelist) + '</span></div>'
                    }

                    // if (i != data.data.tower.length - 1) {
                    //     html += str;
                    // }
                }
                $("#navigationMonitoring").html(html);
            }else if ($("#eb01").val() == 'healthcare') {
                console.log("healthcare");
                var str = '<i class="play-top"></i>';
                var next = '<i class="play-next"></i>';
                var html = str+'<div class="row-box-items box-items-qh box-items-width"><span class="cs-begin">' + self.formatStr(data.data.number) + '</span></div>';
                for (var i = 0; i < data.data.tower.length; i++) {
                    if (data.data.tower[i].towerNatureId == 1) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-dj " id="towerregister" data-target="#dj_myModal1" onclick="towerregister()"><span class="s1">' + self.formatStr(data.data.todaysumregister) + '</span><span class="s2">' + self.formatStr(data.data.sumregister) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 2) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-jz" id="inoculatebtn" data-target="#jz_myModal2" ><span class="s1">' + self.formatStr(data.data.noinoculate) + '</span><span class="s2">' + self.formatStr(data.data.suminoculate) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 3) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-eb box-items-eb-now" id="healthcarebtn" data-target="#eb_myModal3" onclick="healthcarebtn()"><span class="s1">' + self.formatStr(data.data.sumnohealthcare) + '</span><span class="s2">' + self.formatStr(data.data.sumhealthcare) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 4) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-yj" id="precheckbtn" data-target="#yj_myModal4" onclick="precheckbtn()"><span class="s1">' + self.formatStr(data.data.sumnoprecheck) + '</span><span class="s2">' + self.formatStr(data.data.sumprecheck) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 5) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-lg" id="observebtn" data-target="#lg_myModal5" ><span class="s1">' + self.formatStr(data.data.sumobserve) + '</span><span class="s2">' + self.formatStr(data.data.observelist) + '</span></div>'
                    }

                    // if (i != data.data.tower.length - 1) {
                    //     html += str;
                    // }
                }
                $("#navigationMonitoring").html(html);
            }else   if ($("#dj01").val() == 'register') {
                console.log("register");
                var str = '<i class="play-top"></i>';
                var next = '<i class="play-next"></i>';
                var html = str + '<div class="row-box-items box-items-qh box-items-width"><span class="cs-begin">' + self.formatStr(data.data.number) + '</span></div>';
                for (var i = 0; i < data.data.tower.length; i++) {
                    if (data.data.tower[i].towerNatureId == 1) {
                        html += next;
                        html += '<div class="row-box-items handpoint box-items-dj box-items-dj-now " id="towerregister" onclick="towerregister()" data-target="#dj_myModal1" ><span class="s1">' + self.formatStr(data.data.todaysumregister) + '</span><span class="s2">' + self.formatStr(data.data.sumregister) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 2) {
                        html += next;
                        html += '<div class="row-box-items handpoint box-items-jz" id="inoculatebtn" data-target="#jz_myModal2" ><span class="s1">' + self.formatStr(data.data.noinoculate) + '</span><span class="s2">' + self.formatStr(data.data.suminoculate) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 3) {
                        html += next;
                        html += '<div class="row-box-items handpoint box-items-eb" id="healthcarebtn" data-target="#eb_myModal3" onclick="healthcarebtn()"><span class="s1">' + self.formatStr(data.data.sumnohealthcare) + '</span><span class="s2">' + self.formatStr(data.data.sumhealthcare) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 4) {
                        html += next;
                        html += '<div class="row-box-items handpoint box-items-yj" id="precheckbtn" data-target="#yj_myModal4" onclick="precheckbtn()"><span class="s1">' + self.formatStr(data.data.sumnoprecheck) + '</span><span class="s2">' + self.formatStr(data.data.sumprecheck) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 5) {
                        html += next;
                        html += '<div class="row-box-items handpoint box-items-lg" id="observebtn" data-target="#lg_myModal5" ><span class="s1">' + self.formatStr(data.data.sumobserve) + '</span><span class="s2">' + self.formatStr(data.data.observelist) + '</span></div>'
                    }
                }
                $("#navigationMonitoring").html(html);
            }else  if ($("#jz01").val() == 'inoculate') {
                console.log("inoculate");
                var str = '<i class="play-top"></i>';
                var next = '<i class="play-next"></i>';
                var html = str+'<div class="row-box-items box-items-qh box-items-width"><span class="cs-begin">' + self.formatStr(data.data.number) + '</span></div>';
                for (var i = 0; i < data.data.tower.length; i++) {
                    if (data.data.tower[i].towerNatureId == 1) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-dj " id="towerregister" data-target="#dj_myModal1" onclick="towerregister()"><span class="s1">' + self.formatStr(data.data.todaysumregister) + '</span><span class="s2">' + self.formatStr(data.data.sumregister) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 2) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-jz box-items-jz-now" id="inoculatebtn" data-target="#jz_myModal2" ><span class="s1">' + self.formatStr(data.data.noinoculate) + '</span><span class="s2">' + self.formatStr(data.data.suminoculate) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 3) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-eb" id="healthcarebtn" data-target="#eb_myModal3" onclick="healthcarebtn()"><span class="s1">' + self.formatStr(data.data.sumnohealthcare) + '</span><span class="s2">' + self.formatStr(data.data.sumhealthcare) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 4) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-yj" id="precheckbtn" data-target="#yj_myModal4" onclick="precheckbtn()"><span class="s1">' + self.formatStr(data.data.sumnoprecheck) + '</span><span class="s2">' + self.formatStr(data.data.sumprecheck) + '</span></div>'
                    }
                    if (data.data.tower[i].towerNatureId == 5) {
                        html+=next;
                        html += '<div class="row-box-items handpoint box-items-lg" id="observebtn" data-target="#lg_myModal5" ><span class="s1">' + self.formatStr(data.data.sumobserve) + '</span><span class="s2">' + self.formatStr(data.data.observelist) + '</span></div>'
                    }

                    // if (i != data.data.tower.length - 1) {
                    //     html += str;
                    // }
                }
                $("#navigationMonitoring").html(html);
            }

        }
    });
}
