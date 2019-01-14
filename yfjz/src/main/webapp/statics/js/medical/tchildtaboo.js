
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
var vm;
$(function () {
    $(function () {
        vm.loadData();
    });
    $("#jqGrid").jqGrid({
        url: '../tchildtaboo/list',
        datatype: "json",
        colModel: [
            // { label: 'id', name: 'id', width: 50, key: true },
            // { label: '儿童编码', name: 'chilCode', width: 80 },
            // // { label: '儿童姓名', name: 'chilName', width: 80 },
            // { label: '接种禁忌', name: 'istaCode', width: 80 },
            // { label: '疫苗名称', name: 'istaBioCode', width: 80 },
            // { label: '创建人名字', name: 'createUserName', width: 80 },
            // { label: '备注', name: 'remark', width: 80 }
            { label: '接种禁忌', name: 'istaCode', width: 80 },
            { label: '疫苗代码', name: 'istaBioCode', width: 80 },
            { label: '检测日期', name: 'istaCheckDate', width: 150 }
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


    $("#istaCheckDate").datetimepicker({
        language: 'zh-CN',//显示中文
        format: 'yyyy-mm-dd hh:ii:ss',//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),
        forceParse:true,
        autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
        locale: moment.locale('zh-cn')
    }).on('hide',function(e) {
        $('#abnormal').data('bootstrapValidator')
            .updateStatus('istaCheckDate', 'NOT_VALIDATED',null)
            .validateField('istaCheckDate');
    });

    $('#tabooForm').bootstrapValidator({
        live: 'enabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            items: {
                validators: {
                    notEmpty: {
                        message: '接种禁忌不能为空'
                    }
                }
            },
            BioCode: {
                validators: {
                    notEmpty: {
                        message: '疫苗不能为空'
                    }
                }
            },
        }
    });


    // $("#vaccine").jqGrid({
    //     url: '../tvacinfo/list',
    //     datatype: "json",
    //     colModel: [
    //         { label: '国标编码', name: 'bioCode', width: 70 ,search:true},
    //         { label: '疫苗名称', name: 'bioName', width: 130 ,search:true},
    //         { label: '中文简称', name: 'bioCnSortname', width: 100 ,search:true},
    //         { label: '英文简称', name: 'bioEnSortname', width: 80 ,search:true},
    //     ],
    //     viewrecords: true,
    //     width:600,
    //     height: 385,
    //     rowNum: 1000,
    //     rowList : [10,30,50],
    //     rownumWidth: 25,
    //     jsonReader : {
    //         root: "page.list",
    //         page: "page.currPage",
    //         total: "page.totalPage",
    //         records: "page.totalCount"
    //     },
    //     prmNames : {
    //         page:"page",
    //         rows:"limit",
    //         order: "order"
    //     },
    //     gridComplete:function(){
    //         //隐藏grid底部滚动条
    //         $("#vaccine").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
    //     },
    //     //选中一行，然后将数据保存到输入框中，关闭弹出框
    //     onSelectRow:function (rowid,status) {
    //         var row=$("#vaccine").jqGrid("getRowData",rowid);
    //         $("#fkVaccineCode").val(row.bioCode);
    //         $("#fkVaccineName").val(row.bioName);
    //         //vm.tMgrStockBase = {fkVaccineCode:row.bioCode,productName:row.bioName};
    //         $('#myModal').modal('hide')
    //         $("#myModal").on("hidden", function() {
    //             $(this).removeData("modal");
    //         });
    //     }
    // });


});


// function searchName() {
//     var val=$("#searchName").val();
//     console.log(val)
//     var url="../tvacinfo/getAllVaccine";
//     $.ajax({
//         url:url,
//         type:'post',
//         dataType: 'json',
//         contentType:'application/json;charset=UTF-8',//重点关注此参数
//         success: function (data) {
//
//             doSearch(val,data.page,['bioCode','bioName','bioCnSortname','bioEnSortname'],$("#vaccine"));
//         }
//     })
// }

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            chilCode:null,
        },
        showList: true,
        title: null,
        tChildTaboo: {},
        items:[],
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
            vm.tChildTaboo = {
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
        //民族
        loadData:function(){
            var param = new Array();
            $.ajax({
                url: '../child/listdiccode?ttype=code_avoid',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var result=results.data;
                    $.each(result, function (index, item) {
                        param.push({"text":item.text,"value":item.value});
                    });
                    vm.items = param;
                }
            });
        },

        saveOrUpdate: function (event) {
            // var vacinneId=$("#fkVaccineCode").val();
            var istaCheckDate = $("#istaCheckDate").val();
            var chilCode=$("#chilCode").val();
            // Vue.set(vm.tChildTaboo,"istaBioCode",vacinneId);
            Vue.set(vm.tChildTaboo,"chilCode",chilCode);
            Vue.set(vm.tChildTaboo,"istaCheckDate", istaCheckDate);
            $("#tabooForm").bootstrapValidator('validate');//提交验证
            if ($("#tabooForm").data('bootstrapValidator').isValid()) {
                layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
                var url = vm.tChildTaboo.id == null ? "../tchildtaboo/save" : "../tchildtaboo/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.tChildTaboo),
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
                    url: "../tchildtaboo/delete",
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
            $.get("../tchildtaboo/info/"+id, function(r){
                vm.tChildTaboo = r.tChildTaboo;
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