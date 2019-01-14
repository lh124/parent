
function getUrlVars() {
    var vars = [],
        hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for (var i = 0; i < hashes.length; i++) {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}
var chilCode = getUrlVars()["chilCode"];
var chilName = getUrlVars()["chilName"];
$(function () {
    $("#jqGrid").jqGrid({
        url: '../tchildmove/list',
        datatype: "json",
        colModel: [
            { label: '现在册情况', name: 'chheHere', width: 80 },
            { label: '原在册情况', name: 'chhePrehere', width: 80 },
            /*{ label: '出省状态', name: 'chheChilProvince', width: 80 },*/
            { label: '变更时间', name: 'chheChangedate', width: 150 },
            { label: '变更单位', name: 'chheDepaId', width: 180 },
            { label: '变更原因', name: 'chheChilProvinceremark', width: 80 },
            // { label: '是否来源平台', name: 'type', width: 80,
            //     formatter: function (r) {
            //         if(r == 0){
            //             return '本地';
            //         }else if(r ==1){
            //             return '平台';
            //         }else{
            //             return "未知";
            //         }
            //     }
            // },
            // { label: '同步状态,0未同步;1同步', name: 'syncstatus', width: 80 ,
            //     formatter: function (r) {
            //         if(r == 0){
            //             return '未同步';
            //         }else if(r ==1){
            //             return '同步';
            //         }else{
            //             return "未知";
            //         }
            //     }
            // },
            // { label: '状态：0：正常；-1：删除', name: 'status', width: 80 },
            // { label: '创建人id', name: 'createUserId', width: 80 },
            // { label: '创建人名字', name: 'createUserName', width: 80 },
            // { label: '创建时间', name: 'createTime', width: 80 },
            // { label: '机构编码', name: 'orgId', width: 80 }
        ],
        viewrecords: true,
        height: 600,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
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
        postData:{
            "chilCode": $("#chilCode").val(),
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    vm.chheChangedate();
    $('#moveForm').bootstrapValidator({
        live: 'enabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            chheHere: {
                validators: {
                    notEmpty: {
                        message: '在册状态不能为空'
                    }
                }
            },
            chheChilProvince: {
                validators: {
                    notEmpty: {
                        message: '出省状态不能为空'
                    }
                }
            },
            chheChilProvinceremark: {
                validators: {
                    notEmpty: {
                        message: '变更原因不能为空'
                    }
                }
            },
        }
    });
    vm.loadData();
});

function loadTypeData(listType,url) {
    var param = new Array();//定义数组
    $.ajax({
        // get请求地址
        url: url,
        dataType: "json",
        success: function (data) {
            var result = data.data;
            $.each(result, function (index, item) {
                param.push({"text": item.text, "value": item.value});
            });
            vm[listType] = param;
        }
    });

};
var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            chilCode:null,
        },
        showList: true,
        title: null,
        tChildMove: {},
        infostatus:[],
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            if(!chilCode){
                alert("请选择儿童");
                return ;
            }
            vm.showList = false;
            vm.title = "新增";
            vm.tChildMove = { chilCode:chilCode};
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        loadData : function(){
            loadTypeData("infostatus","../child/listdiccode?ttype=child_info_status");
        },
        chheChangedate:function(){
            $('#datetimepicker1').datetimepicker({
                language: 'zh-CN',//显示中文
                format: 'yyyy-mm-dd hh:ii:ss',//显示格式
                minView: "month",//设置只显示到月份
                initialDate: new Date(),
                autoclose: true,//选中自动关闭
                todayBtn: true,//显示今日按钮
                // locale: moment.locale('zh-cn')
            });
            //默认获取当前日期
            var today = new Date();
            var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-" + today.getDate();
            //对日期格式进行处理
            var date = new Date(nowdate);
            var mon = date.getMonth() + 1;
            var day = date.getDate();
            var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day);
            document.getElementById("chheChangedate").value = mydate;

        },
        saveOrUpdate: function (event) {

            var chheChangedate=$("#chheChangedate").val();
            var chilCode=$("#chilCode").val();

            Vue.set(vm.tChildMove,'chheChangedate',chheChangedate);
            Vue.set(vm.tChildMove,'chilCode',chilCode);
            $("#moveForm").bootstrapValidator('validate');//提交验证
            if ($("#moveForm").data('bootstrapValidator').isValid()) {
                layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
                var url = vm.tChildMove.id == null ? "../tchildmove/save" : "../tchildmove/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.tChildMove),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            }
        },
        del: function (event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: "../tchildmove/delete",
                    data: JSON.stringify(ids),
                    contentType:'application/json;charset=UTF-8',
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function(id){
            $.get("../tchildmove/info/"+id, function(r){
                vm.tChildMove = r.tChildMove;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'chilCode':vm.q.chilCode},
                page:page
            }).trigger("reloadGrid");
        }
    }
});