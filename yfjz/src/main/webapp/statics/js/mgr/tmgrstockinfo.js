$(function () {
    $("#jqGrid").jqGrid({
        url: '../tmgrstockinfo/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true ,hidden:true},
            { label: 'storeId', name: 'storeId', width: 50, hidden:true},
            { label: 'equipId', name: 'equipId', width: 50, hidden:true},
            { label: '疫苗/产品名称', name: 'productName', width: 150 },
            { label: '批号', name: 'batchnum', width: 80 },
            { label: '生产厂家', name: 'factoryName', width: 150 },
            { label: '数量(支)', name: 'amount', width: 80 },
            { label: '数量(人份)', name: 'personAmount', width: 80 },
            { label: '规格', name: 'dosenorm', width: 80 },
            { label: '剂型', name: 'drug', width: 80 },
            { label: '计量单位', name: 'doseminUnitCode', width: 80 },
            { label: '单价(元)', name: 'price', width: 80,formatter:function(cellvalue, options, rowObject){
                    return parseFloat(cellvalue).toFixed(2);
                }},
            { label: '失效期', name: 'expiryDate', width: 100 },
            { label: '人份转换', name: 'conversion', width: 80 },
            { label: '批签发', name: 'lotRelease', width: 80 },
            { label: '批准文号', name: 'licenseNumber', width: 80 },
            { label: '类型', name: 'type', width: 80,formatter:function(cellvalue, options, rowObject){
                    return cellvalue==0?'疫苗':'其他';
                }},
            { label: '仓库', name: 'storeName', width: 80 },
            { label: '设备', name: 'equipmentName', width: 80 },
        ],
        viewrecords: true,
        height: 'auto',
        rowNum: 10,
        rowList : [10,20,30,50],
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
            //$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            var ids = $("#jqGrid").getDataIDs();
            for(var i=0;i<ids.length;i++){
                var rowData = $("#jqGrid").getRowData(ids[i]);
                var date=rowData.expiryDate;
                //最近一个月时间
                var oneMonthDate=new Date().getTime()+2592000000;
                var threeMonthDate=new Date().getTime()+7776000000;
               var expDate=new Date(date).getTime();
              if(expDate<=oneMonthDate){
                  $('#'+ids[i]).find("td").addClass("oneMonth");
              }else if(oneMonthDate<expDate&&expDate<threeMonthDate){
                  $('#'+ids[i]).find("td").addClass("threeMonth");
              }
            }

        },
        ondblClickRow:function(rowid, iRow, iCol, e){
            var rowData=$("#jqGrid").getRowData(rowid);
            layer.open({
                title: "收发登记",
                area: ["90%", "90%"],
                type: "2",
                content: '<iframe src="../mgr/dispatchRecord.html" name="child" style="width: 100%;height: 100%;overflow:auto" ></iframe>',
                success: function (index, layero) {
                    // $(".layui-layer-page .layui-layer-content").css("overflow-y", "hidden");
                    // 向子页面的全局函数child传参
                    child.window.setChildValue(rowData);
                    console.log(rowData)
                }
            })
        },

    });
    $("#orderList").jqGrid({
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 0, key: true ,hidden:true},
            { label: '疫苗编码', name: 'fkVaccineCode', width: 110 },
            { label: '疫苗/产品名称', name: 'productName', width: 150 },
            { label: '批号', name: 'batchnum', width: 110 },
            { label: '生产厂家编码', name: 'factoryId', width: 0,hidden:true},
            { label: '生产厂家', name: 'factoryName', width: 120 },
            { label: '人份转化', name: 'conversion', width: 80 },
            { label: '类型', name: 'type', width: 80,formatter:function(cellvalue, options, rowObject){
                    return cellvalue==0?'疫苗':'其他';
                }},
            { label: '入库数量(人份)', name: 'personAmount', width: 120,editable:true },
            { label: '入库数量(支)', name: 'amount', width: 120,editable:true },
            { label: '供货单位', name: 'providerFactory', width: 150,editable:true },
            { label: '供货人', name: 'provider', width: 80,editable:true },
            {label: '存放设备',name:'equipmentName', width:150,
                unformat:Unformat_Select},
            { label: '操作', name: 'opt', width: 100,formatter:function(cellvalue, options, rowObject){
                    return "<a href='javaScript:void(0)' onclick='deleteRows(\""+options.rowId+"\")'>删除</a>";
                }},

        ],
        viewrecords: true,
        height: 385,
        rownumbers: true,
        rownumWidth: 25,
        multiselect: false,
        autowidth:true,
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#orderList").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
        onSelectRow:function (rowid,status) {

            vm.changeStore();
            jQuery('#orderList').editRow(rowid);
            $("#" + rowid+'_personAmount').on("blur",function(){
                var row=$("#orderList").getRowData(rowid);
                var personAmount=$(this).val();
                if(!judgeNumber(personAmount)){
                    return;
                }
                var person=parseInt(personAmount);
                var conversion=row.conversion;
                if(conversion!=undefined||conversion!=''||conversion!=null){
                    if (person % conversion!=0) {
                        layer.msg(row.productName +' 录入数量必须是人份转换的倍数！')
                        return;
                    }
                }

                var amount=person/row.conversion;
                $("#" + rowid+'_amount').val(amount);
            });
            $("#" + rowid+'_amount').on("blur",function(){
                var row=$("#orderList").getRowData(rowid);
                var amount=$(this).val();
                if(!judgeNumber(amount)){
                    return;
                }
                var am=parseInt(amount);
                var person=am*row.conversion;
                $("#" + rowid+'_personAmount').val(person);
            })
        },
    });
    $("#productList").jqGrid({
        url: '../tmgrstockbase/allData',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 0, key: true ,hidden:true},
            { label: '疫苗编码', name: 'fkVaccineCode', width: 100 },
            { label: '疫苗/产品名称', name: 'productName', width: 150 },
            { label: '批号', name: 'batchnum', width: 120 },
            { label: '生产厂家编码', name: 'factoryId', width: 0,hidden:true},
            { label: '生产厂家', name: 'factoryName', width: 120 },
            { label: '失效期', name: 'expiryDate', width: 130 },
            { label: '人份转化', name: 'conversion', width: 90 },
            { label: '类型', name: 'type', width: 80,formatter:function(cellvalue, options, rowObject){
                    return cellvalue==0?'疫苗':'其他';
                }}
        ],
        //viewrecords: true,
        height: 385,
        rownumbers: true,
        autowidth:true,
        rowNum: 1000,
        multiselect: true,
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount",
            repeatitems:false
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        onSelectRow:function (rowid,status) {
            //判断选择行的id是否已经选择，如果已经选择不能再选
            var ids=$("#orderList").jqGrid("getDataIDs");
            for(var i=0;i<ids.length;i++){
                if(ids[i]==rowid){
                    layer.msg("已经选择该物品");
                    $("#productList").jqGrid("setSelection", rowid,false);
                    return
                }
            }

        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#productList").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
        vm.loadData();//初始化下拉框 工作台

    //校验报损弹框表单数据
    $('#damageForm').bootstrapValidator({
        live: 'enabled',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            number: {
                validators: {
                    notEmpty: {
                        message: '报损数量不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]*$/,
                        message: '只能输入数字'
                    }
                }
            },
            damageUser: {
                validators: {
                    notEmpty: {
                        message: '报损人不能为空'
                    }
                }
            },
            reason: {
                validators: {
                    notEmpty: {
                        message: '报损原因不能为空'
                    }
                }
            }

        }
    });
    $('#myModal').on('shown.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#myModal .modal-dialog').height() / 2;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight,
        });
    });

});
function deleteRows(rowId) {
    $("#orderList").jqGrid("delRowData", rowId);
}


/**
 * 关键字模糊查询产品信息
 */
function searchProduct() {
    var val=$("#searchName").val();
    var url="../tmgrstockbase/allData";
    $.ajax({
        url:url,
        type:'post',
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',//重点关注此参数
        success: function (data) {
            doSearch(val,data.page.list,['fkVaccineCode','productName','batchnum','factoryId','factoryName','type'],$("#productList"));
        }
    })
}


var vm;
vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        tMgrStockInfo: {},
        items: [],
        storeId: null,
        equipmentData: null
    },
    methods: {
        query: function () {
            var date = $("#searchDate").val();
            var searchProductName = $("#searchProductName").val();
            var searchFactoryName = $("#searchFactoryName").val();
            var searchBatch = $("#searchBatch").val();
            var searchType = $("#searchType").val();
            var selectType = $("#selectType").val();

            $("#jqGrid").jqGrid('setGridParam', {

                postData: {
                    'searchProductName': searchProductName,
                    'searchFactoryName': searchFactoryName,
                    'searchBatch': searchBatch,
                    'searchDate': date,
                    'searchType': searchType,
                    'selectType': selectType,
                },
                page: 1
            }).trigger("reloadGrid");
        },
        add: function () {
            vm.showList = false;
            vm.title = "产品入库";
            vm.tMgrStockInfo = {};
            $("#orderList").jqGrid("clearGridData");
            $("#putForm")[0].reset();
            $("#saveTime").val(formatDateTime(new Date()));
            vm.getOrderId();
        },
        loadData: function () {
            var param = new Array();//定义数组
            //下拉选框
            $.ajax({
                // get请求地址
                url: '../tmgrstore/list?page=1&limit=1000&type=0&status=0',
                dataType: "json",
                type: 'POST',
                success: function (data) {
                    var result = data.page.list;
                    $.each(result, function (index, item) {
                        param.push({"text": item.name, "value": item.id});
                    });
                    vm.items = param;
                    vm.storeId = param[0].value;
                }
            });
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            //获取参数
            var order = $("#order").val();
            var saveUser = $("#saveUser").val();
            var saveTime = $("#saveTime").val();
            var remark = $("#remark").val();
            /*    var provider=$("#provider").val();
                var providerFactory=$("#providerFactory").val();*/
            var store = $("#store option:selected").val();
            var orders = $("#orderList");
            endEdit(orders);//结束编辑
            var rows = getRows(orders);//获取所有行的数据
            //对数据做校验
            if (order == '' || order == undefined || order == null) {
                layer.msg("请填写入库单号");
                return;
            }
            if (saveUser == '' || saveUser == undefined || saveUser == null) {
                layer.msg("请填写入库人");
                return;
            }
            if (saveTime == '' || saveTime == undefined || saveTime == null) {
                layer.msg("请选择入库时间");
                return;
            }
            if (store == '' || store == undefined || store == null) {
                layer.msg("请选择入库仓库");
                return;
            }
            /* if(provider==''||provider==undefined||provider==null){
                 layer.msg("请填写供货单位");
                 return;
             }
             if(providerFactory==''||providerFactory==undefined||providerFactory==null){
                 layer.msg("请填写供货人");
                 return;
             }*/
            var provider;
            for (var i = 0; i < rows.length; i++) {
                var number = rows[i].personAmount;
                if (provider == '' || provider == undefined || provider == null) {
                    provider = rows[i].provider;
                }

                var providerFactory = rows[i].providerFactory;
                if (rows[i].type == '疫苗') {
                    rows[i].type = 0;
                } else {
                    rows[i].type = 1;
                }
                if (number == '' || number == undefined || number == null) {
                    layer.msg("请填写入库数量");
                    return;
                }

                if (providerFactory == '' || providerFactory == undefined || providerFactory == null) {
                    layer.msg("请填写供货单位");
                    return;
                }
                var conversion=parseInt(rows[i].conversion);

                if(conversion!=undefined||conversion!=''||conversion!=null){
                    if (number % conversion!=0) {
                        layer.msg(rows[i].productName +' 录入数量必须是人份转换的倍数！请检查后提交。')
                        $("#orderList").editRow(rows[i].id);
                        return;
                    }
                }
            }
            if (provider == '' || provider == undefined || provider == null) {
                layer.msg("请填写供货人");
                return;
            }
            $("#inStockBtn").attr("disabled","disabled");
            var resultData = {
                "order": order,
                "saveUser": saveUser,
                "saveTime": saveTime,
                "store": store,
                "remark": remark,
                "rows": rows,
                "provider": provider
            }
            var url = "../tmgrstockinfo/save";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(resultData),
                contentType: 'application/json;charset=UTF-8',
                success: function (r) {
                    $("#inStockBtn").removeAttr("disabled");
                    if (r.code === 0) {
                        layer.msg('操作成功');
                        vm.reload();
                    } else {
                        layer.msg(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: "../tmgrstockinfo/delete",
                    data: JSON.stringify(ids),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get("../tmgrstockinfo/info/" + id, function (r) {
                vm.tMgrStockInfo = r.tMgrStockInfo;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        reset: function () {
            $("#searchForm")[0].reset();

        },
        damage: function () {
            var id = getSelectedRow("jqGrid");
            if (id == null) {
                return;
            }
            $("#damageModel").modal("show");
            document.getElementById("damageForm").reset();
            var row = $("#jqGrid").getRowData(id);
            $("input[name='productName']").val(row.productName);
            $("input[name='stockInfoId']").val(row.id);
            $("input[name='stockNumber']").val(row.amount);
            $("input[name='equipId']").val(row.equipId);
            $("input[name='storeId']").val(row.storeId);
            $("input[name='conversion']").val(row.conversion);
        },
        print:function(){
            var date = $("#searchDate").val();
            var searchProductName = $("#searchProductName").val();
            var searchFactoryName = $("#searchFactoryName").val();
            var searchBatch = $("#searchBatch").val();
            var searchType = $("#searchType").val();
            var selectType = $("#selectType").val();
            window.open("../reports/printStockControl?searchDate="+date
                +"&searchProductName="+searchProductName
                +"&searchFactoryName="+searchFactoryName
                +"&searchBatch="+searchBatch
                +"&searchType="+searchType
                +"&selectType="+selectType).print();

        },
        salesReturn: function () {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            $("#returnModal").modal("show");
            document.getElementById("returnForm").reset();
            var row = $("#jqGrid").getRowData(id);
            $("input[name='productName']").val(row.productName);
            $("input[name='stockInfoId']").val(row.id);
            $("input[name='stockNumber']").val(row.amount);
            $("input[name='equipId']").val(row.equipId);
            $("input[name='storeId']").val(row.storeId);
            $("input[name='conversion']").val(row.conversion);
        },
        changeStore: function () {
            //查询设备数据
            var storeId = vm.storeId;

            if (storeId == null || storeId == undefined || storeId == '') {
                layer.msg("请先选择入库仓库");
                return;
            }
            $.ajax({
                type: "post",
                url: "../tmgrstore/getEquipmentById?storeId=" + storeId,
                dataType: 'json',
                async: false,
                contentType: 'application/json;charset=UTF-8',//重点关注此参数
                success: function (data) {
                    if(data!=""){
                        data = "'':其他;" + data;
                        vm.equipmentData = data;
                        console.log(vm.equipmentData);
                        jQuery('#orderList').setColProp('equipmentName', {
                            editable: true,
                            edittype: "select",
                            editoptions: {buildSelect: null, dataUrl: null, value: vm.equipmentData}
                        });
                    }

                }
            })
            //结束编辑
            endEdit($("#orderList"));
        },
        getOrderId: function () {
            $.ajax(
                $.get("../tmgrstockinfo/getOrderNumber", {"type": "stockIn"}, function (data) {
                    $("#order").val(data);
                })
            )
        },
        selectVac: function () {
            // var data = $("#productList").getRowData(rowid);
            var ids=$("#productList").getGridParam("selarrrow");
            for(var i=0;i<ids.length;i++){
                var data = $("#productList").getRowData(ids[i]);
                data.providerFactory = '上级疾控中心';
                if (data.type == '疫苗') {
                    data.type = 0;
                } else {
                    data.type = 1;
                }
                $("#orderList").jqGrid("addRowData", ids[i], data, "last");
            }
            $("#productList").jqGrid('resetSelection');
            $('#myModal').modal('hide')
        },
        cancelVac: function () {
            $('#myModal').modal('hide');
        }
    }
});

/**
 * 提交报损
 *
 */
function commitDamage() {
  var info=$("input[name='stockInfoId']").val();//库存ID
  var storeId=$("input[name='storeId']").val();//仓库ID
  var equipId=$("input[name='equipId']").val();//设备ID
  var conversion=$("input[name='conversion']").val();//人份转换
  var type=$("select[name='type']").val();//报损类型
  var amount=parseInt($("input[name='number']").val());//报损数量
  var stockNumber=parseInt($("input[name='stockNumber']").val());//库存数量
  var reason=$("textarea[name='reason']").val();//报损原因
    if(amount>stockNumber||amount<1){
        layer.msg("报损数量，必须大于0，小于库存数量");
        return;
    }
    $("#damageBtn").attr("disabled","disabled");
  var result={"infoId":info,"type":type,"number":amount,"reason":reason,"storeId":storeId,"equipId":equipId,"conversion":conversion};
    $.ajax({
        type:'post',
        url:'../tmgrstockinfo/damage',
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',//重点关注此参数\
        data:JSON.stringify(result),
        success:function (data) {
            $("#damageBtn").removeAttr("disabled");
           if(data.code==0){
               layer.msg("报损成功！")
               $("#damageModel").modal("hide");
               $("#jqGrid").trigger("reloadGrid");
               $("#damageForm").data('bootstrapValidator').resetForm();
           }
        },
        error:function (data) {
            layer.msg("报损失败！")
        }
    })

}

function commitReturn() {
    var info=$("input[name='stockInfoId']").val();//库存ID
    var storeId=$("input[name='storeId']").val();//仓库ID
    var equipId=$("input[name='equipId']").val();//设备ID
    var conversion=$("input[name='conversion']").val();//人份转换
    var amount=parseInt($("input[name='returnNumber']").val());//退货数量
    var stockNumber=parseInt($("input[name='stockNumber']").val());//库存数量
    var reason=$("textarea[name='returnReason']").val();//退货原因
    if(amount>stockNumber||amount<1){
        layer.msg("退货数量，必须大于0，小于库存数量");
        return;
    }
    var result={"infoId":info,"type":type,"number":amount,"reason":reason,"storeId":storeId,"equipId":equipId,"conversion":conversion};
    $("#returnBtn").attr("disabled","disabled");
    $.ajax({
        type:'post',
        url:'../tmgrstockinfo/return',
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',//重点关注此参数\
        data:JSON.stringify(result),
        success:function (data) {
            $("#returnBtn").removeAttr("disabled");
            if(data.code==0){
                layer.msg("退货成功！")
                $("#returnModal").modal("hide");
                $("#jqGrid").trigger("reloadGrid");
                $("#returnForm").data('bootstrapValidator').resetForm();
            }
        },
        error:function (data) {
            layer.msg("退货失败！")
        }
    })
}


function cancel(modal) {
    if(modal==1){
        $("#damageForm").data('bootstrapValidator').resetForm();
        $("#damageModel").modal("hide");
    }else if(modal==2){
        $("#returnModal").modal("hide");
        $("#returnForm").data('bootstrapValidator').resetForm();
    }

}

function judgeNumber(number) {
    if(number==undefined||number==null||number==''){
        layer.msg("请输入入库数量！");
        return false;
    }
    if (isNaN(number)) {
        layer.msg("请输入数字！");
        return false;
    }
    if(parseInt(number)<0){
        layer.msg("输入的数量必须大于0");
        return false;
    }
  return true;
}
