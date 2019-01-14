//初始化仓库管理VUE对象
var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        tMgrStore: {posId:null},
        items:[],

    },
    methods: {
        query: function () {
            vm.reload();
        },
        loadData:function(){
            var param = new Array();//定义数组
            //下拉选框
            $.ajax({
                // get请求地址
                url: '../tmgrstore/getTowerList',
                dataType: "json",
                type: 'POST',
                success: function (data) {
                    var result=data.page;
                    param.push({"text":"","value":""});
                    $.each(result, function (index, item) {
                        param.push({"text":item.towerName,"value":item.id});
                    });
                    vm.items = param;
                }
            });
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.tMgrStore = {};
            vm.loadData();
            //清除上次的校验
            vm.reset();
            $("#selectTowers").hide();
            vm.reloadUserInfo();
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.loadData();
            vm.getInfo(id);
            vm.reloadUserInfo();
        },
        saveOrUpdate: function (event) {
            $("#storeForm").bootstrapValidator('validate');//提交验证
            if ($("#storeForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                var url = vm.tMgrStore.id == null ? "../tmgrstore/save" : "../tmgrstore/update";
                var userId=$("#users option:selected").val();
                vm.tMgrStore.mgrUserId=userId;
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.tMgrStore),
                    contentType:'application/json;charset=UTF-8',
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            }
        },
        stop: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }

            confirm('确定要操作选中的仓库吗？', function(){
                $.ajax({
                    type: "POST",
                    url: "../tmgrstore/updateStatus?id="+id,
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
            $.get("../tmgrstore/info/"+id, function(r){
                vm.tMgrStore = r.tMgrStore;
                $("#users").selectpicker("val",r.tMgrStore.mgrUserId)
                if(r.tMgrStore.ttype==1){
                    $("#selectTowers").show();
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        },reset:function () {
            $("#storeForm").data('bootstrapValidator').resetForm();
        },
        reloadUserInfo:function () {
            $.ajax({
                url:'../sys/user/list?pageSize=10000&pageNo=1',
                type:'get',
                async:false,
                success:function (data) {
                    var users= data.page.data;
                    $("#users").empty();
                    for(var i=0;i<users.length;i++){
                        var user=users[i];
                        $("#users").append('<option value="'+user.userId+'">'+user.realName+'</option>')
                    }
                    $("#users").selectpicker('refresh');
                }
            })
        },
        changeShow:function () {
            var optVla=$("#storeType option:selected").val();
            if(optVla==1){
                $("#selectTowers").show();
            }else{
                $("#selectTowers").hide();
            }
        }
    }
});

$(function () {

    //表单JS校验
    $('#storeForm').bootstrapValidator({
        live: 'enabled',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: '仓库名称不能为空'
                    }
                }
            },
            address: {
                validators: {
                    notEmpty: {
                        message: '仓库地址不能为空'
                    }
                }
            },
            mgrUserId: {
                validators: {
                    notEmpty: {
                        message: '仓库管理员不能为空'
                    }
                }
            },
            type: {
                validators: {
                    notEmpty: {
                        message: '仓库类型不能为空'
                    }
                }
            },
            mgrPhone: {
                validators: {
                    notEmpty: {
                        message: '手机号码不能为空'
                    },
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
                    },
                    regexp: {
                        regexp: /^1[3|4|5|6|7|8|9]{1}[0-9]{9}$/,
                        message: '请输入正确的手机号码'
                    }
                }
            },
            mgrEmail: {
                validators: {
                    emailAddress: {//验证email地址
                        message: '请输入正确的邮件地址如：123@qq.com'
                    },
                    notEmpty: {
                        message: 'email地址不能为空'
                    }
                }
            }

        }
    });
    var userArr;
    $.ajax({
        url:'../sys/user/list?limit=1000&page=1',
        type:'get',
        async:false,
        success:function (data) {
            // console.log(data)
            userArr= data.page.list;
        }
    })

    vm.loadData();
    //初始化仓库管理数据表格
    $("#jqGrid").jqGrid({
        url: '../tmgrstore/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true,hidden:true },
            { label: '仓库名称', name: 'name', width: 80 },
            { label: '仓库类型', name: 'ttype', width: 60 ,formatter:function(cellvalue, options, rowObject){
                    if(cellvalue==0){
                        return '<span class="label label-danger">主仓库</span>'
                    }else if(cellvalue==1){
                        return '<span class="label label-success">门诊分仓库</span>'
                    }else if(cellvalue==2){
                        return '<span class="label label-info">村分仓库</span>'
                    }
                }},
            { label: '仓库地址', name: 'address', width: 80 },
            { label: '管理员', name: 'mgrUserId', width: 80 ,formatter:function (celVal,options,rowObject) {
                    if(!isEmpty(celVal)&&celVal.length>35){
                        for(var i=0;i<userArr.length;i++){
                            if(userArr[i].userId==celVal){
                                return userArr[i].realName;
                            }
                        }
                    }else{
                        return celVal==null?'':celVal;
                    }
                }},
            { label: '管理员电话', name: 'mgrPhone', width: 80 },
            { label: '管理员邮箱', name: 'mgrEmail', width: 80 },
            { label: '关联的接种台Id', name: 'posId', width: 80,hidden:true },
            { label: '关联的接种台', name: 'storeName', width: 80 },
            { label: '状态', name: 'status', width: 80,formatter:function(cellvalue, options, rowObject){
                    return cellvalue==1?'<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
                }},
            { label: '备注', name: 'remark', width: 80 }
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
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });


    //初始化输入框
    $("#users").selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'请选择管理员',
    })
});
