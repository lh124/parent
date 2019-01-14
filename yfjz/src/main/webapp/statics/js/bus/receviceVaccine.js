$(function () {
    //库存信息列表
    $("#stockJqGrid").jqGrid({
        url: '../receive/list?type=1',
        datatype: "json",
        //caption:'库存信息',
        colModel: [
            { label: '疫苗/产品名称', name: 'productName', width: 150,frozen:true},
            // { label: '疫苗/产品名称', name: 'productName', width: 150},
            { label: '批号', name: 'batchnum', width: 100 },
            { label: '生产厂家', name: 'factoryName', width: 130 },
            { label: '失效期', name: 'expiryDate', width: 100 },
            { label: '数量(支)', name: 'amount', width: 80 },
            { label: '数量(人份)', name: 'personAmount', width: 80 },
            { label: '人份转换', name: 'conversion', width: 80 },
            { label: '领取数量(支)', name: 'receiveNumber', width: 80,editable:true},
            { label: '费用', name: 'price', width: 80 ,formatter:function(cellvalue, options, rowObject){
                    console.log(cellvalue)
                    var price=parseFloat(cellvalue);
                    console.log(price)
                    if(price>0){
                        return '<span class="label label-warning">自费</span>'
                    }else{

                        return '<span class="label label-success">免费</span>';
                    }
                }},
            { label: '类型', name: 'type', width: 80,formatter:function(cellvalue, options, rowObject){
                    return cellvalue==0?'疫苗' :
                        '其他';
                }},
            { label: 'id', name: 'id',  key: true ,hidden:true},
            { label: 'baseId', name: 'baseId',hidden:true},
            { label: 'storeId', name: 'storeId',hidden:true},
            { label: 'equipId', name: 'equipId',hidden:true},
            { label: '存放设备', name: 'equipmentName', width: 80 },

        ],
        viewrecords: true,
        height:400,
        // width:600,
        rowNum: 50,
        rowList : [10,30,50],
        rownumbers: true,
        //rownumWidth:25,
        shrinkToFit:false,
        autowidth:true,
        multiselect:true,
        pager: "#stockJqGridPager",
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
            $(".frozen-bdiv").css("top","39px");
        },
        onSelectRow:function (rowid,status) {

            //绑定函数校验输入的数据
            var row=$("#stockJqGrid").getRowData(rowid);
            //默认库存数量
            var rec=row.receiveNumber;
            if(rec==null||rec==''||rec==undefined){
                $("#stockJqGrid").setCell(rowid,'receiveNumber',row.amount);
            }
            //开启编辑
            $('#stockJqGrid').editRow(rowid);
            autoGridHeight();
            /**
             * 当输入框失去焦点时，校验输入的数据是否合法
             */
            $("#" + rowid+'_receiveNumber').on("blur",function(){
                var row=$("#stockJqGrid").getRowData(rowid);
                //输入的数据
                var val=$(this).val();
                if(val!=undefined&&val!=null&&val!=null){
                    var inputNumber=parseInt(val);
                    //库存数量
                    var result=isNaN(val);
                    if(result){
                        layer.msg("请输入数字！");
                        return;
                    }
                    var amount=parseInt(row.amount);
                    if(inputNumber!=null&&inputNumber!=''&&inputNumber>amount||inputNumber<1){
                        layer.msg("输入的领取数量，必须大于0且小于等于库存数量！");
                        return;
                    }
                }

            })


        },
    }).jqGrid('setFrozenColumns');;
    //已领取的疫苗
    $("#receiveGird").jqGrid({
        url: '../receive/receiveList?type=0',
        datatype: "json",
        //caption:'库存信息',
        colModel: [
            { label: 'id', name: 'id',  key: true ,hidden:true},
            { label: 'baseId', name: 'baseId',hidden:true},
            { label: 'storeId', name: 'storeId',hidden:true},
            { label: 'equipId', name: 'equipId',hidden:true},
            { label: '疫苗/产品名称', name: 'productName', width: 150 },
            { label: '批号', name: 'batchnum', width: 80 },
            { label: '生产厂家', name: 'factoryName', width: 150 },
            { label: '失效期', name: 'expiryDate', width: 100 },
            { label: '领取数量(支)', name: 'receiveAmount', width: 80 },
            { label: '剩余数量(支)', name: 'amount', width: 80 },
            { label: '剩余数量(人份)', name: 'personAmount', width: 80 },
            { label: '人份转换', name: 'conversion', width: 80 },
            { label: '类型', name: 'type', width: 80,formatter:function(cellvalue, options, rowObject){
                    return cellvalue==0?'<span class="label label-success">疫苗</span>' :
                        '<span class="label label-warning">其他</span>';
                }},
            //{ label: '仓库', name: 'storeName', width: 80 },
        ],
        viewrecords: true,
        height:410,
        // width:600,
        rowNum: 50,
        rowList : [10,30,50],
        rownumbers: true,
        //rownumWidth:25,
        shrinkToFit:false,
        autowidth:true,
        pager: "#receiveGirdPage",
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
            // $("#stockJqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    })
    //领取疫苗记录表
    $("#receiveRecordGird").jqGrid({
        url: '../receive/receiveTotalList',
        datatype: "json",
        subGrid : true,
        colModel: [
            { label: 'id', name: 'id', key: true,hidden:true },
            { label: '领取单号', name: 'outCode', width: 80 },
            { label: '领取人员', name: 'username', width: 80 },
            { label: '领取时间', name: 'createTime', width: 80 },
            /*  { label: '出库类型', name: 'type', width: 80,formatter:function(cellvalue, options, rowObject){
                      return cellvalue==0?'疫苗':'其他';
                  }},*/
            { label: '仓库', name: 'storeName', width: 80 },
            { label: '备注', name: 'remark', width: 80 },

        ],
        viewrecords: true,
        height: 410,
        width:600,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        pager: "#receiveRecordGirdPage",
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
            $("#receiveRecordGird").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
        subGridRowExpanded: function(subgrid_id, row_id) {  // (2)子表格容器的id和需要展开子表格的行id，将传入此事件函数
            var subgrid_table_id;
            subgrid_table_id = subgrid_id + "_t";   // (3)根据subgrid_id定义对应的子表格的table的id

            var subgrid_pager_id;
            subgrid_pager_id = subgrid_id + "_pgr"  // (4)根据subgrid_id定义对应的子表格的pager的id

            // (5)动态添加子报表的table和pager
            $("#" + subgrid_id).html("<table  id='"+subgrid_table_id+"' class='scroll'></table><div id='"+subgrid_pager_id+"' class='scroll'></div>");
            // (6)创建jqGrid对象
            $("#" + subgrid_table_id).jqGrid({
                // url: "fetchPatentCases.action?contact.id="+row_id,  // (7)子表格数据对应的url，注意传入的contact.id参数
                url: '../receive/receiveItemList/'+row_id,
                datatype: "json",
                colModel: [
                    { label: 'id', name: 'id', width: 50, key: true,hidden:true },
                    { label: '疫苗/产品名称', name: 'productName', width: 80 },
                    { label: '价格', name: 'price', width: 80 ,formatter:function(cellvalue, options, rowObject){
                            return cellvalue.toFixed(2);;
                        }},
                    { label: '生产厂家', name: 'factoryName', width: 80 },
                    { label: '批号', name: 'batchnum', width: 80 },
                   // { label: '规格', name: 'dosenorm', width: 80 },
                   // { label: '单位', name: 'doseminUnitCode', width: 80 },
                   // { label: '剂型', name: 'drug', width: 80 },
                    { label: '领取数量', name: 'amount', width: 80 },
                 //   { label: '领取人份数量', name: 'personAmount', width: 80 },
                   // { label: '存放位置', name: 'equipmentName', width: 80 }
                ],
                width:600,
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
                    $("#" + subgrid_table_id).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                },
                //pager: subgrid_pager_id,
                autowidth:true,
                viewrecords: true,
                height: "100%",
                rowNum: 100,
                //rowList : [10,30,50]
            });
        }
    });

    //初始化生产厂家下拉框
    $('.selectpicker').selectpicker({
        'selectedText': 'cat',
        noneSelectedText:'',
        noneResultsText:'没有匹配的结果',
        actionsBox:true,
        search:false,
    });
    $.ajax({
        // get请求地址
        url: '../tvacfactory/getAllData',
        dataType: "json",
        type: 'POST',
        success: function (data) {
            var result=data.page;
            vm.searchFactoryName = data.page;
            $("#searchFactoryName").append(" <option value='-1'></option>")
            $.each(result, function (i, n) {

                $("#searchFactoryName").append(" <option value=\"" + n.factoryCode + "\">" + n.factoryCnName + "</option>");
            })
            $("#searchFactoryName").selectpicker('refresh');
        }
    });
    //加载疫苗的名称信息
    $.ajax({
        // get请求地址
        url: '../tvacinfo/getAllData',
        dataType: "json",
        type: 'POST',
        success: function (data) {
            var result=data.page;
            vm.searchProductName = data.page;
            $("#searchProductName").append(" <option value='-1'></option>")
            $.each(result, function (i, n) {

                $("#searchProductName").append(" <option value=\"" + n.bioCode + "\">" + n.bioCnSortname + "</option>");
            })
            $("#searchProductName").selectpicker('refresh');
        }
    });
    //初始化日期
    $('#searchDate').datetimepicker(
        {
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            startView: 'month',
            minView:'month',
            language:  'zh-CN'
        }
    );

    // 疫苗拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#searchProductName").next().find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $(this).val();
        var hunt = $("#searchProductName");
        var value = vm.searchProductName;
        //清除之前option标签
        hunt.empty();
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {

            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if(reg.test(inputManagerName)){
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
                    }
                }
            }else{
                for (var i = 0; i < value.length; i++) {
                    if(value[i].bioCnSortname.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
                    }
                }
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            event.stopPropagation();
            return false;
        } else{
            var con = '<option value=""></option>';
            for (var i = 0; i < value.length; i++) {
                con += '<option  value="' + value[i].bioCode + '">' + value[i].bioCnSortname + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });

    // 疫苗生产厂家拼音搜索
    //选择得到搜索栏input,松开按键后触发事件
    $("#searchFactoryName").next().find('.bs-searchbox').find('input').keyup(function (event) {
        //键入的值
        var inputManagerName = $(this).val();
        var hunt = $("#searchFactoryName");
        var value = vm.searchFactoryName;
        //清除之前option标签
        hunt.empty();
        //判定键入的值不为空,才调用ajax
        if (inputManagerName != '') {

            var con = '<option value=""></option>';
            var reg = new RegExp("^[A-Za-z]+$");
            if(reg.test(inputManagerName)){
                for (var i = 0; i < value.length; i++) {
                    if(value[i].pinyinInitials != null && value[i].pinyinInitials.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].factoryCode + '">' + value[i].factoryCnName + '</option>';
                    }
                }
            }else{
                for (var i = 0; i < value.length; i++) {
                    if(value[i].factoryCnName.indexOf(inputManagerName) == 0){
                        con += '<option  value="' + value[i].factoryCode + '">' + value[i].factoryCnName + '</option>';
                    }
                }
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            event.stopPropagation();
            return false;
        } else{
            var con = '<option value=""></option>';
            for (var i = 0; i < value.length; i++) {
                con += '<option  value="' + value[i].factoryCode + '">' + value[i].factoryCnName + '</option>';
            }
            hunt.append(con);
            hunt.selectpicker('refresh');
            return false;
        }
    });
})

var vm=new Vue({
    el:'#vac',
    data:{
        showList: true,
        searchProductName:new Array(),
        searchFactoryName:new Array()
    },
    methods: {
        receiveVac: function () {

            var ele=$("#stockJqGrid");
            //开始编辑

            //结束编辑
            endEdit(ele);
            var ids=ele.jqGrid('getGridParam','selarrrow');
            if(ids.length<1){
                layer.msg("请选择一条或者多条数据");
                return;
            }
            var rows=new Array();
            for(var i=0;i<ids.length;i++){
                var row=ele.jqGrid("getRowData",ids[i]);
                rows.push(row);
            }
           //对数据进行校验
            for(var i=0;i<rows.length;i++){
                var rec=rows[i].receiveNumber;
                if(rec==null ||rec==""||rec==undefined){
                    layer.msg("请输入领取的数量！")
                    return;
                }
                var inputNumber=parseInt(rec);
                    //领取数量
                    var result=isNaN(inputNumber);
                    if(result){
                        layer.msg("请输入数字！");
                        //开启编辑
                        $('#stockJqGrid').editRow(rows[i].id);
                        return;
                    }
                    var amount=parseInt(rows[i].amount);
                    if(inputNumber!=null&&inputNumber!=''&&inputNumber>amount||inputNumber<1){
                        layer.msg("输入的领取数量，必须大于0且小于等于库存数量！");
                        //开启编辑
                        $('#stockJqGrid').editRow(rows[i].id);
                        return;
                    }
            }
            $("#receiveVac").attr("disabled","disabled");
            $.ajax({
                type:"post",
                url:"../receive/vaccine",
                dataType:'json',
                data:JSON.stringify({"rows":JSON.stringify(rows)}),
                contentType:'application/json;charset=UTF-8',//重点关注此参数
                success: function (r) {
                    $("#receiveVac").removeAttr("disabled");
                    if(r.code == 0){
                        layer.msg('操作成功');
                        $("#stockJqGrid").trigger("reloadGrid");
                        $("#receiveGird").trigger("reloadGrid");
                        $("#receiveRecordGird").trigger("reloadGrid");
                    }else{
                        layer.msg(r.msg);
                    }
                },
                error:function (data) {
                    console.log(data);
                }
            });
        }
        ,
        query:function () {

            var searchProductName=$("#searchProductName").find("option:selected").attr('value');
            var searchFactoryName=$("#searchFactoryName").find("option:selected").attr('value');
            var searchBatch=$("#searchBatch").val();
            var searchDate=$("#searchDate").val();
            $("#stockJqGrid").jqGrid('setGridParam',{
                postData:{
                    'searchProductName': searchProductName,
                    'searchBatch': searchBatch,
                    'searchDate': searchDate,
                    'searchFactoryName': searchFactoryName
                },
                page:1
            }).trigger("reloadGrid");
        },
        reset:function () {
            $('#searchProductName').selectpicker('val', "");
            $("#searchProductName").selectpicker('refresh');
            $('#searchFactoryName').selectpicker('val', "");
            $("#searchFactoryName").selectpicker('refresh');
            $("#searchForm")[0].reset();
            $("#stockJqGrid").trigger("reloadGrid");
        },
        output:function () {
            window.location.href="../receive/outputVaccineTransport";
        }
    }

})


function autoGridHeight(){
    var grid_frozenTr = $("#stockJqGrid_frozen").find("tbody tr");
    var grid_Tr = $("#stockJqGrid").find("tbody tr");
    if(grid_Tr&&grid_frozenTr&&grid_frozenTr.size()==grid_Tr.size()){
        grid_Tr.each(function(index,item){
            if(index>0){
                var g_obj = $(this);
                var g_f_obj = $(grid_frozenTr.get(index));
                var tdHeight = g_obj.find("td:first").css("height");
                g_f_obj.find("td").css("height",tdHeight);
            }
        });
    }
}
