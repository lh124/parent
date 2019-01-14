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
var sequenceNoId = getUrlVars()["sequenceNoId"];
var flag;
$(function () {
    $("#jqGrid").jqGrid({
        url: '../tchildprecheck/list?chilCode='+chilCode,
        datatype: "json",
        colModel: [
            // { label: 'id', name: 'id', width: 50, key: true },
            { label: '儿童编号', name: 'chilCode', width: 80 },
            { label: '体温', name: 'temp', width: 80 },
            { label: '体检结果', name: 'result', width: 80 ,
                formatter: function (r) {
                    if(r == 0){
                        return '合格';
                    }else if(r ==1){
                        return '不合格';
                    }
                }
            },
            { label: '备注', name: 'remark', width: 80 },
            { label: '创建时间', name: 'createTime', width: 80 },
            // { label: '状态,0正常,1停用', name: 'status', width: 80 },
            // { label: '删除状态：0：正常；1：删除', name: 'deleteStatus', width: 80 },
            // { label: '创建人id', name: 'createUserId', width: 80 },
            // { label: '创建人名字', name: 'createUserName', width: 80 },
            // { label: '机构编码', name: 'orgId', width: 80 },
            // { label: '同步状态,0未同步;1同步', name: 'syncstatus', width: 80 },
            // { label: '是否来源平台 0:本地 ，1：平台', name: 'type', width: 80 },

        ],
        viewrecords: true,
        height: 450,
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
            chilCode:chilCode,
            chilName:chilName
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });

    $('#precheckForm').bootstrapValidator({
        live: 'enabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            temp: {
                validators: {
                    regexp: {
                        regexp: /^\d+(?=\.{0,1}\d+$|$)/,
                        message: '只能输入数字'
                    },
                    notEmpty: {
                        message: '体检温度不能为空'
                    }
                }
            },
            result: {
                validators: {
                    notEmpty: {
                        message: '体检结果不能为空'
                    },
                }
            },
            remark: {
                validators: {
                    notEmpty: {
                        message: '原因不能为空'
                    }
                }
            }
        }
    });

});

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        tChildPrecheck: {},
        sequenceNoId: null,//被呼叫的队列号的id
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            document.getElementById("yjbtn").disabled=false;//启用
            if(!sequenceNoId){
                alert("请呼叫号码或选择儿童");
                return ;
            }
            vm.showList = false;
            vm.title = "新增";
            vm.tChildPrecheck = {
                chilCode:chilCode,
                result:$('#result option:selected').val()
            };
        },
        update: function (event) {
            document.getElementById("yjbtn").disabled=false;//启用
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            flag=true;
            $("#precheckForm").bootstrapValidator('validate');//提交验证
            if ($("#precheckForm").data('bootstrapValidator').isValid()) {
                if (flag) {
                    layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 500});
                    var url = vm.tChildPrecheck.id == null ? "../tchildprecheck/save" : "../tchildprecheck/update";
                    $.ajax({
                        type: "POST",
                        url: url,
                        data: JSON.stringify(vm.tChildPrecheck),
                        contentType: 'application/json;charset=UTF-8',
                        success: function (r) {
                            flag=false;
                            document.getElementById("yjbtn").disabled = true;//禁用
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
                    url: "../tchildprecheck/delete",
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
            $.get("../tchildprecheck/info/"+id, function(r){
                vm.tChildPrecheck = r.tChildPrecheck;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        }
    }
});
