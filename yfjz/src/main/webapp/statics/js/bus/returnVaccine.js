$(function () {
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
    //查询接种台库存信息
    $("#returnVac").jqGrid({
        url: '../return/list?type=0',
        datatype: "json",
        //caption:'库存信息',
        colModel: [
            { label: 'id', name: 'infoId',  key: true ,hidden:true},
            { label: 'baseId', name: 'baseId',hidden:true},
            //{ label: 'storeId', name: 'storeId',hidden:true},
            { label: 'equipId', name: 'equipId',hidden:true},
            // { label: '接种医生', name: 'username', width: 80 },
            // { label: '接种台', name: 'storeName', width: 80 },
            { label: '疫苗名称', name: 'productName', width: 150 },
            { label: '批号', name: 'batchnum', width: 120 },
            { label: '失效期', name: 'expiryDate', width: 100 },
            { label: '生产厂家', name: 'factoryName', width: 150 },
            { label: '领取数量(支)', name: 'receiveAmount', width: 110 },
            { label: '剩余数量(支)', name: 'amount', width: 110 },
            { label: '剩余数量(人份)', name: 'personAmount', width: 110 },
            { label: '使用数量(人份)', name: 'useAmount', width: 110 },
            { label: '人份转换', name: 'conversion', width: 80},
            { label: '归还数量(支)', name: 'returnAmount', width: 110,editable:true},
            { label: '报损人份(人份)', name: 'destroyAmount', width: 110,editable:true},
            { label: '归还日期', name: 'returnDate', width: 150,editable:true,editoptions:{
                    dataInit:function(el){
                        var date=formatDateTime(new Date());
                        $(el).val(date);
                        $(el).datetimepicker(
                            {
                                format: 'yyyy-mm-dd',
                                autoclose: true,
                                todayBtn: true,
                                startView: 'month',
                                minView:'month',
                                language:  'zh-CN'
                            }
                        );
                    }
                }

            },
            { label: '归还仓库', name: 'returnStore', width: 120,unformat:Unformat_Select }

        ],
        viewrecords: true,
        height:400,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        //rownumWidth:25,
        shrinkToFit:false,
        multiselect:true,
        autowidth:true,
        pager: "#returnVacPage",
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
            /* // $("#returnVac").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
              $(".ui-jqgrid-bdiv").removeClass("overflow");
              $(".ui-jqgrid-bdiv").css({ "overflow-x" : "auto" })*/
        },
        onSelectRow:function (rowid,status) {

            //初始化下拉框仓库数据
            $.ajax({
                type:"post",
                url:"../tmgrstore/list?page=1&limit=1000&type=0",
                dataType:'json',
                async: false,
                contentType:'application/json;charset=UTF-8',
                success: function (data) {
                    var result=data.page.list;
                    var str="";
                    $.each(result, function (index, item) {
                        str=str+item.id+":"+item.name+";";
                    });
                    str=str.substring(0,str.length-1)
                    jQuery('#returnVac').setColProp('returnStore',{editable: true,edittype : "select",editoptions:{buildSelect:null,dataUrl:null,value:str}
                    });

                }
            })
            //开启编辑
            $("#returnVac").editRow(rowid);
            /**
             * 当输入框失去焦点时，校验输入的数据是否合法
             */
            $("#" + rowid+'_returnAmount').on("blur",function(){
                var row=$("#returnVac").getRowData(rowid);
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
                    if(inputNumber!=null&&inputNumber!=''&&inputNumber>amount||inputNumber<0){
                        layer.msg("输入的归还数量，必须大于等于0且小于等于剩余数量！");
                        return;
                    }
                }

            })
            if(!status){
                $("#returnVac").saveRow(rowid);
            }

        },
    });


    $("#recordGird").jqGrid({
        url: '../return/returnTotalList',
        datatype: "json",
        subGrid : true,
        colModel: [
            { label: 'id', name: 'id', key: true,hidden:true },
            { label: '归还单号', name: 'inCode', width: 80 },
            { label: '归还人员', name: 'username', width: 80 },
            { label: '归还时间', name: 'createTime', width: 80 },
            /*  { label: '出库类型', name: 'type', width: 80,formatter:function(cellvalue, options, rowObject){
                      return cellvalue==0?'疫苗':'其他';
                  }},*/
            { label: '归还仓库', name: 'storeName', width: 80 },
            { label: '备注', name: 'remark', width: 80 },

        ],
        viewrecords: true,
        height: 500,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        pager: "#recordGirdPage",
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
            $("#recordGirdPage").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
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
                url: '../return/returnItemList/'+row_id,
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
                    { label: '归还数量', name: 'amount', width: 80 },
                    { label: '报损人份', name: 'destoryAmount', width: 80 },
                     { label: '使用人份', name: 'useAmount', width: 80 },
                     { label: '领取数量', name: 'receiveAmount', width: 80 }
                ],
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
                viewrecords: true,
                height: "100%",
                rowNum: 100,
                autowidth:true,
                //rowList : [10,30,50]
            });
        }
    });
   $("#myTab li").on("click",function () {
       $("#returnVac").trigger('reloadGrid');
       $("#recordGird").trigger('reloadGrid');
   })

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
    el:'#ret',
    data:{
        showList: true,
        searchProductName:new Array(),
        searchFactoryName:new Array()
    },
    methods: {
        returnVac: function () {
            var ele=$("#returnVac");
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
            console.log(rows)
            //对数据进行校验
            for(var i=0;i<rows.length;i++){
                var rec=rows[i].returnAmount;
                var des=rows[i].destroyAmount;
                var use=rows[i].useAmount;
                var receive=rows[i].receiveAmount;
                var date=rows[i].returnDate;
                var conversion=rows[i].conversion;
                var productName=rows[i].productName;
                if(rec==null ||rec==""||rec==undefined){
                    layer.msg("请输入归还的数量！")
                    return;
                }
                if(des==null ||des==""||des==undefined){
                    layer.msg("请输入报损的数量！")
                    return;
                }
                //校验日期
                var result=IsDate(date);
                if(!result){
                    return;
                }
                var inputNumber=parseInt(rec);
                var desNumber=parseInt(des);
                var useNumber=parseInt(use);
                var con=parseInt(conversion);
                var rec=parseInt(receive);
                //领取数量
                var result=isNaN(inputNumber);
                if(result){
                    layer.msg("请输入数字！");
                    //开启编辑
                    $('#returnVac').editRow(rows[i].id);
                    return;
                }
                var amount=parseInt(rows[i].amount);
                if(desNumber<0){
                    layer.msg("输入的报损数量，必须大于等于0！");
                    return;
                }
                if(inputNumber!=null&&inputNumber!=''&&inputNumber>amount||inputNumber<0){
                    layer.msg("输入的归还数量，必须大于等于0且小于等于库存数量！");
                    //开启编辑
                    $('#returnVac').editRow(rows[i].id);
                    return;
                }
               var total= (desNumber+useNumber)/con+inputNumber;
                if(rec!=total){
                    layer.msg(productName+" 归还数量+耗损人份+使用人份折合后比领用量少，请重新填写");
                    return;
                }
            }
            $("#returnBtn").attr("disabled","disabled");
            $.ajax({
                type:"post",
                url:"../return/save",
                dataType:'json',
                data:JSON.stringify({"rows":JSON.stringify(rows)}),
                contentType:'application/json;charset=UTF-8',
                success: function (r) {
                    $("#returnBtn").removeAttr("disabled");
                    if(r.code == 0){
                        layer.msg('归还成功');
                        $("#returnVac").trigger("reloadGrid");
                    }else{
                        alert(r.msg);
                    }
                },
                error:function (data) {
                    console.log(data);
                }
            });
        }
        ,
        returnbatch:function () {
           /* var msg="请确定使用批量归还吗？只能归还人份转换为1的疫苗！";
            if(confirm(msg)==true)
            {
                var btn=$("#returnBatch");
                var ele=$("#returnVac");
                var rows=getRows(ele);
                var resultRow=new  Array();
                for(var i=0;i<rows.length;i++){
                    if(rows[i].conversion==1){
                        ele.jqGrid('setSelection',rows[i].infoId);
                        ele.saveRow(rows[i].infoId);
                        var row= ele.jqGrid("getRowData",rows[i].infoId);
                        resultRow.push(row);
                    }
                }
                console.log(resultRow)
                return true;
            }else{
                return false;
            }
*/
            var msg = "请确定使用批量归还吗?<p style='color: red'>只能归还人份转换为1的疫苗！</p>"
            var ele = $("#returnVac");
            var rows = getRows(ele);
            if (rows.length == 0) {
                layer.msg("没有可以归还的疫苗！");
                return;
            }
            var pass=false;
            layer.confirm(msg, function (index) {
                var btn = $("#returnBatch");
                var resultRow = new Array();
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].conversion == 1) {
                        ele.jqGrid('setSelection', rows[i].infoId);
                        ele.saveRow(rows[i].infoId);
                        var row = ele.jqGrid("getRowData", rows[i].infoId);
                        resultRow.push(row);
                    }
                }
                if(!pass){
                    pass=true;
                    $.ajax({
                        type: "post",
                        url: "../return/save",
                        dataType: 'json',
                        data: JSON.stringify({"rows": JSON.stringify(resultRow)}),
                        contentType: 'application/json;charset=UTF-8',
                        success: function (r) {
                            if (r.code == 0) {
                                layer.msg('操作成功');
                                    $("#returnVac").trigger("reloadGrid");
                            } else {
                                layer.msg(r.msg);
                            }
                        },
                        error: function (data) {
                            console.log(data);
                        }
                    });
                }
                layer.close(index);

            })


        },
        query:function () {

            var searchProductName=$("#searchProductName").find("option:selected").attr('value');
            var searchFactoryName=$("#searchFactoryName").find("option:selected").attr('value');
            var searchBatch=$("#searchBatch").val();
            var searchDate=$("#searchDate").val();
            $("#returnVac").jqGrid('setGridParam',{
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
            $("#returnVac").trigger("reloadGrid");
        }
    }

})
function IsDate(str) {

    //我们将在这个方法里面进行日期格式的检测

    //正则表达式
    var reg = /^(\d{4})-(\d{2})-(\d{2})$/;

    //用正则表达匹配
    var arr = reg.exec(str);

    if (str == "") {
        alert("请输入日期");
        return false;
    }

    else if (!reg.test(str) && RegExp.$2 <= 12 && RegExp.$3 <= 31) {
        alert("请保证输入的为2018-01-02日期格式");
        return false;
    } else {
        return true;
    }

}
