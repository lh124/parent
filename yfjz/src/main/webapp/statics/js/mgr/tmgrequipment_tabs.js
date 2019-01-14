var vmp;//设备管理VUE对象变量

$(function () {
    /**
     * 为tab绑定点击事件，判断为点击设备管理页面
     */
    $("ul li").click(function () {
        var val=$(this).find("a").html();
        if(vmp==null||vmp==undefined){
            vmp= new Vue({
                el:'#tab2',
                data:{
                    showList2: true,
                    title: null,
                    tMgrEquipment: {storeid:null},
                    items:[],//下拉绑定
                },
                methods: {
                    query: function () {
                        vmp.reload();
                    },
                    add2: function(){
                        vmp.showList2 = false;
                        vmp.title = "新增";
                        vmp.tMgrEquipment = {};
                    },
                    update2: function (event) {
                        var id = getRow();
                        if(id == null){
                            return ;
                        }
                        vmp.showList2 = false;
                        vmp.title = "修改";

                        vmp.getInfo(id)
                    },
                    loadData:function(){
                        var param = new Array();//定义数组
                        //加载仓库数据
                        $.ajax({
                            // get请求地址
                            url: '../tmgrstore/getAllStore',
                            dataType: "json",
                            type: 'POST',
                            success: function (data) {
                                var result=data.page;
                                $.each(result, function (index, item) {
                                    param.push({"text":item.name,"value":item.id});
                                });
                                vmp.items = param;
                            }
                        });
                    },
                    saveOrUpdate2: function (event) {
                        $("#equipmentForm").bootstrapValidator('validate');//提交验证
                        if ($("#equipmentForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                            var url = vmp.tMgrEquipment.id == null ? "../tmgrequipment/save" : "../tmgrequipment/update";
                            $.ajax({
                                type: "POST",
                                url: url,
                                data: JSON.stringify(vmp.tMgrEquipment),
                                contentType:'application/json;charset=UTF-8',
                                success: function(r){
                                    if( r.code == 0){
                                        alert('操作成功', function(index){
                                            vmp.reload2();
                                        });
                                    }else{
                                        alert(r.msg);
                                    }
                                }
                            });
                        }

                    },
                    stop2: function (event) {

                        var id = getOneSelectRow($("#equipmentGrid"));
                        if(id == null){
                            return ;
                        }

                        confirm('确定要删除选中的记录？', function(){
                            $.ajax({
                                type: "POST",
                                url: "../tmgrequipment/updateStatus?id="+id,
                                contentType:'application/json;charset=UTF-8',
                                success: function(r){
                                    if(r.code == 0){
                                        alert('操作成功', function(index){
                                            $("#equipmentGrid     ").trigger("reloadGrid");
                                        });
                                    }else{
                                        alert(r.msg);
                                    }
                                }
                            });
                        });
                    },
                    getInfo: function(id){
                        $.get("../tmgrequipment/info/"+id, function(r){
                            r.tMgrEquipment.storeid=r.tMgrEquipment.store.id;
                            vmp.tMgrEquipment = r.tMgrEquipment;
                        });
                    },
                    reload2: function (event) {
                        vmp.showList2 = true;
                        var page = $("#equipmentGrid").jqGrid('getGridParam','page');
                        $("#equipmentGrid").jqGrid('setGridParam',{
                            page:page
                        }).trigger("reloadGrid");
                    }
                }
            });
        }
        //表单JS校验
        $('#equipmentForm').bootstrapValidator({
            live: 'enabled',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                equipmentName: {
                    validators: {
                        notEmpty: {
                            message: '设备名不能为空'
                        }
                    }
                },
                code: {
                    validators: {
                        notEmpty: {
                            message: '设备编码不能为空'
                        }
                    }
                },
                store: {
                    message :'仓库验证失败!',
                    validators: {
                        notEmpty: {
                            message: '仓库不能为空'
                        }
                    }
                }
            }
        });
        if(val.trim()=='设备管理'){
            //初始化设备数据量列表
            $("#equipmentGrid").jqGrid({
                    url: '../tmgrequipment/list',
                    datatype: "json",
                    colModel: [
                        { label: 'id', name: 'id', width: 50, key: true ,hidden:true},
                        { label: '设备名称', name: 'name', width: 80 },
                        { label: '设备编码', name: 'code', width: 80 },
                        { label: '关联的仓库', name: 'store.name', width: 80 },
                        { label: '关联的仓库Id', name: 'store.id', width: 80 ,hidden:true},
                        { label: '状态', name: 'status', width: 80,formatter:function(cellvalue, options, rowObject){
                                return cellvalue==1?'<span class="label label-danger">禁用</span>' :
                                    '<span class="label label-success">启用</span>';
                            }},
                        { label: '备注', name: 'remark', width: 80 }
                    ],
                    viewrecords: true,
                    height: 'auto',
                    rowNum: 10,
                    rowList : [10,20,30,50],
                    rownumbers: true,
                    rownumWidth: 25,
                    autowidth:true,
                    multiselect: true,
                    pager: "#equipmentPager",
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
                        $("#equipmentGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                    }
                });
            $(function () {
                vmp.loadData();//初始化下拉框 工作台
            })
        }
    })

})
function getRow() {
    var grid = $("#equipmentGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }
    return selectedIDs[0];
}