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
        url: '../tchildabnormal/list',
        datatype: "json",
        colModel: [
            // { label: 'id', name: 'id', width: 50, key: true },
            // { label: '儿童编码', name: 'chilCode', width: 80,hidden:true },
            // { label: '疫苗名称', name: 'aefiBactCode', width: 80 },
            // { label: '反应症状名称', name: 'aefiCode', width: 80 },
            // { label: '反应日期', name: 'aefiDate', width: 80 },
            // { label: '创建人名字', name: 'createUserName', width: 80 },

            { label: '疫苗编码', name: 'aefiBactCode', width: 80 },
            { label: '反应症状', name: 'aefiCode', width: 80 },
            { label: '反应日期', name: 'aefiDate', width: 80 }

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
            "chilName": $("#chilName").val(),
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
    vm.loadData();
    vm.loadData1();
    vm.aefiDate();//反应日期
    $('#abnormalForm').bootstrapValidator({
        live: 'enabled',
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            BioCode: {
                validators: {
                    notEmpty: {
                        message: '疫苗不能为空'
                    }
                }
            },
            AefiCode: {
                validators: {
                    notEmpty: {
                        message: '反应症状不能为空'
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
        tChildAbnormal: {}
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
            vm.tChildAbnormal = {
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

        loadData:function(){
            $.ajax({
                url: '../child/listdiccode?ttype=code_AEFI',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.data.length; i++) {
                        // con += '<option   data-xyz="' + results.data[i].value + '"> '+results.data[i].text+' </option>';
                        con += '<option  value="' + results.data[i].value + '">'+results.data[i].text+'</option>';
                    }
                    $("#aefiCode").html(con);
                }
            });
        },

        loadData1:function(){
            $.ajax({
                url: '../tvacinfo/list?page=1&limit=10000',
                dataType: "json",
                type: 'POST',
                success: function (results) {
                    var con='';
                    for (var i = 0; i < results.page.list.length; i++) {
                        con += '<option  value="' + results.page.list[i].bioCode + '">'+results.page.list[i].bioCnSortname+"("+results.page.list[i].bioCode+")"+'</option>';
                    }
                    $("#BioCode").html(con);
                }
            });
        },

        aefiDate:function(){
            $('#datetimepicker1').datetimepicker({
                language: 'zh-CN',//显示中文
                format: 'yyyy-mm-dd hh:ii:ss',//显示格式
                minView: "month",//设置只显示到月份
                initialDate: new Date(),
                autoclose: true,//选中自动关闭
                todayBtn: true,//显示今日按钮
                locale: moment.locale('zh-cn')
            });
            //默认获取当前日期
            var today = new Date();
            var nowdate = (today.getFullYear()) + "-" + (today.getMonth() + 1) + "-" + today.getDate();
            //对日期格式进行处理
            var date = new Date(nowdate);
            var mon = date.getMonth() + 1;
            var day = date.getDate();
            var mydate = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day);
            document.getElementById("aefiDate").value = mydate;

        },
        saveOrUpdate: function (event) {

            var vacinneId=$("#BioCode option:selected").val();
            var  chilCode=$("#aefiCode option:selected").val();
            var aefiDate=$("#aefiDate").val();
            Vue.set(vm.tChildAbnormal, 'aefiBactCode', vacinneId);
            Vue.set(vm.tChildAbnormal,'aefiCode',chilCode);
            Vue.set(vm.tChildAbnormal,'aefiDate',aefiDate);
            $("#abnormalForm").bootstrapValidator('validate');//提交验证

            if ($("#abnormalForm").data('bootstrapValidator').isValid()) {
                layer.msg('正在提交中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, time: 10000});
                var url = vm.tChildAbnormal.id == null ? "../tchildabnormal/save" : "../tchildabnormal/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.tChildAbnormal),
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
                    url: "../tchildabnormal/delete",
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
            $.get("../tchildabnormal/info/"+id, function(r){
                vm.tChildAbnormal = r.tChildAbnormal;
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