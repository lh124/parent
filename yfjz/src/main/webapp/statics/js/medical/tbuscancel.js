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
        url: '../tbuscancel/list?chilCode='+chilCode,
        datatype: "json",
        colModel: [
            // { label: 'id', name: 'id', width: 50, key: true },
            { label: '儿童编码', name: 'chilCode', width: 80 },
            { label: '操作用户', name: 'fkOperateUserId', width: 80 },
            { label: '取消原因', name: 'content', width: 80 }
        ],
        viewrecords: true,
        height: 385,
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
    $('#cancelForm').bootstrapValidator({
        live: 'enabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            content: {
                validators: {
                    notEmpty: {
                        message: '取消原因不能为空'
                    }
                }
            }
        }
    });
});


var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            chilCode:null,
        },
        showList: true,
        title: null,
        tBusCancel: {}
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
            vm.tBusCancel = {
                chilCode:chilCode
            };
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
        saveOrUpdate: function (event) {
            var vacinneId=$("#fkVaccineCode").val();
            Vue.set(vm.tBusCancel, 'bioCode', vacinneId);
            $("#cancelForm").bootstrapValidator('validate');//提交验证
            if ($("#cancelForm").data('bootstrapValidator').isValid()) {
                var url = vm.tBusCancel.id == null ? "../tbuscancel/save?chilCode=" + $("#chilCode").val() : "../tbuscancel/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.tBusCancel),
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
                    url: "../tbuscancel/delete",
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
            $.get("../tbuscancel/info/"+id, function(r){
                vm.tBusCancel = r.tBusCancel;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{    'chilCode':vm.q.chilCode},
                page:page
            }).trigger("reloadGrid");
        }
    }
});
