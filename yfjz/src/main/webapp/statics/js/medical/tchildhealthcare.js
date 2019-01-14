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
var sequenceNoId1 = getUrlVars()["sequenceNoId"];
var flag;
$(function () {
    $("#jqGrid").jqGrid({
        url: '../tchildhealthcare/list',
        datatype: "json",
        colModel: [
            // { label: 'id', name: 'id', width: 50, key: true },
            // { label: '儿童编码', name: 'childCode', width: 80 },
            { label: '儿童姓名', name: 'chilCode', width: 80 },
            // { label: '体检内容', name: 'content', width: 80 },
            // { label: '登记日期', name: 'registerDate', width: 80 },
            { label: '登记医生', name: 'registerUserId', width: 80 },
            { label: '体检结果', name: 'result', width: 80 ,
                formatter: function (r) {
                    if(r == 1){
                        return '合格';
                    }else if(r == 2 ){
                        return '不合格';
                    }else if(r == 3){
                        return '需复查';
                    }else{
                        return '';
                    }
                }
            },
            // { label: '身高', name: 'height', width: 80 },
            // { label: '体重', name: 'weight', width: 80 },
            // { label: '头围', name: 'headCirc', width: 80 },
            // { label: '囟门', name: 'fontanelA', width: 80 },
            // { label: '囟门', name: 'fontanelB', width: 80 },
            // { label: '出牙数', name: 'toothNum', width: 80 },
            // { label: '血红蛋白浓度', name: 'hemoglobinVal', width: 80 },
            // { label: '是否服用维生素d', name: 'takedVitd', width: 80 },
            { label: '备注', name: 'remark', width: 80 },
            // { label: '状态:0：正常；-1删除', name: 'status', width: 80 },
            { label: '创建人', name: 'createUserId', width: 80 },
            // { label: '创建时间', name: 'createTime', width: 80 },
            // { label: '组织', name: 'orgId', width: 80 }
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
    // vm.registerDate();

    $('#healthcareForm').bootstrapValidator({
        live: 'enabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            'example-multiple': {
                validators: {
                    notEmpty: {
                        message: '体检内容不能为空'
                    }
                }
            },
            result: {
                validators: {
                    notEmpty: {
                        message: '体检结果不能为空'
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
        tChildHealthcare: {},
        sequenceNoId:null,
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            document.getElementById("ebbtn").disabled=false;//启用
            // $('#ebbtn').attr("disabled","false")
            if(!chilCode){
                alert("请先呼叫号码或请选择儿童");
                return ;
            }
            vm.showList = false;
            vm.title = "新增";
            vm.tChildHealthcare = {  chilCode:chilCode};
        },
        update: function (event) {
            document.getElementById("ebbtn").disabled=false;//启用
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
            $("#healthcareForm").bootstrapValidator('validate');
            var chilCode=$("#chilCode").val();
            Vue.set(vm.tChildHealthcare,'chilCode',chilCode);
            // var  multiple =JSON.stringify($("#example-multiple").val())
            // Vue.set(vm.tChildHealthcare,'content',multiple)
            if ($("#healthcareForm").data('bootstrapValidator').isValid()) {
                layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 500});
                if (flag) {
                    var url = vm.tChildHealthcare.id == null ? "../tchildhealthcare/save?chilCode=" + chilCode + "&chilName=" + chilName + "&sequenceNoId=" + sequenceNoId1 : "../tchildhealthcare/update";
                    $.ajax({
                        type: "POST",
                        url: url,
                        data: JSON.stringify(vm.tChildHealthcare),
                        contentType: 'application/json;charset=UTF-8',
                        success: function (r) {
                            flag=false;
                            document.getElementById("ebbtn").disabled = true;//禁用
                            if (r.code === 0) {
                                // $('#ebbtn').attr("disabled","true")
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
                    url: "../tchildhealthcare/delete",
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

            $.get("../tchildhealthcare/info/"+id, function(r){
                vm.tChildHealthcare = r.tChildHealthcare;
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

vm.$on("callingNumber", function (queue) {
    registerVM.sequenceNoId = queue.id;
})