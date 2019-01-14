var vm = new Vue({
    el:'#inoculationday_tab',
    data:{
        showList: true,
        title: null,
        type:null,//分类，用于区分界面是否显示
        inoculateDays:{
            ids:null,
            settingType:null
        },
        monthSettingdays:null,//月门诊-接种日所选中的日期数组
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
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
        start:function(){

            vm.getCurrentStartDays();//获取当前启用的门诊类型

            $.ajax({
                type: "POST",
                url: "../tbasedaysetting/start",
                data:JSON.stringify(vm.type),
                contentType:'application/json;charset=UTF-8', //作为字符串返送到后台
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功');
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.tMgrStore.id == null ? "../tmgrstore/save" : "../tmgrstore/update";
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
        },
        save:function(){
            //保存列表数据
            if (vm.type === '周门诊-接种点'){
                // 周门诊-接种点
                var ids = getSelectedRows("week_station_gridTable");
                vm.inoculateDays.ids = ids;
                vm.inoculateDays.settingType=vm.type;

                $.ajax({
                    type: "POST",
                    url: "../tbasedaysetting/weekdaysave",
                    data:JSON.stringify(vm.inoculateDays),
                    contentType:'application/json;charset=UTF-8', //作为字符串返送到后台
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
                                $("#inoculationdayjqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        showJqgrid:function() {
            //加载周门诊接种日
            if (vm.type === '周门诊-接种点'){
                $("#week_station_gridTable").jqGrid("clearGridData");//优先情况之前的数据
                $("#week_station_gridTable").jqGrid({
                    width:400,
                    height:400,
                    dataType:'local',
                    colNames:["接种日"],
                    colModel:[
                        {name:"day",index:"day",align:'center'}
                    ],
                    viewrecords:true,
                    multiselect:true,//设置多选框
                    rowNum:15,
                    rowList:[15,20,25,30],
                    jsonReader:{
                        root: "rows",
                        page: "page",
                        total: "total",
                        records: "records",
                        repeatitems: false

                    }
                });

                var datas =[
                    {"day": "周一"},
                    {"day": "周二"},
                    {"day": "周三"},
                    {"day": "周四"},
                    {"day": "周五"},
                    {"day": "周六"},
                    {"day": "周日"},
                ];

                for(var i=0;i<datas.length;i++){
                    $("#week_station_gridTable").jqGrid('addRowData',i+1,datas[i]);
                }
            }
        },
        getCurrentStartDays:function(){
            //加载当前启用的门诊类型
            $.get("../tbasedaysetting/getCurrentStartDays", function(r){
                if (r.code < 0){
                    vm.type = r.msg;
                } else{
                    vm.type = r.settingType;
                }

            });
        },

        getInfo: function(id){
            $.get("../tmgrstore/info/"+id, function(r){
                vm.tMgrStore = $.parseJSON(r).tMgrStore;

            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#inoculationdayjqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        }
    }
});
$(function () {
    $("#inoculationdayjqGrid").jqGrid({
        url: '../tmgrstore/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', width: 50, key: true, hidden: true},
            {label: '仓库名称', name: 'name', width: 80},
            {label: '仓库地址', name: 'address', width: 80},
            {label: '管理员', name: 'mgrUserId', width: 80},
            {label: '管理员电话', name: 'mgrPhone', width: 80},
            {label: '管理员邮箱', name: 'mgrEmail', width: 80},
            {label: '关联的接种台', name: 'posId', width: 80},
            {
                label: '状态', name: 'status', width: 80, formatter: function (cellvalue, options, rowObject) {
                    return cellvalue == 0 ? '启用' : '停用';
                }
            },
            {label: '备注', name: 'remark', width: 80}
        ],
        viewrecords: true,
        height: $(window).height() - 120,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#inoculationdayjqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#inoculationdayjqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    //给单选按钮绑定点击事件
    // 当前选择的类型是:
    // 周门诊-接种点 月门诊-接种点 周门诊-居委会/行政村 月门诊-居委会/行政村  周门诊-疫苗 月门诊-疫苗
    $(":radio").on('click',function(){
        vm.type = $(this).val();//设置类型,动态控制显示类型
        vm.showJqgrid();
    });


    //初始化时加载当前启用的门诊类型
    vm.getCurrentStartDays();


    // 日期td的单机事件
    $(".monthSetting td").not(".selectMonth1").click(function(e) {
        var jqClickedTd = $(this);	// 当前被点击的td
        var jqParentTable = jqClickedTd.parents("table.monthSetting");	// 父表格
        var jqCheckAllBox = jqParentTable.find("td.selectMonth1 input:checkbox"); // 对应的全选按钮
        var allChecked = jqCheckAllBox.prop("checked");	// 全选按钮的选中状态
        var setted = jqClickedTd.hasClass("setted");	// td是否被设置样式
        // 切换样式
        jqClickedTd.toggleClass("setted");
        var dateHtml = jqClickedTd.html();	// td上的日期 1-31号
        alert(dateHtml);
    });

    // 最后一个输入框 全选按钮
    $(".monthSetting td.selectMonth1").click(function(e) {
        var parentTable = $(this).parents("table.monthSetting");	// 父表格
        var jqCheckAllBox = $(this).find("input:checkbox");		// 对应全选按钮
        var allDateChecked = jqCheckAllBox.prop("checked");		// 全选状态
        var jqDateTds = parentTable.find("td").not(".selectMonth1");	// 除了全选td之外的所有td
        if (allDateChecked) {
            jqDateTds.addClass("setted");
            jqDateTds.each(function(i) {
                vm.monthSettingdays.push(this.innerHTML);
            });
        } else {
            jqDateTds.removeClass("setted");
            vm.monthSettingdays = [];
        }


    });
});